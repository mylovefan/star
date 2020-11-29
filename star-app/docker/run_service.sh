#!/usr/bin/env bash
#导入环境变量
#参数1：安装swarm主机的ip和端口，默认192.168.1.35:2375
#参数2：docker私服地址，上传镜像
#参数1：环境值
source docker_env.sh $1 $2 $3
#docker stack  deploy -c docker-stack.yml  gmc-app
REMOTE_IMAGE_REPOSITORY=${DOCKER_HUB}/${IMAGE_REPOSITORY}
#创建服务
createService(){
    PORT_ARG="";
    #设置开放端口
    if [ ! -z  "${PROJECT_PORT}" ];then
        TYPEPATTERN="^.+ .+$"
        if [[ "${PROJECT_PORT[*]}" =~ ${TYPEPATTERN} ]];then
            for SPORT in ${PROJECT_PORT[@]}
            do
              if echo ${SPORT} | grep : >/dev/null ; then
                PORT_ARG="${PORT_ARG} -p ${SPORT}";
              else
                PORT_ARG="${PORT_ARG} -p ${SPORT}:${SPORT}";
              fi
            done
        else
            if echo ${PROJECT_PORT} | grep : >/dev/null ; then
                PORT_ARG="-p ${PROJECT_PORT}";
            else
                PORT_ARG="-p ${PROJECT_PORT}:${PROJECT_PORT}";
            fi
        fi
    fi
    #设置节点个数
    RUN_NODE_ARG="--constraint=node.role==worker";
    if [ ! -z  "${RUN_NODE}" ];then
        RUN_NODE_ARG="--constraint=node.labels.nodeName==${RUN_NODE}";
    fi
    SERVICE_REPLICAS_ARG=""
    if [ ! -z  "${SERVICE_REPLICAS}" ];then
        SERVICE_REPLICAS_ARG="--replicas ${SERVICE_REPLICAS}";
    fi
    echo docker  -H ${SWARM_MASTER_DOCKERHOST} service create --name ${SERVICE_NAME} --no-resolve-image --with-registry-auth ${PORT_ARG} ${RUN_NODE_ARG} ${SERVICE_REPLICAS_ARG} ${DOCKER_ARGS} --env ${INPUT_SB_ENVNAME}=${INPUT_SB_ENV} --network=${NETWORK_NAME} ${REMOTE_IMAGE_REPOSITORY}
    docker  -H ${SWARM_MASTER_DOCKERHOST} service create --name ${SERVICE_NAME} --no-resolve-image --with-registry-auth ${PORT_ARG} ${RUN_NODE_ARG}  ${SERVICE_REPLICAS_ARG} ${DOCKER_ARGS}  --env ${INPUT_SB_ENVNAME}=${INPUT_SB_ENV} --network=${NETWORK_NAME} ${REMOTE_IMAGE_REPOSITORY}
}
#没有定义全局网卡名称，自动生成。
if [ -z "${NETWORK_NAME}" ];then
    NETWORK_NAME=${SERVICE_NAME}-network
fi
#创建共享网络
if ! docker  -H ${SWARM_MASTER_DOCKERHOST} network ls | grep ${NETWORK_NAME} ;then
    docker  -H ${SWARM_MASTER_DOCKERHOST} network create --subnet 172.20.0.0/16 --gateway 172.20.0.1 --driver overlay ${NETWORK_NAME}
fi
#判断该服务是否存在
if docker -H ${SWARM_MASTER_DOCKERHOST} service ls | grep ${SERVICE_NAME}
then
    #获取当前服务目前正在运行版本
    PRE_REMOTE_IMAGE_REPOSITORY=`docker -H ${SWARM_MASTER_DOCKERHOST} service ls | grep ${SERVICE_NAME} | awk -F" +" '{print $5}'  `
    #判断是滚动升级还是直接替换，如果正在运行版本和目前版本是一致,删除目前版本
    if [ "${REMOTE_IMAGE_REPOSITORY}" == "${PRE_REMOTE_IMAGE_REPOSITORY}" ];then
        echo Same mirror exists, deletin
        docker -H ${SWARM_MASTER_DOCKERHOST} service rm ${SERVICE_NAME}
        createService
    else
        echo Mirror promotion,old version ${PRE_REMOTE_IMAGE_REPOSITORY}, new version ${REMOTE_IMAGE_REPOSITORY}
        docker -H ${SWARM_MASTER_DOCKERHOST} service update --image ${REMOTE_IMAGE_REPOSITORY} ${SERVICE_NAME}
    fi
else
    echo first create service.
    #不存在直接创建
    createService
fi

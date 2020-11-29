#!/usr/bin/env bash
#导入环境变量
source docker_env.sh -1 $1 $2
#不同Dockerfile，自行实现该方法。
buildImage(){
    #删除所有之前旧版本的docker容器
    IFS=$'\n'
    OLDIFS="$IFS"
    for v in `docker images | grep ${IMAGE_NAME} `;do
         PRE_IMAGE_VERSION=`echo $v | awk -F" +" '{print $2}'`
         PRE_IMAGE_CID=`echo $v | awk -F" +" '{print $3}'`
         if [ "<none>" == "${PRE_IMAGE_VERSION}" ];then
                docker rmi ${PRE_IMAGE_CID}
         fi
    done
    IFS="$OLDIFS"
    #打包镜像
    echo docker build -t ${IMAGE_REPOSITORY} --build-arg INPUT_JAR_NAME=${APP_NAME} --build-arg INPUT_SB_ENV=${INPUT_SB_ENV}    ../
    docker build -t ${IMAGE_REPOSITORY} --build-arg INPUT_JAR_NAME=${APP_NAME} --build-arg INPUT_SB_ENV=${INPUT_SB_ENV}   ../
}
buildImage
#登录到私服
#docker login ${DOCKER_HUB}
#tag到私服,本机必须先登录到
REMOTE_IMAGE_REPOSITORY=${DOCKER_HUB}/${IMAGE_REPOSITORY}
docker tag ${IMAGE_REPOSITORY} ${REMOTE_IMAGE_REPOSITORY}
docker push ${REMOTE_IMAGE_REPOSITORY}


#!/usr/bin/env bash
#docker私服
DOCKER_HUB=192.168.1.138:5000
#docker集群主机
SWARM_MASTER_DOCKERHOST=192.168.1.35:2375
#环境变量名称
INPUT_SB_ENVNAME=env
#环境变量值
INPUT_SB_ENV=dev

if [ ! -z "$1" -a "-1" != "$1" ];then
    SWARM_MASTER_DOCKERHOST=$1
fi
if [ ! -z "$2" -a "-1" != "$2"  ];then
    DOCKER_HUB=$2
fi
if [ ! -z "$3" ];then
    INPUT_SB_ENV=$3
fi
#项目作用域
PROJECT_SCOPE=spp
#项目名称 对应artifactId
PROJECT_NAME=spp-app
#镜像版本
PROJECT_VERSION=1.0.2
#版本后缀，没有置空
PROJECT_VERSION_SUFFIX="SNAPSHOT"
#对外开放端口，多个端口注意使用(8080 9090)空格隔开，不要使用[[[,]]]
PROJECT_PORT=18011
#网络名称
NETWORK_NAME=gvt_network
#运行节点label nodeName对应值
RUN_NODE=
#镜像名称
IMAGE_NAME=${PROJECT_SCOPE}/${PROJECT_NAME}
#打包镜像名称和标记
IMAGE_REPOSITORY=${IMAGE_NAME}:${INPUT_SB_ENV}-${PROJECT_VERSION}
#应用名称（java平台表示制作jar名称），不同平台重写该方法
getAppName(){
    #程序编译jar名称
    APP_NAME=${PROJECT_NAME}-${PROJECT_VERSION}-SNAPSHOT.jar
    #没有项目版本控制直接项目名名称-版本号.jar
    if [ -z ${PROJECT_VERSION_SUFFIX} ];then
      APP_NAME=${PROJECT_NAME}-${PROJECT_VERSION}.jar
    fi
}
getAppName
#发布到docker的服务名称
SERVICE_NAME=${PROJECT_NAME}
#节点个数
SERVICE_REPLICAS=1
#其他docker运行参数
DOCKER_ARGS="--limit-cpu 2 --limit-memory 2G  --mount type=bind,src=/etc/localtime,dst=/etc/localtime  --mount type=bind,src=/etc/timezone,dst=/etc/timezone --mount type=bind,src=/application/logs,dst=/application/logs "
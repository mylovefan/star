#docker脚本
## docker_env.sh
 用于设置构建的环境变量,该文件用户需设置5个环境变量
 - 项目名称,如PROJECT_NAME=gmc-app。
 - 项目版本（同maven版本一致），如PROJECT_VERSION=3.0.1。
 - 版本后缀，比如版本后的SNASHOT,RELEASE ，如PROJECT_VERSION_SUFFIX=SNASHOT。
   没有后缀置空。
 - 作用域（同一个作用域下多个项目，比如gmc下有gmc-app和gmc-consumer）PROJECT_SCOPE=gmc。
 - 工程端口，对外映射端口 如：PROJECT_PORT=6800。
 - 网络名称，同一个网络在同一局域网，默认值：NETWORK_NAME。
 - 运行节点，指定运行主机名，不指定该变量，由master分配。
## build_images.sh
 用于打包镜像，将镜像推送到私服。
## run_stack.sh
 镜像推送到swarm集群中运行。 
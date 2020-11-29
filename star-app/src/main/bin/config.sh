#!bin/bash
if [ $# -lt 1 ];then
    echo "调用config.sh的参数错误"
    exit 1
fi

env=$3

APP_NAME=$2


# 日志目录
LOG_PATH=$APP_HOME/logs

#JPDA_OPTS="-Xdebug -Xrunjdwp:transport=dt_socket,address=58001,server=y,suspend=n"

CLASSPATH="$APP_HOME/conf"

JAVA_OPTS="$JAVA_OPTS -Denv=$env -Dspring.config.location=file:$APP_HOME/conf/ -Dspring.profiles.active=$env -Dlog.path=$LOG_PATH"

JAVA_OPTS="$JAVA_OPTS -Xms1024m -Xmx2048m -classpath $CLASSPATH"

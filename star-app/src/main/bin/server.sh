#!/bin/bash
if [ $# -lt 1 ];then
    echo "参数: start|stop"
    exit 1
fi

PRG="$0"
EX_JAVA_OPT=$4
while [ -h "$PRG" ]; do
  ls=`ls -ld "$PRG"`
  link=`expr "$ls" : '.*-> \(.*\)$'`
  if expr "$link" : '/.*' > /dev/null; then
    PRG="$link"
  else
    PRG=`dirname "$PRG"`/"$link"
  fi
done

PRGDIR=`dirname "$PRG"`

[ -z "$APP_HOME" ] && APP_HOME=`cd "$PRGDIR/.." >/dev/null; pwd`

echo "Using APP_HOME:    $APP_HOME"

# 调用配置脚本
source $APP_HOME/bin/config.sh "$APP_HOME" $2 $3

createDir() {
	if [ ! -d "$1" ];then
		mkdir -p "$1"
	fi
}

start () {
    appJarFile=$APP_HOME/$APP_NAME
    #拓展额外参数
	if [ ! -z  "$EX_JAVA_OPT" ];then
	    JAVA_OPTS="$JAVA_OPTS $EX_JAVA_OPT"
	fi;
    echo "Using JAVA_OPTS:    $JAVA_OPTS"
    echo "Using CLASSPATH:    $CLASSPATH"
    echo "Using APP:          $appJarFile"

	createDir "$LOG_PATH"
	
	java $JAVA_OPTS -jar $appJarFile
}

jpda () {
	echo "JPDA_OPTS:    $JPDA_OPTS"
	
	start	
}

case $1 in
	start)
	start
	;;
	jpda)
	jpda
	;;
esac

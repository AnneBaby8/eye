#!/bin/sh
APP_NAME=eye
VERSION=1.0-SNAPSHOT
SERVER_PORT=8090
ACTIVE_PROFILE=dev
APP_HOME=$(dirname $(readlink -f "$0"))
JAR_FILE=$APP_HOME/$APP_NAME-$VERSION.jar

if [ ! -d "$APP_HOME/log" ];then
  mkdir $APP_HOME/log
fi

if [ ! -d "$APP_HOME/log/gc" ];then
  mkdir $APP_HOME/log/gc
fi

LOG_FILE=$APP_HOME/nohup.out
GC_LOG_PATH=$APP_HOME/log/gc/gc-$APP_NAME-$GC_TIME.log
HEAP_DUMP_PATH=$APP_HOME/log/heapdump/
#jvm参数
JVM_OPTS="-Dapp.name=$APP_NAME -Duser.timezone=GMT+08 -Dfile.encoding=UTF8 -Dlog.path=$APP_HOME/logs -Xms512M -Xmx512M -XX:NewRatio=3 -XX:SurvivorRatio=8 -XX:+UseConcMarkSweepGC -XX:ParallelCMSThreads=2 -XX:CMSInitiatingOccupancyFraction=70 -XX:MaxGCPauseMillis=1000 -XX:+PrintGCDetails -XX:+PrintGCDateStamps -Xloggc:$GC_LOG_PATH -XX:+HeapDumpOnOutOfMemoryError -XX:+DisableExplicitGC -XX:HeapDumpPath=$HEAP_DUMP_PATH"

appPid=0
monitorPid=0

start(){
  checkappPid
  if [ ! -n "$appPid" ]; then
    nohup $JAVA_HOME/bin/java -jar $JVM_OPTS $JAR_FILE --spring.profiles.active=$ACTIVE_PROFILE --server.port=$SERVER_PORT >$LOG_FILE 2>&1 &
    if [ ! "$CHECK_URL" ]; then
        tail -f $LOG_FILE
    fi
  else
      echo "$APP_NAME is running PID: $appPid"
  fi
}


status(){
   checkappPid
   if [ ! -n "$appPid" ]; then
     echo "$APP_NAME not runing"
   else
     echo "$APP_NAME runing PID: $appPid"
   fi
}

checkappPid(){
    appPid=`ps -ef |grep $JAR_FILE |grep -v grep |awk '{print $2}'`
}

stop(){
    checkappPid
    if [ ! -n "$appPid" ]; then
      echo "$APP_NAME not running"
    else
      kill -9 $appPid
      echo "$APP_NAME stopped..."
    fi
}

restart(){
    stop
    sleep 1s
    start
}

case $1 in
          start) start;;
          stop)  stop;;
          restart)  restart;;
          status)  status;;
              *)  echo "require start|stop|restart|status"  ;;
esac




















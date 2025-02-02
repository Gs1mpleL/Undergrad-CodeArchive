#!/bin/bash

# 检查参数数量
if [ "$#" -ne 1 ]; then
    echo "Usage: $0 <module>"
    exit 1
fi

MODULE=$1
PORT=80

# 根据模块设置端口
if [ "$MODULE" == "myweb-gateway" ]; then
    PORT=80
elif [ "$MODULE" == "myweb-crawler" ]; then
    PORT=9002
elif [ "$MODULE" == "myweb-user" ]; then
    PORT=9001
elif [ "$MODULE" == "myweb-learn" ]; then
    PORT=9003
elif [ "$MODULE" == "myweb-job" ]; then
    PORT=9004
else
    echo "Invalid module: $MODULE"
    exit 1
fi
 result=`echo $(lsof -i:$PORT | grep LISTEN | awk '{print $2}')`
if [[ "$result" != "" ]];then
    echo "正在停止端口[$PORT]的进程...."
    kill -9 $result
fi

# 启动Java应用
nohup java -jar -Xmx300m /root/apps/$MODULE-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod > /root/apps/log/$MODULE.log 2>&1 &
echo "正在启动[$MODULE]....."
# 检查Java进程是否成功启动（这里只是一个简单的检查，实际情况可能需要更复杂的逻辑）
if ps -ef | grep -v grep | grep $MODULE > /dev/null; then
    echo "[$MODULE]启动成功"
else
    echo "[$MODULE]启动失败"
fi
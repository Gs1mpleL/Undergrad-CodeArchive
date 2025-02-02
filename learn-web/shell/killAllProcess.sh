#!/bin/bash

PORTS=(80 9001 9002 9003 9004)  # 定义端口列表

# shellcheck disable=SC2068
for port in ${PORTS[@]}; do
    result=`echo $(lsof -i:$port | grep LISTEN | awk '{print $2}')`
    if [[ "$result" != "" ]];then
        echo "正在停止端口[$port]的进程...."
        kill -9 $result
    fi
done
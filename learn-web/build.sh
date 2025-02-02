# 部署项目到服务器
source ~/.bash_profile
# 检查是否传递了参数
if [ $# -eq 0 ]; then
    echo "错误：请提供需要部署的微服务名称。"
    echo "用法：sh $0 微服务名称"
    exit 1
fi
echo "执行前请确保当前在项目目录中!"
current_dir=$(pwd)
echo "当前目录:[$current_dir]"
buildingModule=$1
if [ $buildingModule = myweb-vue ]; then
    cd $current_dir/$buildingModule || exit
    npm run build
    cp -r dist/* $current_dir/myweb-gateway/src/main/resources/static/
    buildingModule=myweb-gateway
    echo 前端页面生成成功
fi
cd $current_dir/$buildingModule || exit
mvn clean install -Dmaven.test.skip=true -Ptest
cd target || exit
scp $buildingModule-0.0.1-SNAPSHOT.jar root@120.46.137.95:/root/apps
ssh root@120.46.137.95 "sh /root/apps/build.sh $buildingModule"
const {defineConfig} = require('@vue/cli-service')
module.exports = defineConfig({
    transpileDependencies: true,
    lintOnSave: false,
    devServer: {
        client: {
            overlay: false
        },
        proxy: {
            '/api': {
                target: 'http://localhost',
                changeOrigin: true,
                secure: false,//开启代理：在本地会创建一个虚拟服务端，然后发送请求的数据，并同时接收请求
            }
        }
    }
})


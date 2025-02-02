import axios from 'axios'

const http = axios.create({
    baseURL: '/api',
    timeout: 1000000,
})

// 请求拦截器
// 添加请求拦截器
http.interceptors.request.use(
    config => {
        // 在请求头中添加Access-Control-Allow-Origin
        config.headers['Access-Control-Allow-Origin'] = '*'; // 或者指定具体的域名
        return config;
    },
    error => {
        // 处理请求错误
        console.error(error);
        return Promise.reject(error);
    }
);


// 响应拦截器
http.interceptors.response.use(function (response) {
    return response;
}, function (error) {
    return Promise.reject(error);
});

export default http
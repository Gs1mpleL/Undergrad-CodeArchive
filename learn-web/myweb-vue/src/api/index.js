import http from '../utils/request'
// 获取地理位置
export const getLocal = (ip) => {
    return http.get("/myweb-crawler/baidu/getLocal/" + ip)
}

// 请求首页数据
export const getHomeData = () => {
    return http.get("/myweb-user/getHomeData")
}

// 获取菜单
export const getMenu = () => {
    return http.get("/myweb-user/getMenu");
}

// 更新菜单
export const updateMenu = (from) => {
    return http.post("/myweb-user/updateMenu", from);
}

// 删除菜单
export const delMenu = (from) => {
    return http.post("/myweb-user/delMenu", from);
}

// 添加菜单
export const addMenu = (from) => {
    return http.post("/myweb-user/addMenu", from);
}

//B站每日任务
export const biliTask = (totalCookie) => {
    return http.post("/myweb-crawler/bilibili/dailyTask", totalCookie);
}

// 获取微博热搜
export const weiboNews = () => {
    return http.get("/myweb-crawler/weibo/news")
}

// 获取文件列表
export const getFiles = () => {
    return http.get("/myweb-user/file/listForTable")
}

// 删除文件列表
export const deleteFile = (fileName) => {
    let dic = {
        url: fileName
    }
    return http.post("/myweb-user/file/deleteFile", dic)
}

// 删除文件目录列表
export const listFileDir = () => {
    return http.get("/myweb-user/file/listDir")
}


// 获取markdown
export const listMarkDown = () => {
    return http.get("/myweb-learn/markdown/list")
}

// 更新markdown
export const updateMarkDown = (json) => {
    return http.post("/myweb-learn/markdown/update", json)
}


// 删除markdown
export const delMarkDown = (json) => {
    return http.post("/myweb-learn/markdown/del", json)
}

// 更新主页卡片
export const updateCart = (json) => {
    return http.post("/myweb-user/updateCart", json)
}


// 增加体重记录
export const addWeight = (json) => {
    return http.post("/myweb-user/addWeight", json)
}
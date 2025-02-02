import Vue from 'vue'
import VueRouter from 'vue-router'
import home from '../views/home.vue'
import main from '../views/main.vue'
import learn from '../views/learn.vue'
import menu from '../views/menu.vue'
import bilibili from '../views/bilibili.vue'
import file from '../views/file.vue'
import live from '../views/live.vue'
import test from '../views/test.vue'
import markdown from '../views/markdown.vue'

Vue.use(VueRouter)
const routes = [
    {
        path: "/",
        component: main,
        redirect: "/home",
        children: [
            {path: '/home', component: home},
            {path: '/learn', component: learn},
            {path: '/menu', component: menu},
            {path: '/crawler/bilibili', component: bilibili},
            {path: '/file', component: file},
            {path: '/test', component: test},
            {path: '/live', component: live},
            {path: '/markdown', component: markdown}
        ]
    }
]

const router = new VueRouter({
    routes // (缩写) 相当于 routes: routes
})

export default router

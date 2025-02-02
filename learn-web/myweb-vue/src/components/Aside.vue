<template>
  <el-menu :collapse="isCollapse" active-text-color="#ffd04b" background-color="#545c64" class="el-menu-vertical-demo"
           default-active="首页" style="position: relative;" text-color="#fff" @close="handleClose" @open="handleOpen">
    <h3>{{ isCollapse ? "晚风" : "晚风的网站" }}</h3>
    <el-menu-item v-for="item in noChildren" :key="item.name" :index="item.name" @click='clickMenu(item)'>
      <i :class="`el-icon-${item.icon}`"></i>
      <span slot="title">{{ item.label }}</span>
    </el-menu-item>

    <el-submenu v-for="item in haveChildren" :key="item.label" :index="item.label">
      <template slot="title">
        <i :class="`el-icon-${item.icon}`"></i>
        <span slot="title">{{ item.label }}</span>
      </template>
      <el-menu-item-group v-for="subItem in item.children" :key="subItem.path">
        <el-menu-item :index="subItem.path" @click='clickMenu(subItem)'><i
            :class="`el-icon-${subItem.icon}`"></i>{{ subItem.label }}
        </el-menu-item>
      </el-menu-item-group>
    </el-submenu>
    <div style="position: absolute;bottom: 0;width: 100%;display: flex;justify-content: center;color: #cccccc;">
      晋ICP备2024033615号
    </div>
  </el-menu>
</template>

<style lang="less" scoped>
.el-menu-vertical-demo:not(.el-menu--collapse) {
  width: 200px;
  min-height: 400px;
}

.el-menu {
  height: 100vh;
  border-right: none;
  // 左侧标题的css
  h3 {
    color: #fff;
    text-align: center;
    line-height: 48px;
    font-size: 16px;
    font-weight: 400;
  }
}
</style>

<script>
import {getMenu} from '@/api'

export default {
  data() {
    return {
      menuData: []
    };
  },
  mounted() {
    getMenu().then((data) => {
      data = data.data.data
      this.menuData = data
      this.$store.state.tab.menuData = data
    })
  },
  methods: {
    handleOpen(key, keyPath) {
      console.log(key, keyPath);
    },
    handleClose(key, keyPath) {
      console.log(key, keyPath);
    },
    // 点击菜单进行跳转
    clickMenu(item) {
      if (item.path.includes('http')) {
        window.open(item.path, '_blank');
      } else if (item.path !== this.$route.path && !(this.$route.path === '/home' && (item.path === '/'))) {
        this.$router.push(item.path)
      }

    }
  },
  computed: {
    // 没有子菜单
    noChildren() {
      return this.menuData.filter(item => item.children.length === 0)
    },
    // 有子菜单
    haveChildren() {
      return this.menuData.filter(item => item.children.length !== 0)
    },
    isCollapse() {
      return this.$store.state.tab.isCollapse
    }
  }
}
</script>
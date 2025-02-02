<template>
  <div>
    <el-container class="operation-wrapper">
      <el-aside width="auto">
        <!--侧边栏组件-->
        <Aside/>
      </el-aside>
      <el-container>
        <el-header>
          <!--顶部组件-->
          <Header/>
        </el-header>
        <el-main>
          <router-view></router-view>
        </el-main>
      </el-container>
    </el-container>

  </div>
</template>
<script>
import Aside from '../components/Aside.vue'
import Header from '../components/Header.vue'

export default {
  data() {
    return {}
  },
  components: {
    Aside,
    Header
  }
}
</script>
<style scoped>
.operation-wrapper {
  width: 100% !important;

  .el-header {
    padding: 0;
  }

  .el-aside {
    height: 100vh;
    overflow-y: auto;
  }

  .el-main {
    padding: 0 16px !important;
    height: calc(100vh - 200px);
    overflow-y: auto;
  }
}
</style>
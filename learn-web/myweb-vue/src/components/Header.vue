<template>
  <div class="header-container">
    <div class="l-content">
      <el-button icon="el-icon-menu" size="mini" @click="handleMenu"></el-button>
      <span class="text">首页</span>
    </div>
    <div class="r-content">
      <el-dropdown>
      <span class="el-dropdown-link">
        <img alt="晚风" class="userImg" src="../assets/wanfeng.jpg">
      </span>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item>个人中心</el-dropdown-item>
          <el-dropdown-item>修改密码</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
  </div>
</template>
<script>
export default {
  data() {
    return {}
  },
  methods: {
    handleMenu() {
      this.$store.commit('collapseMenu')
    }
  }
}
</script>
<style lang="less" scoped>
.header-container {
  padding: 0 10px;
  background-color: #333;
  height: 50px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.text {
  color: #fff;
  font-size: 14px;
  margin-left: 10px;
}

.userImg {
  align-items: center;
  margin-right: 15px;
  width: 40px;
  height: 40px;
  border-radius: 50%;
}
</style>
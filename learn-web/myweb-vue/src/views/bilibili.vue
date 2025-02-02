<template>
  <div>
    <el-input v-model="input" class="cookieInput" placeholder="请输入完整的B站Cookie"></el-input>
    <el-row>
      <el-button :loading="isLoading" class="submitButton" round type="primary" @click="handleClick()">开始执行
      </el-button>
    </el-row>
    <el-card class="box-card" shadow="hover">
      <div slot="header" class="clearfix">
        <span>操作日志</span>
      </div>
      <div v-for="item in log" :key="item" class="text item">
        {{ item }}
      </div>
    </el-card>
  </div>
</template>

<script>
import {biliTask} from '@/api'

export default {
  data() {
    return {
      active: 0,
      intervalId: null, // 用于存储setInterval返回的ID
      input: 'liuzhuohao123(默认，自定义执行请输入完整B站Cookie)',
      log: [],
      isLoading: false,
    };
  },
  methods: {
    handleClick() {
      let dic = {
        "totalCookie": this.input
      }
      this.log = this.log.concat(['开始执行,totalCookie=' + dic.totalCookie])
      this.isLoading = true
      biliTask(dic).then(data => {
        if (data.data.flag) {
          this.$message.success(data.data.message)
          this.log = this.log.concat(data.data.data.split("\n"))
        } else {
          this.$message.error(data.data.message)
          this.log = this.log.concat([data.data.message])
        }
        this.isLoading = false
      })
    }
  },
};
</script>
<style>
.cookieInput {
  margin-top: 20px;
  margin-bottom: 20px;
}

.submitButton {
  margin-bottom: 20px;
}
</style>
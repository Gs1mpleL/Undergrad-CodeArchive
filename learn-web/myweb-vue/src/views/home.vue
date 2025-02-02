<template>
  <el-row :gutter="20" style="height: 95%">

    <!--左侧-->
    <el-col :span="8" style="display: flex;  flex-direction: column;  height: 100%; /* 确保容器占据整个可用高度 */  ">
      <el-card :shadow="'hover'" style="flex: 0 0 37%;">
        <div class="user">
          <img alt="d" src="../assets/wanfeng.jpg">
          <div class="userinfo">
            <p class="name">{{ userInfo.name }}</p>
            <p class="access">{{ userInfo.position }}</p>
          </div>
        </div>
        <div class="login-info">
          <p>登录地点:<span>{{ userInfo.position }}</span></p>
        </div>
      </el-card>
      <el-card :shadow="'hover'" style="margin-top: 10px;flex: 0 0 63%;">
        <weibo/>
      </el-card>
    </el-col>

    <!--右侧-->
    <el-col :span="16" style="display: flex;  flex-direction: column;  height: 100%;">
      <!--小卡片区域-->
      <div class="num" style="flex: 0 0 22%;">
        <el-card v-for="item in cardData" :key="item.id" :body-style="{display:'flex', padding:0}" :shadow="'hover'">
          <i :class="`el-icon-${item.icon}`" :style="{background: item.color}" class="icon"
             @click="handleIconClick(item)"></i>
          <div class="detail">
            <p class="content">{{ item.content }}</p>
            <p class="title">{{ item.title }}</p>
          </div>
        </el-card>
      </div>


      <el-card :body-style="{height:'100%',width:'100%', padding:0}" :shadow="'hover'" style="flex: 0 0 40%;">
        <!--折线图-->
        <div ref="echarts1" style="margin-left: 30px;height: 100%; width: 100%;"></div>
      </el-card>


      <div class="graph" style="flex: 0 0 28%;">
        <!--2个小图标-->
        <el-card :shadow="'hover'" style="height: 260px">待使用</el-card>
        <el-card :shadow="'hover'" style="height: 260px">待使用</el-card>
      </div>
    </el-col>

    <el-dialog :visible.sync="CardDialogVisible" title="修改卡片" width="30%">
      <el-form ref="form" :model="curUpdateCard" label-width="80px">
        <el-form-item label="标题">
          <el-input v-model="curUpdateCard.title"></el-input>
        </el-form-item>
        <el-form-item label="图标">
          <el-input v-model="curUpdateCard.icon"></el-input>
        </el-form-item>
        <el-form-item label="颜色">
          <el-color-picker v-model="curUpdateCard.color"></el-color-picker>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="CardDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="handleSubmit">确 定</el-button>
      </span>
    </el-dialog>
  </el-row>


</template>
<script>
import {getHomeData, updateCart} from '@/api'
import weibo from "@/components/weibo.vue";
import * as echarts from 'echarts';

let echarts1;
export default {
  data() {
    return {
      userInfo: {},
      cardData: [],
      CardDialogVisible: false,
      curUpdateCard: {},
      coinLogs: []
    }
  },
  mounted() {
    // 获取主页信息
    getHomeData().then((data) => {
      this.userInfo = data.data.data.userInfo
      this.cardData = data.data.data.cardData
      this.coinLogs = data.data.data.coinLogs
      this.userInfo.time = formatDateTime(this.userInfo.time)
      this.drawChart();
    })
  },
  components: {
    weibo
  },
  methods: {
    drawChart() {
      // 基于准备好的dom，初始化echarts实例
      const myChart = echarts.init(this.$refs.echarts1);

      // 指定图表的配置项和数据
      const option = {
        title: {
          text: 'B站硬币变化',
          left: 'center', // 标题水平居中
          textAlign: 'center', // 文字在标题区域内水平居中
          top: 'top', // 标题距离容器顶部的距离，这里设置为顶部，你可以根据需要调整
        },
        tooltip: {
          trigger: 'axis' // 触发类型，默认数据触发，可选为：'item'、'axis'
        },
        xAxis: {
          type: 'category',
          data: this.coinLogs.map(item => item.time) // x轴数据
        },
        yAxis: {
          type: 'value'
        },
        series: [{
          data: this.coinLogs.map(item => item.curTotal), // 设置curTotal为y轴数据
          type: 'line', // 图表类型
          symbol: 'circle', // 设置数据点的形状为圆形
          symbolSize: 5, // 设置数据点的大小
          showSymbol: false, // 默认不显示数据点
        }]
      };

      // 使用刚指定的配置项和数据显示图表。
      myChart.setOption(option);
    },
    handleIconClick(item) {
      this.curUpdateCard = item;
      this.CardDialogVisible = true;
    },
    handleSubmit() {
      updateCart(this.curUpdateCard).then(data => {
        if (data.data.data) {
          this.$message.success(data.data.message)
          this.CardDialogVisible = false;
        } else {
          this.$message.error(data.data.message)
        }

      })
    }
  }
}

function formatDateTime(dateString) {
  // 创建Date对象
  const date = new Date(dateString);
  // 获取年、月、日、小时、分钟和秒
  const year = date.getFullYear();
  const month = date.getMonth() + 1; // 月份从0开始，所以要加1
  const day = date.getDate();
  const hours = date.getHours();
  const minutes = date.getMinutes();
  let seconds = date.getSeconds();
  if (seconds < 10) {
    seconds = "0" + seconds;
  }
  // 格式化输出
  return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
}


function formatDate(dateString) {
  if (dateString === undefined) {
    return "刷新后显示！"
  }
  var date = new Date(dateString);
  var month = String(date.getMonth() + 1).padStart(2, '0'); // 转换为两位数，如 "01"
  var day = String(date.getDate()).padStart(2, '0'); // 转换为两位数，如 "01"
  var hours = String(date.getHours()).padStart(2, '0'); // 转换为两位数，如 "01"
  var minutes = String(date.getMinutes()).padStart(2, '0'); // 转换为两位数，如 "01"

  return `${day}`;
}
</script>
<style lang="less" scoped>
.user {
  display: flex;
  align-items: center;
  padding-bottom: 20px;
  margin-bottom: 20px;
  border-bottom: 1px solid #ccc;

  img {
    margin-right: 40px;
    width: 150px;
    height: 150px;
    border-radius: 50%;
  }

  .userinfo {
    .name {
      font-size: 32px;
      margin-bottom: 10px;
    }

    .access {
      color: #999999;
    }
  }
}

.login-info {
  p {
    line-height: 28px;
    font-size: 14px;
    color: #999999;

    span {
      color: #666666;
      margin-left: 60px;
    }
  }
}

.num {

  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;

  .icon {
    width: 80px;
    font-size: 30px;
    text-align: center;
    line-height: 80px;
    color: #fff;
  }

  .detail {
    margin-left: 15px;
    display: flex;
    flex-direction: column;
    justify-content: center;

    .content {
      font-size: 20px;
      margin-bottom: 0;
      line-height: 10px;
      height: 30px;
    }

    .title {
      font-size: 13px;
      color: #999;
      text-align: center;
      margin-top: 0;
    }
  }

  .el-card {
    width: 32%;
    margin-bottom: 20px;
  }
}

.graph {
  margin-top: 20px;
  display: flex;
  justify-content: space-between;

  .el-card {
    width: 48%;
  }
}
</style>
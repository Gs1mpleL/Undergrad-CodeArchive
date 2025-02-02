<template>
  <el-table :cell-style="{ 'text-align': 'center' }" :data="tableData" :show-header="false"
            @sort-change="handleSortChange" @row-click="handle">
    <el-table-column label="微博热搜" prop="word"></el-table-column>
  </el-table>
</template>

<script>
import {weiboNews} from '@/api'

export default {
  data() {
    return {
      tableData: []
    };
  },
  methods: {
    fetchData() {
      weiboNews()
          .then(response => {
            this.tableData = response.data.data;
          })
          .catch(error => {
            console.error(error);
          });
    },
    handleSortChange({prop, order}) {
      this.tableData.sort((a, b) => {
        if (order === 'ascending') {
          return a[prop] > b[prop] ? 1 : -1;
        } else if (order === 'descending') {
          return a[prop] < b[prop] ? 1 : -1;
        } else {
          return 0;
        }
      });
    },
    handle(row, column, event) { // 添加点击事件处理方法。
      const url = row.url; // 获取当前行的url字段。
      event.preventDefault(); // 阻止默认的点击行为，例如阻止链接跳转。
      window.location.href = url; // 使用window.location对象来跳转到URL。
    }
  },
  mounted() {
    this.fetchData(); // 初始化时获取数据。
  }
};
</script>
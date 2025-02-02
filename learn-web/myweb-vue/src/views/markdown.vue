<template>
  <div>
    <el-row>
      <el-col :span="4">
        <el-table
            :data="tableData"
            border
            highlight-current-row style="margin-right: 0" @row-click="handleClick">
          <el-table-column
              label="已保存的markdown"
              prop="title"
              width="180">
          </el-table-column>
        </el-table>
      </el-col>
      <el-col :span="20">
        <mavon-editor v-model="value" style="min-height: 90vh" @change="updateDoc"
                      @save="saveDoc"/>
      </el-col>
    </el-row>


  </div>
</template>

<script>
import {delMarkDown, listMarkDown, updateMarkDown} from '@/api'

export default {
  data() {
    return {
      value: '',
      tableData: [],
      updateText: '',
      localUpdateText: '',
      updateRow: {}
    };
  },
  beforeRouteLeave(to, from, next) {
    if (this.updateText !== this.localUpdateText) {
      this.$alert('有未保存内容', '⚠️内容已编辑', {
        confirmButtonText: '好的！'
      });
    } else {
      next();
    }
  },
  mounted() {
    this.fetchData();
  },
  methods: {
    fetchData() {
      listMarkDown().then(data => {
        this.tableData = data.data.data
        let oneData = [{
          'title': "待添加"
        }]
        this.tableData = this.tableData.concat(oneData)
      })
    },
    updateDoc(markdown, html) {
      this.localUpdateText = markdown;
      console.log("内容更新：" + markdown)
    },
    saveDoc(markdown, html) {
      let json = {
        'text': markdown
      }
      if (this.updateRow.id) {
        json['id'] = this.updateRow.id
      }
      if (markdown === '') {
        if (this.updateRow.id) {
          delMarkDown(json).then(data => {
            this.localUpdateText = ''
            this.updateText = ''
            if (data.data.data) {
              this.$message.success(data.data.message)
            } else {
              this.$message.error(data.data.message)
            }
            this.updateRow = {}
            this.fetchData()
          })
        } else {
          this.$message.error("内容为空")
        }
      } else {
        json['title'] = getTitle(markdown)
        if (this.updateRow.id) {
          json['id'] = this.updateRow.id
        }
        updateMarkDown(json).then(data => {
          if (data.data.flag) {
            this.updateText = markdown;
            this.$message.success(data.data.message)

          } else {
            this.$message.error(data.data.message)
          }
          this.updateRow = data.data.data
          this.fetchData()
        })
        console.log(markdown)
      }
    },
    handleClick(row, column, event) {
      this.value = row.text
      this.updateRow = row;
      if (row.title === '待添加') {
        this.updateRow = {}
      }
    }
  }
};


function getTitle(str) {
  // 检查字符串是否以#开头
  if (str[0] === '#') {
    // 使用正则表达式匹配#???\n或#???
    const match = str.match(/#(.*)\n?/);
    if (match) {
      // 如果匹配成功，返回匹配到的部分
      return match[1];
    }
  }
  // 如果没有匹配到#开头的内容，返回'无标题'或剔除掉所有#后的内容
  return str.replace(/#.*\n?/, '');
}
</script>
<style>
.row-selected {
  background-color: yellow; /* 或者你想要的任何颜色 */
}
</style>
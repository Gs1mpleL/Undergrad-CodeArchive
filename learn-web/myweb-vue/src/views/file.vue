<template>
  <div>
    <el-row :gutter="20" class="top">
      <el-col :span="6">
        <el-select v-model="uploadDir" class="topDiv" placeholder="选择上传路径" @change="handleChooseDir">
          <el-option
              v-for="item in dirList"
              :key="item"
              :label="item"
              :value="item">
          </el-option>
        </el-select>
        <el-input v-model="uploadDir" class="topDiv" placeholder="自定义修改路径" @change="handleChooseDir"></el-input>

        <el-upload
            :action="uploadUrl"
            :on-success="getData"
            class="topDiv"
            drag
            multiple
            post>
          <i class="el-icon-upload"></i>
          <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
          <div slot="tip" class="el-upload__tip">文件将上传至阿里云OSS</div>
        </el-upload>
      </el-col>

      <el-col :span="10">
        <el-table :data="tableData"
                  :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
                  border
                  row-key="name"
                  style="width: 100%;margin-bottom: 20px;">
          <el-table-column label="文件名" prop="name"></el-table-column>
          <el-table-column label="地址" prop="url"></el-table-column>
          <el-table-column label="操作" prop="operation">
            <template slot-scope="scope">
              <el-button v-if="!scope.row.dir" type="success" @click="download(scope.row)">下载</el-button>
              <el-button v-if="!scope.row.dir" type="danger" @click="setCurrentRowAndOpenDia(scope.row)">删除
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-col>

    </el-row>


    <el-dialog
        :visible.sync="dialogVisible"
        title="提示"
        width="30%"
    >
      <span>是否确认删除文件{{ currentRow.name }}</span>
      <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="deleteMenu(currentRow)">确 定</el-button>
  </span>
    </el-dialog>
  </div>
</template>

<script>
import {deleteFile, getFiles, listFileDir} from '@/api'

function getDirectories(path) {
  var directories = [];
  var parts = path.split('/');

  for (var i = 0; i < parts.length; i++) {
    if (i === 0) {
      directories.push(parts[0]);
    } else if (i === parts.length - 1) {
      directories.push(parts.slice(0, i + 1).join('/'));
    } else {
      directories.push(parts.slice(0, i + 1).join('/'));
    }
  }

  return directories;
}

export default {
  data() {
    return {
      tableData: [],
      dialogVisible: false,
      currentRow: {},
      dirList: [],
      uploadUrl: '/api/myweb-user/file/upload/',
      uploadDir: ''
    };
  },
  mounted() {
    this.getData()
  },
  methods: {
    handleChooseDir() {
      if (!this.uploadDir.endsWith("/")) {
        this.uploadDir += "/";
      }
      this.uploadUrl = '/api/myweb-user/file/upload/?' + "path=" + this.uploadDir
    },
    getData() {
      this.dirList = []
      getFiles().then(data => {
        this.tableData = data.data.data
      })
      listFileDir().then(data => {
        let json = data.data.data
        for (let i = 0; i < json.length; i++) {
          let tmp = json[i].label
          let tmpArr = getDirectories(tmp)
          this.dirList = this.dirList.concat(tmpArr)
        }
        console.log(this.dirList)
        let newArr = this.dirList.map(item => item.endsWith('/') ? item : item + '/');
        this.dirList = newArr.filter((value, index, self) => {
          return self.indexOf(value) === index;
        });
        console.log(this.dirList)
      })
    },
    download(row) {
      window.location.href = row.url;
    },
    deleteMenu(row) {
      console.log(row)
      deleteFile(row.url).then(data => {
        if (data.data.flag) {
          this.$message.success(data.data.message)
        } else {
          this.$message.error(data.data.message)
        }
        this.dialogVisible = false;
        this.currentRow = {}
        this.getData()
      })
    },
    setCurrentRowAndOpenDia(row) {
      this.currentRow = row;
      this.dialogVisible = true;
    }
  }
}
</script>
<style>
.top {
  margin-bottom: 10px;
}

.topDiv {
  margin-top: 10px;
}
</style>
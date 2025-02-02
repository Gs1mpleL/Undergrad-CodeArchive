<template>
  <div>
    <el-row>
      <el-button class="topDiv" type="primary" @click="dialogVisible = true">添加菜单项</el-button>
    </el-row>
    <el-table :data="tableData"
              :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
              border
              row-key="id"
              style="width: 100%;margin-bottom: 20px;">
      <el-table-column label="标题" prop="label"></el-table-column>
      <el-table-column label="图标" prop="icon"></el-table-column>
      <el-table-column label="路由" prop="path"></el-table-column>
      <el-table-column label="操作" prop="operation">
        <template slot-scope="scope">
          <el-button type="primary" @click="editMenu(scope.row)">修改</el-button>
          <el-button type="danger" @click="deleteMenu(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog :visible.sync="dialogVisible" title="表单弹框" width="30%">
      <el-form ref="form" :model="form" label-width="80px">
        <el-form-item label="父菜单">
          <el-select v-model="form.parentId" placeholder="全选择父节点">
            <el-option v-for="item in tableData" :key="item.id" :label="item.label" :value="item.id"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="标题">
          <el-input v-model="form.label"></el-input>
        </el-form-item>
        <el-form-item label="路由">
          <el-input v-model="form.path"></el-input>
        </el-form-item>
        <el-form-item label="图标">
          <el-input v-model="form.icon"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="handleSubmit">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import {addMenu, delMenu, getMenu, updateMenu} from '@/api'

function findItemWithId(array, id) {
  for (let i = 0; i < array.length; i++) {
    const item = array[i];
    if (item.id === id) {
      return item;
    }
    if (Array.isArray(item.children) && item.children.length > 0) {
      const result = findItemWithId(item.children, id);
      if (result) {
        return result;
      }
    }
  }
  return null;
}

function removeItem(array, id) {
  // 遍历数组
  for (let i = 0; i < array.length; i++) {
    const item = array[i];

    // 检查当前项的id是否等于给定的id
    if (item.id === id) {
      // 如果当前项的id等于给定的id，则删除该项
      array.splice(i, 1);
      // 由于删除了一个元素，数组长度减小，索引会发生变化，因此需要调整索引
      i--;
    } else if (Array.isArray(item.children)) {
      // 如果当前项有children字段且是一个数组，则递归调用removeItem函数
      removeItem(item.children, id);
    }
  }

  return array;
}

export default {
  data() {
    return {
      dialogVisible: false, // 控制表单的显示与隐藏
      someId: null, // 这应该是你想编辑的记录的 ID
      tableData: [],
      form: {
        id: '',
        path: '',
        label: '',
        url: '',
        name: '',
        icon: '',
        parentId: ''
      }
    };
  },
  mounted() {
    this.fetchData();
  },
  methods: {
    fetchData() {
      // 从 Vuex store 中获取数据并赋值给 tableData
      getMenu().then((data) => {
        data = data.data.data
        this.tableData = data
      })
    },
    editMenu(row) {
      this.dialogVisible = true; // 显示表单
      this.form = findItemWithId(this.tableData, row.id)
    },
    handleSubmit() {
      if (this.form.id) {
        updateMenu(this.form).then(data => {
          if (data.data.data) {
            this.dialogVisible = false;
          }
          this.$message({
            message: data.data.message,
            type: data.data.data ? 'success' : 'error'
          });
        })
        // 更新后重置表单
        this.form = {
          id: '',
          path: '',
          label: '',
          url: '',
          name: '',
          icon: '',
          parentId: ''
        };
      } else {
        if (this.form.icon === '') {
          this.form.icon = 'eleme'
        }
        addMenu(this.form).then(data => {
          if (data.data.data) {
            this.dialogVisible = false;
          }
          this.$message({
            message: data.data.message,
            type: data.data.data ? 'success' : 'error'
          });
        })
      }
    },
    deleteMenu(id) {
      delMenu(findItemWithId(this.tableData, id)).then((data) => {
        this.$message({
          message: data.data.message,
          type: data.data.data ? 'success' : 'error'
        });
      })
      removeItem(this.tableData, id)
    }
  }
};
</script>
<style>
.topDiv {
  margin-top: 10px;
  margin-bottom: 4px;
}
</style>
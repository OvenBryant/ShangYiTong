<template>
  <div class="app-container">

    <el-button type="primary" icon="el-icon-upload2"
               size="mini" @click="exportDict">导出
    </el-button>

    <el-upload
      :action="'http://localhost:8202/admin/cmn/dict/uploadDict'"
      :show-file-list="false"
      accept="xlsx"
      :on-success="onUploadSuccess"
      style="display: inline-block"
    >
      <el-button
        type="warning"
        plain
        icon="el-icon-download"
        size="mini"
      >导入
      </el-button
      >
    </el-upload>


    <div class="app-table">
      <el-table
        :data="list"
        style="width: 100%"
        row-key="id"
        border
        lazy
        :load="getChildrens"
        :tree-props="{children: 'children', hasChildren: 'hasChildren'}">

        <el-table-column label="名称" width="230" align="left">
          <template slot-scope="scope">
            <span>{{ scope.row.name }}</span>
          </template>
        </el-table-column>
        <el-table-column label="编码" width="220">
          <template slot-scope="{row}">
            {{ row.dictCode }}
          </template>
        </el-table-column>
        <el-table-column label="值" width="230" align="left">
          <template slot-scope="scope">
            <span>{{ scope.row.value }}</span>
          </template>
        </el-table-column>

        <el-table-column label="创建时间" align="center">
          <template slot-scope="scope">
            <span>{{ scope.row.createTime }}</span>
          </template>
        </el-table-column>
      </el-table>
    </div>

  </div>

</template>

<script>
import dict from "@/api/dict";

export default {
  name: "list",
  data() {
    return {
      list: [] //数据字典列表数组
    }
  },
  created() {
    // 默认查询第一层、省、医院等级、学历、民族、证件类型
    this.getDictList(1)
  },
  methods: {
    // 导出
    exportDict() {
      window.open("http://localhost:8202/admin/cmn/dict/exportDict")
    },

    // 导入
    onUploadSuccess() {
      this.getDictList(1);
    },

    // 获取数据列表
    getDictList(id) {
      dict.dictList(id).then(res => {
        console.log(res)
        this.list = res.data
      })
    },

    getChildrens(tree, treeNode, resolve) {
      setTimeout(() => {
        dict.dictList(tree.id).then(response => {
          console.log('子列表：', response.data)
          resolve(response.data)
        })
      }, 300)

    }

  }
}
</script>

<style scoped>
.app-table {
  margin: 20px; /* 上下左右边距为20像素 */
  display: flex;
  justify-content: center; /* 水平居中 */
}
</style>

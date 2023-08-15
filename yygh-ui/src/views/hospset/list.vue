<template>
  <div class="app-container">
    医院设置列表
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item>
        <el-input v-model="searchObj.hosname" placeholder="医院名称"/>
      </el-form-item>
      <el-form-item>
        <el-input v-model="searchObj.hoscode" placeholder="医院编号"/>
      </el-form-item>
      <el-button type="primary" icon="el-icon-search" @click="getList()"
      >查询
      </el-button
      >
      <el-button type="warning" icon="el-icon-search" @click="resetSearch()"
      >重置
      </el-button
      >
    </el-form>

    <!-- 工具条 -->
    <div>
      <el-button type="danger" size="mini" @click="removeRows()"
      >批量删除
      </el-button>
    </div>

    <el-table
      :data="list"
      stripe
      style="width: 100%"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55"/>
      <el-table-column type="index" width="50" label="序号"/>
      <el-table-column prop="hosname" label="医院名称"/>
      <el-table-column prop="hoscode" label="医院编号"/>
      <el-table-column prop="apiUrl" label="api基础路径" width="200"/>
      <el-table-column prop="contactsName" label="联系人姓名"/>
      <el-table-column prop="contactsPhone" label="联系人手机"/>
      <el-table-column label="状态" width="80">
        <template slot-scope="scope">
          {{ scope.row.status === 1 ? "可用" : "不可用" }}
        </template>
      </el-table-column>

      <el-table-column label="操作" width="280" align="center">
        <template slot-scope="scope">
          <el-button
            type="danger"
            size="mini"
            icon="el-icon-delete"
            @click="removeDataById(scope.row.id)"
          >删除
          </el-button
          >
          <el-button
            v-if="scope.row.status == 1"
            type="primary"
            size="mini"
            icon="el-icon-delete"
            @click="lockHostSet(scope.row.id, 0)"
          >锁定
          </el-button
          >
          <el-button
            v-if="scope.row.status == 0"
            type="danger"
            size="mini"
            icon="el-icon-delete"
            @click="lockHostSet(scope.row.id, 1)"
          >取消锁定
          </el-button
          >

          <router-link :to="'/hospSet/edit/' + scope.row.id">
            <el-button type="primary" size="mini" icon="el-icon-edit"
            >编辑
            </el-button
            >
          </router-link>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <el-pagination
      :current-page="pageNum"
      :page-size="pageSize"
      :total="total"
      style="padding: 30px 0; text-align: center"
      layout="total, prev, pager, next, jumper"
      @current-change="getList"
    />
  </div>
</template>

<script>
import hospset from "@/api/hospset";

export default {
  name: "list",
  data() {
    return {
      pageNum: 1,
      pageSize: 5,
      searchObj: {},
      list: [], // 医院信息
      total: 0, // 总数
      multipleSelection: [],
    };
  },

  created() {
    this.getList();
  },
  methods: {
    // 获取医院信息
    getList(page = 1) {
      this.pageNum = page;
      hospset
        .getHospSetList(this.pageNum, this.pageSize, this.searchObj)
        .then((res) => {
          console.log(res);
          this.list = res.data.records;
          this.total = res.data.total;
        })
        .catch((error) => {
          console.log(error);
        });
    },
    removeRows() {
    },
    // 根据id,删除医院
    removeDataById(id) {

      this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {


        hospset.deleteById(id).then((res) => {
          if (res.code === 200) {
            this.getList()
            this.$message({
              type: 'success',
              message: '删除成功!'
            });
          } else {
            this.$message({
              type: 'error',
              message: '删除失败!'
            });
          }
        })


      })

    },
    // 锁定医院
    lockHostSet(id,status) {
      hospset.lockHospitalSet(id,status).then((res)=>{
        if(res.code===200){
          this.getList();
          this.$message.success("锁定成功")
        }else{
          this.$message.error("锁定失败")
        }
      })
    },

    resetSearch() {
      this.searchObj = {},
        this.getList();
    },
    //获取选择复选框的id值
    handleSelectionChange(selection) {
      this.multipleSelection = selection;
    },
  },
};
</script>

<style>
</style>

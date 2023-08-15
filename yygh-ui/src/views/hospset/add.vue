<template>
  <div class="app-container">
    医院设置添加
    <el-form label-width="120px">
      <el-form-item label="医院名称">
        <el-input v-model="hospitalSet.hosname"/>
      </el-form-item>

      <el-form-item label="医院编号">
        <el-input v-model="hospitalSet.hoscode"/>
      </el-form-item>

      <el-form-item label="api基础路径">
        <el-input v-model="hospitalSet.apiUrl"/>
      </el-form-item>

      <el-form-item label="联系人姓名">
        <el-input v-model="hospitalSet.contactsName"/>
      </el-form-item>
      <el-form-item label="联系人手机">
        <el-input v-model="hospitalSet.contactsPhone"/>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="saveOrUpdate">保存</el-button>
      </el-form-item>
    </el-form>

  </div>
</template>

<script>
import hospset from "@/api/hospset";

export default {
  name: 'add',
  data() {
    return {
      hospitalSet: {},
    }
  },
  mounted() {
    var row = this.$route.params.hospsetRow;
    if(row){
      this.hospitalSet = row;
    }
  },
  methods: {
    saveOrUpdate() {
      if(this.hospitalSet.id){ // 说明是编辑状态
        console.log('编辑状态: ',this.hospitalSet)
        hospset.updateByIdHospSet(this.hospitalSet).then(res=>{
          if(res.code===200){
            this.$message.success("修改成功")
            this.$router.push('/hospSet')
          }else{
            this.$message.success("修改失败")
          }
        })
      }else{ // 添加状态
        console.log('添加状态: ',this.hospitalSet)
        hospset.addHospset(this.hospitalSet).then(res => {
          if (res.code === 200) {
            this.$message.success("添加成功")
            this.$router.push('/hospSet')
          } else {
            this.$message.error("添加失败")
          }
        })
      }

    }
  }
}
</script>

<style>

</style>

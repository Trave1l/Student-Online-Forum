<template>
  <div class="table">
    <div class="bgImg" style="text-align: center">
      <el-card style="width: 96%; margin: 20px; text-align: center; background: rgba(0,0,0,0)">
    <el-form ref="form" :model="form" label-width="80px" class="form">
      <el-form-item label="Username">
        <el-input v-model="form.username" disabled></el-input>
      </el-form-item>
      <el-form-item label="Nickname">
        <el-input v-model="form.nickname"></el-input>
      </el-form-item>
      <el-form-item label="Age">
        <el-input v-model="form.age"></el-input>
      </el-form-item>
      <el-form-item label="Sex" style="text-align: left">
        <el-radio-group v-model="form.sex">
          <el-radio label="Male"></el-radio>
          <el-radio label="Female"></el-radio>
          <el-radio label="Unknown"></el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="Password">
        <el-input v-model="form.password" show-password></el-input>
      </el-form-item>
      <el-form-item label="Avatar">
        <el-upload
            action="/api/files/upload"
            ref="upload"
            :on-success="fileUploadSuccess">
          <img v-if="imageUrl" class="avatar" />
          <i v-else class="el-icon-plus avatar-uploader-icon"></i>
        </el-upload>
      </el-form-item>
      <el-form-item label="Emergency" style="text-align: left">
        <b style="color: orangered; font-family: 'Book Antiqua'"> {{form.emergency}} </b>
      </el-form-item>
    </el-form>
    <div style="text-align: center">
      <el-button type="primary" @click="update">保存</el-button>
    </div>
  </el-card>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "Person",
  data() {
    return {
      form: {}
    }
  },
  created() {
    let str = sessionStorage.getItem("user") || "{}"
    this.form = JSON.parse(str)
    console.log(this.form)
  },

  methods:{
    fileUploadSuccess(res){
      this.form.avatar = res.data
    },
    update() {
        request.put("/api/user", this.form).then(res => {
        if (res.code === '0') {
          this.$message({
            type: "success",
            message: "更新成功"
          })
          sessionStorage.setItem("user", JSON.stringify(this.form))
          // 触发Layout更新用户信息
          this.$emit("userInfo")
          this.$refs['upload'].clearFiles()//清除文件列表
          this.$router.push("/")
        } else {
          this.$message({
            type: "error",
            message: res.msg
          })
        }
      })
    }
  },
}
</script>

<style scoped>
.table /deep/ .el-table th {
  background-color: transparent!important;
}

.table{
  background: url("../assets/img_1.png") no-repeat;
  background-size: 100% 100%;
  color: black;
  font-weight: bolder;
}
.el-table el-table-column{
  color: black;
}

.table /deep/ .el-table tr {
  background-color: transparent!important;
}
.table /deep/  .el-table--enable-row-transition .el-table__body td, .el-table .cell{
  background-color: transparent;
}
.form{
  font-family: Bahnschrift;
}
</style>
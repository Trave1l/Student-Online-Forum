<template>
  <div style="padding-left: 10px" class="table">
    <!--    搜索-->
    <div style="margin: 10px 0">
      <el-input v-model="search" placeholder="Please Input" style="width: 20%" clearable></el-input>
      <el-button type="primary" style="margin-left: 5px" @click="load">Search</el-button>
    </div>
    <el-table
        :data="tableData"
        border
        class="table"
        style="width: 100%; background-color: transparent">
      <el-table-column label="Username" prop="username" sortable/>
      <el-table-column label="Nickname" prop="nickname" />
      <el-table-column label="Age" prop="age" sortable/>
      <el-table-column label="Sex" prop="sex" />
      <el-table-column
          label="Avatar">
        <template #default="scope">
          <el-image
              style="width: 100px; height: 100px"
              :src="scope.row.avatar"
              :preview-src-list="[scope.row.avatar]">
          </el-image>
        </template>
      </el-table-column>
      <el-table-column
          label="role">
        <template #default="scope">
          <span v-if="scope.row.role === 1">Admin</span>
          <span v-if="scope.row.role === 2">Ordinary</span>
        </template>
      </el-table-column>
      <el-table-column
          label="State">
        <template #default="scope">
          <span v-if="scope.row.state === 1" style="color:red">Online</span>
          <span v-if="scope.row.state === 0" style="color:grey">Offline</span>
        </template>
      </el-table-column>
      <el-table-column label="Operations">
        <template #default="scope">
          <el-button size="mini" type="text"  @click="handleEdit(scope.row)"
                     v-if="scope.row.state === 0">
            Edit
          </el-button>
          <el-popconfirm type="danger"  title="Are you sure to delete this?"
                         v-if="scope.row.state === 0"
                         @confirm="handleDelete(scope.row.id)" >
            <template #reference>
              <el-button size="mini" type="text">Delete</el-button>
            </template>
          </el-popconfirm>
          <el-button size="mini" type="text"  @click="showMsg(scope.row)" style="color: red" v-if="scope.row.report == 1">
            Violate
          </el-button>
          <el-button size="mini" type="text"  @click="showMsg(scope.row)" style="color: darkblue" v-if="scope.row.report == 2">
            Fraze
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <!--    分页-->
    <div class="demo-pagination-block" style="margin: 10px 0; text-align: center">
      <el-pagination
          v-model:currentPage="currentPage1"
          :page-sizes="[5, 10, 20]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
      >
      </el-pagination>
    </div>
    <div style="margin: 10px 0; text-align: center">
      <el-dialog v-model="dialogVisible" title="Edit" width="50%">
        <el-form :model="form" label-width="120px">
          <el-form-item label="Username">
            <el-input v-model="form.username" style="width: 80%"></el-input>
          </el-form-item>
          <el-form-item label="Nickname">
            <el-input v-model="form.nickname" style="width: 80%"></el-input>
          </el-form-item>
          <el-form-item label="Age">
            <el-input v-model="form.age" style="width: 80%"></el-input>
          </el-form-item>
          <el-form-item label="Sex">
            <el-radio v-model="form.sex" label="Male" >Male</el-radio>
            <el-radio v-model="form.sex" label="Female" >Female</el-radio>
            <el-radio v-model="form.sex" label="Unknown" >Unknown</el-radio>
          </el-form-item>
          <el-form-item label="Role">
            <el-radio v-model="form.role" label="2" >Ordinary</el-radio>
            <el-radio v-model="form.role" label="1" >Admin</el-radio>
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
          <el-form-item style="color: blanchedalmond" prop="report" label="State">
            <el-radio-group v-model="form.report">
              <el-radio  label="0">Normal</el-radio>
              <el-radio  label="1">Violation</el-radio>
              <el-radio  label="2">Fraze</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-form>
        <template #footer style="margin-left: 40%">
          <span  >
            <el-button  @click="dialogVisible = false">Cancel</el-button>
            <el-button style="margin-left: 10px;" type="primary" @click="save">Confirm</el-button>
          </span>
        </template>
      </el-dialog>
    </div>

    <el-dialog v-model="vis" title="Violation" width="40%">
      <el-card>
        <div v-html="detail.content" style="min-height: 80px"></div>
      </el-card>
    </el-dialog>
  </div>
</template>

<script>

import request from "@/utils/request";
import {ElNotification} from "element-plus";

export default {
  name: 'Home',
  data() {
    return {
      form:{},
      dialogVisible: false,
      search: '',
      currentPage1: 1,
      pageSize: 10,
      total: 0,
      tableData: [],
      dialogFormVisible1:false,
      entity1:{},
      vis:false,
      detail:{}
    }
  },
  components: {

  },
  created() {
    this.load();
    this.DDDD()
  },
  destroyed(){
    // 在页面销毁后，清除计时器
    let user = sessionStorage.getItem("user")
    this.form = JSON.parse(user)
    request.put("/api/user/quit", this.form)
  },
  methods:{
    DDDD(){
      setTimeout(function(){
        this.load()    //刷新页面
      },10000);   //这就是 一分钟
    },
    fileUploadSuccess(res){
      this.form.avatar = res.data
    },
    load(){
      request.get("/api/user/", {params:{
          pageNum:this.currentPage1,
          pageSize:this.pageSize,
          search:this.search
        }
      }).then(res =>{
        this.tableData = res.data.records
        this.total = res.data.total
      })
    },
    add(){
      this.dialogVisible = true
      //清空表单，空对象
      this.form = { }
      this.$nextTick(() =>{
        if(this.$refs['upload']){
          this.$refs['upload'].clearFiles()//清除文件列表
        }
      })//处理未来元素
    },
    save(){
      if(this.form.id){//更新
        this.dialogVisible = false
        request.put("/api/user/", this.form).then(res =>{
          console.log(res)
          if(res.code == '0'){
            this.$message({
              type:"success",
              message:"更新成功"
            })
          }else{
            this.$message({
              type:"error",
              message:res.msg
            })
          }
          this.load()//刷新数据
          this.$refs['upload'].clearFiles()//清除文件列表
          this.dialogVisible = false
        })
      }//更新
      else{
        this.dialogVisible = false
        request.post("/api/user/", this.form).then(res =>{
          console.log(res)
          if(res.code == '0'){
            this.$message({
              type:"success",
              message:"新增成功"
            })
          }else{
            this.$message({
              type:"error",
              message:res.msg
            })
          }
          this.load()//刷新数据
          this.dialogVisible = false
        })
      }//新增
    },
    handleEdit(row){
      this.form = JSON.parse((JSON.stringify(row)))
      this.dialogVisible = true
    },
    handleDelete(id){
      console.log(id)
      request.delete("/api/user/" + id).then(res =>{
        if(res.code == '0'){
          this.$message({
            type:"success",
            message:"删除成功"
          })
        }else{
          this.$message({
            type:"success",
            message:res.msg
          })
        }
      })
      this.load()//刷新数据
    },
    handleSizeChange(pageSize){//改变当前个数
      this.pageSize = pageSize
      this.load()
    },
    handleCurrentChange(pageNum){//改变当前页码
      this.currentPage1 = pageNum
      this.load()
    },
    showMsg(row){
      this.vis = true
      console.log(row)
    }
  }
}
</script>
<style>
.table /deep/ .el-table th {
  background-color: transparent!important;
}

.table{
  background: url("../assets/img_3.png") no-repeat;
  background-size: 100% 100%;
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
.dialog-footer{
  text-align: center;
}
</style>
<template>
  <div style="padding-left: 10px; width: 100%" class="table">
    <!--    功能-->
    <!--    搜索-->
    <div style="margin: 10px 0">
      <el-input v-model="search" placeholder="Please Input" style="width: 20%" clearable></el-input>
      <el-button type="primary" style="margin-left: 5px" @click="load">Search</el-button>
    </div>
    <el-table
        v-loading="loading"
        :data="tableData"
        border
        style="width: 100%; background-color: transparent">
      <el-table-column label="Title" prop="title" sortable/>
      <el-table-column label="Author" prop="author" />
      <el-table-column label="Time" prop="time" sortable/>
      <el-table-column label="Section" prop="section" sortable/>
      <el-table-column label="Read" prop="follow" sortable/>
      <el-table-column label="Operations">
        <template #default="scope">
          <el-button size="mini" type="text" @click="details(scope.row)">
            Detail
          </el-button>
          <el-popconfirm type="danger" title="Are you sure to delete this?" @confirm="handleDelete(scope.row.id)" v-if="user.role == 1">
            <template #reference>
              <el-button size="mini" type="text">Delete</el-button>
            </template>
          </el-popconfirm>
          <el-button size="mini" type="text"  @click="handleEdit(scope.row)" >
            State
          </el-button>
          <el-button size="mini" type="text" @click="Star(scope.row)">
            Hot
          </el-button>
          <el-button size="medium" icon="el-icon-lollipop" style="color: orangered" type="text" v-if="scope.row.star == 1">
          </el-button>
          <el-button size="mini" type="text"  @click="" style="color: red" v-if="scope.row.report == 1">
            违规
          </el-button>
          <el-button size="mini" type="text"  @click="" style="color: darkblue" v-if="scope.row.report == 2">
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
      <el-dialog v-model="dialogVisibleState" title="修改" width="30%">
        <el-form :model="form" label-width="120px">
          <el-form-item style="color: blanchedalmond" prop="report" label="状态">
            <el-radio-group v-model="form.report">
              <el-radio  label="0">正常</el-radio>
              <el-radio  label="1">违规</el-radio>
              <el-radio  label="2">冻结</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item style="color: blanchedalmond" prop="report" label="状态">
            <el-radio-group v-model="form.section">
              <el-radio  label="学习">学习</el-radio>
              <el-radio  label="美食">美食</el-radio>
              <el-radio  label="购物">购物</el-radio>
              <el-radio  label="旅游">旅游</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-form>
        <template #footer>
          <span class="dialog-footer">
            <el-button @click="dialogVisible = false">Cancel</el-button>
            <el-button type="primary" @click="save">Confirm</el-button>
          </span>
        </template>
      </el-dialog>
    </div>
    <el-dialog v-model="vis" title="通告" width="50%">
      <el-card>
        <div v-html="detail.content" style="min-height: 200px"></div>
      </el-card>
    </el-dialog>
  </div>
  <el-backtop :bottom="100">
    🚀
  </el-backtop>
</template>

<script>
import request from "@/utils/request";
import E from "wangeditor";

let editor

export default {
  name: "Bbs_Admin",
  data() {
    return {
      form:{
      },
      loading: true,
      dialogVisible: false,
      search: '',
      currentPage1: 1,
      pageSize: 10,
      total: 0,
      tableData: [],
      detail:{},
      vis:false,
      user:{},
      dialogVisibleState:false,
      formState:{},
    }
  },
  components: {},
  created() {
    let userStr = sessionStorage.getItem("user") || "{}"
    this.user = JSON.parse(userStr)
    // // 请求服务端，确认当前登录用户的 合法信息
    request.get("/api/user/" + this.user.id).then(res => {
      if (res.code === '0') {
        this.user = res.data
      }
    })
    this.load();
  },
  methods:{
    details(row) {
      this.vis = true,
          this.detail = row
    },
    Star(row) {
      request.put("/api/bbs/star", row).then(res => {
        if (res.code == '0') {
          this.$message({
            type: "success",
            message: "成功"
          })
          this.load()
        } else {
          this.$message({
            type: "error",
            message: res.msg
          })
        }
      })
    },
    fileUploadSuccess(res){
      this.form.avatar = res.data
    },
    load() {
      this.loading = true
      request.get("/api/bbs/page", {
        params: {
          pageNum: this.currentPage,
          pageSize: this.pageSize,
          search: this.search
        }
      }).then(res => {
        this.loading = false
        this.tableData = res.data.records.reverse()
        this.total = res.data.total
      })
    },
    save(){
      console.log(this.form)
      if(this.form.id){//更新
        this.dialogVisible = false
        request.put("/api/bbs/", this.form).then(res =>{
          if(res.code == '0'){
            this.$message({
              type:"success",
              message:"更新成功"
            })
            this.dialogVisibleState= false;
            this.load()
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
        let userStr = sessionStorage.getItem("user") || "{}"
        let user = JSON.parse(userStr)

        this.form.author = user.nickname
        request.post("/api/bbs/", this.form).then(res =>{
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
      this.dialogVisibleState = true
    },
    handleDelete(id){
      console.log(id)
      request.delete("/api/bbs/" + id).then(res =>{
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
  }
}
</script>

<style scoped>
.table /deep/ .el-table th {
  background-color: transparent!important;
}

.table{
  background: url("../assets/img_4.png") no-repeat;
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
</style>
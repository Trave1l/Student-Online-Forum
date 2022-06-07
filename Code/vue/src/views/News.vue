<template>
  <div style="padding-left: 10px" class="table">
    <!--    功能-->
    <div style="margin: 10px 0">
      <el-button type="primary" @click="add" v-if="user.role == 1">Add</el-button>
    </div>
    <!--    搜索-->
    <div style="margin: 10px 0">
      <el-input v-model="search" placeholder="Please input" style="width: 20%" clearable></el-input>
      <el-button type="primary" style="margin-left: 5px" @click="load">Search</el-button>
    </div>
    <el-table
        v-loading="loading"
        :data="tableData"
        border
        class="table"
        style="width: 100%; background-color: transparent; color: black">
      <el-table-column label="标题" prop="title" sortable/>
      <el-table-column label="作者" prop="author" />
      <el-table-column label="时间" prop="time" sortable/>
      <el-table-column label="Operations">
        <template #default="scope">
          <el-button size="mini" type="text" @click="details(scope.row)">
            详情
          </el-button>
          <el-button size="mini" type="text" @click="handleEdit(scope.row)" v-if="user.role == 1">
            编辑
          </el-button>
          <el-popconfirm type="danger" title="Are you sure to delete this?" @confirm="handleDelete(scope.row.id)" v-if="user.role == 1">
            <template #reference>
              <el-button size="mini" type="text">删除</el-button>
            </template>
          </el-popconfirm>
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
    <el-dialog v-model="dialogVisible" title="新增" width="50%">
      <el-form :model="form" label-width="120px">
        <el-form-item label="标题">
          <el-input v-model="form.title" style="width: 50%"></el-input>
        </el-form-item>
        <div id="div1">
        </div>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">Cancel</el-button>
          <el-button type="primary" @click="save">Confirm</el-button>
        </span>
      </template>
    </el-dialog>

    <el-dialog v-model="vis" title="详情" width="50%">
      <el-card>
        <div v-html="detail.content" style="min-height: 200px"></div>
      </el-card>
    </el-dialog>
  </div>
</template>

<script>

import E from 'wangeditor'
import request from "@/utils/request";
import {ElNotification} from "element-plus";

let editor;

export default {
  name: 'News',
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
    fileUploadSuccess(res){
      this.form.avatar = res.data
    },
    load() {
      this.loading = true
      request.get("/api/news/page", {
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
    add() {
      this.dialogVisible = true
      this.$nextTick(() => {
        // 关联弹窗里面的div，new一个 editor对象
        if (!editor) {
          editor = new E('#div1')

          editor.config.uploadImgServer = "http://localhost:9092/files/editor/upload"
          editor.config.uploadFileName = 'file'

          editor.create()
        }
        editor.txt.html("")
      })
      this.load()
    },
    save(){
      this.form.content = editor.txt.html()//获取编辑器的内容，复制 --> 转存

      if(this.form.id){//更新
        this.dialogVisible = false
        request.put("/api/news/", this.form).then(res =>{
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
        let userStr = sessionStorage.getItem("user") || "{}"
        let user = JSON.parse(userStr)

        this.form.author = user.nickname
        request.post("/api/news/", this.form).then(res =>{
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
      this.load()
    },
    handleEdit(row){
      this.form = JSON.parse((JSON.stringify(row)))
      this.dialogVisible = true
      this.$nextTick(() => {
        // 关联弹窗里面的div，new一个 editor对象
        if (!editor) {
          editor = new E('#div1')

          editor.config.uploadImgServer = "http://localhost:9092/files/editor/upload"
          editor.config.uploadFileName = 'file'
          // 配置 server 接口地址
          editor.create()
        }
        editor.txt.html(row.content)
      })
      this.load()
    },
    handleDelete(id){
      console.log(id)
      request.delete("/api/news/" + id).then(res =>{
        if(res.code == '0'){
          this.$message({
            type:"success",
            message:"删除成功"
          })
          this.load()
        }else{
          this.$message({
            type:"success",
            message:res.msg
          })
        }
      })
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

<style>
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

<template>
  <div style="padding-left: 10px; "  class="table">
    <div style="margin: 10px 0">
      <el-button type="warning" @click="add" style="margin-left: 5px" icon="el-icon-circle-plus" plain></el-button>
    <!--    搜索-->
      <el-input v-model="search" placeholder="Please Input" style="width: 20%; margin-left: 10px" clearable></el-input>
      <el-button type="primary" style="margin-left: 5px" @click="load" icon="el-icon-search" circle></el-button>
    </div>

    <div style="margin-top:20px;">
    <el-table v-loading="loading"
        :data="tableData"
        border
              style="width: 100%;background-color: transparent; color: black">
      <el-table-column style="color: black" label="Title" prop="title" sortable/>
      <el-table-column label="Author" prop="author" />
      <el-table-column label="Time" prop="time" sortable/>
      <el-table-column label="Read" prop="follow" sortable/>
      <el-table-column label="Good" prop="good" sortable/>
      <el-table-column label="Bad" prop="bad" sortable/>
      <el-table-column label="Operations">
        <template #default="scope">
          <el-button size="mini" type="text" @click="Jump(scope.row)">
            More
          </el-button>
          <el-button size="mini" type="text" @click="Star(scope.row)"  v-if="this.user.role == 1">
            Star
          </el-button>
          <el-button size="medium" icon="el-icon-lollipop" style="color: orangered" type="text" v-if="scope.row.star == 1">
          </el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>

    <el-dialog v-model="dialogVisible" title="Add" width="50%">
      <el-form :model="form" label-width="120px">
        <el-form-item label="Title">
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

    <!--    分页-->
    <div class="demo-pagination-block" style="margin: 10px 0; text-align: center">
      <el-pagination
          v-model:currentPage="currentPage1"
          :page-sizes="[5, 10, 20]"
          :page-size="pageSize"
          layout="sizes, prev, pager, next, jumper"
          :total="total"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
      >
      </el-pagination>
    </div>

  </div>
</template>

<script>

import E from "wangeditor";
import request from "@/utils/request";

let editor;

export default {
  name: "Bbs",
  data() {
    return {
      form:{},
      dialogVisible: false,
      search: '',
      tableData: [],
      loading: true,
      notices: [],
      currentPage1: 1,
      pageSize: 10,
      total: 0,
      active: 0,
      vis:false,
      tmp:{},
    }
  },
  created() {
    sessionStorage.removeItem("BBS")
    sessionStorage.removeItem("BbsId")
    this.user = sessionStorage.getItem("user") ? JSON.parse(sessionStorage.getItem("user")) : {};
    this.load()
  },
  methods: {
    Jump(row){
      sessionStorage.setItem("BBS", JSON.stringify(row))
      sessionStorage.setItem("BbsId", row.id)
      this.$router.push('/details')
      this.user = sessionStorage.getItem("user") ? JSON.parse(sessionStorage.getItem("user")) : {};
      request.put("/api/bbs/", row)
      request.put("/api/message/", row)
      request.put("/api/store/", this.tmp)
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
        this.total = res.data.total
      })
      let tmp = "Food"
      request.get("/api/bbs/section/" + tmp).then(res =>{
        this.loading = false
        this.tableData = res.data
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
    },
    save(){
      this.form.content = editor.txt.html()//获取编辑器的内容，复制 --> 转存

      let userStr = sessionStorage.getItem("user") || "{}"
      let user = JSON.parse(userStr)

      this.form.author = user.nickname
      this.form.aid = user.id
      this.form.section = "Food"
      request.post("/api/bbs/", this.form).then(res =>{
        if(res.code == '0'){
          this.$message({
            type:"success",
            message:"新增成功"
          })
          this.load()//刷新数据
        }else{
          this.$message({
            type:"error",
            message:res.msg
          })
        }
        this.dialogVisible = false
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
    Star(row) {
      request.put("/api/bbs/star", row).then(res => {
        if (res.code == '0') {
          this.$message({
            type: "success",
            message: "加精成功"
          })
          this.load()
        } else {
          this.$message({
            type: "error",
            message: res.msg
          })
        }
      })
    }
  }
}
</script>

<style scoped>
.table /deep/ .el-table th {
  background-color: transparent!important;
}

.table{
  background: url("../assets/back_1.png") no-repeat;
  background-size: 100% 100%;
}

.table /deep/ .el-table tr {
  background-color: transparent!important;
}
.table /deep/  .el-table--enable-row-transition .el-table__body td, .el-table .cell{
  background-color: transparent;
}
</style>
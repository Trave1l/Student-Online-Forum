<template>
  <div class="table">
  <div class="bgImg" style="text-align: center">
  <el-card style="width: 96%; margin: 20px; text-align: center; background: rgba(0,0,0,0)">
    <el-form ref="form" :model="form" label-width="80px">
      <el-image :src="form.avatar" style="width: 100px; height: 100px; border-radius: 60%;"></el-image>
      <div style="text-align: center; font-size: x-large;font-weight: bolder;font-family: fantasy">{{form.nickname}}</div>
    </el-form>
    <div style="text-align: center; font-size: large; font-family: 'Book Antiqua'">
      <span v-if="form.role === 1" style="color: #f24b17">Admin</span>
      <span v-if="form.role === 2">Ordinary</span>
    </div><div>&nbsp;</div><div>&nbsp;</div>
    <div style="font-family: fantasy">
      <el-button @click="bbs" type="text" style="font-size: large; margin-left: 20px">BBS Records</el-button>
      <el-button @click="$router.push('/IndexComment')" type="text" style="font-size: large; margin-left: 20px">Comment Records</el-button>
      <el-button @click="$router.push('/IndexStore')" type="text" style="font-size: large; margin-left: 20px">Store</el-button>
    </div>
    <br />
    <el-table :data="tableData"
              border
              style="width: 100%; background-color: rgba(0,0,0,0); color: black"
              v-model="TableVisible"
              class="table">
      <el-table-column label="Title" prop="title" sortable/>
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
          <el-button size="mini" type="text" @click="handleEdit(scope.row)">
            Edit
          </el-button>
          <el-popconfirm type="danger" title="Are you sure to delete this?" @confirm="handleDelete(scope.row.id)">
            <template #reference>
              <el-button size="mini" type="text">Delete</el-button>
            </template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog v-model="dialogVisible" title="新增" width="50%">
      <el-form :model="dlog" label-width="120px">
        <el-form-item label="标题">
          <el-input v-model="dlog.title" style="width: 50%"></el-input>
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

  </el-card>
  </div>
  </div>
</template>

<script>
import request from "@/utils/request";
import E from "wangeditor";

let editor;

export default {
  name: "Index",
  data(){
    return {
      form:{},
      tableData:[],
      TableVisible:false,
      user:{},
      dialogVisible:false,
      dlog:{},
    }
  },
  created() {
     sessionStorage.removeItem("BBS")
     sessionStorage.removeItem("BbsId")
    let str = sessionStorage.getItem("user") || "{}"
    this.form = JSON.parse(str)
    this.load()
  },
  methods:{
    load(){
      this.bbs()
    },
    bbs(){
      let str = sessionStorage.getItem("user") || "{}"
      let tmp = JSON.parse(str)
      request.get("/api/bbs/ubbs/" + tmp.id).then(res=>{
        this.tableData = res.data
      })
    },
    Jump(row){
      sessionStorage.setItem("BBS", JSON.stringify(row))
      sessionStorage.setItem("BbsId", row.id)
      this.$router.push('/details')
      request.put("/api/bbs/", row)
    },
    handleDelete(id){
      request.delete("/api/bbs/" + id).then(res =>{
        if(res.code == '0'){
          this.$message({
            type:"success",
            message:"删除成功"
          })
          this.load()//刷新数据
        }else{
          this.$message({
            type:"success",
            message:res.msg
          })
        }
      })
    },
    handleEdit(row){
      this.dlog = JSON.parse((JSON.stringify(row)))
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
    save(){
      this.dlog.content = editor.txt.html()//获取编辑器的内容，复制 --> 转存

      if(this.dlog.id){//更新
        this.dialogVisible = false
        request.put("/api/bbs/", this.dlog).then(res =>{
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
    }
  }
}
</script>

<style scoped>
.bgImg{
  background: url("../assets/img_4.png") no-repeat;
  background-size: 100% 100%;
  background-opacity: 0.5;
}
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
</style>
<template>
  <div class="table">
    <div class="bgImg" style="text-align: center">
      <el-card style="width: 96%; margin: 20px; text-align: center; background: rgba(0,0,0,0)">
    <el-form ref="form" :model="form" label-width="80px">
      <el-image :src="form.avatar" style="width: 100px; height: 100px; border-radius: 60%; "></el-image>
      <div style="text-align: center; font-size: x-large;font-weight: bolder; font-family: fantasy">{{form.nickname}}</div>
    </el-form>
    <div style="text-align: center; font-size: large; font-family: 'Book Antiqua'">
      <span v-if="form.role === 1" style="color: #f24b17">Admin</span>
      <span v-if="form.role === 2">Ordinary</span>
    </div><div>&nbsp;</div><div>&nbsp;</div>
    <div>
      <el-button @click="$router.push('/index')" type="text" style="font-size: large; margin-left: 20px">BBS Records</el-button>
      <el-button @click="cmt" type="text" style="font-size: large; margin-left: 20px">Comment Records</el-button>
      <el-button @click="$router.push('/IndexStore')" type="text" style="font-size: large; margin-left: 20px">Store</el-button>
    </div>
    <br />
    <el-table :data="tableData"
              border
              style="width: 100%; background-color: rgba(0,0,0,0); color: black"
              v-model="TableVisible"
              class="table">
      <el-table-column label="Title" prop="title" sortable/>
      <el-table-column label="Time" prop="time" sortable/>
      <el-table-column label="Comment" prop="messageContent" sortable/>
      <el-table-column label="Operations">
        <template #default="scope">
          <el-button size="mini" type="text" @click="Jump(scope.row)">
            More
          </el-button>
          <el-popconfirm type="danger" title="Are you sure to delete this?" @confirm="handleDelete(scope.row.id)">
            <template #reference>
              <el-button size="mini" type="text">Delete</el-button>
            </template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
  </el-card>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "Index",
  data(){
    return {
      form:{},
      tableData:[],
      TableVisible:false,
      tmp:{},
    }
  },
  created() {
    let str = sessionStorage.getItem("user") || "{}"
    this.form = JSON.parse(str)
    this.cmt()
  },
  methods:{
    cmt(){
      let str = sessionStorage.getItem("user") || "{}"
      let tmp = JSON.parse(str)

      request.get("/api/message/ucmt/" + tmp.id).then(res=>{
        this.tableData = res.data
      })
    },
    Jump(row){
      row.good = 0;
      row.bad = 0;
      sessionStorage.setItem("BBS", JSON.stringify(row))
      sessionStorage.setItem("BbsId", row.foreignId)
      this.$router.push('/details')
      request.put("/api/bbs/", row)
    },
    handleDelete(id){
      request.delete("/api/message/" + id).then(res =>{
        if(res.code == '0'){
          this.$message({
            type:"success",
            message:"删除成功"
          })
          this.cmt()//刷新数据
        }else{
          this.$message({
            type:"success",
            message:res.msg
          })
        }
      })
    },
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
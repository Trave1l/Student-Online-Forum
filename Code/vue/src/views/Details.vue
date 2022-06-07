<template>
  <el-card title="details" width="50%"  class="bgImg">
    <div style="margin-button: 20px">
      <el-button  class="buttonLeft" @click="$router.push('/bbs')" type="success" icon="el-icon-back" round></el-button>
      <el-button  class="buttonLeft"  @click="comment" type="primary" icon="el-icon-circle-plus" style="margin-left: 10px" round>Comment</el-button>
      <el-button  style="margin-left: 70%;" type="primary" icon="el-icon-check" circle slot="reference"  @click="GoodBBS"></el-button>
      <el-button  class="buttonRight" type="danger" icon="el-icon-close" circle slot="reference"  @click="BadBBS"></el-button>
      <el-button  class="buttonRight" type="warning" icon="el-icon-lollipop" circle slot="reference" @click="StoreBbs"></el-button>
      <el-button  class="buttonRight" type="danger" icon="el-icon-phone" circle slot="reference"  @click="report_1"></el-button>
    </div>
    <el-card style="background: rgba(0,0,0,0);">
      <template #header>
        <div class="card-header">
          <span style="font-weight: bold; font-size: x-large; text-align: center;">{{detail.title}}</span>
          <a style="margin-left: 81%; font-weight: lighter;font-size: smaller; color: dodgerblue">{{detail.section}}</a>
        </div>
      </template>
      <div v-html="detail.content" style="min-height: 200px"></div>
      <hr />
      <div style="font-weight: bold; text-align: center; font-size: x-large">
      <el-label-wrap >Comment：</el-label-wrap>
      </div>
      <div style="display: flex; padding: 20px" v-for="item in messages">
        <div style="padding: 0 10px; flex: 5;">
          <div >
              <el-image :src="item.avatar" style="width: 70px; height: 70px; border-radius: 50%" @click="checkInfo(item)"></el-image>
              <b style="font-size: 14px; margin-left: 5px" >{{ item.username }}</b></div>
          <div style="padding: 10px 0; color: black">
            {{ item.messageContent }}
            <!--              <el-button type="text" size="mini" @click="del(item.id)" v-if="item.username === user.username">删除</el-button>-->
          </div>
          <div style="background: rgba(0,0,0,0.2); padding: 10px" v-if="item.parentMessage">{{ item.parentAuthor }}：{{ item.parentMessage }}</div>
          <div style="color: #888; font-size: 12px">
              <span>{{ item.time  }}</span>
              <el-button type="text" style="margin-left: 20px"
                         @click="reReply(item.id, item.messageContent, item.foreignId, item.username)">Reply</el-button>
            <el-popconfirm type="danger" v-if="detail.aid == user.id || item.aid == user.id || user.role == 1"
                           title="Are you sure to delete this?" @confirm="handleDelete(item.id)">
              <template #reference>
                <el-button size="mini" type="text">Delete</el-button>
              </template>
            </el-popconfirm>
            <el-button size="mini" type="text" style="color:orangered;" @click="RePort(item)">Report</el-button>
          </div>
        </div>
      </div>
    </el-card>

    <el-dialog v-model="dialogFormVisible" title="Comment" width="50%" style="text-align: center;">
      <el-form :model="entity">
        <el-form-item label="Content" label-width="100px">
          <el-input type="textarea" :rows="2" v-model="entity.messageContent"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer" style="text-align: center;">
        <el-button @click="cancel">Cancel</el-button>
        <el-button type="primary" @click="reply">Confirm</el-button>
      </div>
    </el-dialog>

    <el-dialog v-model="div" title="Report" width="40%" style="text-align: center;">
      <el-form :model="report">
        <el-form-item label="Content" label-width="100px">
          <el-input type="textarea" :rows="2" v-model="report.Content"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer" style="text-align: center;">
        <el-button @click="cancel">Cancel</el-button>
        <el-button type="primary" @click="port(report.Content)">Confirm</el-button>
      </div>
    </el-dialog>

    <el-dialog v-model="divT" title="Report" width="40%" style="text-align: center;">
      <el-form :model="reportT">
        <el-form-item label="Content" label-width="100px">
          <el-input type="textarea" :rows="2" v-model="reportT.Content"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer" style="text-align: center;">
        <el-button @click="cancel">Cancel</el-button>
        <el-button type="primary" @click="portT(reportT.Content)">Confirm</el-button>
      </div>
    </el-dialog>

    <el-dialog v-model="divU" title="Report" width="40%" style="text-align: center;">
      <el-form :model="reportU">
        <el-form-item label="Content" label-width="100px">
          <el-input type="textarea" :rows="2" v-model="reportU.Content"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer" style="text-align: center;">
        <el-button @click="cancel">Cancel</el-button>
        <el-button type="primary" @click="portU(reportU)">Confirm</el-button>
      </div>
    </el-dialog>


    <el-dialog v-model="dialogFormVisible1" title="Reply" width="40%" style="text-align: center;">
      <el-form :model="entity1">
        <el-form-item label="Content" label-width="100px">
          <el-input type="textarea" :rows="2" v-model="entity1.messageContent"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer" style="text-align: center;">
        <el-button @click="cancel">Cancel</el-button>
        <el-button type="primary" @click="cReply">Confirm</el-button>
      </div>
    </el-dialog>

    <el-dialog v-model="dialogFormVisible2" title="Detail" width="40%" style="text-align: center;">
      <el-form :model="entity2">
        <el-form-item style="text-align: center">
          <el-image :src="entity2.avatar" style="width: 100px; height: 100px; border-radius: 60%; text-align: center"></el-image>
          <div style="text-align: center"> {{entity2.username}}</div>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer" style="text-align: center">
        <el-button  type="text" style="color:#f24b17" @click="report_U(entity2)">Report</el-button>
      </div>
    </el-dialog>
    <el-backtop :bottom="100">
      🚀
    </el-backtop>
  </el-card>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "Details",
  data() {
    return {
      detail:{},
      messages:[],
      dialogFormVisible1:false,
      entity1:{},
      dialogFormVisible2:false,
      entity2:{},
      entity:{},
      dialogFormVisible:false,
      user:{},
      store:{},
      good:{},
      div:false,
      report:{},
      tmp:{},
      divT:false,
      reportT:{},
      tmpT:{},
      divU:false,
      reportU:{},
      tmpU:{},
      reU:{},
      cnt:{},
      cnt1:{},
    }
  },
  created() {
    let bbs = sessionStorage.getItem("BBS")
    this.detail = JSON.parse(bbs)
    let userStr = sessionStorage.getItem("user") || "{}"
    this.user = JSON.parse(userStr)
    request.get("/api/user/" + this.user.id).then(res => {
      if (res.code === '0') {
        this.user = res.data
      }
    })
    this.load()
  },
  methods:{
    load() {
      let fidStr = JSON.parse(sessionStorage.getItem("BbsId"))
      request.get("/api/message/foreign/" + fidStr).then(res=>{
        this.messages = res.data.reverse()
      })
    },
    cancel() {
      this.dialogFormVisible = false;
      this.dialogFormVisible1 = false;
      this.div = false;
      this.load()
    },
    comment(){
      this.dialogFormVisible = true
    },
    reply() {
      if (!this.entity.messageContent) {
        this.$message({
          message: "请填写内容",
          type: "warning"
        });
        return;
      }
      let userStr = sessionStorage.getItem("user") || "{}"
      let user = JSON.parse(userStr)
      let fidStr = sessionStorage.getItem("BbsId")
      let bbs = JSON.parse(sessionStorage.getItem("BBS"))

      this.entity.username = user.nickname
      this.entity.foreignId = fidStr
      this.entity.avatar = user.avatar
      this.entity.aid = user.id
      this.entity.title = bbs.title
      this.entity.content = bbs.content
      this.entity.follow = bbs.follow
      this.entity.bbsAuthor = bbs.author
      request.post("/api/message", this.entity).then(res => {
        if (res.code === '0') {
          this.$message({
            message: "评论成功",
            type: "success"
          });
          this.load()
        } else {
          this.$message({
            message: res.msg,
            type: "error"
          });
        }
        this.entity = {}
        this.dialogFormVisible = false;
      })
    },
    reReply(id, content,foreignId, username){
      this.dialogFormVisible1 = true
      this.entity1.parentId = id
      this.entity1.foreignId = foreignId
      this.entity1.parentMessage = content
      this.entity1.parentAuthor = username
    },
    cReply(){
      let userStr = sessionStorage.getItem("user") || "{}"
      let user = JSON.parse(userStr)
      let bbs = JSON.parse(sessionStorage.getItem("BBS"))

      if (!this.entity1.messageContent) {
        this.$message({
          message: "请填写内容",
          type: "warning"
        });
        return;
      }

      this.entity1.avatar = user.avatar
      this.entity1.username = user.nickname
      this.entity1.aid = user.id
      this.entity1.bbsMessage = bbs.title
      this.entity1.content = bbs.content
      this.entity1.bbsAuthor = bbs.author
      this.entity1.title = bbs.title
      request.post("/api/message", this.entity1).then(res => {
        if (res.code === '0') {
          this.$message({
            message: "评论成功",
            type: "success"
          });
        } else {
          this.$message({
            message: res.msg,
            type: "error"
          });
        }
        this.entity1 = {}
        this.dialogFormVisible1 = false
        this.load()
      })
    },
    handleDelete(id){
      request.delete("/api/message/" + id).then(res =>{
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
    StoreBbs(){
      let userStr = sessionStorage.getItem("user") || "{}"
      let user = JSON.parse(userStr)
      let bbs = JSON.parse(sessionStorage.getItem("BBS"))

      request.get("/api/store/StoreCheck/" + user.id + "/" + bbs.id).then(res=>{
        let str = JSON.stringify((res.data))
        if(str.length>2){
          if(res.code == '0'){
            this.$message({
              type:"warning",
              message:"您已经收藏过啦！"
            })
            this.load()//刷新数据
          }
        }else{
          this.store.username = user.username
          this.store.title = bbs.title
          this.store.bbsId = bbs.id
          this.store.content = bbs.content
          this.store.follow = bbs.follow
          this.store.userId = user.id
          request.post("/api/store", this.store).then(res=>{
            if(res.code == '0'){
              this.$message({
                type:"success",
                message:"收藏成功"
              })
              this.load()//刷新数据
            }else{
              this.$message({
                type:"error",
                message:res.msg
              })
            }
          })
        }
      })
    },
    GoodBBS(){
      let bbs = JSON.parse(sessionStorage.getItem("BBS"))
      console.log(bbs)
      request.put("/api/bbs/good/", bbs).then(res=>{
        if(res.code == '0'){
          this.$message({
            type:"success",
            message:"点赞成功"
          })
          this.load()//刷新数据
        }else{
          this.$message({
            type:"error",
            message:res.msg
          })
        }
      })
    },
    BadBBS(){
      let bbs = JSON.parse(sessionStorage.getItem("BBS"))
      this.good = bbs
      request.put("/api/bbs/bad", this.good).then(res=>{
        if(res.code == '0'){
          this.$message({
            type:"success",
            message:"点灭成功"
          })
          this.load()//刷新数据
        }else{
          this.$message({
            type:"error",
            message:res.msg
          })
        }
      })
    },
    checkInfo(item){
      this.dialogFormVisible2 = true
      this.entity2 = item
      this.reU = item
    },
    RePort(item){
      this.div = true;
      this.tmp.reContent = item.messageContent
      this.tmp.reMid = item.id
    },
    report_1(){
      this.divT = true
    },
    report_U(entity2){
      this.divU = true
      this.tmpU.reUid = entity2.aid
      this.tmpU.reUname = entity2.username
    },
    port(content) {
      let userStr = sessionStorage.getItem("user") || "{}"
      let user = JSON.parse(userStr)

      this.tmp.portName = user.nickname
      this.tmp.portId = user.id
      this.tmp.message = content
      request.post("/api/report/", this.tmp).then(res => {
        if (res.code === '0') {
          this.$message({
            message: "举报成功",
            type: "success"
          })
          this.tmp = {}
          this.div = false
          this.load()
        } else {
          this.$message({
            message: res.msg,
            type: "error"
          });
        }
      })
    },
    portT(content) {
      let userStr = sessionStorage.getItem("user") || "{}"
      let user = JSON.parse(userStr)
      let bbs = JSON.parse(sessionStorage.getItem("BBS"))

      this.tmpT.portName = user.nickname
      this.tmpT.portId = user.id
      this.tmpT.message = content
      this.tmpT.reTitle = bbs.title
      this.tmpT.reTid = bbs.id
      request.post("/api/report/", this.tmpT).then(res => {
        if (res.code === '0') {
          this.$message({
            message: "举报成功",
            type: "success"
          })
          this.tmp = {}
          this.divT = false
          this.load()
        } else {
          this.$message({
            message: res.msg,
            type: "error"
          });
        }
      })
    },
  }
}
</script>

<style scoped>
.buttonLeft{
  text-align: left;
}
.bgImg{
  background: url("../assets/img_4.png") no-repeat;
  background-size: 100% 100%;
  background-opacity: 0.5;
}
</style>
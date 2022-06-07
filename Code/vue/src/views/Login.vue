<template>

  <div style="width: 100%; height: 100vh;background-color: whitesmoke; overflow: hidden" class="bgImg">
    <!--    Login form  -->
    <div style="width: 500px; height: 320px; background-color:rgba(9, 50, 113 ,0.2); border-radius: 10px;
        margin: 10% auto" class="item" >
      <div class="text">Welcome</div>
      <el-form ref="form" :model="form" size="normal" :rules="rules" class="form" style="text-align: center">
        <el-form-item prop="username">
          <el-input prefix-icon="el-icon-user-solid" v-model="form.username" class="foItem"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input prefix-icon="el-icon-lock" v-model="form.password" show-password class="foItem"></el-input>
        </el-form-item>
        <el-form-item style=" text-align: center; margin-bottom: 1px">
          <el-button style="cursor: pointer;"  @click="$router.push('/register')">Register </el-button>
          <el-button style="cursor: pointer; margin-left: 20px" type="primary" @click="login">Login</el-button>
        </el-form-item>
        <el-form-item style="text-align: center;">
          <el-button style="cursor: pointer; color: #f24b17;margin-left: 50% " type="text" @click="forget">Forget❓</el-button>
          <el-button style="cursor: pointer; color: coral; " type="text" @click="com">Complain❗</el-button>
          <el-button style="cursor: pointer; color: crimson; " type="text" @click="emg">Emergency</el-button>
        </el-form-item>
      </el-form>
    </div>

    <!--    Login with the emergency code  -->
    <el-dialog v-model="dialogEmergency" title="Forget" width="35%">
      <el-form :model="emergency">
        <el-form-item label="Username" label-width="120px">
          <el-input v-model="emergency.username" autocomplete="off" style="width: 80%"></el-input>
        </el-form-item>
        <el-form-item label="Code" label-width="120px">
          <el-input show-password v-model="emergency.emergency" autocomplete="off" style="width: 80%"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" style="text-align: center">
        <el-button @click="dialogFormVisible1 = false">Cancel</el-button>
        <el-button type="primary" style="margin-left: 20px" @click="log()">Log</el-button>
      </div>
    </el-dialog>

<!--    When users forgets the password, they can reset by this form  -->
    <el-dialog v-model="dialogFormVisible1" title="Forget" width="35%">
      <el-form :model="reset">
        <el-form-item label="Username" label-width="120px">
          <el-input v-model="reset.username" autocomplete="off" style="width: 80%"></el-input>
        </el-form-item>
        <el-form-item label="New pwd" label-width="120px">
          <el-input show-password v-model="reset.password" autocomplete="off" style="width: 80%"></el-input>
        </el-form-item>
        <el-form-item label="Confirm" label-width="120px">
          <el-input show-password v-model="reset.confirm" autocomplete="off" style="width: 80%"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" style="text-align: center">
        <el-button @click="dialogFormVisible1 = false">Cancel</el-button>
        <el-button type="primary" style="margin-left: 20px" @click="save(reset.username)">Reset</el-button>
      </div>
    </el-dialog>

    <!--    When users are frozen, they can complain by filling this form  -->
    <el-dialog v-model="dialogFormVisible2" title="Complain" width="25%">
      <el-form :model="Com">
        <el-form-item label="Username" label-width="120px">
          <el-input v-model="Com.comUname" autocomplete="off" style="width: 80%"></el-input>
        </el-form-item>
        <el-form-item label="Reason" label-width="120px">
          <el-input  v-model="Com.comUcontent" autocomplete="off" style="width: 80%"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" style="text-align: center">
        <el-button @click="dialogFormVisible2 = false">Cancel</el-button>
        <el-button type="primary" style="margin-left: 20px" @click="complain">Confirm</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "Login",
  data(){
    return{
      form: {},//Forget form
      reset: {},//Reset form
      rules:{//Rules of username and password when logging
        username: [
          {
            required: true,
            message: 'Please Input Username',
            trigger: 'blur',
          },
        ],
        password: [
          {
            required: true,
            message: 'Please Input password',
            trigger: 'blur',
          },
        ],
      },
      dialogFormVisible1:false,//dialog is visible or not
      entity1:{},//empty entity
      dialogFormVisible2:false,
      Com:{},
      tmp:{},
      dialogEmergency:false,
      emergency:{}
    }
  },
  created() {
   this.load()//refresh
  },
  methods:{
    load(){
      sessionStorage.removeItem("user")//remove the session we set after logging
    },
    login(){
      //valid the data which are from the database
      this.$refs['form'].validate((valid) => {
        if (valid) {
          //post the data to database -- Table: User
          request.post("/api/user/login", this.form).then(res =>{
            if(res.code == '0'){//the account is frozen
              if(res.data.report == 2){
                this.$message({
                  type:"error",
                  message:"账号冻结，请申诉"
                })
              }else{
                this.$message({
                  type:"success",
                  message:"Success login !"
                })
                //If the account has some bad behaviors, they may be warned
                //But they can still use by hinting from the administrator
                if(res.data.report == 1){
                  this.$message({
                    type:"error",
                    message:"账号违规，请注意"
                  })
                }
                sessionStorage.setItem("user", JSON.stringify(res.data))  // Store user information
                this.state()
              }
            }else{//Log in failed
              this.$message({
                type:"error",
                message:res.msg
              })
            }
          })
        }//验证合法
      })
    },
    /*
      Function -- save()
      Aim: To change the password
      Methods: PutMapping
      Table: user
     */
    save() {
      if(this.reset.password != this.reset.confirm){
        this.$message({
          type:"error",
          message:"Two passwords are different！！"
        })
      }
      if(this.reset.password.length < 6){
        this.$message({
          type:"error",
          message:"Password is too short!"
        })
      }
      else if(this.reset.password.length > 18){
        this.$message({
          type:"error",
          message:"Password is too long!"
        })
      }
      else{
        request.put("/api/user/reset", this.reset).then(res => {
          if (res.code === '0') {
            this.$message({
              type: "success",
              message: "重置成功，您的密码已修改，请重新登录"
            })
            this.dialogFormVisible1 = false
            this.load()//refresh
          } else {
            this.$message({
              type: "error",
              message: res.msg
            })
          }
        })
      }
    },
    /*
      Function -- log()
      Aim: To login with emergency code when forget password
      Methods: PostMapping
      Table: user
     */
    log(){
      //post the data to database -- Table: User
      request.post("/api/user/login/emergency", this.emergency).then(res =>{
        if(res.code == '0'){//the account is frozen
          if(res.data.report == 2){
            this.$message({
              type:"error",
              message:"账号冻结，请申诉"
            })
          }else{
            this.$message({
              type:"success",
              message:"Success login !"
            })
            //If the account has some bad behaviors, they may be warned
            //But they can still use by hinting from the administrator
            if(res.data.report == 1){
              this.$message({
                type:"error",
                message:"账号违规，请注意"
              })
            }
            sessionStorage.setItem("user", JSON.stringify(res.data))  // Store user information
            this.state()
          }
        }else{//Log in failed
          this.$message({
            type:"error",
            message:res.msg
          })
        }
      })
    },
    /*
      Function -- complain()
      Aim: To complain for the account
      Methods: PutMapping
      Table: complain
     */
    complain() {
      this.tmp.comUname = this.Com.comUname
      this.tmp.comUcontent = this.Com.comUcontent
      request.post("/api/complain/", this.tmp).then(res => {
        if (res.code === '0') {
          this.$message({
            message: "Complain success, please wait for reply !",
            type: "success"
          })
          this.tmp = {}
          this.Com = {}
          this.dialogFormVisible2 = false
          this.load()
        } else {
          this.$message({
            message: res.msg,
            type: "error"
          });
        }
      })
    },
    state(){//Change to the index of BBS
      this.$router.push("/")
    },
    forget(){
      this.dialogFormVisible1 = true
    },
    com(){
      this.dialogFormVisible2 = true
    },
    emg(){
      this.dialogEmergency = true
    }
  }
}
</script>

<!--CSS tables-->
<style scoped>
.bgImg {
  background: url("../assets/img/cover.jpg") no-repeat;
  background-size: 100% 100vh;
  align-items: center;
}
.item {
  text-align: center;
  padding: 10px 0;
  font-size: 20px;
  font-family: 'Helvetica Neue', Helvetica, 'PingFang SC', 'Hiragino Sans GB', 'Microsoft YaHei', '微软雅黑', Arial, sans-serif;
  color: 	#DDDDDD;
}
.foItem{
  width: 400px;
  margin: 15px 0;
}

.text{
  color: #CCEEFF;
  -webkit-background-clip: text;/* 裁剪背景图，使文字作为裁剪区域向外裁剪 */
  -webkit-background-size: 200% 100%;
  -webkit-animation: masked-animation 4s linear infinite;
  font-size: 40px;
  font-family: Broadway;
}

</style>
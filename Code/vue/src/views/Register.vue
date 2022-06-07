<template>
  <div class="bgImg" style="text-align: center">
    <el-scrollbar ref="scrollbar" height="5%" always @scroll="scroll">
    <div style="width: 100%; height: 100vh; overflow: hidden" >
      <div style="width: 450px; height: 550px; background-color:rgba(9, 50, 113 ,0.2); border-radius: 10px;
        margin: 2% auto;">
        <div class="item" style="font-size: 40px; color: blanchedalmond; font-family: Bahnschrift">Welcome</div>
        <el-form ref="form" :model="form" size="normal" :rules="rules" style="font-family:'Times New Roman'">
          <el-form-item prop="username">
            <el-input v-model="form.username" style="width: 80%; padding: 6px; border-radius: 5px;" placeholder="Please input username(3 < length < 10)"></el-input>
          </el-form-item>
          <el-form-item prop="nickname">
            <el-input style="width: 80%; padding: 5px; border-radius: 6px;  box-sizing:border-box;" placeholder=" Please input nickname" v-model="form.nickname"></el-input>
          </el-form-item>
          <el-form-item prop="password">
            <el-input style="width: 80%; padding: 5px; border-radius: 6px;  box-sizing:border-box;" placeholder=" Password:(6 < length <10)Numbers + Alphabets" v-model="form.password" show-password></el-input>
          </el-form-item>
          <el-form-item prop="confirm">
            <el-input style="width: 80%; padding: 5px; border-radius: 6px;  box-sizing:border-box;" placeholder=" Please confirm password" v-model="form.confirm" show-password></el-input>
          </el-form-item>
          <el-form-item prop="age">
            <el-input style="width: 80%; padding: 5px; border-radius: 6px;  box-sizing:border-box;" placeholder=" Please input your age" v-model="form.age"></el-input>
          </el-form-item>
          <el-form-item style="color: blanchedalmond" prop="sex">
            <el-radio-group v-model="form.sex">
              <el-radio style="color: blanchedalmond" label="Male"></el-radio>
              <el-radio style="color: blanchedalmond" label="Female"></el-radio>
              <el-radio style="color: blanchedalmond" label="Unknown"></el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item>
            <el-radio v-model="form.Agree" label="1" style="color: orangered; ">Agree</el-radio>
            <el-button style="width: 30%" type="primary" @click="Register">Register</el-button>
          </el-form-item>
        </el-form>
      </div>
      <el-backtop />
    </div>
    </el-scrollbar>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "Register",
  data(){
    return{
      form: {},
      rules:{
        username: [
          {
            required: true,
            message: '请输入用户名',
            trigger: 'blur',
          },
        ],
        password: [
          {
            required: true,
            message: '请输入密码',
            trigger: 'blur',
          },
        ],
        confirm: [
          {
            required: true,
            message: '请确认密码',
            trigger: 'blur',
          },
        ],
      },
      dialogFormVisible1:false,
      entity1: {},
    }
  },
  methods:{
    scroll({ scrollTop }) {
      this.value = scrollTop
    },
    Register(){
      if(this.form.Agree != 1) {
        this.$message({
          type:"error",
          message:"Please agree the agreements"
        })
      } else{
        if(this.form.username.length < 3){
          this.$message({
            type:"error",
            message:"Username is too short"
          })
        }
        else if(this.form.username.length > 10){
          this.$message({
            type:"error",
            message:"Username is too long"
          })
        }
        else {
          if(this.form.nickname.length > 10){
            this.$message({
              type:"error",
              message:"Nickname is too long"
            })
          }
          else{
            if(this.form.password != this.form.confirm){
              this.$message({
                type:"error",
                message:"Two passwords are different！！"
              })
            }else {
              if(this.form.password.length < 6){
                this.$message({
                  type:"error",
                  message:"Password is too short"
                })
              }
              if(this.form.password.length > 18){
                this.$message({
                  type:"error",
                  message:"Password is too long"
                })
              }
              else{
                this.$refs['form'].validate((valid) => {
                  if (valid) {
                    request.post("/api/user/register", this.form).then(res =>{
                      if(res.code == '0'){
                        this.$message({
                          type:"success",
                          message:"Success register"
                        })
                        this.$router.push("/login")
                      }else{
                        this.$message({
                          type:"error",
                          message:res.msg
                        })
                      }
                    })
                  }//验证合法
                })
              }
            }
          }
        }
      }
    }
  },
}
</script>

<style scoped>
  .item {
    text-align: center;
    padding: 10px 0
  }
  .bgImg {
    background: url("../assets/img/cover.jpg") no-repeat;
    background-size: 100% 100vh;
  }
</style>
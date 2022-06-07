<template>
  <div style="height:52px; line-height: 50px; border-bottom: 1px solid #ccc;
    display: flex; background-color: ghostwhite ">
    <div  class="bbs">Student BBS</div>
    <div style="flex: 1"></div>
    <div style="margin-right: 10px">
      <el-avatar :size="50" :src="user.avatar"></el-avatar>
    </div>
    <div style="width: 100px">
      <el-dropdown>
      <span class="el-dropdown-link">
           {{ user.nickname }}
          <i class="el-icon-arrow-down el-icon--right"></i>
      </span>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item @click="$router.push('/person')">Personal Info</el-dropdown-item>
            <el-dropdown-item @click="$router.push('/index')">Personal Page</el-dropdown-item>
            <el-dropdown-item @click="quit">Logout</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "Header",
  props: ['user'],
  data() {
    return {
      user:{}
    }
  },
  created() {
    let userStr = sessionStorage.getItem("user") || "{}"
    this.user = JSON.parse(userStr)
  },
  methods:{
    quit(){
      request.put("/api/user/quit", this.user)
      this.$router.push('/login')
    }
  }
}
</script>

<style scoped>
.bbs{
  margin-left: 30px;
  font-size: xx-large;
  font-weight: bold;
  font-family: Candara;
  color: transparent;
  -webkit-text-stroke: 1px cornflowerblue;
  letter-spacing: 0.04em;
}
</style>
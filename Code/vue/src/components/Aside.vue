<template>
    <el-menu
        :default-active="path"
        router
        :collapse="isCollapse"
        style="width: 150px;
        min-height: calc(100vh - 5px);
        background: ghostwhite">
      <el-sub-menu index="1" v-if="user.role == 1">
        <template #title >
          ⚙️&nbsp;Manage</template>
        <el-menu-item index="/user" >
          🧸 User
        </el-menu-item>
        <el-menu-item index="/news">
          📬 News</el-menu-item>
        <el-menu-item index="/bbsadmin">
          📜 BBS</el-menu-item>
      </el-sub-menu>
      <el-menu-item index="/bms" style="margin-top: 20px">
        📰 News
      </el-menu-item>
      <el-sub-menu style="margin-top: 20px">
        <template #title >
          📚 BBS</template>
        <el-menu-item index="/bbs" >
          📖 Index
        </el-menu-item>
        <el-menu-item index="/bbsStudy" >
          📕 Study
        </el-menu-item>
        <el-menu-item index="/bbsFood">
          🍽️ Food</el-menu-item>
        <el-menu-item index="/bbsShop">
          🛒 Shop</el-menu-item>
        <el-menu-item index="/bbsTravel">
          🚢 Travel</el-menu-item>
      </el-sub-menu>
    </el-menu>
</template>

<script>

import request from "@/utils/request";

export default {
  name: "Aside",
  data(){
    return{
      user:{},
      path:this.$route.path,//设置默认高亮
      isCollapse:true,
    }
  },
  created() {
    let userStr = sessionStorage.getItem("user") || "{}"
    this.user = JSON.parse(userStr)

    // // 请求服务端，确认当前登录用户的 合法信息
    request.get("/api/user/" + this.user.id).then(res => {
      if (res.code === '0') {
        this.user = res.data
      }
    })
  },
}
</script>

<style>
.back{
  font-weight: bolder;
}

</style>

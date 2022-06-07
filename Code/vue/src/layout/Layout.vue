<template>
  <div >
    <!--    头部-->
    <Header style="display: flex; " />

    <el-scrollbar ref="scrollbar" height="100%" always @scroll="scroll">
    <!--    主体-->
    <div style="display: flex;" >
      <!--      侧边栏-->
      <Aside  class="back"/>
      <!--      内容区域-->
      <router-view style="flex: 1"/>
    </div>
    </el-scrollbar>
  </div>
</template>

<script>
import Header from "@/components/Header";
import Aside from "@/components/Aside";

export default {
  name: "Layout",
  components: {
    Header,
    Aside
  },
  methods: {
    scroll({ scrollTop }) {
      this.value = scrollTop
    },
  },
}
</script>

<style scoped>

</style>
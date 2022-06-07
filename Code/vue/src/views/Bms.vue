<template>
  <div style="padding-left: 10px; background-color: #f5f5f5;" class="backImg">

    <el-container>
      <el-header class = "header" >
        &nbsp;System Announcement
        <el-button type="text" @click="open3"></el-button>
      </el-header>
      <br />
      <el-main>
        <div class="timeline">
          <el-timeline >
            <el-timeline-item
                :name="index"
                v-for="(item,index) in notices"
                :key="item.id"
                :timestamp="item.time"
                style="color: black"
                placement="top">
              <el-card class="card">
                <template #header>
                  <div class="card-header">
                    {{ item.title }}
                  </div>
                </template>
                <div style="padding: 0 20px" v-html="item.content" class="content"></div>
              </el-card>
            </el-timeline-item>
          </el-timeline>
        </div>
      </el-main>
    </el-container>

    <el-backtop :bottom="100">
      🚀
    </el-backtop>
    </div>

</template>

<script>
import request from "@/utils/request";
import { ElNotification } from 'element-plus'

export default {
  name: "Bms",
  data() {
    return {
      notices: [],
      active: 0,
      dialogFormVisible1:false,
    };
  },
  created() {
    this.load()
    let user = sessionStorage.getItem("user")
    this.form = JSON.parse(user)
    request.put("/api/user/log", this.form)
    this.load()
    this.open3()
  },
  methods: {
    load() {
      request.get("/api/news/").then((res) => {
        let rev = res.data
        rev.reverse()
        this.notices = rev;
      })
    },
    open3() {
      ElNotification({
        title: 'Hint',
        dangerouslyUseHTMLString: true,
        message: 'Please check the <strong style="color: #f24b17">Emergency Code</strong><br />'
            +'Keep it <strong style="color:#f24b17">carefully</strong>',
        type: 'info',
        position: 'bottom-right',
      })
    },
  }
}
</script>

<style scoped>
.header{
  font-size: 50px;
  margin: 0px auto;
  color: transparent;
  background-color: cornflowerblue;
  text-shadow : rgba(255,255,255,0.5) 0 5px 6px, rgba(255,255,255,0.2) 1px 3px 3px;
  -webkit-background-clip : text;
  font-family: "Times New Roman";
}
.timeline {
  text-align: center;
  width: 80%;
  margin: 0px auto;
}
.card-header{
  font-size: x-large;
  background: #EEE url(data:image/gif;base64,iVBORw0KGgoAAAANSUhEUgAAAAQAAAAECAIAAAAmkwkpAAAAHklEQVQImWNkYGBgYGD4//8/A5wF5SBYyAr+//8PAPOCFO0Q2zq7AAAAAElFTkSuQmCC) repeat;
  text-shadow: 5px -5px black, 4px -4px white;
  font-weight: bold;
  -webkit-text-fill-color: transparent;
  -webkit-background-clip: text
}
.backImg{
  background: url("../assets/img_4.png") no-repeat;
  background-size: 100% 100%;
}
.card{
  background: rgba(0,0,0,0);
}
.content{
  font-family: "Arial", "Microsoft YaHei", "黑体", "宋体", sans-serif;
  font-weight: bold;
}
</style>
<template>
  <div>
    <el-button type="primary" @click="play">播放</el-button>
    <div class="video">
      <video
          id="vPull"
          autoplay
          controls
          height="100%"
          muted
          width="100%">
      </video>
    </div>
  </div>
</template>

<script>
import flv from "flv.js";

export default {
  name: "",
  data() {
    return {
      player: null,
    };
  },
  destroyed() {
    this.destruction()
  },
  methods: {
    play() {
      let urls = 'https://hw.flv.huya.com/src/2368274334-2368274334-10171660812486180864-4736672124-10057-A-0-1-imgplus.flv?wsSecret=e85f24c8c798ccbd5cb42773ded0b1fe&wsTime=65a6798c&u=0&seqid=17053225305117720&fm=RFdxOEJjSjNoNkRKdDZUWV8kMF8kMV8kMl8kMw%3D%3D&ctype=tars_mobile&fs=bgct&sphdcdn=al_7-tx_3-js_3-ws_7-bd_2-hw_2&sphdDC=huya&sphd=264_'
      let video = document.getElementById("vPull"); //定义播放路径
      if (flv.isSupported()) {
        this.player = flv.createPlayer(
            {
              type: "flv",
              isLive: true,
              url: urls,
            },
            {
              enableWorker: false, //不启用分离线程
              enableStashBuffer: false, //关闭IO隐藏缓冲区
              isLive: true,
              lazyLoad: false,
            },
        );
      } else {
        console.log("不支持的格式");
        return;
      }
      this.player.attachMediaElement(video);
      this.player.load();
      this.player.play();
    },
    destruction() {
      //销毁对象
      if (this.player) {
        this.player.pause();
        this.player.destroy();
        this.player = null;
      }
    },
  }
};
</script>

<style>
.video {
  height: 500px;
  width: 1000px;
}
</style>
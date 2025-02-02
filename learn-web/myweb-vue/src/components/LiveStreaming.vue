<template>
  <template>
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
  </template>
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
  methods: {
    play(urls) {
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
            }
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
  },
  mounted() {
    this.play('https://aldirect.flv.huya.com/huyalive/62160692-62160692-266978139236728832-124444840-10057-A-0-1.flv?wsSecret=45bcf9e878b249e9be74a6ddaf1e39b0&wsTime=65a66b3c&u=0&seqid=17053188523825424&fm=RFdxOEJjSjNoNkRKdDZUWV8kMF8kMV8kMl8kMw%3D%3D&ctype=tars_mobile&fs=bgct&t=103');
  }
};
</script>
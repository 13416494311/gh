<template>
  <div class="app-container">
    <iframe id="iframe"
            frameborder='0'
            scrolling="no"
            class="tduck-iframe"
            :src="src" ></iframe>
  </div>
</template>
<style>
  .tduck-iframe{
    width: 100%;
  }
</style>
<script>
  import { getTduckLogin } from "@/api/democracy/tduck";

  export default {
    name: 'Tduck',
    data() {
      return {
        src: '',
      }
    },
    created() {
      this.toTduck()
    },
    mounted(){
      /**
       * iframe-宽高自适应显示
       */
      this.resizeIframe();
      window.addEventListener('resize', this.resizeIframe);
    },
    methods: {
      toTduck() {
        const loading = this.$loading({
          lock: true,
          text: '加载中……',
          spinner: 'el-icon-loading',
          background: 'rgba(0, 0, 0, 0.7)'
        });
        getTduckLogin().then(response => {
          this.src = response.msg;
          loading.close();
        });
      },
      resizeIframe(){
        const oIframe = document.getElementById('iframe');
        const deviceHeight = document.documentElement.clientHeight;
        oIframe.style.height = (Number(deviceHeight)-130) + 'px'; //数字是页面布局高度差
      }

    }
  }
</script>


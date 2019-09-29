<template>
  <div class="footer" v-show="isShow">
    <div class="copyright">
      Copyright
      <a-icon type="copyright"/>
      <span v-html="copyright"></span>
    </div>
  </div>
</template>

<script>
export default {
  name: 'GlobalFooter',
  props: ['copyright'],
  data: {
    isShow: false
  },
  computed: {
    isShow: function () {
      return !(this.$store.state.setting.copyright === '')
    }
  }
}
</script>

<style lang="less" scoped>
  .footer {
    padding: 0 16px;
    margin: 24px 0;
    text-align: center;

    .copyright {
      color: rgba(0, 0, 0, .45);
      font-size: 14px;
      i {
        font-size: .8rem !important;
      }
    }

    &a {
      text-decoration: none;
    }
  }
</style>

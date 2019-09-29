<template>
  <a-modal
    class="update-avatar"
    title="选择头像"
    @cancel="handleCancel"
    :width="710"
    :footer="null"
    v-model="show">
    <a-tabs defaultActiveKey="1" class="avatar-tabs">
      <a-tab-pane tab="后田花子" key="1">
        <template v-for="(avatar, index) in hthz">
          <div class="avatar-wrapper" :key="index">
            <img alt="点击选择" :src="'static/avatar/' + avatar" @click="change(avatar)">
          </div>
        </template>
      </a-tab-pane>
      <a-tab-pane tab="阿里系" key="2" forceRender>
        <template v-for="(avatar, index) in al">
          <div class="avatar-wrapper" :key="index">
            <img alt="点击选择" :src="'static/avatar/' + avatar" @click="change(avatar)">
          </div>
        </template>
      </a-tab-pane>
      <a-tab-pane tab="脸萌" key="3">
        <template v-for="(avatar, index) in lm">
          <div class="avatar-wrapper" :key="index">
            <img alt="点击选择" :src="'static/avatar/' + avatar" @click="change(avatar)">
          </div>
        </template>
      </a-tab-pane>
      <!-- 自定义上传 -->
      <a-tab-pane tab="自定义" key="4" disabled>
        <template>
          <a-upload
            name="avatar"
            listType="picture-card"
            class="avatar-uploader"
            :showUploadList="false"
            :action="uploadAvator"
            :beforeUpload="beforeUpload"
            @change="handleChange">
            <img v-if="imageUrl" :src="imageUrl" alt="avatar" />
            <div v-else>
                <a-icon :type="loading ? 'loading' : 'plus'" />
                <div class="ant-upload-text">上传头像</div>
            </div>
          </a-upload>
        </template>
        <template>
        <div>
          <a-button type="primary" disabled>保存</a-button>
        </div>
      </template>
      </a-tab-pane>
    </a-tabs>
  </a-modal>
</template>
<script>
const hthz = ['default.jpg', '1d22f3e41d284f50b2c8fc32e0788698.jpeg',
  '2dd7a2d09fa94bf8b5c52e5318868b4d9.jpg', '2dd7a2d09fa94bf8b5c52e5318868b4df.jpg',
  '8f5b60ef00714a399ee544d331231820.jpeg', '17e420c250804efe904a09a33796d5a10.jpg',
  '17e420c250804efe904a09a33796d5a16.jpg', '87d8194bc9834e9f8f0228e9e530beb1.jpeg',
  '496b3ace787342f7954b7045b8b06804.jpeg', '595ba7b05f2e485eb50565a50cb6cc3c.jpeg',
  '964e40b005724165b8cf772355796c8c.jpeg', '5997fedcc7bd4cffbd350b40d1b5b987.jpg',
  '5997fedcc7bd4cffbd350b40d1b5b9824.jpg', 'a3b10296862e40edb811418d64455d00.jpeg',
  'a43456282d684e0b9319cf332f8ac468.jpeg', 'bba284ac05b041a8b8b0d1927868d5c9x.jpg',
  'c7c4ee7be3eb4e73a19887dc713505145.jpg', 'ff698bb2d25c4d218b3256b46c706ece.jpeg']
const al = ['cnrhVkzwxjPwAaCfPbdc.png', 'BiazfanxmamNRoxxVxka.png', 'gaOngJwsRYRaVAuXXcmB.png',
  'WhxKECPNujWoWEFNdnJE.png', 'ubnKSIfAJTxIgXOKlciN.png', 'jZUIxmJycoymBprLOUbT.png']
const lm = ['19034103295190235.jpg', '20180414165920.jpg', '20180414170003.jpg',
  '20180414165927.jpg', '20180414165754.jpg', '20180414165815.jpg',
  '20180414165821.jpg', '20180414165827.jpg', '20180414165834.jpg',
  '20180414165840.jpg', '20180414165846.jpg', '20180414165855.jpg',
  '20180414165909.jpg', '20180414165914.jpg', '20180414165936.jpg',
  '20180414165942.jpg', '20180414165947.jpg', '20180414165955.jpg']
export default {
  props: {
    updateAvatarModelVisible: {
      default: false
    },
    user: {
      required: true
    }
  },
  data () {
    return {
      hthz,
      al,
      lm,
      updating: false,
      loading: false,
      imageUrl: '',
    }
  },
  computed: {
    show: {
      get: function () {
        return this.updateAvatarModelVisible
      },
      set: function () {
      }
    }
  },
  methods: {
    handleCancel () {
      this.$emit('cancel')
    },
    uploadAvator (file){
      console.log(file);
      // 调用服务上传方法
      // this.$upload('upload', file).then((r) => {
      //   console.log(r)
      // }).catch((r) => {
      //   console.error(r)
      // })
    },
    change (avatar) {
      if (this.updating) {
        this.$message.warning('更换头像中，请勿重复点击')
        return
      }
      this.updating = true
      this.$put('user/avatar', {
        username: this.user.username,
        avatar
      }).then(() => {
        this.$emit('success', avatar)
        this.updating = false
      }).catch((r) => {
        console.error(r)
        this.$message.error('更新头像失败')
        this.updating = false
      })
    },
    handleChange (info) {
      if (info.file.status === 'uploading') {
        this.loading = true
        return
      }
      if (info.file.status === 'done') {
        // Get this url from response in real world.
        getBase64(info.file.originFileObj, (imageUrl) => {
          this.imageUrl = imageUrl
          this.loading = false
        })
      }
    },
    beforeUpload (file) {
      let isJPG = false
      if(file.type === 'image/jpeg' || file.type === 'image/png' || file.type === 'image/gif' || file.type === 'image/bmp'){
        isJPG = true
      }
      if (!isJPG) {
        this.$message.error('仅支持 JPG、PNG、GIF或BMP文件!')
      }
      const isLt2M = file.size / 1024 / 1024 < 2
      if (!isLt2M) {
        this.$message.error('图片必须小于 2MB!')
      }
      return isJPG && isLt2M
    }
  }
}
</script>
<style lang="less">
  .update-avatar {
    .ant-modal-body {
      padding: 0 1rem 1rem 1rem!important;
      .avatar-tabs {
        .avatar-wrapper {
          display: inline-block;
          img {
            width: 6rem;
            border-radius: 2px;
            display: inline-block;
            margin: .5rem;
            cursor: pointer;
          }
        }
      }
    }
  }
  .avatar-uploader > .ant-upload {
    width: 128px;
    height: 128px;
  }
  .ant-upload-select-picture-card i {
    font-size: 32px;
    color: #999;
  }
  .ant-upload-select-picture-card .ant-upload-text {
    margin-top: 8px;
    color: #666;
  }
</style>

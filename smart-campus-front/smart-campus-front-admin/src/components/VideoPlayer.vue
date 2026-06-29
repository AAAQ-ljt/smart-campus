<script setup>
import { ref, watch, nextTick } from 'vue'
import Artplayer from 'artplayer'
import Hls from 'hls.js'
import BaseDialog from '@/components/BaseDialog.vue'
import { getToken } from '@/utils/token'

defineOptions({ name: 'VideoPlayer' })

const props = defineProps({
  visible: { type: Boolean, default: false },
  resourceId: { type: String, default: '' },
  title: { type: String, default: '' }
})

const emit = defineEmits(['update:visible'])

const artRef = ref(null)
let artInstance = null

function buildUrl(path) {
  const token = getToken()
  return `/api${path}?token=${encodeURIComponent(token || '')}`
}

function initPlayer() {
  if (!artRef.value || !props.resourceId) return
  destroyPlayer()

  const m3u8Url = buildUrl(`/resourceInfo/video/${props.resourceId}/index.m3u8`)

  artInstance = new Artplayer({
    container: artRef.value,
    url: m3u8Url,
    type: 'm3u8',
    autoplay: true,
    autoSize: true,
    playbackRate: true,
    aspectRatio: true,
    fullscreen: true,
    setting: true,
    hotkey: true,
    pip: true,
    mutex: true,
    theme: '#409eff',
    customType: {
      m3u8: function (video, url, art) {
        if (Hls.isSupported()) {
          const hls = new Hls()
          hls.loadSource(url)
          hls.attachMedia(video)
          art.on('destroy', () => hls.destroy())
        } else if (video.canPlayType('application/vnd.apple.mpegurl')) {
          video.src = url
        }
      }
    }
  })
}

function destroyPlayer() {
  if (artInstance) {
    artInstance.destroy(false)
    artInstance = null
  }
}

function handleClose() {
  destroyPlayer()
  emit('update:visible', false)
}

watch(() => props.visible, (val) => {
  if (val) {
    nextTick(initPlayer)
  } else {
    destroyPlayer()
  }
})
</script>

<template>
  <BaseDialog
    :model-value="visible"
    :title="title || '视频播放'"
    width="80%"
    top="5vh"
    :show-cancel="false"
    :show-close="true"
    :close-on-click-modal="true"
    confirm-text="关闭"
    content-padding="0"
    class="video-dialog"
    @update:model-value="handleClose"
  >
    <div ref="artRef" class="player-box" />
  </BaseDialog>
</template>

<style>
/* el-dialog 全局覆盖，消除白边 */
.video-dialog {
  --el-dialog-bg-color: #000;
  border-radius: 12px;
  overflow: hidden;
}

.video-dialog .el-dialog__header {
  background: #000;
  padding: 0 20px;
  height: 48px;
  display: flex;
  align-items: center;
  border-bottom: none;
  margin-right: 0;
}

.video-dialog .el-dialog__title {
  color: #e0e0e0;
  font-size: 15px;
  font-weight: 500;
}

.video-dialog .el-dialog__headerbtn {
  top: 12px;
}

.video-dialog .el-dialog__headerbtn .el-dialog__close {
  color: #999;
  font-size: 20px;
}

.video-dialog .el-dialog__headerbtn .el-dialog__close:hover {
  color: #fff;
}

.video-dialog .el-dialog__body {
  padding: 0 !important;
  background: #000;
}

.video-dialog .el-dialog__footer {
  display: none;
}
</style>

<style scoped>
.player-box {
  width: 100%;
  height: calc(80vh - 48px);
  background: #000;
}

:deep(.artplayer) {
  font-family: inherit;
}
</style>

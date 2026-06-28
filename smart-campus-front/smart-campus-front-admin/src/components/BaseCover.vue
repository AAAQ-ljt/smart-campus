<script setup>
import { computed } from 'vue'

defineOptions({ name: 'BaseCover' })

const props = defineProps({
  src: { type: String, default: '' },
  alt: { type: String, default: '' },
  width: { type: String, default: '100%' },
  aspectRatio: { type: String, default: '16 / 9' },
  borderRadius: { type: String, default: '8px' },
  fit: { type: String, default: 'cover' }
})

const wrapperStyle = computed(() => ({
  width: props.width,
  aspectRatio: props.aspectRatio,
  borderRadius: props.borderRadius
}))
</script>

<template>
  <div class="base-cover" :style="wrapperStyle">
    <img
      v-if="src"
      :src="src"
      :alt="alt"
      class="cover-image"
      :style="{ objectFit: fit, borderRadius: borderRadius }"
    />
    <div v-else class="cover-placeholder">
      <el-icon :size="28" color="#c0c4cc"><PictureFilled /></el-icon>
    </div>
  </div>
</template>

<style scoped>
.base-cover {
  position: relative;
  overflow: hidden;
  background: #f5f7fa;
}

.cover-image {
  width: 100%;
  height: 100%;
  display: block;
}

.cover-placeholder {
  position: absolute;
  inset: 0;
  display: flex;
  align-items: center;
  justify-content: center;
  background: #f5f7fa;
  border: 1px dashed #dcdfe6;
  border-radius: inherit;
}
</style>

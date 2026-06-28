<script setup>
import { computed } from 'vue'

defineOptions({ name: 'BaseDialog' })

const props = defineProps({
  modelValue: { type: Boolean, default: false },
  title: { type: String, default: '' },
  width: { type: String, default: '520px' },
  top: { type: String, default: '30px' },
  showCancel: { type: Boolean, default: true },
  showClose: { type: Boolean, default: true },
  contentPadding: { type: String, default: '20px' },
  confirmText: { type: String, default: '确定' },
  cancelText: { type: String, default: '取消' },
  closeOnClickModal: { type: Boolean, default: true },
  loading: { type: Boolean, default: false }
})

const emit = defineEmits(['update:modelValue', 'confirm', 'cancel'])

const topNum = computed(() => parseFloat(props.top) || 30)

const bodyMaxHeight = computed(() => `calc(100vh - ${topNum.value * 2 + 180}px)`)

function handleConfirm() {
  emit('confirm')
}

function handleCancel() {
  emit('cancel')
  emit('update:modelValue', false)
}

function handleClose() {
  emit('update:modelValue', false)
}
</script>

<template>
  <el-dialog
    :model-value="modelValue"
    :title="title"
    :width="width"
    :top="top"
    :close-on-click-modal="closeOnClickModal"
    :show-close="showClose"
    @update:model-value="handleClose"
  >
    <div
      class="dialog-body"
      :style="{ padding: contentPadding, maxHeight: bodyMaxHeight }"
    >
      <slot />
    </div>

    <template #footer>
      <div class="dialog-footer">
        <slot name="footer">
          <el-button v-if="showCancel" link @click="handleCancel">
            {{ cancelText }}
          </el-button>
          <el-button type="primary" :loading="loading" @click="handleConfirm">
            {{ confirmText }}
          </el-button>
        </slot>
      </div>
    </template>
  </el-dialog>
</template>

<style scoped>
.dialog-body {
  overflow-y: auto;
}

.dialog-footer {
  display: flex;
  justify-content: flex-end;
  align-items: center;
  gap: 12px;
}
</style>

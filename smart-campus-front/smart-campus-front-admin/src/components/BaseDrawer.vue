<script setup>
defineOptions({ name: 'BaseDrawer' })

const props = defineProps({
  modelValue: { type: Boolean, default: false },
  title: { type: String, default: '' },
  size: { type: String, default: '480px' },
  showClose: { type: Boolean, default: true },
  showCancel: { type: Boolean, default: true },
  contentPadding: { type: String, default: '20px' },
  confirmText: { type: String, default: '确定' },
  cancelText: { type: String, default: '取消' },
  loading: { type: Boolean, default: false },
  closeOnClickModal: { type: Boolean, default: true },
  direction: { type: String, default: 'rtl' }
})

const emit = defineEmits(['update:modelValue', 'confirm', 'cancel'])

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
  <el-drawer
    :model-value="modelValue"
    :title="title"
    :size="size"
    :direction="direction"
    :close-on-click-modal="closeOnClickModal"
    :with-header="showClose || !!title"
    @close="handleClose"
  >
    <div class="drawer-body" :style="{ padding: contentPadding }">
      <slot />
    </div>

    <template #footer>
      <div class="drawer-footer">
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
  </el-drawer>
</template>

<style scoped>
.drawer-body {
  flex: 1;
  overflow-y: auto;
}

.drawer-footer {
  display: flex;
  justify-content: flex-end;
  align-items: center;
  gap: 12px;
}
</style>

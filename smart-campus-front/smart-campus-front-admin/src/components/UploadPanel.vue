<script setup>
import { ref, computed } from 'vue'
import { ElMessage } from 'element-plus'
import { chunkedUpload, cancelUpload } from '@/api/upload'

const MAX_FILE_SIZE = 1 * 1024 * 1024 * 1024

defineOptions({ name: 'UploadPanel' })

const props = defineProps({
  parentId: { type: String, default: '0' }
})

const emit = defineEmits(['upload-complete'])

const collapsed = ref(false)
const MAX_CONCURRENT = 5

const uploading = ref([])

const activeCount = computed(() => uploading.value.filter(t => t.status === 'uploading').length)
const waitingCount = computed(() => uploading.value.filter(t => t.status === 'waiting').length)
const totalCount = computed(() => uploading.value.length)
const completedCount = computed(() => uploading.value.filter(t => t.status === 'done').length)

function addFiles(files) {
  for (const file of files) {
    if (file.size > MAX_FILE_SIZE) {
      ElMessage.warning(`文件「${file.name}」大小为 ${formatSize(file.size)}，超过系统限制（最大支持 1GB），已跳过`)
      continue
    }
    uploading.value.push({
      id: Date.now() + Math.random().toString(36).slice(2),
      name: file.name,
      size: file.size,
      progress: 0,
      status: 'waiting',
      file,
      abortController: new AbortController()
    })
  }
  processQueue()
}

function processQueue() {
  const waiting = uploading.value.filter(t => t.status === 'waiting')
  for (const task of waiting) {
    if (activeCount.value >= MAX_CONCURRENT) break
    startUpload(task)
  }
}

async function startUpload(task) {
  task.status = 'uploading'
  try {
    const uploadId = await chunkedUpload(
      task.file,
      props.parentId,
      (percent) => { task.progress = percent },
      task.abortController.signal,
      () => { emit('upload-start') }
    )
    task.uploadId = uploadId
    task.status = 'done'
    task.progress = 100
    emit('upload-complete')
  } catch (e) {
    if (e && e.name === 'AbortError') {
      task.status = 'cancelled'
    } else {
      task.status = 'error'
      task.errorMsg = e?.message || '上传失败'
    }
  } finally {
    processQueue()
  }
}

function clearDone() {
  uploading.value = uploading.value.filter(t => t.status === 'uploading' || t.status === 'waiting')
}

function clearItem(task) {
  if (task.status === 'uploading') {
    task.abortController?.abort()
  }
  if (task.uploadId && (task.status === 'uploading' || task.status === 'waiting')) {
    cancelUpload(task.uploadId).catch(() => {})
  }
  uploading.value = uploading.value.filter(t => t.id !== task.id)
}

function toggleCollapse() {
  collapsed.value = !collapsed.value
}

function formatSize(bytes) {
  if (!bytes) return '0 B'
  const units = ['B', 'KB', 'MB', 'GB']
  let i = 0, size = bytes
  while (size >= 1024 && i < units.length - 1) { size /= 1024; i++ }
  return size.toFixed(i === 0 ? 0 : 1) + ' ' + units[i]
}

defineExpose({ addFiles })
</script>

<template>
  <div v-if="uploading.length > 0" class="upload-panel" :class="{ collapsed }">
    <div class="upload-header" @click="toggleCollapse">
      <span class="upload-title">
        文件上传
        <span v-if="activeCount > 0" class="upload-badge">{{ activeCount }}/{{ totalCount }}</span>
      </span>
      <span class="upload-toggle">{{ collapsed ? '展开' : '收起' }}</span>
    </div>

    <div v-show="!collapsed" class="upload-body">
      <div class="upload-summary">
        <span>上传中 {{ activeCount }} / 等待中 {{ waitingCount }} / 已完成 {{ completedCount }}</span>
        <el-button link size="small" type="danger" @click="clearDone">清空已完成</el-button>
      </div>
      <div class="upload-list">
        <div v-for="task in uploading" :key="task.id" class="upload-item">
          <div class="upload-item-info">
            <span class="upload-item-name" :title="task.name">{{ task.name }}</span>
            <span class="upload-item-size">{{ formatSize(task.size) }}</span>
          </div>
          <div class="upload-item-bar">
            <el-progress
              :percentage="task.progress"
              :status="task.status === 'error' ? 'exception' : task.status === 'done' ? 'success' : ''"
              :stroke-width="6"
              :show-text="true"
            />
          </div>
          <div class="upload-item-status">
            <span v-if="task.status === 'waiting'" class="status-waiting">等待上传</span>
            <span v-else-if="task.status === 'uploading'" class="status-uploading">上传中...</span>
            <span v-else-if="task.status === 'done'" class="status-done">已完成</span>
            <span v-else-if="task.status === 'cancelled'" class="status-cancelled">已取消</span>
            <span v-else class="status-error" :title="task.errorMsg || '上传失败'">上传失败</span>
            <el-button link size="small" type="danger" @click="clearItem(task)">清除</el-button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.upload-panel {
  position: fixed;
  right: 16px;
  bottom: 16px;
  width: 420px;
  max-height: 400px;
  background: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 16px rgba(0, 0, 0, 0.12);
  z-index: 2000;
  display: flex;
  flex-direction: column;
  transition: max-height 0.2s;
}

.upload-panel.collapsed {
  max-height: 44px;
}

.upload-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 10px 16px;
  background: #f5f7fa;
  color: #303133;
  border-bottom: 1px solid #ebeef5;
  border-radius: 8px 8px 0 0;
  cursor: pointer;
  flex-shrink: 0;
  user-select: none;
}

.upload-title {
  font-size: 14px;
  font-weight: 600;
}

.upload-badge {
  margin-left: 8px;
  font-size: 12px;
  background: #409eff;
  color: #fff;
  padding: 1px 6px;
  border-radius: 10px;
}

.upload-toggle {
  font-size: 12px;
  color: #909399;
}

.upload-body {
  flex: 1;
  display: flex;
  flex-direction: column;
  overflow: hidden;
}

.upload-summary {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 8px 16px;
  font-size: 12px;
  color: #909399;
  border-bottom: 1px solid #ebeef5;
  flex-shrink: 0;
}

.upload-list {
  flex: 1;
  overflow-y: auto;
  padding: 8px 16px;
}

.upload-item {
  padding: 8px 0;
  border-bottom: 1px solid #f5f5f5;
}

.upload-item:last-child {
  border-bottom: none;
}

.upload-item-info {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 4px;
}

.upload-item-name {
  font-size: 13px;
  color: #303133;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  flex: 1;
  margin-right: 8px;
}

.upload-item-size {
  font-size: 12px;
  color: #909399;
  flex-shrink: 0;
}

.upload-item-bar {
  margin: 2px 0;
}

.upload-item-status {
  display: flex;
  align-items: center;
  justify-content: space-between;
  font-size: 12px;
  margin-top: 2px;
}

.status-waiting { color: #909399; }
.status-uploading { color: #409eff; }
.status-done { color: #67c23a; }
.status-error { color: #f56c6c; }
.status-cancelled { color: #e6a23c; }
</style>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import BaseDataTable from '@/components/BaseDataTable.vue'
import { loadResourceTree, loadResourceList, addResource, updateResource, deleteResource, sortResources, moveResource } from '@/api/resource'
import folderIcon from '@/assets/folder.png'
import videoIcon from '@/assets/video.png'
import wordIcon from '@/assets/word.png'
import pdfIcon from '@/assets/pdf.png'
import pptIcon from '@/assets/ppt1.png'
import excelIcon from '@/assets/excel.png'
import zipIcon from '@/assets/zip.png'
import txtIcon from '@/assets/txt.png'
import othersIcon from '@/assets/others.png'
import genericFileIcon from '@/assets/ic_file2.png'
import moreIcon from '@/assets/icon/more.svg'

defineOptions({ name: 'Resource' })

const resourceTypeOptions = [
  { value: null, label: '全部类型' },
  { value: 1, label: '视频' },
  { value: 2, label: '图片' },
  { value: 3, label: '文档' },
  { value: 4, label: '压缩包' },
  { value: 5, label: '其他' }
]

const resourceTypeMap = { 1: '视频', 2: '图片', 3: '文档', 4: '压缩包', 5: '其他' }
const statusMap = { 1: '上传中', 2: '转码中', 3: '上传成功', 4: '转码失败', 5: '上传失败' }

// ==================== Data ====================
const allDirs = ref([])
const tableItemsRaw = ref([])
const loading = ref(false)

// ==================== Search ====================
const searchFormRef = ref(null)
const searchForm = ref({
  resourceName: '',
  resourceType: null
})

function handleSearch() {
  pagination.value.pageNo = 1
}

function handleReset() {
  searchForm.value = { resourceName: '', resourceType: null }
  pagination.value.pageNo = 1
}

// ==================== API ====================
async function fetchTree() {
  try {
    const res = await loadResourceTree()
    if (res.status === 'success') {
      allDirs.value = res.data || []
    }
  } catch { /* handled by interceptor */ }
}

async function fetchList(parentId) {
  loading.value = true
  try {
    const res = await loadResourceList(parentId)
    if (res.status === 'success') {
      tableItemsRaw.value = res.data || []
    }
  } catch { /* handled by interceptor */ }
  finally {
    loading.value = false
  }
}

// ==================== Directory Tree ====================
const currentDirId = ref('0')

function buildTree(parentId) {
  return allDirs.value
    .filter(item => item.parentId === parentId)
    .sort((a, b) => (a.sortOrder || 0) - (b.sortOrder || 0))
    .map(dir => ({
      id: dir.resourceId,
      label: dir.resourceName,
      parentId: dir.parentId,
      children: buildTree(dir.resourceId)
    }))
}

const treeData = computed(() => buildTree('0'))

function handleNodeClick(data) {
  if (data.id === currentDirId.value) return
  currentDirId.value = data.id
  pagination.value.pageNo = 1
  fetchList(data.id)
}

// ==================== Tree Drag ====================
function allowDrag() {
  return true
}

function allowDrop(draggingNode, dropNode, type) {
  if (type === 'inner') return false
  return draggingNode.data.parentId === dropNode.data.parentId
}

async function handleTreeDrop(draggingNode, dropNode, dropType) {
  const parentId = draggingNode.data.parentId
  const siblings = allDirs.value
    .filter(d => d.parentId === parentId)
    .sort((a, b) => (a.sortOrder || 0) - (b.sortOrder || 0))

  const dragId = draggingNode.data.id
  const dropId = dropNode.data.id
  const newList = [...siblings]

  const dragIdx = newList.findIndex(d => d.resourceId === dragId)
  let dropIdx = newList.findIndex(d => d.resourceId === dropId)
  if (dragIdx === -1 || dropIdx === -1) return

  if (dropType === 'after') dropIdx++
  if (dragIdx < dropIdx) dropIdx--

  const moved = newList.splice(dragIdx, 1)[0]
  newList.splice(dropIdx, 0, moved)

  try {
    await sortResources(newList.map(d => d.resourceId))
    await fetchTree()
  } catch {
    ElMessage.error('排序保存失败')
  }
}

// ==================== Tree Node Actions ====================
function handleTreeAction(command, data) {
  switch (command) {
    case 'add': handleAddSubDir(data); break
    case 'rename': handleRenameDir(data); break
    case 'delete': handleDeleteDir(data); break
  }
}

async function handleAddSubDir(parentData) {
  try {
    const { value } = await ElMessageBox.prompt('请输入目录名称', '新增子目录', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      inputValidator(val) { return val && val.trim() ? true : '目录名称不能为空' }
    })
    await addResource({ resourceName: value.trim(), nodeType: 1, parentId: parentData.id })
    ElMessage.success('目录创建成功')
    await fetchTree()
    if (currentDirId.value === parentData.id) await fetchList(currentDirId.value)
  } catch { /* cancelled */ }
}

async function handleRenameDir(data) {
  try {
    const { value } = await ElMessageBox.prompt('请输入新名称', '重命名目录', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      inputValue: data.label,
      inputValidator(val) { return val && val.trim() ? true : '目录名称不能为空' }
    })
    await updateResource({ resourceId: data.id, resourceName: value.trim() })
    ElMessage.success('重命名成功')
    await fetchTree()
    await fetchList(currentDirId.value)
  } catch { /* cancelled */ }
}

async function handleDeleteDir(data) {
  try {
    await ElMessageBox.confirm(`确定要删除目录「${data.label}」吗？`, '删除确认', {
      confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning'
    })
    await deleteResource(data.id)
    ElMessage.success('删除成功')
    if (currentDirId.value === data.id) currentDirId.value = data.parentId || '0'
    await fetchTree()
    await fetchList(currentDirId.value)
  } catch { /* cancelled or error */ }
}

// ==================== Breadcrumb ====================
function getDirPath(dirId) {
  const path = []
  let current = dirId
  while (current !== '0') {
    const dir = allDirs.value.find(item => item.resourceId === current)
    if (!dir) break
    path.unshift({ id: dir.resourceId, name: dir.resourceName })
    current = dir.parentId
  }
  return path
}

const breadcrumbPath = computed(() => getDirPath(currentDirId.value))

function handleBreadcrumbClick(dirId) {
  if (dirId === currentDirId.value) return
  currentDirId.value = dirId
  pagination.value.pageNo = 1
  fetchList(dirId)
}

// ==================== Table Data ====================
const tableDataRef = ref(null)

const tableItems = computed(() => {
  const dirs = tableItemsRaw.value.filter(item => {
    if (item.nodeType !== 1) return false
    if (searchForm.value.resourceName && !item.resourceName.includes(searchForm.value.resourceName)) return false
    return true
  }).sort((a, b) => (a.sortOrder || 0) - (b.sortOrder || 0))

  const files = tableItemsRaw.value.filter(item => {
    if (item.nodeType !== 2) return false
    if (searchForm.value.resourceName && !item.resourceName.includes(searchForm.value.resourceName) && !item.originalName.includes(searchForm.value.resourceName)) return false
    if (searchForm.value.resourceType && item.resourceType !== searchForm.value.resourceType) return false
    return true
  }).sort((a, b) => (a.sortOrder || 0) - (b.sortOrder || 0))

  return [...dirs, ...files]
})

const columns = [
  { prop: 'name', label: '文件名', minWidth: 240 },
  { prop: 'resourceType', label: '类型', width: 90, align: 'center' },
  { prop: 'fileSize', label: '大小', width: 100, align: 'center' },
  { prop: 'duration', label: '时长', width: 90, align: 'center' },
  { prop: 'status', label: '状态', width: 100, align: 'center' },
  { prop: 'updateTime', label: '更新时间', width: 170, align: 'center' },
  { prop: 'operation', label: '操作', width: 290, fixed: 'right', align: 'center' }
]

const pagination = ref({ pageNo: 1, pageSize: 10 })

const tableData = computed(() => {
  const list = tableItems.value
  const start = (pagination.value.pageNo - 1) * pagination.value.pageSize
  return {
    totalCount: list.length,
    pageSize: pagination.value.pageSize,
    pageNo: pagination.value.pageNo,
    pageTotal: Math.ceil(list.length / pagination.value.pageSize) || 1,
    list: list.slice(start, start + pagination.value.pageSize)
  }
})

function handlePageChange({ pageNo, pageSize }) {
  pagination.value.pageNo = pageNo
  pagination.value.pageSize = pageSize
}

// ==================== Batch Selection ====================
const selectedRows = ref([])

function handleSelectionChange(rows) {
  selectedRows.value = rows
}

async function handleBatchDelete() {
  if (selectedRows.value.length === 0) {
    ElMessage.warning('请先选择要删除的资源')
    return
  }
  // 检查目录非空
  for (const item of selectedRows.value) {
    if (item.nodeType === 1) {
      const children = allDirs.value.filter(d => d.parentId === item.resourceId)
      const files = tableItemsRaw.value.filter(f => f.nodeType === 2 && f.parentId === item.resourceId)
      if (children.length > 0 || files.some(f => f.parentId === item.resourceId)) {
        ElMessage.warning(`目录「${item.resourceName}」下存在子节点，无法删除`)
        return
      }
    }
  }
  try {
    await ElMessageBox.confirm(
      `确定要删除选中的 ${selectedRows.value.length} 项资源吗？`,
      '批量删除确认',
      { confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning' }
    )
  } catch { return }

  loading.value = true
  let success = 0
  for (const item of selectedRows.value) {
    try {
      await deleteResource(item.resourceId)
      success++
    } catch { /* skip failed */ }
  }
  loading.value = false
  ElMessage.success(`成功删除 ${success} 项`)
  selectedRows.value = []
  tableDataRef.value?.clearSelection()
  await fetchTree()
  await fetchList(currentDirId.value)
}

// ==================== Helpers ====================
function getItemIcon(item) {
  if (item.nodeType === 1) return folderIcon
  const ext = item.fileExt?.toLowerCase()
  if (item.resourceType === 1) return videoIcon
  if (ext === '.doc' || ext === '.docx') return wordIcon
  if (ext === '.pdf') return pdfIcon
  if (ext === '.ppt' || ext === '.pptx') return pptIcon
  if (ext === '.xls' || ext === '.xlsx') return excelIcon
  if (ext === '.zip' || ext === '.rar' || ext === '.7z') return zipIcon
  if (ext === '.txt') return txtIcon
  if (ext === '.exe') return exeIcon
  if (item.resourceType === 2) return genericFileIcon
  return othersIcon
}

function getDisplayName(item) {
  return item.nodeType === 1 ? item.resourceName : (item.originalName || item.resourceName)
}

function formatFileSize(bytes) {
  if (!bytes || bytes === 0) return '-'
  const units = ['B', 'KB', 'MB', 'GB', 'TB']
  let i = 0, size = bytes
  while (size >= 1024 && i < units.length - 1) { size /= 1024; i++ }
  return size.toFixed(i === 0 ? 0 : 2) + ' ' + units[i]
}

function formatDuration(seconds) {
  if (!seconds || seconds === 0) return '-'
  const h = Math.floor(seconds / 3600)
  const m = Math.floor((seconds % 3600) / 60)
  const s = seconds % 60
  if (h > 0) return `${h}:${String(m).padStart(2, '0')}:${String(s).padStart(2, '0')}`
  return `${m}:${String(s).padStart(2, '0')}`
}

function getStatusType(status) {
  if (status === 3) return 'success'
  if (status === 1 || status === 2) return 'warning'
  return 'danger'
}

// ==================== Move Dialog ====================
const moveDialogVisible = ref(false)
const moveItem = ref(null)
const moveTargetId = ref('0')

function collectDescendantIds(dirId) {
  const ids = [dirId]
  allDirs.value.filter(d => d.parentId === dirId).forEach(d => {
    ids.push(...collectDescendantIds(d.resourceId))
  })
  return ids
}

const moveExcludeIds = computed(() => {
  if (!moveItem.value || moveItem.value.nodeType !== 1) return []
  return collectDescendantIds(moveItem.value.resourceId)
})

function buildMoveTree(parentId) {
  return allDirs.value
    .filter(item => item.parentId === parentId && !moveExcludeIds.value.includes(item.resourceId))
    .sort((a, b) => (a.sortOrder || 0) - (b.sortOrder || 0))
    .map(dir => ({
      id: dir.resourceId,
      label: dir.resourceName,
      children: buildMoveTree(dir.resourceId)
    }))
}

const moveTreeData = computed(() => buildMoveTree('0'))

function handleMove(item) {
  moveItem.value = item
  moveTargetId.value = '0'
  moveDialogVisible.value = true
}

function handleMoveTargetClick(data) {
  moveTargetId.value = data.id
}

function getMoveTargetName() {
  if (moveTargetId.value === '0') return '根目录'
  const dir = allDirs.value.find(d => d.resourceId === moveTargetId.value)
  return dir ? dir.resourceName : '根目录'
}

async function handleMoveConfirm() {
  if (!moveItem.value) return
  if (moveTargetId.value === moveItem.value.parentId) {
    ElMessage.warning('目标位置与当前位置相同')
    return
  }
  try {
    await moveResource(moveItem.value.resourceId, moveTargetId.value)
    ElMessage.success('移动成功')
    moveDialogVisible.value = false
    await fetchTree()
    await fetchList(currentDirId.value)
  } catch { /* handled by interceptor */ }
}

// ==================== Top Bar Actions ====================
async function handleCreateDir() {
  try {
    const { value } = await ElMessageBox.prompt('请输入目录名称', '新建目录', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      inputValidator(val) { return val && val.trim() ? true : '目录名称不能为空' }
    })
    await addResource({ resourceName: value.trim(), nodeType: 1, parentId: currentDirId.value })
    ElMessage.success('目录创建成功')
    await fetchTree()
    await fetchList(currentDirId.value)
  } catch { /* cancelled */ }
}

function handleUpload() {
  ElMessage.info('上传资源功能开发中')
}

// ==================== Table Row Actions ====================
function handleTableRowClick(row) {
  if (row.nodeType === 1) {
    currentDirId.value = row.resourceId
    pagination.value.pageNo = 1
    fetchList(row.resourceId)
  }
}

async function handleRename(item) {
  const name = item.nodeType === 1 ? item.resourceName : (item.originalName || item.resourceName)
  try {
    const { value } = await ElMessageBox.prompt('请输入新名称', '重命名', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      inputValue: name,
      inputValidator(val) { return val && val.trim() ? true : '名称不能为空' }
    })
    await updateResource({ resourceId: item.resourceId, resourceName: value.trim() })
    ElMessage.success('重命名成功')
    if (item.nodeType === 1) await fetchTree()
    await fetchList(currentDirId.value)
  } catch { /* cancelled */ }
}

function handleReupload(file) {
  ElMessage.info('重新上传：' + file.originalName + '（功能开发中）')
}

function handleDownload(file) {
  ElMessage.info('下载：' + file.originalName + '（功能开发中）')
}

async function handleDeleteItem(item) {
  const name = item.nodeType === 1 ? item.resourceName : item.originalName
  try {
    await ElMessageBox.confirm(
      `确定要删除${item.nodeType === 1 ? '目录' : '文件'}「${name}」吗？`,
      '删除确认',
      { confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning' }
    )
  } catch { return }

  try {
    await deleteResource(item.resourceId)
    ElMessage.success('删除成功')
    if (item.nodeType === 1 && currentDirId.value === item.resourceId) {
      currentDirId.value = item.parentId || '0'
    }
    await fetchTree()
    await fetchList(currentDirId.value)
  } catch { /* handled by interceptor */ }
}

// ==================== Init ====================
onMounted(async () => {
  await fetchTree()
  await fetchList('0')
  if (searchFormRef.value?.$el) {
    searchFormRef.value.$el.addEventListener('submit', (e) => e.preventDefault())
  }
})
</script>

<template>
  <div class="resource-page">
    <h2 class="page-title">资源管理</h2>

    <!-- 搜索区域 -->
    <div class="search-card">
      <el-form ref="searchFormRef" :model="searchForm" @submit.prevent>
        <el-row :gutter="16">
          <el-col :span="6">
            <el-form-item label="资源名称">
              <el-input v-model="searchForm.resourceName" placeholder="请输入" clearable @keyup.enter="handleSearch" />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="文件类型">
              <el-select v-model="searchForm.resourceType" placeholder="全部类型" clearable filterable style="width: 100%">
                <el-option v-for="opt in resourceTypeOptions" :key="opt.value" :label="opt.label" :value="opt.value" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item>
              <el-button type="primary" @click="handleSearch">搜索</el-button>
              <el-button @click="handleReset">重置</el-button>
            </el-form-item>
          </el-col>
          <el-col :span="6" class="search-actions-col">
            <el-button type="primary" @click="handleCreateDir">新建目录</el-button>
            <el-button type="danger" @click="handleBatchDelete">批量删除</el-button>
            <el-button type="success" @click="handleUpload">上传资源</el-button>
          </el-col>
        </el-row>
      </el-form>
    </div>

    <!-- 主内容区 -->
    <div class="main-content">
      <!-- 左侧目录树 -->
      <div class="tree-panel">
        <div class="panel-title">目录结构</div>
        <div class="tree-body">
          <el-tree
            :data="treeData"
            node-key="id"
            default-expand-all
            :expand-on-click-node="true"
            highlight-current
            draggable
            :allow-drag="allowDrag"
            :allow-drop="allowDrop"
            :props="{ children: 'children', label: 'label' }"
            @node-click="handleNodeClick"
            @node-drop="handleTreeDrop"
          >
            <template #default="{ data }">
              <span class="tree-node">
                <img :src="folderIcon" class="tree-node-icon" alt="" />
                <span class="tree-node-label">{{ data.label }}</span>
                <el-dropdown trigger="click" class="tree-node-dropdown" @command="(cmd) => handleTreeAction(cmd, data)">
                  <img :src="moreIcon" class="tree-node-more" alt="" @click.stop />
                  <template #dropdown>
                    <el-dropdown-menu>
                      <el-dropdown-item command="add">新增子目录</el-dropdown-item>
                      <el-dropdown-item command="rename">重命名</el-dropdown-item>
                      <el-dropdown-item command="delete" divided>删除</el-dropdown-item>
                    </el-dropdown-menu>
                  </template>
                </el-dropdown>
              </span>
            </template>
          </el-tree>
        </div>
      </div>

      <!-- 右侧文件列表 -->
      <div class="file-panel">
        <div class="file-panel-header">
          <div class="breadcrumb">
            <span class="breadcrumb-item" :class="{ active: currentDirId === '0' }" @click="handleBreadcrumbClick('0')">根目录</span>
            <template v-for="item in breadcrumbPath" :key="item.id">
              <span class="breadcrumb-sep">/</span>
              <span class="breadcrumb-item" :class="{ active: item.id === currentDirId }" @click="handleBreadcrumbClick(item.id)">{{ item.name }}</span>
            </template>
          </div>
        </div>

        <div class="file-panel-body">
          <div class="file-table-wrap">
            <BaseDataTable
              ref="tableDataRef"
              :columns="columns"
              :data="tableData"
              :show-selection="true"
              :show-pagination="tableItems.length > 10"
              :loading="loading"
              row-key="resourceId"
              @selection-change="handleSelectionChange"
            >
              <template #name="{ row }">
                <div class="name-cell" :class="{ 'is-dir': row.nodeType === 1 }" @click="handleTableRowClick(row)">
                  <img :src="getItemIcon(row)" class="name-icon" alt="" />
                  <span class="name-text" :title="getDisplayName(row)">{{ getDisplayName(row) }}</span>
                </div>
              </template>
              <template #resourceType="{ row }">
                <template v-if="row.nodeType === 1">-</template>
                <el-tag v-else size="small" type="info">{{ resourceTypeMap[row.resourceType] || '未知' }}</el-tag>
              </template>
              <template #fileSize="{ row }">
                <template v-if="row.nodeType === 1">-</template>
                <template v-else>{{ formatFileSize(row.fileSize) }}</template>
              </template>
              <template #duration="{ row }">
                <template v-if="row.nodeType === 1">-</template>
                <template v-else>{{ formatDuration(row.duration) }}</template>
              </template>
              <template #status="{ row }">
                <template v-if="row.nodeType === 1">-</template>
                <el-tag v-else size="small" :type="getStatusType(row.status)">{{ statusMap[row.status] || '未知' }}</el-tag>
              </template>
              <template #updateTime="{ row }">
                {{ row.updateTime }}
              </template>
              <template #operation="{ row }">
                <el-button type="primary" link size="small" @click="handleMove(row)">移动</el-button>
                <el-button type="primary" link size="small" @click="handleRename(row)">重命名</el-button>
                <template v-if="row.nodeType === 2">
                  <el-button type="warning" link size="small" @click="handleReupload(row)">重新上传</el-button>
                  <el-button type="primary" link size="small" @click="handleDownload(row)">下载</el-button>
                </template>
                <el-button type="danger" link size="small" @click="handleDeleteItem(row)">删除</el-button>
              </template>
            </BaseDataTable>
          </div>

          <div v-if="tableItems.length === 0" class="empty-state">
            <span>当前目录为空</span>
          </div>
        </div>
      </div>
    </div>

    <!-- 移动弹窗 -->
    <el-dialog v-model="moveDialogVisible" title="移动到" width="480px" :close-on-click-modal="false" @keydown.enter.prevent>
      <div class="move-dialog-body">
        <div class="move-current">
          <span class="move-label">当前资源：</span>
          <span class="move-name">{{ moveItem ? getDisplayName(moveItem) : '' }}</span>
        </div>
        <div class="move-current">
          <span class="move-label">目标位置：</span>
          <span class="move-name move-target">{{ getMoveTargetName() }}</span>
        </div>
        <div class="move-tree-wrap">
          <el-tree
            :data="moveTreeData"
            node-key="id"
            default-expand-all
            highlight-current
            :props="{ children: 'children', label: 'label' }"
            @node-click="handleMoveTargetClick"
          >
            <template #default="{ data }">
              <span class="move-tree-node">
                <img :src="folderIcon" class="tree-node-icon" alt="" />
                <span>{{ data.label }}</span>
              </span>
            </template>
          </el-tree>
        </div>
      </div>
      <template #footer>
        <el-button @click="moveDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleMoveConfirm">确定移动</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<style scoped>
.resource-page {
  height: 100%;
  display: flex;
  flex-direction: column;
  padding: 16px;
  gap: 16px;
}

.page-title {
  font-size: 20px;
  color: #303133;
  flex-shrink: 0;
}

/* ====== 搜索区域 ====== */
.search-card {
  flex-shrink: 0;
  background: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.06);
  padding: 20px 20px 4px;
}

.search-actions-col {
  display: flex;
  justify-content: flex-end;
  gap: 8px;
}

/* ====== 主内容区 ====== */
.main-content {
  flex: 1;
  min-height: 0;
  display: flex;
  gap: 16px;
}

/* ====== 左侧目录树 ====== */
.tree-panel {
  width: 260px;
  flex-shrink: 0;
  background: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.06);
  display: flex;
  flex-direction: column;
  overflow: hidden;
}

.panel-title {
  font-size: 15px;
  font-weight: 600;
  color: #303133;
  padding: 16px 20px 12px;
  border-bottom: 1px solid #ebeef5;
  flex-shrink: 0;
}

.tree-body {
  flex: 1;
  overflow-y: auto;
  padding: 8px 0;
}

.tree-node {
  display: flex;
  align-items: center;
  gap: 6px;
  flex: 1;
  min-width: 0;
}

.tree-node-icon {
  width: 18px;
  height: 18px;
  flex-shrink: 0;
}

.tree-node-label {
  flex: 1;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  font-size: 14px;
}

.tree-node-dropdown {
  flex-shrink: 0;
  opacity: 0;
  transition: opacity 0.15s;
}

.tree-node:hover .tree-node-dropdown {
  opacity: 1;
}

.tree-node-more {
  width: 16px;
  height: 16px;
  cursor: pointer;
  vertical-align: middle;
}

/* ====== 右侧文件列表 ====== */
.file-panel {
  flex: 1;
  min-width: 0;
  background: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.06);
  display: flex;
  flex-direction: column;
  overflow: hidden;
}

.file-panel-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 14px 20px;
  border-bottom: 1px solid #ebeef5;
  flex-shrink: 0;
}

.breadcrumb {
  display: flex;
  align-items: center;
  gap: 4px;
  font-size: 14px;
}

.breadcrumb-item {
  color: #409eff;
  cursor: pointer;
  padding: 2px 4px;
  border-radius: 4px;
  transition: background 0.2s;
}

.breadcrumb-item:hover {
  background: #ecf5ff;
}

.breadcrumb-item.active {
  color: #303133;
  cursor: default;
  font-weight: 600;
}

.breadcrumb-sep {
  color: #c0c4cc;
  margin: 0 2px;
}

.file-panel-body {
  flex: 1;
  min-height: 0;
  display: flex;
  flex-direction: column;
  overflow: hidden;
}

.file-table-wrap {
  flex: 1;
  min-height: 0;
  padding: 12px 12px 0;
}

.name-cell {
  display: flex;
  align-items: center;
  gap: 8px;
}

.name-cell.is-dir {
  cursor: pointer;
  color: #409eff;
}

.name-cell.is-dir:hover {
  color: #66b1ff;
}

.name-icon {
  width: 24px;
  height: 24px;
  flex-shrink: 0;
  object-fit: contain;
}

.name-text {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.empty-state {
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 80px 0;
  font-size: 14px;
  color: #c0c4cc;
}

/* ====== 移动弹窗 ====== */
.move-dialog-body {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.move-current {
  display: flex;
  align-items: center;
  font-size: 14px;
}

.move-label {
  color: #909399;
  flex-shrink: 0;
}

.move-name {
  color: #303133;
}

.move-name.move-target {
  color: #409eff;
  font-weight: 600;
}

.move-tree-wrap {
  border: 1px solid #dcdfe6;
  border-radius: 6px;
  padding: 8px;
  max-height: 320px;
  overflow-y: auto;
}

.move-tree-node {
  display: flex;
  align-items: center;
  gap: 6px;
  font-size: 14px;
}
</style>

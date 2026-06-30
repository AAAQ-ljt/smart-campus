<script setup>
import { ref, computed, onMounted, nextTick } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import BaseDataTable from '@/components/BaseDataTable.vue'
import PaperEditDrawer from './components/PaperEditDrawer.vue'
import { loadPaperList, deletePaper } from '@/api/paper'

defineOptions({ name: 'ExamPaper' })

const paperTypeMap = { 1: '课后习题', 2: '考试试卷' }

// ==================== Data ====================
const loading = ref(false)
const tableDataRef = ref(null)

// ==================== Search ====================
const searchForm = ref({
  paperTitleFuzzy: '',
  paperType: ''
})
const searchFormRef = ref(null)

function handleSearch() {
  pagination.value.pageNo = 1
  fetchList()
}

function handleReset() {
  searchForm.value = { paperTitleFuzzy: '', paperType: '' }
  pagination.value.pageNo = 1
  fetchList()
}

// ==================== API ====================
const tableItems = ref([])

async function fetchList() {
  loading.value = true
  try {
    const query = { ...searchForm.value }
    query.pageNo = pagination.value.pageNo
    query.pageSize = pagination.value.pageSize
    if (!query.paperType) query.paperType = null
    const res = await loadPaperList(query)
    if (res.status === 'success' && res.data) {
      tableItems.value = res.data.list || []
      pagination.value.totalCount = res.data.totalCount || 0
    }
  } catch { /* handled */ }
  finally { loading.value = false }
}

// ==================== Pagination ====================
const pagination = ref({ pageNo: 1, pageSize: 15, totalCount: 0 })

function handlePageChange({ pageNo, pageSize }) {
  pagination.value.pageNo = pageNo
  pagination.value.pageSize = pageSize
  fetchList()
}

// ==================== Table ====================
const columns = [
  { prop: 'paperId', label: 'ID', width: 140 },
  { prop: 'paperTitle', label: '试卷名称', minWidth: 200 },
  { prop: 'paperType', label: '试卷类型', width: 100, align: 'center' },
  { prop: 'totalScore', label: '总分', width: 80, align: 'center' },
  { prop: 'duration', label: '时长(分)', width: 90, align: 'center' },
  { prop: 'status', label: '状态', width: 80, align: 'center' },
  { prop: 'createTime', label: '创建时间', width: 165, align: 'center' },
  { prop: 'operation', label: '操作', width: 170, fixed: 'right', align: 'center' }
]

const tableData = computed(() => ({
  totalCount: pagination.value.totalCount,
  pageSize: pagination.value.pageSize,
  pageNo: pagination.value.pageNo,
  pageTotal: Math.ceil(pagination.value.totalCount / pagination.value.pageSize) || 1,
  list: tableItems.value
}))

// ==================== Edit Drawer ====================
const drawerVisible = ref(false)
const drawerMode = ref('add')
const editPaperId = ref('')

function handleOpenAdd() {
  drawerMode.value = 'add'
  editPaperId.value = ''
  drawerVisible.value = true
}

function handleOpenEdit(row) {
  drawerMode.value = 'edit'
  editPaperId.value = row.paperId
  drawerVisible.value = true
}

function onSaved() {
  fetchList()
}

async function handleDelete(row) {
  try {
    await ElMessageBox.confirm('确定要删除该试卷吗？删除后试卷内的题目编排也会被清除。', '删除确认', {
      confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning'
    })
  } catch { return }
  try {
    const res = await deletePaper(row.paperId)
    if (res.status === 'success') { ElMessage.success('删除成功'); fetchList() }
  } catch { /* handled */ }
}

// ==================== Init ====================
onMounted(() => {
  fetchList()
  if (searchFormRef.value?.$el) {
    searchFormRef.value.$el.addEventListener('submit', e => e.preventDefault())
  }
})
</script>

<template>
  <div class="exam-paper-page">
    <h2 class="page-title">试卷管理</h2>

    <!-- 搜索区域 -->
    <div class="search-card">
      <el-form ref="searchFormRef" :model="searchForm" @submit.prevent>
        <el-row :gutter="16">
          <el-col :span="6">
            <el-form-item label="试卷名称">
              <el-input v-model="searchForm.paperTitleFuzzy" placeholder="请输入" clearable @keyup.enter="handleSearch" />
            </el-form-item>
          </el-col>
          <el-col :span="5">
            <el-form-item label="试卷类型">
              <el-select v-model="searchForm.paperType" placeholder="全部" clearable filterable style="width: 100%">
                <el-option label="课后习题" :value="1" />
                <el-option label="考试试卷" :value="2" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="4">
            <el-form-item>
              <el-button type="primary" @click="handleSearch">搜索</el-button>
              <el-button @click="handleReset">重置</el-button>
            </el-form-item>
          </el-col>
          <el-col :span="9" class="search-actions-col">
            <el-button type="primary" @click="handleOpenAdd">新增试卷</el-button>
          </el-col>
        </el-row>
      </el-form>
    </div>

    <!-- 表格 -->
    <div class="table-card">
      <BaseDataTable
        ref="tableDataRef"
        :columns="columns"
        :data="tableData"
        :show-pagination="true"
        :loading="loading"
        row-key="paperId"
        @page-change="handlePageChange"
      >
        <template #paperTitle="{ row }">
          <span class="title-text" :title="row.paperTitle">{{ row.paperTitle }}</span>
        </template>
        <template #paperType="{ row }">
          <el-tag size="small" :type="row.paperType === 1 ? '' : 'warning'">
            {{ paperTypeMap[row.paperType] || '未知' }}
          </el-tag>
        </template>
        <template #totalScore="{ row }">{{ row.totalScore || 0 }}</template>
        <template #duration="{ row }">{{ row.paperType === 2 ? (row.duration || 0) : '-' }}</template>
        <template #status="{ row }">
          <el-tag size="small" :type="row.status === 1 ? 'success' : 'info'">
            {{ row.status === 1 ? '启用' : '禁用' }}
          </el-tag>
        </template>
        <template #operation="{ row }">
          <el-button type="primary" link size="small" @click="handleOpenEdit(row)">编辑</el-button>
          <el-button type="danger" link size="small" @click="handleDelete(row)">删除</el-button>
        </template>
      </BaseDataTable>
    </div>

    <!-- 编辑抽屉 -->
    <PaperEditDrawer
      v-model:visible="drawerVisible"
      :mode="drawerMode"
      :paper-id="editPaperId"
      @saved="onSaved"
    />
  </div>
</template>

<style scoped>
.exam-paper-page {
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
}

.table-card {
  flex: 1;
  min-height: 0;
  background: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.06);
  padding: 12px;
  overflow: hidden;
}

.title-text {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  display: inline-block;
  max-width: 100%;
}
</style>

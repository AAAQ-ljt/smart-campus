<script setup>
import { ref, computed, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import BaseDataTable from '@/components/BaseDataTable.vue'
import BaseDialog from '@/components/BaseDialog.vue'
import { loadFacultyInfoList, addFacultyInfo, updateFacultyInfo, deleteFacultyInfo, sortFacultyInfo } from '@/api/faculty'

defineOptions({ name: 'Department' })

const columns = [
  { prop: 'facultyName', label: '院系名称', minWidth: 200 },
  { prop: 'facultyCode', label: '院系编码', width: 140, align: 'center' },
  { prop: 'description', label: '院系描述', minWidth: 220 },
  { prop: 'createTime', label: '创建时间', width: 180, align: 'center' },
  { prop: 'operation', label: '操作', width: 150, fixed: 'right', align: 'center' }
]

const searchFormRef = ref(null)

const searchForm = ref({
  name: '',
  code: ''
})

const allData = ref([])
const loading = ref(false)

const tableData = computed(() => ({
  totalCount: allData.value.length,
  pageSize: allData.value.length,
  pageNo: 1,
  pageTotal: 1,
  list: allData.value
}))

async function fetchList(params) {
  loading.value = true
  try {
    const query = {}
    if (params) {
      if (params.name) query.facultyNameFuzzy = params.name
      if (params.code) query.facultyCodeFuzzy = params.code
    }
    const res = await loadFacultyInfoList(query)
    if (res.status === 'success') {
      allData.value = res.data || []
    }
  } finally {
    loading.value = false
  }
}

function handleSearch() {
  fetchList({ name: searchForm.value.name, code: searchForm.value.code })
}

function handleReset() {
  searchForm.value = { name: '', code: '' }
  fetchList()
}

const dialogVisible = ref(false)
const isEdit = ref(false)
const currentRow = ref(null)

const formData = ref({
  facultyName: '',
  facultyCode: '',
  description: ''
})

function handleAdd() {
  isEdit.value = false
  formData.value = { facultyName: '', facultyCode: '', description: '' }
  dialogVisible.value = true
}

function handleEdit(row) {
  isEdit.value = true
  currentRow.value = row
  formData.value = {
    facultyName: row.facultyName || '',
    facultyCode: row.facultyCode || '',
    description: row.description || ''
  }
  dialogVisible.value = true
}

async function handleDialogConfirm() {
  if (!formData.value.facultyName || !formData.value.facultyCode) {
    ElMessage.warning('请填写院系名称和院系编码')
    return
  }
  loading.value = true
  try {
    if (isEdit.value) {
      await updateFacultyInfo({
        facultyId: currentRow.value.facultyId,
        ...formData.value
      })
      ElMessage.success('编辑成功')
    } else {
      await addFacultyInfo(formData.value)
      ElMessage.success('新增成功')
    }
    dialogVisible.value = false
    await fetchList()
  } finally {
    loading.value = false
  }
}

function handleDelete(row) {
  ElMessageBox.confirm(`确定要删除院系「${row.facultyName}」吗？`, '删除确认', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    loading.value = true
    try {
      await deleteFacultyInfo(row.facultyId)
      ElMessage.success('删除成功')
      await fetchList()
    } finally {
      loading.value = false
    }
  }).catch(() => {})
}

async function handleRowDrag(list) {
  allData.value = list
  const ids = list.map((item) => item.facultyId)
  try {
    await sortFacultyInfo(ids)
  } catch {
    ElMessage.error('排序保存失败')
    await fetchList()
  }
}

function handleDrop(row, targetRow, position) {
  const list = [...allData.value]
  const fromIndex = list.findIndex((item) => item.facultyId === row.facultyId)
  let toIndex = list.findIndex((item) => item.facultyId === targetRow.facultyId)
  if (fromIndex === -1 || toIndex === -1) return
  if (position === 'after') toIndex++
  if (fromIndex < toIndex) toIndex--
  const moved = list.splice(fromIndex, 1)[0]
  list.splice(toIndex, 0, moved)
  allData.value = list
  handleRowDrag(list)
}

onMounted(() => {
  fetchList()
  if (searchFormRef.value?.$el) {
    searchFormRef.value.$el.addEventListener('submit', (e) => e.preventDefault())
  }
})
</script>

<template>
  <div class="department-page">
    <h2 class="page-title">院系管理</h2>

    <!-- 搜索卡片 -->
    <div class="search-card">
      <el-form ref="searchFormRef" :model="searchForm">
        <el-row :gutter="16">
          <el-col :span="6">
            <el-form-item label="院系名称">
              <el-input v-model="searchForm.name" placeholder="请输入" clearable @keyup.enter="handleSearch" />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="院系编码">
              <el-input v-model="searchForm.code" placeholder="请输入" clearable @keyup.enter="handleSearch" />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item>
              <el-button type="primary" @click="handleSearch">搜索</el-button>
              <el-button @click="handleReset">重置</el-button>
            </el-form-item>
          </el-col>
          <el-col :span="6" class="search-add-col">
            <el-button type="primary" @click="handleAdd">新增</el-button>
          </el-col>
        </el-row>
      </el-form>
    </div>

    <!-- 表格卡片 -->
    <div class="table-card">
      <BaseDataTable
        :columns="columns"
        :data="tableData"
        :show-pagination="false"
        :loading="loading"
        draggable
        row-key="facultyId"
        @row-drag="handleRowDrag"
      >
        <template #operation="{ row }">
          <el-button type="primary" link size="small" @click="handleEdit(row)">编辑</el-button>
          <el-button type="danger" link size="small" @click="handleDelete(row)">删除</el-button>
        </template>
      </BaseDataTable>
    </div>

    <!-- 新增/编辑弹窗 -->
    <BaseDialog v-model="dialogVisible" :title="isEdit ? '编辑院系' : '新增院系'" width="520px" @confirm="handleDialogConfirm">
      <el-form label-width="100px" @submit.prevent>
        <el-form-item label="院系名称" required>
          <el-input v-model="formData.facultyName" placeholder="请输入院系名称" />
        </el-form-item>
        <el-form-item label="院系编码" required>
          <el-input v-model="formData.facultyCode" placeholder="请输入院系编码" />
        </el-form-item>
        <el-form-item label="院系描述">
          <el-input v-model="formData.description" type="textarea" :rows="3" placeholder="请输入院系描述" />
        </el-form-item>
      </el-form>
    </BaseDialog>
  </div>
</template>

<style scoped>
.department-page {
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
  padding: 20px;
}

.search-add-col {
  display: flex;
  justify-content: flex-end;
}

.table-card {
  flex: 1;
  min-height: 0;
  background: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.06);
  padding: 20px;
  display: flex;
  flex-direction: column;
}

.table-card :deep(.base-data-table) {
  flex: 1;
}
</style>

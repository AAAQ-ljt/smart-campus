<script setup>
import { ref, computed, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import BaseDataTable from '@/components/BaseDataTable.vue'
import BaseDrawer from '@/components/BaseDrawer.vue'
import { loadMajorInfoList, addMajorInfo, updateMajorInfo, deleteMajorInfo, sortMajorInfo } from '@/api/major'
import { loadFacultyInfoList } from '@/api/faculty'

defineOptions({ name: 'Major' })

const columns = [
  { prop: 'majorName', label: '专业名称', minWidth: 180 },
  { prop: 'majorCode', label: '专业编码', width: 140, align: 'center' },
  { prop: 'facultyName', label: '所属院系', minWidth: 180 },
  { prop: 'description', label: '专业描述', minWidth: 200 },
  { prop: 'createTime', label: '创建时间', width: 180, align: 'center' },
  { prop: 'operation', label: '操作', width: 150, fixed: 'right', align: 'center' }
]

const searchFormRef = ref(null)
const searchForm = ref({
  name: '',
  facultyId: ''
})

const allData = ref([])
const facultyList = ref([])
const loading = ref(false)

const tableData = computed(() => ({
  totalCount: allData.value.length,
  pageSize: allData.value.length,
  pageNo: 1,
  pageTotal: 1,
  list: allData.value
}))

async function fetchFaculties() {
  try {
    const res = await loadFacultyInfoList()
    if (res.status === 'success') {
      facultyList.value = res.data || []
    }
  } catch {}
}

async function fetchList(params) {
  loading.value = true
  try {
    const query = {}
    if (params) {
      if (params.name) query.majorNameFuzzy = params.name
      if (params.facultyId) query.facultyId = params.facultyId
    }
    const res = await loadMajorInfoList(query)
    if (res.status === 'success') {
      allData.value = res.data || []
    }
  } finally {
    loading.value = false
  }
}

function handleSearch() {
  fetchList({
    name: searchForm.value.name,
    facultyId: searchForm.value.facultyId
  })
}

function handleReset() {
  searchForm.value = { name: '', facultyId: '' }
  fetchList()
}

const drawerVisible = ref(false)
const isEdit = ref(false)
const currentRow = ref(null)
const formData = ref({
  majorName: '',
  majorCode: '',
  facultyId: '',
  description: ''
})

function handleAdd() {
  isEdit.value = false
  formData.value = { majorName: '', majorCode: '', facultyId: '', description: '' }
  drawerVisible.value = true
}

function handleEdit(row) {
  isEdit.value = true
  currentRow.value = row
  formData.value = {
    majorName: row.majorName || '',
    majorCode: row.majorCode || '',
    facultyId: row.facultyId || '',
    description: row.description || ''
  }
  drawerVisible.value = true
}

async function handleDrawerConfirm() {
  if (!formData.value.majorName || !formData.value.majorCode || !formData.value.facultyId) {
    ElMessage.warning('请填写专业名称、专业编码并选择所属院系')
    return
  }
  loading.value = true
  try {
    if (isEdit.value) {
      await updateMajorInfo({
        majorId: currentRow.value.majorId,
        ...formData.value
      })
      ElMessage.success('编辑成功')
    } else {
      await addMajorInfo(formData.value)
      ElMessage.success('新增成功')
    }
    drawerVisible.value = false
    await fetchList()
  } finally {
    loading.value = false
  }
}

function handleDelete(row) {
  ElMessageBox.confirm(`确定要删除专业「${row.majorName}」吗？`, '删除确认', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    loading.value = true
    try {
      await deleteMajorInfo(row.majorId)
      ElMessage.success('删除成功')
      await fetchList()
    } finally {
      loading.value = false
    }
  }).catch(() => {})
}

async function handleRowDrag(list) {
  allData.value = list
  const ids = list.map((item) => item.majorId)
  try {
    await sortMajorInfo(ids)
  } catch {
    ElMessage.error('排序保存失败')
    await fetchList()
  }
}

onMounted(async () => {
  await fetchFaculties()
  await fetchList()
  if (searchFormRef.value?.$el) {
    searchFormRef.value.$el.addEventListener('submit', (e) => e.preventDefault())
  }
})
</script>

<template>
  <div class="major-page">
    <h2 class="page-title">专业管理</h2>

    <!-- 搜索卡片 -->
    <div class="search-card">
      <el-form ref="searchFormRef" :model="searchForm">
        <el-row :gutter="16">
          <el-col :span="6">
            <el-form-item label="专业名称">
              <el-input v-model="searchForm.name" placeholder="请输入" clearable @keyup.enter="handleSearch" />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="所属院系">
              <el-select v-model="searchForm.facultyId" placeholder="请选择院系" clearable style="width: 100%">
                <el-option v-for="f in facultyList" :key="f.facultyId" :label="f.facultyName" :value="f.facultyId" />
              </el-select>
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
        row-key="majorId"
        @row-drag="handleRowDrag"
      >
        <template #operation="{ row }">
          <el-button type="primary" link size="small" @click="handleEdit(row)">编辑</el-button>
          <el-button type="danger" link size="small" @click="handleDelete(row)">删除</el-button>
        </template>
      </BaseDataTable>
    </div>

    <!-- 新增/编辑抽屉 -->
    <BaseDrawer v-model="drawerVisible" :title="isEdit ? '编辑专业' : '新增专业'" size="520px" @confirm="handleDrawerConfirm">
      <el-form label-width="80px" @submit.prevent>
        <el-form-item label="专业名称" required>
          <el-input v-model="formData.majorName" placeholder="请输入专业名称" />
        </el-form-item>
        <el-form-item label="专业编码" required>
          <el-input v-model="formData.majorCode" placeholder="请输入专业编码" />
        </el-form-item>
        <el-form-item label="所属院系" required>
          <el-select v-model="formData.facultyId" style="width: 100%" placeholder="请选择院系">
            <el-option v-for="f in facultyList" :key="f.facultyId" :label="f.facultyName" :value="f.facultyId" />
          </el-select>
        </el-form-item>
        <el-form-item label="专业描述">
          <el-input v-model="formData.description" type="textarea" :rows="3" placeholder="请输入专业描述" />
        </el-form-item>
      </el-form>
    </BaseDrawer>
  </div>
</template>

<style scoped>
.major-page {
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

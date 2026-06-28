<script setup>
import { ref, computed, onMounted, reactive } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import BaseDataTable from '@/components/BaseDataTable.vue'
import BaseDrawer from '@/components/BaseDrawer.vue'
import { loadClassInfoList, addClassInfo, updateClassInfo, deleteClassInfo, sortClassInfo } from '@/api/class'
import { loadFacultyInfoList } from '@/api/faculty'
import { loadMajorInfoList } from '@/api/major'

defineOptions({ name: 'ClassManage' })

const columns = [
  { prop: 'className', label: '班级名称', minWidth: 180 },
  { prop: 'classCode', label: '班级编码', width: 140, align: 'center' },
  { prop: 'majorName', label: '所属专业', minWidth: 160 },
  { prop: 'facultyName', label: '所属院系', minWidth: 160 },
  { prop: 'grade', label: '年级', width: 100, align: 'center' },
  { prop: 'description', label: '班级描述', minWidth: 180 },
  { prop: 'createTime', label: '创建时间', width: 180, align: 'center' },
  { prop: 'operation', label: '操作', width: 150, fixed: 'right', align: 'center' }
]

const searchFormRef = ref(null)
const searchForm = ref({
  name: '',
  facultyId: '',
  majorId: '',
  grade: ''
})

const facultyList = ref([])
const allMajorList = ref([])
const loading = ref(false)

const pagination = reactive({
  totalCount: 0,
  pageSize: 15,
  pageNo: 1,
  pageTotal: 1,
  list: []
})

const searchMajorList = computed(() => {
  if (!searchForm.value.facultyId) return allMajorList.value
  return allMajorList.value.filter((m) => m.facultyId === searchForm.value.facultyId)
})

async function fetchFaculties() {
  try {
    const res = await loadFacultyInfoList()
    if (res.status === 'success') {
      facultyList.value = res.data || []
    }
  } catch {}
}

async function fetchAllMajors() {
  try {
    const res = await loadMajorInfoList()
    if (res.status === 'success') {
      allMajorList.value = res.data || []
    }
  } catch {}
}

async function fetchList(params) {
  loading.value = true
  try {
    const query = { pageNo: pagination.pageNo, pageSize: pagination.pageSize }
    if (params) {
      if (params.name) query.classNameFuzzy = params.name
      if (params.facultyId) query.facultyId = params.facultyId
      if (params.majorId) query.majorId = params.majorId
      if (params.grade) query.gradeFuzzy = params.grade
    }
    const res = await loadClassInfoList(query)
    if (res.status === 'success' && res.data) {
      pagination.totalCount = res.data.totalCount || 0
      pagination.pageSize = res.data.pageSize || 15
      pagination.pageNo = res.data.pageNo || 1
      pagination.pageTotal = res.data.pageTotal || 1
      pagination.list = res.data.list || []
    }
  } finally {
    loading.value = false
  }
}

function handleSearchFacultyChange() {
  searchForm.value.majorId = ''
}

function handleSearch() {
  pagination.pageNo = 1
  fetchList({
    name: searchForm.value.name,
    facultyId: searchForm.value.facultyId,
    majorId: searchForm.value.majorId,
    grade: searchForm.value.grade
  })
}

function handleReset() {
  searchForm.value = { name: '', facultyId: '', majorId: '', grade: '' }
  pagination.pageNo = 1
  fetchList()
}

function handlePageChange({ pageNo, pageSize }) {
  pagination.pageNo = pageNo
  pagination.pageSize = pageSize
  fetchList({
    name: searchForm.value.name,
    facultyId: searchForm.value.facultyId,
    majorId: searchForm.value.majorId,
    grade: searchForm.value.grade
  })
}

const drawerVisible = ref(false)
const isEdit = ref(false)
const currentRow = ref(null)
const formData = ref({
  className: '',
  classCode: '',
  facultyId: '',
  majorId: '',
  grade: '',
  description: ''
})

const formMajorList = computed(() => {
  if (!formData.value.facultyId) return allMajorList.value
  return allMajorList.value.filter((m) => m.facultyId === formData.value.facultyId)
})

function handleFormFacultyChange() {
  formData.value.majorId = ''
}

function handleAdd() {
  isEdit.value = false
  formData.value = { className: '', classCode: '', facultyId: '', majorId: '', grade: '', description: '' }
  drawerVisible.value = true
}

function handleEdit(row) {
  isEdit.value = true
  currentRow.value = row
  const major = allMajorList.value.find((m) => m.majorId === row.majorId)
  formData.value = {
    className: row.className || '',
    classCode: row.classCode || '',
    facultyId: major ? major.facultyId : '',
    majorId: row.majorId || '',
    grade: row.grade || '',
    description: row.description || ''
  }
  drawerVisible.value = true
}

async function handleDrawerConfirm() {
  if (!formData.value.className || !formData.value.classCode || !formData.value.majorId || !formData.value.grade) {
    ElMessage.warning('请填写班级名称、班级编码、年级并选择所属专业')
    return
  }
  loading.value = true
  try {
    if (isEdit.value) {
      await updateClassInfo({
        classId: currentRow.value.classId,
        ...formData.value
      })
      ElMessage.success('编辑成功')
    } else {
      await addClassInfo(formData.value)
      ElMessage.success('新增成功')
    }
    drawerVisible.value = false
    await fetchList()
  } finally {
    loading.value = false
  }
}

function handleDelete(row) {
  ElMessageBox.confirm(`确定要删除班级「${row.className}」吗？`, '删除确认', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    loading.value = true
    try {
      await deleteClassInfo(row.classId)
      ElMessage.success('删除成功')
      await fetchList()
    } finally {
      loading.value = false
    }
  }).catch(() => {})
}

async function handleRowDrag(list) {
  pagination.list = list
  const ids = list.map((item) => item.classId)
  try {
    await sortClassInfo(ids)
  } catch {
    ElMessage.error('排序保存失败')
    await fetchList()
  }
}

onMounted(async () => {
  await Promise.all([fetchFaculties(), fetchAllMajors()])
  await fetchList()
  if (searchFormRef.value?.$el) {
    searchFormRef.value.$el.addEventListener('submit', (e) => e.preventDefault())
  }
})
</script>

<template>
  <div class="class-page">
    <h2 class="page-title">班级管理</h2>

    <div class="search-card">
      <el-form ref="searchFormRef" :model="searchForm">
        <el-row :gutter="16">
          <el-col :span="4">
            <el-form-item label="班级名称">
              <el-input v-model="searchForm.name" placeholder="请输入" clearable @keyup.enter="handleSearch" />
            </el-form-item>
          </el-col>
          <el-col :span="4">
            <el-form-item label="所属院系">
              <el-select v-model="searchForm.facultyId" placeholder="请选择院系" clearable filterable style="width: 100%" @change="handleSearchFacultyChange">
                <el-option v-for="f in facultyList" :key="f.facultyId" :label="f.facultyName" :value="f.facultyId" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="4">
            <el-form-item label="所属专业">
              <el-select v-model="searchForm.majorId" placeholder="请选择专业" clearable filterable style="width: 100%">
                <el-option v-for="m in searchMajorList" :key="m.majorId" :label="m.majorName" :value="m.majorId" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="4">
            <el-form-item label="年级">
              <el-select v-model="searchForm.grade" placeholder="请选择年级" clearable style="width: 100%">
                <el-option label="2023" value="2023" />
                <el-option label="2024" value="2024" />
                <el-option label="2025" value="2025" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="4">
            <el-form-item>
              <el-button type="primary" @click="handleSearch">搜索</el-button>
              <el-button @click="handleReset">重置</el-button>
            </el-form-item>
          </el-col>
          <el-col :span="4" class="search-add-col">
            <el-button type="primary" @click="handleAdd">新增</el-button>
          </el-col>
        </el-row>
      </el-form>
    </div>

    <div class="table-card">
      <BaseDataTable
        :columns="columns"
        :data="pagination"
        :loading="loading"
        draggable
        row-key="classId"
        @page-change="handlePageChange"
        @row-drag="handleRowDrag"
      >
        <template #operation="{ row }">
          <el-button type="primary" link size="small" @click="handleEdit(row)">编辑</el-button>
          <el-button type="danger" link size="small" @click="handleDelete(row)">删除</el-button>
        </template>
      </BaseDataTable>
    </div>

    <BaseDrawer v-model="drawerVisible" :title="isEdit ? '编辑班级' : '新增班级'" size="520px" @confirm="handleDrawerConfirm">
      <el-form label-width="80px" @submit.prevent>
        <el-form-item label="班级名称" required>
          <el-input v-model="formData.className" placeholder="请输入班级名称" />
        </el-form-item>
        <el-form-item label="班级编码" required>
          <el-input v-model="formData.classCode" placeholder="请输入班级编码" />
        </el-form-item>
        <el-form-item label="所属院系" required>
          <el-select v-model="formData.facultyId" style="width: 100%" placeholder="请选择院系" filterable @change="handleFormFacultyChange">
            <el-option v-for="f in facultyList" :key="f.facultyId" :label="f.facultyName" :value="f.facultyId" />
          </el-select>
        </el-form-item>
        <el-form-item label="所属专业" required>
          <el-select v-model="formData.majorId" style="width: 100%" placeholder="请选择专业" filterable>
            <el-option v-for="m in formMajorList" :key="m.majorId" :label="m.majorName" :value="m.majorId" />
          </el-select>
        </el-form-item>
        <el-form-item label="年级" required>
          <el-select v-model="formData.grade" style="width: 100%" placeholder="请选择年级">
            <el-option label="2023" value="2023" />
            <el-option label="2024" value="2024" />
            <el-option label="2025" value="2025" />
          </el-select>
        </el-form-item>
        <el-form-item label="班级描述">
          <el-input v-model="formData.description" type="textarea" :rows="3" placeholder="请输入班级描述" />
        </el-form-item>
      </el-form>
    </BaseDrawer>
  </div>
</template>

<style scoped>
.class-page {
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

<script setup>
import { ref, computed, onMounted, reactive } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import BaseDataTable from '@/components/BaseDataTable.vue'
import BaseDrawer from '@/components/BaseDrawer.vue'
import { loadUserInfoList, addUserInfo, updateUserInfo, deleteUserInfo } from '@/api/user'
import { loadFacultyInfoList } from '@/api/faculty'
import { loadMajorInfoList } from '@/api/major'
import { loadClassInfoList } from '@/api/class'

defineOptions({ name: 'Teacher' })

const columns = [
  { prop: 'userNo', label: '工号', width: 140 },
  { prop: 'realName', label: '姓名', width: 100 },
  { prop: 'gender', label: '性别', width: 70, align: 'center' },
  { prop: 'facultyName', label: '所属院系', minWidth: 150 },
  { prop: 'className', label: '负责班级', minWidth: 200 },
  { prop: 'phone', label: '手机号', width: 130, align: 'center' },
  { prop: 'status', label: '状态', width: 80, align: 'center' },
  { prop: 'createTime', label: '创建时间', width: 170, align: 'center' },
  { prop: 'operation', label: '操作', width: 150, fixed: 'right', align: 'center' }
]

const searchFormRef = ref(null)
const searchForm = ref({ userNo: '', realName: '', facultyId: '' })

const facultyList = ref([])
const allMajorList = ref([])
const allClassList = ref([])
const loading = ref(false)

const pagination = reactive({ totalCount: 0, pageSize: 15, pageNo: 1, pageTotal: 1, list: [] })

async function fetchFaculties() { try { const res = await loadFacultyInfoList(); if (res.status === 'success') facultyList.value = res.data || [] } catch {} }
async function fetchAllMajors() { try { const res = await loadMajorInfoList(); if (res.status === 'success') allMajorList.value = res.data || [] } catch {} }
async function fetchAllClasses() { try { const res = await loadClassInfoList({ pageSize: 500 }); if (res.status === 'success') allClassList.value = (res.data && res.data.list) ? res.data.list : (res.data || []) } catch {} }

async function fetchList(params) {
  loading.value = true
  try {
    const query = { pageNo: pagination.pageNo, pageSize: pagination.pageSize, roleType: 'teacher' }
    if (params) {
      if (params.userNo) query.userNoFuzzy = params.userNo
      if (params.realName) query.realNameFuzzy = params.realName
      if (params.facultyId) query.facultyId = params.facultyId
    }
    const res = await loadUserInfoList(query)
    if (res.status === 'success' && res.data) {
      pagination.totalCount = res.data.totalCount || 0
      pagination.pageSize = res.data.pageSize || 15
      pagination.pageNo = res.data.pageNo || 1
      pagination.pageTotal = res.data.pageTotal || 1
      pagination.list = res.data.list || []
    }
  } finally { loading.value = false }
}

function handleSearch() { pagination.pageNo = 1; fetchList({ ...searchForm.value }) }
function handleReset() { searchForm.value = { userNo: '', realName: '', facultyId: '' }; pagination.pageNo = 1; fetchList() }
function handlePageChange({ pageNo, pageSize }) { pagination.pageNo = pageNo; pagination.pageSize = pageSize; fetchList({ ...searchForm.value }) }

const drawerVisible = ref(false)
const isEdit = ref(false)
const currentRow = ref(null)
const formData = ref({ userNo: '', realName: '', gender: '', facultyId: '', majorId: '', classIds: [], phone: '', status: 1 })

const formMajorList = computed(() => {
  const fid = Number(formData.value.facultyId)
  return fid ? allMajorList.value.filter(m => Number(m.facultyId) === fid) : allMajorList.value
})
const formClassList = computed(() => {
  const mid = Number(formData.value.majorId)
  return mid ? allClassList.value.filter(c => Number(c.majorId) === mid) : allClassList.value
})
function handleFormFacultyChange() { formData.value.majorId = '' }
function handleFormMajorChange() { /* 不清空已选班级，教师可跨专业/跨院系 */ }

function handleAdd() {
  isEdit.value = false
  formData.value = { userNo: '', realName: '', gender: '', facultyId: '', majorId: '', classIds: [], phone: '', status: 1 }
  drawerVisible.value = true
}

function handleEdit(row) {
  isEdit.value = true
  currentRow.value = row
  formData.value = {
    userNo: row.userNo || '', realName: row.realName || '', gender: row.gender || '',
    facultyId: row.facultyId != null ? Number(row.facultyId) : '',
    majorId: '',
    classIds: row.classId ? row.classId.split(',').map(Number).filter(Boolean) : [],
    phone: row.phone || '', status: row.status != null ? Number(row.status) : 1
  }
  drawerVisible.value = true
}

async function handleDrawerConfirm() {
  if (!formData.value.userNo || !formData.value.realName || !formData.value.facultyId || formData.value.classIds.length === 0) {
    ElMessage.warning('请填写工号、姓名并选择院系和负责班级')
    return
  }
  if (formData.value.phone && !/^1\d{10}$/.test(formData.value.phone)) {
    ElMessage.warning('手机号格式不正确，请输入11位手机号')
    return
  }
  loading.value = true
  try {
    const payload = { ...formData.value, roleType: 'teacher', classId: formData.value.classIds.join(',') }
    delete payload.classIds
    delete payload.majorId
    if (isEdit.value) {
      await updateUserInfo({ userId: currentRow.value.userId, ...payload })
      ElMessage.success('编辑成功')
    } else {
      await addUserInfo(payload)
      ElMessage.success('新增成功')
    }
    drawerVisible.value = false
    await fetchList({ ...searchForm.value })
  } finally { loading.value = false }
}

function handleDelete(row) {
  ElMessageBox.confirm(`确定要删除教师「${row.realName}」吗？`, '删除确认', { confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning' })
    .then(async () => { loading.value = true; try { await deleteUserInfo(row.userId); ElMessage.success('删除成功'); await fetchList({ ...searchForm.value }) } finally { loading.value = false } })
    .catch(() => {})
}

function getClassTags(className) {
  if (!className) return []
  return className.split(',').map(n => n.trim()).filter(Boolean)
}

onMounted(async () => {
  await Promise.all([fetchFaculties(), fetchAllMajors(), fetchAllClasses()])
  await fetchList()
  if (searchFormRef.value?.$el) searchFormRef.value.$el.addEventListener('submit', e => e.preventDefault())
})
</script>

<template>
  <div class="teacher-page">
    <h2 class="page-title">教师管理</h2>
    <div class="search-card">
      <el-form ref="searchFormRef" :model="searchForm">
        <el-row :gutter="16">
          <el-col :span="5">
            <el-form-item label="工号"><el-input v-model="searchForm.userNo" placeholder="工号" clearable /></el-form-item>
          </el-col>
          <el-col :span="5">
            <el-form-item label="姓名"><el-input v-model="searchForm.realName" placeholder="姓名" clearable /></el-form-item>
          </el-col>
          <el-col :span="4">
            <el-form-item label="院系">
              <el-select v-model="searchForm.facultyId" placeholder="院系" clearable filterable style="width:100%">
                <el-option v-for="f in facultyList" :key="f.facultyId" :label="f.facultyName" :value="f.facultyId" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="5" style="display:flex;align-items:flex-end;padding-bottom:18px;gap:8px;">
            <el-button type="primary" @click="handleSearch">搜索</el-button>
            <el-button @click="handleReset">重置</el-button>
          </el-col>
          <el-col :span="5" style="display:flex;align-items:flex-end;padding-bottom:18px;justify-content:flex-end;">
            <el-button type="primary" @click="handleAdd">新增</el-button>
          </el-col>
        </el-row>
      </el-form>
    </div>
    <div class="table-card">
      <BaseDataTable :columns="columns" :data="pagination" :loading="loading" row-key="userId" @page-change="handlePageChange">
        <template #gender="{ row }">{{ row.gender === 'M' ? '男' : row.gender === 'F' ? '女' : '' }}</template>
        <template #className="{ row }">
          <div class="class-tags">
            <span v-for="(name, i) in getClassTags(row.className)" :key="i" class="class-tag">{{ name }}</span>
          </div>
        </template>
        <template #status="{ row }"><el-tag :type="row.status === 1 ? 'success' : 'danger'" size="small">{{ row.status === 1 ? '启用' : '禁用' }}</el-tag></template>
        <template #operation="{ row }">
          <el-button type="primary" link size="small" @click="handleEdit(row)">编辑</el-button>
          <el-button type="danger" link size="small" @click="handleDelete(row)">删除</el-button>
        </template>
      </BaseDataTable>
    </div>
    <BaseDrawer v-model="drawerVisible" :title="isEdit ? '编辑教师' : '新增教师'" size="520px" @confirm="handleDrawerConfirm">
      <el-form label-width="80px" @submit.prevent>
        <el-form-item label="工号" required><el-input v-model="formData.userNo" placeholder="工号（唯一）" /></el-form-item>
        <el-form-item label="姓名" required><el-input v-model="formData.realName" placeholder="姓名" /></el-form-item>
        <el-form-item label="性别"><el-select v-model="formData.gender" style="width:100%" placeholder="性别"><el-option label="男" value="M" /><el-option label="女" value="F" /></el-select></el-form-item>
        <el-form-item label="院系" required>
          <el-select v-model="formData.facultyId" style="width:100%" placeholder="院系" filterable @change="handleFormFacultyChange">
            <el-option v-for="f in facultyList" :key="f.facultyId" :label="f.facultyName" :value="f.facultyId" />
          </el-select>
        </el-form-item>
        <el-form-item label="专业">
          <el-select v-model="formData.majorId" style="width:100%" placeholder="专业（方便定位班级）" filterable @change="handleFormMajorChange">
            <el-option v-for="m in formMajorList" :key="m.majorId" :label="m.majorName" :value="m.majorId" />
          </el-select>
        </el-form-item>
        <el-form-item label="负责班级" required>
          <el-select v-model="formData.classIds" style="width:100%" placeholder="负责班级（可多选）" multiple filterable>
            <el-option v-for="c in formClassList" :key="c.classId" :label="c.className" :value="c.classId" />
          </el-select>
        </el-form-item>
        <el-form-item label="手机号"><el-input v-model="formData.phone" placeholder="11位手机号" maxlength="11" /></el-form-item>
        <el-form-item label="状态"><el-switch v-model="formData.status" :active-value="1" :inactive-value="0" active-text="启用" inactive-text="禁用" /></el-form-item>
      </el-form>
    </BaseDrawer>
  </div>
</template>

<style scoped>
.teacher-page { height: 100%; display: flex; flex-direction: column; padding: 16px; gap: 16px; }
.page-title { font-size: 20px; color: #303133; flex-shrink: 0; }
.search-card { flex-shrink: 0; background: #fff; border-radius: 8px; box-shadow: 0 2px 12px rgba(0,0,0,.06); padding: 20px; }
.table-card { flex: 1; min-height: 0; background: #fff; border-radius: 8px; box-shadow: 0 2px 12px rgba(0,0,0,.06); padding: 20px; display: flex; flex-direction: column; }
.table-card :deep(.base-data-table) { flex: 1; }

.class-tags { display: flex; flex-wrap: wrap; gap: 4px; }
.class-tag {
  display: inline-block; padding: 2px 10px; font-size: 12px;
  color: #2b7be4; background: #ecf5ff; border: 1px solid #b3d8ff;
  border-radius: 4px; white-space: nowrap;
}
</style>

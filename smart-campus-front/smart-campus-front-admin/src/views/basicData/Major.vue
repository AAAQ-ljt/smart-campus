<script setup>
import { ref, computed } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import BaseDataTable from '@/components/BaseDataTable.vue'
import BaseDrawer from '@/components/BaseDrawer.vue'

defineOptions({ name: 'Major' })

const departmentNames = [
  '计算机科学与技术学院', '数学与统计学院', '物理与电子工程学院',
  '化学与材料科学学院', '生命科学学院', '经济管理学院', '法学院',
  '教育学院', '文学院', '外国语学院'
]

const majorNames = [
  '计算机科学与技术', '软件工程', '数据科学与大数据技术', '人工智能',
  '数学与应用数学', '信息与计算科学', '统计学',
  '物理学', '电子信息工程', '通信工程',
  '化学', '材料科学与工程', '应用化学',
  '生物科学', '生物工程', '生物技术',
  '经济学', '金融学', '会计学', '工商管理',
  '法学', '知识产权', '社会工作',
  '教育学', '心理学', '学前教育',
  '汉语言文学', '新闻学', '英语', '日语'
]

function randomDate() {
  const start = new Date(2023, 0, 1).getTime()
  const end = new Date(2026, 5, 28).getTime()
  const d = new Date(start + Math.random() * (end - start))
  const y = d.getFullYear()
  const m = String(d.getMonth() + 1).padStart(2, '0')
  const day = String(d.getDate()).padStart(2, '0')
  const h = String(d.getHours()).padStart(2, '0')
  const min = String(d.getMinutes()).padStart(2, '0')
  const s = String(d.getSeconds()).padStart(2, '0')
  return `${y}-${m}-${day} ${h}:${min}:${s}`
}

const allData = Array.from({ length: 200 }, (_, i) => ({
  id: i + 1,
  name: majorNames[i % majorNames.length] + (i >= majorNames.length ? `（${Math.floor(i / majorNames.length) + 1}）` : ''),
  code: 'MAJ' + String(i + 1).padStart(3, '0'),
  department: departmentNames[i % departmentNames.length],
  duration: [3, 4, 4, 5, 4, 4, 3, 4, 4, 4][i % 10] + '年',
  createTime: randomDate(),
  status: i % 7 === 0 ? '禁用' : '启用'
}))

const columns = [
  { prop: 'id', label: '序号', width: 80, align: 'center' },
  { prop: 'name', label: '专业名称', minWidth: 180 },
  { prop: 'code', label: '专业编码', width: 120, align: 'center' },
  { prop: 'department', label: '所属院系', minWidth: 160 },
  { prop: 'duration', label: '学制', width: 80, align: 'center' },
  { prop: 'createTime', label: '创建时间', width: 180, align: 'center' },
  { prop: 'status', label: '状态', width: 90, align: 'center' },
  { prop: 'operation', label: '操作', width: 150, fixed: 'right', align: 'center' }
]

const searchForm = ref({
  name: '',
  code: '',
  department: '',
  status: ''
})

const pageNo = ref(1)
const pageSize = ref(15)

const paginatedList = computed(() => {
  let filtered = allData
  if (searchForm.value.name) filtered = filtered.filter((d) => d.name.includes(searchForm.value.name))
  if (searchForm.value.code) filtered = filtered.filter((d) => d.code.includes(searchForm.value.code))
  if (searchForm.value.department) filtered = filtered.filter((d) => d.department === searchForm.value.department)
  if (searchForm.value.status) filtered = filtered.filter((d) => d.status === searchForm.value.status)
  const start = (pageNo.value - 1) * pageSize.value
  return filtered.slice(start, start + pageSize.value)
})

const totalCount = computed(() => {
  let filtered = allData
  if (searchForm.value.name) filtered = filtered.filter((d) => d.name.includes(searchForm.value.name))
  if (searchForm.value.code) filtered = filtered.filter((d) => d.code.includes(searchForm.value.code))
  if (searchForm.value.department) filtered = filtered.filter((d) => d.department === searchForm.value.department)
  if (searchForm.value.status) filtered = filtered.filter((d) => d.status === searchForm.value.status)
  return filtered.length
})

const tableData = computed(() => ({
  totalCount: totalCount.value,
  pageSize: pageSize.value,
  pageNo: pageNo.value,
  pageTotal: Math.ceil(totalCount.value / pageSize.value),
  list: paginatedList.value
}))

function handlePageChange({ pageNo: p, pageSize: s }) {
  pageNo.value = p
  pageSize.value = s
}

function handleSearch() {
  pageNo.value = 1
}

function handleReset() {
  searchForm.value = { name: '', code: '', department: '', status: '' }
  pageNo.value = 1
}

function handleEdit(row) {
  ElMessage.info(`编辑：${row.name}`)
}

function handleDelete(row) {
  ElMessageBox.confirm(`确定要删除专业「${row.name}」吗？`, '删除确认', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    ElMessage.success('删除成功')
  }).catch(() => {})
}

const drawerVisible = ref(false)

function handleAdd() {
  drawerVisible.value = true
}

function handleDrawerConfirm() {
  ElMessage.success('新增成功')
  drawerVisible.value = false
}
</script>

<template>
  <div class="major-page">
    <h2 class="page-title">专业管理</h2>

    <!-- 搜索卡片 -->
    <div class="search-card">
      <div class="search-card-row">
        <el-form :model="searchForm" inline>
          <el-form-item label="专业名称">
            <el-input v-model="searchForm.name" placeholder="请输入专业名称" clearable style="width: 180px" />
          </el-form-item>
          <el-form-item label="专业编码">
            <el-input v-model="searchForm.code" placeholder="请输入专业编码" clearable style="width: 180px" />
          </el-form-item>
          <el-form-item label="所属院系">
            <el-select v-model="searchForm.department" placeholder="请选择院系" clearable style="width: 180px" @change="handleSearch">
              <el-option v-for="dept in departmentNames" :key="dept" :label="dept" :value="dept" />
            </el-select>
          </el-form-item>
          <el-form-item label="状态">
            <el-select v-model="searchForm.status" placeholder="请选择状态" clearable style="width: 140px" @change="handleSearch">
              <el-option label="启用" value="启用" />
              <el-option label="禁用" value="禁用" />
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="handleSearch">搜索</el-button>
            <el-button @click="handleReset">重置</el-button>
          </el-form-item>
        </el-form>
        <el-button type="primary" @click="handleAdd">新增</el-button>
      </div>
    </div>

    <!-- 表格卡片 -->
    <div class="table-card">
      <BaseDataTable
        :columns="columns"
        :data="tableData"
        show-selection
        @page-change="handlePageChange"
      >
        <template #status="{ row }">
          <el-tag :type="row.status === '启用' ? 'success' : 'danger'">
            {{ row.status }}
          </el-tag>
        </template>
        <template #operation="{ row }">
          <el-button type="primary" link size="small" @click="handleEdit(row)">编辑</el-button>
          <el-button type="danger" link size="small" @click="handleDelete(row)">删除</el-button>
        </template>
      </BaseDataTable>
    </div>

    <!-- 新增抽屉 -->
    <BaseDrawer v-model="drawerVisible" title="新增专业" size="520px" @confirm="handleDrawerConfirm">
      <el-form label-width="80px">
        <el-form-item label="专业名称">
          <el-input placeholder="请输入专业名称" />
        </el-form-item>
        <el-form-item label="专业编码">
          <el-input placeholder="请输入专业编码" />
        </el-form-item>
        <el-form-item label="所属院系">
          <el-select style="width: 100%" placeholder="请选择院系">
            <el-option v-for="dept in departmentNames" :key="dept" :label="dept" :value="dept" />
          </el-select>
        </el-form-item>
        <el-form-item label="学制">
          <el-select style="width: 100%" placeholder="请选择学制">
            <el-option label="三年制" value="3年" />
            <el-option label="四年制" value="4年" />
            <el-option label="五年制" value="5年" />
          </el-select>
        </el-form-item>
        <el-form-item label="状态">
          <el-select style="width: 100%" placeholder="请选择状态">
            <el-option label="启用" value="启用" />
            <el-option label="禁用" value="禁用" />
          </el-select>
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
  padding: 20px 20px 4px;
}

.search-card-row {
  display: flex;
  align-items: flex-start;
  justify-content: space-between;
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

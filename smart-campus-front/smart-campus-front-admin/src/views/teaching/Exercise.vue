<script setup>
import { ref, computed, onMounted, nextTick } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import BaseDataTable from '@/components/BaseDataTable.vue'
import { loadExerciseList, getExerciseDetail, addExercise, updateExercise, deleteExercise } from '@/api/exercise'

defineOptions({ name: 'Exercise' })

const OPTION_LETTERS = ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H']
const questionTypeMap = { 1: '单选题', 2: '多选题', 3: '判断题', 4: '简答题' }
const difficultyMap = { 1: '简单', 2: '中等', 3: '困难' }

// ==================== Data ====================
const loading = ref(false)
const tableDataRef = ref(null)

// ==================== Search ====================
const searchForm = ref({
  questionTitleFuzzy: '',
  questionType: '',
  difficulty: ''
})
const searchFormRef = ref(null)

function handleSearch() {
  pagination.value.pageNo = 1
  fetchList()
}

function handleReset() {
  searchForm.value = { questionTitleFuzzy: '', questionType: '', difficulty: '' }
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
    if (!query.questionType) query.questionType = null
    if (!query.difficulty) query.difficulty = null
    const res = await loadExerciseList(query)
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
  { prop: 'questionId', label: 'ID', width: 140 },
  { prop: 'questionTitle', label: '题干', minWidth: 260 },
  { prop: 'questionType', label: '题型', width: 90, align: 'center' },
  { prop: 'difficulty', label: '难度', width: 80, align: 'center' },
  { prop: 'answer', label: '答案', width: 90, align: 'center' },
  { prop: 'score', label: '分值', width: 65, align: 'center' },
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

// ==================== Form Dialog ====================
const dialogVisible = ref(false)
const dialogTitle = ref('新增习题')
const dialogLoading = ref(false)
const formRef = ref(null)

const form = ref({
  questionId: '',
  questionTitle: '',
  questionType: 1,
  difficulty: 1,
  answer: '',
  analysis: '',
  score: 5,
  options: []
})

const rules = {
  questionTitle: [{ required: true, message: '请输入题干', trigger: 'blur' }],
  questionType: [{ required: true, message: '请选择题型', trigger: 'change' }],
  analysis: [{ required: true, message: '请输入答案解析', trigger: 'blur' }],
  answer: [{ required: true, message: '请选择正确答案', trigger: 'change' }]
}

const needOptions = computed(() => form.value.questionType === 1 || form.value.questionType === 2)

function addOption() {
  if (form.value.options.length >= 8) { ElMessage.warning('最多添加8个选项'); return }
  form.value.options.push({ optionContent: '' })
}

function removeOption(index) {
  form.value.options.splice(index, 1)
  form.value.answer = form.value.questionType === 2 ? [] : ''
}

function getOptionLetter(index) {
  return index < OPTION_LETTERS.length ? OPTION_LETTERS[index] : String(index)
}

const answerOptions = computed(() =>
  form.value.options.map((_, i) => ({
    label: getOptionLetter(i),
    value: OPTION_LETTERS[i]
  }))
)

const isMultiAnswer = computed(() => form.value.questionType === 2)

// ==================== 拖拽排序 ====================
const dragIndex = ref(-1)

function onDragStart(index) {
  dragIndex.value = index
}

function onDragOver(e, index) {
  e.preventDefault()
}

function onDrop(index) {
  if (dragIndex.value === -1 || dragIndex.value === index) return
  const item = form.value.options.splice(dragIndex.value, 1)[0]
  form.value.options.splice(index, 0, item)
  form.value.answer = form.value.questionType === 2 ? [] : ''
  dragIndex.value = -1
}

function onDragEnd() {
  dragIndex.value = -1
}

// ==================== Dialog Actions ====================
function handleOpenAdd() {
  dialogTitle.value = '新增习题'
  form.value = { questionId: '', questionTitle: '', questionType: 1, difficulty: 1, answer: '', analysis: '', score: 5, options: [] }
  dialogVisible.value = true
  nextTick(() => formRef.value?.clearValidate())
}

async function handleOpenEdit(row) {
  dialogTitle.value = '编辑习题'
  try {
    const res = await getExerciseDetail(row.questionId)
    if (res.status === 'success' && res.data) {
      const { question, options } = res.data
      form.value = {
        questionId: question.questionId,
        questionTitle: question.questionTitle,
        questionType: question.questionType,
        difficulty: question.difficulty,
        answer: question.answer || (question.questionType === 2 ? [] : ''),
        analysis: question.analysis || '',
        score: question.score || 5,
        options: (options || []).map(o => ({ optionContent: o.optionContent }))
      }
    }
  } catch { /* handled */ }
  dialogVisible.value = true
  nextTick(() => formRef.value?.clearValidate())
}

async function handleSave() {
  if (!formRef.value) return
  try { await formRef.value.validate() } catch { return }

  if (needOptions.value && form.value.options.length < 2) {
    ElMessage.warning('请至少添加两个选项')
    return
  }

  if (needOptions.value) {
    for (let i = 0; i < form.value.options.length; i++) {
      if (!form.value.options[i].optionContent.trim()) {
        ElMessage.warning(`选项${getOptionLetter(i)}内容不能为空`)
        return
      }
    }
  }

  if (needOptions.value) {
    const answerVal = form.value.answer
    if (isMultiAnswer.value) {
      if (!answerVal || answerVal.length === 0) { ElMessage.warning('请选择正确答案'); return }
    } else {
      if (!answerVal) { ElMessage.warning('请选择正确答案'); return }
    }
  }

  dialogLoading.value = true
  try {
    const params = {
      questionId: form.value.questionId || '',
      questionTitle: form.value.questionTitle,
      questionType: form.value.questionType,
      difficulty: form.value.difficulty,
      answer: isMultiAnswer.value ? (form.value.answer || []).join('') : (form.value.answer || ''),
      analysis: form.value.analysis,
      score: form.value.score
    }
    if (needOptions.value) {
      params.options = form.value.options.map(opt => ({ optionContent: opt.optionContent }))
    }

    const res = form.value.questionId ? await updateExercise(params) : await addExercise(params)
    if (res.status === 'success') {
      ElMessage.success(form.value.questionId ? '更新成功' : '新增成功')
      dialogVisible.value = false
      fetchList()
    }
  } catch { /* handled */ }
  finally { dialogLoading.value = false }
}

async function handleDelete(row) {
  try {
    await ElMessageBox.confirm('确定要删除该习题吗？', '删除确认', {
      confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning'
    })
  } catch { return }
  try {
    const res = await deleteExercise(row.questionId)
    if (res.status === 'success') { ElMessage.success('删除成功'); fetchList() }
  } catch { /* handled */ }
}

function onQuestionTypeChange() {
  form.value.answer = form.value.questionType === 2 ? [] : ''
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
  <div class="exercise-page">
    <h2 class="page-title">习题管理</h2>

    <!-- 搜索区域 -->
    <div class="search-card">
      <el-form ref="searchFormRef" :model="searchForm" @submit.prevent>
        <el-row :gutter="16">
          <el-col :span="6">
            <el-form-item label="题干">
              <el-input v-model="searchForm.questionTitleFuzzy" placeholder="请输入" clearable @keyup.enter="handleSearch" />
            </el-form-item>
          </el-col>
          <el-col :span="5">
            <el-form-item label="题型">
              <el-select v-model="searchForm.questionType" placeholder="全部" clearable filterable style="width: 100%">
                <el-option label="单选题" :value="1" />
                <el-option label="多选题" :value="2" />
                <el-option label="判断题" :value="3" />
                <el-option label="简答题" :value="4" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="5">
            <el-form-item label="难度">
              <el-select v-model="searchForm.difficulty" placeholder="全部" clearable filterable style="width: 100%">
                <el-option label="简单" :value="1" />
                <el-option label="中等" :value="2" />
                <el-option label="困难" :value="3" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="4">
            <el-form-item>
              <el-button type="primary" @click="handleSearch">搜索</el-button>
              <el-button @click="handleReset">重置</el-button>
            </el-form-item>
          </el-col>
          <el-col :span="4" class="search-actions-col">
            <el-button type="primary" @click="handleOpenAdd">新增习题</el-button>
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
        row-key="questionId"
        @page-change="handlePageChange"
      >
        <template #questionTitle="{ row }">
          <span class="title-text" :title="row.questionTitle">{{ row.questionTitle }}</span>
        </template>
        <template #questionType="{ row }">
          <el-tag size="small">{{ questionTypeMap[row.questionType] || '未知' }}</el-tag>
        </template>
        <template #difficulty="{ row }">
          <el-tag size="small" :type="row.difficulty === 1 ? 'success' : row.difficulty === 2 ? 'warning' : 'danger'">
            {{ difficultyMap[row.difficulty] || '未知' }}
          </el-tag>
        </template>
        <template #answer="{ row }">
          <span class="answer-text">{{ row.answer }}</span>
        </template>
        <template #score="{ row }">{{ row.score }}</template>
        <template #operation="{ row }">
          <el-button type="primary" link size="small" @click="handleOpenEdit(row)">编辑</el-button>
          <el-button type="danger" link size="small" @click="handleDelete(row)">删除</el-button>
        </template>
      </BaseDataTable>
    </div>

    <!-- 弹窗 -->
    <el-dialog v-model="dialogVisible" :title="dialogTitle" width="720px" :close-on-click-modal="false" @keydown.enter.prevent>
      <el-form ref="formRef" :model="form" :rules="rules" label-width="90px">
        <!-- 题型 -->
        <el-form-item label="题型" prop="questionType">
          <el-radio-group v-model="form.questionType" @change="onQuestionTypeChange">
            <el-radio-button :value="1">单选题</el-radio-button>
            <el-radio-button :value="2">多选题</el-radio-button>
            <el-radio-button :value="3">判断题</el-radio-button>
            <el-radio-button :value="4">简答题</el-radio-button>
          </el-radio-group>
        </el-form-item>

        <!-- 题干 -->
        <el-form-item label="题干" prop="questionTitle">
          <el-input v-model="form.questionTitle" type="textarea" :rows="2" placeholder="请输入题干内容" />
        </el-form-item>

        <!-- 难度 -->
        <el-form-item label="难度" prop="difficulty">
          <el-radio-group v-model="form.difficulty">
            <el-radio :value="1">简单</el-radio>
            <el-radio :value="2">中等</el-radio>
            <el-radio :value="3">困难</el-radio>
          </el-radio-group>
        </el-form-item>

        <!-- 分值 -->
        <el-form-item label="分值" prop="score">
          <el-input-number v-model="form.score" :min="1" :max="100" />
        </el-form-item>

        <!-- 选项（单选/多选） -->
        <el-form-item v-if="needOptions" label="选项列表" required>
          <div class="options-wrap">
            <div
              v-for="(opt, index) in form.options"
              :key="index"
              class="option-row"
              :class="{ 'option-dragging': dragIndex === index }"
              draggable="true"
              @dragstart="onDragStart(index)"
              @dragover="onDragOver($event, index)"
              @drop="onDrop(index)"
              @dragend="onDragEnd"
            >
              <span class="drag-handle">&#x2630;</span>
              <span class="option-letter">{{ getOptionLetter(index) }}.</span>
              <el-input v-model="opt.optionContent" :placeholder="`选项${getOptionLetter(index)}内容`" class="option-input" />
              <el-button link type="danger" size="small" @click="removeOption(index)">&#x2715;</el-button>
            </div>
            <el-button class="add-option-btn" @click="addOption" :disabled="form.options.length >= 8">
              + 添加选项
            </el-button>
          </div>
        </el-form-item>

        <!-- 答案：单选/多选 -->
        <el-form-item v-if="needOptions" label="正确答案" prop="answer">
          <el-select
            v-if="!isMultiAnswer"
            v-model="form.answer"
            placeholder="请选择正确答案"
            style="width: 100%"
          >
            <el-option
              v-for="(opt, i) in answerOptions"
              :key="opt.value"
              :label="`${opt.label}. ${form.options[i]?.optionContent || ''}`"
              :value="opt.value"
            />
          </el-select>
          <el-select
            v-else
            v-model="form.answer"
            multiple
            placeholder="请选择正确答案（可多选）"
            style="width: 100%"
          >
            <el-option
              v-for="(opt, i) in answerOptions"
              :key="opt.value"
              :label="`${opt.label}. ${form.options[i]?.optionContent || ''}`"
              :value="opt.value"
            />
          </el-select>
        </el-form-item>

        <!-- 答案：判断 -->
        <el-form-item v-if="form.questionType === 3" label="正确答案" prop="answer">
          <el-radio-group v-model="form.answer">
            <el-radio value="T">正确 &#10004;</el-radio>
            <el-radio value="F">错误 &#10008;</el-radio>
          </el-radio-group>
        </el-form-item>

        <!-- 答案：简答 -->
        <el-form-item v-if="form.questionType === 4" label="参考答案" prop="answer">
          <el-input v-model="form.answer" type="textarea" :rows="3" placeholder="请输入参考答案" />
        </el-form-item>

        <!-- 解析 -->
        <el-form-item label="答案解析" prop="analysis">
          <el-input v-model="form.analysis" type="textarea" :rows="3" placeholder="请输入答案解析" />
        </el-form-item>
      </el-form>

      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" :loading="dialogLoading" @click="handleSave">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<style scoped>
.exercise-page {
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

.answer-text {
  font-weight: 600;
  color: #409eff;
}

/* 选项 */
.options-wrap {
  width: 100%;
}

.option-row {
  display: flex;
  align-items: center;
  gap: 8px;
  margin-bottom: 8px;
  padding: 6px 8px;
  background: #fafafa;
  border-radius: 6px;
  border: 1px solid #ebeef5;
  transition: all 0.2s;
}

.option-row:hover {
  border-color: #c0c4cc;
}

.option-dragging {
  opacity: 0.4;
}

.drag-handle {
  cursor: grab;
  color: #c0c4cc;
  font-size: 18px;
  padding: 0 4px;
  user-select: none;
  flex-shrink: 0;
}

.option-letter {
  font-weight: 700;
  color: #409eff;
  min-width: 22px;
  flex-shrink: 0;
  text-align: center;
}

.option-input {
  flex: 1;
}

.add-option-btn {
  width: 100%;
  border-style: dashed;
}
</style>

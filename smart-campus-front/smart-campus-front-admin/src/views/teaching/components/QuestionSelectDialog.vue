<script setup>
import { ref, watch, nextTick } from 'vue'
import { ElMessage } from 'element-plus'
import { loadExerciseList } from '@/api/exercise'
import { batchQuestionOptions } from '@/api/paper'

defineOptions({ name: 'QuestionSelectDialog' })

const OPTION_LETTERS = ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H']
const questionTypeMap = { 1: '单选题', 2: '多选题', 3: '判断题', 4: '简答题' }
const difficultyMap = { 1: '简单', 2: '中等', 3: '困难' }

const props = defineProps({
  visible: { type: Boolean, default: false },
  selectedIds: { type: Array, default: () => [] }
})

const emit = defineEmits(['update:visible', 'confirm'])

// ==================== Search ====================
const searchForm = ref({ questionTitleFuzzy: '', questionType: '', difficulty: '' })
const loading = ref(false)
const tableItems = ref([])
const pagination = ref({ pageNo: 1, pageSize: 10, totalCount: 0 })
const checkedIds = ref([])
const optionsMap = ref({})

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

function handleSearch() {
  pagination.value.pageNo = 1
  fetchList()
}

function handleReset() {
  searchForm.value = { questionTitleFuzzy: '', questionType: '', difficulty: '' }
  pagination.value.pageNo = 1
  fetchList()
}

function handlePageChange({ pageNo, pageSize }) {
  pagination.value.pageNo = pageNo
  pagination.value.pageSize = pageSize
  fetchList()
}

// ==================== Selection ====================
const tableRef = ref(null)

watch(() => props.visible, (val) => {
  if (val) {
    checkedIds.value = [...props.selectedIds]
    fetchList().then(() => {
      nextTick(() => {
        if (!tableRef.value) return
        tableItems.value.forEach(row => {
          if (checkedIds.value.includes(row.questionId)) {
            tableRef.value.toggleRowSelection(row, true)
          }
        })
      })
    })
    if (props.selectedIds.length > 0) {
      loadOptionsForIds(props.selectedIds)
    }
  } else {
    optionsMap.value = {}
  }
})

async function loadOptionsForIds(ids) {
  if (!ids || ids.length === 0) return
  try {
    const needIds = ids.filter(id => !optionsMap.value[id])
    if (needIds.length === 0) return
    const res = await batchQuestionOptions(needIds)
    if (res.status === 'success' && res.data) {
      Object.assign(optionsMap.value, res.data)
    }
  } catch { /* handled */ }
}

function onSelectionChange(selection) {
  const ids = selection.map(row => row.questionId)
  checkedIds.value = ids
  loadOptionsForIds(ids)
}

function handleConfirm() {
  if (checkedIds.value.length === 0) {
    ElMessage.warning('请至少选择一道题目')
    return
  }
  const selected = tableItems.value.filter(item => checkedIds.value.includes(item.questionId))
  emit('confirm', selected.map(item => ({
    questionId: item.questionId,
    questionTitle: item.questionTitle,
    questionType: item.questionType,
    difficulty: item.difficulty,
    answer: item.answer,
    score: item.score,
    options: optionsMap.value[item.questionId] || []
  })))
}

function needOptions(questionType) {
  return questionType === 1 || questionType === 2 || questionType === 3
}

function getOptionLetter(index) {
  return index < OPTION_LETTERS.length ? OPTION_LETTERS[index] : String(index)
}
</script>

<template>
  <el-dialog
    :model-value="visible"
    @update:model-value="$emit('update:visible', $event)"
    title="选择题库题目"
    width="900px"
    :close-on-click-modal="false"
  >
    <!-- 搜索 -->
    <el-form :model="searchForm" @submit.prevent>
      <el-row :gutter="12">
        <el-col :span="8">
          <el-form-item label="题干">
            <el-input v-model="searchForm.questionTitleFuzzy" placeholder="请输入" clearable @keyup.enter="handleSearch" />
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="题型">
            <el-select v-model="searchForm.questionType" placeholder="全部" clearable style="width: 100%">
              <el-option label="单选题" :value="1" />
              <el-option label="多选题" :value="2" />
              <el-option label="判断题" :value="3" />
              <el-option label="简答题" :value="4" />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="难度">
            <el-select v-model="searchForm.difficulty" placeholder="全部" clearable style="width: 100%">
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
      </el-row>
    </el-form>

    <!-- 表格 -->
    <el-table
      :data="tableItems"
      v-loading="loading"
      row-key="questionId"
      size="small"
      max-height="360"
      border
      @selection-change="onSelectionChange"
      ref="tableRef"
    >
      <el-table-column type="selection" width="42" :reserve-selection="true" />
      <el-table-column prop="questionTitle" label="题干" min-width="220" show-overflow-tooltip />
      <el-table-column label="题型" width="80" align="center">
        <template #default="{ row }">
          <el-tag size="small">{{ questionTypeMap[row.questionType] || '未知' }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="难度" width="70" align="center">
        <template #default="{ row }">
          <el-tag size="small" :type="row.difficulty === 1 ? 'success' : row.difficulty === 2 ? 'warning' : 'danger'">
            {{ difficultyMap[row.difficulty] || '未知' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="score" label="分值" width="60" align="center" />
    </el-table>

    <!-- 分页 -->
    <div class="dialog-pagination">
      <el-pagination
        v-model:current-page="pagination.pageNo"
        v-model:page-size="pagination.pageSize"
        :total="pagination.totalCount"
        :page-sizes="[10, 15, 20]"
        layout="total, sizes, prev, pager, next"
        small
        @size-change="handlePageChange({ pageNo: pagination.pageNo, pageSize: pagination.pageSize })"
        @current-change="handlePageChange({ pageNo: pagination.pageNo, pageSize: pagination.pageSize })"
      />
    </div>

    <!-- 已选题目及其选项 -->
    <div v-if="checkedIds.length > 0" class="selected-preview">
      <h4>已选题目预览 ({{ checkedIds.length }}题)</h4>
      <div v-for="item in tableItems.filter(t => checkedIds.includes(t.questionId))" :key="item.questionId" class="selected-item">
        <div class="selected-title">
          <el-tag size="small" class="selected-tag">{{ questionTypeMap[item.questionType] }}</el-tag>
          <span class="selected-text">{{ item.questionTitle }}</span>
          <span class="selected-score">({{ item.score }}分)</span>
        </div>
        <div v-if="needOptions(item.questionType) && optionsMap[item.questionId]?.length" class="selected-options">
          <span
            v-for="(opt, idx) in optionsMap[item.questionId]"
            :key="opt.optionId"
            class="option-item"
            :class="{ 'is-answer': item.answer && item.answer.includes(getOptionLetter(idx)) }"
          >
            {{ getOptionLetter(idx) }}. {{ opt.optionContent }}
          </span>
        </div>
      </div>
    </div>

    <template #footer>
      <el-button @click="$emit('update:visible', false)">取消</el-button>
      <el-button type="primary" @click="handleConfirm">确认选择</el-button>
    </template>
  </el-dialog>
</template>

<style scoped>
.dialog-pagination {
  display: flex;
  justify-content: flex-end;
  margin-top: 12px;
}

.selected-preview {
  margin-top: 16px;
  border-top: 1px solid #ebeef5;
  padding-top: 12px;
}

.selected-preview h4 {
  font-size: 14px;
  color: #303133;
  margin-bottom: 10px;
}

.selected-item {
  padding: 8px 0;
  border-bottom: 1px dashed #ebeef5;
}

.selected-item:last-child {
  border-bottom: none;
}

.selected-title {
  display: flex;
  align-items: center;
  gap: 8px;
}

.selected-tag {
  flex-shrink: 0;
}

.selected-text {
  color: #303133;
  font-size: 13px;
}

.selected-score {
  color: #909399;
  font-size: 12px;
  flex-shrink: 0;
}

.selected-options {
  margin-top: 6px;
  padding-left: 16px;
  display: flex;
  flex-wrap: wrap;
  gap: 8px;
}

.option-item {
  font-size: 12px;
  color: #606266;
  background: #f5f7fa;
  padding: 2px 8px;
  border-radius: 4px;
}

.option-item.is-answer {
  color: #67c23a;
  background: #f0f9eb;
  font-weight: 600;
}
</style>

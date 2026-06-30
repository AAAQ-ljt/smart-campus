<script setup>
import { ref, computed, watch, nextTick } from 'vue'
import { ElMessage } from 'element-plus'
import { getPaperDetail, addPaper, updatePaper } from '@/api/paper'
import QuestionSelectDialog from './QuestionSelectDialog.vue'

defineOptions({ name: 'PaperEditDrawer' })

const OPTION_LETTERS = ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H']
const CHINESE_NUMS = ['', '一', '二', '三', '四', '五', '六', '七', '八', '九', '十',
  '十一', '十二', '十三', '十四', '十五', '十六', '十七', '十八', '十九', '二十']
const questionTypeMap = { 1: '单选题', 2: '多选题', 3: '判断题', 4: '简答题' }

const props = defineProps({
  visible: { type: Boolean, default: false },
  mode: { type: String, default: 'add' },
  paperId: { type: String, default: '' }
})

const emit = defineEmits(['update:visible', 'saved'])

// ==================== Form ====================
const formRef = ref(null)
const formLoading = ref(false)
const questionDialogVisible = ref(false)
const currentGroupIndex = ref(-1)

const form = ref({
  paperId: '',
  paperTitle: '',
  paperType: 1,
  description: '',
  duration: 60,
  status: 1,
  groups: []
})

const rules = {
  paperTitle: [{ required: true, message: '请输入试卷名称', trigger: 'blur' }],
  paperType: [{ required: true, message: '请选择试卷类型', trigger: 'change' }]
}

const isExam = computed(() => form.value.paperType === 2)

// ==================== Group Management ====================
function addGroup() {
  form.value.groups.push({ groupName: '', questions: [] })
}

function removeGroup(index) {
  form.value.groups.splice(index, 1)
}

function openQuestionDialog(index) {
  currentGroupIndex.value = index
  questionDialogVisible.value = true
}

function onQuestionConfirm(selectedQuestions) {
  if (currentGroupIndex.value >= 0 && currentGroupIndex.value < form.value.groups.length) {
    const existing = form.value.groups[currentGroupIndex.value].questions
    const existingIds = new Set(existing.map(q => q.questionId))
    const newQuestions = selectedQuestions.filter(q => !existingIds.has(q.questionId))
    existing.push(...newQuestions)
  }
  questionDialogVisible.value = false
  currentGroupIndex.value = -1
}

function removeQuestion(groupIndex, questionIndex) {
  form.value.groups[groupIndex].questions.splice(questionIndex, 1)
}

// ==================== Preview ====================
const previewItems = computed(() => {
  const items = []
  let globalIdx = 0
  let groupNum = 0
  form.value.groups.forEach((group) => {
    if (group.questions.length === 0) return
    groupNum++
    items.push({ type: 'group', groupName: group.groupName, groupNum, key: 'g-' + groupNum })
    group.questions.forEach((q) => {
      globalIdx++
      items.push({ type: 'question', ...q, globalIdx, key: 'q-' + globalIdx })
    })
  })
  return items
})

const totalScore = computed(() => {
  return form.value.groups.reduce((sum, g) =>
    sum + g.questions.reduce((s, q) => s + (q.score || 0), 0), 0)
})

const questionCount = computed(() => {
  return form.value.groups.reduce((sum, g) => sum + g.questions.length, 0)
})

function chineseNum(n) {
  return n < CHINESE_NUMS.length ? CHINESE_NUMS[n] : String(n)
}

function needOptions(questionType) {
  return questionType === 1 || questionType === 2 || questionType === 3
}

function getOptionLetter(index) {
  return index < OPTION_LETTERS.length ? OPTION_LETTERS[index] : String(index)
}

// ==================== Load / Save ====================
async function loadDetail() {
  if (!props.paperId) return
  formLoading.value = true
  try {
    const res = await getPaperDetail(props.paperId)
    if (res.status === 'success' && res.data) {
      const { paper, paperQuestions, questionMap, optionMap } = res.data
      form.value.paperId = paper.paperId
      form.value.paperTitle = paper.paperTitle
      form.value.paperType = paper.paperType
      form.value.description = paper.description || ''
      form.value.duration = paper.duration || 60
      form.value.status = paper.status != null ? paper.status : 1

      const groups = []
      const pqList = paperQuestions || []
      const qm = questionMap || {}
      const om = optionMap || {}
      console.log('paperQuestions count:', pqList.length, 'questionMap keys:', Object.keys(qm).length, 'optionMap keys:', Object.keys(om).length)
      const groupMap = new Map()
      const childMap = new Map()
      pqList.forEach(pq => {
        if (!pq.parentPaperQuestionId) {
          const g = { groupName: pq.groupName || '', questions: [] }
          groups.push(g)
          groupMap.set(pq.paperQuestionId, g)
        } else {
          if (!childMap.has(pq.parentPaperQuestionId)) {
            childMap.set(pq.parentPaperQuestionId, [])
          }
          childMap.get(pq.parentPaperQuestionId).push(pq)
        }
      })
      groupMap.forEach((g, groupId) => {
        const children = childMap.get(groupId) || []
        children.sort((a, b) => (a.sortOrder || 0) - (b.sortOrder || 0))
        g.questions = children.map(pq => {
          const qi = qm[pq.questionId] || {}
          return {
            questionId: pq.questionId,
            questionTitle: qi.questionTitle || '',
            questionType: qi.questionType,
            difficulty: qi.difficulty,
            answer: qi.answer || '',
            score: pq.score || qi.score || 5,
            options: om[pq.questionId] || []
          }
        })
      })
      form.value.groups = groups
    }
  } catch (e) {
    console.error('加载试卷详情失败:', e)
  }
  finally { formLoading.value = false }
}

async function handleSave() {
  if (!formRef.value) return
  try { await formRef.value.validate() } catch { return }
  if (form.value.groups.length === 0) { ElMessage.warning('请至少添加一个分组'); return }
  if (form.value.groups.every(g => g.questions.length === 0)) { ElMessage.warning('请至少为一个分组添加题目'); return }

  formLoading.value = true
  try {
    const params = {
      paperId: form.value.paperId || '',
      paperTitle: form.value.paperTitle,
      paperType: form.value.paperType,
      description: form.value.description,
      duration: isExam.value ? form.value.duration : null,
      status: form.value.status,
      groups: form.value.groups.map(g => ({
        groupName: g.groupName,
        questions: g.questions.map(q => ({
          questionId: q.questionId,
          score: q.score
        }))
      }))
    }

    const res = form.value.paperId ? await updatePaper(params) : await addPaper(params)
    if (res.status === 'success') {
      ElMessage.success(form.value.paperId ? '更新成功' : '新增成功')
      emit('saved')
      handleClose()
    }
  } catch { /* handled */ }
  finally { formLoading.value = false }
}

function handleClose() {
  emit('update:visible', false)
}

function resetForm() {
  form.value = { paperId: '', paperTitle: '', paperType: 1, description: '', duration: 60, status: 1, groups: [] }
  nextTick(() => formRef.value?.clearValidate())
}

watch(() => props.visible, (val) => {
  if (val) {
    if (props.mode === 'edit' && props.paperId) {
      loadDetail()
    } else {
      resetForm()
    }
  } else {
    questionDialogVisible.value = false
    currentGroupIndex.value = -1
    // 延迟重置表单，避免覆盖关闭动画期间新打开的数据
    setTimeout(resetForm, 300)
  }
})
</script>

<template>
  <el-drawer
    :model-value="visible"
    @update:model-value="$emit('update:visible', $event)"
    :title="mode === 'add' ? '新增试卷' : '编辑试卷'"
    size="80%"
    :close-on-click-modal="false"
    direction="rtl"
  >
    <div v-loading="formLoading" class="drawer-body">
      <!-- 左侧：编排区域 -->
      <div class="left-panel">
        <el-form ref="formRef" :model="form" :rules="rules" label-width="80px">
          <el-row :gutter="16">
            <el-col :span="14">
              <el-form-item label="试卷名称" prop="paperTitle">
                <el-input v-model="form.paperTitle" placeholder="请输入试卷名称" />
              </el-form-item>
            </el-col>
            <el-col :span="10">
              <el-form-item label="试卷类型" prop="paperType">
                <el-radio-group v-model="form.paperType">
                  <el-radio :value="1">课后习题</el-radio>
                  <el-radio :value="2">考试试卷</el-radio>
                </el-radio-group>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="16">
            <el-col :span="12">
              <el-form-item label="试卷描述">
                <el-input v-model="form.description" type="textarea" :rows="2" placeholder="请输入试卷描述" />
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item v-if="isExam" label="考试时长">
                <el-input-number v-model="form.duration" :min="1" :max="300" />
                <span style="margin-left:4px;color:#909399;font-size:12px">分钟</span>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="状态">
                <el-radio-group v-model="form.status">
                  <el-radio :value="1">启用</el-radio>
                  <el-radio :value="0">禁用</el-radio>
                </el-radio-group>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>

        <!-- 分组编排 -->
        <div class="groups-section">
          <div class="groups-header">
            <span class="groups-title">题目编排</span>
            <el-button type="primary" size="small" @click="addGroup">+ 添加分组</el-button>
          </div>

          <div v-if="form.groups.length === 0" class="empty-groups">
            暂无分组，请点击"添加分组"开始编排试卷
          </div>

          <div v-for="(group, gi) in form.groups" :key="gi" class="group-card">
            <div class="group-card-header">
              <span class="group-label">{{ chineseNum(gi + 1) }}、</span>
              <el-input
                v-model="group.groupName"
                placeholder="请输入分组名称（如：单选题）"
                class="group-name-input"
              />
              <el-button link type="danger" size="small" @click="removeGroup(gi)">删除分组</el-button>
            </div>

            <div class="group-questions">
              <div
                v-for="(q, qi) in group.questions"
                :key="qi"
                class="question-row"
              >
                <span class="q-num">{{ qi + 1 }}.</span>
                <el-tag size="small">{{ questionTypeMap[q.questionType] || '未知' }}</el-tag>
                <span class="q-title">{{ q.questionTitle }}</span>
                <el-input-number
                  v-model="q.score"
                  :min="1"
                  :max="100"
                  size="small"
                  class="q-score"
                />
                <el-button link type="danger" size="small" @click="removeQuestion(gi, qi)">移除</el-button>
              </div>
              <el-button class="add-question-btn" @click="openQuestionDialog(gi)">
                + 加入题目
              </el-button>
            </div>
          </div>
        </div>
      </div>

      <!-- 右侧：预览区域 -->
      <div class="right-panel">
        <div class="preview-paper">
          <div class="preview-header">
            <h2 class="preview-title">{{ form.paperTitle || '试卷名称' }}</h2>
            <div class="preview-meta">
              <span v-if="isExam">考试时长：{{ form.duration }}分钟</span>
              <span>总分：{{ totalScore }}分</span>
              <span>共 {{ questionCount }} 题</span>
            </div>
            <div v-if="form.description" class="preview-desc">{{ form.description }}</div>
          </div>

          <template v-if="previewItems.length > 0">
            <template v-for="item in previewItems" :key="item.key">
              <!-- 分组标题 -->
              <h3 v-if="item.type === 'group'" class="preview-group-name">
                {{ chineseNum(item.groupNum) }}、{{ item.groupName }}
              </h3>

              <!-- 题目 -->
              <div v-else class="preview-question">
                <div class="pq-header">
                  <span class="pq-num">{{ item.globalIdx }}.</span>
                  <span class="pq-score">({{ item.score }}分)</span>
                  <el-tag size="small">{{ questionTypeMap[item.questionType] || '未知' }}</el-tag>
                  <span class="pq-title">{{ item.questionTitle }}</span>
                </div>

                <div v-if="needOptions(item.questionType) && item.options?.length" class="pq-options">
                  <div
                    v-for="(opt, oi) in item.options"
                    :key="opt.optionId"
                    class="pq-option"
                  >
                    <span class="pq-option-letter">{{ getOptionLetter(oi) }}.</span>
                    <span>{{ opt.optionContent }}</span>
                  </div>
                </div>

                <div v-if="item.questionType === 3 && (!item.options || item.options.length === 0)" class="pq-options">
                  <div class="pq-option">T. 正确</div>
                  <div class="pq-option">F. 错误</div>
                </div>

                <div v-if="item.questionType === 4" class="pq-answer-blank">
                  （简答题）
                </div>
              </div>
            </template>
          </template>

          <div v-else class="preview-empty">
            请在左侧添加分组和题目
          </div>
        </div>
      </div>
    </div>

    <QuestionSelectDialog
      v-model:visible="questionDialogVisible"
      :selected-ids="currentGroupIndex >= 0 ? form.groups[currentGroupIndex]?.questions.map(q => q.questionId) || [] : []"
      @confirm="onQuestionConfirm"
    />

    <template #footer>
      <el-button @click="handleClose">取消</el-button>
      <el-button type="primary" :loading="formLoading" @click="handleSave">保存</el-button>
    </template>
  </el-drawer>
</template>

<style scoped>
.drawer-body {
  display: flex;
  height: calc(100vh - 120px);
  gap: 20px;
}

.left-panel {
  flex: 1;
  overflow-y: auto;
  padding-right: 4px;
}

.right-panel {
  width: 45%;
  min-width: 420px;
  overflow-y: auto;
  border-left: 1px solid #ebeef5;
  padding-left: 20px;
}

.groups-section {
  margin-top: 12px;
}

.groups-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 12px;
}

.groups-title {
  font-size: 14px;
  font-weight: 600;
  color: #303133;
}

.empty-groups {
  color: #909399;
  font-size: 13px;
  text-align: center;
  padding: 40px 0;
}

.group-card {
  background: #fafafa;
  border: 1px solid #ebeef5;
  border-radius: 8px;
  padding: 12px;
  margin-bottom: 12px;
}

.group-card-header {
  display: flex;
  align-items: center;
  gap: 8px;
  margin-bottom: 8px;
}

.group-label {
  font-weight: 700;
  color: #409eff;
  white-space: nowrap;
}

.group-name-input {
  flex: 1;
}

.group-questions {
  padding-left: 8px;
}

.question-row {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 6px 8px;
  margin-bottom: 4px;
  background: #fff;
  border-radius: 4px;
  border: 1px solid #ebeef5;
}

.q-num {
  font-weight: 600;
  color: #909399;
  min-width: 24px;
}

.q-title {
  flex: 1;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  font-size: 13px;
  color: #303133;
}

.q-score {
  width: 70px;
}

.add-question-btn {
  width: 100%;
  border-style: dashed;
  margin-top: 4px;
}

/* 预览 */
.preview-paper {
  padding: 16px 0;
}

.preview-header {
  text-align: center;
  border-bottom: 2px solid #333;
  padding-bottom: 16px;
  margin-bottom: 20px;
}

.preview-title {
  font-size: 22px;
  margin-bottom: 8px;
  color: #000;
  font-weight: 700;
}

.preview-meta {
  font-size: 13px;
  color: #666;
  display: flex;
  justify-content: center;
  gap: 24px;
}

.preview-desc {
  margin-top: 8px;
  font-size: 13px;
  color: #666;
}

.preview-group-name {
  font-size: 16px;
  font-weight: 700;
  color: #303133;
  margin: 20px 0 12px;
}

.preview-question {
  margin-bottom: 16px;
}

.pq-header {
  display: flex;
  align-items: baseline;
  gap: 6px;
  margin-bottom: 6px;
}

.pq-num {
  font-weight: 700;
  color: #303133;
  font-size: 14px;
}

.pq-score {
  color: #909399;
  font-size: 12px;
  white-space: nowrap;
}

.pq-title {
  color: #303133;
  font-size: 14px;
}

.pq-options {
  padding-left: 24px;
}

.pq-option {
  padding: 2px 0;
  font-size: 13px;
  color: #606266;
}

.pq-option-letter {
  font-weight: 600;
  margin-right: 4px;
}

.pq-answer-blank {
  padding-left: 24px;
  font-size: 13px;
  color: #909399;
  font-style: italic;
}

.preview-empty {
  text-align: center;
  color: #c0c4cc;
  padding: 80px 0;
  font-size: 14px;
}
</style>

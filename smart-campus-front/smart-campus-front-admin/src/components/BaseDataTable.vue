<script setup>
import { ref, computed, onMounted, onUnmounted, watch } from 'vue'

defineOptions({ name: 'BaseDataTable' })

const props = defineProps({
  columns: {
    type: Array,
    required: true
  },
  data: {
    type: Object,
    required: true
  },
  border: {
    type: Boolean,
    default: false
  },
  showSelection: {
    type: Boolean,
    default: false
  },
  loading: {
    type: Boolean,
    default: false
  },
  rowKey: {
    type: String,
    default: 'id'
  },
  size: {
    type: String,
    default: 'default'
  }
})

const emit = defineEmits(['page-change', 'selection-change'])

const rootRef = ref(null)
const tableWrapRef = ref(null)
const tableRef = ref(null)
const tableHeight = ref(400)

let resizeObserver = null

function calcHeight() {
  if (!tableWrapRef.value) return
  tableHeight.value = tableWrapRef.value.clientHeight
}

onMounted(() => {
  calcHeight()
  resizeObserver = new ResizeObserver(() => {
    calcHeight()
  })
  if (tableWrapRef.value) {
    resizeObserver.observe(tableWrapRef.value)
  }
})

onUnmounted(() => {
  resizeObserver?.disconnect()
})

const innerPageNo = ref(props.data.pageNo)
const innerPageSize = ref(props.data.pageSize)
const total = computed(() => props.data.totalCount || 0)

watch(() => props.data.pageNo, (v) => { innerPageNo.value = v })
watch(() => props.data.pageSize, (v) => { innerPageSize.value = v })

function handlePageChange(page) {
  innerPageNo.value = page
  emit('page-change', { pageNo: page, pageSize: innerPageSize.value })
}

function handleSizeChange(size) {
  innerPageSize.value = size
  innerPageNo.value = 1
  emit('page-change', { pageNo: 1, pageSize: size })
}

function handleSelectionChange(rows) {
  emit('selection-change', rows)
}

function clearSelection() {
  tableRef.value?.clearSelection()
}

defineExpose({ clearSelection })
</script>

<template>
  <div ref="rootRef" class="base-data-table">
    <div ref="tableWrapRef" class="table-wrap">
      <el-table
        ref="tableRef"
        :data="data.list"
        :border="border"
        :height="tableHeight"
        :row-key="rowKey"
        :default-expand-all="true"
        v-loading="loading"
        :size="size"
        style="width: 100%"
        @selection-change="handleSelectionChange"
      >
        <el-table-column
          v-if="showSelection"
          type="selection"
          width="50"
          fixed="left"
          :reserve-selection="true"
        />
        <el-table-column
          v-for="col in columns"
          :key="col.prop"
          :prop="col.prop"
          :label="col.label"
          :width="col.width"
          :min-width="col.minWidth"
          :fixed="col.fixed"
          :align="col.align || 'left'"
          :show-overflow-tooltip="col.showOverflowTooltip !== false"
        >
          <template #default="{ row, column, $index }">
            <slot :name="col.prop" :row="row" :column="column" :$index="$index">
              {{ row[col.prop] }}
            </slot>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div class="pagination-wrap">
      <el-pagination
        v-model:current-page="innerPageNo"
        v-model:page-size="innerPageSize"
        :page-sizes="[10, 15, 20, 50, 100]"
        :total="total"
        :disabled="loading"
        layout="total, sizes, prev, pager, next, jumper"
        background
        @current-change="handlePageChange"
        @size-change="handleSizeChange"
      />
    </div>
  </div>
</template>

<style scoped>
.base-data-table {
  height: 100%;
  display: flex;
  flex-direction: column;
}

.table-wrap {
  flex: 1;
  min-height: 0;
}

.pagination-wrap {
  display: flex;
  justify-content: flex-end;
  padding: 12px 0 0;
  flex-shrink: 0;
}
</style>

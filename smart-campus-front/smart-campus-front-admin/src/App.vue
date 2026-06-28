<script setup>
import { computed } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { menuConfig } from '@/router/menu'

const router = useRouter()
const route = useRoute()

const activePrimary = computed(() => {
  const path = route.path
  for (const item of menuConfig) {
    if (path.startsWith('/' + item.code)) return item.code
  }
  return 'home'
})

const secondaryMenus = computed(() => {
  const menu = menuConfig.find((m) => m.code === activePrimary.value)
  return menu ? menu.children : []
})

const currentMenuName = computed(() => {
  const menu = menuConfig.find((m) => m.code === activePrimary.value)
  return menu ? menu.name : ''
})

function handlePrimaryClick(item) {
  if (item.children && item.children.length > 0) {
    router.push(item.children[0].path)
  }
}

function isSecondaryActive(path) {
  return route.path === path
}

function handleSecondaryClick(item) {
  router.push(item.path)
}
</script>

<template>
  <div class="framework">
    <!-- 顶部导航 -->
    <header class="header">
      <div class="header-left">
        <div class="logo">
          <el-icon :size="24" color="#2b7be4"><School /></el-icon>
        </div>
        <span class="project-name">智慧校园后台</span>
      </div>

      <nav class="header-nav">
        <div
          v-for="item in menuConfig"
          :key="item.code"
          class="nav-item"
          :class="{ active: activePrimary === item.code }"
          @click="handlePrimaryClick(item)"
        >
          {{ item.name }}
        </div>
      </nav>

      <div class="header-right">
        <el-dropdown trigger="click">
          <span class="user-info">
            <el-icon :size="18"><UserFilled /></el-icon>
            <span class="username">管理员</span>
            <el-icon :size="12"><ArrowDown /></el-icon>
          </span>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item>个人设置</el-dropdown-item>
              <el-dropdown-item divided>退出登录</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </div>
    </header>

    <!-- 主体区域 -->
    <div class="main-area">
      <!-- 左侧二级菜单卡片 -->
      <aside class="sidebar-card">
        <div class="sidebar-title">{{ currentMenuName }}</div>
        <div class="sidebar-menu">
          <div
            v-for="item in secondaryMenus"
            :key="item.code"
            class="menu-item"
            :class="{ active: isSecondaryActive(item.path) }"
            @click="handleSecondaryClick(item)"
          >
            {{ item.name }}
          </div>
        </div>
      </aside>

      <!-- 右侧内容区域卡片 -->
      <section class="content-card">
        <router-view />
      </section>
    </div>
  </div>
</template>

<style scoped>
.framework {
  display: flex;
  flex-direction: column;
  height: 100vh;
  overflow: hidden;
}

/* ===== 顶部导航 ===== */
.header {
  display: flex;
  align-items: center;
  height: 56px;
  padding: 0 24px;
  background: #fff;
  box-shadow: 0 1px 4px rgba(0, 0, 0, 0.08);
  flex-shrink: 0;
  z-index: 100;
}

.header-left {
  display: flex;
  align-items: center;
  gap: 10px;
  width: 220px;
  flex-shrink: 0;
}

.logo {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 36px;
  height: 36px;
  background: linear-gradient(135deg, #d6e4ff, #e8f0fe);
  border-radius: 8px;
}

.project-name {
  font-size: 17px;
  font-weight: 600;
  color: #1d2b3a;
  letter-spacing: 0.5px;
  white-space: nowrap;
}

.header-nav {
  display: flex;
  align-items: stretch;
  flex: 1;
  height: 100%;
  gap: 0;
}

.nav-item {
  position: relative;
  display: flex;
  align-items: center;
  padding: 0 20px;
  font-size: 15px;
  color: #606266;
  cursor: pointer;
  transition: color 0.2s;
  white-space: nowrap;
  user-select: none;
}

.nav-item:hover {
  color: #2b7be4;
}

.nav-item.active {
  color: #2b7be4;
  font-weight: 500;
}

.nav-item.active::after {
  content: '';
  position: absolute;
  bottom: 0;
  left: 20px;
  right: 20px;
  height: 2px;
  background: #2b7be4;
  border-radius: 1px 1px 0 0;
}

.header-right {
  display: flex;
  align-items: center;
  justify-content: flex-end;
  width: 180px;
  flex-shrink: 0;
}

.user-info {
  display: flex;
  align-items: center;
  gap: 6px;
  color: #606266;
  cursor: pointer;
  font-size: 14px;
  padding: 4px 8px;
  border-radius: 4px;
  transition: background 0.2s;
}

.user-info:hover {
  background: #f5f7fa;
}

.username {
  max-width: 80px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

/* ===== 主体区域 ===== */
.main-area {
  display: flex;
  flex: 1;
  padding: 16px;
  gap: 16px;
  overflow: hidden;
  min-height: 0;
}

/* ===== 左侧卡片 ===== */
.sidebar-card {
  width: 220px;
  flex-shrink: 0;
  background: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.06);
  display: flex;
  flex-direction: column;
  overflow: hidden;
}

.sidebar-title {
  font-size: 16px;
  font-weight: 600;
  color: #303133;
  padding: 16px 20px 14px;
  border-bottom: 1px solid #ebeef5;
}

.sidebar-menu {
  flex: 1;
  overflow-y: auto;
  padding: 8px 0;
}

.menu-item {
  padding: 12px 20px;
  font-size: 14px;
  color: #606266;
  cursor: pointer;
  transition: all 0.2s;
  user-select: none;
  margin: 2px 12px;
  border-radius: 6px;
}

.menu-item:hover {
  background: #f5f7fa;
  color: #2b7be4;
}

.menu-item.active {
  background: rgb(198, 222, 254);
  color: #2b7be4;
  font-weight: 500;
}

/* ===== 右侧内容卡片 ===== */
.content-card {
  flex: 1;
  background: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.06);
  padding: 20px;
  overflow-y: auto;
  min-width: 0;
}
</style>

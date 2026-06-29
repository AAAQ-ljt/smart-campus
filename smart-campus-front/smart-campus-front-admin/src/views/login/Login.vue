<script setup>
import { ref, onMounted, computed } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { md5 } from 'js-md5'
import { getCheckCode, login } from '@/api/login'
import { useUserStore } from '@/stores/user'
import { REDIRECT_KEY } from '@/router'
import bgImg from '@/assets/bg.jpg'

const router = useRouter()
const userStore = useUserStore()

const ACCOUNTS_KEY = 'rememberedAdminAccounts'

const loading = ref(false)
const showInput = ref(false)
const rememberedAccounts = ref([])
const selectedAccount = ref('')

const formData = ref({
  userNo: '',
  password: '',
  checkCode: '',
  codeKey: ''
})

const captchaImage = ref('')

function loadRememberedAccounts() {
  try {
    const raw = localStorage.getItem(ACCOUNTS_KEY)
    if (raw) {
      rememberedAccounts.value = JSON.parse(raw)
    } else {
      rememberedAccounts.value = [{ userNo: 'admin', password: '123456' }]
      localStorage.setItem(ACCOUNTS_KEY, JSON.stringify(rememberedAccounts.value))
    }
  } catch {
    rememberedAccounts.value = [{ userNo: 'admin', password: '123456' }]
  }
}

function saveAccount(userNo, password) {
  const accounts = rememberedAccounts.value.filter(a => a.userNo !== userNo)
  accounts.unshift({ userNo, password })
  if (accounts.length > 5) accounts.pop()
  rememberedAccounts.value = accounts
  localStorage.setItem(ACCOUNTS_KEY, JSON.stringify(accounts))
}

function removeAccount(userNo) {
  rememberedAccounts.value = rememberedAccounts.value.filter(a => a.userNo !== userNo)
  localStorage.setItem(ACCOUNTS_KEY, JSON.stringify(rememberedAccounts.value))
  if (selectedAccount.value === userNo) {
    selectedAccount.value = ''
    formData.value.userNo = ''
    formData.value.password = ''
  }
}

const hasAccounts = computed(() => rememberedAccounts.value.length > 0)
const showAccountText = computed(() => hasAccounts.value && !showInput.value)

function selectAccount(acc) {
  selectedAccount.value = acc.userNo
  formData.value.userNo = acc.userNo
  formData.value.password = acc.password || ''
}

function switchToInput() {
  showInput.value = true
  selectedAccount.value = ''
  formData.value.userNo = ''
  formData.value.password = ''
}

function backToAccounts() {
  showInput.value = false
  selectedAccount.value = ''
  formData.value.userNo = ''
}

async function fetchCaptcha() {
  try {
    const params = {}
    if (formData.value.codeKey) {
      params.codeKey = formData.value.codeKey
    }
    const res = await getCheckCode(params)
    if (res.status === 'success' && res.data) {
      formData.value.codeKey = res.data.codeKey
      captchaImage.value = res.data.captchaImage
    }
  } catch { /* 静默处理 */ }
}

async function handleLogin() {
  if (!formData.value.userNo) {
    ElMessage.warning('请输入工号或学号')
    return
  }
  if (!formData.value.password) {
    ElMessage.warning('请输入密码')
    return
  }
  if (!formData.value.checkCode) {
    ElMessage.warning('请输入验证码')
    return
  }
  loading.value = true
  try {
    const res = await login({
      userNo: formData.value.userNo,
      password: md5(formData.value.password),
      codeKey: formData.value.codeKey,
      checkCode: formData.value.checkCode
    })
    if (res.status === 'success' && res.data) {
      userStore.setLogin(res.data.token, res.data.userInfo)
      saveAccount(formData.value.userNo, formData.value.password)
      ElMessage.success('登录成功')
      const redirect = sessionStorage.getItem(REDIRECT_KEY)
      sessionStorage.removeItem(REDIRECT_KEY)
      router.push(redirect || '/')
    }
  } catch {
    formData.value.checkCode = ''
    fetchCaptcha()
  } finally {
    loading.value = false
  }
}

function handleKeyup(event) {
  if (event.key === 'Enter') handleLogin()
}

onMounted(() => {
  loadRememberedAccounts()
  fetchCaptcha()
})
</script>

<template>
  <div class="login-page" :style="{ backgroundImage: `url(${bgImg})` }">
    <div class="login-overlay">
      <div class="login-card">
        <h2 class="login-title">智慧校园后台</h2>

        <div class="login-form" autocomplete="off">
          <!-- 账号区域 -->
          <div class="form-item">
            <div class="input-wrapper">
              <span class="input-icon iconfont icon-user"></span>
              <template v-if="showAccountText">
                <div class="account-tags">
                  <el-tag
                    v-for="acc in rememberedAccounts"
                    :key="acc.userNo"
                    :type="selectedAccount === acc.userNo ? '' : 'info'"
                    :effect="selectedAccount === acc.userNo ? 'dark' : 'light'"
                    closable
                    size="default"
                    class="account-tag-item"
                    @click="selectAccount(acc)"
                    @close="removeAccount(acc.userNo)"
                  >
                    {{ acc.userNo }}
                  </el-tag>
                  <el-button type="primary" link size="small" @click="switchToInput">切换账号</el-button>
                </div>
              </template>
              <template v-else>
                <el-input
                  v-model="formData.userNo"
                  placeholder="工号 / 学号"
                  autocomplete="off"
                  @keyup.enter="handleLogin"
                >
                  <template v-if="hasAccounts" #suffix>
                    <el-button type="primary" link size="small" @click="backToAccounts">已存账号</el-button>
                  </template>
                </el-input>
              </template>
            </div>
          </div>

          <!-- 密码 -->
          <div class="form-item">
            <div class="input-wrapper">
              <span class="input-icon iconfont icon-password"></span>
              <el-input
                v-model="formData.password"
                type="password"
                placeholder="请输入密码"
                show-password
                autocomplete="new-password"
                @keyup.enter="handleLogin"
              />
            </div>
          </div>

          <!-- 验证码 -->
          <div class="form-item captcha-row">
            <div class="input-wrapper captcha-input">
              <span class="input-icon iconfont icon-checkcode"></span>
              <el-input
                v-model="formData.checkCode"
                placeholder="验证码"
                maxlength="4"
                @keyup.enter="handleLogin"
              />
            </div>
            <img
              :src="captchaImage"
              alt="验证码"
              class="captcha-img"
              @click="fetchCaptcha"
              title="点击刷新验证码"
            />
          </div>

          <el-button
            type="primary"
            class="login-btn"
            :loading="loading"
            @click="handleLogin"
          >
            登 录
          </el-button>

          <div class="register-link">
            还没有账号？<el-button type="primary" link @click="router.push('/account/register')">立即注册</el-button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.login-page {
  width: 100vw;
  height: 100vh;
  background-size: cover;
  background-position: center;
  background-repeat: no-repeat;
  position: fixed;
  top: 0;
  left: 0;
}

.login-overlay {
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.15);
  display: flex;
  align-items: center;
  justify-content: center;
}

.login-card {
  width: 420px;
  background: rgba(255, 255, 255, 0.72);
  backdrop-filter: blur(20px);
  -webkit-backdrop-filter: blur(20px);
  border: 1px solid rgba(255, 255, 255, 0.5);
  border-radius: 16px;
  padding: 44px 40px 40px;
  box-shadow: 0 8px 32px rgba(0, 0, 0, 0.1);
}

.login-title {
  font-size: 24px;
  font-weight: 600;
  color: #303133;
  text-align: center;
  margin: 0 0 32px 0;
  letter-spacing: 3px;
}

.login-form {
  display: flex;
  flex-direction: column;
  gap: 18px;
}

.form-item {
  width: 100%;
}

.input-wrapper {
  display: flex;
  align-items: center;
  border: 1px solid rgba(0, 0, 0, 0.12);
  border-radius: 8px;
  transition: border-color 0.25s, box-shadow 0.25s;
  overflow: hidden;
  background: rgba(255, 255, 255, 0.55);
}

.input-wrapper:focus-within {
  border-color: #2b7be4;
  box-shadow: 0 0 0 3px rgba(43, 123, 228, 0.12);
}

.input-icon {
  width: 44px;
  text-align: center;
  font-size: 18px;
  color: #909399;
  flex-shrink: 0;
}

.input-wrapper :deep(.el-input__wrapper) {
  border: none !important;
  box-shadow: none !important;
  padding-left: 0;
  background: transparent !important;
}

.input-wrapper :deep(.el-input__inner) {
  color: #303133;
}

.input-wrapper :deep(.el-input__inner::placeholder) {
  color: #b0b3bb;
}

.input-wrapper :deep(.el-input) {
  flex: 1;
}

/* 账号标签 */
.account-tags {
  flex: 1;
  display: flex;
  flex-wrap: wrap;
  gap: 6px;
  padding: 8px 12px 8px 0;
  align-items: center;
}

.account-tag-item {
  cursor: pointer;
}

/* 验证码行 */
.captcha-row {
  display: flex;
  gap: 12px;
}

.captcha-input {
  flex: 1;
}

.captcha-img {
  width: 130px;
  height: 42px;
  border-radius: 8px;
  cursor: pointer;
  flex-shrink: 0;
  border: 1px solid rgba(0, 0, 0, 0.1);
}

.login-btn {
  width: 100%;
  height: 46px;
  font-size: 16px;
  letter-spacing: 4px;
  margin-top: 6px;
  border-radius: 8px;
}

.register-link {
  text-align: center;
  font-size: 14px;
  color: #909399;
}
</style>

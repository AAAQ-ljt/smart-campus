<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { md5 } from 'js-md5'
import { register } from '@/api/login'
import bgImg from '@/assets/bg.jpg'

const router = useRouter()

const loading = ref(false)
const formData = ref({
  userNo: '',
  realName: '',
  phone: '',
  password: '',
  confirmPassword: ''
})

const rules = {
  userNo: [{ required: true, message: '请输入工号', trigger: 'blur' }],
  realName: [{ required: true, message: '请输入姓名', trigger: 'blur' }],
  phone: [
    { required: true, message: '请输入手机号', trigger: 'blur' },
    { pattern: /^1\d{10}$/, message: '手机号格式不正确', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, message: '密码至少6位', trigger: 'blur' }
  ],
  confirmPassword: [
    { required: true, message: '请确认密码', trigger: 'blur' },
    {
      validator: (rule, value, callback) => {
        if (value !== formData.value.password) {
          callback(new Error('两次密码不一致'))
        } else {
          callback()
        }
      },
      trigger: 'blur'
    }
  ]
}

const formRef = ref(null)

async function handleRegister() {
  const valid = await formRef.value.validate().catch(() => false)
  if (!valid) return

  loading.value = true
  try {
    await register({
      userNo: formData.value.userNo,
      realName: formData.value.realName,
      phone: formData.value.phone,
      password: md5(formData.value.password)
    })
    ElMessage.success('注册成功，请登录')
    router.push('/account/login')
  } catch {
    // 拦截器已弹错
  } finally {
    loading.value = false
  }
}
</script>

<template>
  <div class="register-page" :style="{ backgroundImage: `url(${bgImg})` }">
    <div class="register-overlay">
      <div class="register-card">
        <h2 class="register-title">注册账号</h2>

        <div class="register-form">
          <el-form ref="formRef" :model="formData" :rules="rules" label-width="80px" autocomplete="off" @submit.prevent>
            <el-form-item label="工号" prop="userNo">
              <div class="input-wrapper">
                <span class="input-icon iconfont icon-user"></span>
                <el-input v-model="formData.userNo" placeholder="工号（登录用）" maxlength="20" autocomplete="off" />
              </div>
            </el-form-item>
            <el-form-item label="姓名" prop="realName">
              <div class="input-wrapper">
                <span class="input-icon iconfont icon-user"></span>
                <el-input v-model="formData.realName" placeholder="真实姓名" maxlength="20" autocomplete="off" />
              </div>
            </el-form-item>
            <el-form-item label="手机号" prop="phone">
              <div class="input-wrapper">
                <span class="input-icon iconfont icon-phone"></span>
                <el-input v-model="formData.phone" placeholder="11位手机号" maxlength="11" autocomplete="off" />
              </div>
            </el-form-item>
            <el-form-item label="密码" prop="password">
              <div class="input-wrapper">
                <span class="input-icon iconfont icon-password"></span>
                <el-input v-model="formData.password" type="password" placeholder="至少6位" show-password autocomplete="new-password" />
              </div>
            </el-form-item>
            <el-form-item label="确认密码" prop="confirmPassword">
              <div class="input-wrapper">
                <span class="input-icon iconfont icon-password"></span>
                <el-input v-model="formData.confirmPassword" type="password" placeholder="再次输入密码" show-password autocomplete="new-password" />
              </div>
            </el-form-item>

            <el-button
              type="primary"
              class="register-btn"
              :loading="loading"
              @click="handleRegister"
            >
              注 册
            </el-button>

            <div class="login-link">
              已有账号？<el-button type="primary" link @click="router.push('/account/login')">立即登录</el-button>
            </div>
          </el-form>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.register-page {
  width: 100vw;
  height: 100vh;
  background-size: cover;
  background-position: center;
  background-repeat: no-repeat;
  position: fixed;
  top: 0;
  left: 0;
}

.register-overlay {
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.15);
  display: flex;
  align-items: center;
  justify-content: center;
}

.register-card {
  width: 440px;
  background: rgba(255, 255, 255, 0.72);
  backdrop-filter: blur(20px);
  -webkit-backdrop-filter: blur(20px);
  border: 1px solid rgba(255, 255, 255, 0.5);
  border-radius: 16px;
  padding: 40px 36px 36px;
  box-shadow: 0 8px 32px rgba(0, 0, 0, 0.1);
}

.register-title {
  font-size: 24px;
  font-weight: 600;
  color: #303133;
  text-align: center;
  margin: 0 0 30px 0;
  letter-spacing: 3px;
}

.register-form {
  display: flex;
  flex-direction: column;
}

.input-wrapper {
  display: flex;
  align-items: center;
  border: 1px solid rgba(0, 0, 0, 0.12);
  border-radius: 8px;
  transition: border-color 0.25s, box-shadow 0.25s;
  overflow: hidden;
  width: 100%;
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

:deep(.el-form-item__label) {
  color: #303133;
}

:deep(.el-form-item__content) {
  flex: 1;
}

.register-btn {
  width: 100%;
  height: 46px;
  font-size: 16px;
  letter-spacing: 4px;
  margin-top: 10px;
  border-radius: 8px;
}

.login-link {
  text-align: center;
  font-size: 14px;
  color: #909399;
  margin-top: 14px;
}
</style>

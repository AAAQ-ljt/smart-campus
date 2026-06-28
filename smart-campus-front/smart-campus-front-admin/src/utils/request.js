import axios from 'axios'
import { getToken, removeToken } from './token'
import { ElMessage } from 'element-plus'

const request = axios.create({
  baseURL: '/api',
  timeout: 30000
})

request.interceptors.request.use(
  (config) => {
    const token = getToken()
    if (token) {
      config.headers.adminToken = token
    }
    return config
  },
  (error) => Promise.reject(error)
)

request.interceptors.response.use(
  (response) => {
    const res = response.data
    if (res.status === 'success') {
      return res
    }
    ElMessage.error(res.info || '请求失败')
    return Promise.reject(new Error(res.info || '请求失败'))
  },
  (error) => {
    if (error.response) {
      const { status } = error.response
      if (status === 401) {
        removeToken()
        const redirect = encodeURIComponent(window.location.href)
        window.location.href = `/login?redirect=${redirect}`
        return Promise.reject(error)
      }
      ElMessage.error(error.response.data?.info || '网络错误')
    } else {
      ElMessage.error('网络连接异常')
    }
    return Promise.reject(error)
  }
)

export default request

import request from '@/utils/request'

export function getCheckCode(data) {
  return request.post('/login/getCheckCode', data || {})
}

export function login(data) {
  return request.post('/login/login', data)
}

export function logout() {
  return request.post('/login/logout')
}

export function register(data) {
  return request.post('/login/register', data)
}

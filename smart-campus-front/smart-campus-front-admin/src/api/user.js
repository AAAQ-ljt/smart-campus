import request from '@/utils/request'

export function loadUserInfoList(params) {
  return request.post('/userInfo/loadUserInfoList', params || {})
}

export function addUserInfo(data) {
  return request.post('/userInfo/addUserInfo', data)
}

export function updateUserInfo(data) {
  return request.post('/userInfo/updateUserInfo', data)
}

export function deleteUserInfo(userId) {
  return request.post('/userInfo/deleteUserInfo', null, { params: { userId } })
}

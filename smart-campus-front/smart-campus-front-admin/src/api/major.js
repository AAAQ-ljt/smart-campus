import request from '@/utils/request'

export function loadMajorInfoList(params) {
  return request.post('/majorInfo/loadMajorInfoList', params || {})
}

export function addMajorInfo(data) {
  return request.post('/majorInfo/addMajorInfo', data)
}

export function updateMajorInfo(data) {
  return request.post('/majorInfo/updateMajorInfo', data)
}

export function deleteMajorInfo(majorId) {
  return request.post('/majorInfo/deleteMajorInfo', null, { params: { majorId } })
}

export function sortMajorInfo(majorIds) {
  return request.post('/majorInfo/sortMajorInfo', majorIds)
}

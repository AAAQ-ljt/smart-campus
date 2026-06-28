import request from '@/utils/request'

export function loadClassInfoList(params) {
  return request.post('/classInfo/loadClassInfoList', params || {})
}

export function addClassInfo(data) {
  return request.post('/classInfo/addClassInfo', data)
}

export function updateClassInfo(data) {
  return request.post('/classInfo/updateClassInfo', data)
}

export function deleteClassInfo(classId) {
  return request.post('/classInfo/deleteClassInfo', null, { params: { classId } })
}

export function sortClassInfo(classIds) {
  return request.post('/classInfo/sortClassInfo', classIds)
}

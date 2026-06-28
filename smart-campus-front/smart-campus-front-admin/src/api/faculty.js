import request from '@/utils/request'

export function loadFacultyInfoList(params) {
  return request.post('/facultyInfo/loadFacultyInfoList', params || {})
}

export function addFacultyInfo(data) {
  return request.post('/facultyInfo/addFacultyInfo', data)
}

export function updateFacultyInfo(data) {
  return request.post('/facultyInfo/updateFacultyInfo', data)
}

export function deleteFacultyInfo(facultyId) {
  return request.post('/facultyInfo/deleteFacultyInfo', null, { params: { facultyId } })
}

export function sortFacultyInfo(facultyIds) {
  return request.post('/facultyInfo/sortFacultyInfo', facultyIds)
}

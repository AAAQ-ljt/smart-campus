import request from '@/utils/request'

export function loadExerciseList(data) {
  return request.post('/questionInfo/loadDataList', data)
}

export function getExerciseDetail(questionId) {
  return request.post('/questionInfo/detail', null, { params: { questionId } })
}

export function addExercise(data) {
  return request.post('/questionInfo/add', data)
}

export function updateExercise(data) {
  return request.post('/questionInfo/update', data)
}

export function deleteExercise(questionId) {
  return request.post('/questionInfo/delete', null, { params: { questionId } })
}

import request from '@/utils/request'

export function loadPaperList(data) {
  return request.post('/paperInfo/loadDataList', data)
}

export function getPaperDetail(paperId) {
  return request.post('/paperInfo/detail', null, { params: { paperId } })
}

export function addPaper(data) {
  return request.post('/paperInfo/add', data)
}

export function updatePaper(data) {
  return request.post('/paperInfo/update', data)
}

export function deletePaper(paperId) {
  return request.post('/paperInfo/delete', null, { params: { paperId } })
}

export function batchQuestionOptions(questionIds) {
  return request.post('/paperInfo/batchQuestionOptions', { questionIds })
}

import request from '@/utils/request'

export function loadResourceTree() {
  return request.post('/resourceInfo/loadResourceTree')
}

export function loadResourceList(parentId) {
  return request.post('/resourceInfo/loadResourceList', null, {
    params: { parentId: parentId || '0' }
  })
}

export function addResource(data) {
  return request.post('/resourceInfo/addResource', data)
}

export function updateResource(data) {
  return request.post('/resourceInfo/updateResource', data)
}

export function deleteResource(resourceId) {
  return request.post('/resourceInfo/deleteResource', null, {
    params: { resourceId }
  })
}

export function sortResources(resourceIds) {
  return request.post('/resourceInfo/sortResources', resourceIds)
}

export function moveResource(resourceId, targetParentId) {
  return request.post('/resourceInfo/moveResource', null, {
    params: { resourceId, targetParentId }
  })
}

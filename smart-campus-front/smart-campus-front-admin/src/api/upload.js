import request from '@/utils/request'
import { getToken } from '@/utils/token'

const CHUNK_SIZE = 2 * 1024 * 1024

function detectResourceType(ext) {
  const videoExts = ['.mp4', '.avi', '.mov', '.mkv', '.flv', '.wmv', '.webm']
  const imageExts = ['.jpg', '.jpeg', '.png', '.gif', '.bmp', '.webp', '.svg']
  const docExts = ['.doc', '.docx', '.pdf', '.ppt', '.pptx', '.xls', '.xlsx', '.txt', '.csv']
  const archiveExts = ['.zip', '.rar', '.7z', '.tar', '.gz']
  if (videoExts.includes(ext)) return 1
  if (imageExts.includes(ext)) return 2
  if (docExts.includes(ext)) return 3
  if (archiveExts.includes(ext)) return 4
  return 5
}

// initUpload 走 axios（普通 JSON 调用）
export function initUpload(params) {
  return request.post('/file/initUpload', null, { params })
}

// uploadChunk 直接用 fetch，绕过 axios 的 FormData 处理问题
export async function uploadChunk(uploadId, chunkIndex, blob) {
  const form = new FormData()
  form.append('uploadId', uploadId)
  form.append('chunkIndex', String(chunkIndex))
  form.append('chunk', blob, 'chunk_' + chunkIndex)

  const token = getToken()
  const headers = {}
  if (token) headers.adminToken = token

  const res = await fetch('/api/file/uploadChunk', {
    method: 'POST',
    headers,
    body: form
  })
  const json = await res.json()
  if (json.status !== 'success') {
    throw new Error(json.info || '分片上传失败')
  }
  return json
}

export function cancelUpload(uploadId) {
  return request.post('/file/cancelUpload', null, { params: { uploadId } })
}

export async function chunkedUpload(file, parentId, onProgress, signal, onInit) {
  const totalChunks = Math.ceil(file.size / CHUNK_SIZE)
  const ext = '.' + (file.name.split('.').pop() || '').toLowerCase()

  const initRes = await initUpload({
    originalName: file.name,
    fileExt: ext,
    fileSize: file.size,
    mimeType: file.type || 'application/octet-stream',
    resourceType: detectResourceType(ext),
    parentId,
    totalChunks
  })
  if (initRes.status !== 'success') throw new Error(initRes.info || '初始化失败')
  const uploadId = initRes.data.uploadId
  if (onInit) onInit(uploadId)

  for (let i = 0; i < totalChunks; i++) {
    if (signal?.aborted) throw new DOMException('取消', 'AbortError')

    const blob = file.slice(i * CHUNK_SIZE, Math.min((i + 1) * CHUNK_SIZE, file.size))

    let ok = false
    let lastErr = null
    for (let r = 0; r < 3 && !ok; r++) {
      try {
        await uploadChunk(uploadId, i, blob)
        ok = true
      } catch (e) {
        lastErr = e
        if (r < 2) await new Promise(resolve => setTimeout(resolve, 1000))
      }
    }
    if (!ok) throw lastErr || new Error('分片上传失败')

    if (onProgress) onProgress(Math.round(((i + 1) / totalChunks) * 100))
  }

  return uploadId
}

import request from '@/utils/request'

// 查询工会培训列表
export function listTrain(query) {
  return request({
    url: '/party/train/list',
    method: 'get',
    params: query
  })
}

// 查询工会培训详细
export function getTrain(trainId) {
  return request({
    url: '/party/train/' + trainId,
    method: 'get'
  })
}

// 新增工会培训
export function addTrain(data) {
  return request({
    url: '/party/train',
    method: 'post',
    data: data
  })
}

// 修改工会培训
export function updateTrain(data) {
  return request({
    url: '/party/train',
    method: 'put',
    data: data
  })
}

// 删除工会培训
export function delTrain(trainId) {
  return request({
    url: '/party/train/' + trainId,
    method: 'delete'
  })
}

// 导出工会培训
export function exportTrain(query) {
  return request({
    url: '/party/train/export',
    method: 'get',
    params: query
  })
}

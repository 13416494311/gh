import request from '@/utils/request'

// 查询工作经历列表
export function listWork(query) {
  return request({
    url: '/member/work/list',
    method: 'get',
    params: query
  })
}

// 查询工作经历详细
export function getWork(workId) {
  return request({
    url: '/member/work/' + workId,
    method: 'get'
  })
}

// 新增工作经历
export function addWork(data) {
  return request({
    url: '/member/work',
    method: 'post',
    data: data
  })
}

// 修改工作经历
export function updateWork(data) {
  return request({
    url: '/member/work',
    method: 'put',
    data: data
  })
}

// 删除工作经历
export function delWork(workId) {
  return request({
    url: '/member/work/' + workId,
    method: 'delete'
  })
}

// 导出工作经历
export function exportWork(query) {
  return request({
    url: '/member/work/export',
    method: 'get',
    params: query
  })
}
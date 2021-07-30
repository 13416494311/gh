import request from '@/utils/request'

// 查询工会述职评议列表
export function listAssessor(query) {
  return request({
    url: '/party/assessor/list',
    method: 'get',
    params: query
  })
}

// 查询工会述职评议详细
export function getAssessor(id) {
  return request({
    url: '/party/assessor/' + id,
    method: 'get'
  })
}

// 新增工会述职评议
export function addAssessor(data) {
  return request({
    url: '/party/assessor',
    method: 'post',
    data: data
  })
}

// 修改工会述职评议
export function updateAssessor(data) {
  return request({
    url: '/party/assessor',
    method: 'put',
    data: data
  })
}

// 删除工会述职评议
export function delAssessor(id) {
  return request({
    url: '/party/assessor/' + id,
    method: 'delete'
  })
}

// 导出工会述职评议
export function exportAssessor(query) {
  return request({
    url: '/party/assessor/export',
    method: 'get',
    params: query
  })
}

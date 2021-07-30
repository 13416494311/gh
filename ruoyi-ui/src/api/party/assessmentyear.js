import request from '@/utils/request'

// 查询工会考核年列表
export function listAssessmentyear(data) {
  return request({
    url: '/party/assessmentyear/list',
    method: 'post',
    data: data
  })
}

// 查询工会考核年详细
export function getAssessmentyear(id) {
  return request({
    url: '/party/assessmentyear/' + id,
    method: 'get'
  })
}

// 新增工会考核年
export function addAssessmentyear(data) {
  return request({
    url: '/party/assessmentyear',
    method: 'post',
    data: data
  })
}

// 修改工会考核年
export function updateAssessmentyear(data) {
  return request({
    url: '/party/assessmentyear',
    method: 'put',
    data: data
  })
}

// 删除工会考核年
export function delAssessmentyear(id) {
  return request({
    url: '/party/assessmentyear/' + id,
    method: 'delete'
  })
}

// 导出工会考核年
export function exportAssessmentyear(query) {
  return request({
    url: '/party/assessmentyear/export',
    method: 'get',
    params: query
  })
}

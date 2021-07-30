import request from '@/utils/request'

// 查询工会考核评价清单列表
export function listAssessmentList(query) {
  return request({
    url: '/party/assessmentList/list',
    method: 'get',
    params: query
  })
}

// 查询工会考核评价清单详细
export function getAssessmentList(listId) {
  return request({
    url: '/party/assessmentList/' + listId,
    method: 'get'
  })
}

// 新增工会考核评价清单
export function addAssessmentList(data) {
  return request({
    url: '/party/assessmentList',
    method: 'post',
    data: data
  })
}

// 修改工会考核评价清单
export function updateAssessmentList(data) {
  return request({
    url: '/party/assessmentList',
    method: 'put',
    data: data
  })
}

// 删除工会考核评价清单
export function delAssessmentList(listId) {
  return request({
    url: '/party/assessmentList/' + listId,
    method: 'delete'
  })
}

// 导出工会考核评价清单
export function exportAssessmentList(query) {
  return request({
    url: '/party/assessmentList/export',
    method: 'get',
    params: query
  })
}

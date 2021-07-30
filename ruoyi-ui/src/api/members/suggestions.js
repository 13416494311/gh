import request from '@/utils/request'

// 查询会员建议列表
export function listSuggestions(query) {
  return request({
    url: '/members/suggestions/list',
    method: 'get',
    params: query
  })
}

// 查询会员建议详细
export function getSuggestions(suggestionsId) {
  return request({
    url: '/members/suggestions/' + suggestionsId,
    method: 'get'
  })
}

// 新增会员建议
export function addSuggestions(data) {
  return request({
    url: '/members/suggestions',
    method: 'post',
    data: data
  })
}

// 修改会员建议
export function updateSuggestions(data) {
  return request({
    url: '/members/suggestions',
    method: 'put',
    data: data
  })
}

// 删除会员建议
export function delSuggestions(suggestionsId) {
  return request({
    url: '/members/suggestions/' + suggestionsId,
    method: 'delete'
  })
}

// 导出会员建议
export function exportSuggestions(query) {
  return request({
    url: '/members/suggestions/export',
    method: 'get',
    params: query
  })
}

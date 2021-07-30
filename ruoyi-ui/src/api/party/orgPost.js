import request from '@/utils/request'

// 查询工会职务列表
export function listPartyOrgPost(query) {
  return request({
    url: '/party/orgPost/list',
    method: 'get',
    params: query
  })
}

// 查询工会职务详细
export function getPartyOrgPost(id) {
  return request({
    url: '/party/orgPost/' + id,
    method: 'get'
  })
}

// 新增工会职务
export function addPartyOrgPost(data) {
  return request({
    url: '/party/orgPost',
    method: 'post',
    data: data
  })
}

// 修改工会职务
export function updatePartyOrgPost(data) {
  return request({
    url: '/party/orgPost',
    method: 'put',
    data: data
  })
}

// 删除工会职务
export function delPartyOrgPost(id) {
  return request({
    url: '/party/orgPost/' + id,
    method: 'delete'
  })
}

// 导出工会职务
export function exportPartyOrgPost(query) {
  return request({
    url: '/party/orgPost/export',
    method: 'get',
    params: query
  })
}

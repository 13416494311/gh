import request from '@/utils/request'

// 查询会员流动列表
export function listMemberFlow(data) {
  return request({
    url: '/party/memberFlow/list',
    method: 'post',
    data: data
  })
}

// 查询会员流动详细
export function getMemberFlow(flowId) {
  return request({
    url: '/party/memberFlow/' + flowId,
    method: 'get'
  })
}

// 新增会员流动
export function addMemberFlow(data) {
  return request({
    url: '/party/memberFlow',
    method: 'post',
    data: data
  })
}

// 修改会员流动
export function updateMemberFlow(data) {
  return request({
    url: '/party/memberFlow',
    method: 'put',
    data: data
  })
}

// 删除会员流动
export function delMemberFlow(flowId) {
  return request({
    url: '/party/memberFlow/' + flowId,
    method: 'delete'
  })
}

// 导出会员流动
export function exportMemberFlow(query) {
  return request({
    url: '/party/memberFlow/export',
    method: 'get',
    params: query
  })
}

import request from '@/utils/request'

// 查询会员会费列表
export function listDue(data) {
  return request({
    url: '/party/due/list',
    method: 'post',
    data: data
  })
}

// 查询会员会费列表
export function listDueMember(query) {
  return request({
    url: '/party/due/listDueMember',
    method: 'get',
    params: query
  })
}

// 查询会员会费详细
export function getDue(dueMemberId) {
  return request({
    url: '/party/due/' + dueMemberId,
    method: 'get'
  })
}

// 新增会员会费
export function addDue(data) {
  return request({
    url: '/party/due',
    method: 'post',
    data: data
  })
}

// 修改会员会费
export function updateDue(data) {
  return request({
    url: '/party/due',
    method: 'put',
    data: data
  })
}

// 修改会员会费
export function updateMemberDue(data) {
  return request({
    url: '/party/due/updateMemberDue',
    method: 'post',
    data: data
  })
}


// 删除会员会费
export function delDue(dueMemberId) {
  return request({
    url: '/party/due/' + dueMemberId,
    method: 'delete'
  })
}

// 导出会员会费
export function exportDue(query) {
  return request({
    url: '/party/due/export',
    method: 'get',
    params: query
  })
}

import request from '@/utils/request'

// 查询会员变更表列表
export function listPartyMemberChange(query) {
  return request({
    url: '/party/partyMemberChange/list',
    method: 'get',
    params: query
  })
}

// 查询会员变更表详细
export function getPartyMemberChange(memberId) {
  return request({
    url: '/party/partyMemberChange/' + memberId,
    method: 'get'
  })
}

// 查询该会员上一次的变更
export function getPrePartyMemberChange(partyMemberId) {
  return request({
    url: '/party/partyMemberChange/getPrePartyMemberChange/' + partyMemberId,
    method: 'get'
  })
}

// 查询会员变更表详细
export function getPartyMemberChangeByMemberUuid(memberUuid) {
  return request({
    url: '/party/partyMemberChange/getByMemberUuid/' + memberUuid,
    method: 'get'
  })
}

// 新增会员变更表
export function addPartyMemberChange(data) {
  return request({
    url: '/party/partyMemberChange',
    method: 'post',
    data: data
  })
}

// 修改会员变更表
export function updatePartyMemberChange(data) {
  return request({
    url: '/party/partyMemberChange',
    method: 'put',
    data: data
  })
}

// 删除会员变更表
export function delPartyMemberChange(memberId) {
  return request({
    url: '/party/partyMemberChange/' + memberId,
    method: 'delete'
  })
}

// 导出会员变更表
export function exportPartyMemberChange(query) {
  return request({
    url: '/party/partyMemberChange/export',
    method: 'get',
    params: query
  })
}

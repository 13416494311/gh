import request from '@/utils/request'

// 查询家庭成员列表
export function listFamily(query) {
  return request({
    url: '/member/family/list',
    method: 'get',
    params: query
  })
}

// 查询家庭成员详细
export function getFamily(familyId) {
  return request({
    url: '/member/family/' + familyId,
    method: 'get'
  })
}

// 新增家庭成员
export function addFamily(data) {
  return request({
    url: '/member/family',
    method: 'post',
    data: data
  })
}

// 修改家庭成员
export function updateFamily(data) {
  return request({
    url: '/member/family',
    method: 'put',
    data: data
  })
}

// 删除家庭成员
export function delFamily(familyId) {
  return request({
    url: '/member/family/' + familyId,
    method: 'delete'
  })
}

// 导出家庭成员
export function exportFamily(query) {
  return request({
    url: '/member/family/export',
    method: 'get',
    params: query
  })
}
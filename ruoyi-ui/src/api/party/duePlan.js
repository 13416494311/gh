import request from '@/utils/request'

// 查询会费计划列表
export function listDuePlan(query) {
  return request({
    url: '/party/duePlan/list',
    method: 'get',
    params: query
  })
}

// 查询会费计划详细
export function getDuePlan(duePlanId) {
  return request({
    url: '/party/duePlan/' + duePlanId,
    method: 'get'
  })
}

// 新增会费计划
export function addDuePlan(data) {
  return request({
    url: '/party/duePlan',
    method: 'post',
    data: data
  })
}

// 修改会费计划
export function updateDuePlan(data) {
  return request({
    url: '/party/duePlan',
    method: 'put',
    data: data
  })
}

// 删除会费计划
export function delDuePlan(duePlanId) {
  return request({
    url: '/party/duePlan/' + duePlanId,
    method: 'delete'
  })
}

// 导出会费计划
export function exportDuePlan(query) {
  return request({
    url: '/party/duePlan/export',
    method: 'get',
    params: query
  })
}

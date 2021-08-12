import request from '@/utils/request'


// 查询党建工作计划列表
export function getTduckLogin() {
  return request({
    url: '/tduck/getTduckLogin',
    method: 'get'
  })
}

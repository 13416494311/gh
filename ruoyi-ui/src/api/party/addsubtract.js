import request from '@/utils/request'

// 查询工会加减分审核列表
export function listAddsubtract(query) {
  return request({
    url: '/party/addsubtract/list',
    method: 'get',
    params: query
  })
}

// 查询工会加减分审核详细
export function getAddsubtract(addsubtractId) {
  return request({
    url: '/party/addsubtract/' + addsubtractId,
    method: 'get'
  })
}

// 新增工会加减分审核
export function addAddsubtract(data) {
  return request({
    url: '/party/addsubtract',
    method: 'post',
    data: data
  })
}

// 修改工会加减分审核
export function updateAddsubtract(data) {
  return request({
    url: '/party/addsubtract',
    method: 'put',
    data: data
  })
}

// 删除工会加减分审核
export function delAddsubtract(addsubtractId) {
  return request({
    url: '/party/addsubtract/' + addsubtractId,
    method: 'delete'
  })
}

// 导出工会加减分审核
export function exportAddsubtract(query) {
  return request({
    url: '/party/addsubtract/export',
    method: 'get',
    params: query
  })
}

import request from '@/utils/request'


export function checkPartyMemberUnique(data) {
  return request({
    url: '/system/register/checkPartyMemberUnique',
    method: 'post',
    data: data
  })
}

export function registerMember(data) {
  return request({
    url: '/system/register/registerMember',
    method: 'post',
    data: data
  })
}


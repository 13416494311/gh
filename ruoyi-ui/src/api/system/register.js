import request from '@/utils/request'


export function checkPartyMemberUnique(data) {
  return request({
    url: '/system/register/checkPartyMemberUnique',
    method: 'post',
    data: data
  })
}

export function registerPartyMember(data) {
  return request({
    url: '/system/register/registerPartyMember',
    method: 'post',
    data: data
  })
}


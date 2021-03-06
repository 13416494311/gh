package com.ruoyi.project.party.mapper;

import com.ruoyi.project.party.domain.DjPartyMember;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 会员信息Mapper接口
 *
 * @author ruoyi
 * @date 2020-08-06
 */
public interface DjPartyMemberMapper
{

    public int getMemberCount();

    /**
     * 查询会员信息
     *
     * @param memberId 会员信息ID
     * @return 会员信息
     */
    public DjPartyMember selectDjPartyMemberById(Long memberId);

    public DjPartyMember selectPartyMemberByUuid(String memberUuid);
    /**
     * 查询工会下所有会员信息（包含子级工会）列表
     *
     * @param djPartyMember 会员信息
     * @return 会员信息集合
     */
    public List<DjPartyMember> selectDjPartyMemberList(DjPartyMember djPartyMember);

    /**
     * 查询工会下所有会员信息（不包含子级工会）列表
     *
     * @param djPartyMember 会员信息
     * @return 会员信息集合
     */
    public List<DjPartyMember> selectPartyMemberList(DjPartyMember djPartyMember);

    public List<DjPartyMember> selectPoliticalBirthdayPartyMemberList();
    /**
     * 新增会员信息
     *
     * @param djPartyMember 会员信息
     * @return 结果
     */
    public int insertDjPartyMember(DjPartyMember djPartyMember);

    /**
     * 修改会员信息
     *
     * @param djPartyMember 会员信息
     * @return 结果
     */
    public int updateDjPartyMember(DjPartyMember djPartyMember);

    public int orderPartyMember(DjPartyMember djPartyMember);

    public int updatePartyPositionType(DjPartyMember djPartyMember);
    /**
     * 删除会员信息
     *
     * @param memberId 会员信息ID
     * @return 结果
     */
    public int deleteDjPartyMemberById(Long memberId);

    /**
     * 批量删除会员信息
     *
     * @param memberIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteDjPartyMemberByIds(Long[] memberIds);

    /**
     * 校验会员姓名是否唯一
     *
     * @param memberName 会员姓名
     * @return 结果
     */
    public DjPartyMember checkMemberNameUnique(String memberName);

    /**
     * 校验手机号码是否唯一
     *
     * @param mobile 手机号码
     * @return 结果
     */
    public DjPartyMember checkMobileUnique(String mobile);

    /**
     * 校验邮箱是否唯一
     *
     * @param email 邮箱
     * @return 结果
     */
    public DjPartyMember checkEmailUnique(String email);

    /**
     * 校验身份证号是否唯一
     *
     * @param identityCard 身份证号
     * @return 结果
     */
    public DjPartyMember checkIdentityCardUnique(String identityCard);


    public List<Map<String,Object>> echarts_31();

    public List<Map<String,Object>> echarts_32();

    public List<Map<String,Object>> echarts_33();
}

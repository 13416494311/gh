package com.ruoyi.project.party.service;

import com.ruoyi.project.party.domain.DjPartyMember;

import java.util.List;
import java.util.Map;

/**
 * 会员信息Service接口
 *
 * @author ruoyi
 * @date 2020-08-06
 */
public interface IDjPartyMemberService
{

    public int getMemberCount();
    /**
     * 查询会员信息
     *
     * @param memberId 会员信息ID
     * @return 会员信息
     */
    public DjPartyMember selectPartyMemberById(Long memberId);

    public DjPartyMember selectPartyMemberByUuid(String memberUuid);
    /**
     * 查询会员信息
     *
     * @param memberId 会员信息ID
     * @return 会员信息
     */
    public DjPartyMember selectDjPartyMemberById(Long memberId);

    public Map<String,Object> getPartyMemberRadarData(Long memberId);

    public List<DjPartyMember> getDjPartyMemberList(DjPartyMember djPartyMember);

    /**
     * 查询会员信息列表
     *
     * @param djPartyMember 会员信息
     * @return 会员信息集合
     */
    public List<DjPartyMember> selectDjPartyMemberList(DjPartyMember djPartyMember);

    public List<DjPartyMember> selectPartyMemberList(DjPartyMember djPartyMember);

    /**
     * 新增会员信息
     *
     * @param djPartyMember 会员信息
     * @return 结果
     */
    public int insertDjPartyMember(DjPartyMember djPartyMember);

    /**
     * 新增会员信息
     *
     * @param djPartyMember 会员信息
     * @return 结果
     */
    public int insertDjPartyMemberForApp(DjPartyMember djPartyMember,String password);

    /**
     * 修改会员信息
     *
     * @param djPartyMember 会员信息
     * @return 结果
     */
    public int updateDjPartyMember(DjPartyMember djPartyMember);

    /**
     * 排序会员信息
     *
     * @param djPartyMember 会员信息
     * @return 结果
     */
    public int orderPartyMember(DjPartyMember djPartyMember);

    public int updatePartyPositionType(DjPartyMember djPartyMember);

    public int updateAvatar(DjPartyMember djPartyMember);

    /**
     * 批量删除会员信息
     *
     * @param memberIds 需要删除的会员信息ID
     * @return 结果
     */
    public int deleteDjPartyMemberByIds(Long[] memberIds);

    /**
     * 删除会员信息信息
     *
     * @param memberId 会员信息ID
     * @return 结果
     */
    public int deleteDjPartyMemberById(Long memberId);

    /**
     * 检查会员姓名 手机号  邮箱唯一性
     *
     * @param djPartyMember
     * @return
     */
    public String checkPartyMemberNameUnique(DjPartyMember djPartyMember);
    /**
     * 检查会员手机号唯一性
     *
     * @param djPartyMember
     * @return
     */
    public String checkPartyMemberMobileUnique(DjPartyMember djPartyMember);
    /**
     * 检查会员邮箱唯一性
     *
     * @param djPartyMember
     * @return
     */
    public String checkPartyMemberEmailUnique(DjPartyMember djPartyMember);
    /**
     * 检查会员身份证号唯一性
     *
     * @param djPartyMember
     * @return
     */
    public String checkPartyMemberIdentityCardUnique(DjPartyMember djPartyMember);




    public List<Map<String,Object>> echarts_31();

    public List<Map<String,Object>> echarts_32();

    public List<Map<String,Object>> echarts_33();
}

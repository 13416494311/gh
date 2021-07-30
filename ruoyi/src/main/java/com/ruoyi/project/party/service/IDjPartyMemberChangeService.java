package com.ruoyi.project.party.service;

import java.util.List;
import com.ruoyi.project.party.domain.DjPartyMemberChange;

/**
 * 会员变更表Service接口
 *
 * @author ruoyi
 * @date 2020-09-16
 */
public interface IDjPartyMemberChangeService
{
    /**
     * 查询会员变更表
     *
     * @param memberId 会员变更表ID
     * @return 会员变更表
     */
    public DjPartyMemberChange selectDjPartyMemberChangeById(Long memberId);

    public DjPartyMemberChange selectDjPartyMemberChangeByMemberUuid(String memberUuid);

    public DjPartyMemberChange selectPrePartyMemberChangeByPartyMemberId(String partyMemberId);

    /**
     * 查询会员变更表列表
     *
     * @param djPartyMemberChange 会员变更表
     * @return 会员变更表集合
     */
    public List<DjPartyMemberChange> selectDjPartyMemberChangeList(DjPartyMemberChange djPartyMemberChange);

    /**
     * 新增会员变更表
     *
     * @param djPartyMemberChange 会员变更表
     * @return 结果
     */
    public int insertDjPartyMemberChange(DjPartyMemberChange djPartyMemberChange);

    /**
     * 修改会员变更表
     *
     * @param djPartyMemberChange 会员变更表
     * @return 结果
     */
    public int updateDjPartyMemberChange(DjPartyMemberChange djPartyMemberChange);

    /**
     * 批量删除会员变更表
     *
     * @param memberIds 需要删除的会员变更表ID
     * @return 结果
     */
    public int deleteDjPartyMemberChangeByIds(Long[] memberIds);

    /**
     * 删除会员变更表信息
     *
     * @param memberId 会员变更表ID
     * @return 结果
     */
    public int deleteDjPartyMemberChangeById(Long memberId);
}

package com.ruoyi.project.party.mapper;

import java.util.List;
import com.ruoyi.project.party.domain.DjPartyMemberDue;

/**
 * 会员会费Mapper接口
 *
 * @author wandengfeng
 * @date 2021-06-15
 */
public interface DjPartyMemberDueMapper
{
    /**
     * 查询会员会费
     *
     * @param dueMemberId 会员会费ID
     * @return 会员会费
     */
    public DjPartyMemberDue selectDjPartyMemberDueById(Long dueMemberId);

    /**
     * 查询会员会费列表
     *
     * @param djPartyMemberDue 会员会费
     * @return 会员会费集合
     */
    public List<DjPartyMemberDue> selectDjPartyMemberDueList(DjPartyMemberDue djPartyMemberDue);

    /**
     * 新增会员会费
     *
     * @param djPartyMemberDue 会员会费
     * @return 结果
     */
    public int insertDjPartyMemberDue(DjPartyMemberDue djPartyMemberDue);

    /**
     * 修改会员会费
     *
     * @param djPartyMemberDue 会员会费
     * @return 结果
     */
    public int updateDjPartyMemberDue(DjPartyMemberDue djPartyMemberDue);

    /**
     * 删除会员会费
     *
     * @param dueMemberId 会员会费ID
     * @return 结果
     */
    public int deleteDjPartyMemberDueById(Long dueMemberId);

    /**
     * 批量删除会员会费
     *
     * @param dueMemberIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteDjPartyMemberDueByIds(Long[] dueMemberIds);
}

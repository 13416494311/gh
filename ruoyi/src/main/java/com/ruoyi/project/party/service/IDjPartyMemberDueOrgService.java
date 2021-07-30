package com.ruoyi.project.party.service;

import java.util.List;
import com.ruoyi.project.party.domain.DjPartyMemberDueOrg;

/**
 * 工会会费Service接口
 *
 * @author ruoyi
 * @date 2021-06-15
 */
public interface IDjPartyMemberDueOrgService
{
    /**
     * 查询工会会费
     *
     * @param dueOrgId 工会会费ID
     * @return 工会会费
     */
    public DjPartyMemberDueOrg selectDjPartyMemberDueOrgById(Long dueOrgId);

    /**
     * 查询工会会费列表
     *
     * @param djPartyMemberDueOrg 工会会费
     * @return 工会会费集合
     */
    public List<DjPartyMemberDueOrg> selectDjPartyMemberDueOrgList(DjPartyMemberDueOrg djPartyMemberDueOrg);

    /**
     * 新增工会会费
     *
     * @param djPartyMemberDueOrg 工会会费
     * @return 结果
     */
    public int insertDjPartyMemberDueOrg(DjPartyMemberDueOrg djPartyMemberDueOrg);

    /**
     * 修改工会会费
     *
     * @param djPartyMemberDueOrg 工会会费
     * @return 结果
     */
    public int updateDjPartyMemberDueOrg(DjPartyMemberDueOrg djPartyMemberDueOrg);

    /**
     * 批量删除工会会费
     *
     * @param dueOrgIds 需要删除的工会会费ID
     * @return 结果
     */
    public int deleteDjPartyMemberDueOrgByIds(Long[] dueOrgIds);

    /**
     * 删除工会会费信息
     *
     * @param dueOrgId 工会会费ID
     * @return 结果
     */
    public int deleteDjPartyMemberDueOrgById(Long dueOrgId);
}

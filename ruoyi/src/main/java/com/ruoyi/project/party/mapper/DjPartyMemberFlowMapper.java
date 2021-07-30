package com.ruoyi.project.party.mapper;

import java.util.List;
import com.ruoyi.project.party.domain.DjPartyMemberFlow;

/**
 * 会员流动Mapper接口
 *
 * @author ruoyi
 * @date 2020-09-21
 */
public interface DjPartyMemberFlowMapper
{
    /**
     * 查询会员流动
     *
     * @param flowId 会员流动ID
     * @return 会员流动
     */
    public DjPartyMemberFlow selectDjPartyMemberFlowById(Long flowId);

    /**
     * 查询会员流动列表
     *
     * @param djPartyMemberFlow 会员流动
     * @return 会员流动集合
     */
    public List<DjPartyMemberFlow> selectDjPartyMemberFlowList(DjPartyMemberFlow djPartyMemberFlow);

    /**
     * 新增会员流动
     *
     * @param djPartyMemberFlow 会员流动
     * @return 结果
     */
    public int insertDjPartyMemberFlow(DjPartyMemberFlow djPartyMemberFlow);

    /**
     * 修改会员流动
     *
     * @param djPartyMemberFlow 会员流动
     * @return 结果
     */
    public int updateDjPartyMemberFlow(DjPartyMemberFlow djPartyMemberFlow);

    /**
     * 删除会员流动
     *
     * @param flowId 会员流动ID
     * @return 结果
     */
    public int deleteDjPartyMemberFlowById(Long flowId);

    /**
     * 批量删除会员流动
     *
     * @param flowIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteDjPartyMemberFlowByIds(Long[] flowIds);
}

package com.ruoyi.project.party.mapper;

import java.util.List;
import com.ruoyi.project.party.domain.DjPartyChange;

/**
 * 工会换届Mapper接口
 *
 * @author ruoyi
 * @date 2021-02-18
 */
public interface DjPartyChangeMapper
{
    /**
     * 查询工会换届
     *
     * @param changeId 工会换届ID
     * @return 工会换届
     */
    public DjPartyChange selectDjPartyChangeById(Long changeId);

    /**
     * 查询工会换届列表
     *
     * @param djPartyChange 工会换届
     * @return 工会换届集合
     */
    public List<DjPartyChange> selectDjPartyChangeList(DjPartyChange djPartyChange);

    /**
     * 新增工会换届
     *
     * @param djPartyChange 工会换届
     * @return 结果
     */
    public int insertDjPartyChange(DjPartyChange djPartyChange);

    /**
     * 修改工会换届
     *
     * @param djPartyChange 工会换届
     * @return 结果
     */
    public int updateDjPartyChange(DjPartyChange djPartyChange);

    /**
     * 删除工会换届
     *
     * @param changeId 工会换届ID
     * @return 结果
     */
    public int deleteDjPartyChangeById(Long changeId);

    /**
     * 批量删除工会换届
     *
     * @param changeIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteDjPartyChangeByIds(Long[] changeIds);
}

package com.ruoyi.project.party.service;

import java.util.List;
import com.ruoyi.project.party.domain.DjPartyTrain;

/**
 * 工会培训Service接口
 *
 * @author ruoyi
 * @date 2021-03-09
 */
public interface IDjPartyTrainService
{
    /**
     * 查询工会培训
     *
     * @param trainId 工会培训ID
     * @return 工会培训
     */
    public DjPartyTrain selectDjPartyTrainById(Long trainId);

    /**
     * 查询工会培训列表
     *
     * @param djPartyTrain 工会培训
     * @return 工会培训集合
     */
    public List<DjPartyTrain> selectDjPartyTrainList(DjPartyTrain djPartyTrain);

    /**
     * 新增工会培训
     *
     * @param djPartyTrain 工会培训
     * @return 结果
     */
    public int insertDjPartyTrain(DjPartyTrain djPartyTrain);

    /**
     * 修改工会培训
     *
     * @param djPartyTrain 工会培训
     * @return 结果
     */
    public int updateDjPartyTrain(DjPartyTrain djPartyTrain);

    /**
     * 批量删除工会培训
     *
     * @param trainIds 需要删除的工会培训ID
     * @return 结果
     */
    public int deleteDjPartyTrainByIds(Long[] trainIds);

    /**
     * 删除工会培训信息
     *
     * @param trainId 工会培训ID
     * @return 结果
     */
    public int deleteDjPartyTrainById(Long trainId);
}

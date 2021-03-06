package com.ruoyi.project.party.service;

import java.util.List;
import com.ruoyi.project.party.domain.DjOrgAssessmentList;

/**
 * 工会考核评价清单Service接口
 *
 * @author admin
 * @date 2021-03-08
 */
public interface IDjOrgAssessmentListService
{
    /**
     * 查询工会考核评价清单
     *
     * @param listId 工会考核评价清单ID
     * @return 工会考核评价清单
     */
    public DjOrgAssessmentList selectDjOrgAssessmentListById(Long listId);

    /**
     * 查询工会考核评价清单列表
     *
     * @param djOrgAssessmentList 工会考核评价清单
     * @return 工会考核评价清单集合
     */
    public List<DjOrgAssessmentList> selectDjOrgAssessmentListList(DjOrgAssessmentList djOrgAssessmentList);

    /**
     * 新增工会考核评价清单
     *
     * @param djOrgAssessmentList 工会考核评价清单
     * @return 结果
     */
    public int insertDjOrgAssessmentList(DjOrgAssessmentList djOrgAssessmentList);

    /**
     * 修改工会考核评价清单
     *
     * @param djOrgAssessmentList 工会考核评价清单
     * @return 结果
     */
    public int updateDjOrgAssessmentList(DjOrgAssessmentList djOrgAssessmentList);

    /**
     * 批量删除工会考核评价清单
     *
     * @param listIds 需要删除的工会考核评价清单ID
     * @return 结果
     */
    public int deleteDjOrgAssessmentListByIds(Long[] listIds);

    /**
     * 删除工会考核评价清单信息
     *
     * @param listId 工会考核评价清单ID
     * @return 结果
     */
    public int deleteDjOrgAssessmentListById(Long listId);
}

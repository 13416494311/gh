package com.ruoyi.project.party.service;

import java.util.List;
import com.ruoyi.project.party.domain.DjOrgAssessmentAssessor;

/**
 * 工会述职评议Service接口
 *
 * @author admin
 * @date 2021-03-23
 */
public interface IDjOrgAssessmentAssessorService
{
    /**
     * 查询工会述职评议
     *
     * @param id 工会述职评议ID
     * @return 工会述职评议
     */
    public DjOrgAssessmentAssessor selectDjOrgAssessmentAssessorById(Long id);

    /**
     * 查询工会述职评议列表
     *
     * @param djOrgAssessmentAssessor 工会述职评议
     * @return 工会述职评议集合
     */
    public List<DjOrgAssessmentAssessor> selectDjOrgAssessmentAssessorList(DjOrgAssessmentAssessor djOrgAssessmentAssessor);

    /**
     * 新增工会述职评议
     *
     * @param djOrgAssessmentAssessor 工会述职评议
     * @return 结果
     */
    public int insertDjOrgAssessmentAssessor(DjOrgAssessmentAssessor djOrgAssessmentAssessor);

    /**
     * 修改工会述职评议
     *
     * @param djOrgAssessmentAssessor 工会述职评议
     * @return 结果
     */
    public int updateDjOrgAssessmentAssessor(DjOrgAssessmentAssessor djOrgAssessmentAssessor);

    /**
     * 批量删除工会述职评议
     *
     * @param ids 需要删除的工会述职评议ID
     * @return 结果
     */
    public int deleteDjOrgAssessmentAssessorByIds(Long[] ids);

    /**
     * 删除工会述职评议信息
     *
     * @param id 工会述职评议ID
     * @return 结果
     */
    public int deleteDjOrgAssessmentAssessorById(Long id);
}

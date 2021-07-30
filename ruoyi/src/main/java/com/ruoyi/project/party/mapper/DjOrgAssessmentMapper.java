package com.ruoyi.project.party.mapper;

import java.util.List;
import com.ruoyi.project.party.domain.DjOrgAssessment;

/**
 * 工会考核Mapper接口
 *
 * @author admin
 * @date 2021-03-10
 */
public interface DjOrgAssessmentMapper
{
    /**
     * 查询工会考核
     *
     * @param id 工会考核ID
     * @return 工会考核
     */
    public DjOrgAssessment selectDjOrgAssessmentById(Long id);
    /**
     * 查询工会考核
     *
     * @param uuid 工会考核uuid
     * @return 工会考核
     */
    public DjOrgAssessment selectDjOrgAssessmentByUuid(String uuid);

    /**
     * 查询工会考核列表
     *
     * @param djOrgAssessment 工会考核
     * @return 工会考核集合
     */
    public List<DjOrgAssessment> selectDjOrgAssessmentList(DjOrgAssessment djOrgAssessment);

    public List<DjOrgAssessment> selectDjOrgAssessmentListRank(DjOrgAssessment djOrgAssessment);

    /**
     * 新增工会考核
     *
     * @param djOrgAssessment 工会考核
     * @return 结果
     */
    public int insertDjOrgAssessment(DjOrgAssessment djOrgAssessment);

    /**
     * 修改工会考核
     *
     * @param djOrgAssessment 工会考核
     * @return 结果
     */
    public int updateDjOrgAssessment(DjOrgAssessment djOrgAssessment);

    /**
     * 删除工会考核
     *
     * @param id 工会考核ID
     * @return 结果
     */
    public int deleteDjOrgAssessmentById(Long id);

    /**
     * 批量删除工会考核
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteDjOrgAssessmentByIds(Long[] ids);
}

package com.ruoyi.project.party.service;

import java.util.List;
import com.ruoyi.project.party.domain.DjOrgAssessmentyear;

/**
 * 工会考核年Service接口
 *
 * @author admin
 * @date 2021-03-10
 */
public interface IDjOrgAssessmentyearService
{
    /**
     * 查询工会考核年
     *
     * @param id 工会考核年ID
     * @return 工会考核年
     */
    public DjOrgAssessmentyear selectDjOrgAssessmentyearById(Long id);

    /**
     * 查询工会考核年
     *
     * @param assessmentyearUuid 工会考核年UUID
     * @return 工会考核年
     */
    public DjOrgAssessmentyear selectDjOrgAssessmentyearByUuid(String assessmentyearUuid);

    /**
     * 查询工会考核年列表
     *
     * @param djOrgAssessmentyear 工会考核年
     * @return 工会考核年集合
     */
    public List<DjOrgAssessmentyear> selectDjOrgAssessmentyearList(DjOrgAssessmentyear djOrgAssessmentyear);

    /**
     * 新增工会考核年
     *
     * @param djOrgAssessmentyear 工会考核年
     * @return 结果
     */
    public int insertDjOrgAssessmentyear(DjOrgAssessmentyear djOrgAssessmentyear);

    /**
     * 修改工会考核年
     *
     * @param djOrgAssessmentyear 工会考核年
     * @return 结果
     */
    public int updateDjOrgAssessmentyear(DjOrgAssessmentyear djOrgAssessmentyear);

    /**
     * 批量删除工会考核年
     *
     * @param ids 需要删除的工会考核年ID
     * @return 结果
     */
    public int deleteDjOrgAssessmentyearByIds(Long[] ids);

    /**
     * 删除工会考核年信息
     *
     * @param id 工会考核年ID
     * @return 结果
     */
    public int deleteDjOrgAssessmentyearById(Long id);
}

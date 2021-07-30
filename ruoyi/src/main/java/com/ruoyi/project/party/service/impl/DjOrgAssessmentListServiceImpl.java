package com.ruoyi.project.party.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.party.mapper.DjOrgAssessmentListMapper;
import com.ruoyi.project.party.domain.DjOrgAssessmentList;
import com.ruoyi.project.party.service.IDjOrgAssessmentListService;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
/**
 * 工会考核评价清单Service业务层处理
 *
 * @author admin
 * @date 2021-03-08
 */
@Service
@Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
public class DjOrgAssessmentListServiceImpl implements IDjOrgAssessmentListService
{
    @Autowired
    private DjOrgAssessmentListMapper djOrgAssessmentListMapper;

    /**
     * 查询工会考核评价清单
     *
     * @param listId 工会考核评价清单ID
     * @return 工会考核评价清单
     */
    @Override
    public DjOrgAssessmentList selectDjOrgAssessmentListById(Long listId)
    {
        return djOrgAssessmentListMapper.selectDjOrgAssessmentListById(listId);
    }

    /**
     * 查询工会考核评价清单列表
     *
     * @param djOrgAssessmentList 工会考核评价清单
     * @return 工会考核评价清单
     */
    @Override
    public List<DjOrgAssessmentList> selectDjOrgAssessmentListList(DjOrgAssessmentList djOrgAssessmentList)
    {
        return djOrgAssessmentListMapper.selectDjOrgAssessmentListList(djOrgAssessmentList);
    }

    /**
     * 新增工会考核评价清单
     *
     * @param djOrgAssessmentList 工会考核评价清单
     * @return 结果
     */
    @Override
    public int insertDjOrgAssessmentList(DjOrgAssessmentList djOrgAssessmentList)
    {
        djOrgAssessmentList.setDelFlag("0");
        djOrgAssessmentList.setCreateBy(SecurityUtils.getLoginUser().getUser().getUserId().toString());
        djOrgAssessmentList.setCreateTime(DateUtils.getNowDate());
        return djOrgAssessmentListMapper.insertDjOrgAssessmentList(djOrgAssessmentList);
    }

    /**
     * 修改工会考核评价清单
     *
     * @param djOrgAssessmentList 工会考核评价清单
     * @return 结果
     */
    @Override
    public int updateDjOrgAssessmentList(DjOrgAssessmentList djOrgAssessmentList)
    {
        djOrgAssessmentList.setUpdateBy(SecurityUtils.getLoginUser().getUser().getUserId().toString());
        djOrgAssessmentList.setUpdateTime(DateUtils.getNowDate());
        return djOrgAssessmentListMapper.updateDjOrgAssessmentList(djOrgAssessmentList);
    }

    /**
     * 批量删除工会考核评价清单
     *
     * @param listIds 需要删除的工会考核评价清单ID
     * @return 结果
     */
    @Override
    public int deleteDjOrgAssessmentListByIds(Long[] listIds)
    {
        return djOrgAssessmentListMapper.deleteDjOrgAssessmentListByIds(listIds);
    }

    /**
     * 删除工会考核评价清单信息
     *
     * @param listId 工会考核评价清单ID
     * @return 结果
     */
    @Override
    public int deleteDjOrgAssessmentListById(Long listId)
    {
        return djOrgAssessmentListMapper.deleteDjOrgAssessmentListById(listId);
    }
}

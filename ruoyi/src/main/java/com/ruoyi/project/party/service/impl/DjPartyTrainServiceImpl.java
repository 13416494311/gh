package com.ruoyi.project.party.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.project.party.domain.DjPartyOrg;
import com.ruoyi.project.party.service.IDjPartyOrgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.party.mapper.DjPartyTrainMapper;
import com.ruoyi.project.party.domain.DjPartyTrain;
import com.ruoyi.project.party.service.IDjPartyTrainService;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
/**
 * 工会培训Service业务层处理
 *
 * @author ruoyi
 * @date 2021-03-09
 */
@Service
@Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
public class DjPartyTrainServiceImpl implements IDjPartyTrainService
{
    @Autowired
    private DjPartyTrainMapper djPartyTrainMapper;
    @Autowired
    private IDjPartyOrgService  partyOrgService;

    /**
     * 查询工会培训
     *
     * @param trainId 工会培训ID
     * @return 工会培训
     */
    @Override
    public DjPartyTrain selectDjPartyTrainById(Long trainId)
    {
        DjPartyTrain partyTrain = djPartyTrainMapper.selectDjPartyTrainById(trainId);
        if (StringUtils.isNotNull(partyTrain.getPartyOrgId())) {
            partyTrain.setPartyOrg(partyOrgService.selectDjPartyOrgById(partyTrain.getPartyOrgId()));
        }
        return partyTrain;
    }

    /**
     * 查询工会培训列表
     *
     * @param djPartyTrain 工会培训
     * @return 工会培训
     */
    @Override
    public List<DjPartyTrain> selectDjPartyTrainList(DjPartyTrain djPartyTrain)
    {
        List<DjPartyTrain> partyTrainList = djPartyTrainMapper.selectDjPartyTrainList(djPartyTrain);
        partyTrainList.stream().forEach(partyTrain->{
            if (StringUtils.isNotNull(partyTrain.getPartyOrgId())) {
                DjPartyOrg partyOrg = partyOrgService.selectDjPartyOrgById(partyTrain.getPartyOrgId());
                partyOrg.setPartyOrgFullName(partyOrg.getPartyOrgFullName().substring(partyOrg.getPartyOrgFullName().indexOf("/")+1));
                partyTrain.setPartyOrg(partyOrg);
            }
        });
        return partyTrainList;
    }

    /**
     * 新增工会培训
     *
     * @param djPartyTrain 工会培训
     * @return 结果
     */
    @Override
    public int insertDjPartyTrain(DjPartyTrain djPartyTrain)
    {
        djPartyTrain.setCreateBy(SecurityUtils.getLoginUser().getUser().getUserId().toString());
        djPartyTrain.setCreateTime(DateUtils.getNowDate());
        return djPartyTrainMapper.insertDjPartyTrain(djPartyTrain);
    }

    /**
     * 修改工会培训
     *
     * @param djPartyTrain 工会培训
     * @return 结果
     */
    @Override
    public int updateDjPartyTrain(DjPartyTrain djPartyTrain)
    {
        djPartyTrain.setUpdateBy(SecurityUtils.getLoginUser().getUser().getUserId().toString());
        djPartyTrain.setUpdateTime(DateUtils.getNowDate());
        return djPartyTrainMapper.updateDjPartyTrain(djPartyTrain);
    }

    /**
     * 批量删除工会培训
     *
     * @param trainIds 需要删除的工会培训ID
     * @return 结果
     */
    @Override
    public int deleteDjPartyTrainByIds(Long[] trainIds)
    {
        return djPartyTrainMapper.deleteDjPartyTrainByIds(trainIds);
    }

    /**
     * 删除工会培训信息
     *
     * @param trainId 工会培训ID
     * @return 结果
     */
    @Override
    public int deleteDjPartyTrainById(Long trainId)
    {
        return djPartyTrainMapper.deleteDjPartyTrainById(trainId);
    }
}

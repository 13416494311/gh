package com.ruoyi.project.party.service;

import java.util.List;
import java.util.Map;

import com.ruoyi.project.party.domain.DjPartyOrg;
import com.ruoyi.project.party.domain.PartyOrgTreeData;

/**
 * 工会架构Service接口
 *
 * @author ruoyi
 * @date 2020-08-03
 */
public interface IDjPartyOrgService
{
    public List<Map<String,Object>> getOrgMemberChartData();

    public List<Map<String,Object>> getRegistOrgMemberChartData();

    public int getOrgCount();

    /**
     * 查询工会架构
     *
     * @param partyOrgId 工会架构ID
     * @return 工会架构
     */
    public DjPartyOrg selectDjPartyOrgById(Long partyOrgId);

    public DjPartyOrg getPartyOrgInfo(Long partyOrgId);

    /**
     * 查询工会架构列表
     *
     * @param djPartyOrg 工会架构
     * @return 工会架构集合
     */
    public List<DjPartyOrg> selectDjPartyOrgList(DjPartyOrg djPartyOrg);

    /**
     * 查询工会架构下属列表
     *
     * @param partyOrgId 工会架构Id
     * @return 工会架构集合
     */
    public List<DjPartyOrg> selectChildrenPartyOrgById(Long partyOrgId);


    /**
     * 新增工会架构
     *
     * @param djPartyOrg 工会架构
     * @return 结果
     */
    public int insertDjPartyOrg(DjPartyOrg djPartyOrg);

    /**
     * 修改工会架构
     *
     * @param djPartyOrg 工会架构
     * @return 结果
     */
    public int updateDjPartyOrg(DjPartyOrg djPartyOrg);

    /**
     * 批量删除工会架构
     *
     * @param partyOrgIds 需要删除的工会架构ID
     * @return 结果
     */
    public int deleteDjPartyOrgByIds(Long[] partyOrgIds);

    /**
     * 删除工会架构信息
     *
     * @param partyOrgId 工会架构ID
     * @return 结果
     */
    public int deleteDjPartyOrgById(Long partyOrgId);

    /**
     * 校验工会架构名称是否唯一
     *
     * @param djPartyOrg 工会架构信息
     * @return 结果
     */
    public String checkPartyOrgNameUnique(DjPartyOrg djPartyOrg);

    /**
     * 根据ID查询所有子工会架构（正常状态）
     *
     * @param partyOrgId 工会架构ID
     * @return 子工会架构数
     */
    public int selectNormalChildrenPartyOrgById(Long partyOrgId);

    /**
     * 是否存在工会架构子节点
     *
     * @param partyOrgId 工会架构ID
     * @return 结果
     */
    public boolean hasChildByPartyOrgId(Long partyOrgId);

    /**
     * 工会架构是否存在会员
     *
     * @param partyOrgId 工会架构ID
     * @return 结果
     */
    public boolean checkOrgExistMember(Long partyOrgId);

    /**
     * 获取工会架构树数据
     *
     * @return
     */
    public PartyOrgTreeData getPartyOrgTreeData();

    /**
     * 获取工会架构树数据
     *
     * @return
     */
    public List<PartyOrgTreeData> getPartyOrgTreeDataList();

    /**
     * 获取工会架构树数据
     *
     * @return
     */
    public List<PartyOrgTreeData> getPartyOrgTreeDataListByEdit();

    /**
     * 查询工会架构
     *
     * @param partyOrgUuid 工会架构UUID
     * @return 工会架构
     */
    public DjPartyOrg selectDjPartyOrgByPartyOrgUuid(String partyOrgUuid);

    public List<Map<String,Object>> echarts_2();

    public List<Map<String,Object>> echarts_1();
}

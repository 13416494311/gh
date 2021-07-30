package com.ruoyi.project.party.mapper;

import java.util.List;
import java.util.Map;

import com.ruoyi.project.party.domain.PartyOrgTreeData;
import org.apache.ibatis.annotations.Param;
import com.ruoyi.project.party.domain.DjPartyOrg;

/**
 * 工会架构Mapper接口
 *
 * @author ruoyi
 * @date 2020-08-03
 */
public interface DjPartyOrgMapper
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

    /**
     * 查询工会架构
     *
     * @param partyOrgId 工会架构ID
     * @return 工会架构
     */
    public DjPartyOrg selectDjPartyOrgByUuid(String partyOrgUuid);

    /**
     * 查询工会架构列表
     *
     * @param djPartyOrg 工会架构
     * @return 工会架构集合
     */
    public List<DjPartyOrg> selectDjPartyOrgList(DjPartyOrg djPartyOrg);

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
     * 删除工会架构
     *
     * @param partyOrgId 工会架构ID
     * @return 结果
     */
    public int deleteDjPartyOrgById(Long partyOrgId);

    /**
     * 批量删除工会架构
     *
     * @param partyOrgIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteDjPartyOrgByIds(Long[] partyOrgIds);

    /**
     * 校验工会架构名称是否唯一
     *
     * @param partyOrgName 工会架构名称
     * @param partyOrgId 父工会架构ID
     * @return 结果
     */
    public DjPartyOrg checkPartyOrgNameUnique(@Param("partyOrgName") String partyOrgName,
                                              @Param("partyOrgId") Long partyOrgId);

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
    public int hasChildByPartyOrgId(Long partyOrgId);

    /**
     * 工会架构是否存在会员
     *
     * @param partyOrgId 工会架构ID
     * @return 结果
     */
    public int checkOrgExistMember(Long partyOrgId);

    /**
     * 修改所在部门的上级工会架构状态
     *
     * @param djPartyOrg 工会架构
     */
    public void updatePartyOrgStatus(DjPartyOrg djPartyOrg);

    /**
     * 修改子元素关系
     *
     * @param partyOrgs 子元素
     * @return 结果
     */
    public int updatePartyOrgChildren(@Param("partyOrgs") List<DjPartyOrg> partyOrgs);
    /**
     * 根据ID查询所有子工会架构
     *
     * @param partyOrgId 工会架构ID
     * @return 工会架构列表
     */
    public List<DjPartyOrg> selectChildrenPartyOrgById(Long partyOrgId);
    /**
     * 获取工会架构树数据
     *
     * @return
     */
    public List<PartyOrgTreeData> getPartyOrgTreeData(@Param("partyOrgs") List<DjPartyOrg> partyOrgs);

    public List<Map<String,Object>> echarts_2();

    public List<Map<String,Object>> echarts_1();
}

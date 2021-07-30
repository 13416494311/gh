package com.ruoyi.project.party.service;

import java.util.List;
import com.ruoyi.project.party.domain.DjPartyOrgPost;

/**
 * 工会职务Service接口
 *
 * @author ruoyi
 * @date 2020-08-08
 */
public interface IDjPartyOrgPostService
{
    /**
     * 查询工会职务
     *
     * @param id 工会职务ID
     * @return 工会职务
     */
    public DjPartyOrgPost selectDjPartyOrgPostById(Long id);

    /**
     * 查询工会职务列表
     *
     * @param djPartyOrgPost 工会职务
     * @return 工会职务集合
     */
    public List<DjPartyOrgPost> selectDjPartyOrgPostList(DjPartyOrgPost djPartyOrgPost);

    /**
     * 新增工会职务
     *
     * @param djPartyOrgPost 工会职务
     * @return 结果
     */
    public int insertDjPartyOrgPost(DjPartyOrgPost djPartyOrgPost);

    /**
     * 修改工会职务
     *
     * @param djPartyOrgPost 工会职务
     * @return 结果
     */
    public int updateDjPartyOrgPost(DjPartyOrgPost djPartyOrgPost);

    /**
     * 批量删除工会职务
     *
     * @param ids 需要删除的工会职务ID
     * @return 结果
     */
    public int deleteDjPartyOrgPostByIds(Long[] ids);

    /**
     * 删除工会职务信息
     *
     * @param id 工会职务ID
     * @return 结果
     */
    public int deleteDjPartyOrgPostById(Long id);
}

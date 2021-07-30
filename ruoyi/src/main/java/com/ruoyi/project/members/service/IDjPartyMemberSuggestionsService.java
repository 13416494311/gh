package com.ruoyi.project.members.service;

import java.util.List;
import com.ruoyi.project.members.domain.DjPartyMemberSuggestions;

/**
 * 会员建议Service接口
 *
 * @author ruoyi
 * @date 2021-02-27
 */
public interface IDjPartyMemberSuggestionsService
{
    /**
     * 查询会员建议
     *
     * @param suggestionsId 会员建议ID
     * @return 会员建议
     */
    public DjPartyMemberSuggestions selectDjPartyMemberSuggestionsById(Long suggestionsId);

    /**
     * 查询会员建议列表
     *
     * @param djPartyMemberSuggestions 会员建议
     * @return 会员建议集合
     */
    public List<DjPartyMemberSuggestions> selectDjPartyMemberSuggestionsList(DjPartyMemberSuggestions djPartyMemberSuggestions);

    /**
     * 新增会员建议
     *
     * @param djPartyMemberSuggestions 会员建议
     * @return 结果
     */
    public int insertDjPartyMemberSuggestions(DjPartyMemberSuggestions djPartyMemberSuggestions);

    /**
     * 修改会员建议
     *
     * @param djPartyMemberSuggestions 会员建议
     * @return 结果
     */
    public int updateDjPartyMemberSuggestions(DjPartyMemberSuggestions djPartyMemberSuggestions);

    /**
     * 批量删除会员建议
     *
     * @param suggestionsIds 需要删除的会员建议ID
     * @return 结果
     */
    public int deleteDjPartyMemberSuggestionsByIds(Long[] suggestionsIds);

    /**
     * 删除会员建议信息
     *
     * @param suggestionsId 会员建议ID
     * @return 结果
     */
    public int deleteDjPartyMemberSuggestionsById(Long suggestionsId);
}

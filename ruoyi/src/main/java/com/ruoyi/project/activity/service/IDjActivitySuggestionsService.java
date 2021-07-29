package com.ruoyi.project.activity.service;

import java.util.List;
import com.ruoyi.project.activity.domain.DjActivitySuggestions;

/**
 * 建言献策Service接口
 * 
 * @author ruoyi
 * @date 2020-08-22
 */
public interface IDjActivitySuggestionsService 
{
    /**
     * 查询建言献策
     * 
     * @param suggestionsId 建言献策ID
     * @return 建言献策
     */
    public DjActivitySuggestions selectDjActivitySuggestionsById(Long suggestionsId);

    /**
     * 查询建言献策列表
     * 
     * @param djActivitySuggestions 建言献策
     * @return 建言献策集合
     */
    public List<DjActivitySuggestions> selectDjActivitySuggestionsList(DjActivitySuggestions djActivitySuggestions);

    /**
     * 新增建言献策
     * 
     * @param djActivitySuggestions 建言献策
     * @return 结果
     */
    public int insertDjActivitySuggestions(DjActivitySuggestions djActivitySuggestions);

    /**
     * 修改建言献策
     * 
     * @param djActivitySuggestions 建言献策
     * @return 结果
     */
    public int updateDjActivitySuggestions(DjActivitySuggestions djActivitySuggestions);

    /**
     * 批量删除建言献策
     * 
     * @param suggestionsIds 需要删除的建言献策ID
     * @return 结果
     */
    public int deleteDjActivitySuggestionsByIds(Long[] suggestionsIds);

    /**
     * 删除建言献策信息
     * 
     * @param suggestionsId 建言献策ID
     * @return 结果
     */
    public int deleteDjActivitySuggestionsById(Long suggestionsId);
}

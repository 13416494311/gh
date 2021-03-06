package com.ruoyi.project.members.mapper;

import java.util.List;
import com.ruoyi.project.members.domain.DjPartyMemberHelp;

/**
 * 会员帮扶Mapper接口
 *
 * @author ruoyi
 * @date 2021-02-27
 */
public interface DjPartyMemberHelpMapper
{
    /**
     * 查询会员帮扶
     *
     * @param helpId 会员帮扶ID
     * @return 会员帮扶
     */
    public DjPartyMemberHelp selectDjPartyMemberHelpById(Long helpId);

    /**
     * 查询会员帮扶列表
     *
     * @param djPartyMemberHelp 会员帮扶
     * @return 会员帮扶集合
     */
    public List<DjPartyMemberHelp> selectDjPartyMemberHelpList(DjPartyMemberHelp djPartyMemberHelp);

    /**
     * 新增会员帮扶
     *
     * @param djPartyMemberHelp 会员帮扶
     * @return 结果
     */
    public int insertDjPartyMemberHelp(DjPartyMemberHelp djPartyMemberHelp);

    /**
     * 修改会员帮扶
     *
     * @param djPartyMemberHelp 会员帮扶
     * @return 结果
     */
    public int updateDjPartyMemberHelp(DjPartyMemberHelp djPartyMemberHelp);

    /**
     * 删除会员帮扶
     *
     * @param helpId 会员帮扶ID
     * @return 结果
     */
    public int deleteDjPartyMemberHelpById(Long helpId);

    /**
     * 批量删除会员帮扶
     *
     * @param helpIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteDjPartyMemberHelpByIds(Long[] helpIds);
}

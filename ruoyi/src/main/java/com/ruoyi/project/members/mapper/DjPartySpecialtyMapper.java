package com.ruoyi.project.members.mapper;

import java.util.List;
import com.ruoyi.project.members.domain.DjPartySpecialty;

/**
 * 会员特长Mapper接口
 *
 * @author admin
 * @date 2021-02-19
 */
public interface DjPartySpecialtyMapper
{
    /**
     * 查询会员特长
     *
     * @param specialtyId 会员特长ID
     * @return 会员特长
     */
    public DjPartySpecialty selectDjPartySpecialtyById(Long specialtyId);

    /**
     * 查询会员特长列表
     *
     * @param djPartySpecialty 会员特长
     * @return 会员特长集合
     */
    public List<DjPartySpecialty> selectDjPartySpecialtyList(DjPartySpecialty djPartySpecialty);

    /**
     * 新增会员特长
     *
     * @param djPartySpecialty 会员特长
     * @return 结果
     */
    public int insertDjPartySpecialty(DjPartySpecialty djPartySpecialty);

    /**
     * 修改会员特长
     *
     * @param djPartySpecialty 会员特长
     * @return 结果
     */
    public int updateDjPartySpecialty(DjPartySpecialty djPartySpecialty);

    /**
     * 删除会员特长
     *
     * @param specialtyId 会员特长ID
     * @return 结果
     */
    public int deleteDjPartySpecialtyById(Long specialtyId);

    /**
     * 批量删除会员特长
     *
     * @param specialtyIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteDjPartySpecialtyByIds(Long[] specialtyIds);
}

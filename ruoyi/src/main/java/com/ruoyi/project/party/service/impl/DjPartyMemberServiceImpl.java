package com.ruoyi.project.party.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.project.activity.domain.DjActivityDetailed;
import com.ruoyi.project.activity.domain.DjActivityMember;
import com.ruoyi.project.activity.mapper.DjActivityDetailedMapper;
import com.ruoyi.project.activity.mapper.DjActivityMemberMapper;
import com.ruoyi.project.members.domain.DjPartyExemplary;
import com.ruoyi.project.members.domain.DjPartyMemberHelp;
import com.ruoyi.project.members.domain.DjPartySpecialty;
import com.ruoyi.project.members.mapper.DjPartyExemplaryMapper;
import com.ruoyi.project.members.mapper.DjPartyMemberHelpMapper;
import com.ruoyi.project.members.mapper.DjPartySpecialtyMapper;
import com.ruoyi.project.party.domain.DjPartyMember;
import com.ruoyi.project.party.domain.DjPartyTrain;
import com.ruoyi.project.party.domain.DjPartyTrainMember;
import com.ruoyi.project.party.mapper.DjPartyMemberMapper;
import com.ruoyi.project.party.mapper.DjPartyOrgMapper;
import com.ruoyi.project.party.mapper.DjPartyTrainMapper;
import com.ruoyi.project.party.mapper.DjPartyTrainMemberMapper;
import com.ruoyi.project.party.service.IDjPartyMemberService;
import com.ruoyi.project.party.service.IDjPartyOrgService;
import com.ruoyi.project.system.domain.SysComment;
import com.ruoyi.project.system.domain.SysDept;
import com.ruoyi.project.system.domain.SysUser;
import com.ruoyi.project.system.mapper.SysCommentMapper;
import com.ruoyi.project.system.mapper.SysDeptMapper;
import com.ruoyi.project.system.mapper.SysUserMapper;
import com.ruoyi.project.system.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
/**
 * ????????????Service???????????????
 *
 * @author ruoyi
 * @date 2020-08-06
 */
@Service
@Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
public class DjPartyMemberServiceImpl implements IDjPartyMemberService
{
    @Autowired
    private DjPartyMemberMapper djPartyMemberMapper;
    @Autowired
    private IDjPartyOrgService djPartyOrgService;
    @Autowired
    private ISysDeptService deptService;
    @Autowired
    private ISysConfigService configService;
    @Autowired
    private ISysUserService userService;
    @Autowired
    private ISysDictDataService dictDataService;
    @Autowired
    private ISysPostService postService;
    @Autowired
    private SysDeptMapper sysDeptMapper;
    @Autowired
    private DjPartyMemberHelpMapper partyMemberHelpMapper;

    @Autowired
    private DjActivityDetailedMapper activityDetailedMapper;
    @Autowired
    private DjActivityMemberMapper djActivityMemberMapper;
    @Autowired
    private DjPartyTrainMapper partyTrainMapper;
    @Autowired
    private DjPartyTrainMemberMapper partyTrainMemberMapper;
    @Autowired
    private DjPartyExemplaryMapper djPartyExemplaryMapper;
    @Autowired
    private DjPartySpecialtyMapper djPartySpecialtyMapper;
    @Autowired
    private SysCommentMapper sysCommentMapper;
    @Autowired
    private SysUserMapper userMapper;

    @Override
    public int getMemberCount(){
        return djPartyMemberMapper.getMemberCount();
    }

    @Override
    public DjPartyMember selectPartyMemberById(Long memberId)
    {
        DjPartyMember partyMember = djPartyMemberMapper.selectDjPartyMemberById(memberId);
        if(StringUtils.isNotNull(partyMember.getDeptId())){
            partyMember.setSysDept(deptService.selectDeptById(partyMember.getDeptId()));
        }
        SysUser sysUser = userService.selectUserByPartyMemberId(memberId);
        partyMember.setSysUser(sysUser);
        return partyMember;
    }

    public DjPartyMember selectPartyMemberByUuid(String memberUuid){
        return djPartyMemberMapper.selectPartyMemberByUuid(memberUuid);
    }

    /**
     * ??????????????????
     *
     * @param memberId ????????????ID
     * @return ????????????
     */
    @Override
    public DjPartyMember selectDjPartyMemberById(Long memberId)
    {
        DjPartyMember member = djPartyMemberMapper.selectDjPartyMemberById(memberId);
        if(StringUtils.isNotNull(member.getPartyOrgId())){
            member.setDjPartyOrg(djPartyOrgService.selectDjPartyOrgById(member.getPartyOrgId()));
        }
        if(StringUtils.isNotNull(member.getDeptId())){
            member.setSysDept(deptService.selectDeptById(member.getDeptId()));
        }
        if(member.getMemberType()!=null){
            member.setMemberTypeText(dictDataService.selectDictLabel("party_member_type",member.getMemberType()));
        }
        if(member.getAdministrativePosition()!=null){
            member.setAdministrativePositionText(dictDataService.selectDictLabel("administrative_position_type",member.getAdministrativePosition()));
        }
        if(member.getPostId()!=null){
            member.setSysPost(postService.selectPostById(member.getPostId()));
        }
        if(member.getEducation()!=null){
            member.setEducationText(dictDataService.selectDictLabel("education_type",member.getEducation()));
        }
        if(member.getEducation()!=null){
            member.setEducationText(dictDataService.selectDictLabel("education_type",member.getEducation()));
        }
        if(member.getNation()!=null){
            member.setNationText(dictDataService.selectDictLabel("nation_type",member.getNation()));
        }
        if(member.getPolity()!=null){
            member.setPolityText(dictDataService.selectDictLabel("polity_type",member.getPolity()));
        }

        DjPartyMemberHelp partyMemberHelp = new DjPartyMemberHelp();
        partyMemberHelp.setPartyMemberId(memberId);
        List<DjPartyMemberHelp> memberHelpList = partyMemberHelpMapper.selectDjPartyMemberHelpList(partyMemberHelp);
        //???????????????????????????
        if(memberHelpList!=null && memberHelpList.size()>0){
            member.setMemberHelp(memberHelpList.get(0));
        }
        return member;
    }

    @Override
    public Map<String,Object> getPartyMemberRadarData(Long memberId)
    {
        Map<String,Object> result = new HashMap<>();
        DjPartyMember partyMember = djPartyMemberMapper.selectDjPartyMemberById(memberId);

        List<Map<String,Object>> indicator = new ArrayList<>();

        List<Map<String,Object>> data = new ArrayList<>();
        Map<String,Object> valueMap =  new HashMap<>();
        Long[] value = new Long[5];

        // ????????????
        Map<String,Object> activity = new HashMap<>();
        activity.put("name","????????????");
        DjActivityMember djActivityMember = new DjActivityMember();
        djActivityMember.setPartyMemberId(memberId);

        Map<String,Object> arrangeStatus = new HashMap<>();
        arrangeStatus.put("arrangeStatus",2);
        djActivityMember.setParams(arrangeStatus);
        List<DjActivityMember> activityMemberList = djActivityMemberMapper.selectDjActivityMemberList(djActivityMember);
        activity.put("max", CollectionUtils.isEmpty(activityMemberList)?0:activityMemberList.size());
        indicator.add(activity);

        activityMemberList = activityMemberList.stream().filter(
                activityMember->"2".equals(activityMember.getStatus()) ||"3".equals(activityMember.getStatus()) ||"4".equals(activityMember.getStatus()))
                .collect(Collectors.toList());
        value[0] = Long.valueOf(CollectionUtils.isEmpty(activityMemberList)?0:activityMemberList.size());

        // ????????????
        Map<String,Object> train = new HashMap<>();
        train.put("name","????????????");
        DjPartyTrainMember partyTrainMember = new  DjPartyTrainMember();
        partyTrainMember.setPartyMemberId(memberId);
        List<DjPartyTrainMember> partyTrainMemberList = partyTrainMemberMapper.selectDjPartyTrainMemberList(partyTrainMember);
        train.put("max",CollectionUtils.isEmpty(partyTrainMemberList)?0:partyTrainMemberList.size());
        indicator.add(train);

        partyTrainMemberList = partyTrainMemberList.stream().filter(
                trainMember->"2".equals(trainMember.getStatus()) ||"3".equals(trainMember.getStatus()) ||"4".equals(trainMember.getStatus()))
                .collect(Collectors.toList());
        value[1] = Long.valueOf(CollectionUtils.isEmpty(partyTrainMemberList)?0:partyTrainMemberList.size());

        // ????????????
        Map<String,Object> exemplary = new HashMap<>();
        exemplary.put("name","????????????");
        exemplary.put("max",10);
        indicator.add(exemplary);

        DjPartyExemplary partyExemplary = new DjPartyExemplary();
        partyExemplary.setMemberId(memberId);
        List<DjPartyExemplary> exemplaryList = djPartyExemplaryMapper.selectDjPartyExemplaryList(partyExemplary);
        value[2] = Long.valueOf(CollectionUtils.isEmpty(exemplaryList)?0:(exemplaryList.size()>10?10:exemplaryList.size()));

        // ????????????
        Map<String,Object> specialty = new HashMap<>();
        specialty.put("name","????????????");
        specialty.put("max",10);
        indicator.add(specialty);

        DjPartySpecialty partySpecialty = new DjPartySpecialty();
        partySpecialty.setMemberId(memberId);
        List<DjPartySpecialty> specialtyList = djPartySpecialtyMapper.selectDjPartySpecialtyList(partySpecialty);
        value[3] = Long.valueOf(CollectionUtils.isEmpty(specialtyList)?0:(specialtyList.size()>10?10:specialtyList.size()));

        // ????????????
        Map<String,Object> comment = new HashMap<>();
        comment.put("name","????????????");
        SysUser sysUser = userMapper.selectUserByPartyMemberId(memberId);
        SysComment sysComment = new SysComment();
        comment.put("max",sysCommentMapper.selectSysCommentCount(sysComment));
        indicator.add(comment);
        sysComment.setCommentUserId(sysUser.getUserId());
        value[4] = Long.valueOf(sysCommentMapper.selectSysCommentCount(sysComment));

        valueMap.put("value",value);
        data.add(valueMap);

        result.put("indicator",indicator);
        result.put("data",data);
        return result;
    }



    @Override
    public List<DjPartyMember> getDjPartyMemberList(DjPartyMember djPartyMember)
    {
        List<DjPartyMember> partyMemberList = djPartyMemberMapper.selectPartyMemberList(djPartyMember);
        partyMemberList.stream().forEach(partyMember -> {
            String deptAdminiPosFullName ="";
            if(partyMember.getDeptId()!=null){
                SysDept sysDept = sysDeptMapper.selectDeptById(partyMember.getDeptId());
                String[] sysDeptIds =sysDept.getAncestors().split(",");
                for(String sysDeptId:sysDeptIds){
                    if(!"100".equals(sysDeptId)&&!"0".equals(sysDeptId)){
                        deptAdminiPosFullName+=sysDeptMapper.selectDeptById(Long.parseLong(sysDeptId)).getDeptName()+"/";
                    }
                }
                deptAdminiPosFullName+=sysDept.getDeptName();
            }
            if(partyMember.getAdministrativePosition()!=null){
                deptAdminiPosFullName +="  "+ dictDataService.selectDictLabel("administrative_position_type",partyMember.getAdministrativePosition());
            }
            partyMember.setDeptAdminiPosFullName(deptAdminiPosFullName);
        });

        return partyMemberList;
    }

    /**
     * ????????????????????????
     *
     * @param djPartyMember ????????????
     * @return ????????????
     */
    @Override
    public List<DjPartyMember> selectDjPartyMemberList(DjPartyMember djPartyMember)
    {
        List<DjPartyMember> list = new ArrayList<DjPartyMember>();
        SysUser sysUser = SecurityUtils.getLoginUser().getUser();
        if(SecurityUtils.isAdmin(sysUser.getUserId())||SecurityUtils.isPartyOrgAll()){
            list = djPartyMemberMapper.selectDjPartyMemberList(djPartyMember);
        }else{
            djPartyMember.setPartyOrgId(sysUser.getDjPartyMember().getPartyOrgId());
            list = djPartyMemberMapper.selectPartyMemberList(djPartyMember);
        }

        list.stream().forEach( member ->{
            if(member.getPartyOrgId()!=null){
                member.setDjPartyOrg(djPartyOrgService.selectDjPartyOrgById(member.getPartyOrgId()));
            }
            if(member.getDeptId()!=null){
                member.setSysDept(deptService.selectDeptById(member.getDeptId()));
            }
            if(member.getMemberType()!=null){
                member.setMemberTypeText(dictDataService.selectDictLabel("party_member_type",member.getMemberType()));
            }
        });
        return list;
    }

    @Override
    public List<DjPartyMember> selectPartyMemberList(DjPartyMember djPartyMember)
    {

        List<DjPartyMember> list = new ArrayList<DjPartyMember>();
        SysUser sysUser = SecurityUtils.getLoginUser().getUser();
        if(SecurityUtils.isAdmin(sysUser.getUserId())||SecurityUtils.isPartyOrgAll()){
            list = djPartyMemberMapper.selectDjPartyMemberList(djPartyMember);
        }else{
            djPartyMember.setPartyOrgId(sysUser.getDjPartyMember().getPartyOrgId());
            list = djPartyMemberMapper.selectPartyMemberList(djPartyMember);
        }
        list.stream().forEach( member ->{
            if(member.getPartyOrgId()!=null){
                member.setDjPartyOrg(djPartyOrgService.selectDjPartyOrgById(member.getPartyOrgId()));
            }
            if(member.getDeptId()!=null){
                member.setSysDept(deptService.selectDeptById(member.getDeptId()));
            }
            if(member.getMemberType()!=null){
                member.setMemberTypeText(dictDataService.selectDictLabel("party_member_type",member.getMemberType()));
            }
            if(member.getAdministrativePosition()!=null){
                member.setAdministrativePositionText(dictDataService.selectDictLabel("administrative_position_type",member.getAdministrativePosition()));
            }
            if(member.getPostId()!=null){
                member.setSysPost(postService.selectPostById(member.getPostId()));
            }
            if(member.getEducation()!=null){
                member.setEducationText(dictDataService.selectDictLabel("education_type",member.getEducation()));
            }
            if(member.getEducation()!=null){
                member.setEducationText(dictDataService.selectDictLabel("education_type",member.getEducation()));
            }
            if(member.getNation()!=null){
                member.setNationText(dictDataService.selectDictLabel("nation_type",member.getNation()));
            }
            if(member.getPolity()!=null){
                member.setPolityText(dictDataService.selectDictLabel("polity_type",member.getPolity()));
            }

        });
        return list;
    }

    /**
     * ??????????????????
     *
     * @param djPartyMember ????????????
     * @return ??????
     */
    @Override
    public int insertDjPartyMember(DjPartyMember djPartyMember)
    {
        //????????????
        if("1".equals(djPartyMember.getLifeDifficulty()) && !"3".equals(djPartyMember.getMemberType()) ){
            DjPartyMemberHelp memberHelp = new DjPartyMemberHelp();
            memberHelp.setHelpUuid(UUID.randomUUID().toString());
            memberHelp.setPartyMemberId(djPartyMember.getMemberId());
            memberHelp.setPartyOrgId(djPartyMember.getPartyOrgId());
            partyMemberHelpMapper.insertDjPartyMemberHelp(memberHelp);
        }


        djPartyMember.setCreateBy(SecurityUtils.getLoginUser().getUser().getUserId().toString());
        djPartyMember.setCreateTime(DateUtils.getNowDate());
        djPartyMemberMapper.insertDjPartyMember(djPartyMember);

        SysUser sysUser = new SysUser();
        sysUser.setAvatar(djPartyMember.getAvatar());
        sysUser.setDeptId(djPartyMember.getDeptId());
        sysUser.setUserName(djPartyMember.getMemberName());
        sysUser.setNickName(djPartyMember.getMemberName());
        sysUser.setPartyMemberId(djPartyMember.getMemberId());
        sysUser.setSex(djPartyMember.getSex());
        sysUser.setPhonenumber(djPartyMember.getMobile());
        sysUser.setEmail(djPartyMember.getEmail());
        String password = configService.selectConfigByKey("sys.user.initPassword");
        sysUser.setPassword(SecurityUtils.encryptPassword(password));
        sysUser.setRoleIds(new Long[]{Long.valueOf(3)});  //??????????????????
        sysUser.setPostIds(new Long[]{Long.valueOf(4)});  //??????????????????
        sysUser.setCreateBy(SecurityUtils.getLoginUser().getUser().getUserId().toString());
        sysUser.setCreateTime(DateUtils.getNowDate());
        return userService.insertUser(sysUser);
    }

    /**
     * ??????????????????
     *
     * @param djPartyMember ????????????
     * @return ??????
     */
    @Override
    public int insertDjPartyMemberForApp(DjPartyMember djPartyMember,String password)
    {
        djPartyMember.setCreateTime(DateUtils.getNowDate());
        if(StringUtils.isBlank(djPartyMember.getPartyMemberUuid())){
            djPartyMember.setPartyMemberUuid(UUID.randomUUID().toString());
        }
        djPartyMemberMapper.insertDjPartyMember(djPartyMember);

        SysUser sysUser = new SysUser();
        sysUser.setAvatar(djPartyMember.getAvatar());
        sysUser.setDeptId(djPartyMember.getDeptId());
        sysUser.setUserName(djPartyMember.getMemberName());
        sysUser.setNickName(djPartyMember.getMemberName());
        sysUser.setPartyMemberId(djPartyMember.getMemberId());
        sysUser.setSex(djPartyMember.getSex());
        sysUser.setPhonenumber(djPartyMember.getMobile());
        sysUser.setEmail(djPartyMember.getEmail());
        sysUser.setPassword(SecurityUtils.encryptPassword(password));
        //??????????????????
        sysUser.setRoleIds(new Long[]{Long.valueOf(3)});
        //??????????????????
        sysUser.setPostIds(new Long[]{Long.valueOf(4)});
        sysUser.setCreateTime(DateUtils.getNowDate());
        //????????????
        sysUser.setState("10");
        return userService.insertUserForApp(sysUser);
    }

    /**
     * ??????????????????
     *
     * @param djPartyMember ????????????
     * @return ??????
     */
    @Override
    public int updateDjPartyMember(DjPartyMember djPartyMember)
    {

        //????????????
        if("Y".equals(djPartyMember.getLifeDifficulty()) && !"3".equals(djPartyMember.getMemberType())){
            DjPartyMember partyMember =djPartyMemberMapper.selectDjPartyMemberById(djPartyMember.getMemberId());
            //????????????????????????
            if(!"Y".equals(partyMember.getLifeDifficulty())){
                DjPartyMemberHelp partyMemberHelp = new DjPartyMemberHelp();
                partyMemberHelp.setPartyMemberId(djPartyMember.getMemberId());
                List<DjPartyMemberHelp> memberHelpList = partyMemberHelpMapper.selectDjPartyMemberHelpList(partyMemberHelp);
                //???????????????????????????
                if(memberHelpList==null || memberHelpList.size()==0){
                    DjPartyMemberHelp memberHelp = new DjPartyMemberHelp();
                    memberHelp.setHelpUuid(UUID.randomUUID().toString());
                    memberHelp.setPartyMemberId(djPartyMember.getMemberId());
                    memberHelp.setPartyOrgId(djPartyMember.getPartyOrgId());
                    partyMemberHelpMapper.insertDjPartyMemberHelp(memberHelp);
                }
            }
        }

        SysUser sysUser = userService.selectUserByPartyMemberId(djPartyMember.getMemberId());
        sysUser.setAvatar(djPartyMember.getAvatar());
        sysUser.setDeptId(djPartyMember.getDeptId());
        sysUser.setUserName(djPartyMember.getMemberName());
        sysUser.setNickName(djPartyMember.getMemberName());
        sysUser.setPartyMemberId(djPartyMember.getMemberId());
        sysUser.setSex(djPartyMember.getSex());
        sysUser.setPhonenumber(djPartyMember.getMobile());
        sysUser.setEmail(djPartyMember.getEmail());
        sysUser.setUpdateBy(SecurityUtils.getLoginUser().getUser().getUserId().toString());
        sysUser.setUpdateTime(DateUtils.getNowDate());
        userService.updateUser(sysUser);

        djPartyMember.setUpdateBy(SecurityUtils.getLoginUser().getUser().getUserId().toString());
        djPartyMember.setUpdateTime(DateUtils.getNowDate());
        return djPartyMemberMapper.updateDjPartyMember(djPartyMember);
    }

    @Override
    public int orderPartyMember(DjPartyMember djPartyMember) {
        return djPartyMemberMapper.orderPartyMember(djPartyMember);
    }


    @Override
    public int updatePartyPositionType(DjPartyMember djPartyMember){
        return djPartyMemberMapper. updatePartyPositionType(djPartyMember);
    }

    @Override
    public int updateAvatar(DjPartyMember djPartyMember){

        SysUser sysUser = userService.selectUserByPartyMemberId(djPartyMember.getMemberId());
        sysUser.setAvatar(djPartyMember.getAvatar());
        userService.updateUser(sysUser);

        djPartyMember.setUpdateBy(SecurityUtils.getLoginUser().getUser().getUserId().toString());
        djPartyMember.setUpdateTime(DateUtils.getNowDate());
        return djPartyMemberMapper.updateDjPartyMember(djPartyMember);
    }
    /**
     * ????????????????????????
     *
     * @param memberIds ???????????????????????????ID
     * @return ??????
     */
    @Override
    public int deleteDjPartyMemberByIds(Long[] memberIds)
    {
        return djPartyMemberMapper.deleteDjPartyMemberByIds(memberIds);
    }

    /**
     * ????????????????????????
     *
     * @param memberId ????????????ID
     * @return ??????
     */
    @Override
    public int deleteDjPartyMemberById(Long memberId)
    {
        return djPartyMemberMapper.deleteDjPartyMemberById(memberId);
    }

    /**
     * ???????????????????????????
     *
     * @param djPartyMember
     * @return
     */
    public String checkPartyMemberNameUnique(DjPartyMember djPartyMember)
    {
        Long memberId = StringUtils.isNull(djPartyMember.getMemberId()) ? -1L : djPartyMember.getMemberId();
        DjPartyMember info = djPartyMemberMapper.checkMemberNameUnique(djPartyMember.getMemberName());
        if (StringUtils.isNotNull(info) && info.getMemberId().longValue() != memberId.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * ??????????????????????????????
     *
     * @param djPartyMember
     * @return
     */
    public String checkPartyMemberMobileUnique(DjPartyMember djPartyMember)
    {
        Long memberId = StringUtils.isNull(djPartyMember.getMemberId()) ? -1L : djPartyMember.getMemberId();
        DjPartyMember info = djPartyMemberMapper.checkMobileUnique(djPartyMember.getMobile());
        if (StringUtils.isNotNull(info) && info.getMemberId().longValue() != memberId.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * ???????????????????????????
     *
     * @param djPartyMember
     * @return
     */
    public String checkPartyMemberEmailUnique(DjPartyMember djPartyMember)
    {
        Long memberId = StringUtils.isNull(djPartyMember.getMemberId()) ? -1L : djPartyMember.getMemberId();
        DjPartyMember info = djPartyMemberMapper.checkEmailUnique(djPartyMember.getEmail());
        if (StringUtils.isNotNull(info) && info.getMemberId().longValue() != memberId.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * ?????????????????????????????????
     *
     * @param djPartyMember
     * @return
     */
    public String checkPartyMemberIdentityCardUnique(DjPartyMember djPartyMember)
    {
        Long memberId = StringUtils.isNull(djPartyMember.getMemberId()) ? -1L : djPartyMember.getMemberId();
        DjPartyMember info = djPartyMemberMapper.checkIdentityCardUnique(djPartyMember.getIdentityCard());
        if (StringUtils.isNotNull(info) && info.getMemberId().longValue() != memberId.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    @Override
    public List<Map<String,Object>> echarts_31(){
        return djPartyMemberMapper.echarts_31();
    }

    @Override
    public List<Map<String,Object>> echarts_32(){
        return djPartyMemberMapper.echarts_32();
    }

    @Override
    public List<Map<String,Object>> echarts_33(){
        return djPartyMemberMapper.echarts_33();
    }



}

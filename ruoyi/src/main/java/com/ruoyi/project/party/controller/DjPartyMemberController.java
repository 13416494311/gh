package com.ruoyi.project.party.controller;

import com.alibaba.fastjson.JSON;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.common.utils.file.FileDownloadUtils;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.common.utils.poi.ExcelTemplateUtil;
import com.ruoyi.framework.aspectj.lang.annotation.DataScope;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.config.RuoYiConfig;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.party.domain.DjPartyMember;
import com.ruoyi.project.party.domain.DjPartyMemberChange;
import com.ruoyi.project.party.domain.DjPartyMemberEducation;
import com.ruoyi.project.party.domain.DjPartyOrg;
import com.ruoyi.project.party.service.IDjPartyMemberChangeService;
import com.ruoyi.project.party.service.IDjPartyMemberEducationService;
import com.ruoyi.project.party.service.IDjPartyMemberService;
import com.ruoyi.project.party.service.IDjPartyOrgService;
import com.ruoyi.project.sys.domain.DjSysLog;
import com.ruoyi.project.sys.domain.DjSysMessage;
import com.ruoyi.project.sys.domain.DjSysTodo;
import com.ruoyi.project.sys.service.IDjSysLogService;
import com.ruoyi.project.sys.service.IDjSysMessageService;
import com.ruoyi.project.sys.service.IDjSysTodoService;
import com.ruoyi.project.system.service.ISysDictDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * ????????????Controller
 *
 * @author ruoyi
 * @date 2020-08-06
 */
@RestController
@RequestMapping("/party/member")
public class DjPartyMemberController extends BaseController
{
    @Autowired
    private IDjPartyMemberService djPartyMemberService;
    @Autowired
    private IDjPartyMemberChangeService djPartyMemberChangeService;
    @Autowired
    private IDjSysLogService djSysLogService;
    @Autowired
    private IDjSysTodoService djSysTodoService;
    @Autowired
    private IDjSysMessageService sysMessageService;
    @Autowired
    private ISysDictDataService dictDataService;
    @Autowired
    private IDjPartyOrgService djPartyOrgService;
    @Autowired
    private IDjPartyMemberEducationService djPartyMemberEducationService;

    @GetMapping("/getMemberCount")
    public AjaxResult getMemberCount()
    {
        return AjaxResult.success(djPartyMemberService.getMemberCount());
    }

    /**
     * ????????????????????????
     */
    @RequestMapping("/list")
    @DataScope( partyOrgAlias = "pm"  ,userAlias = "u")
    public TableDataInfo list(DjPartyMember djPartyMember)
    {
        startPage();
        if(StringUtils.isNull(djPartyMember.getDelFlag())){
            djPartyMember.setDelFlag("0");
        }else if("all".equals(djPartyMember.getDelFlag())){
            djPartyMember.setDelFlag(null);
        }
        List<DjPartyMember> list = djPartyMemberService.selectDjPartyMemberList(djPartyMember);
        return getDataTable(list);
    }

    @RequestMapping("/listNoPage")
    public AjaxResult listNoPage(DjPartyMember djPartyMember)
    {
        if(StringUtils.isNull(djPartyMember.getDelFlag())){
            djPartyMember.setDelFlag("0");
        }else if("all".equals(djPartyMember.getDelFlag())){
            djPartyMember.setDelFlag(null);
        }
        List<DjPartyMember> list = djPartyMemberService.selectDjPartyMemberList(djPartyMember);
        return AjaxResult.success(list);
    }

    @PostMapping("/listForApp")
    public AjaxResult listForApp(@RequestBody DjPartyMember djPartyMember)
    {
        startPage();
        if(StringUtils.isNull(djPartyMember.getDelFlag())){
            djPartyMember.setDelFlag("0");
        }
        List<DjPartyMember> list = djPartyMemberService.selectPartyMemberList(djPartyMember);
        return AjaxResult.success(list);
    }


    /**
     * ????????????????????????
     */
    @Log(title = "????????????", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    @DataScope( partyOrgAlias = "pm"  ,userAlias = "u")
    public void export(HttpServletRequest request, HttpServletResponse response, String memberType , String partyOrgId)
    {
        Long start = System.currentTimeMillis();

        DjPartyMember djPartyMember = new DjPartyMember();
        djPartyMember.setMemberType(memberType);
        djPartyMember.setPartyOrgId(Long.valueOf(partyOrgId));
        List<DjPartyMember> list = djPartyMemberService.selectDjPartyMemberList(djPartyMember);


        String fileName = "";
        String excelTemplate ="";
        if("3".equals(djPartyMember.getMemberType())){
            fileName ="????????????????????????.xlsx";
            excelTemplate="partyPreMember.xlsx";
        }else{
            fileName ="?????????????????????????????????.xls";
            excelTemplate="partyMember.xls";
        }

        Map<String, Object> data = new HashMap<>();
        Map<String, Object> cls = new HashMap<>();
        data.put("cls", cls);
        cls.put("exportDate",DateUtils.parseDateToStr("yyyy???MM???",new Date()));

        List<Map<String,Object>> member = new ArrayList<Map<String,Object>>();
        List<Map<String,Object>> member1 = new ArrayList<Map<String,Object>>();

        list.stream().forEach(partyMember->{
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("memberName",partyMember.getMemberName()==null?"":partyMember.getMemberName());
            map.put("sex",partyMember.getSex()==null?"":dictDataService.selectDictLabel("sys_user_sex",partyMember.getSex()));
            map.put("nation",partyMember.getNation()==null?"":dictDataService.selectDictLabel("nation_type",partyMember.getNation()));
            map.put("birthday",partyMember.getBirthday()==null?"":DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD,partyMember.getBirthday()));
            map.put("age",partyMember.getBirthday()==null?"":DateUtils.getYearReduce(partyMember.getBirthday(),new Date()));
            map.put("workingDate",partyMember.getWorkingDate()==null?"":DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD,partyMember.getWorkingDate()));

            if(StringUtils.isNotEmpty(partyMember.getPartyMemberUuid())){
                DjPartyMemberEducation djPartyMemberEducation = new DjPartyMemberEducation();
                djPartyMemberEducation.setEducationUuid(partyMember.getPartyMemberUuid());
                List<DjPartyMemberEducation> educationList = djPartyMemberEducationService.selectDjPartyMemberEducationList(djPartyMemberEducation);
                final String[] educationType = {""};
                final String[] school = {""};

                List<String> firstEducationType = new ArrayList<>();
                educationList.stream().forEach(education->{
                    map.put("highestEducationType",education.getEducationType()==null?"":
                            dictDataService.selectDictLabel("education_type1",education.getEducationType()));
                    if("3".equals(education.getEducationType()) || "4".equals(education.getEducationType())
                            ||"5".equals(education.getEducationType())){
                        firstEducationType.add(education.getEducationType());
                    }
                    educationType[0] +=education.getEducationType()==null?"\n":
                            dictDataService.selectDictLabel("education_type1",education.getEducationType()) + "\n";
                    school[0] +=education.getSchool()==null?" \n":education.getSchool() + "\n";
                });
                map.put("educationType",educationType[0]);
                map.put("school",school[0]);

                if(!CollectionUtils.isEmpty(firstEducationType)){
                    map.put("firstEducationType",firstEducationType.get(0)==null?"":
                            dictDataService.selectDictLabel("education_type1",firstEducationType.get(0)));
                }else{
                    map.put("firstEducationType","");
                }

            }else{
                map.put("educationType","");
                map.put("school","");
            }

            map.put("joinData",partyMember.getJoinData()==null?"":DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD,partyMember.getJoinData()));
            map.put("formalData",partyMember.getFormalData()==null?"":DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD,partyMember.getFormalData()));
            map.put("memberType",partyMember.getMemberType()==null?"":("1".equals(partyMember.getMemberType())?"??????":"??????"));
            map.put("deptName",partyMember.getSysDept()==null?"":partyMember.getSysDept().getDeptName());
            map.put("administrativePosition",partyMember.getAdministrativePosition()==null?"":
                    dictDataService.selectDictLabel("administrative_position_type",partyMember.getAdministrativePosition()));
            map.put("title",partyMember.getTitle()==null?"":partyMember.getTitle());
            map.put("identityCard",partyMember.getIdentityCard()==null?"":partyMember.getIdentityCard());
            map.put("partyOrgFullName",partyMember.getDjPartyOrg()==null?"":
                    partyMember.getDjPartyOrg().getPartyOrgFullName().substring(partyMember.getDjPartyOrg().getPartyOrgFullName().indexOf("/")+1));
            map.put("createTime",partyMember.getCreateTime()==null?"":DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD,partyMember.getCreateTime()));

            if("1".equals(partyMember.getMemberStatus())){
                map.put("memberStatus",partyMember.getMemberStatus()==null?"":
                        dictDataService.selectDictLabel("party_member_status",partyMember.getMemberStatus()));
                member.add(map);
            }else{
                map.put("memberStatus",partyMember.getMemberStatus()==null?"":
                        dictDataService.selectDictLabel("party_member_status",partyMember.getMemberStatus()));
                member1.add(map);
            }
        });

        cls.put("member",member);
        cls.put("member1",member1);


        ServletOutputStream outputStream = null;
        try {
            ExcelTemplateUtil.setResponseHeader(response,fileName);
            outputStream = response.getOutputStream();
            ExcelTemplateUtil.process(data,excelTemplate,outputStream);
            outputStream.flush();
            outputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        Long end = System.currentTimeMillis();

        logger.info("============???????????????????????????{}ms",end-start);
    }


    /**
     * ??????????????????????????????
     */
    @GetMapping(value = "/{memberId}")
    public AjaxResult getInfo(@PathVariable("memberId") Long memberId)
    {
        return AjaxResult.success(djPartyMemberService.selectDjPartyMemberById(memberId));
    }

    /**
     * ????????????????????????????????????
     */
    @GetMapping(value = "/getPartyMemberRadarData/{memberId}")
    public AjaxResult getPartyMemberRadarData(@PathVariable("memberId") Long memberId)
    {

        return AjaxResult.success(djPartyMemberService.getPartyMemberRadarData(memberId));
    }

    /**
     * ??????????????????
     */
    @Log(title = "????????????", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Map<String,Object> params)
    {
        //???????????????
        DjPartyMemberChange memberChange = JSON.parseObject(JSON.toJSONString(params), DjPartyMemberChange.class);
        memberChange.setMemberUuid(UUID.randomUUID().toString());
        memberChange.setPartyMemberId(memberChange.getMemberId());
        memberChange.setChangeType("add");
        memberChange.setAuditState("2");
        djPartyMemberChangeService.insertDjPartyMemberChange(memberChange);

        createSysLogAndTodo( memberChange,
                Long.parseLong(params.get("auditUserId").toString()),
                params.get("operReason")==null?null:params.get("operReason").toString());

        return AjaxResult.success(memberChange);
    }

    /**
     * ??????????????????
     */
    @Log(title = "????????????", businessType = BusinessType.INSERT)
    @PostMapping(value = "add")
    public AjaxResult add1(@RequestBody DjPartyMember djPartyMember)
    {
        return AjaxResult.success(djPartyMemberService.insertDjPartyMember(djPartyMember));
    }

    @Log(title = "????????????", businessType = BusinessType.INSERT)
    @PostMapping(value = "/updateAvatar")
    public AjaxResult updateAvatar(@RequestBody DjPartyMember partyMember)
    {
        if(StringUtils.isNull(partyMember.getMemberId())){
            return AjaxResult.error("???????????????");
        }
        return AjaxResult.success(djPartyMemberService.updateAvatar(partyMember));
    }

    /**
     * ??????????????????
     */
    @Log(title = "????????????", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Map<String,Object> params)
    {

        //???????????????
        DjPartyMemberChange memberChange = JSON.parseObject(JSON.toJSONString(params), DjPartyMemberChange.class);
        memberChange.setMemberUuid(UUID.randomUUID().toString());
        memberChange.setChangeType("edit");
        memberChange.setPartyMemberId(Long.parseLong(params.get("memberId").toString()));
        memberChange.setAuditState("2");
        djPartyMemberChangeService.insertDjPartyMemberChange(memberChange);

        createSysLogAndTodo( memberChange,
                Long.parseLong(params.get("auditUserId").toString()),
                params.get("operReason")==null?null:params.get("operReason").toString());


        return AjaxResult.success(memberChange);
    }

    /**
     * ??????????????????
     */
    @Log(title = "????????????", businessType = BusinessType.UPDATE)
    @PutMapping(value = "/update")
    public AjaxResult update(@RequestBody DjPartyMember djPartyMember)
    {
        return AjaxResult.success(djPartyMemberService.updateDjPartyMember(djPartyMember));
    }

    /**
     * ??????????????????
     */
    @Log(title = "??????????????????", businessType = BusinessType.UPDATE)
    @PutMapping(value = "/order")
    public AjaxResult order(@RequestBody DjPartyMember djPartyMember)
    {
        return AjaxResult.success(djPartyMemberService.orderPartyMember(djPartyMember));
    }


    private void createSysLogAndTodo(DjPartyMemberChange memberChange,Long auditUserId, String operReason){
        //??????????????????
        DjSysLog sysLog = new DjSysLog();
        sysLog.setUuid(memberChange.getMemberUuid());
        sysLog.setStepType("his");
        sysLog.setStepName("????????????");
        sysLog.setOperUserId(SecurityUtils.getLoginUser().getUser().getUserId());
        switch (memberChange.getChangeType()){
            case "add" : sysLog.setOperResult("??????????????????");  break;
            case "edit" : sysLog.setOperResult("??????????????????");  break;
            case "del" : sysLog.setOperResult("??????????????????");  break;
            case "enable" : sysLog.setOperResult("??????????????????");  break;
            default:break;
        }
        sysLog.setOperTime(new Date());
        sysLog.setOperReason(operReason);
        djSysLogService.insertDjSysLog(sysLog);

        DjSysLog nextSysLog = new DjSysLog();
        nextSysLog.setUuid(memberChange.getMemberUuid());
        nextSysLog.setStepType("cur");
        switch (memberChange.getChangeType()){
            case "add" : nextSysLog.setStepName("??????????????????");  break;
            case "edit" : nextSysLog.setStepName("??????????????????");  break;
            case "del" : nextSysLog.setStepName("??????????????????");  break;
            case "enable" : nextSysLog.setStepName("??????????????????");  break;
            default:break;
        }
        nextSysLog.setOperUserId(auditUserId);
        djSysLogService.insertDjSysLog(nextSysLog);

        DjSysTodo sysTodo = new DjSysTodo();
        sysTodo.setUuid(memberChange.getMemberUuid());
        sysTodo.setType("3"); //??????????????????
        switch (memberChange.getChangeType()){
            case "add" : sysTodo.setTitle(memberChange.getMemberName()+" ????????????");;  break;
            case "edit" : sysTodo.setTitle(memberChange.getMemberName()+" ????????????");;  break;
            case "del" : sysTodo.setTitle(memberChange.getMemberName()+" ????????????");;  break;
            case "enable" : sysTodo.setTitle(memberChange.getMemberName()+" ????????????");;  break;
            default:break;
        }
        sysTodo.setUrlName("MemberChangeDetail");
        sysTodo.setUrlPath("todo/memberChangeDetail");
        sysTodo.setUserId(auditUserId);
        sysTodo.setStatus("0");
        Map<String, String> map = new HashMap<String, String>();
        map.put("sysLogId", nextSysLog.getId().toString());
        map.put("memberUuid", memberChange.getMemberUuid());
        sysTodo.setUrlParams(JSON.toJSONString(map));
        djSysTodoService.insertDjSysTodo(sysTodo);

        DjSysMessage sysMessage = new DjSysMessage();
        sysMessage.setMessageUuid(sysTodo.getUuid());
        sysMessage.setTitle(dictDataService.selectDictLabel("sys_todo_type",sysTodo.getType()));
        sysMessage.setContent("???????????????"+sysTodo.getTitle()+"???????????????????????????????????????!");
        sysMessage.setType(2);
        sysMessage.setPlatform(0);
        sysMessage.setGroupName("");
        sysMessage.setStatus("0");
        sysMessage.setUserIds(sysTodo.getUserId().toString());
        sysMessageService.insertDjSysMessage(sysMessage);
    }

    /**
     * ??????????????????
     */
    @Log(title = "????????????", businessType = BusinessType.DELETE)
    @PostMapping("/del")
    public AjaxResult remove(@RequestBody Map<String,Object> params)
    {
        DjPartyMember djPartyMember = djPartyMemberService.selectDjPartyMemberById(Long.parseLong(params.get("memberId").toString()));

        DjPartyMemberChange memberChange = new DjPartyMemberChange();
        BeanUtils.copyBeanProp(memberChange,djPartyMember);
        memberChange.setMemberUuid(UUID.randomUUID().toString());
        memberChange.setChangeType("del");
        memberChange.setPartyMemberId(djPartyMember.getMemberId());
        memberChange.setAuditState("2");

        createSysLogAndTodo( memberChange,
                Long.parseLong(params.get("auditUserId").toString()),
                params.get("operReason")==null?null:params.get("operReason").toString());

        return toAjax(djPartyMemberChangeService.insertDjPartyMemberChange(memberChange));
    }

    @Log(title = "????????????", businessType = BusinessType.DELETE)
    @PostMapping("/enable")
    public AjaxResult enable(@RequestBody Map<String,Object> params)
    {
        DjPartyMember djPartyMember = djPartyMemberService.selectDjPartyMemberById(Long.parseLong(params.get("memberId").toString()));

        DjPartyMemberChange memberChange = new DjPartyMemberChange();
        BeanUtils.copyBeanProp(memberChange,djPartyMember);
        memberChange.setMemberUuid(UUID.randomUUID().toString());
        memberChange.setChangeType("enable");
        memberChange.setPartyMemberId(djPartyMember.getMemberId());
        memberChange.setAuditState("2");

        createSysLogAndTodo( memberChange,
                Long.parseLong(params.get("auditUserId").toString()),
                params.get("operReason")==null?null:params.get("operReason").toString());

        return toAjax(djPartyMemberChangeService.insertDjPartyMemberChange(memberChange));
    }

    /**
     * ????????????
     */
    @Log(title = "????????????", businessType = BusinessType.UPDATE)
    @PostMapping("/avatar")
    public AjaxResult avatar(@RequestParam("avatarfile") MultipartFile file) throws IOException
    {
        if (!file.isEmpty())
        {
            String avatar = FileUploadUtils.upload(RuoYiConfig.getAvatarPath(), file);
            return AjaxResult.success(avatar);
        }
        return AjaxResult.error("???????????????????????????????????????");
    }


    /**
     *  ???????????? ????????? ????????? ??????????????????
     * @param djPartyMember
     * @return
     * @throws IOException
     */
    @PostMapping("/checkPartyMemberUnique")
    public AjaxResult checkPartyMemberUnique(@Validated @RequestBody DjPartyMember djPartyMember) throws IOException
    {

        if (UserConstants.NOT_UNIQUE.equals(djPartyMemberService.checkPartyMemberNameUnique(djPartyMember)))
        {
            return AjaxResult.success("???????????????");
        }
        else if (UserConstants.NOT_UNIQUE.equals(djPartyMemberService.checkPartyMemberMobileUnique(djPartyMember)))
        {
            return AjaxResult.success("??????????????????");
        }
        else if (UserConstants.NOT_UNIQUE.equals(djPartyMemberService.checkPartyMemberEmailUnique(djPartyMember)))
        {
            return AjaxResult.success("Email?????????");
        }
        else if (UserConstants.NOT_UNIQUE.equals(djPartyMemberService.checkPartyMemberIdentityCardUnique(djPartyMember)))
        {
            return AjaxResult.success("?????????????????????");
        }
        return AjaxResult.success();
    }

    /**
     *  ???????????? ????????? ????????? ??????????????????
     * @param djPartyMember
     * @return
     * @throws IOException
     */
    @PostMapping("/checkPartyMemberUniquePc")
    public AjaxResult checkPartyMemberUniquePc(@Validated @RequestBody DjPartyMember djPartyMember) throws IOException
    {

        if (UserConstants.NOT_UNIQUE.equals(djPartyMemberService.checkPartyMemberNameUnique(djPartyMember)))
        {
            return AjaxResult.success("???????????????");
        }
        else if (UserConstants.NOT_UNIQUE.equals(djPartyMemberService.checkPartyMemberMobileUnique(djPartyMember)))
        {
            return AjaxResult.success("??????????????????");
        }
        else if (UserConstants.NOT_UNIQUE.equals(djPartyMemberService.checkPartyMemberEmailUnique(djPartyMember)))
        {
            return AjaxResult.success("Email?????????");
        }
        else if (UserConstants.NOT_UNIQUE.equals(djPartyMemberService.checkPartyMemberIdentityCardUnique(djPartyMember)))
        {
            return AjaxResult.success("?????????????????????");
        }
        return AjaxResult.success();
    }

    @GetMapping("/listPartyOrgPositionType/{partyOrgUuid}")
    public AjaxResult listPartyOrgPositionType(@PathVariable("partyOrgUuid") String partyOrgUuid)
    {
        DjPartyOrg partyOrg = djPartyOrgService.selectDjPartyOrgByPartyOrgUuid(partyOrgUuid);
        DjPartyMember djPartyMember = new DjPartyMember();
        djPartyMember.setPartyOrgId(partyOrg.getPartyOrgId());
        List<DjPartyMember> djPartyMemberList = djPartyMemberService.getDjPartyMemberList(djPartyMember);
        return AjaxResult.success(djPartyMemberList.stream().filter(partyMember ->
                StringUtils.isNotEmpty(partyMember.getPartyPositionType())
        ).collect(Collectors.toList()));
    }

}

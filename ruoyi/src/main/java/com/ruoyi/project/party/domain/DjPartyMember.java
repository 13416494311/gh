package com.ruoyi.project.party.domain;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.project.members.domain.DjPartyMemberHelp;
import com.ruoyi.project.system.domain.SysDept;
import com.ruoyi.project.system.domain.SysDictData;
import com.ruoyi.project.system.domain.SysPost;
import com.ruoyi.project.system.domain.SysUser;
import lombok.Data;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * 会员信息对象 dj_party_member
 *
 * @author ruoyi
 * @date 2020-08-06
 */
@Data
public class DjPartyMember extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 用户ID */
    private Long memberId;

    private String partyMemberUuid;

    /** 工会ID */
    @Excel(name = "工会ID")
    private Long partyOrgId;

    private DjPartyOrg djPartyOrg;

    /** 工号 */
    private String workNo;

    /** 会员照片 */
    private String avatar;

    /** 会员姓名 */
    @Excel(name = "会员姓名")
    private String memberName;

    /** 用户性别（0男 1女 2未知） */
    private String sex;

    /** 手机号 */
    @Excel(name = "手机号")
    private String mobile;

    /** 身份证号 */
    private String identityCard;

    /** 出生日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    /** 所在单位 */
    private String companyName;

    /** 部门 */
    @Excel(name = "部门")
    private Long deptId;

    private SysDept sysDept;

    private String deptAdminiPosFullName ;

    /** 职务 */
    private String administrativePosition;

    /** 职务 */
    private String administrativePositionText;

    /** 会内职务 */
    private String partyPositionType;

    private String partyPositionTypeText;

    /** 职称 */
    private String title;

    /** 岗位 */
    private Long postId;

    /** 岗位 */
    private SysPost sysPost;

    /** 参加工作日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date workingDate;

    /** 民族 */
    private String nation;

    private String nationText;

    /** 政治面貌 */
    private String polity;

    private String polityText;

    /** 身份 */
    private String workIdentity;

    /** 学历 */
    private String education;

    /** 学历 */
    private String educationText;

    /** 学位 */
    private String academicDegree;

    /** 籍贯 */
    private String nativePlace;

    /** 家庭住址 */
    private String homeAddress;

    /** 固定电话 */
    private String housePhone;

    /** 电子邮箱 */
    private String email;

    /** qq */
    private String qq;

    /** 微信号码 */
    private String wechat;

    /** 会员类型 */
    @Excel(name = "会员类型")
    private String memberType;

    private String memberTypeText;

    /** 在岗状态 */
    @Excel(name = "在岗状态")
    private String memberStatus;

    /** 加入会支部日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date joinBranchData;

    /** 加入会日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date joinData;

    /** 成为入会积极分子日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date preMemberData;

    /** 转为正式会员日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date formalData;

    /** 流动会员（1：是  0：否） */
    private String floatingType;

    /** 会员分组 */
    private String memberGroup;

    /** 上一个工会ID */
    private Long prePartyOrgId;

    /** 生活困难（1：是  0：否） */
    private String lifeDifficulty;

    private DjPartyMemberHelp memberHelp;

    /** 组织认定（1：是  0：否） */
    private String cognizance;

    /** 经济状况 */
    private String economicSituation;

    /** 身体健康情况 */
    private String physicalHealth;

    /** 生活困难类型 */
    private String lifeDifficultyType;

    /** 享受帮扶 */
    private String enjoyHelp;

    /** 补助情况 */
    private String helpInfo;

    /** 具体情况描述 */
    private String detail;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    private SysUser sysUser;

    private Integer orderNum;

    private List<DjPartyMemberDue> memberDueList;

}

<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :span="6" :xs="24">
        <div class="head-container">
          <el-input
            v-model="partyOrgName"
            placeholder="请输入工会架构名称"
            clearable
            size="small"
            prefix-icon="el-icon-search"
            style="margin-bottom: 20px"
          />
        </div>
        <div class="head-container">
          <el-tree
            :data="partyOrgOptions"
            :props="defaultProps"
            :expand-on-click-node="false"
            :filter-node-method="filterNode"
            ref="tree"
            default-expand-all
            @node-click="handleNodeClick"
          />
        </div>
      </el-col>
      <el-col :span="18" :xs="24">
        <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
          <el-form-item label="会员姓名" prop="memberName">
            <el-input
              v-model="queryParams.memberName"
              placeholder="请输入会员姓名"
              clearable
              size="small"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="会员类型" prop="memberType">
            <el-select v-model="queryParams.memberType" placeholder="请选择会员类型" clearable size="small">
              <el-option
                v-for="dict in memberTypeOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              />
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
          </el-form-item>
        </el-form>
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button
              type="primary"
              icon="el-icon-plus"
              size="mini"
              @click="handleAdd"
              v-hasPermi="['party:member:add']"
            >新增
            </el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="warning"
              icon="el-icon-download"
              size="mini"
              @click="handleExport"
              v-hasPermi="['party:member:export']"
            >导出
            </el-button>
          </el-col>
        </el-row>
        <el-table :stripe="true"
                  :border="true" v-loading="loading" :data="partyMemberList" @selection-change="handleSelectionChange">
          <el-table-column label="序号" align="center" type="index" :index="indexMethod"/>
          <el-table-column label="会员姓名" align="center" prop="memberName"/>
          <el-table-column label="会内职务" align="center" prop="partyPositionType" :formatter="partyPositionTypeFormat" />
          <el-table-column label="部门" align="center" prop="deptId" :formatter="deptIdFormat" />
          <el-table-column label="工会" align="center" prop="partyOrgId" :formatter="partyOrgIdFormat" />
          <el-table-column label="会员类型" align="center" prop="memberType" :formatter="memberTypeFormat"/>
          <!--<el-table-column label="在岗状态" align="center" prop="memberStatus" :formatter="memberStatusFormat"/>-->
          <el-table-column label="排序" align="center"  width="180" v-if="checkPermi(['party:member:order'])">
            <template slot-scope="scope">
              <el-input-number style="width: 100px" v-model="scope.row.orderNum" size="small" controls-position="right"
                               :precision="0" :step="1" :min="0" ></el-input-number>
              <el-button
                style="width: 50px"
                size="small"
                type="text"
                @click="handleOrder(scope.row)"
              >保存
              </el-button>
            </template>
          </el-table-column>
          <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
            <template slot-scope="scope">
              <el-button
                size="small"
                type="text"
                icon="el-icon-search"
                @click="handleSee(scope.row)"
              >查看
              </el-button>
              <el-button
                size="small"
                type="text"
                icon="el-icon-s-custom"
                @click="handlePortrait(scope.row)"
              >画像
              </el-button>
              <el-button
                size="small"
                type="text"
                icon="el-icon-edit"
                @click="handleUpdate(scope.row)"
                v-if="showHandleUpdate(scope.row)"
                v-hasPermi="['party:member:edit']"
              >修改
              </el-button>
              <el-button
                size="small"
                type="text"
                icon="el-icon-delete"
                @click="chooseAuditUser1(scope.row,'del')"
                v-if="showHandleUpdate(scope.row)"
                v-show="scope.row.delFlag ==0"
                v-hasPermi="['party:member:remove']"
              >禁用
              </el-button>
              <el-button
                size="small"
                type="text"
                icon="el-icon-delete"
                @click="chooseAuditUser1(scope.row,'enable')"
                v-show="scope.row.delFlag ==1"
                v-hasPermi="['party:member:remove']"
              >启用
              </el-button>
            </template>
          </el-table-column>
        </el-table>
        <pagination
          v-show="total>0"
          :total="total"
          :page.sync="queryParams.pageNum"
          :limit.sync="queryParams.pageSize"
          @pagination="getList"
        />
      </el-col>
    </el-row>
    <!-- 添加或修改会员信息对话框 -->
    <party-member-dialog ref="partyMemberDialog"/>

    <choose-audit-user ref="chooseAuditUser"  @ok="submitForm" @del="handleDelete" @enable="handleEnable"/>
    <member-portrait ref="memberPortrait"/>
  </div>
</template>

<style lang="scss" scoped>
  .head-container {
    overflow-x: hidden;
  }

  .avatar-upload {
    margin-left: 40%;
  }

  .avatar {
    width: 120px;
    height: 160px;
    align-items: center;
    border: 1px solid #e6ebf5;
    img{
      height:100%;
    }
    div {
      width:100%;
      height:100%;
      background: url("../../../assets/image/dj-avatar.jpg") no-repeat ;
      background-size: 100% 100%;
    }
  }

</style>

<script>
  import {
    listPartyMember,
    getPartyMember,
    delPartyMember,
    enablePartyMember,
    addPartyMember,
    updatePartyMember,
    updatePartyMemberAvatar,
    orderPartyMember,
    exportPartyMember,
    uploadAvatar,
    checkPartyMemberUnique,
  } from "@/api/party/member";
  import { listPartyMemberChange } from "@/api/party/memberChange";
  import { postOptionSelect } from "@/api/system/post";
  import { partyOrgTreeselect,partyOrgTreeselectByEdit, getPartyOrg } from "@/api/party/org";
  import { getDept } from "@/api/system/dept";
  import { treeselect } from "@/api/system/dept";
  import Treeselect from "@riophae/vue-treeselect";
  import "@riophae/vue-treeselect/dist/vue-treeselect.css";
  import selectTree from '../../components/selectTree';
  import { getUserProfile } from "@/api/system/user";
  import ChooseAuditUser from "../../audit/chooseAuditUser";
  import { listLog, getLog, delLog, addLog, updateLog, exportLog } from "@/api/sys/log";
  import Specialty from "../../members/specialty/specialty";
  import Exemplary from "../../members/exemplary/exemplary";
  import MemberPortrait from "./memberPortrait";
  import MemberEducation from "../memberEducation/memberEducation";
  import {downLoadZip} from "@/utils/zipdownload";
  import { checkPermi, checkRole } from "@/utils/permission";
  import MemberWork from "../../member/work/memberWork";
  import MemberFamily from "../../member/family/memberFamily";
  import PartyMemberDialog from "./partyMemberDialog";

  export default {
    name: "PartyMember",
    components: {
      PartyMemberDialog,
      MemberFamily,
      MemberWork,
      MemberEducation, MemberPortrait, Exemplary, Specialty, ChooseAuditUser, Treeselect,selectTree},
    data() {
      let checkIdentityCard = (rule, value, callback) => {
        if (!value) {
          callback();
        } else {
          const reg = /^\d{6}(18|19|20)?\d{2}(0[1-9]|1[0-2])(([0-2][1-9])|10|20|30|31)\d{3}(\d|X|x)$/;
          const card = reg.test(value);
          if (!card) {
            //判断座机为12位
            callback(new Error("身份证格式如:423024xxxx0216xxxx"));
          } else {
            let param={};
            param.memberId = this.form.memberId;
            param.identityCard  = value;
            checkPartyMemberUnique(param).then(response => {
              if (response.code === 200 && response.msg.indexOf("已存在")!=-1) {
                callback(new Error(response.msg));
              } else {
                callback();
              }
            });
          }
        }
      };
      let checkPhone = (rule, value, callback) => {
        if (!value) {
          callback();
        } else {
          const telReg = /^1(3[0-9]|4[5,7]|5[0,1,2,3,5,6,7,8,9]|6[2,5,6,7]|7[0,1,7,8]|8[0-9]|9[1,8,9])\d{8}$/;
          const isTelPhone = telReg.test(value);
          const reg = /^((0\d{2,3}-\d{7,8})|(1[3584]\d{9}))$/;
          const isPhone = reg.test(value);
          if (!isTelPhone && !isPhone) {
            callback(new Error("请输入正确手机号或座机"));
          } else {
            callback();
          }
        }
      };
      let checkMobile = (rule, value, callback) => {
        if (!value) {
          callback();
        } else {
          const telReg = /^1(3[0-9]|4[5,7]|5[0,1,2,3,5,6,7,8,9]|6[2,5,6,7]|7[0,1,7,8]|8[0-9]|9[1,8,9])\d{8}$/;
          const isTelPhone = telReg.test(value);
          if (!isTelPhone ) {
            callback(new Error("请输入正确手机号"));
          } else {
            let param={};
            param.memberId = this.form.memberId;
            param.mobile = value;
            checkPartyMemberUnique(param).then(response => {
              if (response.code === 200 && response.msg.indexOf("已存在")!=-1) {
                callback(new Error(response.msg));
              } else {
                callback();
              }
            });
          }
        }
      };
      let checkEmail = (rule, value, callback) => {
        if (!value) {
          callback();
        } else {
          let param={};
          param.memberId = this.form.memberId;
          param.email = value;
          checkPartyMemberUnique(param).then(response => {
            if (response.code === 200 && response.msg.indexOf("已存在")!=-1) {
              callback(new Error(response.msg));
            } else {
              callback();
            }
          });
        }
      };
      let checkMemberName = (rule, value, callback) => {
        if (!value) {
          return new Error("请输入会员姓名");
        } else {
          let param={};
          param.memberId = this.form.memberId;
          param.memberName = value;
          checkPartyMemberUnique(param).then(response => {
            if (response.code === 200 && response.msg.indexOf("已存在")!=-1) {
              callback(new Error(response.msg));
            } else {
              callback();
            }
          });
        }
      };
      return {
        // 遮罩层
        loading: true,
        // 选中数组
        ids: [],
        // 非单个禁用
        single: true,
        // 非多个禁用
        multiple: true,
        // 总条数
        total: 0,
        // 会员信息表格数据
        partyMemberList: [],
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        // 用户性别字典
        sexOptions: [],
        // 职务字典
        administrativePositionOptions: [],
        // 会内职务字典
        partyPositionTypeOptions: [],
        // 岗位选项
        postOptions: [],
        // 民族字典
        nationOptions: [],
        // 婚姻状况
        maritalStatusOptions: [],
        // 政治面貌字典
        polityOptions: [],
        // 身份字典
        workIdentityOptions: [],
        // 学历字典
        educationOptions: [],
        // 学位字典
        academicDegreeOptions: [],
        // 户口性质字典
        householdStatusOptions: [],
        // 会员类型字典
        memberTypeOptions: [],
        // 在岗状态字典
        memberStatusOptions: [],
        // 流动会员字典
        floatingTypeOptions: [],
        // 会员分组字典
        memberGroupOptions: [],
        // 生活困难字典
        lifeDifficultyOptions: [],
        lifeDifficulty:false,
        // 组织认定字典
        cognizanceOptions: [],
        // 经济状况字典
        economicSituationOptions: [],
        // 身体健康情况字典
        physicalHealthOptions: [],
        // 生活困难类型字典
        lifeDifficultyTypeOptions: [],
        // 工会架构树选项
        orgOptions: [],
        partyOrgOptions: [],
        partyOrgOptionsByEdait: [],
        // 部门树选项
        deptOptions: [],
        // 享受帮扶字典
        enjoyHelpOptions: [],
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          memberName: undefined,
          memberType: undefined,
        },
        defaultProps: {
          value: 'id',          // 唯一标识
          label: 'label',       // 标签显示
          children: 'children', // 子级
        },
        // 表单参数
        form: {},
        // 表单校验
        rules: {
          memberName: [
            {required: true, message: "会员姓名不能为空", trigger: "blur"},
            {validator: checkMemberName, trigger: 'blur'}
          ],
          sex: [
            {required: true, message: "性别不能为空", trigger: "blur"},
          ],
          mobile: [
            {required: true, message: "联系电话不能为空", trigger: "blur"},
            { validator: checkMobile, trigger: "blur" }
          ],
          urgentPerson: [
            {required: true, message: "紧急联系人不能为空", trigger: "blur"},
          ],
          urgentPersonMobile: [
            {required: true, message: "紧急联系电话不能为空", trigger: "blur"},
            { validator: checkMobile, trigger: "blur" }
          ],
          identityCard: [
            { required: false, message: "身份证号不能为空", trigger: "blur" },
            {validator: checkIdentityCard, trigger: 'blur'}
          ],
          birthday: [
            { required: true, message: "出生日期不能为空", trigger: "blur" },
          ],
          companyName: [
            { required: true, message: "所在单位不能为空", trigger: "blur" },
          ],
          deptId: [
            { required: true, message: "部门不能为空", trigger: "blur" },
          ],
          administrativePosition: [
            { required: true, message: "职务不能为空", trigger: "blur" },
          ],
          postId: [
            { required: true, message: "岗位不能为空", trigger: "blur" },
          ],
          workingDate: [
            { required: true, message: "参加工作日期不能为空", trigger: "blur" },
          ],
          nativePlace: [
            { required: true, message: "籍贯不能为空", trigger: "blur" },
          ],
          householdAddress: [
            { required: true, message: "户口所在地不能为空", trigger: "blur" },
          ],
          homeAddress: [
            { required: true, message: "现居住住址不能为空", trigger: "blur" },
          ],
          householdStatus: [
            { required: true, message: "户口性质不能为空", trigger: "blur" },
          ],
          polity: [
            { required: true, message: "政治面貌不能为空", trigger: "blur" },
          ],
          education: [
            { required: true, message: "学历不能为空", trigger: "blur" },
          ],
          nation: [
            { required: true, message: "民族不能为空", trigger: "blur" },
          ],
          maritalStatus: [
            { required: true, message: "婚姻状况不能为空", trigger: "blur" },
          ],
          joinData: [
            { required: true, message: "入会日期不能为空", trigger: "blur" },
          ],
          housePhone: [
            { validator: checkPhone, trigger: "blur" }
          ],
          email: [
            {
              type: "email",
              message: "请输入正确的邮箱地址",
              trigger: ["blur", "change"]
            },
            {validator: checkEmail, trigger: 'blur'}
          ],
          partyOrgId: [
            { required: true, message: "工会不能为空", trigger: "blur" }
          ],
          memberType: [
            { required: true, message: "会员类型不能为空", trigger: "blur" }
          ],
          memberStatus: [
            { required: true, message: "在岗状态不能为空", trigger: "blur" }
          ],

        },
        bodyStyle: {
          overflowY: 'auto',
          height: '',
          marginLeft: '2%',
          paddingRight: '2%',
        },
        // 工会架构名称
        partyOrgName: undefined,
        partyOrg:{
          partyOrgId:undefined,
          partyOrgName:undefined,
        },
        avatarUrl:'',
        disabled:false,
        afterTimeOption: {
          disabledDate(date) {
            //disabledDate 文档上：设置禁用状态，参数为当前日期，要求返回 Boolean
            return date.getTime() > Date.now();
          }
        },
        user: {},
        formalDataRequire:false,
      };
    },
    mounted() {
      window.addEventListener('resize', this.getHeight);
    },
    watch: {
      // 根据名称筛选部门树
      partyOrgName(val) {
        this.$refs.tree.filter(val);
      },
      formalData(val){
        this.getDateYearSub(val,this.getNowFormatDate)
      }

    },
    created() {
      this.getUser();
      this.getPartyOrgTreeSelect();
      this.getPartyOrgTreeSelectByEdit();
      this.getDeptTreeselect();
      this.postOptionSelect();
      this.getDicts("sys_user_sex").then(response => {
        this.sexOptions = response.data;
      });
      this.getDicts("administrative_position_type").then(response => {
        this.administrativePositionOptions = response.data;
      });
      this.getDicts("party_position_type").then(response => {
        this.partyPositionTypeOptions = response.data;
      });
      this.getDicts("nation_type").then(response => {
        this.nationOptions = response.data;
      });
      this.getDicts("marital_status_type").then(response => {
        this.maritalStatusOptions = response.data;
      });
      this.getDicts("polity_type").then(response => {
        this.polityOptions = response.data;
      });
      this.getDicts("work_identity_type").then(response => {
        this.workIdentityOptions = response.data;
      });
      this.getDicts("education_type").then(response => {
        this.educationOptions = response.data;
      });
      this.getDicts("academic_degree_type").then(response => {
        this.academicDegreeOptions = response.data;
      });
      this.getDicts("party_member_type").then(response => {
        this.memberTypeOptions = response.data;
      });
      this.getDicts("party_member_status").then(response => {
        this.memberStatusOptions = response.data;
      });
      this.getDicts("sys_yes_no").then(response => {
        this.floatingTypeOptions = response.data;
      });
      this.getDicts("party_member_group").then(response => {
        this.memberGroupOptions = response.data;
      });
      this.getDicts("sys_yes_no").then(response => {
        this.lifeDifficultyOptions = response.data;
      });
      this.getDicts("sys_yes_no").then(response => {
        this.cognizanceOptions = response.data;
      });
      this.getDicts("economic_situation_type").then(response => {
        this.economicSituationOptions = response.data;
      });
      this.getDicts("physical_health_type").then(response => {
        this.physicalHealthOptions = response.data;
      });
      this.getDicts("life_difficulty_type").then(response => {
        this.lifeDifficultyTypeOptions = response.data;
      });
      this.getDicts("sys_yes_no").then(response => {
        this.enjoyHelpOptions = response.data;
      });
      this.getDicts("household_status_type").then(response => {
        this.householdStatusOptions = response.data;
      });

    },
    methods: {
      checkPermi,
      checkRole,
      //下拉树选择后设置值
      setVModelValue(vModel,val){
        if(val!=null){
          this.form[vModel] =  val;
        }else{
          this.form[vModel] =  undefined;
        }
      },
      /**附件上传*/
      uploadAtt(file) {
        const loading = this.$loading({
          lock: true,
          text: '上传中……',
          spinner: 'el-icon-loading',
          background: 'rgba(0, 0, 0, 0.7)'
        });
        let formData = new FormData();
        formData.append("avatarfile", file.file);
        uploadAvatar(formData).then(response => {
          if (response.code === 200) {
            this.form.avatar = response.msg
            this.avatarUrl=process.env.VUE_APP_BASE_API + response.msg;
            if (this.form.memberId != undefined) {
              let formData = {}
              formData.memberId = this.form.memberId
              formData.avatar = this.form.avatar
              updatePartyMemberAvatar(formData).then(response => {
                if (response.code === 200) {
                  loading.close();
                  this.msgSuccess("上传成功！")
                } else {
                  this.msgError(response.msg);
                }
              });
            }else{
              loading.close();
              this.msgSuccess("上传成功！")
            }
          } else {
            loading.close();
            this.msgError(response.msg);
          }
        }).catch(function (err) {
          loading.close();
        });
      },
      // 筛选节点
      filterNode(value, data) {
        if (!value) return true;
        return data.label.indexOf(value) !== -1;
      },
      // 节点单击事件
      handleNodeClick(data) {
        this.queryParams.pageNum = 1;
        this.queryParams.partyOrgId = data.id;
        this.partyOrg.partyOrgId = data.id;
        this.partyOrg.partyOrgName = data.label;
        this.getList();
      },
      /** 对话框自适应高度 */
      getHeight() {
        this.bodyStyle.height = window.innerHeight - 281 + 'px';
      },
      /** 查询岗位下拉结构 */
      postOptionSelect(){
        postOptionSelect().then(response => {
          this.postOptions = response.data;
        });
      },
      /** 查询部门下拉树结构 */
      getDeptTreeselect() {
        treeselect().then(response => {
          this.deptOptions = response.data;
        });
      },
      /** 查询工会下拉树结构 */
      getPartyOrgTreeSelect() {
        partyOrgTreeselect().then(response => {
          this.partyOrgOptions = this.treeInitData(response.data);
        });
      },
      /** 修改时查询工会下拉树结构 */
      getPartyOrgTreeSelectByEdit() {
        partyOrgTreeselectByEdit().then(response => {
          this.partyOrgOptionsByEdait = this.treeInitData(response.data);
        });
      },
      /** 查询会员信息列表 */
      getList() {
        this.loading = true;
        this.queryParams.delFlag = "all";
        listPartyMember(this.queryParams).then(response => {
          let partyMemberList = response.rows;
          for(var i = 0; i < partyMemberList.length; i++) {
            if(partyMemberList[i].orderNum == null ){
              delete partyMemberList[i].orderNum
            }
          }
          this.partyMemberList = partyMemberList;
          this.total = response.total;
          this.loading = false;
        });
      },
      indexMethod(index) {
        let pageNum = Number(this.queryParams.pageNum);
        let pageSize = Number(this.queryParams.pageSize);
        return  (pageNum -1)*pageSize+index+1;
      },
      // 用户性别字典翻译
      sexFormat(row, column) {
        return this.selectDictLabel(this.sexOptions, row.sex);
      },
      // 职务字典翻译
      administrativePositionFormat(row, column) {
        return this.selectDictLabel(this.administrativePositionOptions, row.administrativePosition);
      },
      // 会内职务字典翻译
      partyPositionTypeFormat(row, column) {
        return this.selectDictLabel(this.partyPositionTypeOptions, row.partyPositionType);
      },
      // 民族字典翻译
      nationFormat(row, column) {
        return this.selectDictLabel(this.nationOptions, row.nation);
      },
      // 政治面貌字典翻译
      polityFormat(row, column) {
        return this.selectDictLabel(this.polityOptions, row.polity);
      },
      // 身份字典翻译
      workIdentityFormat(row, column) {
        return this.selectDictLabel(this.workIdentityOptions, row.workIdentity);
      },
      // 学历字典翻译
      educationFormat(row, column) {
        return this.selectDictLabel(this.educationOptions, row.education);
      },
      // 学位字典翻译
      academicDegreeFormat(row, column) {
        return this.selectDictLabel(this.academicDegreeOptions, row.academicDegree);
      },
      // 部门id翻译
      deptIdFormat(row, column,value){
        if(row.sysDept!=null){
          return row.sysDept.deptFullName;
        }else{
          return "";
        }
      },
      // 工会id翻译
      partyOrgIdFormat(row, column){
        return row.djPartyOrg.partyOrgName;
      },
      // 会员类型字典翻译
      memberTypeFormat(row, column) {
        return this.selectDictLabel(this.memberTypeOptions, row.memberType);
      },
      orderNumFormat(row, column,value) {

        console.log(value)
      },
      // 在岗状态字典翻译
      memberStatusFormat(row, column) {
        return this.selectDictLabel(this.memberStatusOptions, row.memberStatus);
      },
      // 流动会员字典翻译
      floatingTypeFormat(row, column) {
        return this.selectDictLabel(this.floatingTypeOptions, row.floatingType);
      },
      // 会员分组字典翻译
      memberGroupFormat(row, column) {
        return this.selectDictLabel(this.memberGroupOptions, row.memberGroup);
      },
      // 生活困难字典翻译
      lifeDifficultyFormat(row, column) {
        return this.selectDictLabel(this.lifeDifficultyOptions, row.lifeDifficulty);
      },
      // 组织认定字典翻译
      cognizanceFormat(row, column) {
        return this.selectDictLabel(this.cognizanceOptions, row.cognizance);
      },
      // 经济状况字典翻译
      economicSituationFormat(row, column) {
        return this.selectDictLabel(this.economicSituationOptions, row.economicSituation);
      },
      // 身体健康情况字典翻译
      physicalHealthFormat(row, column) {
        return this.selectDictLabel(this.physicalHealthOptions, row.physicalHealth);
      },
      // 生活困难类型字典翻译
      lifeDifficultyTypeFormat(row, column) {
        return this.selectDictLabel(this.lifeDifficultyTypeOptions, row.lifeDifficultyType);
      },
      // 享受帮扶字典翻译
      enjoyHelpFormat(row, column) {
        return this.selectDictLabel(this.enjoyHelpOptions, row.enjoyHelp);
      },
      // 取消按钮
      cancel() {
        this.open = false;
        this.reset();
      },
      // 表单重置
      reset() {
        this.form = {
          memberId: undefined,
          partyMemberUuid: undefined,
          partyOrgId: undefined,
          workNo: undefined,
          avatar: undefined,
          memberName: undefined,
          sex: undefined,
          mobile: undefined,
          urgentPerson: undefined,
          urgentPersonMobile: undefined,
          identityCard: undefined,
          birthday: undefined,
          companyName: undefined,
          deptId: undefined,
          administrativePosition: undefined,
          title: undefined,
          postId: undefined,
          workingDate: undefined,
          nation: undefined,
          maritalStatus: undefined,
          polity: undefined,
          workIdentity: undefined,
          education: undefined,
          academicDegree: undefined,
          nativePlace: undefined,
          householdAddress: undefined,
          householdStatus: undefined,
          homeAddress: undefined,
          housePhone: undefined,
          email: undefined,
          qq: undefined,
          wechat: undefined,
          remark: undefined,
          memberType: undefined,
          memberStatus: undefined,
          joinBranchData: undefined,
          joinData: undefined,
          formalData: undefined,
          floatingType: undefined,
          memberGroup: undefined,
          prePartyOrgId: undefined,
          lifeDifficulty: undefined,
          cognizance: undefined,
          economicSituation: undefined,
          physicalHealth: undefined,
          lifeDifficultyType: undefined,
          enjoyHelp: undefined,
          helpInfo: undefined,
          detail: undefined,
          delFlag: undefined,
          createBy: undefined,
          createTime: undefined,
          updateBy: undefined,
          updateTime: undefined
        };
        this.resetForm("form");
      },
      /** 搜索按钮操作 */
      handleQuery() {
        this.queryParams.pageNum = 1;
        this.getList();
      },
      /** 重置按钮操作 */
      resetQuery() {
        this.resetForm("queryForm");
        this.handleQuery();
      },
      // 多选框选中数据
      handleSelectionChange(selection) {
        this.ids = selection.map(item => item.memberId)
        this.single = selection.length != 1
        this.multiple = !selection.length
      },
      /** 新增按钮操作 */
      handleAdd() {
        this.$refs.partyMemberDialog.handleAdd();
      },
      /** 查看按钮操作 */
      handleSee(row){
        this.$refs.partyMemberDialog.handleSee(row);
      },
      handlePortrait(row){
        this.$refs.memberPortrait.init(row.memberId)
      },
      handleOrder(row){
        let partyMember ={} ;
        partyMember.memberId = row.memberId;
        partyMember.orderNum = row.orderNum;
        orderPartyMember(partyMember).then(response => {
          if (response.code === 200) {
            this.msgSuccess("排序成功");
            this.getList();
          } else {
            this.msgError(response.msg);
          }
        });

      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.$refs.partyMemberDialog.handleUpdate(row);
      },
      chooseAuditUser(){
        this.$refs["form"].validate(valid => {
          if (valid) {
            if(this.form.memberId){
              listPartyMemberChange({"partyMemberId":this.form.memberId, "auditState":"2"}).then(response => {
                if(response.rows&&response.rows.length >0){
                  this.msgSuccess("该会员变更正在审批中！")
                }else{
                  this.$refs.chooseAuditUser.init(6,"edit",this.form.memberId)
                }
              });
            }else{
              this.$refs.chooseAuditUser.init(6,"add",this.form.memberId)
            }
          }
        });



      },
      /** 提交按钮 */
      submitForm: function (form) {
        this.$refs["form"].validate(valid => {
          if (valid) {
            this.form.auditUserId = form.auditUserId;
            this.form.operReason = form.reason;
            if (this.form.memberId != undefined) {
              updatePartyMember(this.form).then(response => {
                if (response.code === 200) {
                  this.msgSuccess("提交审批成功");
                  this.open = false;
                } else {
                  this.msgError(response.msg);
                }
              });
            } else {
              addPartyMember(this.form).then(response => {
                if (response.code === 200) {
                  this.msgSuccess("提交审批成功");
                  this.open = false;
                } else {
                  this.msgError(response.msg);
                }
              });
            }
          }else{
            setTimeout(() => {
              var isError = document.getElementsByClassName("is-error");
              isError[0].querySelector('input').focus();
            }, 100);
            return false;
          }
        });
      },
      chooseAuditUser1(row,type){
        listPartyMemberChange({"partyMemberId":row.memberId, "auditState":"2"}).then(response => {
          if(response.rows&&response.rows.length >0){
            this.msgSuccess("该会员变更正在审批中！")
          }else{
            this.$refs.chooseAuditUser.init(6,type,row.memberId)
          }
        });
      },
      /** 删除按钮操作 */
      handleDelete(form) {
        delPartyMember(form).then(response => {
          if (response.code === 200) {
            this.msgSuccess("提交审批成功");
            this.getList();
          } else {
            this.msgError(response.msg);
          }
        });
      },
      handleEnable(form) {
        enablePartyMember(form).then(response => {
          if (response.code === 200) {
            this.msgSuccess("提交审批成功");
            this.getList();
          } else {
            this.msgError(response.msg);
          }
        });
      },
      /** 导出按钮操作 */
      handleExport() {
        let that = this;
        this.$confirm('是否确认导出所选工会所有会员信息数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function () {
          console.log(that.queryParams)
          downLoadZip("/party/member/export?partyOrgId="+ that.queryParams.partyOrgId);
        });
      },
      getUser() {
        getUserProfile().then(response => {
          this.user = response.data;
          if(this.user.djPartyMember){
            this.queryParams.partyOrgId = this.user.djPartyMember.partyOrgId;
          }
        }).then(()=>{
          this.getList();
        });
      },
      showHandleUpdate(row){
        let showFlag = false;
        let roles = this.user.roles;
        if(roles && roles.length!=0){
          for(let i=0;i<roles.length;i++){
            //管理员角色
            if(roles[i].roleId == 1){
              showFlag = true;
              break;
            }
          }
        }
        if(this.user.djPartyMember && this.user.djPartyMember.partyOrgId == row.partyOrgId){
          showFlag = true;
        }
        return showFlag ;
      },
      changeMemberType(){
        /*if(this.form.memberType == '1'){
         this.formalDataRequire = true
       }else{
         this.formalDataRequire = false
       }*/
        this.formalDataRequire = false
      },
      lifeDifficultyChange(){
        if(this.form.lifeDifficulty == 'Y'){
          this.lifeDifficulty = true
        }else{
          this.lifeDifficulty = false
        }
      },
    }
  };
</script>

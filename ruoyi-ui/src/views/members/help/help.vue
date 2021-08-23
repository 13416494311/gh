<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="100px">
      <el-form-item label="会员姓名" prop="memberName">
        <el-input
          v-model="queryParams.memberName"
          placeholder="请输入会员姓名"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>

      <el-form-item label="工会名称" prop="partyOrgId">
        <select-tree :value="queryParams.partyOrgId"
                     style="width:100%;"
                     :options="partyOrgOptions"
                     vModel="partyOrgId"
                     @selected="setVModelValue"
                     placeholder="请选择工会"
        />
      </el-form-item>

      <!--<el-form-item label="组织认定" prop="cognizance">
        <el-select v-model="queryParams.cognizance" placeholder="请选择组织认定" clearable size="small">
          <el-option
            v-for="dict in cognizanceOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="困难程度" prop="economicSituation">
        <el-select v-model="queryParams.economicSituation" placeholder="请选择困难程度" clearable size="small">
          <el-option
            v-for="dict in economicSituationOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>-->
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
          v-hasPermi="['members:help:add']"
        >新增
        </el-button>
      </el-col>
    </el-row>


    <el-table :stripe="true" :border="true"   v-loading="loading" :data="helpList" @selection-change="handleSelectionChange">
      <el-table-column label="序号" align="center" type="index"/>
      <el-table-column label="会员姓名" align="center" prop="djPartyMember.memberName" />
      <el-table-column label="工会名称" align="center" prop="djPartyOrg.partyOrgFullName" />
      <!--<el-table-column label="组织认定" align="center" prop="cognizance" :formatter="cognizanceFormat" />
      <el-table-column label="困难程度" align="center" prop="economicSituation" :formatter="economicSituationFormat" />-->
      <el-table-column label="申报人" align="center" prop="createUser.userName" />
      <el-table-column label="申报时间" align="center" prop="createTime" >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="帮扶类别" align="center" prop="helpType" :formatter="helpTypeFormat" />
      <el-table-column label="状态" align="center" prop="status" :formatter="auditStateFormat" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="small"
            type="text"
            icon="el-icon-search"
            @click="handleSee(scope.row)"
          >查看</el-button>
          <el-button
            v-if="scope.row.status =='1'||scope.row.status =='4'"
            size="small"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['members:help:edit']"
          >修改</el-button>
          <el-button
            v-if="scope.row.status =='3'"
            size="small"
            type="text"
            icon="el-icon-edit"
            @click="handleRecordAdd(scope.row)"
            v-hasPermi="['members:help:edit']"
          >帮扶</el-button>
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

    <!-- 添加或修改会员帮扶对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="90%" append-to-body
               @open="getHeight"  :close-on-click-modal="false">
      <el-form ref="form" :model="form" :rules="rules" :style="bodyStyle"  label-width="150px">
        <el-card shadow="always" style="margin-bottom: 30px;">
          <div slot="header" style="height: 25px">
            <span style="font-weight: bold;font-size: 16px">基本信息</span>
          </div>

          <el-row>
            <el-col :span="12">
              <el-form-item label="会员姓名" prop="partyMemberId">
                <el-input :disabled="true" v-model="form.partyMemberName" placeholder="请选择换会员名称">
                  <el-button :disabled="disabled" slot="append" icon="el-icon-search"
                             @click="openMemberChoose"></el-button>
                </el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="工会名称" prop="partyOrgId">
                <select-tree :disabled="true"
                             :value="form.partyOrgId"
                             :options="partyOrgOptions"
                             vModel="partyOrgId"
                             @selected="setVModelValue"
                             placeholder="请选择工会"
                />
              </el-form-item>
            </el-col>


          <el-col :span="12">
            <el-form-item label="帮扶类别" prop="helpType">
              <el-select v-model="form.helpType"
                         :disabled="disabled"
                         style="width: 100%"
                         placeholder="请选择帮扶类别">
                <el-option
                  v-for="dict in helpTypeOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>

            <el-col :span="24">
              <el-form-item label="帮扶描述" prop="helpDesc">
                <el-input  v-model="form.helpDesc"
                           type="textarea" :autosize="{ minRows: 4, maxRows: 6}"  placeholder="请输入帮扶描述" :disabled="disabled" />
              </el-form-item>
            </el-col>
          <el-col :span="24">
            <el-form-item label="附件" prop="">
              <upload-all-file ref="uploadAllFile" :disabled="disabled"/>
            </el-form-item>
          </el-col>

          <!--<el-row>
            <el-col :span="12">
              <el-form-item label="组织认定" prop="cognizance">
                <el-select v-model="form.cognizance"
                           :disabled="disabled"
                           style="width: 100%"
                           placeholder="请选择组织认定">
                  <el-option
                    v-for="dict in cognizanceOptions"
                    :key="dict.dictValue"
                    :label="dict.dictLabel"
                    :value="dict.dictValue"
                  ></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="困难程度" prop="economicSituation">
                <el-select v-model="form.economicSituation"
                           :disabled="disabled"
                           style="width: 100%"
                           placeholder="请选择困难程度">
                  <el-option
                    v-for="dict in economicSituationOptions"
                    :key="dict.dictValue"
                    :label="dict.dictLabel"
                    :value="dict.dictValue"
                  ></el-option>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>-->

          </el-row>

        </el-card>

        <help-record v-show="form.status =='3'" ref="helpRecord" :disabled ="disabled1"/>

        <audit-log ref="auditLog"/>

      </el-form>
      <div slot="footer" class="dialog-footer"  :style="{textAlign:'center'}">
        <el-button v-show="!disabled" type="primary" @click="submitForm(1)">保 存</el-button>
        <el-button v-show="!disabled" type="primary" @click="chooseAuditUser">提 交</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <party-member-choose ref="partyMember" @callbackMember="setMember"/>

    <choose-audit-user ref="chooseAuditUser"  @ok="handleSubmit"/>

  </div>
</template>

<script>
  import { listHelp, getHelp, delHelp, addHelp, updateHelp, exportHelp } from "@/api/members/help";
  import HelpRecord from "./helpRecord";
  import {getUserProfile} from "@/api/system/user";
  import {partyOrgTreeselect, getPartyOrg} from "@/api/party/org";
  import selectTree from '../../components/selectTree';
  import UploadAllFile from "../../upload/uploadAllFile";
  import { getPartyMember,} from "@/api/party/member";
  import PartyMemberChoose from "../../party/org/partyMemberChoose";
  import ChooseAuditUser from "../../audit/chooseAuditUser";
  import AuditLog from "../../audit/auditLog";

  export default {
    name: "Help",
    components: {AuditLog, ChooseAuditUser, PartyMemberChoose, UploadAllFile, HelpRecord ,selectTree},
    data() {
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
        // 会员帮扶表格数据
        helpList: [],
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        // 组织认定字典
        cognizanceOptions: [],
        // 困难程度字典
        economicSituationOptions: [],
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          helpUuid: undefined,
          partyMemberId: undefined,
          partyOrgId: undefined,
          cognizance: undefined,
          economicSituation: undefined,
          status: undefined,
        },
        // 表单参数
        form: {},
        // 表单校验
        rules: {
          partyMemberId: [
            { required: true, message: "会员姓名不能为空", trigger: "blur" }
          ],
          partyOrgId: [
            { required: true, message: "工会名称不能为空", trigger: "blur" }
          ],
          cognizance: [
            { required: false, message: "组织认定不能为空", trigger: "blur" }
          ],
          economicSituation: [
            { required: false, message: "困难程度不能为空", trigger: "blur" }
          ],
          helpType: [
            { required: true, message: "帮扶类别不能为空", trigger: "blur" }
          ],
        },
        bodyStyle:{
          overflowY:'auto',
          height: '',
          marginLeft:'2%' ,
          paddingRight:'2%',
        },
        disabled:false,
        disabled1:true,
        //组织架构
        partyOrgOptions: [],
        helpTypeOptions: [],
        // 审批状态字典
        auditStateOptions: [],
      };
    },
    mounted () {
      window.addEventListener('resize', this.getHeight);
    },
    created() {
      this.getUser();
      //组织架构树
      this.getPartyOrgTreeSelect();
      this.getDicts("sys_yes_no").then(response => {
        this.cognizanceOptions = response.data;
      });
      this.getDicts("economic_situation_type").then(response => {
        this.economicSituationOptions = response.data;
      });
      this.getDicts("help_type").then(response => {
        this.helpTypeOptions = response.data;
      });
      this.getDicts("audit_state").then(response => {
        this.auditStateOptions = response.data;
      });
    },
    methods: {
      auditStateFormat(row, column){
        return this.selectDictLabel(this.auditStateOptions, row.status);
      },
      openMemberChoose(){
        this.$refs.partyMember.open = true ;
        this.$refs.partyMember.title = "选择会员";
      },
      setMember(member){
        this.form.partyMemberId = member.memberId;
        this.form.partyMemberName = member.memberName;
        getPartyMember(this.form.partyMemberId).then(response => {
          this.form.partyOrgId  = response.data.partyOrgId;
        });
      },
      //获取组织架构树
      getPartyOrgTreeSelect() {
        partyOrgTreeselect().then(response => {
          this.partyOrgOptions = this.treeInitData(response.data);
        });
      },
      //下拉树选择后设置值
      setVModelValue(vModel, val) {
        if (val != null) {
          this.queryParams[vModel] = val;
        } else {
          this.queryParams[vModel] = undefined;
        }
        this.handleQuery();
      },
      getUser() {
        getUserProfile().then(response => {
          this.user = response.data;
        }).then(()=>{
          this.getList()
        })
      },
      /** 对话框自适应高度 */
      getHeight(){
        this.bodyStyle.height=window.innerHeight-281+'px';
      },
      /** 查询会员帮扶列表 */
      getList() {
        this.loading = true;
        listHelp(this.queryParams).then(response => {
          this.helpList = response.rows;
          this.total = response.total;
          this.loading = false;
        });
      },
      // 组织认定字典翻译
      cognizanceFormat(row, column) {
        return this.selectDictLabel(this.cognizanceOptions, row.cognizance);
      },
      // 困难程度字典翻译
      economicSituationFormat(row, column) {
        return this.selectDictLabel(this.economicSituationOptions, row.economicSituation);
      },
      // 组织认定字典翻译
      helpTypeFormat(row, column) {
        return this.selectDictLabel(this.helpTypeOptions, row.helpType);
      },
      // 取消按钮
      cancel() {
        this.open = false;
        this.reset();
      },
      // 表单重置
      reset() {
        this.form = {
          helpId: undefined,
          helpUuid: undefined,
          partyMemberId: undefined,
          partyMemberName: undefined,
          partyOrgId: undefined,
          partyOrgName: undefined,
          cognizance: undefined,
          economicSituation: undefined,
          helpType: undefined,
          helpDesc: undefined,
          status: "0",
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
        this.ids = selection.map(item => item.helpId)
        this.single = selection.length!=1
        this.multiple = !selection.length
      },
      /** 新增按钮操作 */
      handleAdd() {
        this.reset();
        this.disabled = false;
        this.open = true;
        this.form.helpUuid = this.uuid();
        this.title = "添加会员帮扶";
        this.$nextTick(function () {
          this.$refs.uploadAllFile.init(this.form.helpUuid, 'help') ;
          this.$refs.helpRecord.init(this.form.helpUuid) ;
          this.$refs.auditLog.init(this.form.helpUuid);
        })
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.reset();
        this.disabled1=true;
        this.disabled = false;
        const helpId = row.helpId || this.ids
        getHelp(helpId).then(response => {
          this.form = response.data;
          this.form.partyOrgName = this.form.djPartyOrg.partyOrgName;
          this.form.partyMemberName = this.form.djPartyMember.memberName;
          this.open = true;
          this.title = "修改会员帮扶";
        }).then(()=>{
          this.$refs.helpRecord.init(this.form.helpUuid) ;
          this.$refs.uploadAllFile.init(this.form.helpUuid, 'help') ;
          this.$refs.auditLog.init(this.form.helpUuid);
        });
      },
      handleRecordAdd(row){
        this.handleSee(row).then(()=>{
          this.disabled1=false;
        });
      },
      handleSee(row) {
        this.reset();
        this.disabled1=true;
        this.disabled = true;
        const helpId = row.helpId || this.ids
        getHelp(helpId).then(response => {
          this.form = response.data;
          this.form.partyOrgName = this.form.djPartyOrg.partyOrgName;
          this.form.partyMemberName = this.form.djPartyMember.memberName;
          this.open = true;
          this.title = "查看会员帮扶";
        }).then(()=>{
          this.$refs.helpRecord.init(this.form.helpUuid) ;
          this.$refs.uploadAllFile.init(this.form.helpUuid, 'help') ;
          this.$refs.auditLog.init(this.form.helpUuid);
        });
      },
      chooseAuditUser(){
        this.$refs["form"].validate(valid => {
          if (valid) {
            this.$refs.chooseAuditUser.init(17);
          }
        });
      },
      handleSubmit(form){
        this.form.params = {}
        this.form.params.auditUserId = form.auditUserId;
        this.form.params.operReason = form.reason;
        this.submitForm(2);
      },
      /** 提交按钮 */
      submitForm: function(status) {
        this.$refs["form"].validate(valid => {
          if (valid) {
            this.form.status = status;
            if (this.form.helpId != undefined) {
              updateHelp(this.form).then(response => {
                if (response.code === 200) {
                  this.msgSuccess("修改成功");
                  this.open = false;
                  this.getList();
                } else {
                  this.msgError(response.msg);
                }
              });
            } else {
              addHelp(this.form).then(response => {
                if (response.code === 200) {
                  this.msgSuccess("新增成功");
                  this.open = false;
                  this.getList();
                } else {
                  this.msgError(response.msg);
                }
              });
            }
          }
        });
      },
      /** 删除按钮操作 */
      handleDelete(row) {
        const helpIds = row.helpId || this.ids;
        this.$confirm('是否确认删除会员帮扶编号为"' + helpIds + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delHelp(helpIds);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
      },
      /** 导出按钮操作 */
      handleExport() {
        const queryParams = this.queryParams;
        this.$confirm('是否确认导出所有会员帮扶数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportHelp(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
      }
    }
  };
</script>

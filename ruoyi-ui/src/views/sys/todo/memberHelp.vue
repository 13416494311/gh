<template>
  <div style="padding: 30px">
    <el-form ref="form" :model="form" :rules="rules"  label-width="150px">
      <el-card shadow="always" style="margin-bottom: 30px;">
        <div slot="header" style="height: 25px">
          <span style="font-weight: bold;font-size: 16px">基本信息</span>
        </div>
        <el-row>
          <el-col :span="12">
            <el-form-item label="会员姓名" prop="partyMemberId">
              <el-input :disabled="true" v-model="form.partyMemberName" placeholder="请选择换会员名称">
                <el-button slot="append" icon="el-icon-search"
                           type="primary"
                           class="input-button"
                           @click="partyMemberOpen"></el-button>
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
        </el-row>
      </el-card>

    </el-form>

    <audit-log ref="auditLog"/>

    <div :style="{textAlign:'center',paddingTop:'30px'}">
      <el-button v-if="todoStatus=='0'" type="primary" @click="auditResultOpen">审 核</el-button>
      <el-button @click="cancel">关 闭</el-button>
    </div>

    <audit-result ref="auditResult" @ok="submitAudit"/>

    <party-member-dialog ref="partyMemberDialog"/>

  </div>

</template>

<style  lang="scss" scoped>
  .input-button {
    background-color:#1890ff !important;
    color:#fff !important;
  }
</style>

<script>

  import {listTodo, getTodo, delTodo, addTodo, updateTodo, exportTodo} from "@/api/sys/todo";
  import {partyOrgTreeselect, getPartyOrg} from "@/api/party/org";
  import { listHelp, getHelp, delHelp, addHelp, updateHelp, exportHelp } from "@/api/members/help";
  import SelectTree from "../../components/selectTree";
  import UploadAllFile from "../../upload/uploadAllFile";
  import AuditResult from "../../audit/auditResult";
  import AuditLog from "../../audit/auditLog";
  import {addLog, delLog, exportLog, getLog, listLog, updateLog} from "@/api/sys/log";
  import PartyMemberDialog from "../../party/member/partyMemberDialog";

  export default {
    name: "MemberHelp",
    components: {PartyMemberDialog, AuditLog, AuditResult, UploadAllFile, SelectTree },
    data() {
      return {
        todoId: undefined,
        todoStatus: undefined,
        disabled: false,
        helpId:undefined,
        sysLogId:undefined,
        status:undefined,
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
        disabled:false,
        //组织架构
        partyOrgOptions: [],
        helpTypeOptions: [],
      };
    },
    mounted() {

    },
    created() {
      this.init();
      //组织架构树
      this.getPartyOrgTreeSelect();
      this.getDicts("help_type").then(response => {
        this.helpTypeOptions = response.data;
      });
    },
    watch:{

    },
    methods: {

      init() {
        this.todoId = this.$route.query.todoId;
        getTodo(this.todoId).then(response => {
          if (response.data == undefined) {
            this.$store.dispatch('tagsView/delView', this.$route)
            this.$router.go(-1)
          }
          this.todoStatus = response.data.status
          let params = eval('(' + response.data.urlParams + ')');
          this.helpId = Number(params.helpId);
          this.sysLogId = Number(params.sysLogId);
        }).then(() =>{
          this.handleSee();
        });
      },
      handleSee() {
        this.reset();
        this.disabled = true;
        const helpId = this.helpId
        getHelp(helpId).then(response => {
          this.form = response.data;
          this.form.partyMemberName = this.form.djPartyMember.memberName;
        }).then(()=>{
          this.$refs.uploadAllFile.init(this.form.helpUuid, 'help') ;
          this.$refs.auditLog.init(this.form.helpUuid);
        });
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
      // 取消按钮
      cancel() {
        this.$store.dispatch('tagsView/delView', this.$route)
        this.$router.go(-1)
      },
      auditResultOpen() {
        this.$refs.auditResult.init();
      },
      submitAudit(form) {
        this.form.params = {}
        this.form.params.state = form.state;
        this.form.params.reason = form.reason;
        this.form.params.sysLogId = this.sysLogId;
        this.form.status = form.state;
        updateHelp(this.form).then(response => {
          if (response.code === 200) {
            this.msgSuccess("审批成功");
          } else {
            this.msgError(response.msg);
          }
        }).then(()=>{
          updateLog({"id": this.sysLogId, "operResult": form.state=='3'?"通过":"不通过", "operReason": form.reason})
        }).then(() => {
          updateTodo({"todoId": this.todoId, "status": "1"}).then(response => {
            if (response.code === 200) {
              this.$store.dispatch('tagsView/delView', this.$route)
              this.$router.go(-1)
            } else {
              this.msgError(response.msg);
            }
          });
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
      partyMemberOpen(){
        let row={memberId:this.form.partyMemberId}
        this.$refs.partyMemberDialog.handleSee(row);
      },
    }
  };
</script>

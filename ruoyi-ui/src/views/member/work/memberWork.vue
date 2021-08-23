<template>
  <div>
    <el-card shadow="always" style="margin-bottom: 30px;">
      <div slot="header" style="height: 25px">
        <span style="font-weight: bold;font-size: 16px">工作经历</span>
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="small"
          @click="handleAdd"
          v-hasPermi="['member:work:add']"
          style="float: right;margin-top: -5px"
          v-show="!disabled"
        >新增
        </el-button>
      </div>
      <el-table :border="true"  :stripe="true"
                v-loading="loading" :data="workList" @selection-change="handleSelectionChange">
        <el-table-column label="序号" align="center" type="index" />
        <el-table-column label="起始时间" align="center" prop="startDate" width="180">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.startDate, '{y}-{m}-{d}') }}</span>
          </template>
        </el-table-column>
        <el-table-column label="截止时间" align="center" prop="endDate" width="180">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.endDate, '{y}-{m}-{d}') }}</span>
          </template>
        </el-table-column>
        <el-table-column label="工作单位" align="center" prop="workCompanyName" />
        <el-table-column label="工作岗位" align="center" prop="workPost" />
        <el-table-column label="证明人" align="center" prop="witness" />
        <el-table-column label="联系方式" align="center" prop="witnessMobile" />
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
              v-show="!disabled"
              size="small"
              type="text"
              icon="el-icon-edit"
              @click="handleUpdate(scope.row)"
              v-hasPermi="['member:work:edit']"
            >修改</el-button>
            <el-button
              v-show="!disabled"
              size="small"
              type="text"
              icon="el-icon-delete"
              @click="handleDelete(scope.row)"
              v-hasPermi="['member:work:remove']"
            >删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
    <!-- 添加或修改工作经历对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="80%" append-to-body
               @open="getHeight"  :close-on-click-modal="false">
      <el-form ref="form" :model="form" :rules="rules" :style="bodyStyle"  label-width="150px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="起始时间" prop="startDate">
              <el-date-picker :disabled="disabled1"
                              clearable size="small" style="width: 100%"
                              v-model="form.startDate"
                              type="date"
                              value-format="yyyy-MM-dd"
                              placeholder="选择起始时间">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="截止时间" prop="endDate">
              <el-date-picker :disabled="disabled1"
                              clearable size="small" style="width: 100%"
                              v-model="form.endDate"
                              type="date"
                              value-format="yyyy-MM-dd"
                              placeholder="选择截止时间">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="工作单位" prop="workCompanyName">
              <el-input :disabled="disabled1" v-model="form.workCompanyName" placeholder="请输入工作单位" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="工作岗位" prop="workPost">
              <el-input :disabled="disabled1" v-model="form.workPost" placeholder="请输入工作岗位" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="证明人" prop="witness">
              <el-input :disabled="disabled1" v-model="form.witness" placeholder="请输入证明人" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="联系方式" prop="witnessMobile">
              <el-input :disabled="disabled1" v-model="form.witnessMobile" placeholder="请输入联系方式" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer"  :style="{textAlign:'center'}">
        <el-button v-if="!disabled1" type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import {addWork, delWork, exportWork, getWork, listWork, updateWork} from "@/api/member/work";

  export default {
    name: "MemberWork",
    props: {
      disabled: {
        type: Boolean,
        default: () => {
          return true
        }
      },
    },
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
        // 工作经历表格数据
        workList: [],
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        // 查询参数
        queryParams: {
          memberUuid: undefined,
          startDate: undefined,
          endDate: undefined,
          workCompanyName: undefined,
          workPost: undefined,
          witness: undefined,
          witnessMobile: undefined,
        },
        // 表单参数
        form: {},
        // 表单校验
        rules: {
          startDate: [
            { required: true, message: "起始时间不能为空", trigger: "blur" }
          ],
          endDate: [
            { required: true, message: "截止时间不能为空", trigger: "blur" }
          ],
          workCompanyName: [
            { required: true, message: "工作单位不能为空", trigger: "blur" }
          ],
          workPost: [
            { required: true, message: "工作岗位不能为空", trigger: "blur" }
          ],
          witness: [
            { required: false, message: "证明人不能为空", trigger: "blur" }
          ],
          witnessMobile: [
            { required: false, message: "联系方式不能为空", trigger: "blur" },
            { validator: this.$validate.validMobile, trigger: "blur" }
          ],
        },
        bodyStyle:{
          overflowY:'auto',
          height: '',
          marginLeft:'2%' ,
          paddingRight:'2%',
        },
        memberUuid:undefined,
        disabled1:false,
      };
    },
    mounted () {
      window.addEventListener('resize', this.getHeight);
    },
    created() {
      this.getList();
    },
    methods: {
      init(memberUuid){
        this.memberUuid = memberUuid
        this.getList();
      },
      /** 对话框自适应高度 */
      getHeight(){
        this.bodyStyle.height=window.innerHeight-281+'px';
      },
      /** 查询工作经历列表 */
      getList() {
        this.loading = true;
        this.queryParams.memberUuid = this.memberUuid;
        listWork(this.queryParams).then(response => {
          this.workList = response.rows;
          this.total = response.total;
          this.loading = false;
        });
      },
      // 取消按钮
      cancel() {
        this.open = false;
        this.reset();
      },
      // 表单重置
      reset() {
        this.form = {
          workId: undefined,
          memberUuid: undefined,
          startDate: undefined,
          endDate: undefined,
          workCompanyName: undefined,
          workPost: undefined,
          witness: undefined,
          witnessMobile: undefined,
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
        this.ids = selection.map(item => item.workId)
        this.single = selection.length!=1
        this.multiple = !selection.length
      },
      /** 新增按钮操作 */
      handleAdd() {
        this.reset();
        this.form.memberUuid = this.memberUuid;
        this.open = true;
        this.title = "添加工作经历";
        this.disabled1 =false;
      },

      /** 修改按钮操作 */
      handleUpdate(row) {
        this.reset();
        const workId = row.workId || this.ids
        getWork(workId).then(response => {
          this.form = response.data;
          this.open = true;
          this.title = "修改工作经历";
          this.disabled1 =false;
        });
      },
      handleSee(row) {
        this.reset();
        const workId = row.workId || this.ids
        getWork(workId).then(response => {
          this.form = response.data;
          this.open = true;
          this.title = "查看工作经历";
          this.disabled1 =true;
        });
      },
      /** 提交按钮 */
      submitForm: function() {
        this.$refs["form"].validate(valid => {
          if (valid) {
            if (this.form.workId != undefined) {
              updateWork(this.form).then(response => {
                if (response.code === 200) {
                  this.msgSuccess("修改成功");
                  this.open = false;
                  this.getList();
                } else {
                  this.msgError(response.msg);
                }
              });
            } else {
              addWork(this.form).then(response => {
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
        const workIds = row.workId || this.ids;
        this.$confirm('是否确认删除工作经历编号为"' + workIds + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delWork(workIds);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
      },
      /** 导出按钮操作 */
      handleExport() {
        const queryParams = this.queryParams;
        this.$confirm('是否确认导出所有工作经历数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportWork(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
      }
    }
  };
</script>

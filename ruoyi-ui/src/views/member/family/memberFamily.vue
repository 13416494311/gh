<template>
  <div>
    <el-card shadow="always" style="margin-bottom: 30px;">
      <div slot="header" style="height: 25px">
        <span style="font-weight: bold;font-size: 16px">家庭成员</span>
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="small"
          @click="handleAdd"
          v-hasPermi="['member:family:add']"
          style="float: right;margin-top: -5px"
          v-show="!disabled"
        >新增
        </el-button>
      </div>
      <el-table :border="true"  :stripe="true"
                v-loading="loading" :data="familyList" @selection-change="handleSelectionChange">
        <el-table-column label="序号" align="center" type="index" />
        <el-table-column label="与本人关系" align="center" prop="relationWithMe" />
        <el-table-column label="姓名" align="center" prop="name" />
        <el-table-column label="工作单位" align="center" prop="companyName" />
        <el-table-column label="工作岗位" align="center" prop="post" />
        <el-table-column label="联系方式" align="center" prop="mobile" />
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
              v-hasPermi="['member:family:edit']"
            >修改</el-button>
            <el-button
              v-show="!disabled"
              size="small"
              type="text"
              icon="el-icon-delete"
              @click="handleDelete(scope.row)"
              v-hasPermi="['member:family:remove']"
            >删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <!-- 添加或修改家庭成员对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="80%" append-to-body
               @open="getHeight"  :close-on-click-modal="false">
      <el-form ref="form" :model="form" :rules="rules" :style="bodyStyle"  label-width="150px">

        <el-row>
          <el-col :span="12">
            <el-form-item label="与本人关系" prop="relationWithMe">
              <el-input :disabled="disabled1" v-model="form.relationWithMe" placeholder="请输入与本人关系" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="姓名" prop="name">
              <el-input :disabled="disabled1" v-model="form.name" placeholder="请输入姓名" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="工作单位" prop="companyName">
              <el-input :disabled="disabled1" v-model="form.companyName" placeholder="请输入工作单位" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="工作岗位" prop="post">
              <el-input :disabled="disabled1" v-model="form.post" placeholder="请输入工作岗位" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="联系方式" prop="mobile">
              <el-input :disabled="disabled1" v-model="form.mobile" placeholder="请输入联系方式" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer"  :style="{textAlign:'center'}">
        <el-button type="primary"v-if="!disabled1"  @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import { listFamily, getFamily, delFamily, addFamily, updateFamily, exportFamily } from "@/api/member/family";

  export default {
    name: "MemberFamily",
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
        // 家庭成员表格数据
        familyList: [],
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        // 查询参数
        queryParams: {
          memberUuid: undefined,
          name: undefined,
          relationWithMe: undefined,
          companyName: undefined,
          post: undefined,
          mobile: undefined,
        },
        // 表单参数
        form: {},
        // 表单校验
        rules: {
          name: [
            { required: true, message: "姓名不能为空", trigger: "blur" }
          ],
          relationWithMe: [
            { required: true, message: "与本人关系不能为空", trigger: "blur" }
          ],
          companyName: [
            { required: false, message: "工作单位不能为空", trigger: "blur" }
          ],
          post: [
            { required: false, message: "工作岗位不能为空", trigger: "blur" }
          ],
          mobile: [
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
      /** 查询家庭成员列表 */
      getList() {
        this.loading = true;
        this.queryParams.memberUuid = this.memberUuid;
        listFamily(this.queryParams).then(response => {
          this.familyList = response.rows;
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
          familyId: undefined,
          memberUuid: undefined,
          name: undefined,
          relationWithMe: undefined,
          companyName: undefined,
          post: undefined,
          mobile: undefined,
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
        this.ids = selection.map(item => item.familyId)
        this.single = selection.length!=1
        this.multiple = !selection.length
      },
      /** 新增按钮操作 */
      handleAdd() {
        this.reset();
        this.form.memberUuid = this.memberUuid;
        this.open = true;
        this.title = "添加家庭成员";
        this.disabled1 =false;
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.reset();
        const familyId = row.familyId || this.ids
        getFamily(familyId).then(response => {
          this.form = response.data;
          this.open = true;
          this.title = "修改家庭成员";
          this.disabled1 =false;
        });
      },
      handleSee(row) {
        this.reset();
        const familyId = row.familyId || this.ids
        getFamily(familyId).then(response => {
          this.form = response.data;
          this.open = true;
          this.title = "查看家庭成员";
          this.disabled1 =true;
        });
      },
      /** 提交按钮 */
      submitForm: function() {
        this.$refs["form"].validate(valid => {
          if (valid) {
            if (this.form.familyId != undefined) {
              updateFamily(this.form).then(response => {
                if (response.code === 200) {
                  this.msgSuccess("修改成功");
                  this.open = false;
                  this.getList();
                } else {
                  this.msgError(response.msg);
                }
              });
            } else {
              addFamily(this.form).then(response => {
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
        const familyIds = row.familyId || this.ids;
        this.$confirm('是否确认删除家庭成员编号为"' + familyIds + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delFamily(familyIds);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
      },
      /** 导出按钮操作 */
      handleExport() {
        const queryParams = this.queryParams;
        this.$confirm('是否确认导出所有家庭成员数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportFamily(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
      }
    }
  };
</script>

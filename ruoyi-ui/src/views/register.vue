<template>
  <div class="register">
    <div class="register-title" @click="toLogin()">
      <div class="h3">{{systemTitle}}</div>
      <el-button style="float: right;margin: 16px 50px" plain icon="el-icon-arrow-left">返 回</el-button>
    </div>
    <div class="register-div">
      <el-form ref="registerForm" :model="registerForm" :rules="registerRules" class="register-form">
        <el-form-item label="用户账号"  prop="userName">
          <el-input v-model="registerForm.userName"
                    type="text"
                    auto-complete="off"
                    placeholder="请输入真实姓名做为用户账号">
            <svg-icon slot="prefix" icon-class="user" class="el-input__icon input-icon" />
          </el-input>
        </el-form-item>
        <el-form-item label="输入密码"  prop="password">
          <el-input
            v-model="registerForm.password"
            type="password"
            auto-complete="off"
            placeholder="请输入密码"
          >
            <svg-icon slot="prefix" icon-class="password" class="el-input__icon input-icon" />
          </el-input>
        </el-form-item>
        <el-form-item label="确认密码"  prop="confirmPassword">
          <el-input
            v-model="registerForm.confirmPassword"
            type="password"
            auto-complete="off"
            placeholder="请输入确认密码"
          >
            <svg-icon slot="prefix" icon-class="password" class="el-input__icon input-icon" />
          </el-input>
        </el-form-item>
        <el-form-item label="联系方式"  prop="phonenumber">
          <el-input
            v-model="registerForm.phonenumber"
            type=""
            auto-complete="off"
            placeholder="请输入确认联系方式"
          >
            <svg-icon slot="prefix" icon-class="password" class="el-input__icon input-icon" />
          </el-input>
        </el-form-item>

        <el-form-item style="width:100%;text-align: center;margin-top: 50px">
          <el-button
            :loading="loading"
            size="medium"
            type="primary"
            style="width:50%;height: 45px;font-size: 16px"
            @click="handleRegister"
          >
            <span v-if="!loading">注 册</span>
            <span v-else>注 册 中...</span>
          </el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>

  import { encrypt, decrypt } from '@/utils/jsencrypt'
  import { checkPartyMemberUnique,registerPartyMember } from "@/api/system/register";


  export default {
    name: "Register",
    data() {
      const equalToPassword = (rule, value, callback) => {
        if (this.registerForm.password !== value) {
          callback(new Error("两次输入的密码不一致"));
        } else {
          callback();
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
      let checkMemberName = (rule, value, callback) => {
        if (!value) {
          return new Error("请输入用户账号");
        } else {
          let param={};
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
        registerForm: {
          userName: "",
          password: "",
          confirmPassword: "",
          phonenumber: "",
        },
        registerRules: {
          userName: [
            { required: true, trigger: "blur", message: "用户名不能为空" },
            {validator: checkMemberName, trigger: 'blur'}
          ],
          password: [
            { required: true, trigger: "blur", message: "密码不能为空" },
            { min: 6, max: 20, message: "长度在 6 到 20 个字符", trigger: "blur" }
          ],
          confirmPassword: [
            { required: true, trigger: "blur", message: "确认密码不能为空" },
            { required: true, validator: equalToPassword, trigger: "blur" }
          ],
          phonenumber:[
            { validator: checkMobile, trigger: "blur" }
          ]
        },
        loading: false,


      };
    },
    computed:{
      systemTitle() {
        return this.$store.state.settings.systemTitle
      },
    },
    watch: {

    },
    created() {

    },
    methods: {
      handleRegister() {
        let that = this;
        that.$refs.registerForm.validate(valid => {
          if (valid) {
            that.loading = true;
            let params = that.registerForm
            params.password =  encrypt(that.registerForm.password)
            params.confirmPassword = encrypt(that.registerForm.confirmPassword);

            registerPartyMember(params).then(response => {

              that.loading = false;
            })

          }
        });
      },
      toLogin(){
        this.$router.push({path: "/login", query: {}})
      },
    }
  };
</script>

<style rel="stylesheet/scss" lang="scss" scoped>

  .register{
    width: 100%;
    height: 100%
  }

  .register-title{
    width: 100%;
    height: 64px;
    text-align: left;
    background-color: #1890ff;
    .h3{
      display: inline;
      font-size: 20px;
      font-weight: bold;
      color: #fff;
      line-height:64px;
      letter-spacing: 3px;
      padding-left: 30px;
    }
  }

  .register-div {
    display: flex;
    justify-content: center;
    align-items: center;
    height: calc( 100% - 64px);
    width: 100%;
    /*background: url("../assets/image/login_bg.jpg") no-repeat;
    background-size: 100% calc( 100% - 64px);*/

    .register-form {
      border-radius: 6px;
      background: #fff;
      width: 60%;
      top:64px;
      position: absolute;
      padding: 50px 15%;
      border:1px solid #d0d6d6;

      /deep/ .el-form-item__label{
        font-weight: 400;
        line-height: 40px !important;
        font-size: 16px;
      }

      /deep/ .el-input {
        height: 45px;
        font-size: 14px;
        input {
          height: 45px;
        }
      }
      /deep/ .input-icon {
        height: 46px;
        width: 15px;
        margin-left: 3px;
      }
    }
  }

  .login-tip {
    font-size: 13px;
    text-align: center;
    color: #bfbfbf;
  }
  .login-code {
    width: 33%;
    height: 38px;
    float: right;
    img {
      cursor: pointer;
      vertical-align: middle;
    }
  }



</style>

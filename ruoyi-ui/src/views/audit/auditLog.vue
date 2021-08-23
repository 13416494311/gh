<template>
  <div>
    <el-card v-show="logList.length>0" shadow="always" style="margin-bottom: 30px;">
      <div slot="header" style="height: 25px">
        <span style="font-weight: bold;font-size: 16px">审批记录</span>
      </div>
      <el-table v-loading="loading" :data="logList">
        <el-table-column label="操作名称" align="center" prop="stepName"/>
        <el-table-column label="操作人" align="center" prop="sysUser.nickName"/>
        <el-table-column label="操作" align="center" prop="operResult"/>
        <el-table-column label="操作时间" align="center" prop="operTime" width="180">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.operTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
          </template>
        </el-table-column>
        <el-table-column label="备注" align="center" prop="operReason"/>
      </el-table>

    </el-card>
  </div>
</template>

<script>
  import {addLog, delLog, exportLog, getLog, listLog, updateLog} from "@/api/sys/log";
  export default {
    name:"AuditLog",
    data(){
      return{
        logList: [],
        uuid:undefined,
        loading: true,
      }
    },
    methods: {
      init(uuid){
        this.uuid = uuid ;
        this.getLogList();
      },
      getLogList() {
        this.loading = true;
        listLog({"uuid": this.uuid}).then(response => {
          this.logList = response.rows;
          this.loading = false;
        });
      },
    }
  }
</script>

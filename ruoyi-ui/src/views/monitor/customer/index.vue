<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="客户编码" prop="postCode">
        <el-input
          v-model="queryParams.postCode"
          placeholder="请输入客户编码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="客户姓名" prop="postName">
        <el-input
          v-model="queryParams.postName"
          placeholder="请输入客户姓名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
<!--      <el-form-item label="状态" prop="status">-->
<!--        <el-select v-model="queryParams.status" placeholder="商家状态" clearable>-->
<!--          <el-option-->
<!--            v-for="dict in dict.type.sys_normal_disable"-->
<!--            :key="dict.value"-->
<!--            :label="dict.label"-->
<!--            :value="dict.value"-->
<!--          />-->
<!--        </el-select>-->
<!--      </el-form-item>-->
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:post:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:post:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:post:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:post:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="postList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="客户编号" align="center" prop="postId" />
      <el-table-column label="客户姓名" align="center" prop="postCode" />
      <el-table-column label="客户电话" align="center" prop="postName" />
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-add"
            @click="addorder()"
            v-hasPermi="['system:post:edit']"
          >添加订单</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleView(scope.row)"
            v-hasPermi="['system:post:edit']"
          >查看</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:post:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:post:remove']"
          >删除</el-button>
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

    <!-- 添加或修改岗位对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="商家名称" prop="postCode">
          <el-input v-model="form.postCode" placeholder="请输入商家名称" />
        </el-form-item>
        <el-form-item label="商家地址" prop="postName">
          <el-input v-model="form.postName" placeholder="请输入商家地址" />
        </el-form-item>
        <!--        <el-form-item label="商家地址" prop="postSort">-->
        <!--          <el-input v-model="form.postSort" placeholder="请输入商家地址" />-->
        <!--        </el-form-item>-->
        <el-form-item label="岗位状态" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio
              v-for="dict in dict.type.sys_normal_disable"
              :key="dict.value"
              :label="dict.value"
            >{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <el-dialog :title="title" :visible.sync="openn" width="500px" append-to-body>
      <el-form ref="form" :model="form"  label-width="80px">
        <el-form-item label="商家名称" prop="postCode">
          <span>{{form.postCode}}</span>
        </el-form-item>
        <el-form-item label="商家地址" prop="postName">
          <span>{{ form.postName }}</span>
        </el-form-item>
        <!--        <el-form-item label="商家地址" prop="postSort">-->
        <!--          <el-input v-model="form.postSort" placeholder="请输入商家地址" />-->
        <!--        </el-form-item>-->
        <el-form-item label="岗位状态" prop="status">
          <span>{{form.statusText}}</span>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <span>{{ form.remark }}</span>
        </el-form-item>
      </el-form>
      <!--      <div slot="footer" class="dialog-footer">-->
      <!--        <el-button type="primary" @click="submitForm">确 定</el-button>-->
      <!--        <el-button @click="cancel">取 消</el-button>-->
      <!--      </div>-->
    </el-dialog>
    <el-dialog :title="title" :visible.sync="opp" width="1300px" height="2000px" >
      <el-row :gutter="20" >
        <el-col :span="5" :xs="24">
          <div class="head-container">
            <el-input
              v-model="objectqueryParams.userName"
              placeholder="请输入用户名称"
              clearable
              size="small"
              prefix-icon="el-icon-search"
              style="margin-bottom: 20px"
            />
          </div>
          <div class="head-container">
            <el-scrollbar style="height: 400px;">
              <el-table v-loading="loading" :data="filteredUserList" @selection-change="handleSelectionChange" :show-header="false">
                <el-table-column label="用户名称" align="center" key="userName" prop="userName" :show-overflow-tooltip="true" />
                <el-table-column
                  label="操作"
                  align="center"
                  width="100"
                  class-name="small-padding fixed-width"
                >
                  <template slot-scope="scope" v-if="scope.row.userId !== 1">
                    <el-button
                      size="mini"
                      type="text"
                      icon="el-icon-edit"
                      @click="orderadd(scope.row)"
                    >添加</el-button>

                  </template>
                </el-table-column>
              </el-table>
            </el-scrollbar>
            <!--              :expand-on-click-node="false"-->
            <!--              :filter-node-method="filterNode"-->
            <!--              ref="tree"-->
            <!--              node-key="id"-->
            <!--              default-expand-all-->
            <!--              highlight-current-->
            <!--              @node-click="handleNodeClick"-->
          </div>
        </el-col>
        <el-col :span="18" :xs="24">
          <el-form ref="form" :model="form"  label-width="80px">
            <el-form-item label="商家名称" prop="postCode" style="display: inline-block; margin-right: 50px;">
              <span>{{form.userName}}</span>
            </el-form-item>
            <el-form-item label="商家地址" prop="postName" style="display: inline-block; margin-right: 50px;">
              <span>{{ form.phonenumber }}</span>
            </el-form-item>
            <!--        <el-form-item label="商家地址" prop="postSort">-->
            <!--          <el-input v-model="form.postSort" placeholder="请输入商家地址" />-->
            <!--        </el-form-item>-->
            <el-form-item label="岗位状态" prop="status" style="display: inline-block; margin-right: 50px;">
              <span>{{form.status}}</span>
            </el-form-item>
            <el-form-item label="备注" prop="remark" style="display: inline-block; margin-right: 50px;">
              <span>{{ form.remark }}</span>
            </el-form-item>
          </el-form>
          <el-table v-loading="loading" :data="selectedItems">
            <!--            <el-table-column label="用户名称" align="center" key="userName" prop="userName" :show-overflow-tooltip="true" />-->
            <el-table-column label="商品编号" align="center" key="userId" prop="userId" />
            <el-table-column label="商品名称" align="center" key="userName" prop="userName" v-if="columns[1].visible" :show-overflow-tooltip="true" />
            <!--            <el-table-column label="商品昵称" align="center" key="nickName" prop="nickName" v-if="columns[2].visible" :show-overflow-tooltip="true" />-->
            <el-table-column label="分区" align="center" key="deptName" prop="dept.deptName" v-if="columns[3].visible" :show-overflow-tooltip="true" />
            <el-table-column label="手机号码" align="center" key="phonenumber" prop="phonenumber" v-if="columns[4].visible" width="120" />
            <el-table-column label="数量" align="center" width="120" prop="quantity">
              <template slot-scope="scope">
                <div style="display: flex; align-items: center; justify-content: center;">
                  <el-button size="mini" type="text" icon="el-icon-minus" @click="decreaseQuantity(scope.row)"></el-button>
                  <el-input-number v-model="quantity" :min="1" :max="100"  /><!-- 这个后端加属性**-->
                  <!--                  <span>{{quantity}}</span>-->
                  <el-button size="mini" type="text" icon="el-icon-plus" @click="increaseQuantity(scope.row)"></el-button>
                </div>
              </template>
            </el-table-column>
            <!-- 右侧表格的其他列 -->
          </el-table>
        </el-col>

      </el-row>

    </el-dialog>
  </div>
</template>

<script>
import { listSupplier, getPost, delPost, addPost, updatePost } from "@/api/monitor/customer";
import { listUser, getUser, delUser, addUser, updateUser, resetUserPwd, changeUserStatus, deptTreeSelect } from "@/api/monitor/object";
import post from "@/views/system/post/index.vue";

export default {
  name: "Post",
  dicts: ['sys_normal_disable'],
  data() {
    return {
      selectedUserNames: [],
      selectedItems: [],
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 岗位表格数据
      postList: [],
      userList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      openn: false,
      opn:false,
      opp:false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        postCode: undefined,
        postName: undefined,
        status: undefined
      },
      objectqueryParams: {
        pageNum: 1,
        pageSize: 10,
        userName: undefined,
        phonenumber: undefined,
        status: undefined,
        deptId: undefined
      },
      columns: [
        { key: 0, label: `用户编号`, visible: true },
        { key: 1, label: `用户名称`, visible: true },
        { key: 2, label: `用户昵称`, visible: true },
        { key: 3, label: `部门`, visible: true },
        { key: 4, label: `手机号码`, visible: true },
        { key: 5, label: `状态`, visible: true },
        { key: 6, label: `创建时间`, visible: true }
      ],
      dateRange: [],
      // 表单参数
      form: {},
      formm:{},
      // 表单校验
      rules: {
        postName: [
          { required: true, message: "岗位名称不能为空", trigger: "blur" }
        ],
        postCode: [
          { required: true, message: "岗位编码不能为空", trigger: "blur" }
        ],
        postSort: [
          { required: true, message: "岗位顺序不能为空", trigger: "blur" }
        ]
      }
    };
  },
  computed: {
    post() {
      return post
    },
    // 根据搜索关键词过滤用户数据
    filteredUserList() {
      const keyword = this.objectqueryParams.userName; // 转换为小写进行不区分大小写搜索
      return this.userList.filter(user => user.userName.includes(keyword));
      // return this.userList;
    },
  },
  created() {
    this.getList();
  },
  methods: {
    increaseQuantity(row) {
      row.quantity++;
    },
    // 减少数量
    decreaseQuantity(row) {
      if (row.quantity > 1) {
        row.quantity--;
      }
    },
    orderadd(row) {
      this.selectedItems.push(row);
    },
    /** 查询岗位列表 */
    getList() {
      this.loading = true;
      listUser(this.addDateRange(this.objectqueryParams, this.dateRange)).then(response => {
          this.userList = response.rows;
          // this.total = response.total;
          // this.loading = false;
        }
      );
      listSupplier(this.queryParams).then(response => {
        this.postList = response.rows;
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
        postId: undefined,
        postCode: undefined,
        postName: undefined,
        postSort: 0,
        status: "0",
        remark: undefined
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
      this.ids = selection.map(item => item.postId)
      this.single = selection.length!=1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加供货商";
    },
    /** 添加订单 **/
    addorder(){
      this.opp = true;

    },
    handleView(row) {
      // this.reset();
      this.form = {
        postCode: row.postCode,
        postName: row.postName,
        status: row.status,
        remark: row.remark
      };
      this.form.statusText = this.dict.type.sys_normal_disable.find(item => item.value === row.status)?.label || '';
      this.openn = true;
      this.title = "查看信息"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const postId = row.postId || this.ids
      getPost(postId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改供货商";
      });
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        // if (valid) {
        if (this.form.postId != undefined) {
          updatePost(this.form).then(response => {
            this.$modal.msgSuccess("修改成功");
            this.open = false;
            this.getList();
          });
        } else {
          addPost(this.form).then(response => {
            this.$modal.msgSuccess("新增成功");
            this.open = false;
            this.getList();
          });
        }
        // }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const postIds = row.postId || this.ids;
      this.$modal.confirm('是否确认删除岗位编号为"' + postIds + '"的数据项？').then(function() {
        return delPost(postIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/post/export', {
        ...this.queryParams
      }, `post_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>

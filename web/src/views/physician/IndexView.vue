<template>
  <ElForm :model="filterModel" :rules="filterRules" @submit.prevent="fetch" inline>
    <ElFormItem prop="keywordsName" label="医师查询" class="min-width-192" >
      <ElInput v-model="filterModel.keywords" :prefix-icon="Search" placeholder="关键字 姓名or手机号" clearable />
    </ElFormItem>
    <ElFormItem prop="keywordsofficeId" label="选择科室" class="min-width-192" >
      <ElSelect v-model="filterModel.officeId" placeholder="科室" clearable filterable>
          <ElOption
              label="全部"
              :value="''"
          />
          <ElOption
              v-for="office in offices"
              :key="office.id"
              :value="office.id"
              :label="`${office.name}`"
          />
      </ElSelect>
    </ElFormItem>
    <ElFormItem>
      <ElButton native-type="submit" type="primary" class="shaixuan">筛选</ElButton>
    </ElFormItem>
  </ElForm>
  <ElTable :data="physicians" style="width: 100%" stripe border :show-header="true">
    <!-- <ElTableColumn prop="id" label="#" /> -->
    <ElTableColumn prop="username" label="账号"/>
    <!-- <ElTableColumn prop="password" label="密码" /> -->
    <ElTableColumn prop="name" label="医生姓名" align="center" />
    <ElTableColumn prop="age" label="年龄" align="center" sortable />
    <ElTableColumn prop="gender" label="性别" align="center" />
    <ElTableColumn label="科室诊断">
        <template #default="{ row }">
            <span>{{ row.office?.name || '未设置' }}</span>
        </template>
    </ElTableColumn>
    <ElTableColumn prop="phone" label="手机号" align="center" width="120" />
    <ElTableColumn prop="serve" label="是否在职" align="center">
      <template #default="{ row }">
        <ElTag
          :type="row.serve === '在职' ? 'success' : 'danger'"
          effect="dark"
        >
          {{ row.serve }}
        </ElTag>
      </template>
    </ElTableColumn>
    <ElTableColumn label="设置" width="200" header-align="center">
      <template #default = "{ row }">
        <ElButton
          type="primary"
          @click.stop="router.push({ name: 'physician_update' , params: { id: row.id} })"
          style="margin-left: 1rem;"
          >修改</ElButton
        >
        <ElPopconfirm
          title="确认移除该角色吗？"
          confirm-button-text="确定"
          cancel-button-text="取消"
          @confirm="remove(row)"
        >
          <template #reference>
            <ElButton type="danger">移除</ElButton>
          </template>
        </ElPopconfirm>
      </template>
    </ElTableColumn>
  </ElTable>
  <ElPagination
  layout="prev, pager, next, jumper, sizes, ->, total"
  :page-sizes="[1, 5, 10, 20, 50, 100]"
  v-model:current-page="pagination.currentPage"
  v-model:page-size="pagination.pageSize"
  :total="pagination.total"
  background
  />
</template>

<script setup>
import { reactive, ref, watch } from 'vue'
import { useRouter } from 'vue-router'
import {
  ElForm,
  ElFormItem,
  ElInput,
  ElTable,
  ElTableColumn,
  ElPopconfirm,
  ElButton,
  ElPagination,
  ElMessage,
  ElTag,
  ElSelect,
  ElOption,
} from 'element-plus'
import request from '@/utils/request'
import { Search } from '@element-plus/icons-vue'

const router = useRouter()

const physicians = ref([])

const filterModel = reactive({
  keywords: '',
  officeId: '',
})

const filterRules = reactive({
  keywordsName: [{ min: 1, max:16, message: '关键字仅限1~16个字符。', trigger: 'change'}],
  keywordsOffice: [{ min: 1, max:16, message: '关键字仅限1~16个字符。', trigger: 'change'}]
})

const pagination = reactive({
  currentPage: 1,
  pageSize: 5,
  total: 0,
})

const fetch = () => {
  // `/role?page=${pagination.currentPage}&pageSize=${pagination.pageSize}`
  /*
      params: {
        page: pagination.currentPage,
        pageSize: pagination.pageSize,
      },
  */
  let params = new URLSearchParams()
  params.append('page', pagination.currentPage)
  params.append('pageSize', pagination.pageSize)

  if (filterModel.keywords.length > 0) {
      params.append('keywords', filterModel.keywords)
  }

  if (filterModel.officeId !== '' && filterModel.officeId !== undefined && filterModel.officeId !== null) {
    params.append('officeId', filterModel.officeId)
  }


  request.get('/physician', { params }).then((response) => {
    if (response.data.status === true) {
      // physicians.value = response.data.payload.physicians
      // pagination.currentPage = response.data.payload.page
      // pagination.pageSize = response.data.payload.pageSize
      // pagination.total = response.data.payload.total
      physicians.value = response.data.payload.physicians
      Object.assign(pagination, response.data.payload.pagination)
    }
  })
}

watch(
  () => [pagination.currentPage, pagination.pageSize],
  () => {
    fetch()
  },
  { immediate: true },
)

const remove = (row) => {
    request.post('/physician/remove', row).then((response) => {
        if (response.data.status === true) {
            fetch()
            ElMessage.success('移除成功！')
        } else {
            ElMessage.error('移除失败！')
        }
    })
}

const offices = ref([])
const fetchOffices = () => {
    request.get('/office/all').then((response) => {
        if(response.data.status === true) {
            offices.value = response.data.payload.office
        }
    })
}
fetchOffices()
</script>

<style scoped>
.min-width-192{
  min-width: 12rem;
}

.shaixuan{
  color:rgba(255, 255, 255, 1);
  --el-color-primary: #91caff;
  --el-color-primary-light-3: #b5f5ec;
}

/* .el-button--primary{
  color:rgba(255, 255, 255, 1);
  --el-color-primary: #0da5aa;
  --el-color-primary-light-5: #37d4cf;
} */

.el-pagination{
  --el-color-primary: #91caff;
}
</style>

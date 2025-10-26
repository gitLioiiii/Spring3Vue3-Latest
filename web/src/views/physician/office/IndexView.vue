<template>
  <ElTable :data="offices" style="width: 80%" stripe border :show-header="true">
    <ElTableColumn prop="id" label="#" />
    <ElTableColumn prop="name" label="科室" align="center" />
    <ElTableColumn prop="description" label="描述" align="center" />
    <ElTableColumn label="设置" width="200" header-align="center">
      <template #default = "{ row }">
        <ElButton
          type="primary"
          @click.stop="router.push({ name: 'office_update' , params: { id: row.id} })"
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
</template>

<script setup>
import { reactive, ref, watch } from 'vue'
import { useRouter } from 'vue-router'
import {
  ElTable,
  ElTableColumn,
  ElPopconfirm,
  ElButton,
  ElMessage,
} from 'element-plus'
import request from '@/utils/request'

const router = useRouter()

const offices = ref([])

const pagination = reactive({
  currentPage: 1,
  pageSize: 5,
  total: 0,
})

const fetch = () => {
  request.get('/office/all').then((response) => {
    if (response.data.status === true) {
      offices.value = response.data.payload.office
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
    request.post('/office/remove', row).then((response) => {
        if (response.data.status === true) {
            fetch()
            ElMessage.success('移除成功！')
        } else {
            ElMessage.error('移除失败！')
        }
    })
}
</script>

<style>

.el-table__inner-wrapper{
  color: #4e5969;
}

</style>

<template>
  <ElTable :data="rows" style="width: 100%" stripe border :show-header="true">
    <ElTableColumn prop="id" label="#" width="100" />
    <ElTableColumn prop="name" label="医生姓名" align="center" />
    <ElTableColumn label="职位" align="left" width="400">
      <template #default="{ row }">
          <ElTag v-for="(pos, frist) in row.positions" :type="frist === 0 ? 'danger' : 'success'"
          :key="'主要职位'+pos.id" style="margin-left:0.1rem; margin-bottom: 0.2rem;">
            {{ pos.name }}
          </ElTag>
      </template>
    </ElTableColumn>
    <ElTableColumn prop="gender" label="性别" align="center" />
    <ElTableColumn prop="age" label="年龄" align="center" sortable />
    <!-- <ElTableColumn prop="position.description" label="描述" align="center" /> -->
    <ElTableColumn label="设置" width="180" header-align="center" align="center">
      <template #default="{ row }" >
          <ElButton type="warning" class="shezhi" @click.stop="openPositionDialog(row)">设置职位</ElButton>
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
  
  <ElDialog
        v-model="dialogVisible"
        :close-on-click-modal="false"
        @opened="fetchPhysicianPositions"
        title="医师职位设置"
        destroy-on-close
    >
      <template #default>
          <div>
              <div style="max-height: 400px; overflow-y: auto;">
                  <ElTree
                      ref="positionTree"
                      :data="positionOptions"
                      node-key="id"
                      :props="{ label: (data) => `${data.name}【${data.description}】` }"
                      show-checkbox
                      check-strictly
                      :default-expand-all="true"
                      @check-change="onPositionCheckChange"
                  />
              </div>
              <div style="margin-bottom: 10px;">
                  <ElButton size="small" @click="selectAllPositions">全选</ElButton>
                  <ElButton size="small" @click="clearAllPositions">清空</ElButton>
              </div>
          </div>
      </template>
      <template #footer>
          <ElButton type="default" @click.stop="dialogVisible = false">取消</ElButton>
          <ElButton type="primary" @click.stop="savePosition">保存</ElButton>
      </template>
  </ElDialog>
</template>

<script setup>
import { reactive, ref, watch } from 'vue'
// import { useRouter } from 'vue-router'
import {
  ElTable,
  ElTableColumn,
  ElButton,
  ElDialog,
  ElMessage,
  ElPagination,
  ElTree,
  ElTag,
  ElNotification
} from 'element-plus'
import request from '@/utils/request'

// const router = useRouter()
const rows = ref([])
const positionOptions = ref([])

const pagination = reactive({
  currentPage: 1,
  pageSize: 5,
  total: 0,
})

const fetch = () => {
  let params = new URLSearchParams()
  params.append('page', pagination.currentPage)
  params.append('pageSize', pagination.pageSize)

  request.get('/assign/view', { params }).then((response) => {
    if (response.data.status === true) {
      rows.value = response.data.payload.position
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

/****模态框****/
const selectedPhysician = ref(null)
const positionTree = ref(null)
const lastCheckedCount = ref(0)

const fetchPositions = () => {
    request.get('/position/all').then((response) => {
        if (response.data.status === true) {
            positionOptions.value = response.data.payload.position
        }
    })
}
fetchPositions()

const fetchPhysicianPositions = () => {
    request.get(`/physician/${selectedPhysician.value.id}/positions`).then((response) => {
        if (response.data.status === true) {
            positionTree.value.setCheckedKeys(
                response.data.payload.positions.map((item) => item.id),
                false,
            )
            // 初始化已勾选叶子数量，避免重复通知
            lastCheckedCount.value = positionTree.value.getCheckedKeys(true).length
        }
    })
}

const dialogVisible = ref(false)

const openPositionDialog = (role) => {
    selectedPhysician.value = role
    dialogVisible.value = true
}

const selectAllPositions = () => {
    const allKeys = positionOptions.value.map(item => item.id)
    positionTree.value.setCheckedKeys(allKeys, false)
}

const onPositionCheckChange = (data, checked) => {
    const checkedCount = positionTree.value.getCheckedKeys(true).length
    if (checked && checkedCount === 4 && lastCheckedCount.value !== 4) {
        notice_four()
    }
    lastCheckedCount.value = checkedCount
}

const notice_four = () => {
    ElNotification({
      type: 'success',
      title: '建议',
      message: '职位最好不要超过四个以上！',
      offset: 100,
    })
}

const clearAllPositions = () => {
    positionTree.value.setCheckedKeys([], false)
}

const savePosition = () => {
    let formData = new FormData()
    formData.append(
        'positionsId',
        positionTree.value.getCheckedNodes(false, true).map((item) => item.id),
    )

    request
        .post(`/physician/${selectedPhysician.value.id}/positions`, formData)
        .then((response) => {
            if (response.data.status === true) {
                dialogVisible.value = false
                ElMessage.success('保存成功！')
                // 保存成功后重新加载数据
                fetch()
            } else {
                ElMessage.error('保存失败！')
            }
        })
        .catch(() => {
            ElMessage.error('保存失败！')
        })
}
</script>

<style lang="scss">

.el-table__inner-wrapper{
  color: #4e5969;
}

.shezhi{
    color:rgba(255, 255, 255, 1);
  --el-color-warning: #91caff;
  --el-color-warning-light-3: #b5f5ec;
}

.el-pagination{
  --el-color-primary: #91caff;
}

.el-dialog {
  .el-tree {
    // 根层级：选中项中的第一个节点
    > .el-tree-node:nth-child(1 of .el-tree-node:has(.el-checkbox.is-checked)) {
      .el-checkbox.is-checked .el-checkbox__inner {
        background-color: #f56c6c;
        border-color: #f56c6c;
      }
    }

    // 子层级：选中项中的第一个节点
    .el-tree-node__children {
      > .el-tree-node:nth-child(1 of .el-tree-node:has(.el-checkbox.is-checked)) {
        .el-checkbox.is-checked .el-checkbox__inner {
          background-color: #f56c6c;
          border-color: #f56c6c;
        }
      }
    }
  }
}
</style>

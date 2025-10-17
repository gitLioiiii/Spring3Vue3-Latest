<template>
  <ElTable :data="users" style="width: 80%" stripe border :show-header="true">
      <ElTableColumn prop="id" label="#" />
      <ElTableColumn prop="avatar" label="头像">
        <template #default="{ row }">
          <!-- <img v-if="row.avatar" :src="buildURL(row.avatar)" :alt="row.username" :title="row.username"
           style="width: 50px; height: 50px; object-fit: cover;" />
          <span v-else>无头像</span> -->
          <ElImage
            v-if="row.avatar"
            style="width: 50px; height: 50px"
            :src="buildURL(row.avatar)"
            :preview-src-list="srcList"
            fit="cover"
            show-progress
          />
          <span v-else>
            <ElImage
              style="width: 50px; height: 50px"
              :src="noImage"
              fit="cover"
            />
          </span>
        </template>
      </ElTableColumn>
      <ElTableColumn prop="username" label="账号"/>
      <ElTableColumn prop="password" label="密码">
        <template #default>
          <span>未对密码序列化</span>
        </template>
      </ElTableColumn>
      <ElTableColumn label="设置" width="200" header-align="center">
        <template #default = "{ row }">
          <ElButton
            type="primary"
            @click="openEdit(row)"
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
    
<!-- z-index放高点覆盖元素 -->
    <ElDrawer
      v-model="drawer"
      title="编辑用户"
      :with-header="true"
      :teleported="true"
      :z-index="3000"
      direction="rtl"
      size="30%"
    >
      <ElForm ref="form" :model="model" label-width="80">
        <ElFormItem label="账号">
          <ElInput v-model="model.username" disabled />
        </ElFormItem>
        <ElFormItem label="密码">
          <ElInput v-model="model.password" type="password" placeholder="修改用户密码" />
        </ElFormItem>
        <ElFormItem label="头像">
          <FileUploader v-model="model.avatarFiles" :limit="1">
            <template #default="{ file }">
              <ElImage :src="buildURL(file.filename)" style="width: 80px; height: 80px" fit="cover" />
            </template>
            <template #trigger>
              <ElButton type="primary" size="small">上传头像</ElButton>
            </template>
          </FileUploader>
        </ElFormItem>
      </ElForm>
      <template #footer>
        <ElButton type="default" @click.stop="closeDrawer">取消</ElButton>
        <ElButton type="primary" @click.stop="save">保存</ElButton>
      </template>
    </ElDrawer>
</template>

<script setup>
import { ref, reactive } from 'vue'
import {
  ElTable,
  ElTableColumn,
  ElPopconfirm,
  ElButton,
  ElMessage,
  ElImage,
  ElDrawer,
  ElForm,
  ElFormItem,
  ElInput
} from 'element-plus'
import request from '@/utils/request'
import { buildURL } from '@/utils/helper'
import noImage from '@/assets/img/图片未上传.png'
import FileUploader from '@/components/FileUploader.vue'


const users = ref([])
const srcList = ref([])

const fetch = () => {
  let params = new URLSearchParams()

  request.get('/user/', { params }).then((response) => {
    if (response.data.status === true) {
      users.value = response.data.payload.user
      srcList.value = users.value
        .filter(user => user.avatar)// 筛选出有头像的用户
        .map(user => buildURL(user.avatar))// 将头像路径转换为完整URL
    }
  })
}

// 页面加载时获取数据
fetch()

const remove = (row) => {
    request.post(`/user/remove/${row.id}`).then((response) => {
        if (response.data === true) {
            fetch()
            ElMessage.success('移除成功！')
        } else {
            ElMessage.error('移除失败！')
        }
    })
}

// 抽屉
const drawer = ref(false)
const form = ref(null)
const model = reactive({
  id: null,
  username: '',
  password: '',
  name: null,
  avatar: null,
  avatarFiles: [],
})

const openEdit = (row) => {
  model.id = row.id
  model.username = row.username
  model.password = ''
  model.name = row.name || null
  model.avatar = row.avatar || null
  model.avatarFiles = row.avatar ? [{ filename: row.avatar }] : []
  drawer.value = true
}

const closeDrawer = () => {
  drawer.value = false
}

const save = () => {
  const payload = {
    name: model.name,
    password: model.password && model.password.length > 0 ? model.password : null,
    avatar: model.avatarFiles.length > 0 ? model.avatarFiles[0].filename : null,
  }
  request.post(`/user/update/${model.id}`, payload).then((response) => {
    if (response.status === 200) {
      ElMessage.success('保存成功！')
      closeDrawer()
      fetch()
    } else {
      ElMessage.error('保存失败！')
    }
  }).catch(() => ElMessage.error('保存失败！'))
}

</script>

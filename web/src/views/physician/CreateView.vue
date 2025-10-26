<template>
  <ElForm
      ref="form"
      :model="model"
      @submit="save"
      :rules="rules"
      label-width="60"
      label-position="left"
      >
      <ElFormItem prop="username" label="账号">
        <ElInput v-model="model.username" type="text" placeholder="账号" />
      </ElFormItem>
      <ElFormItem prop="password" label="密码">
        <ElInput v-model="model.password" type="password" placeholder="密码" />
      </ElFormItem>
      <ElFormItem prop="name" label="姓名">
        <ElInput v-model="model.name" type="text" placeholder="姓名" />
      </ElFormItem>
      <ElFormItem prop="age" label="年龄">
        <ElInput v-model="model.age" type="number" placeholder="年龄" />
      </ElFormItem>
      <ElFormItem prop="gender" label="性别">
        <ElSelect v-model="model.gender" placeholder="请选择">
          <ElOption label="男" value="男" />
          <ElOption label="女" value="女" />
        </ElSelect>
      </ElFormItem>
      <ElFormItem prop="officeId" label="科室">
        <ElSelect v-model="model.officeId" placeholder="科室">
            <ElOption
                v-for="office in offices"
                :key="office.id"
                :value="office.id"
                :label="`${office.name}`"
            />
        </ElSelect>
      </ElFormItem>
      <ElFormItem prop="phone" label="手机号">
        <ElInput v-model="model.phone" type="text" placeholder="手机号" />
      </ElFormItem>
      <ElFormItem prop="serve" label="是否在职">
        <ElSelect v-model="model.serve" placeholder="请选择">
          <ElOption label="在职" value="在职" />
          <ElOption label="离职" value="离职" />
        </ElSelect>
      </ElFormItem>
      <ElFormItem label="">
        <ElButton type="primary" native-type="submit">保存</ElButton>
      </ElFormItem>
  </ElForm>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { ElForm, ElFormItem, ElInput, ElButton, ElMessage, ElSelect, ElOption } from 'element-plus'
import request from '@/utils/request'

const router = useRouter()

const offices = ref([])
const fetchOffices = () => {
    request.get('/office/all').then((response) => {
        if(response.data.status === true) {
            offices.value = response.data.payload.office
        }
    })
}
fetchOffices()

const form = ref(null)

const model = reactive({
    username: '',
    password: '',
    name: '',
    age: '',
    gender: '',
    officeId: '',
    phone: '',
    serve: '',
})

const rules = reactive({
  name: [
    { required: true, message: '请输入医师姓名。', trigger: 'blur' },
    { min: 2, max: 24, message: '医师姓名仅限2~24个字符。', trigger: 'change'},
  ],
  phone: [
    { required: true, message: '请输入手机号。', trigger: 'blur' },
    { min: 11, max: 11, message: '手机号必须为11位。', trigger: 'change'},
  ],
  officeId: [{ required: true, message: '请选择科室。', trigger: 'blur' }],
  serve: [
    { required: true, message: '请选择是否在职。', trigger: 'change' },
  ],
})

const save = (e) => {
  e.preventDefault()
  form.value
    .validate()
    .then((result) => {
        if (result === true) {
          request.post('/physician/create', { ...model }).then((response) => {
              if (response.data.status === true) {
                ElMessage.success('保存成功！')
                router.push({ name:'physician_index' })
              } else {
                ElMessage.error('保存失败!')
              }
          })
        }
    })
    .catch(() => {
          ElMessage.error('保存失败！')
    })
}

// watch(
//   model,
//   (v) => {
//     console.log(v)
//   },
//   { deep: true },
// )
</script>

<style scoped>
:deep(.el-form-item) {
  margin-bottom: 22px;
}
:deep(.el-form-item__label):before {
  display: none !important;
}
</style>

<template>
  <article class="base-article">
    <ElCard style="max-width: 400px">
    <ElForm
      ref="form"
      :model="model"
      @submit="save"
      :rules="rules"
      label-width="80"
      label-position="left"
    >
      <h2>注册新账户</h2>
      <p>欢迎注册，请输入您的信息</p>
      <ElFormItem prop="username" label="用户名">
        <ElInput v-model="model.username" type="text" placeholder="请输入用户名">
          <template v-slot:suffix>
            <img src="@/assets/icons/user.png" alt="用户图标" style="width: 16px; height: 16px;" />
          </template>
        </ElInput>
      </ElFormItem>
      <ElFormItem prop="password" label="密码">
        <ElInput v-model="model.password" type="password" placeholder="请输入密码">
          <template v-slot:suffix>
            <img src="@/assets/icons/password.png" alt="密码图标" style="width: 16px; height: 16px;" />
          </template>
        </ElInput>
      </ElFormItem>
      <ElFormItem>
        <ElButton type="primary" native-type="submit">注册</ElButton>
      </ElFormItem>
      <nav style="text-align: center; margin-top: 16px;">
        <span>已有账户？</span>
        <RouterLink :to="{name: 'login'}">登录</RouterLink>
      </nav>
    </ElForm>
    </ElCard>
  </article>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { ElCard,ElForm,ElFormItem,ElInput,ElButton,ElMessage } from 'element-plus'
import { useRouter,RouterLink } from 'vue-router'
import axios from 'axios'

const form = ref(null)
const router = useRouter()
const model = reactive({
  username: '',
  password: '',
})

const rules= reactive({
  username: [
    { required:true, message: '请输入用户名。', trigger: 'blur'},
    { min: 2, max:18, message: '名称仅限2~18个字符', trigger: 'change' },
  ],
  password: [
    { required:true, message:'请输入密码。', trigger: 'blur'},
    { min: 2, max:32, message: '名称仅限2~18个字符', trigger: 'change' },
  ],
})

const save = (e) => {
  e.preventDefault()
  form.value
    .validate()
    .then((result) => {
      if (result === true) {
        axios.post('http://localhost:8080/user/register', { ...model }).then((response) => {
          if (response.data.status === true) {
            ElMessage.success('注册成功！')
            router.push({ name: 'login' })
          } else {
            ElMessage.error('注册失败！')
          }
        })
      }
    })
    .catch(() => {
      ElMessage.error('注册失败！')
    })
}
</script>

<style scoped>
.base-article {
  height: 100vh;
  background: url('@/assets/img/login.jpg') center center no-repeat;
  background-size: cover;
  display: flex;
  align-items: center;
  justify-content: center;
  margin: 0rem 0rem 0rem 0rem;
}

.el-card {
  background: rgba(255, 255, 255, 0.7);
  box-shadow: 0 4px 24px rgba(0, 0, 0, 0.18);
}

.sapn {
  color: #666;
}

.RouterLink {
  color: #409eff;
  text-decoration: none;
  margin-left: 4px;
}
</style>

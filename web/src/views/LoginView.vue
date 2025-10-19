<template>
  <article class="base-article">

    <ElCard style="max-width: 400px">
    <!-- 没有 .prevent 时，点击“登录”或按回车会触发原生提交→页面刷新→状态清空→路由守卫检测到未登录→跳回登录页。加上它就能避免这个循环。 -->
    <ElForm
      ref="form"
      :model="model"
      @submit.prevent="login"
      :rules="rules"
      label-width="80"
      label-position="left"
    >
      <h2>登录您的账户</h2>
      <p>欢迎登录，请输入您的信息</p>
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
        <ElButton type="primary" native-type="submit">登录</ElButton>
      </ElFormItem>
      <nav style="text-align: center; margin-top: 16px;">
        <span>还没有账户？</span>
        <RouterLink :to="{name: 'register'}">注册</RouterLink>
      </nav>
    </ElForm>
    </ElCard>
  </article>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { ElCard,ElForm,ElFormItem,ElInput,ElButton,ElMessage } from 'element-plus'
import { useRouter,RouterLink } from 'vue-router'
// import axios from 'axios'
import request from '@/utils/request'
import { useUserStore } from '@/stores/user'
const userStore = useUserStore()

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
    { min: 2, max:32, message: '名称仅限2~32个字符', trigger: 'change' },
  ],
})

// const save = (e) => {
//   e.preventDefault()
//   form.value
//     .validate()
//     .then((result) => {
//       if (result === true) {
//         // var formData = new FormData()
//         // formData.append('name', model.name)
//         // formData.append('description', model.description)

//         axios.post('http://localhost:8080/login', { ...model }).then((response) => {
//           if (response.data.status === true) {
//             ElMessage.success('登录成功！')
//             router.push({ name: 'index' })
//           } else {
//             ElMessage.error('登录失败！')
//           }
//         })
//       }
//     })
//     .catch(() => {
//       ElMessage.error('登录失败！')
//     })
// }
const login = () => {
    form.value.validate().then((result) => {
        if (result === true) {
            request
                // 发送登录请求
                .post('/login', model)
                .then((response) => {
                    if (response.data.status === true) {

                        // 保存用户信息到Store和本地存储
                        userStore.login({ ...response.data.payload })
                        userStore.cache('user', { ...response.data.payload })

                        ElMessage.success('登录成功。')
                        router.replace({ name: 'index' })
                    } else {
                        ElMessage.error('登录失败。')
                    }
                })
                .catch(() => {
                    ElMessage.error('登录失败。')
                })
        }
    })
}
</script>

<style lang="scss" scoped>

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

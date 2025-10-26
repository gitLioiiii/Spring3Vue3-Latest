import axios from 'axios'
import { useUserStore } from '@/stores/user'
import { useRouter } from 'vue-router'

const instance = axios.create({
  baseURL: import.meta.env.APP_BASE_URL,
})

// 请求拦截器：自动添加Authorization头
instance.interceptors.request.use((config) => {
    const userStore = useUserStore()

    if (userStore.logged) {
        config.headers.Authorization = userStore.user.token.token
    }

    return config
})

// 响应拦截器：处理token过期
instance.interceptors.response.use(
    (response) => {
        return response
    },
    (error) => {
        // 检查是否是401未授权错误
        if (error.response && error.response.status === 401) {
            const userStore = useUserStore()
            const router = useRouter()
            
            // 清除用户登录状态
            userStore.logout()
            
            // 跳转到登录页
            router.push({ name: 'login' })
        }
        return Promise.reject(error)
    }
)

export default instance

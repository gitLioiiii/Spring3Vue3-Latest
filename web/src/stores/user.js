import { computed, ref } from 'vue'
import { defineStore } from 'pinia'

// 登录状态持久化和鉴权
export const useUserStore = defineStore('user', () => {
    const user = ref(null) // 存储用户信息

    const name = computed(() => user.value?.user.name || user.value?.user.username)

    const logged = computed(() => user.value !== null) // 登录状态

    // 刷新页面后从本地存储恢复登录状态
    const granted = () => {
        return logged.value
    }

    const login = (data) => {
        user.value = data // 设置用户信息
        cache('user', data)
    }

    const logout = () => {
        user.value = null // 清除用户信息
        clear('user')
    }

    const load = (key) => {
        return JSON.parse(window.localStorage.getItem(key))
    }

    // 本地存储管理
    const cache = (key, value) => {
        window.localStorage.setItem(key, JSON.stringify(value))
    }

    const clear = (key) => {
        window.localStorage.removeItem(key)
    }

    // 初始化：从本地存储恢复用户信息
    const storedUser = JSON.parse(window.localStorage.getItem('user'))
    if (storedUser) {
        user.value = storedUser
    }

    return { user,
             name,
             logged,
             login,
             logout,
             load,
             cache,
             clear,
             granted
            }
})

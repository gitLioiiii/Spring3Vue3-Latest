import { computed, ref } from 'vue'
import { defineStore } from 'pinia'

// 登录状态持久化和鉴权
export const useUserStore = defineStore('user', () => {
    const user = ref(null) // 存储用户信息

    const name = computed(() => user.value?.user?.name || user.value?.user?.username || '')

    // const logged = computed(() => user.value !== null) // 登录状态
    const logged = computed(() => {
        if (user.value === null) return false
        
        // 检查token是否过期
        if (user.value?.token?.expireAt) {
            const now = new Date()
            const expireAt = new Date(user.value.token.expireAt)
            if (now > expireAt) {
                // token已过期，清除用户信息
                logout()
                return false
            }
        }
        
        return true
    })

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
    const storedUser = window.localStorage.getItem('user')
    if (storedUser) {
        try {
            // user.value = JSON.parse(storedUser)
            const parsedUser = JSON.parse(storedUser)
            user.value = parsedUser
            
            // 检查token是否过期
            if (parsedUser?.token?.expireAt) {
                const now = new Date()
                const expireAt = new Date(parsedUser.token.expireAt)
                if (now > expireAt) {
                    // token已过期，清除用户信息
                    logout()
                }
            }
        } catch (error) {
            console.error('Failed to parse stored user data:', error)
            clear('user')
        }
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

import axios from 'axios'
// import { useUserStore } from '@/stores/user'

const instance = axios.create({
  baseURL: import.meta.env.APP_BASE_URL,
})


export default instance

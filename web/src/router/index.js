import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'index',
      component: () => import('@/views/IndexView.vue'),
    },
    // 医师列表
    {
      path: '/physician',
      name: 'physician_base',
      component: () => import('@/views/physician/BaseView.vue'),
      children: [
        {
          path: "",
          name: 'physician_index',
          component: () => import('@/views/physician/IndexView.vue'),
        },
        {
          path: "/create",
          name: 'physician_create',
          component: () => import('@/views/physician/CreateView.vue')
        },
        {
          path: "/update:id",
          name: 'physician_update',
          component: () => import('@/views/physician/UpdateView.vue')
        }
      ],
    },
    // 医师职位
    {
      path: '/position',
      name: 'position_base',
      component: () => import('@/views/physician/position/BaseView.vue'),
      children: [
        {
          path: "",
          name: 'position_index',
          component: () => import('@/views/physician/position/IndexView.vue'),
        },
        {
          path: "/assign",
          name: 'position_assign',
          component: () => import('@/views/physician/position/AssignView.vue'),
        },
        {
          path: "/create",
          name: 'position_create',
          component: () => import('@/views/physician/position/CreateView.vue'),
        },
        {
          path: "/update:id",
          name: 'position_update',
          component: () => import('@/views/physician/position/UpdateView.vue')
        }
      ],
    },
    // 医师科室
    {
      path: '/office',
      name: 'office_base',
      component: () => import('@/views/physician/office/BaseView.vue'),
      children: [
        {
          path: "",
          name: 'office_index',
          component: () => import('@/views/physician/office/IndexView.vue'),
        },
        {
          path: "/create",
          name: 'office_create',
          component: () => import('@/views/physician/office/CreateView.vue'),
        },
        {
          path: "/update:id",
          name: 'office_update',
          component: () => import('@/views/physician/office/UpdateView.vue')
        }
      ],
    },
    // 医师数据直观
    {
      path: '/data',
      name: 'data_base',
      component: () => import('@/views/physician/data/BaseView.vue'),
      children: [
        {
          path: "",
          name: 'data_index',
          component: () => import('@/views/physician/data/IndexView.vue'),
        }
      ],
    },
    {
      path: '/login',
      name: 'login',
      component: () => import('@/views/LoginView.vue'),
      meta: { HideChrome: true },
    },
    {
      path: '/register',
      name: 'register',
      component: () => import('@/views/RegisterView.vue'),
      meta: { HideChrome: true },
    }
  ],
})

export default router

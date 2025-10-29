<template>
  <nav class="base-navigator">
    <!-- <ElMenu :router="true" :style="{ height: '100%' }"> -->
    <ElMenu :router="true" :class="['base-navigator-menu']">
      <ElMenuItem index="index" :route="{ name: 'index' }">
        <img src="@/assets/index/首页.png" alt="index" style="width: 16px; height: 16px; margin-right: 8px;" />
        首页
      </ElMenuItem>
      <ElSubMenu index="physician">
        <template #title><img src="@/assets/icons/physician/认证医师.png" alt="医师" style="width: 16px; height: 16px; margin-right: 8px;" />医师</template>
          <ElMenuItem index="physician_index" :route="{ name: 'physician_index' }">
            <img src="@/assets/icons/physician/医师列表.png" alt="医师列表" style="width: 16px; height: 16px; margin-right: 8px;" />
            医师列表
          </ElMenuItem>
          <ElMenuItem index="position_assign" :route="{ name: 'position_assign' }">
            <img src="@/assets/icons/physician/职位详情.png" alt="医师职位" style="width: 16px; height: 16px; margin-right: 8px;" />
            医师职位
          </ElMenuItem>
          <ElMenuItem index="office_index" :route="{ name: 'office_index' }">
            <img src="@/assets/icons/physician/科室.png" alt="医师科室" style="width: 16px; height: 16px; margin-right: 8px;" />
            医师科室
          </ElMenuItem>
          <ElMenuItem index="data_index" :route="{ name: 'data_index' }">
            <img src="@/assets/icons/physician/数据.png" alt="数据" style="width: 16px; height: 16px; margin-right: 8px;" />
            数据直观图
          </ElMenuItem>
          <ElMenuItem index="user_create" :route="{ name: 'user_create' }">
            <img src="@/assets/icons/physician/用户.png" alt="用户界面" style="width: 16px; height: 16px; margin-right: 8px;" />
            用户界面
          </ElMenuItem>
      </ElSubMenu>
    </ElMenu>
  </nav>
</template>

<script setup>
import { ElMenu, ElMenuItem, ElSubMenu } from 'element-plus'
</script>

<style>
.base-navigator-menu {
  height: 100%;
}

/* .base-navigator {
  flex: 0 0 15rem;

  position: relative;
}

.base-navigator-menu {
  height: 100%;

  position: absolute;
  top: 0rem;
  left: 0rem;

  width: 100%;
} */
</style>

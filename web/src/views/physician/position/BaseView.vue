<template>
  <article class="base-article">
    <div class="base-heading">
      <h1>
        <span v-if="route.name === 'position_assign'" style="color:#9FD4FD">医师职位管理</span>
        <span v-if="route.name === 'position_index'" style="color:#9FD4FD">医师职位列表</span>
      </h1>
      <div>
        <ElButton
          :type="route.name === 'position_assign' ? 'primary' : 'default'"
          native-type="button"
          @click.stop="router.push({ name: 'position_assign' })"
          >职位列表</ElButton
        >
        <ElButton
          :type="route.name === 'position_index' ? 'primary' : 'default'"
          native-type="button"
          @click.stop="router.push({ name: 'position_index' })"
          >职位设置</ElButton
        >
        <ElButton
          :type="route.name === 'position_create' ? 'primary' : 'default'"
          native-type="button"
          @click.stop="router.push({ name: 'position_create' })"
          >新增</ElButton
        >
      </div>
    </div>
    <RouterView />
  </article>
</template>

<script setup>
import { RouterView, useRoute, useRouter } from 'vue-router'
import { ElButton } from 'element-plus'

const route = useRoute()
const router = useRouter()
</script>

<style>
.base-heading {
  display: flex;
  flex-flow: row nowrap;
  align-items: center;
  justify-content: space-between;
}
</style>

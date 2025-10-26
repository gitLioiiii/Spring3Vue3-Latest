<template>
    <BaseHeader v-if="!HideChrome" />
    <main :class="['base-main', { 'no-conponent': HideChrome }]">
    <BaseNavigator v-if="!HideChrome" />
    <RouterView />
  </main>
  <BaseFooter />
</template>

<script setup>
import { RouterView, useRoute } from 'vue-router'
import { computed } from 'vue'

import BaseHeader from '@/components/BaseHeader.vue'
import BaseNavigator from '@/components/BaseNavigator.vue'
import BaseFooter from '@/components/BaseFooter.vue'

const route = useRoute()
const HideChrome = computed(() => route.meta?.HideChrome === true)
</script>

<style lang="scss">

.el-pagination {
    margin: 2rem 0rem;
}

body {
  margin: 0rem;
}

.base-main > .base-navigator {
  position: fixed;
  top: 5rem;
  left: 0rem;

  height: calc(100vh - 5rem);
  width: 15rem;
}

.base-main > .base-article {
  /* 1 个值：四边相同
  2 个值：上下、左右
  3 个值：上、左右、下
  4 个值：上、右、下、左 */
  margin: 5rem 0rem 0rem 15rem;
  padding: 2rem;
}

.base-main.no-component > .base-article {
  margin: 0rem;
}

/* .base-main {
  display: flex;
  flex-flow: row nowrap;
  align-items: stretch;
  justify-content: flex-start;

  height: calc(100vh - 5rem);
  padding-top: 5rem;
}

.base-main > .base-article {
  flex: auto;

  overflow: auto;
} */
</style>

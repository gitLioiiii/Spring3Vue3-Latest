<template>
  <article class="base-article">
    <h1 class="color-red">主页数据面板</h1>
    <div :style="{ height: '2000px' }"></div>
  </article>
</template>

<script setup>
import axios from 'axios'

axios.get('http://localhost:8080/').then((response) => {
  console.log(response.data)
})
</script>

<style lang="scss">
@use "@/styles/element/rootConfig" as *;

.color-red {
  color: $red;
}
</style>

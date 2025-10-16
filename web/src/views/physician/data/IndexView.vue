<template>
  <div class="Cardview">
    <ElCard shadow="hover">
      <img src="@/assets/icons/physician/人数.png" alt="总人数" />
      <div>总人数</div>
      <div>{{ totalPeopleCount }}</div>
    </ElCard>
    <ElCard shadow="hover">
      <img src="@/assets/icons/physician/在职.png" alt="在职人数" />
      <div>在职人数</div>
      <div>{{ onJobCount }}</div>
    </ElCard>
    <ElCard shadow="hover">
      <img src="@/assets/icons/physician/离职退休.png" alt="离职人数" />
      <div>离职人数</div>
      <div>{{ offJobCount }}</div>
    </ElCard>
    <ElCard shadow="hover">
      <img src="@/assets/icons/physician/男生.png" alt="男生占比" />
      <div>男性占比</div>
      <div>{{ manProportion }}%</div>
    </ElCard>
    <ElCard shadow="hover">
      <img src="@/assets/icons/physician/年龄.png" alt="平均年龄" />
      <div>平均年龄</div>
      <div>{{ averageAge }}</div>
    </ElCard>
    <ElCard shadow="hover">
      <img src="@/assets/icons/physician/科室.png" alt="科室" />
      <div>科室数量</div>
      <div>{{ officeCount }}</div>
    </ElCard>
  </div>
  <!-- ECHARTS图表区域 -->
  <div class="chart-view">
    <div id="main-bar"></div>
  </div>
</template>

<script setup>
import { onMounted, onUnmounted, ref, computed } from 'vue'
import { ElCard } from 'element-plus'
import * as echarts from 'echarts'
import request from "@/utils/request"

let myChart = null
const physicians = ref([])

// 统计数据
const totalPeopleCount = computed(() => physicians.value.length)

const onJobCount = computed(() => {
return physicians.value.filter(p => p.serve === '在职').length
})

const offJobCount = computed(() => {
return physicians.value.filter(p => p.serve === '离职').length
})

const manProportion = computed(() => {
if (totalPeopleCount.value === 0) return 0
const manCount = physicians.value.filter(p => p.gender === '男').length
return ((manCount / totalPeopleCount.value) * 100).toFixed(1)
})

const averageAge = computed(() => {
if (totalPeopleCount.value === 0) return 0
const totalAge = physicians.value.reduce((sum, p) => sum + (parseInt(p.age) || 0), 0)
return (totalAge / totalPeopleCount.value).toFixed(1)
})

const officeCount = computed(() => {
const offices = new Set()
physicians.value.forEach(phy => {
  if (phy.office && phy.office.name) {
    offices.add(phy.office.name)
  }
})
return offices.size
})


onMounted(() => {
// 基于准备好的dom，初始化echarts实例
myChart = echarts.init(document.getElementById('main-bar'))

fetchPhysicians()

window.addEventListener('resize', handleResize)
})

const fetchPhysicians = () => {
request.get('/physician').then((response) => {
  if (response.data.status === true) {
    physicians.value = response.data.payload.physicians
    updateChart()
  }
})
}

const updateChart = () => {

const Count = {}

physicians.value.forEach(physician => {
  if(physician.office && physician.office.name) {
    const Name = physician.office.name
    Count[Name] = (Count[Name] || 0) + 1
  }
})

const x_positions = Object.keys(Count)
const y_peoplecounts = Object.values(Count)

myChart.setOption({
  title:{
    text: '各科室医护人员统计',
    left: 'center'
  },
  tooltip: {
    trigger: 'axis',
    formatter: function(params) {
      return `${params[0].name}<br/>医护人数: ${params[0].value}人`
    }
  },
  grid: {
    containLabel: true
  },
    xAxis: {
      type: 'category',
      data: x_positions,
      axisLabel: {
        fontSize: 12
      },
      axisTick: {
        alignWithLabel: true
      }
    },
    yAxis: {
      type: 'value',
      name: '人数/个',
      // nameLocation: 'middle',
      nameGap: 50,
      axisLine: {
        show: true
      },
      axisTick: {
        show: true
      },
      axisLabel: {
        formatter: '{value}人'
      },
      splitNumber: 5,
      minInterval: 2
    },
    series: [
      {
        name: '医师人数',
        type: 'bar',
        data: y_peoplecounts,
        barMaxWidth: 50,
        itemStyle: {
        color: '#C3E7FE',
        borderRadius: [6, 6, 0, 0]
        },
        emphasis: {
          itemStyle: {
            color: '#89E9E0'
          }
        },
        label: {
          show: true,
          position: 'top',
          formatter: '{c}人'
        }
      }
    ]
})
}

onUnmounted(() => {
window.removeEventListener('resize', handleResize)
if (myChart) {
  myChart.dispose()
}
})

function handleResize() {
if (myChart) {
  myChart.resize()
}
}
</script>

<style scoped>

.Cardview {
display: flex;
justify-content:space-around;
margin-bottom: 20px;
}

.Cardview :deep(.el-card) {
flex: 0 0 8rem;
padding: 0.125rem;
border-radius: 0.625rem;
}

.Cardview :deep(.el-card__body) {
display: flex;
flex-direction: column;
align-items: center;
gap: 6px;
}

.Cardview img {
width: 36px;
height: 36px;
object-fit: contain;
}

.Cardview :deep(.el-card:nth-child(1)) { background: #F3F8FF; border-color: transparent; }
.Cardview :deep(.el-card:nth-child(2)) { background: #F5FFF7; border-color: transparent; }
.Cardview :deep(.el-card:nth-child(3)) { background: #FFF7F5; border-color: transparent; }
.Cardview :deep(.el-card:nth-child(4)) { background: #F9F5FF; border-color: transparent; }
.Cardview :deep(.el-card:nth-child(5)) { background: #FFFDF5; border-color: transparent; }
.Cardview :deep(.el-card:nth-child(6)) { background: #F5FBFF; border-color: transparent; }

.chart-view {
display: flex;
justify-content: center;
align-items: center;
width: 100%;
background: white;
border-radius: 12px;
padding: 20px;
box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

#main-bar {
width: 100%;
height: 400px;
min-height: 400px;
}
</style>

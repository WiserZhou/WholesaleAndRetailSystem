<template>
  <div class="leftbox">
    <div class="top">
      <span class="sspan">预约时刻表</span>
      <el-select v-model="selectedOption" placeholder="请选择" class="selected">
        <el-option
          v-for="item in options"
          :key="item.value"
          :label="item.label"
          :value="item.value">
        </el-option>
      </el-select>
    </div>
    <div ref="chart" style="width: 100%; height: 100%;"></div>
  </div>

</template>

<script setup>
import { ref, onMounted } from 'vue';
import * as echarts from 'echarts';

const chart = ref(null);
const selectedOption = ref("今日")
const drawChart = () => {
  if (!chart.value) return;

  const myChart = echarts.init(chart.value);

  const dataX = ["8:00", "10:00", "13:00", "15:00", "17:00"];
  const dataY = [20, 35, 10, 42, 28];

  const gradientColor = new echarts.graphic.LinearGradient(0, 0, 0, 1, [
    { offset: 0, color: 'rgba(0, 191, 255, 0.5)' },
    { offset: 1, color: 'rgba(0, 191, 255, 0)' },
  ]);

  const option = {
    xAxis: {
      type: 'category',
      data: dataX,
    },
    yAxis: {
      type: 'value',
    },
    legend: {
      top: '90%',
      left: 'center'
    },
    grid: {
      top: '20%', // 顶部留出 10% 的高度
      left: '10%', // 左边留出 10% 的宽度
      right: '10%', // 右边留出 10% 的宽度
      bottom: '20%', // 底部留出 10% 的高度
    },
    series: [
      {
        type: 'line',
        data: dataY,
        smooth: true, // 启用曲线平滑
        areaStyle: {
          color: gradientColor,
        },
      },
    ],
  };

  myChart.setOption(option);
};

onMounted(() => {
  drawChart();
});

</script>
<style scoped>
.leftbox{
  height: 100%;
  width: 100%;
  display: flex;
  flex-direction: column;
}
.top{
  height: 20%;
  width: 100%;
  display: flex;
  flex-direction: row;
}
.sspan{
  margin-top: 10px;
  font-size: 24px;
  font-family: "幼圆", Arial, Helvetica, sans-serif;
  font-weight: bolder;
  margin-left: 20%;
}
.selected{
  margin-left: 20%;
  margin-top: 30px;
}
</style>

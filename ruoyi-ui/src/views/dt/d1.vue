<template>
  <div class="barBox">
    <div class="boxHead">
      <span class="title">出院vs住院人数</span>
    </div>
    <div ref="barContainer" class="bar"></div>
  </div>
</template>

<script setup>
import * as echarts from 'echarts';
import { ref, onMounted } from 'vue';

const barContainer = ref(null);

function drawBar() {
  const myBar = echarts.init(barContainer.value);
  const data = [189, 221, 223, 170, 193];
  const data2 = [208, 174, 197, 215, 207];

  // 配置项
  const option = {
    xAxis: {
      type: 'category',
      data: ['2023.5', '2023.6', '2023.7', '2023.8', '2023.9'],
      axisLabel: {
        margin: 10,
        interval: 0 // 设置刻度值离横坐标远一点
      },
    },
    yAxis: {
      type: 'value',
    },
    // legend: {
    //   data: ['出院人数', '住院人数'], // 添加两个柱的名称
    // },
    series: [
      {
        name: '出院人数', // 设置第一个柱的名称
        type: 'bar',
        data: data,
        // 设置柱的宽度，可以是像素值或百分比
        barWidth: '7%', // 30% 的宽度
        // 设置柱的顶部为圆形
        itemStyle: {
          normal: {
            barBorderRadius: [30, 30, 0, 0], // 分别指定四个角的圆角半径
          },
        },
        color: '#5EAEF6', // 设置第一个柱的颜色
      },
      {
        name: '住院人数', // 设置第二个柱的名称
        type: 'bar',
        data: data2,
        // 设置柱的宽度，可以是像素值或百分比
        barWidth: '7%', // 30% 的宽度
        // 设置柱的顶部为圆形
        itemStyle: {
          normal: {
            barBorderRadius: [30, 30, 0, 0], // 分别指定四个角的圆角半径
          },
        },
        color: '#E86283', // 设置第二个柱的颜色
      },
    ],
  };

  myBar.setOption(option);
}

onMounted(() => {
  drawBar(); // 在组件加载后绘制图表
});
</script>

<style scoped>
/* 在这里添加样式 */
.boxHead{
  align-items: center;
  margin-top: 0px;
  margin-left: 90px;
  width: 85%;
  height: 30px;
  border-bottom: 2px solid #d3d3d4;
  display: flex;
}
.barBox {
  width: 50%;
  height: 100%;
  padding-top: 10px;
}

.bar {
  padding-top: 0;
  margin-left: 30px;
  width: 100%;
  height: 33vh;
  margin-top: 0;
  margin-bottom: 0;
}
.title{
  font-family: "幼圆", Arial, Helvetica, sans-serif;
  font-size: 20px;
  margin-left: 70px;
  /* margin-top: 50px; */
  font-weight: bolder;
}
</style>

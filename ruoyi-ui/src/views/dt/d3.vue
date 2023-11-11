<template>
  <div class="chart-container">
    <span class="title">问诊设备情况</span>
    <div ref="chart" style="width: 250px; height: 250px;"></div>
  </div>
</template>

<script>
import { ref, onMounted } from 'vue';
import * as echarts from 'echarts';

export default {
  setup() {
    const chart = ref(null);

    onMounted(() => {
      const myChart = echarts.init(chart.value);

      // 饼图配置
      const pieOption = {
        series: [
          {
            type: 'pie',
            radius: ['60%', '70%'],
            avoidLabelOverlap: false,
            label: {
              show: false,
              position: 'center',
            },
            emphasis: {
              label: {
                show: true,
                fontSize: '30',
                fontWeight: 'bold',
              },
            },
            labelLine: {
              show: false,
            },
            data: [
              {
                value: 60,
                name: '损坏报备',
                itemStyle: { color: '#ED8C5C' },
                label: { show: false }, // 禁用标签
                emphasis: {
                  label: { show: true }, // 在高亮状态下显示标签
                },
              },
              {
                value: 100,
                name: '可用设备',
                itemStyle: { color: '#6D42D2' },
                label: { show: false },
                emphasis: {
                  label: { show: true },
                },
              },
            ],
          },
        ],
      };

      // 中心图片配置
      const centerImageOption = {
        type: 'image',
        style: {
          image: '/MedicalSystem/MedicalSystem/src/assets/images/doctor.png',
          x: 'center',
          y: 'center',
          width: 100,
          height: 100,
        },
      };

      // 合并配置
      const option = {
        tooltip: {
          trigger: 'item',
        },
        legend: {
          top: '90%',
          left: 'center',
        },
        graphic: [centerImageOption], // 将中心图片配置添加到图表中
        series: pieOption.series,
      };

      myChart.setOption(option);
    });

    return { chart };
  },
};
</script>

<style scoped>
.title {
  font-family: "幼圆", Arial, Helvetica, sans-serif;
  font-size: 20px;
  margin-left: 20px;
  margin-top: 10px;
  font-weight: bolder;
}
.chart-container {
  background-image: url('/MedicalSystem/MedicalSystem/src/assets/images/doctor.png');
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  margin: 0;
  background-size: 100px; /* 图片大小适应盒子 */
  background-repeat: no-repeat;
  background-position: center center;
}
.selected {
  margin-top: 20px;
  width: 170px;
}
</style>

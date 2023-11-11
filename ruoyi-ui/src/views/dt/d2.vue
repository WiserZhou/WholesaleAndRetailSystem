<template>
  <div class="chart-container">
    <el-select v-model="selectedOption" placeholder="请选择" class="selected">
      <el-option
        v-for="item in options"
        :key="item.value"
        :label="item.label"
        :value="item.value">
      </el-option>
    </el-select>
    <div ref="chart" style="width: 250px; height: 250px;"></div>
  </div>
</template>

<script>
import { ref, onMounted } from 'vue';
import * as echarts from 'echarts';

export default {
  setup() {
    const chart = ref(null);
    const options = ref([
      {value:'YEAR',label:'年'},
      {value:'HALF_YEAR',label:'半年'},
      {value:'SEASON',label:'季'},
      {value:'MONTH',label:'月'},
      {value:'WEEK',label:'周'},
      {value:'DAY',label:'日'}
    ])
    const selectedOption = ref('MONTH');
    onMounted(() => {
      const myChart = echarts.init(chart.value);

      // 中心图片配置
      const centerImageOption = {
        type: 'image',
        style: {
          image: '/src/assets/images/patient.png', // 图片路径
          x: 'center', // 图片水平居中
          y: 'center', // 图片垂直居中
          width: 50, // 图片宽度
          height: 50, // 图片高度
        },
      };

      // 饼图配置
      const pieOption = {
        series: [
          {
            type: 'pie',
            radius: ['60%', '70%'], // 内半径和外半径，创建一个环形图
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
              { value: 60, name: '出院人数',itemStyle: { color: '#5EAEF6' } },
              { value:100,name:'住院人数',itemStyle: { color: '#E86283' }}
              // 添加更多数据项以表示不同的数据片段
            ],
            itemStyle: {
              borderRadius: [100, 100, 100, 100], // 设置四个角的圆角半径为 100，使其成为圆形
            },
          },
        ],
      };
      pieOption.series[0].graphic = [
        centerImageOption,
        {
          type: 'circle',
          shape: {
            cx: '50%', // 圆心横坐标
            cy: '50%', // 圆心纵坐标
            r: '30%', // 圆的半径，可以根据需要调整
          },
          style: {
            fill: 'none', // 圆的填充颜色，none 表示透明
            stroke: 'red', // 圆的边框颜色
            lineWidth: 2, // 圆的边框宽度
          },
        },
      ];
      // 合并配置
      const option = {
        tooltip: {
          trigger: 'item'
        },
        legend: {
          top: '90%',
          left: 'center'
        },
        graphic: [centerImageOption], // 添加中心图片
        series: pieOption.series, // 添加饼图
      };

      // 使用配置绘制图表
      myChart.setOption(option);
    });

    return { chart,options,selectedOption };
  },
};
</script>

<style scoped>
.chart-container {
  background-image: url('/MedicalSystem/MedicalSystem/src/assets/images/hos.png');
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  /* height: 100vh; */
  margin: 0;
  background-size: 100px; /* 图片大小适应盒子 */
  background-repeat: no-repeat;
  background-position: center center;
}
.selected{
  margin-top: 20px;
  width: 170px;
}
</style>

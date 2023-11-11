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
<!-- <template>
    <div class="msg">

    </div>
  </template>

  <script setup>

</script>

<style scoped>

</style> -->
<template>
  <div class="bigbox">
    <div class="boxtop">
      <span>病例分类</span>
    </div>
    <div class="patient-list">
      <div class="patient-items" ref="patientItems">
        <div v-for="(item,index) in patientData" :key="index" class="per">
          <!-- <img :src="item.img" width="25" height="25"> -->
          <span>{{item.name}}</span>
          <span :class="{data1:index===0,data2:index===1,data3:index===2,data4:index===3}">{{ item.medicalRecordNumber }}</span>
        </div>
      </div>
    </div>
  </div>

</template>

<script>
export default {
  data() {
    return {
      patientData: [
        {
          id: 1,
          name: '黑色素痣（正常）',
          medicalRecordNumber: '12',
        },
        {
          id: 2,
          name: '基底细胞癌',
          medicalRecordNumber: '6',
        },
        {
          id: 3,
          name: '黑色素瘤',
          medicalRecordNumber: '4',
        },
        {
          id: 4,
          name: '其他',
          medicalRecordNumber: '5',
        },
        // 添加更多病人数据
      ],
      // currentPatientIndex: 0, // 当前显示的病人索引
      // autoScrollInterval: null, // 定时器
    };
  }
//     mounted() {
//       // 开始自动滚动
//       this.startAutoScroll();
//     },
//     methods: {
//       startAutoScroll() {
//         // 每隔5秒滚动到下一条病人信息
//         this.autoScrollInterval = setInterval(() => {
//           this.currentPatientIndex = (this.currentPatientIndex + 1) % this.patients.length;
//           this.scrollToCurrentPatient();
//         }, 5000);
//       },
//       scrollToCurrentPatient() {
//         // 滚动到当前病人信息位置
//         const patientItems = this.$refs.patientItems;
//         const itemHeight = patientItems.children[0].offsetHeight;
//         patientItems.scrollTop = this.currentPatientIndex * itemHeight;
//       },
//     },
//     beforeDestroy() {
//       // 在组件销毁前清除定时器
//       clearInterval(this.autoScrollInterval);
//     },
};
</script>

<style scoped>
.data1{
  color: #6133CC;
  font-weight: bolder;

}
.data2{
  color: #5DAAF6;
  font-weight: bolder;
}
.data3{
  color: #E98F62;
  font-weight: bolder;
}
.data4{
  color: #E45D7E;
  font-weight: bolder;

  @keyframes floatUp {
    0% {
      transform: translateY(10%); /* 初始位置在底部 */
      opacity: 0; /* 初始时透明 */
    }
    100% {
      transform: translateY(0); /* 最终位置在原始位置 */
      opacity: 1; /* 完全不透明 */
    }
  }
}
.per{
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  height: 50px;
  font-size: 17px;
  margin-left: 10px;
  margin-right: 20px;
}
.bigbox{
  display: flex;
  flex-direction: column;
  height: 100%;
  width: 100%;
}
.boxtop{
  height: 24%;
  width: 100%;
  display: flex;
  flex-direction: row;
  align-items: center;
}
.boxtop span{
  margin-left: 50px;
}
.boxtop span{
  font-family: yahei;
  font-size: 20px;
}
.patient-list {
  display: flex;
  flex-direction: column;
  width: 100%;
  height:75%;
}

.patient-items {
  /* 这个容器用于包含病人项 */
  display: flex;
  flex-direction: column;
  overflow-y: hidden; /* 隐藏垂直滚动条 */
}

.patient-item {
  /* padding: 10px; */
  border: 1px solid #ccc;
  width: 100%;
  height: 20%;
  /* margin: 5px; */
  background-color: fff;
  transition: transform 0.5s ease; /* 添加滚动动画效果 */
}

.patient-info {
  /* 样式病人信息的容器 */
  display: flex;
  flex-direction: row;
  justify-content: space-between;
}
</style>

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

<template>
  <div class="er-editor-demo-container">
    <div
      id="refContainer"
      ref="refContainer"
      style="width: 100%; height: 100%"/>
    <div id="refMinimapContainer" ref="refMinimapContainer" class="minimap-container" />
  </div>
</template>

<script lang="js">
import './index.less'
import { BaseGraph } from '../xflow/index' //  GraphData, GraphOptions

export default {
  name: 'index',
  components: {
  },
  props: {
    graphOptions: {
      type: Object,
      default: null,
      required: false
    },
    graphData: {
      type: Object,
      default: null,
      required: false
    }
  },
  data () {
    return {
      // graphContainer: document.getElementById('refContainer'),
      // minimapContainer: document.getElementById('refMinimapContainer'),
      baseGraph: null
    }
  },
  mounted () {
    const that = this

    setTimeout(() => {
      /** 初始化画布 */
      this.baseGraph = new BaseGraph({
        ...this.graphOptions,
        container: document.getElementById('refContainer'),
        grid: {
          visible: false
        },
        minimap: {
          enabled: true,
          container: document.getElementById('refMinimapContainer'),
          minScale: 0.5,
          maxScale: 2
        }
      })
      /** 渲染画布内容 */
      this.baseGraph.updateGraph(that.graphData)
    }, 100)
  },
  methods: {
    onHandleToolbar (action) {
      switch (action) {
        case 'in':
          console.log('in')
          this.baseGraph.zoomGraph(0.1)
          break
        case 'out':
          console.log('out')
          this.baseGraph.zoomGraph(-0.1)
          break
        case 'fit':
          console.log('fit')
          this.baseGraph.zoomGraph('fit')
          break
        case 'real':
          console.log('real')
          this.baseGraph.zoomGraph('real')
          break
        default:
      }
    }
  }
}
</script>

<style scoped>

</style>

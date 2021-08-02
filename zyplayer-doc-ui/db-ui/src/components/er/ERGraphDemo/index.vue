<template>
  <div style="width: 100%; height: 650px">
    <ERGraph :graphData="graphData"/>
  </div>
</template>

<script lang="js">
import ERGraph from '../ERGraph'
import { mockEntityData, mockRelationData } from './mock'
import Entity from './Entity'
export default {
  name: 'index',
  components: {
    ERGraph
  },
  data () {
    return {
      graphData: {
        nodes: '',
        edges: ''
      }

    }
  },
  mounted () {
    this.calRenderData()
  },
  methods: {
    calRenderData () {
      this.graphData.nodes = mockEntityData.map((entity) => {
        const { entityId, x, y, width, height } = entity
        return {
          x,
          y,
          width,
          height,
          id: entityId,
          component: {
            template: '<Entity :entity="entity"/>',
            data () {
              return {
                entity: entity
              }
            },
            components: {
              Entity
            }
          }
          // data: entity
        }
      }
      )

      this.graphData.edges = mockRelationData.map((relation) => {
        const { relationId, sourceEntityId, targetEntityId } = relation
        return {
          id: relationId,
          source: sourceEntityId,
          target: targetEntityId,
          label: '1:N',
          // render: (data: RelationCanvasModel) => {
          //   return null;
          // },
          data: relation
        }
      }
      )
    }
  }
}
</script>

<style scoped>

</style>

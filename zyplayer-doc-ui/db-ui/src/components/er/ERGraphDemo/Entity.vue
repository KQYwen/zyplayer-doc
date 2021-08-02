<template>
    <div class="entity-container fact">
      <div class="content other">
        <div class="head">
          <div>
            <a-icon type="bars" class="type"/>
            <span>{{ entity.name }}</span>
          </div>
          <a-icon type="ellipsis" class="more" />
        </div>

        <div class="body">

          <div class="body-item" v-for="(item,index) in entity.properties" :key="index">
            <div class="name">
              <!--            {{ value.isPK }} && <span class="pk">PK</span>-->
              <!--            {{value.isFK }} && <span class="fk">FK</span>-->
              {{ item.name }}
            </div>
            <div class="type">{{ item.propertyType }}</div>
          </div>
        </div>
      </div>

    </div>

</template>

<script>
import './Entity.less'
export default {
  name: 'Entity',
  props: {
    entity: {
      type: Object,
      default: null,
      required: true
    }
  },
  mounted () {
    console.log(this.entity, 'this.entity')
  }
}
</script>

<style scoped>

</style>

<template>
  <van-collapse v-model:value="pageSelect">
    <template v-for="page in pageList">
      <div
        v-if="!page.children"
        @click="pageSelectChange(page.id)"
        class="van-cell van-cell--clickable"
      >
        {{ page.name }}
      </div>
      <van-collapse-item :name="page.id" v-else>
        <template v-slot:title>
          <span @click="pageSelectChange(page.id)">{{ page.name }}</span>
        </template>
        <page-tree
          :page-list="page.children"
          @pageChange="pageSelectChange"
        ></page-tree>
      </van-collapse-item>
    </template>
  </van-collapse>
</template>

<script>
import { $on, $off, $once, $emit } from '../../utils/gogocodeTransfer'
export default {
  name: 'pageTree',
  data() {
    return {
      pageSelect: [],
    }
  },
  props: { pageList: Array },
  mounted() {},
  methods: {
    pageSelectChange(value) {
      $emit(this, 'pageChange', value)
    },
  },
  emits: ['pageChange'],
}
</script>

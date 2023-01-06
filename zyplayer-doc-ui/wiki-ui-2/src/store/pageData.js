import { defineStore } from 'pinia'

export const useStorePageData = defineStore('pageData', {
  state: () => {
    return {
      pageLoadStatus: 0,
    }
  },
})

/**

import { useStore } from '../../../store/wikiDisplay.js'
let store = useStore();

订阅变化：
store.$subscribe((mutation, state) => {
	if (mutation.events.key === 'xxx') {

	}
});

修改：
const commentChange = () => {
	store.commentShow = !store.commentShow;
}

计算属性：

let commentShow = computed(() => store.commentShow);

*/

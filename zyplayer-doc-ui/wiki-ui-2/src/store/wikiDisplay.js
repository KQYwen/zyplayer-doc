import { defineStore } from 'pinia'

export const useStoreDisplay = defineStore('wikiDisplay', {
  state: () => {
    return {
      // 左边目录栏宽度
      viewMenuWidth: 300,
    }
  },
})

/**

import { useStore } from '../../../store/wikiDisplay.js'
let store = useStore();

订阅变化：
store.$subscribe((mutation, state) => {
	if (mutation.events.key === 'lastFavoritePageId') {

	}
});

修改：
const commentChange = () => {
	store.commentShow = !store.commentShow;
}

计算属性：

let commentShow = computed(() => store.commentShow);

*/

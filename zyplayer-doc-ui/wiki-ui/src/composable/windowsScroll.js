import {ref, onMounted, onUnmounted, watch} from 'vue';
import {useStoreDisplay} from "@/store/wikiDisplay";
import { useWindowSize, useWindowScroll } from '@vueuse/core';

export function useScroll(callback) {
	const {x, y} = useWindowScroll();
	watch([x, y], () => {
		if (callback) {
			callback();
		}
	});
}

export function useResizeEvent(callback) {
	const {width, height} = useWindowSize();
	watch([width, height], () => {
		setTimeout(callback, 0);
	});

	let storeDisplay = useStoreDisplay();
	watch(storeDisplay, () => {
		setTimeout(callback, 0);
	});
}

<template>
    <div ref="leftResizeRef" class="left-resize">
        <i ref="leftResizeBarRef">...</i>
    </div>
</template>

<script>
    import {computed, onMounted, ref, watch} from 'vue';

    export default {
        emits: ['update:value', 'change'],
        setup(props, {emit}) {
            onMounted(() => {
                dragChangeRightAsideWidth();
            });
            let leftAsideWidth = ref(300);
            let leftResizeRef = ref();
            let leftResizeBarRef = ref();
            const dragChangeRightAsideWidth = () => {
                // 保留this引用
                let resize = leftResizeRef.value;
                let resizeBar = leftResizeBarRef.value;
                resize.onmousedown = e => {
                    let startX = e.clientX;
                    // 颜色改变提醒
                    resize.style.background = "#ccc";
                    resizeBar.style.background = "#aaa";
                    resize.left = resize.offsetLeft;
                    document.onmousemove = e2 => {
                        // 计算并应用位移量
                        let endX = e2.clientX;
                        let moveLen = startX - endX;
                        if ((moveLen < 0 && leftAsideWidth.value < 600) || (moveLen > 0 && leftAsideWidth.value > 300)) {
                            startX = endX;
                            leftAsideWidth.value -= moveLen;
                            if (leftAsideWidth.value < 300) {
                                leftAsideWidth.value = 300;
                            }
                            emit('update:value', leftAsideWidth.value);
                            emit('change', leftAsideWidth.value);
                        }
                    };
                    document.onmouseup = () => {
                        // 颜色恢复
                        resize.style.background = "#fafafa";
                        resizeBar.style.background = "#ccc";
                        document.onmousemove = null;
                        document.onmouseup = null;
                    };
                    return false;
                };
            };
            return {
                leftAsideWidth,
                leftResizeRef,
                leftResizeBarRef
            };
        },
    };
</script>

<style scoped>
    .left-resize {
        width: 5px;
        height: 100vh;
        cursor: w-resize;
        background: #fafafa;
    }

    .left-resize i {
        margin-top: 300px;
        width: 5px;
        height: 35px;
        display: inline-block;
        word-wrap: break-word;
        word-break: break-all;
        line-height: 8px;
        border-radius: 5px;
        background: #ccc;
        color: #888;
    }
</style>


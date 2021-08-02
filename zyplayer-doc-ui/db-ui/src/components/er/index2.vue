<template>
	<div style="padding: 10px;">
		<div ref="graphContainer"></div>
	</div>
</template>

<script>
	import {Graph} from '@antv/x6';

	export default {
		data() {
			return {
				graphData: {
					// 节点
					nodes: [
						{
							id: 'node1', // String，可选，节点的唯一标识
							x: 40,       // Number，必选，节点位置的 x 值
							y: 40,       // Number，必选，节点位置的 y 值
							width: 80,   // Number，可选，节点大小的 width 值
							height: 40,  // Number，可选，节点大小的 height 值
							label: 'hello', // String，节点标签
						},
						{
							id: 'node2', // String，节点的唯一标识
							x: 160,      // Number，必选，节点位置的 x 值
							y: 180,      // Number，必选，节点位置的 y 值
							width: 80,   // Number，可选，节点大小的 width 值
							height: 40,  // Number，可选，节点大小的 height 值
							label: 'world', // String，节点标签
						},
					],
					// 边
					edges: [
						{
							source: 'node1', // String，必须，起始节点 id
							target: 'node2', // String，必须，目标节点 id
						},
					],
				},
				graph: {},
			};
		},
		mounted () {
			this.graph = new Graph({
				container: this.$refs.graphContainer,
				height: 600,
				scroller: {
					enabled: true,
					pannable: true,
					pageBreak: false,
				},
				grid: {
					size: 10,
					visible: true
				}
			});
			this.graph.fromJSON(this.graphData);
			this.graph.centerContent();
		},
		methods: {
			addNode(){
				this.graph.createNode({
					shape: 'org-node',
					attrs: {
						'.card': { fill: background },
						'.image': { xlinkHref: image },
						'.rank': {
							fill: textColor,
							text: Dom.breakText(rank, { width: 160, height: 45 }),
						},
						'.name': {
							fill: textColor,
							text: Dom.breakText(name, { width: 160, height: 45 }),
						},
						'.btn > circle': { stroke: textColor },
						'.btn > text': { fill: textColor, stroke: textColor },
					},
				})
			}
		}
	}
</script>
<style>

</style>


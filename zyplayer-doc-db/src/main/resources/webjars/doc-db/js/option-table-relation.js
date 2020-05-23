var tableRelationData = {
//	"name": "user_info",
//	"children": [
//		{
//		"name": "user_auth",
//		"children": []
//	},{
//		"name": "group_info",
//		"children": []
//	}]
}

var tableRelationOption = {
	tooltip: {
		trigger: 'item',
		triggerOn: 'mousemove'
	},
	series: [
		{
			type: 'tree',
			/*orient: 'vertical',*/
			data: [tableRelationData],
			top: '1%',
			left: '7%',
			bottom: '1%',
			right: '20%',
			symbolSize: 7,
			label: {
				normal: {
					position: 'left',
					verticalAlign: 'middle',
					align: 'right',
					fontSize: 14
				}
			},
			leaves: {
				label: {
					normal: {
						position: 'right',
						verticalAlign: 'middle',
						align: 'left'
					}
				}
			},
			expandAndCollapse: true,
			animationDuration: 550,
			animationDurationUpdate: 750
		}
	]
}
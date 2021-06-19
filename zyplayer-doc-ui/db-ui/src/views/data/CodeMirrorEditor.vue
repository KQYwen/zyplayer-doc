<template>
    <div class="data-executor-vue">
		<textarea id="sqlTest" style="width: 100%;height: 100px;margin-top: 0;"></textarea>
    </div>
</template>

<script>

	import * as CodeMirror from 'codemirror/lib/codemirror'
	import 'codemirror/lib/codemirror.css'
	import 'codemirror/theme/monokai.css'
	import 'codemirror/addon/hint/show-hint.css'

	import 'codemirror/lib/codemirror.js'
	import 'codemirror/mode/sql/sql.js'
	import 'codemirror/mode/clike/clike.js'
	import 'codemirror/addon/display/autorefresh.js'
	import 'codemirror/addon/edit/matchbrackets.js'
	import 'codemirror/addon/selection/active-line.js'
	import 'codemirror/addon/display/fullscreen.js'
	import 'codemirror/addon/hint/show-hint.js'
	import 'codemirror/addon/hint/sql-hint.js'

    export default {
        data() {
            return {
            }
        },
        mounted: function () {
            this.initCodemirrorEditor();
        },
        methods: {
			initCodemirrorEditor(){
				CodeMirror.resolveMode("text/x-sql").keywords["left join"] = true;
				CodeMirror.resolveMode("text/x-sql").keywords["left"] = true;
				CodeMirror.resolveMode("text/x-sql").keywords["right join"] = true;
				CodeMirror.resolveMode("text/x-sql").keywords["right"] = true;
				CodeMirror.resolveMode("text/x-sql").keywords["inner join"] = true;
				CodeMirror.resolveMode("text/x-sql").keywords["inner"] = true;
				CodeMirror.resolveMode("text/x-sql").keywords["when"] = true;
				CodeMirror.resolveMode("text/x-sql").keywords["FROM_DAYS(N)"] = true;
				CodeMirror.resolveMode("text/x-sql").keywords["UPGRADE"] = true;
				let sqlCodeMirror = CodeMirror.fromTextArea(document.getElementById("sqlTest"), {
					mode: "text/x-sql",
					theme: "monokai",
					lineNumbers: true,
					lineWrapping: true,
					styleActiveLine: true,
					matchBrackets: true,
					autoRefresh: true,
					extraKeys: {
						"Alt-/": "autocomplete",
					},
					hintOptions: {
						completeSingle: false,
						tables: {
							"t_test_login": ["col_a", "col_B", "col_C"],
							"t_test_employee": ["other_columns1", "other_columns2"],
							"zyplayer_doc.user_info": [],
						}
					}
				});
				sqlCodeMirror.setValue("select * from t_test_login where 1=1");
				sqlCodeMirror.on("change", function (editor, change) {
					if (change.origin == "+input") {
						var textArray = change.text;
						//不提示
						setTimeout(function () { editor.execCommand("autocomplete"); }, 100);
						// if (!ignoreInputCode(textArray)) {
						// }
					}
				});
			},
		}
	}
</script>

<style>
	.CodeMirror {
		font-size: 16px;
	}
	.cm-s-monokai span.cm-keyword { line-height: 1em; font-weight: bold; }
</style>

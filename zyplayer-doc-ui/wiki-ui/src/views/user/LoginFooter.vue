<template>
	<div class="login-footer">
		<span class="item">
			Powered by <a target="_blank" href="https://doc.zyplayer.com">zyplayer-doc</a>
		</span>
	</div>
</template>

<script setup>
import {toRefs, ref, reactive, onMounted, watch, defineEmits} from 'vue';
import { useRouter, useRoute } from "vue-router";
import {useStoreUserData} from "@/store/userData";

let storeUser = useStoreUserData();
let route = useRoute();
let router = useRouter();
onMounted(() => {
});
</script>

<style lang="scss" scoped>
.login-footer {
	font-size: 14px;
	color: #666;
	width: 100%;
	position: absolute;
	bottom: 0;
	text-align: center;
	padding: 15px 0;

	.item + .item {
		margin-left: 15px;
	}

	a {
		color: #666;
		text-decoration: none;
	}
}
</style>

<template>
    <template class="menu-layout-children" v-if="!menuItem.meta || !menuItem.meta.hidden">
        <template v-if="!!menuItem.children">
            <a-sub-menu :key="menuItem.path" v-if="haveShowChildren(menuItem.children)">
                <template #title>
                    <template v-if="menuItem.meta">
                        <SettingOutlined v-if="menuItem.meta.icon === 'SettingOutlined'"/>
                        <FileTextOutlined v-if="menuItem.meta.icon === 'FileTextOutlined'"/>
                    </template>
                    <span>{{menuItem.name}}</span>
                </template>
                <MenuLayoutChildren :menuItem="children" v-for="children in menuItem.children"></MenuLayoutChildren>
            </a-sub-menu>
        </template>
        <a-menu-item :key="menuItem.path" v-else>
            <router-link :to="{path: menuItem.path, query: menuItem.query}">
                <template v-if="menuItem.meta">
                    <DashboardOutlined v-if="menuItem.meta.icon === 'DashboardOutlined'"/>
                    <FileTextOutlined v-if="menuItem.meta.icon === 'FileTextOutlined'"/>
                    <InfoCircleOutlined v-if="menuItem.meta.icon === 'InfoCircleOutlined'"/>
                </template>
                <span>{{menuItem.name}}</span>
            </router-link>
        </a-menu-item>
    </template>
</template>

<script>
    import {
        StarOutlined,
        SettingOutlined,
        CarryOutOutlined,
        FileTextOutlined,
        DashboardOutlined,
        InfoCircleOutlined,
    } from '@ant-design/icons-vue';

    export default {
        name: 'MenuLayoutChildren',
        props: {
            menuItem: Object,
        },
        data() {
            return {}
        },
        components: {
            StarOutlined, SettingOutlined, CarryOutOutlined, FileTextOutlined,
            DashboardOutlined, InfoCircleOutlined
        },
        methods: {
            haveShowChildren(children) {
                return children.filter(item => (!item.meta || !item.meta.hidden)).length > 0;
            },
        }
    }
</script>


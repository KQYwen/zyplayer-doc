<template>
  <div class="luckysheet-editor-container">
    <div id="luckysheet" class="luckysheet-content" style="margin:0px;padding:0px;position:absolute;width:100%;height:100%;left: 0px;top: 0px;"></div>
  </div>
</template>

<script setup>
import {onMounted, onUnmounted, ref, defineProps, defineEmits, watch} from 'vue';
import {ElMessage} from 'element-plus';
import axios from 'axios';
// 以 ESM 方式引入 luckysheet 及其样式，避免动态脚本导出形式不一致
import $ from 'jquery';
import mousewheel from 'jquery-mousewheel';
import 'luckysheet/dist/plugins/css/pluginsCss.css';
import 'luckysheet/dist/plugins/plugins.css';
import 'luckysheet/dist/css/luckysheet.css';
import 'luckysheet/dist/assets/iconfont/iconfont.css';

const props = defineProps({
  pageId: {
    type: [String, Number],
    default: ''
  },
  content: {
    type: String,
    default: ''
  },
  title: {
    type: String,
    default: ''
  }
});

const emit = defineEmits(['update:content', 'update:title']);

let luckysheet = null;
let isInitialized = ref(false);

// 初始化Luckysheet
const initLuckysheet = async () => {
  // 将 jQuery 挂到全局，供 luckysheet 使用
  if (typeof window !== 'undefined') {
    // @ts-ignore
    window.$ = window.$ || $;
    // @ts-ignore
    window.jQuery = window.jQuery || $;
  }
  // 注册 mousewheel 插件到当前 jQuery 实例
  try { mousewheel($); } catch (e) { /* ignore */ }

  // 动态引入 luckysheet，确保在 jQuery 就绪后再执行模块初始化
  let mod;
  try {
    mod = await import('luckysheet');
  } catch (e) {
    console.error('Luckysheet模块加载失败：', e);
    ElMessage.error('Luckysheet模块加载失败');
    return;
  }
  luckysheet = mod && (mod.default || mod);
  if (!luckysheet) {
    ElMessage.error('Luckysheet未加载，请检查依赖');
    return;
  }

  const options = {
    container: 'luckysheet',
    title: props.title || '在线表格',
    lang: 'zh',
    allowEdit: true,
    showinfobar: false,
    showsheetbar: true,
    showtoolbar: true,
    showstatisticBar: true,
    enableAddRow: true,
    enableAddCol: true,
    // 自定义工具栏
    config: {
      // 工具栏配置
      toolbar: {
        // 撤销重做
        undoRedo: true,
        // 字体
        font: true,
        // 格式
        format: true,
        // 对齐方式
        align: true,
        // 数字格式
        numberFormat: true,
        // 单元格
        cellFormat: true,
        // 表格操作
        table: true,
        // 插入
        insert: true,
        // 数据
        data: true,
        // 图表
        chart: true,
        // 图片
        image: true,
        // 链接
        link: true,
        // 条件格式
        conditionalFormat: true,
        // 公式
        formula: true,
        // 更多
        more: true
      },
      // 是否显示底部计数栏
      showstatisticBar: true,
      // 是否显示sheet页按钮
      showsheetbar: true,
      // 是否显示顶部信息栏
      showinfobar: false,
      // 自定义配置
      customConfig: {
        // 自动保存间隔（毫秒）
        autoSaveInterval: 30000,
        // 是否启用自动保存
        enableAutoSave: true
      }
    },
    // 钩子函数
    hook: {
      // 单元格编辑后触发
      cellEditAfter: function(cell, value, r, c) {
        handleContentChange();
      },
      // 工作表创建后触发
      sheetCreateAfter: function() {
        handleContentChange();
      },
      // 工作表删除后触发
      sheetDeleteAfter: function() {
        handleContentChange();
      },
      // 工作表重命名后触发
      sheetRenameAfter: function() {
        handleContentChange();
      }
    }
  };

  // 如果有已有内容，加载内容
  if (props.content) {
    try {
      const sheetData = JSON.parse(props.content);
      if (sheetData && sheetData.length > 0) {
        options.data = sheetData;
      }
    } catch (e) {
      console.error('解析表格数据失败:', e);
      // 创建默认工作表
      options.data = [{
        name: "Sheet1",
        color: "",
        index: 0,
        status: 1,
        order: 0,
        celldata: [],
        config: {}
      }];
    }
  } else {
    // 创建默认工作表
    options.data = [{
      name: "Sheet1", 
      color: "",
      index: 0,
      status: 1,
      order: 0,
      celldata: [],
      config: {}
    }];
  }

  // luckysheet 对象已在上面通过动态 import 确定，这里不再覆盖
  // 兼容多种导出形式（部分打包场景下 luckysheet 可能是函数或默认导出）
  try {
    if (luckysheet && typeof luckysheet.create === 'function') {
      luckysheet.create(options);
    } else if (typeof luckysheet === 'function') {
      // 某些版本/打包形式下直接调用函数完成初始化
      luckysheet(options);
    } else if (luckysheet && luckysheet.default && typeof luckysheet.default.create === 'function') {
      luckysheet.default.create(options);
    } else {
      console.error('Luckysheet导出异常：', luckysheet);
      ElMessage.error('Luckysheet导出异常，请检查脚本文件');
      return;
    }
  } catch (err) {
    console.error('Luckysheet初始化失败：', err);
    ElMessage.error('Luckysheet初始化失败');
    return;
  }
  isInitialized.value = true;

  // 设置自动保存
  if (options.config.customConfig.enableAutoSave) {
    setInterval(() => {
      if (isInitialized.value) {
        handleContentChange();
      }
    }, options.config.customConfig.autoSaveInterval);
  }
};

// 处理内容变化
const handleContentChange = () => {
  if (!luckysheet || !isInitialized.value) return;
  
  try {
    const sheetData = luckysheet.getAllSheets();
    const content = JSON.stringify(sheetData);
    emit('update:content', content);
  } catch (e) {
    console.error('获取表格数据失败:', e);
  }
};

// 获取内容
const getContent = () => {
  if (!luckysheet || !isInitialized.value) return '';
  
  try {
    const sheetData = luckysheet.getAllSheets();
    return JSON.stringify(sheetData);
  } catch (e) {
    console.error('获取表格数据失败:', e);
    return '';
  }
};

// 设置内容
const setContent = (content) => {
  if (!luckysheet || !isInitialized.value || !content) return;
  
  try {
    const sheetData = JSON.parse(content);
    // 销毁当前实例并重新创建
    luckysheet.destroy();

    // 如果传入内容不是数组或为空，使用默认工作表，避免初始化失败或卡死
    const dataForCreate = Array.isArray(sheetData) && sheetData.length > 0 ? sheetData : [{
      name: 'Sheet1',
      color: '',
      index: 0,
      status: 1,
      order: 0,
      celldata: [],
      config: {}
    }];

    const options = {
      container: 'luckysheet',
      title: props.title || '在线表格',
      lang: 'zh',
      allowEdit: true,
      showinfobar: false,
      showsheetbar: true,
      showtoolbar: true,
      showstatisticBar: true,
      enableAddRow: true,
      enableAddCol: true,
      data: dataForCreate,
      hook: {
        cellEditAfter: function() {
          handleContentChange();
        },
        sheetCreateAfter: function() {
          handleContentChange();
        },
        sheetDeleteAfter: function() {
          handleContentChange();
        },
        sheetRenameAfter: function() {
          handleContentChange();
        }
      }
    };

    luckysheet.create(options);
  } catch (e) {
    console.error('设置表格数据失败:', e);
  }
};

// 获取标题
const getTitle = () => {
  return props.title || '';
};

// 设置标题
const setTitle = (title) => {
  emit('update:title', title);
};

// 导出Excel
const exportExcel = () => {
  if (!luckysheet || !isInitialized.value) return;
  
  try {
    luckysheet.exportExcel();
  } catch (e) {
    console.error('导出Excel失败:', e);
    ElMessage.error('导出Excel失败');
  }
};

// 导入Excel
const importExcel = (file) => {
  if (!luckysheet || !isInitialized.value) return;
  
  try {
    // 实现文件上传和解析逻辑
    const formData = new FormData();
    formData.append('files', file);
    if (props.pageId) {
      formData.append('pageId', props.pageId);
    }

    axios.post('/zyplayer-doc-wiki/page/file/luckysheet/upload', formData, {
      headers: {
        'Content-Type': 'multipart/form-data'
      }
    }).then((response) => {
      if (response.data.errno === 0) {
        // 加载导入的数据 - 后端返回文件URL
        if (response.data.data && response.data.data.url) {
          const fileUrl = response.data.data.url;
          console.log('文件上传成功，URL:', fileUrl);
          
          // 使用Luckysheet的Excel导入功能
          if (luckysheet && luckysheet.excelImport) {
            // 直接使用URL进行导入（Luckysheet支持从URL导入）
            luckysheet.excelImport(fileUrl);
          } else {
            // 降级处理：下载文件后导入
            axios.get(fileUrl, {
              responseType: 'arraybuffer'
            }).then((excelResponse) => {
              if (luckysheet && luckysheet.excelImport) {
                luckysheet.excelImport(excelResponse.data);
              } else {
                ElMessage.warning('Excel解析功能暂不可用');
              }
            }).catch((error) => {
              console.error('下载Excel文件失败:', error);
              ElMessage.error('下载Excel文件失败');
            });
          }
        }
        ElMessage.success('导入成功');
      } else {
        ElMessage.error(response.data.message || '导入失败');
      }
    }).catch((error) => {
      console.error('导入失败:', error);
      ElMessage.error('导入失败');
    });
  } catch (e) {
    console.error('导入Excel失败:', e);
    ElMessage.error('导入Excel失败');
  }
};

// 监听内容变化
watch(() => props.content, (newContent) => {
  if (newContent && isInitialized.value) {
    setContent(newContent);
  }
});

onMounted(() => {
  // 使用动态 import 初始化
  initLuckysheet();
  return;
  // 检查Luckysheet是否已经加载
  if (typeof window.luckysheet !== 'undefined') {
    initLuckysheet();
    return;
  }

  // 动态加载Luckysheet CSS
  if (!document.getElementById('luckysheet-css')) {
    // 使用相对路径，确保在带有上下文路径的部署环境下也能正确加载
    const basePath = '';
    const link = document.createElement('link');
    link.id = 'luckysheet-css';
    link.rel = 'stylesheet';
    link.href = basePath + 'luckysheet/dist/plugins/css/pluginsCss.css';
    document.head.appendChild(link);

    const link2 = document.createElement('link');
    link2.rel = 'stylesheet';
    link2.href = basePath + 'luckysheet/dist/plugins/plugins.css';
    document.head.appendChild(link2);

    const link3 = document.createElement('link');
    link3.rel = 'stylesheet';
    link3.href = basePath + 'luckysheet/dist/css/luckysheet.css';
    document.head.appendChild(link3);

    const link4 = document.createElement('link');
    link4.rel = 'stylesheet';
    link4.href = basePath + 'luckysheet/dist/assets/iconfont/iconfont.css';
    document.head.appendChild(link4);
  }

  // 动态加载Luckysheet JS
  const script = document.createElement('script');
  // 改为相对路径，避免在生产环境根路径不同导致 404
  script.src = 'luckysheet/dist/luckysheet.umd.js';
  script.onload = () => {
    // 延迟执行，确保Luckysheet完全加载
    setTimeout(() => {
      initLuckysheet();
    }, 100);
  };
  script.onerror = () => {
    ElMessage.error('Luckysheet脚本加载失败');
    console.error('Luckysheet脚本加载失败');
  };
  document.head.appendChild(script);
});


onUnmounted(() => {
  if (luckysheet && isInitialized.value) {
    luckysheet.destroy();
    isInitialized.value = false;
  }
});

// 获取页面数据（用于保存）
const getPageData = () => {
  if (!luckysheet || !isInitialized.value) {
    return {
      title: props.title || '',
      json: '[]'
    };
  }
  
  try {
    const sheetData = luckysheet.getAllSheets();
    return {
      title: props.title || '',
      json: JSON.stringify(sheetData)
    };
  } catch (e) {
    console.error('获取页面数据失败:', e);
    return {
      title: props.title || '',
      json: '[]'
    };
  }
};

// 设置页面ID
const setPageId = (pageId) => {
  // 可以在这里实现页面ID相关的逻辑
  console.log('设置页面ID:', pageId);
};

// 暴露方法给父组件
defineExpose({
  getContent,
  setContent,
  getTitle,
  setTitle,
  exportExcel,
  importExcel,
  getPageData,
  setPageId
});
</script>

<style scoped>
.luckysheet-editor-container {
  position: relative;
  width: 100%;
  height: 100%;
  background: #fff;
}

.luckysheet-content {
  border: 1px solid #e0e0e0;
  border-radius: 4px;
}

/* Luckysheet样式覆盖 */
:deep(.luckysheet) {
  font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, "Helvetica Neue", Arial, sans-serif;
}

:deep(.luckysheet-toolbar-button) {
  border-radius: 4px;
}

:deep(.luckysheet-toolbar-button:hover) {
  background-color: #f5f5f5;
}

:deep(.luckysheet-cell-selected) {
  border-color: #1890ff;
}
</style>
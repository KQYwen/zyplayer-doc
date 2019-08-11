# zyplayer-doc-db项目的开放文档UI

## 环境要求

`Node >= 6`


## 开始

``` bash
# 执行下面的命令初始化
yarn
```

## 开发环境

``` bash
# 执行下面的命令后即可到 localhost:8010 看到页面
npm run dev
```

## 打包

``` bash
# 开发完成后执行打包命令，然后复制dist目录里的文件到zyplayer-doc-manage项目的webjars目录下即可
# 打包前记得修改zyplayer-doc-ui/manage-ui/src/common/config/apimix.js里的HOST接口地址
npm run build
```

# zyplayer-doc-wiki项目的UI
zyplayer-doc-wiki项目的UI使用此项目构建，开发起来爽得很，
但是，才发第一版，又他喵得重构一次~
之前从jQuery换到element以为是结局了，爽到吐，结果又被这个迷住了，前端真是让人高潮个不停啊，
但是，好东西怎么放过呢！

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
# 开发完成后执行打包命令，然后复制dist目录里的文件到zyplayer-doc-wiki项目的webjars目录下即可
# 打包前记得修改zyplayer-doc-ui/wiki-ui/src/common/config/apimix.js里的HOST接口地址
npm run build
```

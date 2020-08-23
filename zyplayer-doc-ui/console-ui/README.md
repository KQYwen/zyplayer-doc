# zyplayer-doc-manage项目的UI

## 常见问题

1、命令行要进入这个文件夹才能执行命令：

zyplayer-doc/zyplayer-doc-ui/console-ui

2、增加host，否则run不起来：

在文件 C:\Windows\System32\drivers\etc\hosts 末尾增加：

127.0.0.1 local.zyplayer.com

## 配置文件

1、这里可以配置接口请求的域名，可使用本地、线上，测试通过后就会打包到zyplayer-doc-wiki项目里面使用

zyplayer-doc-ui/manage-ui/src/common/config/apimix.js

2、这里可以配置启动后访问的地址，建议不改

zyplayer-doc-ui/console-ui/webpack.config.js

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

## 常见问题

1、命令行要进入这个文件夹才能执行命令：

zyplayer-doc/zyplayer-doc-ui/wiki-ui

2、修改host，否则run不起来：

在文件 C:\Windows\System32\drivers\etc\hosts 末尾增加：

127.0.0.1 local.zyplayer.com

## 配置文件

配置开发环境和线上环境的请求域名地址：
开发环境：.env.development
线上环境：.env.production

## 环境要求

`Node >= 6`

## 开始
执行下面的命令安装项目依赖
``` bash
npm install
```

## 开发环境
执行下面的命令启动前端工程
``` bash
npm run dev
```

## 打包
执行下面的命令编译打包前端页面
``` bash
npm run build
```
打包会自动将打包的文件放到项目的resource文件夹下
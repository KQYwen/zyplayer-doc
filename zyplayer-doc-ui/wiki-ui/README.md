# zyplayer-doc-wiki项目的UI
zyplayer-doc-wiki项目的UI使用此项目构建，开发起来爽得很，
但是，才发第一版，又他喵得重构一次~
之前从jQuery换到element以为是结局了，爽到吐，结果又被这个迷住了，前端真是让人高潮个不停啊，
但是，好东西怎么放过呢！
---来自一个前端新手的BB

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

``` bash
# 执行下面的命令初始化
npm install
```

## 开发环境

``` bash
# 执行下面的命令后即可到启动前端工程
npm run dev
```

## 打包

``` bash
npm run build
# 开发完成后执行打包命令，然后复制dist目录里的文件到zyplayer-doc-wiki/resources目录下
# 复制完成后记得修改index.html为doc-wiki.html
# todo：是否可以直接打包为：doc-wiki.html，不用手动改名字
# todo：是否可以直接打包到zyplayer-doc-wiki/resources文件夹下，难得复制
```

#!/bin/sh

# 执行文件名称
app_name="zyplayer-doc-manage"

# 先关闭服务
sh shutdown.sh

nohup java -jar $app_name.jar >/dev/null 2>&1 &

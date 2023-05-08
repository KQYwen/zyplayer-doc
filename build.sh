#!/bin/sh

dist_dir="dist"

app_name="zyplayer-doc"

version="1.1.5"

build_folder="${app_name}-${version}"

# 输出目录
target_dir="$dist_dir/${build_folder}"

echo "开始构建服务端..."

mvn clean package -Dmaven.test.skip=true

# 复制文件
if [ ! -d "$target_dir" ]; then
  mkdir -p $target_dir
fi

rm -rf $target_dir/*

# 复制服务端资源
cp -r zyplayer-doc-manage/target/zyplayer-doc.jar $target_dir
cp -r zyplayer-doc-manage/src/main/resources/application.yml $target_dir
cp -r zyplayer-doc-other/script/* $target_dir

echo "服务端构建完毕，构建结果在${target_dir}文件夹下"

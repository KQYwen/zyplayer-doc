@echo off

set dist_dir=dist

set app_name=zyplayer-doc

set version="1.1.5"

set build_folder=%app_name%-%version%

set target_dir=%dist_dir%\%build_folder%

call mvn clean package -Dmaven.test.skip=true

::copy files

rd /q /s %target_dir%

md %target_dir%

copy zyplayer-doc-manage\target\zyplayer-doc.jar %target_dir%
copy zyplayer-doc-manage\src\main\resources\application.yml %target_dir%

xcopy /e /y /q zyplayer-doc-other\script  %target_dir%

pause

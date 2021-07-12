package com.eyesmart.testapplication.android;

/**
 * adb:
 * https://www.jianshu.com/p/73a2d36ecf89
 * <p>
 * shell:
 * http://baijiahao.baidu.com/s?id=1583484244727721573&wfr=spider&for=pc
 */

public class ADB {
    /**
     adb的全称为Android Debug Bridge 调试桥，是连接Android手机与PC端的桥梁，
     通过adb可以管理、操作模拟器和设备，如安装软件、系统升级、运行shell命令等。
     adb工具位于SDK的platform-tools目录下

     ------------------管理设备-------------------------------------------

     注：android手机、模拟器统一称为“设备”
     adb devices                                // 显示连接到计算机的设备
     adb get-serialno                           // 获取设备的ID和序列号serialNumber

     adb reboot                                 // 重启设备
     adb shell cat /sys/class/net/wlan0/address // 获取mac地址

     ------------------adb服务相关-------------------------------------------

     adb kill-server                            // 终止adb服务进程
     adb start-server                           // 重启adb服务进程
     adb root                                   // 已root权限
     adb remount                                // 将system分区重新挂载为可读写分区
     adb wait-for-device                        // 在模拟器/设备连接之前把命令转载在adb的命令器中
     ------------------端口占用-------------------------------------------
     netstat -aon|findstr "5037"                // 找到占用5037端口的进程PID
     tasklist|findstr "5440"                    // 通过PID找出进程
     ------------------管理app----------------------------------------

     aapt d badging <apkfile>                   // 获取apk的packagename 和 classname

     adb install <apkfile>                      // 安装apk
     adb install -r <apkfile>                   // 保留数据和缓存文件，重新安装apk，
     adb install -s <apkfile>                   // 安装apk到sd卡

     adb uninstall <package>                    // 卸载app
     adb uninstall -k <package>                 // 卸载app但保留数据和缓存文件

     adb shell am start -n <package_name>/.<activity_class_name> // 启动应用

     ------------------查看内存占用----------------------------------------

     adb shell top                              // 查看设备cpu和内存占用情况
     adb shell procrank                         // 查询各进程内存使用情况
     adb shell kill [pid]                       // 杀死一个进程
     adb shell ps                               // 查看进程列表
     adb shell ps -x [PID]                      // 查看指定进程状态
     adb shell service list                     // 查看后台services信息
     adb shell cat /proc/meminfo                // 查看当前内存占用
     adb shell cat /proc/iomem                  // 查看IO内存分区

     ------------------文件操作--------------------------------------------

     //android中，sdcard代表内置存储，不同系统中tf卡的设备名可能不同，使用查看adb shell ls mnt查看所有存储设备名。

     adb remount                                // 将system分区重新挂载为可读写分区
     adb push <local> <remote>                  // 从本地复制文件到设备
     adb pull <remote>  <local>                 // 从设备复制文件到本地
     adb shell ls                               // 列出目录下的文件和文件夹，等同于dos中的dir命令
     adb shell cd <folder>                      // 进入文件夹，等同于dos中的cd 命令
     adb shell rename path/oldfilename path/newfilename // 重命名文件
     adb shell rm /system/avi.apk               // 删除system/avi.apk
     adb shell rm -r <folder>                   // 删除文件夹及其下面所有文件
     adb shell mv path/file newpath/file        // 移动文件
     adb shell chmod 777 /system/fonts/DroidSansFallback.ttf // 设置文件权限
     adb shell mkdir path/foldelname            // 新建文件夹
     adb shell cat <file>                       // 查看文件内容

     ------------------shell操作--------------------------------------------

     (change directory)
     cd /system/bin                 表示切换到/system/bin路径下。
     cd /                           表示切换到根目录。
     cd ../                         表示切换到上一层路径。

     (list)
     ls                             显示目录下的所有文件及文件夹。
     ls -l                          显示当前路径下的所有文件及文件夹的详细信息，

     (change mode)
     chmod 755 1.sh                 对于文件1.sh，设定1.sh的所有者权限为可读可写可执行，1.sh文件所有者所在组和其他用户对1.sh只有读取和执行的权限，没有修改权限。

     (concatenate)连接
     cat test.txt                   读取当前路径下test.txt文件内容。

     echo hi 显示hi到屏幕上。
     echo hi > test2.txt            将hi写入test2.txt中，test2.txt中原有的内容被覆盖。
     echo hi >> test2.txt           将hi追加写入test2.txt中，test2.txt中原有的内容仍保留在hi之前。

     (grep)检索
     ls -l | grep test              检索包含test的文件和文件夹的意思。
     cat test.txt | grep -i name    检索cat test.txt的执行结果中是否有包含name的内容(-i表示不区分name的大小写），也就查找包含test.txt中包含name的行。

     (make directory)
     mkdir /data/path               在/data路径下创建path文件夹。
     mkdir -p a/b/c                 参数 -p用于创建多级文件夹，这句命令表示在当前路径下创建文件夹a， 而a文件夹包含子文件夹b，b文件夹下又包含子文件夹c。

     (remove)
     rm -rf path                    删除path。常用参数-r -f，-r表示删除目录，也可以用与删除文件，-f表示强制删除，不需要确认
     rm test.txt                    删除test.txt。

     (copy)
     cp /data/logs /data/tmp/logs   复制/data路径下的logs到/data/tmp路径下。
     cp 1.sh /sdcard/               复制当前路径下的1.sh到/sdcard下。

     (move)
     mv 111 222                     重命名
     mv aaa 222/                    移动到

     (process status)
     ps                             查看进程详细信息

     kill 3497                      停止进程号为3497的进程。

     date                           查看日期和当前时间信息。


     wm size                        查看设备分辨率
     wm density                     查看设备像素密度
     pm list packages               列出所有安装程序信息

     */
}

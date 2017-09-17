package com.eyesmart.testapplication;

/**
 * Git:最先进的分布式版本控制系统
 * CVS、SVN:集中式的版本控制系统
 * <p>
 * Git优点：不必须有“中央服务器”，不必须联网；分布在每台电脑上都有完整版本，可互相推送
 */

class Git {
/**
 * 配置：
 * $ git config --global user.name "Your Name"
 * $ git config --global user.email "email@example.com"
 *
 * 初始化repository：
 * $ git init                   //会生成.git目录
 *
 * 工作区控制：
 * $ git add <file>             //添加修改；<file>为.时代表全部；若是删除文件，也可用git rm <file>
 * $ git checkout -- <file>     //替换为暂存区、版本库的文件状态
 * $ git status                 //查看状态
 * $ git diff HEAD -- <file>    //查看和版本库中的不同
 *
 * 暂存区控制：
 * $ git reset HEAD <file>      //撤销暂存区修改
 *
 * 版本库控制：
 * $ git commit -m "提交说明"   //提交版本
 * $ git reset --hard HEAD^     //回退版本，最后参数为HEAD^、HEAD~1、版本号，HEAD为指针
 * $ git log                    //查看提交历史
 * $ git reflog                 //查看命令历史
 *
 * 远程仓库：
 * $ ssh-keygen -t rsa -C "youremail@example.com" //创建SSH Key
 *
 *
 * $ git remote -v              //查看远程库
 * $ git pull                   //抓取最新
 * $ git push origin <branch-name>  //推送提交
 * $ git checkout -b <branch-name> origin/<branch-name> //创建并关联远程分支
 * $ git branch --set-upstream <branch-name> origin/<branch-name> //建立分支关联
 */
}

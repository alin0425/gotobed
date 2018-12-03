#####配置用户名：git config --global user.name "用户名"
#####配置邮箱：git config --global user.email "邮箱"
#####编码配置(gui)：git config --global gui.encoding utf-8
#####编码配置(status)：git config --global core.quotepath off
###git ssh key pair配置---------
#####1.ssh-keygen -t rsa -C "邮箱"
#####2.在用户目录下生成.ssh文件夹，找到公钥复制
#####3.进入github，将公钥添加进去
###常用命令---------
#####创建本地仓库：git init 
#####添加到暂存区：git add 
#####提交到本地仓库：git commit -m "描述" 
#####检查文件：git status 
#####查看提交：git log 
#####版本回退：git reset --hard committid 
#####查看分值：git branch
#####分支创建与切换：git checkout -b 分支名
#####切换分支：git checkout 分支名
#####拉取：git pull
#####提交：git commit -u origin master
#####分支合并：git merge branchname
###本地仓库与远程长裤---------
#####关联：git remote add origin "远程仓库地址"
#####frist上传：git push -u origin master
#####after: git pust origin master

#####   .gitignore文件：其中配置不需要添加到版本管理中
#####1./idea 忽略根目录下的idea文件
#####2.idea/ 忽略idea目录下的所有文件
#####git add . 提交所有.....
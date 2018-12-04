 ##### 配置用户名：git config --global user.name "用户名"
 ##### 配置邮箱：git config --global user.email "邮箱"
 ##### 编码配置(gui)：git config --global gui.encoding utf-8
 ##### 编码配置(status)：git config --global core.quotepath off
 ### git ssh key pair配置---------
 ##### 1.ssh-keygen -t rsa -C "邮箱"
 ##### 2.在用户目录下生成.ssh文件夹，找到公钥复制
 ##### 3.进入github，将公钥添加进去
 ### 常用命令---------
 ##### 创建本地仓库：git init 
 ##### 添加到暂存区：git add 
 ##### 提交到本地仓库：git commit -m "描述" 
 ##### 检查文件：git status 
 ##### 查看提交：git log 
 ##### 版本回退：git reset --hard committid 
 ##### 查看分值：git branch
 ##### 分支创建与切换：git checkout -b 分支名
 ##### 切换分支：git checkout 分支名
 ##### 拉取：git pull
 ##### 提交：git commit -u origin master
 ##### 分支合并：git merge branchname
 ### 本地仓库与远程长裤---------
##### 关联：git remote add origin "远程仓库地址"
 ##### frist上传：git push -u origin master
 ##### after: git pust origin master

 #####   .gitignore文件：其中配置不需要添加到版本管理中
 ##### 1./idea 忽略根目录下的idea文件
 ##### 2.idea/ 忽略idea目录下的所有文件
 ##### git add . 提交所有.....
 
 
      
# 电商项目-需求分析

### 用户：
    实体类+数据库表
    登陆
    注册
    密码操作
    获取个人信息
    退出登陆    
### 商品
#### 后台
    商品显示
    商品修改
    商品删除
    商品上下架状态
#### 前台
    搜索商品
    商品详情
### 类别
    增/删/改商品
 #### 类别显示
    查看子类以及子类的子类(多级下拉选择功能)   
###  购物车
    商品操作——添加到购物车  
    商品数量 +或-
    商品移出购物车
    对商品操作  单选/多选/全选/取消选择
    显示购物车商品数量
### 地址
    用户信息其一
    订单创建时对地址的  添加/修改/删除
### 订单
#### 前台
    创建订单
    取消订单
    显示所有订单
    订单详情    
#### 后台
    订单显示
    订单详情
    发货
### 支付
    支付
    支付回调
    查看支付状态  
    
### 部署
    阿里云部署  
    
  
    
 
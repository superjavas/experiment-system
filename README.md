# experiment-system
你好哈哈哈
#### 介绍
虚拟仿真实验平台是⼀个基于计算机组成原理的双端⼝实验的线上实验平台。学⽣可以通过此平台在线上模拟双端⼝存储器实验的读写操作，并填写相应的实验报告并⽣成成绩。⽽⽼师通过此平台来创建实验的班级、对学⽣的管理等功能。前端为Vue，后端为SpringBoot和MyBatis，数据库是MySQL数据库



#### 软件架构
本项目主要基于 SpringBoot 和 Vue2.0 的前后端数据分离的 Web虚拟仿真实验平台。SpringBoot 使用 Maven 搭建，数据库方面采用 MySQL5.7，使用 MyBatis 持久层框架用于简化JDBC代码和参数，以及逆向生成 POJO 实体类。前端使用 Vue 脚手架搭建前端框架，使用 Element-ui组件对前端页面进行渲染，方便前端的开发，使用 Axios 方法实现前后端数据传输操作。


#### 安装教程

将项目下载到本地，用idea或其他IDE打开。在SpringBoot配置文件更改自己的MySQL用户名和密码，前端Vue使用脚手架的方式搭建。将sql文件导入navicat之中生成数据库表。确保数据库连接正常

#### 使用说明

有三种不同的登陆角色：管理员，老师，学生

对于管理员用户来说默认的账户是 admin，密码是 123456

对于学生小岑用户名为2019081612，密码是123456

对于张老师用户名为admin2，密码123456

1.  ![image-20240824152638659](E:\gitee仓库\experiment-system\README.assets\image-20240824152638659.png)
1.  ![image-20240824153417652](https://cdn.jsdelivr.net/gh/superjavas/picturedemo/image/image-20240824153417652.png)
1.  ![image-20240824153458891](https://cdn.jsdelivr.net/gh/superjavas/picturedemo/image/image-20240824153458891.png)
2.  双端口存储器原理实验主要参考了 TEC-4 实验面板，主要的功能部件包括 SW_BUS 控制信号，地址寄存器 AR1 和 AR2，双端口 RAM 存储和 IR 指令寄存器，复位按钮 CLR# 以及实验相关的引脚，包括控制左端口读写操作的 CEL#和 LRW，控制右端口读操作的 CER。实验的主要步骤为操作实验的读写操作，包括左端口读和左端口写以及右端口读 操作通过控制SW0~SW7将来设置相应的地址插入相应的数据通过数据通路的信号灯以 及 AR1 和 AR2 的数据指示灯反映出来。双端口存储器 RAM 中存储着地址数据对应信息以用来检验实验数据的正确性。如图所示：
2.  ![image-20240824153939721](https://cdn.jsdelivr.net/gh/superjavas/picturedemo/image/image-20240824153939721.png)
3.  ![image-20240824153722991](https://cdn.jsdelivr.net/gh/superjavas/picturedemo/image/image-20240824153722991.png)

#### 参与贡献

1.  Fork 本仓库
2.  新建 Feat_xxx 分支
3.  提交代码
4.  新建 Pull Request


#### 特技

1.  使用 Readme\_XXX.md 来支持不同的语言，例如 Readme\_en.md, Readme\_zh.md
2.  Gitee 官方博客 [blog.gitee.com](https://blog.gitee.com)
3.  你可以 [https://gitee.com/explore](https://gitee.com/explore) 这个地址来了解 Gitee 上的优秀开源项目
4.  [GVP](https://gitee.com/gvp) 全称是 Gitee 最有价值开源项目，是综合评定出的优秀开源项目
5.  Gitee 官方提供的使用手册 [https://gitee.com/help](https://gitee.com/help)
6.  Gitee 封面人物是一档用来展示 Gitee 会员风采的栏目 [https://gitee.com/gitee-stars/](https://gitee.com/gitee-stars/)

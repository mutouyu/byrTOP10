#介绍
byrTOP10介绍 byrTOP10是一个Java Servlet 和 python Scrapy(0.24) 结合的一个微信公众帐号开发应用。功能是实现对北邮人论坛每日10大帖子的爬取后存入mysql数据库供微信公众号的检索。如输入20150831则会返回2015年8月31号的当日十大帖子。由于开发不久，数据库里的数据不多，记录日期重20150831开始，后续的数据库信息由scrapy和crontab每日定时6点30分开始爬去当日十大。


![](http://i.imgur.com/nZNgLgf.png)

![](http://i.imgur.com/rDJHDlT.jpg)

##mysql数据库配置
修改mysql数据库配置

```
nano /etc/mysql/my.cnf 
#bind-address 127.0.0.1 
```
```
sudo /etc/init.d/mysql restart #重启mysql
```

创建用户
```
CREATE USER '****'@'localhost' IDENTIFIED BY '****';
CREATE USER '****'@'%' IDENTIFIED BY '****';
GRANT ALL ON *.* TO '****'@'localhost';
GRANT ALL ON *.* TO '****'@'%';
```
创建数据库
```
CREATE DATABASE `TOP10` CHARACTER SET utf8 COLLATE utf8_general_ci;
```
建top10表
```
python dataScrapy/t1/spiders/sp2.py
```
运行爬虫
```
cd dataScrapy
scrapy crawl t2
```

##修改dataScrapy项目
修改sp2.py 中的数据库用户名和密码
##修改myJavaWeb项目
修改jdbcUtils.java 中的数据库用户名和密码
修改SignUtil.java 中的token

#部署服务器
```
#修改apache-tomcat-7.0.57/conf/tomcat-users.xml 
<tomcat-users>
  <role rolename="manager-gui"/>
    <role rolename="manager-script"/>
    <user username="tomcat" password="tomcat" roles="manager-gui, manager-script"/>
</tomcat-users>
```

```
#修改maven settings.xml
<servers>
  <server>
    <id>Tomcat7</id>
    <username>tomcat</username>
    <password>tomcat</password>
  </server>
</servers>
```

```
cd myJavaWeb
#运行tomcat7
mvn tomcat7:deploy
```

#增加爬虫定时任务 crontab

>由于是美国的VPS，所以改下时区

```
sudo dpkg-reconfigure tzdata #选取Asia Shanghai就可以了
```

```
nano Top10.sh

#!/bin/bash
export PATH="/usr/local/bin:/usr/bin:/bin"
cd `dirname $0`
exec "$@"


```

```
crontab -e 
```
```
30 6 * * * /root/tomcat/dataScrapy/Top10.sh  scrapy crawl t2   #每天6点30分去爬论坛数据
```

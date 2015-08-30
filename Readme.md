

![](http://i.imgur.com/nZNgLgf.png)

![](http://i.imgur.com/rDJHDlT.jpg)

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

#修改dataScrapy项目
修改sp2.py 中的数据库用户名和密码
#修改myJavaWeb项目
修改jdbcUtils.java 中的数据库用户名和密码
修改SignUtil.java 中的token
#!/usr/bin/env python
# -*- coding: utf-8 -*-
from scrapy.spider import BaseSpider
from scrapy.selector import HtmlXPathSelector
from ..items import T2Item

import peewee
from datetime import datetime
from peewee import *

db = MySQLDatabase('TOP10', user='****',passwd='****')

class Top10(peewee.Model):
    Tdate = peewee.TextField()
    title = peewee.TextField()
    url = peewee.TextField()

    class Meta:
        database = db

class t1spider(BaseSpider):
    name = 't2'
    allowed_domains = ['m.byr.cn']
    start_urls =['http://m.byr.cn/']
    def parse(self,response):
        hxs = HtmlXPathSelector(response)
        for i in hxs.select('//li//a'):
            title = i.xpath('text()').extract()
            url = i.xpath('@href').extract()
            item = T2Item()
            item['title'] = title[0][:-1]
            item['url'] = 'http://m.byr.cn'+url[0][:]
            top10 = Top10(Tdate= datetime.now().strftime("%Y-%m-%d").replace('-',''), title=title[0][:-1],url='http://m.byr.cn'+url[0][:])
            top10.save()
            yield item

if __name__ == '__main__':
    Top10.create_table()

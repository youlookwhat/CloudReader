# CloudReader(云阅)

> Netease cloud music Ui && Retrofit + RxJava + Mvvm-databinding && GankIo、Douban Api 

一款基于网易云音乐UI，使用GankIo及豆瓣api开发的符合Google Material Desgin阅读类的App。项目采取的是Retrofit + RxJava + MVVM-DataBinding架构开发，现主要包括：干货区、电影区和书籍区三个模块。



## 模块分析
### 干货区（gankio）
 - 每日推荐
 - 福利
 - 干货订制
 - 大安卓

### 电影区（豆瓣）
 - 热映榜（每日更新）
 - 豆瓣电影Top250

### 书籍区（豆瓣）[正在完善]
 - 综合
 - 文学
 - 生活

## 技术干货
 - 基于``DataBinding``的快速开发框架（Activity、Fragment(懒加载)基类，包含 加载中，加载成功和加载失败等）
 - 基于``RecyclerView``的下拉刷新，上拉加载，使用基于``DataBinding``的BaseHolder,使用方便，高效，简洁。
 - 基于``SwipeRefreshLayout``刷新控件，包含``RecyclerView``的上拉加载，支持一般线性列表、GridView和瀑布流。
 - 高仿网易云音乐歌单详情页，并封装成基类，方面使用。
 - ``ToolBar``的详细使用，仿网易云音乐ToolBar。
 - ``Glide``的项目使用，高斯模糊、加载监听、获取缓存路径存储图片等。
 - 透明状态栏的用法。

## License
```
Copyright (C) 2016 Bin Jing

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
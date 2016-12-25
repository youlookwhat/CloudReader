# CloudReader

> Netease cloud music Ui && Retrofit + RxJava + Mvvm-databinding && GankIo、Douban Api 

#### 云阅是一款基于网易云音乐UI，使用GankIo及豆瓣api开发的符合Google Material Desgin阅读类的开源项目。

## 项目

项目采取的是Retrofit + RxJava + MVVM-DataBinding架构开发。其中现主要包括：干货区、电影区和书籍区三个模块。

## 效果图

<img width="160" height=“274” src="https://github.com/youlookwhat/CloudReader/blob/master/screenshot/page_gank_00.png"></img>
<img width="160" height=“274” src="https://github.com/youlookwhat/CloudReader/blob/master/screenshot/page_gank_01.png"></img>
<img width="160" height=“274” src="https://github.com/youlookwhat/CloudReader/blob/master/screenshot/page_gank_02.png"></img>
<img width="160" height=“274” src="https://github.com/youlookwhat/CloudReader/blob/master/screenshot/page_gank_03.png"></img>
<img width="160" height=“274” src="https://github.com/youlookwhat/CloudReader/blob/master/screenshot/page_gank_04.png"></img>
<img width="160" height=“274” src="https://github.com/youlookwhat/CloudReader/blob/master/screenshot/page_movie_01.png"></img>
<img width="160" height=“274” src="https://github.com/youlookwhat/CloudReader/blob/master/screenshot/page_movie_02.png"></img>
<img width="160" height=“274” src="https://github.com/youlookwhat/CloudReader/blob/master/screenshot/page_movie_03.png"></img>


## 模块分析
### 干货区（gank.io）
 - 介绍：其中包括 每日推荐、福利、干货订制、大安卓。API使用的是动听（轮播图）和代码家的Gank.Io。切换加载动画和缓存都仿网易云音乐，不可以说一模一样但基本是这个样子。
 
 - 功能：1、首页是干货的每日内容，第二天12：30之后更新，因为双休不更新所以内容缓存三天取不到就取缓存；2、福利图片使用的是Glide加载，可以点击查看大图，支持双指缩放，一下可查看列表的所有图片，再也不用逐个点击每张图啦；3、干货订制里可以筛选喜欢干货的类别；4、大安卓支持下拉刷新方便查看最新的资源，列表使用的是``RecyclerView``，并基于``DataBinding``的``BaseHolder``,使用方便，高效，简洁。

### 电影区（豆瓣）
 - 介绍：分为电影热映区和豆瓣电影Top250。API是豆瓣提供的。限制了每个ip每分钟请求40次，所以请酌情使用，由此带来的不便请见谅。

 - 功能：电影详情页是仿网易云音乐的歌单详情页做的，并封装成基类，方便使用。效果有转场动画，透明状态栏，滑动title渐变色等，几乎和歌单详情页一样，具体细节还有待优化。

### 书籍区（豆瓣）
 - 介绍：包括 综合、文学和生活三部分。使用的是豆瓣的搜索API。更多订制内容由于时间原因第一版还未添加，第二版会加上。

 - 功能：使用的是``SwipeRefreshLayout``刷新控件结合``RecyclerView``的方式，支持一般的列表、GridView和瀑布流的上拉加载的使用。书籍详情页类似电影详情页。


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
# CloudReader

[![Apache License 2.0][1]][2] 
[![API][3]][4]

> Netease cloud music Ui && Retrofit + RxJava + MVVM-databinding && GankIo、Douban Api 

#### 一款基于网易云音乐UI，使用GankIo及豆瓣api开发的符合Google Material Desgin阅读类的开源项目。现主要包括：干货区、电影区和书籍区。App内图片主要来源于iconfont，一部分来源于网易云音乐。

## 项目

项目采取的是Retrofit + RxJava + MVVM-DataBinding架构开发。全项目使用的是DataBinding，包括RecyclerView的BaseHolder、页面布局等，使用方便简洁，代码结构清晰。开发中所遇到的各种问题已总结在[这里][5]。

## 效果图

<img width="160" height=“274” src="https://github.com/youlookwhat/CloudReader/blob/master/screenshot/page_gank_00.png"></img>
<img width="160" height=“274” src="https://github.com/youlookwhat/CloudReader/blob/master/screenshot/page_gank_01.png"></img>
<img width="160" height=“274” src="https://github.com/youlookwhat/CloudReader/blob/master/screenshot/page_gank_02.png"></img>
<img width="160" height=“274” src="https://github.com/youlookwhat/CloudReader/blob/master/screenshot/page_gank_03.png"></img>
<img width="160" height=“274” src="https://github.com/youlookwhat/CloudReader/blob/master/screenshot/page_gank_04.png"></img>
<img width="160" height=“274” src="https://github.com/youlookwhat/CloudReader/blob/master/screenshot/page_movie_01.png"></img>
<img width="160" height=“274” src="https://github.com/youlookwhat/CloudReader/blob/master/screenshot/page_movie_02.png"></img>
<img width="160" height=“274” src="https://github.com/youlookwhat/CloudReader/blob/master/screenshot/page_movie_03.png"></img>
<img width="160" height=“274” src="https://github.com/youlookwhat/CloudReader/blob/master/screenshot/page_book_01.png"></img>
<img width="160" height=“274” src="https://github.com/youlookwhat/CloudReader/blob/master/screenshot/page_menu_01.png"></img>

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

## 特别鸣谢
 - 我几乎看过了所有关于Android仿网易云音乐的项目与文章，发现大部分做的都不够细致，也没有比较好的内容填充，于是决心自己着手做一个，才有了这个开源项目。这里列出主要参考的内容。
 
 - 感谢[iconfont][6]提供图片资源，项目中的大部分图片皆出于此。

 - 参考项目：[ImitateNetEasyCloud][7]、[banya][8]；主要数据来源：[Gank.Io][9]、[豆瓣Api][10]。

 - 使用到的开源库：[glide][11]、[bottomsheet][12]、[nineoldandroids][13]、[rxandroid][14]等等。

 - 感谢[代码家][15]、[张鸿洋][16]、[drakeet][17]、[yang747046912][18]、[GiitSmile][19]、[forezp][20]等众多开发者贡献的开源项目，让我从中学到了很多!
 
 
## End
最后，如果你觉得不错，对你有帮助，可以帮忙分享给你更多的朋友，这是给我们最大的动力与支持，也可以请我喝杯咖啡（支付宝：770413277@qq.com），也就是所谓的打赏O(∩_∩)O~。同时希望你多多fork，star，follow，我将贡献更多的开源项目。开源使生活更美好！

## About me
 - **QQ：**770413277
 - **简书：**[肖子理](http://www.jianshu.com/users/e43c6e979831/latest_articles)
 - **Email：**770413277@qq.com | jingbin127@gmail.com
 - **Blog：**[http://jingbin.me](http://jingbin.me)
 - **GitHub：**[https://github.com/youlookwhat](https://github.com/youlookwhat)

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

[1]:https://img.shields.io/github/license/HeinrichReimer/material-intro.svg
[2]:https://www.apache.org/licenses/LICENSE-2.0.html
[3]:https://img.shields.io/badge/API-19%2B-blue.svg?style=flat
[4]:https://android-arsenal.com/api?level=19

[5]:http://jingbin.me/2017/11/23/%E5%BC%80%E5%8F%91%E4%B8%AD%E6%89%80%E9%81%87%E9%97%AE%E9%A2%98%E5%BD%92%E7%BA%B3/
[6]:http://www.iconfont.cn/plus
[7]:https://github.com/GiitSmile/ImitateNetEasyCloud
[8]:https://github.com/forezp/banya
[9]:https://gank.io/api
[10]:https://developers.douban.com/wiki/?title=terms
[11]:https://github.com/bumptech/glide
[12]:https://github.com/Flipboard/bottomsheet
[13]:https://github.com/JakeWharton/NineOldAndroids
[14]:https://github.com/ReactiveX/RxAndroid
[15]:https://github.com/daimajia
[16]:https://github.com/hongyangAndroid
[17]:https://github.com/drakeet
[18]:https://github.com/yang747046912
[19]:https://github.com/GiitSmile
[20]:https://github.com/forezp
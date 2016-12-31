# CloudReader

[![Apache License 2.0][1]][2] 
[![API][3]][4]

> Netease cloud music Ui && Retrofit + RxJava + MVVM-databinding && GankIo、Douban Api 


## 项目介绍
一款基于网易云音乐UI，使用GankIo及豆瓣api开发的符合Google Material Desgin阅读类的开源项目。项目采取的是Retrofit + RxJava + MVVM-DataBinding架构开发。全项目使用的是DataBinding，包括RecyclerView的BaseHolder、页面布局等，使用方便简洁，代码结构清晰。现主要包括：干货区、电影区和书籍区。开发中所遇到的各种问题已总结在[这里][5]。

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
> API使用的是动听（轮播图）和代码家的Gank.Io。

- **每日推荐：** 干货集中营推送的每日内容，包括每天一个妹子图，相关Android、IOS等其他干货。每天第12：30之后更新，因为双休不更新所以内容缓存三天网络取不到就取缓存。

- **福利：** 图片使用的是Glide加载，可以点击查看大图，支持双指缩放，一下可查看列表的所有图片，再也不用逐个点击每张图啦。

- **干货订制：** 可以筛选自己喜欢干货的类别，有全部、IOS、App、前端、休息视频和拓展资源。

- **大安卓：** 显示安卓的全部资讯。支持下拉刷新方便查看最新的资源。


### 电影区（豆瓣）
> API是豆瓣提供的，因为限制了每个ip每分钟请求的次数，所以请酌情使用，由此带来的不便请见谅。

 - **电影热映区：** 每日更新，展示最新上映的电影热度排行榜。
 - **豆瓣电影Top250：** 豆瓣高分电影集锦，让你放心找好片~

### 书籍区（豆瓣）
> 使用的是豆瓣的搜索API。更多订制内容由于时间原因第一版还未添加，第二版会加上。

 - **综合：** 检索豆瓣综合类的书籍并展示。
 - **文学：** 检索豆瓣文学类的书籍并展示。
 - **生活：** 检索豆瓣生活类的书籍并展示。

### 抽屉界面
> 完全仿网易云音乐抽屉界面，包括诸多细节如透明标题栏，背景透明度，水波纹颜色等。

 - **项目主页：**展示项目介绍信息，及内容说明，可以分享给你的好友哦。
 - **扫码下载：**扫码即可下载App，帮助您快速试用~
 - **问题反馈：**常见问题归纳，反馈地方，联系方式都在这里哦！
 - **关于云阅：**更新日志在这里可见，主人是利用工作外时间做的，周期较长，满意的小伙伴给个Star吧，这将是我继续迭代的动力，谢谢~


## 功能特性
 - 高仿网易云音乐UI，追求细节，执着于1dp与1px的差距。
 - Material Desgin控件的综合使用，如``CoordinatorLayout``、``AppBarLayout``、``Toolbar``、``FloatingActionButton``、``TabLayout``、水波纹点击效果，等等。
 - 项目``BaseActivity``和``BaseFragment``皆基于``DataBinding``，且使用``fragment``懒加载模式，节省内存资源，代码结构清晰。
 - 加载动画和缓存策略都仿网易云音乐，不可以说一模一样但基本是这个套路。列表使用的是``RecyclerView``基于``DataBinding``的``BaseHolder``,使用方便，高效，简洁。
 - 电影详情页是仿网易云音乐的歌单详情页做的，并封装成基类，方便使用。效果有转场动画，透明状态栏，滑动title渐变色等，几乎和歌单详情页一模一样。
 - 书籍类数据展示使用的是``SwipeRefreshLayout``刷新控件结合``RecyclerView``的方式，支持一般的列表、GridView和瀑布流的上拉加载的使用。书籍详情页类似电影详情页。

## 特别鸣谢
 - 我几乎看过了所有关于Android仿网易云音乐的项目与文章，发现大部分做的都不够细致，也没有比较好的内容填充，于是决心自己着手做一个，才有了这个开源项目。这里列出主要参考的内容。
 
 - 感谢[iconfont][6]提供图片资源，项目中的大部分图片皆出于此，一部分来自网易云音乐。

 - 感谢我们的UI工程师[Sandawang](https://github.com/Sandawang)，图标等部分图片皆由他设计。 

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
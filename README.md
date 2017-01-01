# CloudReader

[![Apache License 2.0][1]][2] 
[![API][3]][4]

> Netease cloud music Ui && Retrofit + RxJava + MVVM-databinding && GankIo、Douban Api 


## Introduce
一款基于网易云音乐UI，使用GankIo及豆瓣api开发的符合Google Material Desgin阅读类的开源项目。项目采取的是Retrofit + RxJava + MVVM-DataBinding架构开发。全项目使用的是DataBinding，包括RecyclerView的BaseHolder、页面布局等，使用方便简洁，代码结构清晰。开发中所遇到的各种问题已总结在[这里][5]。

## 效果图
- **干货**

<img width="160" height=“274” src="https://github.com/youlookwhat/CloudReader/blob/master/file/page_gank_00.png"></img>
<img width="160" height=“274” src="https://github.com/youlookwhat/CloudReader/blob/master/file/page_gank_01.png"></img>
<img width="160" height=“274” src="https://github.com/youlookwhat/CloudReader/blob/master/file/page_gank_02.png"></img>
<img width="160" height=“274” src="https://github.com/youlookwhat/CloudReader/blob/master/file/page_gank_03.png"></img>
<img width="160" height=“274” src="https://github.com/youlookwhat/CloudReader/blob/master/file/page_gank_04.png"></img>

- **电影**

<img width="160" height=“274” src="https://github.com/youlookwhat/CloudReader/blob/master/file/page_movie_01.png"></img>
<img width="160" height=“274” src="https://github.com/youlookwhat/CloudReader/blob/master/file/page_movie_02.png"></img>
<img width="160" height=“274” src="https://github.com/youlookwhat/CloudReader/blob/master/file/page_movie_03.png"></img>

- **书籍**

<img width="160" height=“274” src="https://github.com/youlookwhat/CloudReader/blob/master/file/page_book_01.png"></img>
<img width="160" height=“274” src="https://github.com/youlookwhat/CloudReader/blob/master/file/page_book_02.png"></img>
<img width="160" height=“274” src="https://github.com/youlookwhat/CloudReader/blob/master/file/page_book_03.png"></img>

- **抽屉界面**

<img width="160" height=“274” src="https://github.com/youlookwhat/CloudReader/blob/master/file/page_menu_01.png"></img>
<img width="160" height=“274” src="https://github.com/youlookwhat/CloudReader/blob/master/file/page_menu_02.png"></img>
<img width="160" height=“274” src="https://github.com/youlookwhat/CloudReader/blob/master/file/page_menu_03.png"></img>

- gif演示

<img width="180" height=“300” src="https://github.com/youlookwhat/CloudReader/blob/master/file/cloudreader.gif"></img>


## 特性 Features
 
- 1、基本遵循Google Material Design设计风格。
 　　Basic follow Google Material Design Design style.
- 2、``NavigationView``搭配``DrawerLayout``的具体使用。
 　NavigationView collocation DrawerLayout specific use.
- 3、沉浸式状态栏使用与版本适配。
　　mmersive status bar with version adapter.
- 4、MvvM-DataBing的项目应用。
- 5、RxBus代替EventBus进行组件之间通讯。
- 6、``ToolBar``的使用姿势。
- 7、``Glide``加载监听，获取缓存，圆角图片，高斯模糊。
- 8、水波纹点击效果详细使用与适配。
- 9、``RecyclerView``下拉刷新上拉加载。
- 10、基于``DataBinding``的``Viewolder``。
- 11、基于``DataBinding``的``BaseActivity``和``BaseFragment``。
- 12、``Fragment``懒加载模式。
- 13、高仿网易云音乐歌单详情页。
- 14、``SwipeRefreshLayout``结合``RecyclerView``下拉刷新上拉加载。
- 15、``CoordinatorLayout + Behavior``实现标题栏渐变。
- 16、干货集中营内容与豆瓣电影书籍内容。

## TODO
 - [ ]每日推荐可设置是否显示图片及查看具体某一天的干货信息。
 - [x]每日推荐可调整栏目顺序。
 - 书籍部分增加更多内容并可自由定制。
 - 增加干货、电影及内容的搜索。
 - 查找播放本地音乐。

## statement 声明
感谢网易云音乐App提供参考，本人是网易云音乐的粉丝，使用了其中的部分素材，并非攻击，如构成侵权请及时通知我修改或删除。大部分数据来自于干货集中营和豆瓣APIV2.0，一切数据解释权都归代码家和豆瓣所有。
本项目仅供学习使用，禁止用于商业！



## See Detail
> [戳这里查看详情](https://github.com/youlookwhat/CloudReader/blob/master/file/Introduction.md)

## Thanks
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
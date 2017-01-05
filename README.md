# CloudReader

[![Apache License 2.0][1]][2] 
[![Release Version][30]][31]
[![PRs Welcome][32]][33]
[![API][3]][4]

> Netease cloud music Ui && Retrofit + RxJava + MVVM-databinding && GankIo、Douban Api 


## Introduce
一款基于网易云音乐UI，使用GankIo及豆瓣api开发的符合Google Material Desgin阅读类的开源项目。项目采取的是Retrofit + RxJava + MVVM-DataBinding架构开发。开发中所遇到的各种问题已归纳在[这里][5]。

A netease cloud music based UI, using GankIo and douban API development accord with Google Material Desgin reading class open source projects.Various problems encountered in the development of has been [summed up here][5].

## 效果图
- **部分效果图**

<img width="160" height=“274” src="https://github.com/youlookwhat/CloudReader/blob/master/file/page_gank_00.png"></img>
<img width="160" height=“274” src="https://github.com/youlookwhat/CloudReader/blob/master/file/page_movie_01.png"></img>
<img width="160" height=“274” src="https://github.com/youlookwhat/CloudReader/blob/master/file/page_movie_03.png"></img>
<img width="160" height=“274” src="https://github.com/youlookwhat/CloudReader/blob/master/file/page_book_01.png"></img>
<img width="160" height=“274” src="https://github.com/youlookwhat/CloudReader/blob/master/file/page_menu_01.png"></img>

- **gif演示**

<img width="320" height=“590” src="https://github.com/youlookwhat/CloudReader/blob/master/file/cloudreader.gif"></img>

## Issues 宝贵意见
如果有任何问题，请到github的[issue处][21]写上你不明白的地方，也可以通过下面提供的方式联系我，我会及时给予帮助。另外常见的问题已总结在[这里][22]。

If you have any questions, please write to [the issue][21] of making you don't understand of place, also can contact me through here, I will help them in time.Another common problem is summarized [here][22].

## Features 特性
* 1、基本遵循Google Material Design设计风格。                                    
Basic follow Google Material Design style. 
* 2、干货集中营内容与豆瓣电影书籍内容。                                    
The Content of gank.io and Douban movie,books. 
* 3、高仿网易云音乐歌单详情页。                                    
High imitation netease cloud music playlist page for details. 
* 4、``NavigationView``搭配``DrawerLayout``的具体使用。                                    
NavigationView collocation DrawerLayout specific use.
* 5、沉浸式状态栏使用与版本适配。                                    
Immersive status bar with version adapter.
* 6、MvvM-DataBing的项目应用。                                    
The MvvM-DataBing project application.
* 7、RxBus代替EventBus进行组件之间通讯。                                    
RxBus replace EventBus for communication between components.
* 8、``ToolBar``的全方面使用。                                    
All aspects of the ToolBar.
* 9、``Glide``加载监听，获取缓存，圆角图片，高斯模糊。                                    
Glide load monitoring, access to the cache, the rounded pictures, gaussian blur.
* 10、水波纹点击效果详细使用与适配。                                    
Click the water ripple effect use and adaptation in detail.
* 11、``RecyclerView``下拉刷新，上拉加载。                                    
RecyclerView drop-down refresh and tensile loading.
* 12、基于``DataBinding``的``ViewHolder``。                                    
Based on the DataBinding ViewHolder.
* 13、基于``DataBinding``的``BaseActivity``和``BaseFragment``。                                    
Based on the DataBinding BaseActivity and BaseFragment.
* 14、``Fragment``懒加载模式。                                    
Fragments lazy loading mode.
* 15、``SwipeRefreshLayout``结合``RecyclerView``下拉刷新上拉加载。                                    
SwipeRefreshLayout combining RecyclerView drop-down refresh and on load.
* 16、``CoordinatorLayout``+``Behavior``实现标题栏渐变。                                    
CoordinatorLayout add behaviors to achieve the title bar gradient.
* 17、``NestedScrollView ``嵌套``RecyclerView ``的使用。                                    
NestedScrollView nested RecyclerView for specific use.


## TODO
* 每日推荐可设置是否显示图片及查看具体某一天的干货信息。                                    
Recommended daily can set whether to display images and view specific one day dry cargo information.
* 每日推荐可调整栏目顺序。                                    
Recommended daily can adjust the column order.
* 书籍部分增加更多内容并可自由定制。                                    
And can customize free books section to add more content.
* 增加搜索功能。                                    
Increase the search function.
* 网页增加分享功能。                                    
WebView increase share function.


## See Detail
> [项目介绍详情](https://github.com/youlookwhat/CloudReader/blob/master/file/Introduction.md) | [细节优化详情](https://github.com/youlookwhat/CloudReader/wiki)

### Download
　[火速跳转](https://fir.im/cloudreader)                                    
　　<img width="200" height=“200” src="https://github.com/youlookwhat/CloudReader/blob/master/file/download.png"></img>
　　
## Thanks
 - 我几乎看过了所有关于Android仿网易云音乐的项目与文章，发现大部分做的都不够细致，也没有比较好的内容填充，于是决心自己着手做一个，才有了这个开源项目。这里列出主要参考的内容。
 
 - 感谢[iconfont][6]提供图片资源，项目中的大部分图片皆出于此，一部分来自网易云音乐。

 - 感谢我们的UI工程师[Sandawang](https://github.com/Sandawang)，图标等部分图片皆由他设计。 

 - 参考项目：[ImitateNetEasyCloud][7]、[banya][8]；主要数据来源：[Gank.Io][9]、[豆瓣Api][10]。

 - 使用到的开源库：[glide][11]、[bottomsheet][12]、[nineoldandroids][13]、[rxandroid][14]等等。

 - 感谢[代码家][15]、[张鸿洋][16]、[drakeet][17]、[yang747046912][18]、[GiitSmile][19]、[forezp][20]等众多开发者贡献的开源项目，让我从中学到了很多!
 
### Statement
感谢[网易云音乐App](https://play.google.com/store/apps/details?id=com.netease.cloudmusic)提供参考，附上[《网易云音乐Android 3.0视觉设计规范文档》](http://www.25xt.com/appdesign/12385.html)。本人是网易云音乐的粉丝，使用了其中的部分素材，并非攻击，如构成侵权请及时通知我修改或删除。大部分数据来自于干货集中营和豆瓣APIV2.0，一切数据解释权都归代码家和豆瓣所有。
 
## End
最后，如果你觉得不错，对你有帮助，可以帮忙分享给你更多的朋友，这是给我们最大的动力与支持，也可以请我喝杯咖啡（支付宝：770413277@qq.com），也就是所谓的打赏O(∩_∩)O~。同时希望你多多fork，star，follow，我将贡献更多的开源项目。开源使生活更美好！

## About me
 - **QQ：**770413277
 - **简书：**[Jingbin_](http://www.jianshu.com/users/e43c6e979831/latest_articles)
 - **Blog：**[http://jingbin.me](http://jingbin.me)
 - **Email：**jingbin127@163.com

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

[1]:https://img.shields.io/:license-apache-blue.svg
[2]:https://www.apache.org/licenses/LICENSE-2.0.html
[3]:https://img.shields.io/badge/API-19%2B-red.svg?style=flat
[4]:https://android-arsenal.com/api?level=19
[30]:https://img.shields.io/badge/release-1.0-red.svg
[31]:https://github.com/youlookwhat/CloudReader/releases
[32]:https://img.shields.io/badge/PRs-welcome-brightgreen.svg
[33]:https://github.com/youlookwhat/CloudReader/pulls

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

[21]:https://github.com/youlookwhat/CloudReader/issues
[22]:http://jingbin.me/2016/12/25/%E5%B8%B8%E8%A7%81%E9%97%AE%E9%A2%98-%E4%BA%91%E9%98%85/

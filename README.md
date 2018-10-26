# CloudReader

[![Apache License 2.0][1]][2]
[![fir.im][34]][35] 
[![Release Version][30]][31]
[![API][3]][4]
[![PRs Welcome][32]][33]

> Netease cloud music Ui && Retrofit + RxJava + MVVM-databinding && wanandroid、Gank.Io、Douban Api 


## Introduce
一款基于网易云音乐UI，使用GankIo及豆瓣api开发的符合Google Material Desgin阅读类的开源项目。项目采取的是Retrofit + RxJava + MVVM-DataBinding架构开发。

A netease cloud music based UI, using wanandroid、Gank.Io and douban API development accord with Google Material Desgin reading class open source projects.

## Screenshots

<img width="173" height=“274” src="https://github.com/youlookwhat/CloudReader/blob/master/file/page_book_01.png"></img>
<img width="173" height=“274” src="https://github.com/youlookwhat/CloudReader/blob/master/file/page_gank_00.png"></img>
<img width="173" height=“274” src="https://github.com/youlookwhat/CloudReader/blob/master/file/page_movie_01.png"></img>
<img width="173" height=“274” src="https://github.com/youlookwhat/CloudReader/blob/master/file/page_menu_01.png"></img>
<img width="173" height=“274” src="https://github.com/youlookwhat/CloudReader/blob/master/file/page_movie_03.png"></img>

#### Gif Preview

<img width="320" height=“590” src="https://github.com/youlookwhat/CloudReader/blob/master/file/cloudreader.gif"></img>

## Issues 宝贵意见
如果有任何问题，请到github的[issue处][21]写上你不明白的地方，也可以通过下面提供的方式联系我，我会及时给予帮助。

If you have any questions, please write to [the issue][21] of making you don't understand of place, also can contact me through here, I will help them in time.

## Features 特性
* 1、基本遵循Google Material Design设计风格。                                    
* 2、玩安卓、干货集中营与豆瓣电影书籍内容。                                    
* 3、MvvM-DataBinding的项目应用。                                    
* 4、``NavigationView``搭配``DrawerLayout``的具体使用。                                    
* 5、透明状态栏使用与版本适配。                                    
* 6、高仿网易云音乐歌单详情页。                                    
* 7、RxBus代替EventBus进行组件之间通讯。                                    
* 8、``ToolBar``的全方面使用。                                    
* 9、``Glide``加载监听，获取缓存，圆角图片，高斯模糊。                                    
* 10、水波纹点击效果详细使用与适配。                                    
* 11、``Room``的基本使用。
* 12、基于``DataBinding``的``ViewHolder``。                                    
* 13、基于``DataBinding``的``BaseActivity``和``BaseFragment``。                                    
* 14、``Fragment``懒加载模式。                                    
* 15、``SwipeRefreshLayout``结合``RecyclerView``下拉刷新上拉加载。                                    
* 16、``CoordinatorLayout``+``Behavior``实现标题栏渐变。                                                                       


## See Detail
> [项目介绍详情](https://github.com/youlookwhat/CloudReader/blob/master/file/Introduction.md) | [细节优化详情](https://github.com/youlookwhat/CloudReader/wiki) | [**版本更新详情(V2.0)**](http://jingbin.me/2016/12/30/%E6%9B%B4%E6%96%B0%E6%97%A5%E5%BF%97-%E4%BA%91%E9%98%85/)

### Download
　[火速跳转](https://fir.im/cloudreader)                                    
　　<img width="200" height=“200” src="https://github.com/youlookwhat/CloudReader/blob/master/file/download.png"></img>
　　
### Documents 
 - [《云阅》一个仿网易云音乐UI，使用Gank.Io及豆瓣Api开发的开源项目][23]
 - [开发中所遇问题归纳（jar包的具体使用等）][24]
 - [App使用中的常见问题][25]
 - [Data Binding 用户指南（Android）][26]
　

### Version
#### V2.0（10-26）
 - 1、[新增] 知识体系页面
 - 2、[新增] 导航数据页面
 - 3、[新增] 收藏网址功能
 - 4、[更改] 固定图片链接替换
 - 5、[更改] 暂时将书籍/段子页面移入我的收藏里
 - 6、[修复] 段子头像显示不全修复
 - 7、[优化] 首页ui及部分图片显示比例优化

#### V1.9.4（05-21）
 - 1、[修复] 修复干货订制点击“选择分类”崩溃的bug
 - 2、[新增] 干货订制页面长按item增加[水波纹扩散效果](https://jingbin.me/2018/05/20/Android%20%E6%B0%B4%E6%B3%A2%E7%BA%B9%E6%95%88%E6%9E%9C%E7%9A%84%E6%8E%A2%E7%A9%B6/)

#### V1.9.3（05-18）
 - 1、[新增] 玩安卓模块增加登录与收藏文章的功能
 - 2、[新增] 增加Room的使用
 - 3、[修复] 修复7.0以上系统主页显示错乱的bug
 - 4、[完善] 性能优化之[布局大幅优化](https://jingbin.me/2018/05/17/%E6%80%A7%E8%83%BD%E4%BC%98%E5%8C%96%E4%B9%8B%E5%B8%83%E5%B1%80%E4%BC%98%E5%8C%96%E8%AE%B0%E5%BD%95/)
 - 5、[完善] 完善体验修复若干bug

<!-- 
#### V1.9.2（05-06）
 - 1、[完善] 主页显示结构修改
 - 2、[修复] 去掉内涵段子数据
 - 3、[修复] 解决“项目主页”页面两个布局的问题
 - 4、[完善] 布局优化及解决部分bug

#### V1.9.1（02-27）
 - 1、[新增] 新增段子页面，长按条目可选择复制或分享

#### V1.9.0（02-08）
 - 1、首页大幅调整，增加进入玩安卓页面和Trending页面的入口
 - 2、新增鸿洋玩安卓模块(部分功能)
 - 3、优化WebView网页显示，解决大部分问题,[详细](https://github.com/youlookwhat/WebViewStudy)
 - 4、尽可能规范MvvM框架，后期会慢慢调整
 - 5、使用fir.im更新接口，完善更新功能

#### V1.8.3（11-14）
 - 1、[修复] 已选择分类退出App再次进入,type失效的问题
 - 2、[修复] 跳转B站视频显示网页错误的问题

#### V1.8.2（3-30）
 - 1、[修复] 首页轮播图显示异常问题
 - 2、[修复] 干货订制页，刷新内容到底无内容后切换到其他类别，无法上拉加载的情况

#### V1.8.1（3-7）
 - 1、[修复]修复首页有六个以上item时，图片显示不出的bug

#### V1.8.0（3-3）
 - 1、[新增] 侧边栏增加“**登录GitHub账号**”和“**退出应用**”功能
 - 2、[新增] WebView新增“**分享到**”、“**复制链接**”、“**打开链接**”功能
 - 3、[新增] 使其系统更改字体无效
 - 4、[改进] 网络请求更换成``retrofit 2.x``
 - 5、[改进] 首页轮播图更换显示链接
 - 6、[优化] 进入主界面动画优化
 - 7、[优化] 兼容至7.0、升级关联库和代码优化等

#### V1.5.0（1-29）
 - 1、**App体积大小从16M降到5.8M！**
 - 2、将App里固定的图片以移动到七牛。
 - 3、更改项目主页透明状态栏显示方式。
 - 4、更改每日推荐图片显示规则，使其不重复显示。
 - 5、更换过渡图图片。
 - 6、代码优化；删除多余资源。

#### V1.2.0（1-18）
 - 1、更改每日推荐逻辑，使其一定有数据
 - 2、干货集中营的item改为CardView展示
 - 3、代码优化

#### V1.1.0（1-15）

 - 1.书籍详情页面增加自定义元素共享切换动画，并可简单添加需要支持的详情页
 - 2.代码优化
 - 3.其他
　　
-->


## Thanks
 - 我几乎看过了所有关于Android仿网易云音乐的项目与文章，发现大部分做的都不够细致，也没有比较好的内容填充，于是决心自己着手做一个，才有了这个开源项目。这里列出主要参考的内容。
 
 - 图片来源：[iconfont][6]、UI工程师[Sandawang](https://github.com/Sandawang)和网易云音乐App。

 - 主要数据来源：[Gank.Io][9]、[豆瓣Api][10]、[wanandroid][27]。

 - 使用到的开源库：[glide][11]、[bottomsheet][12]、[nineoldandroids][13]、[rxandroid][14]等等。

 
### Statement
感谢[网易云音乐App](https://play.google.com/store/apps/details?id=com.netease.cloudmusic)提供参考，附上[《网易云音乐Android 3.0视觉设计规范文档》](http://www.25xt.com/appdesign/12385.html)。本人是网易云音乐的粉丝，使用了其中的部分素材，并非攻击，如构成侵权请及时通知我修改或删除。大部分数据来自于干货集中营和豆瓣APIV2.0，一切数据解释权都归代码家和豆瓣所有。
 
## End
> 注意：此开源项目仅做学习交流使用，如用到实际项目还需多考虑其他因素如并发等，请多多斟酌。如果你觉得不错，对你有帮助，欢迎点个star，follow，也可以帮忙分享给你更多的朋友，或给点赞赏，这是给我们最大的动力与支持。
> 
> 捐赠后均会被记录到列表中，可以留下友情链接，起到一定的推广作用。感谢大家的信任，[捐赠列表](https://github.com/youlookwhat/CloudReader/blob/master/file/admire.md)。

<img width="200" height=“200” src="https://github.com/youlookwhat/CloudReader/blob/master/file/Wechat-admire.jpeg"></img>

## About me
 - **QQ：** 770413277
 - **简书：**[Jingbin_](http://www.jianshu.com/users/e43c6e979831/latest_articles)
 - **Blog：**[http://jingbin.me](http://jingbin.me)
 - **Email：** jingbin127@163.com

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
[30]:https://img.shields.io/badge/release-2.0-red.svg
[31]:https://github.com/youlookwhat/CloudReader/releases
[32]:https://img.shields.io/badge/PRs-welcome-brightgreen.svg
[33]:https://github.com/youlookwhat/CloudReader/pulls
[34]:https://img.shields.io/badge/download-fir.im-blue.svg
[35]:https://fir.im/cloudreader

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

[21]:https://github.com/youlookwhat/CloudReader/issues

[23]:http://www.jianshu.com/p/69a229fb6e1d
[24]:http://jingbin.me/2017/11/23/%E5%BC%80%E5%8F%91%E4%B8%AD%E6%89%80%E9%81%87%E9%97%AE%E9%A2%98%E5%BD%92%E7%BA%B3/
[25]:http://jingbin.me/2016/12/25/%E5%B8%B8%E8%A7%81%E9%97%AE%E9%A2%98-%E4%BA%91%E9%98%85/
[26]:https://segmentfault.com/a/1190000002876984#articleHeader21
[27]:http://www.wanandroid.com/index
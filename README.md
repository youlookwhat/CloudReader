# CloudReader

[![Apache License 2.0][1]][2]
[![coolapk.com][34]][35] 
[![Release Version][30]][31]
[![API][3]][4]
[![Codacy Badge][36]][37]


> Netease cloud music Ui && Retrofit2 + RxJava2 + MVVM-databinding && wanandroid、Gank.Io、mtime Api 


## Introduce
一款基于网易云音乐UI，使用wanandroid、GankIo及时光网api开发的符合Google Material Desgin阅读类的开源项目。项目采取的是Retrofit2 + RxJava2 + MVVM-DataBinding架构开发。

A netease cloud music based UI, using wanandroid、Gank.Io and mtime API development accord with Google Material Desgin reading class open source projects.

## Screenshots

![](https://raw.githubusercontent.com/youlookwhat/CloudReader/master/file/images.png)
![](https://raw.githubusercontent.com/youlookwhat/CloudReader/master/file/images2.png)

#### Gif Preview

![](https://raw.githubusercontent.com/youlookwhat/CloudReader/master/file/cloudreader.gif)

## Issues 宝贵意见
如果有任何问题，请到github的[issue处][21]写上你不明白的地方，也可以通过下面提供的方式联系我，我会及时给予帮助。

If you have any questions, please write to [the issue][21] of making you don't understand of place, also can contact me through here, I will help them in time.

## Features 特性
* 1、基本遵循Google Material Design设计风格。                                    
* 2、玩安卓、干货集中营与时光网电影内容。
* 3、MvvM-DataBinding的项目应用。                                    
* 4、`NavigationView`搭配`DrawerLayout`的具体使用。                                    
* 5、透明状态栏使用与版本适配。                                    
* 6、高仿网易云音乐歌单详情页。                                    
* 7、RxBus代替EventBus进行组件之间通讯。                                    
* 8、`ToolBar`的全方面使用。                                    
* 9、`Glide`加载监听，获取缓存，圆形图片，高斯模糊。                                    
* 10、水波纹点击效果详细使用与适配。                                    
* 11、`Room`的基本使用。
* 12、基于`DataBinding`的`ViewHolder`。                                    
* 13、基于`DataBinding`的`BaseActivity`和`BaseFragment`。                                    
* 14、`Fragment`懒加载模式。                                    
* 15、`SwipeRefreshLayout`结合`RecyclerView`下拉刷新上拉加载。                                    
* 16、`CoordinatorLayout`+`Behavior`实现标题栏渐变和滑动置顶。                                                                       


## See Detail
> [项目介绍详情](https://github.com/youlookwhat/CloudReader/wiki/Introduction) | [细节优化详情](https://github.com/youlookwhat/CloudReader/wiki) | 🚀[**版本更新详情(V3.4.3)**](https://github.com/youlookwhat/CloudReader/wiki/Update-log)

### Download
　[酷安下载](https://www.coolapk.com/apk/127875)


　  ![](https://raw.githubusercontent.com/youlookwhat/CloudReader/master/file/download_200.png)
　　
### Documents 
 - [《云阅》一个仿网易云音乐UI，使用Gank.Io及豆瓣Api开发的开源项目][23]
 - [开发中所遇问题归纳（jar包的具体使用等）][24]
 - [App使用中的常见问题][25]
 - [开源项目CloudReader笔记][26]
 - [ByRecyclerView - 下拉刷新、加载更多、item点击/长按、万能分割线、粘性Header等][28]
　

### Version
#### V3.4.3（2020-11-16）
 - 1、[新增] 去酷安应用市场评星
 - 2、[新增] WebView拦截简书直接打开App
 - 3、[修复] 即将上映电影显示问题

#### V3.4.2（2020-08-25）
 - 1、[优化] 首页和干货页使用骨架图
 - 2、[优化] 电影页Api替换
 - 3、[优化] 代码整理 AndroidX / bymvvm / ByWebView

#### V3.4.0（2020-05-20）
 - 1、[替换] 干货集中营Api更新
 - 2、[优化] 体系页面选择类别优化
 - 3、[优化] 首页、福利等页面Ui优化

#### V3.3.0（2020-02-29）
 - 1、[新增] 公众号页面改为发现页面，并可订制
 - 2、[优化] 导航页面增加滑动置顶
 - 3、[优化] 启动图、代码等部分优化

#### V3.2.0（2019-12-16）
 - 1、[新增] 热搜词等tag处使用[圆角有色水波纹](https://github.com/youlookwhat/CloudReader/blob/master/app/src/main/res/drawable-v21/shape_ripple_tag_bg.xml)点击效果
 - 2、[优化] 知识体系页面恢复及优化
 - 3、[优化] banner和ByRecyclerView版本升级优化

#### V3.1.0（2019-11-15）
 - 1、[优化] 将所有列表展示的地方替换为[ByRecyclerView][28]
 - 2、[优化] WebView进度条替换为[WebProgress，使其加载进度平滑过渡。](https://github.com/youlookwhat/WebProgress)
 - 3、[优化] 我的收藏-网址内容倒序显示
 - 4、[修复] 修复了4.4系统状态栏适配问题

#### V3.0.0（2019-09-30）
 - 1、[新增] 首页增加公众号栏目
 - 2、[新增] 玩安卓增加积分系统模块
 - 3、[新增] 赞赏云阅页面(每月后7天开启)
 - 4、[新增] 电影详情剧照查看大图使用转场动画
 - 5、[修复] 部分文章作者不显示修正
 - 6、[优化] 屏蔽网页里的广告
 - 7、[优化] 更改收藏图标等ui优化

#### V2.9.5（2019-07-18）
 - 1、[新增] 首页增加项目分类api
 - 2、[新增] 增加历史搜索记录
 - 3、[优化] 知识体系页ui改版
 - 4、[优化] 导航数据页ui改版
 - 5、[优化] 统一TabLayout指示器样式

#### V2.9.1（2019-06-05）
 - 1、[新增] BaseViewModel释放资源
 - 2、[修复] 适配Android9.0不允许http连接的问题
 - 3、[优化] 电影详情页等其他页面ui优化

#### V2.9.0（2019-05-16）
 - 1、[新增] 时光网电影模块
 - 2、[修复] 隐藏豆瓣电影页面(api失效)
 - 3、[修复] 代码优化及修改部分bug

#### V2.8.5（2019-05-06）
 - 1、[新增] 问题反馈内加入QQ群
 - 2、[升级] glide升级到4.9.0
 - 3、[优化] ui部分优化
 - 4、[修复] 修复若干bug

#### V2.8.0（2019-03-21）
 - 1、[新增] 搜索页面
 - 2、[修复] 隐藏书籍分类页面(api失效)
 - 3、[修复] 修复收藏页面星星未选中的bug

#### V2.7.5（2019-03-13）
 - 1、[升级] 玩android接口升级为https
 - 2、[升级] RxJava2.x升级
 - 3、[优化] 设置不能修改字体
 - 4、[优化] 优化导航数据等页面
 - 5、[修复] 代码优化及修改部分bug

#### V2.7.0（2019-02-14）
 - 1、[新增] 知识体系详情页
 - 2、[优化] 项目主页优化

#### V2.6.0（2019-01-16）
 - 1、[新增] 豆瓣即将上映电影数据
 - 2、[优化] 启动白屏问题优化
 - 3、[优化] RecyclerView列表加载优化
 - 4、[修复] 修复无邮箱应用时点击跳转崩溃问题

<!-- 
#### V2.5.0（12-28）
 - 1、[规范] 所有页面mvvm架构修正
 - 2、[优化] 将电影和书籍放置在一块
 - 3、[新增] 可[作为三方浏览器打开外部链接](https://www.jianshu.com/p/272bfb6c0779)
 - 4、[修复] 修复部分兼容性bug

#### V2.2（12-05）
 - 1、[新增] 进入首页可以获取剪切板的链接
 - 2、[规范] 玩安卓模块mvvm修正
 - 3、[优化] 导航数据界面显示优化
 - 4、[优化] 电影、书籍和玩安卓首页等item点击效果
 - 5、[修复] 保存图片权限问题、上拉加载逻辑问题

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
 - 2、[新增] 干货订制页面长按item增加[水波纹扩散效果](https://jinbeen.com/2018/05/20/Android%20%E6%B0%B4%E6%B3%A2%E7%BA%B9%E6%95%88%E6%9E%9C%E7%9A%84%E6%8E%A2%E7%A9%B6/)

#### V1.9.3（05-18）
 - 1、[新增] 玩安卓模块增加登录与收藏文章的功能
 - 2、[新增] 增加Room的使用
 - 3、[修复] 修复7.0以上系统主页显示错乱的bug
 - 4、[完善] 性能优化之[布局大幅优化](https://jinbeen.com/2018/05/17/%E6%80%A7%E8%83%BD%E4%BC%98%E5%8C%96%E4%B9%8B%E5%B8%83%E5%B1%80%E4%BC%98%E5%8C%96%E8%AE%B0%E5%BD%95/)
 - 5、[完善] 完善体验修复若干bug

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

 - 主要数据来源：[wanandroid][27]、[Gank.Io][9]、[时光网][10]。

 - 使用到的开源库：[glide][11]、[bottomsheet][12]、[nineoldandroids][13]、[rxandroid][14]等等。

 
### Statement
感谢[网易云音乐App](https://play.google.com/store/apps/details?id=com.netease.cloudmusic)提供参考，附上[《网易云音乐Android 3.0视觉设计规范文档》](http://www.25xt.com/appdesign/12385.html)。本人是网易云音乐的粉丝，使用了其中的部分素材，并非攻击，如构成侵权请及时通知我修改或删除。大部分数据来自于玩安卓、干货集中营和时光网，一切数据解释权都归张鸿洋、代码家和时光网所有。
 
## End
> 注意：此开源项目仅做学习交流使用，如用到实际项目还需多考虑其他因素，请多多斟酌。如果你觉得不错，对你有帮助，欢迎点个star，follow，也可以帮忙分享给你更多的朋友，或给点赞赏，这是给我们最大的动力与支持。
> 
> 捐赠后均会被记录到列表中，可以留下友情链接，起到一定的推广作用。感谢大家的信任，[捐赠列表](https://github.com/youlookwhat/CloudReader/blob/master/file/admire.md)。


<img src="https://raw.githubusercontent.com/youlookwhat/CloudReader/master/file/Wechat-admire.jpg" width="250" height="250" /><img src="https://raw.githubusercontent.com/youlookwhat/CloudReader/master/file/alipay-admire.jpg" width="250" height="250"/>
    
<!--<center class="half">-->
<!--</center>-->

<!--![](https://raw.githubusercontent.com/youlookwhat/CloudReader/master/file/Wechat-admire.jpg)![](https://raw.githubusercontent.com/youlookwhat/CloudReader/master/file/alipay-admire.jpg)-->

<!--<img width="200" height=“200” src="https://github.com/youlookwhat/CloudReader/blob/master/file/Wechat-admire.jpeg"></img><img width="200" height=“200” src="https://github.com/youlookwhat/CloudReader/blob/master/file/qq_code.JPG"></img>-->

## About me
 - **QQ：** 770413277
 - **简书：**[Jinbeen](https://www.jianshu.com/u/e43c6e979831)
 - **Blog：**[http://jinbeen.com](http://jinbeen.com)
 - **Email：** jingbin127@163.com
 - **QQ交流群：**[![](https://img.shields.io/badge/%E7%BE%A4%E5%8F%B7-727379132-orange.svg?style=flat-square)](https://shang.qq.com/wpa/qunwpa?idkey=5685061359b0a767674cd831d8261d36b347bde04cc23746cb6570e09ee5c8aa)

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
[30]:https://img.shields.io/badge/release-3.4.3-red.svg
[31]:https://github.com/youlookwhat/CloudReader/releases
[32]:https://img.shields.io/badge/PRs-welcome-brightgreen.svg
[33]:https://github.com/youlookwhat/CloudReader/pulls
[34]:https://img.shields.io/badge/download-coolapk.com-blue.svg
[35]:https://www.coolapk.com/apk/127875
[36]:https://api.codacy.com/project/badge/Grade/1045e902e1294badaf783a640869c208?isInternal=true
[37]:https://app.codacy.com/manual/youlookwhat/CloudReader/dashboard?bid=14239237

[5]:http://jinbeen.com/2017/11/23/%E5%BC%80%E5%8F%91%E4%B8%AD%E6%89%80%E9%81%87%E9%97%AE%E9%A2%98%E5%BD%92%E7%BA%B3/
[6]:http://www.iconfont.cn/plus
[7]:https://github.com/GiitSmile/ImitateNetEasyCloud
[8]:https://github.com/forezp/banya
[9]:https://gank.io/api
[10]:http://www.mtime.com
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
[24]:http://jinbeen.com/2017/11/23/%E5%BC%80%E5%8F%91%E4%B8%AD%E6%89%80%E9%81%87%E9%97%AE%E9%A2%98%E5%BD%92%E7%BA%B3/
[25]:http://jinbeen.com/2016/12/25/%E5%B8%B8%E8%A7%81%E9%97%AE%E9%A2%98-%E4%BA%91%E9%98%85/
[26]:https://blog.csdn.net/jingbin_/column/info/34963
[27]:http://www.wanandroid.com/index
[28]:https://github.com/youlookwhat/ByRecyclerView
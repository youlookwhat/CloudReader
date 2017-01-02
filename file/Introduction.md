<!--<div align=center><img width="900" height=“170” src="https://github.com/youlookwhat/CloudReader/blob/master/file/title.png""/></div>-->

## Detail Introduction
　　网易云音乐于2013年4月23日正式发布，是一款主打发现和分享，带有浓厚社交基因的网络音乐产品。相信用过的人都知道它给人的体验是极好的，我看过了绝大多数仿写的案例，基本UI都不够细致，于是决定自己动手写一个，起初也不知道具体它是怎么布局的，后来使用SDK提供的工具``uiautomatorviewer``慢慢分析后再逐渐完善的，争取效果一致~ 
　　
<!--## 效果图
- **干货**

<img src="https://github.com/youlookwhat/CloudReader/blob/master/file/pic_gank.png"></img>

- **电影**

<img src="https://github.com/youlookwhat/CloudReader/blob/master/file/pic_movie.png"></img>

- **书籍**

<img src="https://github.com/youlookwhat/CloudReader/blob/master/file/pic_book.png"></img>

- **抽屉界面**

<img src="https://github.com/youlookwhat/CloudReader/blob/master/file/pic_menu.png"></img>
　　-->
　　
## Module analysis
### 干货区（gank.io）
> API使用的是动听（轮播图）和代码家的Gank.Io。

- **每日推荐：** 干货集中营推送的每日内容，包括每天一个妹子图，相关Android、IOS等其他干货。每天第12：30之后更新，因为双休不更新所以内容缓存三天网络取不到就取缓存。

- **福利：** Glide加载图片，点击查看大图，支持双指缩放，一下可查看列表的所有图片，再也不用逐个点击每张图啦。

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


## What can be learned about this project 
从本项目中你能学到哪些知识呢？

- 1、干货集中营内容与豆瓣电影书籍内容。
- 2、``NavigationView``搭配``DrawerLayout``的具体使用。
- 3、沉浸式状态栏使用与版本适配。
- 4、MvvM-DataBing的项目应用。
- 5、RxBus代替EventBus进行组件之间通讯。
- 6、``ToolBar``的使用姿势。
- 7、``Glide``加载监听，获取缓存，圆角图片，高斯模糊。
- 8、水波纹点击效果详细使用与适配。
- 9、``RecyclerView``下拉刷新上拉加载。
- 10、基于``DataBinding``的``ViewHolder``。
- 11、基于``DataBinding``的``BaseActivity``和``BaseFragment``。
- 12、``Fragment``懒加载模式。
- 13、高仿网易云音乐歌单详情页。
- 14、``SwipeRefreshLayout``结合``RecyclerView``下拉刷新上拉加载。
- 15、``CoordinatorLayout + Behavior``实现标题栏渐变。
- 16、``NestedScrollView``嵌套``RecyclerView``的使用。

## End
　　希望本项目对你有所帮助，同时有什么问题可以随时反馈哦~
　　

　　２０１７　元࿐旦࿐快࿐樂࿐



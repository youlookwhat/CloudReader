package com.example.jingbin.cloudreader.bean.wanandroid;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.example.jingbin.cloudreader.BR;

import java.util.List;

/**
 * @author jingbin
 * @data 2018/9/15
 * @description
 */

public class TreeBean extends BaseObservable {


    /**
     * data : [{"children":[{"children":[],"courseId":13,"id":60,"name":"Android Studio相关","order":1000,"parentChapterId":150,"visible":1},{"children":[],"courseId":13,"id":169,"name":"gradle","order":1001,"parentChapterId":150,"visible":1},{"children":[],"courseId":13,"id":269,"name":"官方发布","order":1002,"parentChapterId":150,"visible":1}],"courseId":13,"id":150,"name":"开发环境","order":1,"parentChapterId":0,"visible":1},{"children":[{"children":[],"courseId":13,"id":168,"name":"Drawable","order":5000,"parentChapterId":167,"visible":1},{"children":[],"courseId":13,"id":172,"name":"deep link","order":5001,"parentChapterId":167,"visible":1},{"children":[],"courseId":13,"id":198,"name":"基础概念","order":5002,"parentChapterId":167,"visible":1},{"children":[],"courseId":13,"id":224,"name":"adb","order":5003,"parentChapterId":167,"visible":1},{"children":[],"courseId":13,"id":240,"name":"字符编码","order":5004,"parentChapterId":167,"visible":1},{"children":[],"courseId":13,"id":241,"name":"线程池","order":5005,"parentChapterId":167,"visible":1},{"children":[],"courseId":13,"id":257,"name":"Span","order":5006,"parentChapterId":167,"visible":1},{"children":[],"courseId":13,"id":306,"name":"多线程与并发","order":5007,"parentChapterId":167,"visible":1},{"children":[],"courseId":13,"id":307,"name":"Apk诞生记","order":5008,"parentChapterId":167,"visible":1},{"children":[],"courseId":13,"id":403,"name":"序列化","order":5009,"parentChapterId":167,"visible":1}],"courseId":13,"id":167,"name":"基础知识","order":5,"parentChapterId":0,"visible":1},{"children":[{"children":[],"courseId":13,"id":10,"name":"Activity","order":10000,"parentChapterId":9,"visible":1},{"children":[],"courseId":13,"id":15,"name":"Service","order":10001,"parentChapterId":9,"visible":1},{"children":[],"courseId":13,"id":16,"name":"BroadcastReceiver","order":10002,"parentChapterId":9,"visible":1},{"children":[],"courseId":13,"id":17,"name":"ContentProvider","order":10003,"parentChapterId":9,"visible":1},{"children":[],"courseId":13,"id":19,"name":"Intent","order":10004,"parentChapterId":9,"visible":1},{"children":[],"courseId":13,"id":40,"name":"Context","order":10005,"parentChapterId":9,"visible":1},{"children":[],"courseId":13,"id":267,"name":"handler","order":10006,"parentChapterId":9,"visible":1}],"courseId":13,"id":9,"name":"四大组件","order":10,"parentChapterId":0,"visible":1},{"children":[{"children":[],"courseId":13,"id":26,"name":"基础UI控件","order":15000,"parentChapterId":25,"visible":1},{"children":[],"courseId":13,"id":27,"name":"ListView&GridView","order":15001,"parentChapterId":25,"visible":1},{"children":[],"courseId":13,"id":121,"name":"ViewPager","order":15003,"parentChapterId":25,"visible":1},{"children":[],"courseId":13,"id":124,"name":"Fragment","order":15004,"parentChapterId":25,"visible":1},{"children":[],"courseId":13,"id":259,"name":"ScrollView","order":15005,"parentChapterId":25,"visible":1}],"courseId":13,"id":25,"name":"常用控件","order":15,"parentChapterId":0,"visible":1},{"children":[{"children":[],"courseId":13,"id":30,"name":"Toast","order":20000,"parentChapterId":29,"visible":1},{"children":[],"courseId":13,"id":31,"name":"Dialog","order":20001,"parentChapterId":29,"visible":1},{"children":[],"courseId":13,"id":32,"name":"PopupWindow","order":20002,"parentChapterId":29,"visible":1},{"children":[],"courseId":13,"id":33,"name":"Notification","order":20003,"parentChapterId":29,"visible":1},{"children":[],"courseId":13,"id":190,"name":"震动","order":20004,"parentChapterId":29,"visible":1},{"children":[],"courseId":13,"id":263,"name":"截屏","order":20005,"parentChapterId":29,"visible":1},{"children":[],"courseId":13,"id":341,"name":"键盘","order":20006,"parentChapterId":29,"visible":1}],"courseId":13,"id":29,"name":"用户交互","order":20,"parentChapterId":0,"visible":1},{"children":[{"children":[],"courseId":13,"id":98,"name":"WebView","order":12000,"parentChapterId":34,"visible":1},{"children":[],"courseId":13,"id":67,"name":"网络基础","order":25000,"parentChapterId":34,"visible":1},{"children":[],"courseId":13,"id":68,"name":"volley","order":25001,"parentChapterId":34,"visible":1},{"children":[],"courseId":13,"id":69,"name":"okhttp","order":25002,"parentChapterId":34,"visible":1},{"children":[],"courseId":13,"id":70,"name":"retrofit","order":25003,"parentChapterId":34,"visible":1},{"children":[],"courseId":13,"id":71,"name":"数据解析","order":25004,"parentChapterId":34,"visible":1},{"children":[],"courseId":13,"id":200,"name":"https","order":25005,"parentChapterId":34,"visible":1}],"courseId":13,"id":34,"name":"网络访问","order":25,"parentChapterId":0,"visible":1},{"children":[{"children":[],"courseId":13,"id":87,"name":"图片加载库","order":6000,"parentChapterId":72,"visible":1},{"children":[],"courseId":13,"id":86,"name":"图片处理","order":6000,"parentChapterId":72,"visible":1}],"courseId":13,"id":72,"name":"图片加载","order":30,"parentChapterId":0,"visible":1},{"children":[{"children":[],"courseId":13,"id":89,"name":"app缓存相关","order":7000,"parentChapterId":35,"visible":1},{"children":[],"courseId":13,"id":90,"name":"数据库","order":7000,"parentChapterId":35,"visible":1}],"courseId":13,"id":35,"name":"数据存储","order":35,"parentChapterId":0,"visible":1},{"children":[{"children":[],"courseId":13,"id":91,"name":"常规动画","order":8000,"parentChapterId":36,"visible":1},{"children":[],"courseId":13,"id":188,"name":"转场动画","order":8000,"parentChapterId":36,"visible":1},{"children":[],"courseId":13,"id":92,"name":"属性动画","order":8000,"parentChapterId":36,"visible":1},{"children":[],"courseId":13,"id":238,"name":"其他动画","order":8001,"parentChapterId":36,"visible":1}],"courseId":13,"id":36,"name":"动画效果","order":40,"parentChapterId":0,"visible":1},{"children":[{"children":[],"courseId":13,"id":134,"name":"SurfaceView","order":9000,"parentChapterId":37,"visible":1},{"children":[],"courseId":13,"id":126,"name":"绘图相关","order":9000,"parentChapterId":37,"visible":1},{"children":[],"courseId":13,"id":93,"name":"基础知识","order":9000,"parentChapterId":37,"visible":1},{"children":[],"courseId":13,"id":94,"name":"事件分发","order":9000,"parentChapterId":37,"visible":1},{"children":[],"courseId":13,"id":99,"name":"具体案例","order":9000,"parentChapterId":37,"visible":1}],"courseId":13,"id":37,"name":"自定义控件","order":45,"parentChapterId":0,"visible":1},{"children":[{"children":[],"courseId":13,"id":95,"name":"相机Camera","order":10000,"parentChapterId":38,"visible":1},{"children":[],"courseId":13,"id":97,"name":"音视频","order":10000,"parentChapterId":38,"visible":1},{"children":[],"courseId":13,"id":170,"name":"闹钟","order":10005,"parentChapterId":38,"visible":1}],"courseId":13,"id":38,"name":"多媒体技术","order":50,"parentChapterId":0,"visible":1},{"children":[{"children":[],"courseId":13,"id":54,"name":"新的控件","order":11000,"parentChapterId":39,"visible":1},{"children":[],"courseId":13,"id":179,"name":"Data Binding","order":11000,"parentChapterId":39,"visible":1},{"children":[],"courseId":13,"id":166,"name":"BottomSheet","order":11000,"parentChapterId":39,"visible":1},{"children":[],"courseId":13,"id":165,"name":"Support Annotations","order":11000,"parentChapterId":39,"visible":1},{"children":[],"courseId":13,"id":183,"name":"WebP","order":11000,"parentChapterId":39,"visible":1},{"children":[],"courseId":13,"id":164,"name":"File Provider","order":11000,"parentChapterId":39,"visible":1},{"children":[],"courseId":13,"id":125,"name":"CoordinatorLayout","order":11000,"parentChapterId":39,"visible":1},{"children":[],"courseId":13,"id":151,"name":"Vector","order":11000,"parentChapterId":39,"visible":1},{"children":[],"courseId":13,"id":55,"name":"新的API","order":11000,"parentChapterId":39,"visible":1},{"children":[],"courseId":13,"id":100,"name":"RecyclerView","order":11000,"parentChapterId":39,"visible":1},{"children":[],"courseId":13,"id":192,"name":"权限管理","order":11000,"parentChapterId":39,"visible":1},{"children":[],"courseId":13,"id":142,"name":"ConstraintLayout","order":11000,"parentChapterId":39,"visible":1},{"children":[],"courseId":13,"id":193,"name":"分屏","order":11000,"parentChapterId":39,"visible":1},{"children":[],"courseId":13,"id":140,"name":"dagger2","order":16000,"parentChapterId":39,"visible":1},{"children":[],"courseId":13,"id":329,"name":"Android 8.0","order":16001,"parentChapterId":39,"visible":1},{"children":[],"courseId":13,"id":332,"name":"嵌套滑动","order":16002,"parentChapterId":39,"visible":1},{"children":[],"courseId":13,"id":334,"name":"Architecture Components","order":16003,"parentChapterId":39,"visible":1},{"children":[],"courseId":13,"id":373,"name":"support-design","order":16004,"parentChapterId":39,"visible":1},{"children":[],"courseId":13,"id":406,"name":"Android 9.0","order":16005,"parentChapterId":39,"visible":1}],"courseId":13,"id":39,"name":"5.+高新技术","order":55,"parentChapterId":0,"visible":0},{"children":[{"children":[],"courseId":13,"id":186,"name":"沉浸式","order":12000,"parentChapterId":53,"visible":1},{"children":[],"courseId":13,"id":174,"name":"增量更新","order":12000,"parentChapterId":53,"visible":1},{"children":[],"courseId":13,"id":195,"name":"设计模式","order":12000,"parentChapterId":53,"visible":1},{"children":[],"courseId":13,"id":196,"name":"常见异常","order":12000,"parentChapterId":53,"visible":1},{"children":[],"courseId":13,"id":197,"name":"Native Crash","order":12000,"parentChapterId":53,"visible":1},{"children":[],"courseId":13,"id":185,"name":"组件化","order":12000,"parentChapterId":53,"visible":1},{"children":[],"courseId":13,"id":184,"name":"推送&即时通讯","order":12000,"parentChapterId":53,"visible":1},{"children":[],"courseId":13,"id":80,"name":"Github用法进阶","order":12000,"parentChapterId":53,"visible":1},{"children":[],"courseId":13,"id":61,"name":"Android测试相关","order":12000,"parentChapterId":53,"visible":1},{"children":[],"courseId":13,"id":73,"name":"面试相关","order":12000,"parentChapterId":53,"visible":1},{"children":[],"courseId":13,"id":78,"name":"性能优化","order":12000,"parentChapterId":53,"visible":1},{"children":[],"courseId":13,"id":75,"name":"插件化","order":12000,"parentChapterId":53,"visible":1},{"children":[],"courseId":13,"id":76,"name":"项目架构","order":12000,"parentChapterId":53,"visible":1},{"children":[],"courseId":13,"id":77,"name":"响应式编程","order":12000,"parentChapterId":53,"visible":1},{"children":[],"courseId":13,"id":74,"name":"反编译","order":12000,"parentChapterId":53,"visible":1},{"children":[],"courseId":13,"id":81,"name":"学习的正确姿势","order":12000,"parentChapterId":53,"visible":1},{"children":[],"courseId":13,"id":146,"name":"React Native","order":12000,"parentChapterId":53,"visible":1},{"children":[],"courseId":13,"id":191,"name":"数据采集与埋点","order":12000,"parentChapterId":53,"visible":1},{"children":[],"courseId":13,"id":160,"name":"热修复","order":12000,"parentChapterId":53,"visible":1},{"children":[],"courseId":13,"id":159,"name":"OpenGL ES","order":12000,"parentChapterId":53,"visible":1},{"children":[],"courseId":13,"id":79,"name":"黑科技","order":12000,"parentChapterId":53,"visible":1},{"children":[],"courseId":13,"id":239,"name":"Xposed","order":12001,"parentChapterId":53,"visible":1},{"children":[],"courseId":13,"id":246,"name":"保活","order":12002,"parentChapterId":53,"visible":1},{"children":[],"courseId":13,"id":250,"name":"直播","order":12003,"parentChapterId":53,"visible":1},{"children":[],"courseId":13,"id":251,"name":"OpenCV","order":12004,"parentChapterId":53,"visible":1},{"children":[],"courseId":13,"id":262,"name":"SDK开发","order":12005,"parentChapterId":53,"visible":1},{"children":[],"courseId":13,"id":302,"name":"ANR","order":12006,"parentChapterId":53,"visible":1},{"children":[],"courseId":13,"id":364,"name":"模块化","order":12007,"parentChapterId":53,"visible":0}],"courseId":13,"id":53,"name":"热门专题","order":60,"parentChapterId":0,"visible":0},{"children":[{"children":[],"courseId":13,"id":131,"name":"蓝牙","order":14000,"parentChapterId":127,"visible":1},{"children":[],"courseId":13,"id":132,"name":"传感器","order":14000,"parentChapterId":127,"visible":1},{"children":[],"courseId":13,"id":133,"name":"NFC","order":14000,"parentChapterId":127,"visible":1},{"children":[],"courseId":13,"id":322,"name":"指纹","order":14001,"parentChapterId":127,"visible":1}],"courseId":13,"id":127,"name":"硬件模块","order":70,"parentChapterId":0,"visible":1},{"children":[{"children":[],"courseId":13,"id":182,"name":"JNI编程","order":15000,"parentChapterId":128,"visible":1},{"children":[],"courseId":13,"id":149,"name":"so文件相关","order":15000,"parentChapterId":128,"visible":1}],"courseId":13,"id":128,"name":"JNI","order":75,"parentChapterId":0,"visible":1},{"children":[{"children":[],"courseId":13,"id":136,"name":"地图","order":17000,"parentChapterId":130,"visible":1},{"children":[],"courseId":13,"id":137,"name":"后端云","order":17000,"parentChapterId":130,"visible":1},{"children":[],"courseId":13,"id":138,"name":"推送","order":17000,"parentChapterId":130,"visible":1},{"children":[],"courseId":13,"id":139,"name":"Crash捕获","order":17000,"parentChapterId":130,"visible":1},{"children":[],"courseId":13,"id":223,"name":"ocr&图像识别","order":17001,"parentChapterId":130,"visible":1},{"children":[],"courseId":13,"id":404,"name":"语音","order":17002,"parentChapterId":130,"visible":1}],"courseId":13,"id":130,"name":"常用SDK","order":85,"parentChapterId":0,"visible":1},{"children":[{"children":[],"courseId":13,"id":173,"name":"Choregrapher","order":18000,"parentChapterId":152,"visible":1},{"children":[],"courseId":13,"id":171,"name":"binder","order":18000,"parentChapterId":152,"visible":1},{"children":[],"courseId":13,"id":178,"name":"apk安装","order":18000,"parentChapterId":152,"visible":1},{"children":[],"courseId":13,"id":153,"name":"Zygote进程启动","order":18000,"parentChapterId":152,"visible":1},{"children":[],"courseId":13,"id":155,"name":"SystemServer启动过程","order":18000,"parentChapterId":152,"visible":1},{"children":[],"courseId":13,"id":233,"name":"framework-四大组件","order":18001,"parentChapterId":152,"visible":1}],"courseId":13,"id":152,"name":"framework","order":90,"parentChapterId":0,"visible":1},{"children":[{"children":[],"courseId":13,"id":135,"name":"二维码","order":16000,"parentChapterId":156,"visible":1},{"children":[],"courseId":13,"id":199,"name":"MultiDex 启动优化","order":19000,"parentChapterId":156,"visible":1},{"children":[],"courseId":13,"id":158,"name":"Fragment懒加载","order":19000,"parentChapterId":156,"visible":1},{"children":[],"courseId":13,"id":157,"name":"获取设备唯一标识","order":19000,"parentChapterId":156,"visible":1},{"children":[],"courseId":13,"id":163,"name":"Splash页优化","order":19000,"parentChapterId":156,"visible":1},{"children":[],"courseId":13,"id":222,"name":"持续集成","order":19001,"parentChapterId":156,"visible":1},{"children":[],"courseId":13,"id":228,"name":"辅助 or 工具类","order":19002,"parentChapterId":156,"visible":1},{"children":[],"courseId":13,"id":230,"name":"打包","order":19003,"parentChapterId":156,"visible":1},{"children":[],"courseId":13,"id":237,"name":"下拉刷新&上拉加载","order":19004,"parentChapterId":156,"visible":1},{"children":[],"courseId":13,"id":242,"name":"实用插件","order":19005,"parentChapterId":156,"visible":1},{"children":[],"courseId":13,"id":247,"name":"防逆向","order":19006,"parentChapterId":156,"visible":1},{"children":[],"courseId":13,"id":252,"name":"奇怪的Bug","order":19007,"parentChapterId":156,"visible":1},{"children":[],"courseId":13,"id":258,"name":"三方分享 & 登录","order":19008,"parentChapterId":156,"visible":1},{"children":[],"courseId":13,"id":260,"name":"RxJava & Retrofit & MVP","order":19009,"parentChapterId":156,"visible":1},{"children":[],"courseId":13,"id":261,"name":"屏幕适配","order":19010,"parentChapterId":156,"visible":1},{"children":[],"courseId":13,"id":268,"name":"优秀的设计","order":19011,"parentChapterId":156,"visible":1},{"children":[],"courseId":13,"id":270,"name":"埋点","order":19012,"parentChapterId":156,"visible":1},{"children":[],"courseId":13,"id":295,"name":"混淆","order":19013,"parentChapterId":156,"visible":1},{"children":[],"courseId":13,"id":335,"name":"应用内更新","order":19014,"parentChapterId":156,"visible":1},{"children":[],"courseId":13,"id":345,"name":"国际化","order":19015,"parentChapterId":156,"visible":1},{"children":[],"courseId":13,"id":379,"name":"Android P 适配","order":19016,"parentChapterId":156,"visible":1},{"children":[],"courseId":13,"id":392,"name":"兼容性","order":19017,"parentChapterId":156,"visible":1},{"children":[],"courseId":13,"id":399,"name":"抓包","order":19018,"parentChapterId":156,"visible":1}],"courseId":13,"id":156,"name":"项目必备","order":95,"parentChapterId":0,"visible":1},{"children":[{"children":[],"courseId":13,"id":176,"name":"个人博客","order":20000,"parentChapterId":175,"visible":1},{"children":[],"courseId":13,"id":177,"name":"公司对外","order":20000,"parentChapterId":175,"visible":1}],"courseId":13,"id":175,"name":"推荐网站","order":100,"parentChapterId":0,"visible":1},{"children":[{"children":[],"courseId":13,"id":203,"name":"javapoet","order":21000,"parentChapterId":180,"visible":1},{"children":[],"courseId":13,"id":202,"name":"机器学习&人工智能","order":21000,"parentChapterId":180,"visible":1},{"children":[],"courseId":13,"id":181,"name":"javassist","order":21000,"parentChapterId":180,"visible":1},{"children":[],"courseId":13,"id":243,"name":"JVM","order":21001,"parentChapterId":180,"visible":1},{"children":[],"courseId":13,"id":273,"name":"Android AR","order":21002,"parentChapterId":180,"visible":1},{"children":[],"courseId":13,"id":296,"name":"阅读","order":21003,"parentChapterId":180,"visible":1},{"children":[],"courseId":13,"id":303,"name":"区块链","order":21004,"parentChapterId":180,"visible":1},{"children":[],"courseId":13,"id":318,"name":"搭建博客","order":21005,"parentChapterId":180,"visible":1},{"children":[],"courseId":13,"id":368,"name":"计算机基础","order":21006,"parentChapterId":180,"visible":1}],"courseId":13,"id":180,"name":"延伸技术","order":105,"parentChapterId":0,"visible":1},{"children":[{"children":[],"courseId":13,"id":227,"name":"注解","order":110000,"parentChapterId":225,"visible":1},{"children":[],"courseId":13,"id":229,"name":"AOP","order":110001,"parentChapterId":225,"visible":1},{"children":[],"courseId":13,"id":265,"name":"反射","order":110002,"parentChapterId":225,"visible":1}],"courseId":13,"id":225,"name":"注解 & 反射 & AOP","order":110,"parentChapterId":0,"visible":1},{"children":[{"children":[],"courseId":13,"id":232,"name":"入门及知识点","order":115000,"parentChapterId":231,"visible":1}],"courseId":13,"id":231,"name":"Kotlin","order":115,"parentChapterId":0,"visible":1},{"children":[{"children":[],"courseId":13,"id":245,"name":"集合相关","order":120000,"parentChapterId":244,"visible":1},{"children":[],"courseId":13,"id":304,"name":"基础源码","order":120001,"parentChapterId":244,"visible":1},{"children":[],"courseId":13,"id":308,"name":"多线程","order":120002,"parentChapterId":244,"visible":1},{"children":[],"courseId":13,"id":313,"name":"字节码","order":120003,"parentChapterId":244,"visible":1},{"children":[],"courseId":13,"id":317,"name":"Lambda","order":120004,"parentChapterId":244,"visible":1},{"children":[],"courseId":13,"id":320,"name":"内存管理","order":120005,"parentChapterId":244,"visible":1},{"children":[],"courseId":13,"id":321,"name":"算法","order":120006,"parentChapterId":244,"visible":1},{"children":[],"courseId":13,"id":346,"name":"JVM","order":120007,"parentChapterId":244,"visible":1},{"children":[],"courseId":13,"id":362,"name":"泛型","order":120008,"parentChapterId":244,"visible":1}],"courseId":13,"id":244,"name":"Java深入","order":120,"parentChapterId":0,"visible":1},{"children":[{"children":[],"courseId":13,"id":249,"name":"干货资源","order":125000,"parentChapterId":248,"visible":1},{"children":[],"courseId":13,"id":292,"name":"pdf电子书","order":125001,"parentChapterId":248,"visible":1},{"children":[],"courseId":13,"id":305,"name":"各类工具","order":125002,"parentChapterId":248,"visible":1},{"children":[],"courseId":13,"id":361,"name":"课程推荐","order":125003,"parentChapterId":248,"visible":1}],"courseId":13,"id":248,"name":"干货资源","order":125,"parentChapterId":0,"visible":1},{"children":[{"children":[],"courseId":13,"id":254,"name":"新闻资讯","order":130000,"parentChapterId":253,"visible":1},{"children":[],"courseId":13,"id":255,"name":"工具类","order":130001,"parentChapterId":253,"visible":1},{"children":[],"courseId":13,"id":256,"name":"音乐、视频类","order":130002,"parentChapterId":253,"visible":1}],"courseId":13,"id":253,"name":"完整开源项目","order":130,"parentChapterId":0,"visible":1},{"children":[{"children":[],"courseId":13,"id":272,"name":"常用网站","order":140000,"parentChapterId":271,"visible":1},{"children":[],"courseId":13,"id":274,"name":"个人博客","order":140001,"parentChapterId":271,"visible":1},{"children":[],"courseId":13,"id":280,"name":"开发社区","order":140001,"parentChapterId":271,"visible":1},{"children":[],"courseId":13,"id":281,"name":"公司博客","order":140001,"parentChapterId":271,"visible":1},{"children":[],"courseId":13,"id":275,"name":"常用工具","order":140002,"parentChapterId":271,"visible":1},{"children":[],"courseId":13,"id":393,"name":"查看源码","order":140003,"parentChapterId":271,"visible":1},{"children":[],"courseId":13,"id":276,"name":"在线学习","order":140003,"parentChapterId":271,"visible":1},{"children":[],"courseId":13,"id":277,"name":"开放平台","order":140004,"parentChapterId":271,"visible":1},{"children":[],"courseId":13,"id":278,"name":"互联网资讯","order":140005,"parentChapterId":271,"visible":1},{"children":[],"courseId":13,"id":279,"name":"求职招聘","order":140006,"parentChapterId":271,"visible":1},{"children":[],"courseId":13,"id":282,"name":"应用加固","order":140007,"parentChapterId":271,"visible":1},{"children":[],"courseId":13,"id":283,"name":"三方支付","order":140008,"parentChapterId":271,"visible":1},{"children":[],"courseId":13,"id":284,"name":"推送平台","order":140009,"parentChapterId":271,"visible":1},{"children":[],"courseId":13,"id":285,"name":"三方分享","order":140010,"parentChapterId":271,"visible":1},{"children":[],"courseId":13,"id":286,"name":"地图平台","order":140011,"parentChapterId":271,"visible":1},{"children":[],"courseId":13,"id":287,"name":"直播SDK","order":140012,"parentChapterId":271,"visible":1},{"children":[],"courseId":13,"id":288,"name":"IM即时通讯","order":140013,"parentChapterId":271,"visible":1},{"children":[],"courseId":13,"id":289,"name":"Bug管理","order":140014,"parentChapterId":271,"visible":1},{"children":[],"courseId":13,"id":290,"name":"后端云","order":140015,"parentChapterId":271,"visible":1},{"children":[],"courseId":13,"id":291,"name":"WebView内核","order":140016,"parentChapterId":271,"visible":1},{"children":[],"courseId":13,"id":299,"name":"创意&素材","order":140017,"parentChapterId":271,"visible":1},{"children":[],"courseId":13,"id":300,"name":"互联网统计","order":140018,"parentChapterId":271,"visible":1},{"children":[],"courseId":13,"id":301,"name":"快速开发","order":140019,"parentChapterId":271,"visible":1},{"children":[],"courseId":13,"id":359,"name":"应用发布","order":140020,"parentChapterId":271,"visible":1},{"children":[],"courseId":13,"id":365,"name":"反馈平台","order":140021,"parentChapterId":271,"visible":1},{"children":[],"courseId":13,"id":366,"name":"在线文档","order":140022,"parentChapterId":271,"visible":1},{"children":[],"courseId":13,"id":369,"name":"短视频SDK","order":140023,"parentChapterId":271,"visible":1}],"courseId":13,"id":271,"name":"导航主Tab","order":140,"parentChapterId":0,"visible":1},{"children":[{"children":[],"courseId":13,"id":294,"name":"完整项目","order":145000,"parentChapterId":293,"visible":0},{"children":[],"courseId":13,"id":402,"name":"跨平台应用","order":145001,"parentChapterId":293,"visible":1},{"children":[],"courseId":13,"id":367,"name":"资源聚合类","order":145002,"parentChapterId":293,"visible":1},{"children":[],"courseId":13,"id":323,"name":"动画","order":145003,"parentChapterId":293,"visible":1},{"children":[],"courseId":13,"id":314,"name":"RV列表动效","order":145004,"parentChapterId":293,"visible":1},{"children":[],"courseId":13,"id":358,"name":"项目基础功能","order":145005,"parentChapterId":293,"visible":1},{"children":[],"courseId":13,"id":328,"name":"网络&amp;文件下载","order":145011,"parentChapterId":293,"visible":1},{"children":[],"courseId":13,"id":331,"name":"TextView","order":145013,"parentChapterId":293,"visible":1},{"children":[],"courseId":13,"id":336,"name":"键盘","order":145015,"parentChapterId":293,"visible":1},{"children":[],"courseId":13,"id":337,"name":"快应用","order":145016,"parentChapterId":293,"visible":1},{"children":[],"courseId":13,"id":338,"name":"日历&amp;时钟","order":145017,"parentChapterId":293,"visible":1},{"children":[],"courseId":13,"id":339,"name":"K线图","order":145018,"parentChapterId":293,"visible":1},{"children":[],"courseId":13,"id":340,"name":"硬件相关","order":145019,"parentChapterId":293,"visible":1},{"children":[],"courseId":13,"id":357,"name":"表格类","order":145022,"parentChapterId":293,"visible":1},{"children":[],"courseId":13,"id":363,"name":"创意汇","order":145024,"parentChapterId":293,"visible":1},{"children":[],"courseId":13,"id":380,"name":"ImageView","order":145029,"parentChapterId":293,"visible":1},{"children":[],"courseId":13,"id":382,"name":"音视频&amp;相机","order":145030,"parentChapterId":293,"visible":1},{"children":[],"courseId":13,"id":383,"name":"相机","order":145031,"parentChapterId":293,"visible":1},{"children":[],"courseId":13,"id":310,"name":"下拉刷新","order":145032,"parentChapterId":293,"visible":1},{"children":[],"courseId":13,"id":385,"name":"架构","order":145033,"parentChapterId":293,"visible":1},{"children":[],"courseId":13,"id":387,"name":"对话框","order":145035,"parentChapterId":293,"visible":1},{"children":[],"courseId":13,"id":388,"name":"数据库","order":145036,"parentChapterId":293,"visible":1},{"children":[],"courseId":13,"id":391,"name":"AS插件","order":145037,"parentChapterId":293,"visible":1},{"children":[],"courseId":13,"id":400,"name":"ViewPager","order":145039,"parentChapterId":293,"visible":1},{"children":[],"courseId":13,"id":401,"name":"二维码","order":145040,"parentChapterId":293,"visible":1},{"children":[],"courseId":13,"id":312,"name":"富文本编辑器","order":145041,"parentChapterId":293,"visible":1}],"courseId":13,"id":293,"name":"开源项目主Tab","order":145,"parentChapterId":0,"visible":0},{"children":[{"children":[],"courseId":13,"id":298,"name":"我的博客","order":150000,"parentChapterId":297,"visible":1},{"children":[],"courseId":13,"id":360,"name":"小编发布","order":150001,"parentChapterId":297,"visible":1}],"courseId":13,"id":297,"name":"原创文章","order":150,"parentChapterId":0,"visible":1},{"children":[{"children":[],"courseId":13,"id":343,"name":"TV","order":155000,"parentChapterId":342,"visible":1}],"courseId":13,"id":342,"name":"TV相关","order":155,"parentChapterId":0,"visible":1},{"children":[{"children":[],"courseId":13,"id":349,"name":"日历","order":160000,"parentChapterId":348,"visible":1},{"children":[],"courseId":13,"id":350,"name":"电影&音乐","order":160001,"parentChapterId":348,"visible":1},{"children":[],"courseId":13,"id":352,"name":"资讯","order":160002,"parentChapterId":348,"visible":1},{"children":[],"courseId":13,"id":353,"name":"快递","order":160003,"parentChapterId":348,"visible":1},{"children":[],"courseId":13,"id":354,"name":"天气","order":160004,"parentChapterId":348,"visible":1},{"children":[],"courseId":13,"id":355,"name":"他人收集","order":160005,"parentChapterId":348,"visible":1},{"children":[],"courseId":13,"id":356,"name":"翻译","order":160006,"parentChapterId":348,"visible":1}],"courseId":13,"id":348,"name":"开放API","order":160,"parentChapterId":0,"visible":1},{"children":[{"children":[],"courseId":13,"id":375,"name":"Flutter","order":165000,"parentChapterId":374,"visible":1}],"courseId":13,"id":374,"name":"跨平台","order":165,"parentChapterId":0,"visible":1},{"children":[{"children":[],"courseId":13,"id":377,"name":"优质内推","order":170000,"parentChapterId":376,"visible":1}],"courseId":13,"id":376,"name":"内推","order":170,"parentChapterId":0,"visible":1},{"children":[{"children":[],"courseId":13,"id":390,"name":"Git","order":175000,"parentChapterId":389,"visible":1}],"courseId":13,"id":389,"name":"项目管理","order":175,"parentChapterId":0,"visible":1},{"children":[{"children":[],"courseId":13,"id":395,"name":"事件总线","order":180000,"parentChapterId":394,"visible":1}],"courseId":13,"id":394,"name":"开源项目源码解析","order":180,"parentChapterId":0,"visible":1},{"children":[{"children":[],"courseId":13,"id":398,"name":"速查","order":185000,"parentChapterId":397,"visible":1}],"courseId":13,"id":397,"name":"速查知识表","order":185,"parentChapterId":0,"visible":1}]
     * errorCode : 0
     * errorMsg :
     */

    private int errorCode;
    private String errorMsg;
    private List<DataBean> data;

    @Bindable
    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
        notifyPropertyChanged(BR.errorCode);
    }

    @Bindable
    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
        notifyPropertyChanged(BR.errorMsg);
    }

    @Bindable
    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
        notifyPropertyChanged(BR.data);
    }

    public static class DataBean {
        /**
         * children : [{"children":[],"courseId":13,"id":60,"name":"Android Studio相关","order":1000,"parentChapterId":150,"visible":1},{"children":[],"courseId":13,"id":169,"name":"gradle","order":1001,"parentChapterId":150,"visible":1},{"children":[],"courseId":13,"id":269,"name":"官方发布","order":1002,"parentChapterId":150,"visible":1}]
         * courseId : 13
         * id : 150
         * name : 开发环境
         * order : 1
         * parentChapterId : 0
         * visible : 1
         */

        private int courseId;
        private int id;
        private String name;
        private int order;
        private int parentChapterId;
        private int visible;
        private List<ChildrenBean> children;

        public int getCourseId() {
            return courseId;
        }

        public void setCourseId(int courseId) {
            this.courseId = courseId;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getOrder() {
            return order;
        }

        public void setOrder(int order) {
            this.order = order;
        }

        public int getParentChapterId() {
            return parentChapterId;
        }

        public void setParentChapterId(int parentChapterId) {
            this.parentChapterId = parentChapterId;
        }

        public int getVisible() {
            return visible;
        }

        public void setVisible(int visible) {
            this.visible = visible;
        }

        public List<ChildrenBean> getChildren() {
            return children;
        }

        public void setChildren(List<ChildrenBean> children) {
            this.children = children;
        }

        public static class ChildrenBean {
            /**
             * children : []
             * courseId : 13
             * id : 60
             * name : Android Studio相关
             * order : 1000
             * parentChapterId : 150
             * visible : 1
             */

            private int courseId;
            private int id;
            private String name;
            private int order;
            private int parentChapterId;
            private int visible;
            private List<?> children;

            public int getCourseId() {
                return courseId;
            }

            public void setCourseId(int courseId) {
                this.courseId = courseId;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getOrder() {
                return order;
            }

            public void setOrder(int order) {
                this.order = order;
            }

            public int getParentChapterId() {
                return parentChapterId;
            }

            public void setParentChapterId(int parentChapterId) {
                this.parentChapterId = parentChapterId;
            }

            public int getVisible() {
                return visible;
            }

            public void setVisible(int visible) {
                this.visible = visible;
            }

            public List<?> getChildren() {
                return children;
            }

            public void setChildren(List<?> children) {
                this.children = children;
            }
        }
    }
}

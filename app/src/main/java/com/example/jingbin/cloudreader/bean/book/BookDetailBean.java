package com.example.jingbin.cloudreader.bean.book;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.example.http.ParamNames;
import com.example.jingbin.cloudreader.BR;
import com.example.jingbin.cloudreader.bean.moviechild.ImagesBean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jingbin on 2016/12/25.
 */

public class BookDetailBean extends BaseObservable implements Serializable{

    /**
     * rating : {"max":10,"numRaters":72,"average":"7.3","min":0}
     * subtitle : VAN LOON'S GEOGRAPHY
     * author : ["（美）房龙　著，王希发　译"]
     * pubdate : 2008-9
     * tags : [{"count":33,"name":"地理","title":"地理"},{"count":24,"name":"房龙","title":"房龙"},{"count":8,"name":"世界地理","title":"世界地理"},{"count":6,"name":"综合","title":"综合"},{"count":4,"name":"世界","title":"世界"},{"count":4,"name":"科普","title":"科普"},{"count":4,"name":"社会学","title":"社会学"},{"count":3,"name":"人文","title":"人文"}]
     * origin_title :
     * image : https://img3.doubanio.com/mpic/s9007440.jpg
     * binding :
     * translator : ["王希发"]
     * catalog : 一　人类与家园二　什么是“地理学”三　地球的特点、规律和状况四　地图：万水千山寻路难五　地球有四季六　海洋中的大陆七　发现欧洲八　希腊：连接古老亚洲和新兴欧洲的桥梁九　意大利：地理造就的海上霸主或陆上强国十　西班牙：非洲与欧洲交锋之地十一　法国：应有尽有的国家十二　比利时：几页文件决定了它的命运十三  卢森堡：遭遇历史的捉弄十四  瑞士：四个语言不同的民族和睦相处十五　德国：建国太迟的国家十六  奥地利：无人喝彩的国家十七  丹麦：小国在某些方面胜过大国的典范十八　冰岛：北冰洋上一个有趣的政治实验室十九　斯堪的纳维亚半岛：瑞典王国和挪威王国的属地二十　荷兰：沼泽上崛起的帝国二十一  英国：小小岛国人满为患二十二  俄罗斯：欧洲之国还是亚洲之国二十三  波兰：自家的土地别人的走廊二十四  捷克斯洛伐克：《凡尔赛和约》的果实二十五  南斯拉夫：《凡尔赛和约》的另一件作品二十六  保加利亚：最正统的巴尔干国家二十七  罗马尼亚：一个有石油有王室的国家二十八  匈牙利：或者匈牙利的残余二十九　芬兰：勤劳和智慧战胜恶劣环境的又一明证三十　发现亚洲三十一　亚洲与世界三十二  亚洲中部高原三十三　亚洲西部高原三十四　阿拉伯三十五  印度：人和自然相互促进，共同发展三十六  亚洲南部半岛的主人三十七　中国：东亚大半岛三十八　朝鲜与蒙古：前途未卜三十九　日本：野心勃勃的岛国四十　菲律宾：原墨西哥的领地四十一　荷属东印度群岛：小人物掌大权四十二  澳大利亚：造物主的随意之作四十三　新西兰：珊瑚岛屿的王国四十四  太平洋群岛：不耕不织，照样生活四十五  非洲：矛盾重重的大陆四十六　美洲：最幸福的大陆四十七　创造新世界
     * pages : 308
     * images : {"small":"https://img3.doubanio.com/spic/s9007440.jpg","large":"https://img3.doubanio.com/lpic/s9007440.jpg","medium":"https://img3.doubanio.com/mpic/s9007440.jpg"}
     * alt : https://book.douban.com/subject/3235564/
     * id : 3235564
     * publisher : 北京出版社
     * isbn10 : 7200073261
     * isbn13 : 9787200073263
     * title : 地理的故事
     * url : https://api.douban.com/v2/book/3235564
     * alt_title :
     * author_intro : 房龙，荷裔美国人。他是一位才艺卓绝的博学之士。房龙的人生经历异常丰富，曾经从事过各种各样的工作，先后当过教师、编辑、记者和播音员。他一生创作了大量饮誉世界的作品。在写作中，他善于运用生动活泼的文字，撰写通俗易懂的历史著作。自20世纪20年代起，凡是他发表的作品，都在美国畅销一空，并被译成多种文字在世界各国出版发行，深受各国年轻读者的喜爱。在他众多的畅销书中，就包括这部独树一帜的地理学著作--《地理的故事》。瓣 房龙的这部著作保持了其惯有的行文风格。他用诙谐幽默的文字把枯燥的地理知识描述得活灵活现，使读者在轻松愉快之际不仅了解了人类漫长历史发展的来龙去脉，且能在掩卷之后获得不少启发。世界地理在房龙的笔下，既非气象风云的亘古变迁，也非沧海桑田的物换星移。他所写的地理，是一部有血有肉的“人的”地理。因为他坚信，世界上任何一块土地的重要性都取决于这块土地上的人民以科学、商业、宗教或某种艺术形式为全人类的幸福所作出的或大或小的贡献。
     * 为什么丹麦人偏好静谧的书斋，而西班牙人则热衷于广阔的天地?为什么日本总是千方百计想要扩张，而瑞士则想方设法追求中立?为什么亚洲国家总是安于现状，而欧洲国家却总是强调改革?一个国家的民族性格和历史发展与其地理因素究竟有何关联?房龙在这部书中给出了他自己的答案。
     * 房龙在本书中摒弃了枯燥乏味的科普说教和传统填鸭式的内容灌输，而是以一种清新活泼的方式讲述世界地理知识，从而激发读者的阅读兴趣，让地理知识变得生动有趣。与此同时，他在书中对一部分国家的地理环境进行了浓重的描述，并从中分析出地理对一个国家的历史演变和一个民族的性格形成所产生的影响。
     * summary : 沧海桑田、物换星移，几度风雨、几度春秋，地理变迁永无止歇。然而，这变迁展现的仅仅是一种自风情吗？当然不是。在房龙的笔下，世界地理远非如此，它是一部有血有肉的“人的”地理。在这部地理学著作中，房龙以幽默睿智的文风，用一个个小故事，将每个国家的民族性格、历史发展与地理环境的关联娓娓而来，为读者打开了从另一个角度看世界的窗户，使枯燥的地理知识不再乏味。跟随着这位伟大的文化传播者和出色的通俗读物作家的笔触，读者既能轻松愉快地了解人类漫长历史的来龙去脉，也会在掩卷之后回味沉思，久久不忍释卷。
     * --------------------------------------------------------------------------------
     * 一人类与家园
     * 二什么是“地理学”
     * 三地球的特点、规律和状况
     * 四地图：万水千山寻路难
     * 五地球有四季
     * 六海洋中的大陆
     * 七发现欧洲
     * 八希腊：连接古老亚洲和新兴欧洲的桥梁
     * 九意大利：地理造就的海上霸主或陆上强国
     * 十西班牙：非洲与欧洲交锋之地
     * 十一法国：应有尽有的国家
     * 十二比利时：几页文件决定了它的命运
     * 十三卢森堡：遭遇历史的捉弄
     * 十四瑞士：四个语言不同的民族和睦相处
     * 十五德国：建国太迟的国家
     * 十六奥地利：无人喝彩的国家
     * 十七丹麦：小国在某些方面胜过大国的典范
     * 十八冰岛：北冰洋上一个有趣的政治实验室
     * 十九斯堪的纳维亚半岛：瑞典王国和挪威王国的属地
     * 二十荷兰：沼泽上崛起的帝国
     * 二十一英国：小小岛国人满为患
     * 二十二俄罗斯：欧洲之国还是亚洲之国
     * 二十三波兰：自家的土地别人的走廊
     * 二十四捷克斯洛伐克：《凡尔赛和约》的果实
     * 二十五南斯拉夫：《凡尔赛和约》的另一件作品
     * 二十六保加利亚：最正统的巴尔干国家
     * 二十七罗马尼亚：一个有石油有王室的国家
     * 二十八匈牙利：或者匈牙利的残余
     * 二十九芬兰：勤劳和智慧战胜恶劣环境的又一明证
     * 三十发现亚洲
     * 三十一亚洲与世界
     * 三十二亚洲中部高原
     * 三十三亚洲西部高原
     * 三十四阿拉伯
     * 三十五印度：人和自然相互促进，共同发展
     * 三十六亚洲南部半岛的主人
     * 三十七中国：东亚大半岛
     * 三十八朝鲜与蒙古：前途未
     * 三十九日本：野心勃勃的岛国
     * 四十菲律宾：原墨西哥的领地
     * 四十一荷属东印度群岛：小人物掌大权
     * 四十二澳大利亚：造物主的随意之作
     * 四十三新西兰：珊瑚岛屿的王国
     * 四十四太平洋群岛：不耕不织，照样生活
     * 四十五非洲：矛盾重重的大陆
     * 四十六美洲：最幸福的大陆
     * 四十七创措新世界
     * price : 23.90元
     */
    @ParamNames("rating")
    private BooksBean.RatingBean rating;
    @ParamNames("subtitle")
    private String subtitle;
    @ParamNames("pubdate")
    private String pubdate;
    @ParamNames("origin_title")
    private String origin_title;
    @ParamNames("image")
    private String image;
    @ParamNames("binding")
    private String binding;
    @ParamNames("catalog")
    private String catalog;
    @ParamNames("pages")
    private String pages;
    @ParamNames("images")
    private ImagesBean images;
    @ParamNames("alt")
    private String alt;
    @ParamNames("id")
    private String id;
    @ParamNames("publisher")
    private String publisher;
    @ParamNames("isbn10")
    private String isbn10;
    @ParamNames("isbn13")
    private String isbn13;
    @ParamNames("title")
    private String title;
    @ParamNames("url")
    private String url;
    @ParamNames("alt_title")
    private String alt_title;
    @ParamNames("author_intro")
    private String author_intro;
    @ParamNames("summary")
    private String summary;
    @ParamNames("price")
    private String price;
    @ParamNames("author")
    private List<String> author;
    @ParamNames("tags")
    private List<BooksBean.TagsBean> tags;
    @ParamNames("translator")
    private List<String> translator;

    @Bindable
    public BooksBean.RatingBean getRating() {
        return rating;
    }

    public void setRating(BooksBean.RatingBean rating) {
        this.rating = rating;
        notifyPropertyChanged(BR.rating);
    }

    @Bindable
    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
        notifyPropertyChanged(BR.subtitle);
    }

    @Bindable
    public String getPubdate() {
        return pubdate;
    }

    public void setPubdate(String pubdate) {
        this.pubdate = pubdate;
        notifyPropertyChanged(BR.pubdate);
    }

    @Bindable
    public String getOrigin_title() {
        return origin_title;
    }

    public void setOrigin_title(String origin_title) {
        this.origin_title = origin_title;
        notifyPropertyChanged(BR.origin_title);
    }

    @Bindable
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
        notifyPropertyChanged(BR.image);
    }

    @Bindable
    public String getBinding() {
        return binding;
    }

    public void setBinding(String binding) {
        this.binding = binding;
        notifyPropertyChanged(BR.binding);
    }

    @Bindable
    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
        notifyPropertyChanged(BR.catalog);
    }

    @Bindable
    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
        notifyPropertyChanged(BR.pages);
    }

    @Bindable
    public ImagesBean getImages() {
        return images;
    }

    public void setImages(ImagesBean images) {
        this.images = images;
        notifyPropertyChanged(BR.images);
    }

    @Bindable
    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
        notifyPropertyChanged(BR.alt);
    }

    @Bindable
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
        notifyPropertyChanged(BR.id);
    }

    @Bindable
    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
        notifyPropertyChanged(BR.publisher);
    }

    @Bindable
    public String getIsbn10() {
        return isbn10;
    }

    public void setIsbn10(String isbn10) {
        this.isbn10 = isbn10;
        notifyPropertyChanged(BR.isbn10);
    }

    @Bindable
    public String getIsbn13() {
        return isbn13;
    }

    public void setIsbn13(String isbn13) {
        this.isbn13 = isbn13;
        notifyPropertyChanged(BR.isbn13);
    }

    @Bindable
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        notifyPropertyChanged(BR.title);
    }

    @Bindable
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
        notifyPropertyChanged(BR.url);
    }

    @Bindable
    public String getAlt_title() {
        return alt_title;
    }

    public void setAlt_title(String alt_title) {
        this.alt_title = alt_title;
        notifyPropertyChanged(BR.alt_title);
    }

    @Bindable
    public String getAuthor_intro() {
        return author_intro;
    }

    public void setAuthor_intro(String author_intro) {
        this.author_intro = author_intro;
        notifyPropertyChanged(BR.author_intro);
    }

    @Bindable
    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
        notifyPropertyChanged(BR.summary);
    }

    @Bindable
    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
        notifyPropertyChanged(BR.price);
    }

    @Bindable
    public List<String> getAuthor() {
        return author;
    }

    public void setAuthor(List<String> author) {
        this.author = author;
        notifyPropertyChanged(BR.author);
    }

    @Bindable
    public List<BooksBean.TagsBean> getTags() {
        return tags;
    }

    public void setTags(List<BooksBean.TagsBean> tags) {
        this.tags = tags;
        notifyPropertyChanged(BR.tags);
    }

    @Bindable
    public List<String> getTranslator() {
        return translator;
    }

    public void setTranslator(List<String> translator) {
        this.translator = translator;
        notifyPropertyChanged(BR.translator);
    }

}

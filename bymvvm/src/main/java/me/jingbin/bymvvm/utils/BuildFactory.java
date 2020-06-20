package me.jingbin.bymvvm.utils;


import me.jingbin.bymvvm.http.HttpUtils;


/**
 * @author jingbin
 * @data 2018/2/9
 * @Description
 */

public class BuildFactory {

    private static BuildFactory instance;
    private Object gankHttps;
    private Object doubanHttps;
    private Object dongtingHttps;
    private Object firHttps;
    private Object wanAndroidHttps;
    private Object qsbkHttps;
    private Object mtimeHttps;
    private Object mtimeTicketHttps;

    public static BuildFactory getInstance() {
        if (instance == null) {
            synchronized (BuildFactory.class) {
                if (instance == null) {
                    instance = new BuildFactory();
                }
            }
        }
        return instance;
    }

    public <T> T create(Class<T> a, String type) {

        switch (type) {
            case HttpUtils.API_GANKIO:
                if (gankHttps == null) {
                    synchronized (BuildFactory.class) {
                        if (gankHttps == null) {
                            gankHttps = HttpUtils.getInstance().getBuilder(type).build().create(a);
                        }
                    }
                }
                return (T) gankHttps;
            case HttpUtils.API_DOUBAN:
                if (doubanHttps == null) {
                    synchronized (BuildFactory.class) {
                        if (doubanHttps == null) {
                            doubanHttps = HttpUtils.getInstance().getBuilder(type).build().create(a);
                        }
                    }
                }
                return (T) doubanHttps;
            case HttpUtils.API_TING:
                if (dongtingHttps == null) {
                    synchronized (BuildFactory.class) {
                        if (dongtingHttps == null) {
                            dongtingHttps = HttpUtils.getInstance().getBuilder(type).build().create(a);
                        }
                    }
                }
                return (T) dongtingHttps;
            case HttpUtils.API_GITEE:
                if (firHttps == null) {
                    synchronized (BuildFactory.class) {
                        if (firHttps == null) {
                            firHttps = HttpUtils.getInstance().getBuilder(type).build().create(a);
                        }
                    }
                }
                return (T) firHttps;
            case HttpUtils.API_WAN_ANDROID:
                if (wanAndroidHttps == null) {
                    synchronized (BuildFactory.class) {
                        if (wanAndroidHttps == null) {
                            wanAndroidHttps = HttpUtils.getInstance().getBuilder(type).build().create(a);
                        }
                    }
                }
                return (T) wanAndroidHttps;
            case HttpUtils.API_QSBK:
                if (qsbkHttps == null) {
                    synchronized (BuildFactory.class) {
                        if (qsbkHttps == null) {
                            qsbkHttps = HttpUtils.getInstance().getBuilder(type).build().create(a);
                        }
                    }
                }
                return (T) qsbkHttps;
            case HttpUtils.API_MTIME:
                if (mtimeHttps == null) {
                    synchronized (BuildFactory.class) {
                        if (mtimeHttps == null) {
                            mtimeHttps = HttpUtils.getInstance().getBuilder(type).build().create(a);
                        }
                    }
                }
                return (T) mtimeHttps;
            case HttpUtils.API_MTIME_TICKET:
                if (mtimeTicketHttps == null) {
                    synchronized (BuildFactory.class) {
                        if (mtimeTicketHttps == null) {
                            mtimeTicketHttps = HttpUtils.getInstance().getBuilder(type).build().create(a);
                        }
                    }
                }
                return (T) mtimeTicketHttps;
            default:
                if (gankHttps == null) {
                    synchronized (BuildFactory.class) {
                        if (gankHttps == null) {
                            gankHttps = HttpUtils.getInstance().getBuilder(type).build().create(a);
                        }
                    }
                }
                return (T) gankHttps;
        }
    }

}

package com.example.jingbin.cloudreader.http.api;

import java.util.List;
import java.util.Random;

/**
 * Created by forezp on 16/10/1.
 */
public class MusicApiUtils {


    public static String Music_Titles []={"流行","经典","韩系","欧美"};
    public static String PopulayTag[] ={"歌声", "青春", "回忆", "孙燕姿","周杰伦","林俊杰", "陈奕迅", "王力宏", "邓紫棋","风声",  "海边", "童话", "美女",  "一生", "爱", "爱情", "远方", "缘分","天空","张国荣","黄家驹","　beyond", "黑豹乐队" };
    public static String ClassicTag []={"张国荣","黄家驹","　beyond", "黑豹乐队", "王菲", "五月天", "陈奕迅", "古巨基", "杨千嬅", "叶倩文", "许嵩","刘德华","邓丽君","张学友"};
    public static String KereaTag[] ={"bigbang","rain", "PSY", "李弘基", "李承哲","金钟国", "李孝利", "孝琳", "IU", "EXO", "T-ara", "东方神起", "Epik High", "Girl's Day", " 紫雨林", "Zebra"};
    public static String AmericanTag[] ={"Jay-Z","Justin Bieber","James Blunt","Eminem","Akon","Adele","Avril Lavigne","Beyoncé","Lady GaGa","Taylor Swift","Alicia Keys","Owl City","Coldplay"};


    public static String[] getApiTag(int pos){
        switch (pos){
            case 0:
                return PopulayTag;
            case 1:
                return ClassicTag;
            case 2:
                return KereaTag;
            case 3:
                return AmericanTag;



        }
        return null;
    }


    public static  String getRandomTAG(List<String> list){
        Random random=new Random();
        int i=random.nextInt(list.size());
        return  list.get(i);
    }
}

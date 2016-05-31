package qiqi.love.you.recycletabs;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by iscod on 2016/5/30.
 */
public class DataFactory {
    private static List<String> mList;

    public static List<String> getList() {
        if (mList == null)
            mList = new ArrayList<>();
        if (mList.isEmpty()) {
            mList.add("头条");
            mList.add("琪琪");
            mList.add("Android");
            mList.add("科技");
            mList.add("Google");
            mList.add("热点");
            mList.add("移动");
            mList.add("军事");
            mList.add("移动互联");
            mList.add("娱乐");
            mList.add("财经");
            mList.add("健康");
            mList.add("时尚");
            mList.add("历史");
            mList.add("社会");
            mList.add("政务");
            mList.add("直播");
            mList.add("段子");
            mList.add("彩票");
            mList.add("房产");
            mList.add("博客");
            mList.add("轻松一刻");
            mList.add("GitHub");
            mList.add("Java");
            mList.add("Coder");
        }
        return mList;
    }
}

package qiqi.love.you.recycletabs;

/**
 * Created by iscod on 2016/5/31.
 */
public interface onMoveAndSwipedListener {
    boolean onItemMove(int position, int targetPos);

    void onItemDismiss(int position);
}

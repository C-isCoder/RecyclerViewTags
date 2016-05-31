package qiqi.love.you.recycletabs;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Created by iscod on 2016/5/31.
 */
public class SimpleItemTouchHelperCallback extends ItemTouchHelper.Callback {
    private onMoveAndSwipedListener mAdapter;

    public SimpleItemTouchHelperCallback(onMoveAndSwipedListener listener) {
        this.mAdapter = listener;
    }

    /**
     * 这个方法是用来设置我们拖动的方向以及侧滑的方向的
     */
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        //如果ListView样式的RecyclerView
        //设置拖拽方向上下左右都可以
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN |
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        //侧滑方向从左到右，从右到左都可以。
        int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
        //将参数设置进去
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    /**
     * 拖住Iitem的时候回调此方法
     *
     * @param recyclerView
     * @param viewHolder
     * @param target
     * @return
     */
    @Override
    public boolean onMove(RecyclerView recyclerView,
                          RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        /**
         * 如果两个Ietm类型不一致，不让拖拽
         */
        if (viewHolder.getItemViewType() != target.getItemViewType()) {
            return false;
        }
        //回调Adapter中的onItemMove（）方法
        mAdapter.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());

        return true;
    }

    /**
     * 侧滑Item的时候回调此方法
     *
     * @param viewHolder
     * @param direction
     */
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        mAdapter.onItemDismiss(viewHolder.getAdapterPosition());
    }
}

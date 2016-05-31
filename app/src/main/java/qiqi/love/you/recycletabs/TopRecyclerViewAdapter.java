package qiqi.love.you.recycletabs;

import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by iscod on 2016/5/30.
 */
public class TopRecyclerViewAdapter extends RecyclerView.Adapter<TopRecyclerViewAdapter.
        ViewHolder> implements onMoveAndSwipedListener {
    private List<String> mList;

    public TopRecyclerViewAdapter(List<String> mList) {
        this.mList = mList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recycle_view, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final View view = holder.itemView;
        final int pos = position;
        holder.mTvTags.setText(mList.get(position));
        holder.mTvTags.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnitemClickListener.onItemClick(view, pos);
                Snackbar.make(v, "come on baby",
                        Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    protected class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_tv_tags)
        TextView mTvTags;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private OnItemClickListener mOnitemClickListener;

    public void setOnItemClickListenter(OnItemClickListener listenter) {
        mOnitemClickListener = listenter;
    }

    //实现接口，重写拖拽方法
    @Override
    public boolean onItemMove(int position, int targetPos) {
        //交换item数据的位置
        Collections.swap(mList, position, targetPos);
        //交换RecycleView列表中Item的位置
        notifyItemMoved(position, targetPos);
        //更新Activity中的数据
        mOnChangeListData.ChangeData(mList, -1);
        return true;
    }

    //左滑 右滑删除实现
    @Override
    public void onItemDismiss(int position) {
        //mList.remove(position);
        //MainActivity.allList.add(MainActivity.topList.get(position));
        //notifyDataSetChanged();
        mOnChangeListData.ChangeData(mList, position);
        Log.d("CID", "Adapter变换：" + mList.toString());
        //mList.remove(position);
        //notifyDataSetChanged();
    }

    private OnChangeListData mOnChangeListData;

    public void setOnChangeListDataListenter(OnChangeListData listDataListenter) {
        mOnChangeListData = listDataListenter;
    }
}

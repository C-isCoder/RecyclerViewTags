package qiqi.love.you.recycletabs;

import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
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
public class AllRecyclerViewAdapter extends RecyclerView.Adapter<AllRecyclerViewAdapter.ViewHolder>{
    private List<String> mList;

    public AllRecyclerViewAdapter(List<String> mList) {
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
}

package qiqi.love.you.recycletabs;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements OnChangeListData {
    @BindView(R.id.recycle_view1)
    RecyclerView mTopRecyclerView;
    @BindView(R.id.recycle_view2)
    RecyclerView mAllRecyclerView;
    private TopRecyclerViewAdapter mTopAdapter;
    private AllRecyclerViewAdapter mAllAdapter;
    public List<String> topList = new ArrayList<>();
    public List<String> allList = new ArrayList<>();
    //@BindView(R.id.linear_layout)
    RelativeLayout linearLayout;
    Object lock = new Object();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    private void initData() {
        allList = DataFactory.getList();
        //topList = DataFactory.getList();
    }

    private void initView() {
        mTopAdapter = new TopRecyclerViewAdapter(topList);
        mAllAdapter = new AllRecyclerViewAdapter(allList);
        mTopAdapter.setOnChangeListDataListenter(this);
        mTopAdapter.setOnItemClickListenter(new OnItemClickListener() {
            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void onItemClick(View view, int position) {
                /*synchronized (lock) {

                }*/
                //mAllAdapter.notifyItemInserted(position);
                allList.add(topList.get(position));
                mAllAdapter.notifyDataSetChanged();
                //mTopAdapter.notifyItemRemoved(position);
                topList.remove(position);
                mTopAdapter.notifyDataSetChanged();
                //mAllAdapter.notifyDataSetChanged();
                //mTopAdapter.notifyDataSetChanged();

                Log.d("CID", "添加后list大小变化(TOP)：" + topList.size());

            }
        });
        mAllAdapter.setOnItemClickListenter(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                topList.add(allList.get(position));
                mTopAdapter.notifyDataSetChanged();
                allList.remove(position);
                mAllAdapter.notifyDataSetChanged();
                //mTopAdapter.notifyItemInserted(position);
                //mAllAdapter.notifyItemRemoved(position);
                Log.d("CID", "添加后list大小变化(ALL)：" + allList.size());
                //mTopAdapter.notifyItemInserted(position);
                //mAllAdapter.notifyItemRemoved(position);
                //addAnimator(view);
            }
        });
        GridLayoutManager topLayout = new GridLayoutManager(this, 4);
        GridLayoutManager allLayout = new GridLayoutManager(this, 4);
        mTopRecyclerView.setLayoutManager(topLayout);
        mAllRecyclerView.setLayoutManager(allLayout);
        mTopRecyclerView.setAdapter(mTopAdapter);
        mAllRecyclerView.setAdapter(mAllAdapter);
        mTopRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAllRecyclerView.setItemAnimator(new DefaultItemAnimator());
        //关联ItemTouchHelper和RecyclerView
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(mTopAdapter);
        ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mTopRecyclerView);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void addAnimator(View view) {
        final PathMeasure mPathMeasure;
        final float[] mCurrentPosition = new float[2];
        int parentLoc[] = new int[2];
        linearLayout.getLocationInWindow(parentLoc);
        int startLoc[] = new int[2];
        view.getLocationInWindow(startLoc);

        final View startView = view;
        LinearLayout.LayoutParams params = new LinearLayout
                .LayoutParams(view.getWidth(), view.getHeight());
        mAllRecyclerView.removeView(view);
        linearLayout.addView(startView, params);

        View endView;
        float toX, toY;
        int endLoc[] = new int[2];
        //进行判断
        int i = topList.size();
        if (i == 0) {
            toX = view.getWidth();
            toY = view.getHeight();
        } else if (i % 4 == 0) {
            endView = mTopRecyclerView.getChildAt(i - 4);
            endView.getLocationInWindow(endLoc);
            toX = endLoc[0] - parentLoc[0];
            toY = endLoc[1] + view.getHeight() - parentLoc[1];
        } else {
            endView = mTopRecyclerView.getChildAt(i - 1);
            endView.getLocationInWindow(endLoc);
            toX = endLoc[0] + view.getWidth() - parentLoc[0];
            toY = endLoc[1] - parentLoc[1];
        }
        Log.e("CID", "addAnimator: " + allList.size() + "@");
        Log.e("CID", "addAnimator: " + topList.size() + "@@");

        float startX = startLoc[0] - parentLoc[0];
        float startY = startLoc[1] - parentLoc[1];

        Path path = new Path();
        path.moveTo(startX, startY);
        path.lineTo(toX, toY);
        mPathMeasure = new PathMeasure(path, false);

        //属性动画实现
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, mPathMeasure.getLength());
        valueAnimator.setDuration(500);
        //匀速插值器
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                //获取当前点的坐标封装到mCurrentPosition;
                mPathMeasure.getPosTan(value, mCurrentPosition, null);
                startView.setTranslationX(mCurrentPosition[0]);
                startView.setTranslationY(mCurrentPosition[1]);
            }
        });
        valueAnimator.start();
    }

    //更新List数据
    @Override
    public void ChangeData(List<String> List, int position) {
        if (position != -1) {
            allList.add(topList.get(position));
            topList.remove(position);
            mAllAdapter.notifyDataSetChanged();
            mTopAdapter.notifyDataSetChanged();
        } else {
            topList = List;
            Log.d("CID", "MainActivity变换：" + topList.toString());
        }
    }
}

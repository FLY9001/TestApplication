package com.eyesmart.testapplication.ui.viewwidget;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.eyesmart.testapplication.R;

import java.util.ArrayList;
import java.util.List;


/**
 * RecyclerView：高度解耦、更灵活、更效率
 * 替代ListView、GridView
 * 需导入support-v7包
 */
public class RecyclerFragment extends Fragment {
    private View rootView;
    private RecyclerView rv_recycler;

    public RecyclerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_recycler, container, false);
        rv_recycler = (RecyclerView) rootView.findViewById(R.id.rv_recycler);
        List<Bean> beens = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            if (i % 3 != 0) {
                beens.add(new Bean(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher), "11111", ListViewFragment.Bean.TYPE_RECEIVED));
            } else {
                beens.add(new Bean(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher), "11111", ListViewFragment.Bean.TYPE_SENT));
            }
        }

        //设置LayoutManager，LinearLayoutManager线性排列（垂直、水平），GridLayoutManager网格，StaggeredGridLayoutManager瀑布流
        rv_recycler.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        //设置分割线
        rv_recycler.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        //设置item增加和删除时动画
        rv_recycler.setItemAnimator(new DefaultItemAnimator());
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        rv_recycler.setHasFixedSize(true);

        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(beens);
        //设置item点击事件
        recyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getContext(), "点击了第" + position + "个", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View view, int position) {
                Toast.makeText(getContext(), "长点击了第" + position + "个", Toast.LENGTH_SHORT).show();
            }
        });
        rv_recycler.setAdapter(recyclerViewAdapter);
        return rootView;
    }

    class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private List<Bean> mBeans;
        private OnItemClickListener mOnItemClickListener;

        public RecyclerViewAdapter(List<Bean> beans) {
            mBeans = beans;
        }

        //加载条目布局
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            switch (viewType) {
                case Bean.TYPE_RECEIVED:
                    View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_left, parent, false);
                    ViewHolder viewHolder = new ViewHolder(itemView);
                    return viewHolder;
                case Bean.TYPE_SENT:
                    View itemView2 = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_right, parent, false);
                    ViewHolder2 viewHolder2 = new ViewHolder2(itemView2);
                    return viewHolder2;
            }
            return null;
        }

        //绑定条目数据
        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof RecyclerViewAdapter.ViewHolder) {
                ((RecyclerViewAdapter.ViewHolder) holder).icon.setImageBitmap(mBeans.get(position).getIcon());
                ((RecyclerViewAdapter.ViewHolder) holder).text.setText(mBeans.get(position).getText());
            } else if (holder instanceof RecyclerViewAdapter.ViewHolder2) {
                ((RecyclerViewAdapter.ViewHolder2) holder).icon.setImageBitmap(mBeans.get(position).getIcon());
                ((RecyclerViewAdapter.ViewHolder2) holder).text.setText(mBeans.get(position).getText());
            }
            //设置点击监听
            if (mOnItemClickListener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = holder.getLayoutPosition();
                        mOnItemClickListener.onItemClick(holder.itemView, position);
                    }
                });
                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        int position = holder.getLayoutPosition();
                        mOnItemClickListener.onItemLongClick(holder.itemView, position);
                        return false;
                    }
                });

                //改变布局参数，可设置瀑布流效果
                ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
                lp.height = 0;
                holder.itemView.setLayoutParams(lp);
            }
        }

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            mOnItemClickListener = onItemClickListener;
        }

        @Override
        public int getItemCount() {
            return mBeans.size();
        }

        @Override
        public int getItemViewType(int position) {
            return mBeans.get(position).getType();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            View itemView;
            ImageView icon;
            TextView text;

            public ViewHolder(View itemView) {
                super(itemView);
                this.itemView = itemView;
                icon = (ImageView) itemView.findViewById(R.id.iv_left_listView);
                text = (TextView) itemView.findViewById(R.id.tv_left_listView);
            }
        }

        class ViewHolder2 extends RecyclerView.ViewHolder {
            View itemView;
            ImageView icon;
            TextView text;

            public ViewHolder2(View itemView) {
                super(itemView);
                this.itemView = itemView;
                icon = (ImageView) itemView.findViewById(R.id.iv_right_listView);
                text = (TextView) itemView.findViewById(R.id.tv_right_listView);
            }
        }
    }

    interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    /**
     * 实体类
     * Created by lgl on 16/3/20.
     */
    public class Bean {
        public static final int TYPE_RECEIVED = 0;
        public static final int TYPE_SENT = 1;

        private int type;
        private Bitmap icon;
        private String text;

        public Bean(Bitmap icon, String text, int type) {
            this.icon = icon;
            this.text = text;
            this.type = type;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public Bitmap getIcon() {
            return icon;
        }

        public void setIcon(Bitmap icon) {
            this.icon = icon;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }
}

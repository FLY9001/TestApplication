package com.eyesmart.testapplication.mvp.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eyesmart.testapplication.mvp.presenter.MVPPresenter;

import java.util.List;

public class MVPAdapter extends RecyclerView.Adapter<MVPAdapter.MVPViewHolder> {
    private MVPPresenter mPresenter;
    private List<String> items;

    public MVPAdapter(List<String> items, MVPPresenter presenter) {
        this.items = items;
        this.mPresenter = presenter;
    }


    @Override
    public MVPViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TextView v = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.test_list_item, parent, false);
        return new MVPViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MVPViewHolder holder, int position) {
        final String item = items.get(position);
        holder.textView.setText(item);
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.onItemClicked(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class MVPViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        MVPViewHolder(TextView textView) {
            super(textView);
            this.textView = textView;
        }
    }
}

package com.example.fiveaddone.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import lumenghz.com.pullrefresh.PullToRefreshView;

/**
 * @author lumeng on 2016-06-17.
 *         jiahehz@gmail.com
 */
public class myFragment extends myBaseFragment {

    @BindView(R.id.pull_to_refresh)
    PullToRefreshView mPullToRefresh;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recyclerview, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initRecylerView();
        initRefreshView();
    }

    private void initRecylerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new SampleAdaper());
    }

    private void initRefreshView() {
        mPullToRefresh.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPullToRefresh.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPullToRefresh.setRefreshing(false);
                    }
                }, REFRESH_DELAY);
            }
        });
    }

    class SampleAdaper extends RecyclerView.Adapter<SampleHolder> {
        @Override
        public SampleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item, parent, false);
            return new SampleHolder(view);
        }

        @Override
        public void onBindViewHolder(SampleHolder holder, int position) {
            Map<String, String> data = mSampleDatas.get(position);
            holder.bindData(data);
        }

        @Override
        public int getItemCount() {
            return mSampleDatas.size();
        }
    }

    class SampleHolder extends RecyclerView.ViewHolder {
        private TextView gongsi;
        private TextView time;
        private TextView baochou;
        private TextView weihe;
        private TextView xingbie;
        private TextView riqi;

        private Map<String, String> mData;

        public SampleHolder(View itemView) {
            super(itemView);
            gongsi = (TextView)itemView.findViewById(R.id.tv_gongsi);
            time = (TextView)itemView.findViewById(R.id.tv_time);
            baochou = (TextView)itemView.findViewById(R.id.tv_baochou);
            weihe = (TextView)itemView.findViewById(R.id.tv_weihe);
            xingbie = (TextView)itemView.findViewById(R.id.tv_xingbie);
        }

        public void bindData(Map<String, String> data) {
            mData = data;

            gongsi.setText(mData.get(GONGSI)+'('+mData.get(DANHAO)+')');
            time.setText(mData.get(time));
            baochou.setText(mData.get(BAOCHOU));
            weihe.setText(mData.get(WEIHE));
            xingbie.setText(mData.get(XINGBIE));
        }
    }
}
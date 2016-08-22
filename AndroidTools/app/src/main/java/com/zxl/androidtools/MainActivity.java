package com.zxl.androidtools;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.plugin.utils.SystemInfoUtils;
import com.zxl.androidtools.adapter.MainRecyclerAdapter;
import com.zxl.androidtools.inter.OnClickPositonListerner;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements OnClickPositonListerner {

    @Bind(R.id.recycler)
    RecyclerView recycler;

    private List<String> activity_labname;//activity别名
    private List<String> activity_packagename;//activity的路径:包名+类名
    private MainRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        getList();
        setRecycler();
    }

    /**
     * 获取Acticity清单
     */
    public void getList() {
        activity_labname = new ArrayList<>();
        activity_packagename = new ArrayList<>();
        ActivityInfo[] activities = SystemInfoUtils.getInstance(this).getActivityInfo();
        if (activities != null) {
            for (ActivityInfo activityInfo : activities) {
                if (activityInfo.labelRes != 0) {
                    activity_labname.add(getString(activityInfo.labelRes));
                    activity_packagename.add(activityInfo.name);
                }
            }
        }
    }

    /**
     * 设置RecycleView
     */
    public void setRecycler() {
        adapter = new MainRecyclerAdapter(activity_labname, this);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setAdapter(adapter);
        adapter.setOnItemClickListerner(this);
    }

    @Override
    public void clickPosition(int position) {
        jump(position);
    }

    public void jump(int position) {
        Intent intent = new Intent();
        intent.setClassName(getPackageName(), activity_packagename.get(position));
        startActivity(intent);
    }
}
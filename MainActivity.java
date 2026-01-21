package com.example.timerapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<TimerPack> timerPacks;
    private TimerPackAdapter adapter;
    private ListView timerListView;
    private TextView emptyStateText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 初始化UI组件
        timerListView = findViewById(R.id.timer_list_view);
        emptyStateText = findViewById(R.id.empty_state_text);
        Button addTimerBtn = findViewById(R.id.add_timer_btn);

        // 加载计时器数据
        timerPacks = TimerStorage.loadTimerPacks(this);
        if (timerPacks.isEmpty()) {
            showEmptyState();
        }

        // 设置适配器
        adapter = new TimerPackAdapter(this, timerPacks);
        timerListView.setAdapter(adapter);

        // 设置添加按钮点击事件
        addTimerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 打开添加计时器对话框
                AddTimerDialog dialog = new AddTimerDialog(MainActivity.this, new AddTimerDialog.OnTimerAddedListener() {
                    @Override
                    public void onTimerAdded(TimerPack timerPack) {
                        timerPacks.add(timerPack);
                        TimerStorage.saveTimerPacks(MainActivity.this, timerPacks);
                        adapter.notifyDataSetChanged();
                        hideEmptyState();
                    }
                });
                dialog.show();
            }
        });

        // 设置列表项点击事件
        timerListView.setOnItemClickListener((parent, view, position, id) -> {
            TimerPack selectedPack = timerPacks.get(position);
            // 打开计时器详情页面
            Intent intent = new Intent(MainActivity.this, TimerDetailActivity.class);
            intent.putExtra("timer_pack_index", position);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 刷新数据
        timerPacks = TimerStorage.loadTimerPacks(this);
        adapter.updateData(timerPacks);
        if (timerPacks.isEmpty()) {
            showEmptyState();
        } else {
            hideEmptyState();
        }
    }

    private void showEmptyState() {
        timerListView.setVisibility(View.GONE);
        emptyStateText.setVisibility(View.VISIBLE);
    }

    private void hideEmptyState() {
        timerListView.setVisibility(View.VISIBLE);
        emptyStateText.setVisibility(View.GONE);
    }

    // 删除计时器包
    public void deleteTimerPack(int position) {
        timerPacks.remove(position);
        TimerStorage.saveTimerPacks(this, timerPacks);
        adapter.notifyDataSetChanged();
        if (timerPacks.isEmpty()) {
            showEmptyState();
        }
    }
}
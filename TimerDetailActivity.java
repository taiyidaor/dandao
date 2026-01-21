package com.example.timerapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class TimerDetailActivity extends AppCompatActivity {

    private TimerPack currentTimerPack;
    private int timerPackIndex;
    private CountDownTimer countDownTimer;
    private boolean isTimerRunning = false;
    private int currentSubTimerIndex = 0;

    // UI组件
    private TextView timerNameText;
    private TextView timeDisplayText;
    private TextView currentSubTimerNameText;
    private Button startButton;
    private Button pauseButton;
    private Button resetButton;
    private ListView subTimersListView;
    private SubTimerAdapter subTimerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer_detail);

        // 获取传递的计时器包索引
        timerPackIndex = getIntent().getIntExtra("timer_pack_index", -1);
        if (timerPackIndex == -1) {
            finish();
            return;
        }

        // 加载计时器包数据
        currentTimerPack = TimerStorage.loadTimerPacks(this).get(timerPackIndex);
        currentSubTimerIndex = currentTimerPack.getCurrentSubTimerIndex();

        // 初始化UI组件
        timerNameText = findViewById(R.id.timer_name_text);
        timeDisplayText = findViewById(R.id.time_display_text);
        currentSubTimerNameText = findViewById(R.id.current_subtimer_name_text);
        startButton = findViewById(R.id.start_button);
        pauseButton = findViewById(R.id.pause_button);
        resetButton = findViewById(R.id.reset_button);
        subTimersListView = findViewById(R.id.subtimers_list_view);

        // 设置计时器名称
        timerNameText.setText(currentTimerPack.getName());

        // 设置子计时器适配器
        subTimerAdapter = new SubTimerAdapter(this, currentTimerPack.getSubTimers(), currentSubTimerIndex);
        subTimersListView.setAdapter(subTimerAdapter);

        // 更新当前显示
        updateCurrentTimerDisplay();

        // 设置按钮点击事件
        startButton.setOnClickListener(v -> startTimer());
        pauseButton.setOnClickListener(v -> pauseTimer());
        resetButton.setOnClickListener(v -> resetTimer());

        // 返回按钮
        findViewById(R.id.back_button).setOnClickListener(v -> finish());
    }

    private void updateCurrentTimerDisplay() {
        if (currentTimerPack.getSubTimers().isEmpty()) {
            timeDisplayText.setText("00:00");
            currentSubTimerNameText.setText("无计时阶段");
            return;
        }

        SubTimer currentSubTimer = currentTimerPack.getSubTimers().get(currentSubTimerIndex);
        timeDisplayText.setText(formatTime(currentSubTimer.getRemainingTime()));
        currentSubTimerNameText.setText(currentSubTimer.getName());
        subTimerAdapter.setCurrentSubTimerIndex(currentSubTimerIndex);
        subTimerAdapter.notifyDataSetChanged();
    }

    private void startTimer() {
        if (isTimerRunning || currentTimerPack.getSubTimers().isEmpty()) return;

        isTimerRunning = true;
        startButton.setVisibility(View.GONE);
        pauseButton.setVisibility(View.VISIBLE);

        final SubTimer currentSubTimer = currentTimerPack.getSubTimers().get(currentSubTimerIndex);

        countDownTimer = new CountDownTimer(currentSubTimer.getRemainingTime() * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int seconds = (int) (millisUntilFinished / 1000);
                currentSubTimer.setRemainingTime(seconds);
                timeDisplayText.setText(formatTime(seconds));
            }

            @Override
            public void onFinish() {
                // 当前子计时器结束
                currentSubTimer.setStatus(SubTimer.Status.COMPLETED);

                // 播放提示音和振动
                NotificationUtil.playNotificationSound(TimerDetailActivity.this);
                NotificationUtil.vibrate(TimerDetailActivity.this);

                // 检查是否有下一个子计时器
                if (currentSubTimerIndex < currentTimerPack.getSubTimers().size() - 1) {
                    // 自动开始下一个子计时器
                    currentSubTimerIndex++;
                    currentTimerPack.setCurrentSubTimerIndex(currentSubTimerIndex);
                    updateCurrentTimerDisplay();
                    startTimer();
                } else {
                    // 所有子计时器都已完成
                    isTimerRunning = false;
                    startButton.setVisibility(View.VISIBLE);
                    pauseButton.setVisibility(View.GONE);
                    currentTimerPack.setStatus(TimerPack.Status.COMPLETED);
                }

                // 保存状态
                saveTimerState();
            }
        }.start();

        // 启动前台服务，确保锁屏时计时器继续运行
        startTimerService();
    }

    private void pauseTimer() {
        if (!isTimerRunning) return;

        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

        isTimerRunning = false;
        startButton.setVisibility(View.VISIBLE);
        pauseButton.setVisibility(View.GONE);

        // 保存状态
        saveTimerState();

        // 停止前台服务
        stopTimerService();
    }

    private void resetTimer() {
        pauseTimer();
        currentTimerPack.reset();
        currentSubTimerIndex = 0;
        updateCurrentTimerDisplay();
        saveTimerState();
    }

    private void startTimerService() {
        Intent serviceIntent = new Intent(this, TimerService.class);
        serviceIntent.putExtra("timer_pack_index", timerPackIndex);
        startService(serviceIntent);
    }

    private void stopTimerService() {
        Intent serviceIntent = new Intent(this, TimerService.class);
        stopService(serviceIntent);
    }

    private void saveTimerState() {
        currentTimerPack.setCurrentSubTimerIndex(currentSubTimerIndex);
        TimerStorage.saveTimerPack(this, currentTimerPack, timerPackIndex);
    }

    private String formatTime(int seconds) {
        int minutes = seconds / 60;
        int remainingSeconds = seconds % 60;
        return String.format("%02d:%02d", minutes, remainingSeconds);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 如果计时器正在运行，保存状态
        if (isTimerRunning) {
            saveTimerState();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        stopTimerService();
    }
}
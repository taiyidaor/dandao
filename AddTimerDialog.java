package com.example.timerapp;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class AddTimerDialog extends AlertDialog {

    private EditText timerNameInput;
    private LinearLayout subTimersContainer;
    private Button addSubTimerBtn;
    private Button saveBtn;
    private Button cancelBtn;
    private OnTimerAddedListener listener;

    public interface OnTimerAddedListener {
        void onTimerAdded(TimerPack timerPack);
    }

    public AddTimerDialog(Context context, OnTimerAddedListener listener) {
        super(context);
        this.listener = listener;
        init();
    }

    private void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_timer, null);
        setView(view);
        setTitle("新建计时器");

        timerNameInput = view.findViewById(R.id.timer_name_input);
        subTimersContainer = view.findViewById(R.id.subtimers_container);
        addSubTimerBtn = view.findViewById(R.id.add_subtimer_btn);
        saveBtn = view.findViewById(R.id.save_btn);
        cancelBtn = view.findViewById(R.id.cancel_btn);

        // 添加一个默认的子计时器
        addSubTimerForm();

        // 添加子计时器按钮点击事件
        addSubTimerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addSubTimerForm();
            }
        });

        // 保存按钮点击事件
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTimerPack();
            }
        });

        // 取消按钮点击事件
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private void addSubTimerForm() {
        final View subTimerForm = LayoutInflater.from(getContext()).inflate(R.layout.subtimer_form, null);
        
        // 删除按钮点击事件
        Button removeBtn = subTimerForm.findViewById(R.id.remove_subtimer_btn);
        removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 确保至少有一个子计时器
                if (subTimersContainer.getChildCount() > 1) {
                    subTimersContainer.removeView(subTimerForm);
                }
            }
        });

        subTimersContainer.addView(subTimerForm);
    }

    private void saveTimerPack() {
        String timerName = timerNameInput.getText().toString().trim();
        if (timerName.isEmpty()) {
            timerNameInput.setError("请输入计时器名称");
            return;
        }

        TimerPack timerPack = new TimerPack(timerName);

        // 收集所有子计时器
        for (int i = 0; i < subTimersContainer.getChildCount(); i++) {
            View formView = subTimersContainer.getChildAt(i);
            
            EditText nameInput = formView.findViewById(R.id.subtimer_name_input);
            EditText minutesInput = formView.findViewById(R.id.subtimer_minutes_input);
            EditText secondsInput = formView.findViewById(R.id.subtimer_seconds_input);

            String name = nameInput.getText().toString().trim();
            if (name.isEmpty()) {
                name = "阶段 " + (i + 1);
            }

            int minutes = 0;
            int seconds = 1;

            try {
                minutes = Integer.parseInt(minutesInput.getText().toString());
            } catch (NumberFormatException e) {
                minutes = 0;
            }

            try {
                seconds = Integer.parseInt(secondsInput.getText().toString());
            } catch (NumberFormatException e) {
                seconds = 1;
            }

            // 确保至少有1秒
            if (minutes == 0 && seconds == 0) {
                seconds = 1;
            }

            int totalSeconds = minutes * 60 + seconds;
            SubTimer subTimer = new SubTimer(name, totalSeconds);
            timerPack.addSubTimer(subTimer);
        }

        // 回调监听器
        if (listener != null) {
            listener.onTimerAdded(timerPack);
        }

        dismiss();
    }
}
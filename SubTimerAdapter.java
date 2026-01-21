package com.example.timerapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class SubTimerAdapter extends ArrayAdapter<SubTimer> {

    private Context context;
    private List<SubTimer> subTimers;
    private int currentSubTimerIndex;

    public SubTimerAdapter(Context context, List<SubTimer> subTimers, int currentSubTimerIndex) {
        super(context, 0, subTimers);
        this.context = context;
        this.subTimers = subTimers;
        this.currentSubTimerIndex = currentSubTimerIndex;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.subtimer_item, parent, false);
        }

        SubTimer subTimer = subTimers.get(position);

        TextView nameText = convertView.findViewById(R.id.subtimer_name);
        TextView durationText = convertView.findViewById(R.id.subtimer_duration);
        TextView statusIcon = convertView.findViewById(R.id.subtimer_status);

        nameText.setText(subTimer.getName());
        durationText.setText(formatDuration(subTimer.getDuration()));

        // 设置状态图标和样式
        if (position == currentSubTimerIndex) {
            convertView.setBackgroundResource(R.drawable.subtimer_item_active);
            
            if (subTimer.getStatus() == SubTimer.Status.RUNNING) {
                statusIcon.setText(R.string.status_running);
                statusIcon.setTextColor(context.getResources().getColor(R.color.colorSecondary));
            } else {
                statusIcon.setText(R.string.status_current);
                statusIcon.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            }
        } else {
            convertView.setBackgroundResource(0);
            
            if (subTimer.getStatus() == SubTimer.Status.COMPLETED) {
                statusIcon.setText(R.string.status_completed);
                statusIcon.setTextColor(context.getResources().getColor(R.color.colorSuccess));
                convertView.setAlpha(0.6f);
            } else {
                statusIcon.setText("");
                convertView.setAlpha(1.0f);
            }
        }

        return convertView;
    }

    public void setCurrentSubTimerIndex(int currentSubTimerIndex) {
        this.currentSubTimerIndex = currentSubTimerIndex;
    }

    private String formatDuration(int seconds) {
        int minutes = seconds / 60;
        int remainingSeconds = seconds % 60;
        return String.format("%02d:%02d", minutes, remainingSeconds);
    }
}
package com.example.timerapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class TimerPackAdapter extends ArrayAdapter<TimerPack> {

    private Context context;
    private ArrayList<TimerPack> timerPacks;

    public TimerPackAdapter(Context context, ArrayList<TimerPack> timerPacks) {
        super(context, 0, timerPacks);
        this.context = context;
        this.timerPacks = timerPacks;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.timer_pack_item, parent, false);
        }

        final TimerPack timerPack = timerPacks.get(position);

        TextView nameText = convertView.findViewById(R.id.timer_pack_name);
        TextView infoText = convertView.findViewById(R.id.timer_pack_info);
        Button deleteButton = convertView.findViewById(R.id.delete_button);

        nameText.setText(timerPack.getName());
        int subTimersCount = timerPack.getSubTimers().size();
        int totalDuration = timerPack.getTotalDuration();
        infoText.setText(subTimersCount + " 个阶段 · " + formatDuration(totalDuration));

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (context instanceof MainActivity) {
                    ((MainActivity) context).deleteTimerPack(position);
                }
            }
        });

        return convertView;
    }

    public void updateData(ArrayList<TimerPack> newTimerPacks) {
        this.timerPacks.clear();
        this.timerPacks.addAll(newTimerPacks);
        notifyDataSetChanged();
    }

    private String formatDuration(int seconds) {
        int minutes = seconds / 60;
        int remainingSeconds = seconds % 60;
        if (minutes > 0) {
            return String.format("%d分%d秒", minutes, remainingSeconds);
        } else {
            return String.format("%d秒", remainingSeconds);
        }
    }
}
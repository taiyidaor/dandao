package com.example.timerapp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TimerPack implements Serializable {
    private String id;
    private String name;
    private Status status;
    private int currentSubTimerIndex;
    private List<SubTimer> subTimers;

    public enum Status {
        IDLE, RUNNING, PAUSED, COMPLETED
    }

    public TimerPack(String name) {
        this.id = "pack_" + System.currentTimeMillis();
        this.name = name;
        this.status = Status.IDLE;
        this.currentSubTimerIndex = 0;
        this.subTimers = new ArrayList<>();
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getCurrentSubTimerIndex() {
        return currentSubTimerIndex;
    }

    public void setCurrentSubTimerIndex(int currentSubTimerIndex) {
        this.currentSubTimerIndex = currentSubTimerIndex;
    }

    public List<SubTimer> getSubTimers() {
        return subTimers;
    }

    public void addSubTimer(SubTimer subTimer) {
        this.subTimers.add(subTimer);
    }

    public void removeSubTimer(int index) {
        if (index >= 0 && index < subTimers.size()) {
            this.subTimers.remove(index);
        }
    }

    public void reset() {
        this.status = Status.IDLE;
        this.currentSubTimerIndex = 0;
        for (SubTimer subTimer : subTimers) {
            subTimer.reset();
        }
    }

    public int getTotalDuration() {
        int total = 0;
        for (SubTimer subTimer : subTimers) {
            total += subTimer.getDuration();
        }
        return total;
    }
}
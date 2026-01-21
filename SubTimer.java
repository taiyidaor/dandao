package com.example.timerapp;

import java.io.Serializable;

public class SubTimer implements Serializable {
    private String id;
    private String name;
    private int duration; // 总时长（秒）
    private int remainingTime; // 剩余时间（秒）
    private Status status;

    public enum Status {
        IDLE, RUNNING, PAUSED, COMPLETED
    }

    public SubTimer(String name, int duration) {
        this.id = "sub_" + System.currentTimeMillis() + "_" + Math.random();
        this.name = name;
        this.duration = duration;
        this.remainingTime = duration;
        this.status = Status.IDLE;
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

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
        this.remainingTime = duration;
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(int remainingTime) {
        this.remainingTime = remainingTime;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void reset() {
        this.remainingTime = duration;
        this.status = Status.IDLE;
    }
}
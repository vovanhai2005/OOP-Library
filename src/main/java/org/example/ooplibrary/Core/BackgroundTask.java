package org.example.ooplibrary.Core;

public class BackgroundTask implements Runnable {
    private final int taskId;

    public BackgroundTask(int taskId) {
        this.taskId = taskId;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
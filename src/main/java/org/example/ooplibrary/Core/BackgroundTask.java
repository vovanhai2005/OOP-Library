package org.example.ooplibrary.Core;

public class BackgroundTask implements Runnable {
    private final int taskId;

    public BackgroundTask(int taskId) {
        this.taskId = taskId;
    }

    @Override
    public void run() {
        // Simulate a background task
        for (int i = 0; i < 5; i++) {
            try {
                Thread.sleep(1000); // Simulate time-consuming task
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
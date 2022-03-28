// CSC 331 JavaFX Group Project
// By: Aaron Csetter, Nicholas Bradley, Noah Davis, Steven McCarthy
package edu.uncw.timer;

import java.util.ArrayList;

/**
 * An asynchronous timer object that updates every second.
 * <p>
 *     A {@link TimerListener} must be implemented and added to the Timer via {@link #addTimerListener(TimerListener)} to receive callback information from the timer while it's running.
 * </p>
 */
public class Timer implements Runnable {
    private ArrayList<TimerListener> listeners;
    private Thread t;
    private volatile boolean flag;
    private volatile boolean active;
    private int seconds = 0;

    public Timer(TimerListener timerListener) {
        this.addTimerListener(timerListener);
    }

    /**
     * Called on via a separate thread to run the timer.
     * <br>
     * Calls {@link TimerListener#onStart(int)} every second.
     * <p><strong>
     *     Do not call run directly. Use {@link #start()} method instead.
     * </strong></p>
     */
    @Override
    public void run() {
        listeners.forEach(listener -> listener.onStart(seconds));
        long time = System.currentTimeMillis();
        do {
            if (System.currentTimeMillis() - time >= 1000) {
                time = System.currentTimeMillis();
                if (this.active) seconds++;
                listeners.forEach(listener -> listener.onUpdate(seconds));
            }
        } while (flag);
    }

    /**
     * Start's the Timer on a separate thread.
     * <br>
     * Calls {@link TimerListener#onStart(int)}.
     */
    public void start() {
        if (t == null) {
            this.flag = true;
            this.active = true;
            t = new Thread (this);
            t.start();
        }
    }

    /**
     * Restarts the timer at zero seconds while keeping the thread alive.
     * <br>
     * Calls {@link TimerListener#onReset(int)}
     */
    public void reset() {
        if (t != null) {
            listeners.forEach(listener -> listener.onReset(seconds));
        }

        this.active = true;
        this.seconds = 0;
    }

    /**
     * Keeps the Thread alive but pauses timer incrementation.
     */
    public void pause() {
        this.active = false;
    }

    /**
     * Resumes timer incrementation after being paused.
     */
    public void resume() {
        this.active = true;
    }

    /**
     * Add an implemented {@link TimerListener} to the Timer to invoke the interface methods while running.
     * <br>
     * You may add multiple listeners to the Timer.
     * @param listener an implemented {@link TimerListener} class object.
     */
    public void addTimerListener(TimerListener listener) {
        if (listeners == null) {
            listeners = new ArrayList<>();
        }

        listeners.add(listener);
    }

    /**
     * Removes all registered {@link TimerListener}s from the Timer.
     */
    public void removeAllListeners() {
        listeners = null;
    }

    /**
     * Safely stops the Timer and disposes of the respective thread. <br>
     *     Calls {@link TimerListener#onStop(int)}
     */
    public void dispose() {
        if (t != null) {
            this.flag = false;

            try {
                t.join();
                t = null;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            listeners.forEach(listener -> listener.onStop(seconds));
            removeAllListeners();
        }
    }
}

// CSC 331 JavaFX Group Project
// By: Aaron Csetter, Nicholas Bradley, Noah Davis, Steven McCarthy
package edu.uncw.timer;

/**
 * An implementable listener interface invoked on {@link Timer} events.
 * <p><strong>
 *     This listener must be registered using {@link Timer#addTimerListener(TimerListener)}.
 * </strong></p>
 * <p>
 *     The {@link Timer} only invokes these methods while a thread exists and is alive.
 * </p>
 */
public interface TimerListener {
    /**
     * Invoked when {@link Timer#start()} is called and a thread is created.
     * @param seconds int of seconds on the timer.
     */
    void onStart(int seconds);

    /**
     * Invoked when a running {@link Timer#dispose()} is called and a thread rejoins the main thread and disposed of.
     * @param seconds int of seconds on the timer.
     */
    void onStop(int seconds);

    /**
     * Invoked when a running {@link Timer#reset()} is called and the timer's seconds is reset to zero.
     * @param seconds int of seconds on the timer.
     */
    default void onReset(int seconds) {};

    /**
     * Invoked every time the Timer's seconds are updated while {@link Timer#run()} is running.
     * @param seconds int of seconds on the timer before reset.
     */
    void onUpdate(int seconds);
}

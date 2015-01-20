package com.spix.jobqueue.log;

/**
 * You can provide your own logger implementation to {@link com.spix.jobqueue.JobManager}
 * it is very similar to Roboguice's logger
 */
public interface CustomLogger {
    /**
     * JobManager may call this before logging sth that is (relatively) expensive to calculate
     * @return
     */
    public boolean isDebugEnabled();
    public void d(String text, Object... args);
    public void e(Throwable t, String text, Object... args);
    public void e(String text, Object... args);
}

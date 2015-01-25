package com.spix.jobqueue;

/**
 * Identifies the current status of a job if it is in the queue
 */
public enum JobStatus {
    /**
     * Job is in the queue but cannot run yet.
     * As of v 1.1, this might be:
     *     Job requires network but there is no available network connection
     *     Job is delayed. We are waiting for the time to pass
     */
    WAITING_NOT_READY,
    /**
     * Job is in the queue, ready to be run. Waiting for an available consumer.
     */
    WAITING_READY,
    /**
     * Job is being executed by one of the runners.
     */
    RUNNING,
    /**
     * Job is not known by job queue.
     * This might be:
     *    Invalid ID
     *    Job has been completed
     *    Job has failed
     *    Job has just been added, about to be delivered into a queue
     */
    UNKNOWN
}

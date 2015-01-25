package com.spix.jobmanager.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.spix.jobmanager.R;
import com.spix.jobqueue.Job;
import com.spix.jobqueue.JobManager;
import com.spix.jobqueue.Params;
import com.spix.jobqueue.config.Configuration;

import java.util.concurrent.atomic.AtomicInteger;

public class MainActivity extends Activity {

    private JobManager jobManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Configuration configs = new Configuration.Builder(getApplicationContext()).id("test").loadFactor(100).build();
        this.jobManager = new JobManager(getApplicationContext(), configs);
        this.jobManager.start();
        this.jobManager.setOnAllJobsFinishedListener(new JobManager.OnAllJobsFinishedListener() {
            @Override
            public void onAllJobsFinished() {
                Log.d("Job", "On all jobs finished callback");
            }
        });
        jobManager.addJobInBackground(new SimpleJob(jobManager));
    }

    private static class SimpleJob extends Job {

        private static AtomicInteger i = new AtomicInteger(0);
        private final JobManager jobManager;

        protected SimpleJob(JobManager jobManager) {
            super(new Params(1).setRequiresNetwork(false));
            this.jobManager = jobManager;
        }

        @Override
        public void onAdded() {
        }

        @Override
        public void onRun() throws Throwable {
            Log.d("Job", "Threadid: " + Thread.currentThread().getId() + "  job Nr: " + i.get());
            Thread.sleep(100);

            if (i.get() > 20) {
                return;
            }

            jobManager.addJobInBackground(new SimpleJob(jobManager));

            i.incrementAndGet();
        }

        @Override
        protected void onCancel() {
        }

        @Override
        protected boolean shouldReRunOnThrowable(Throwable throwable) {
            return false;
        }
    }


}

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
        Configuration configs = new Configuration.Builder(getApplicationContext()).id("test").loadFactor(100).startWhenInitialized(false).build();
        this.jobManager = new JobManager(getApplicationContext(), configs);
        jobManager.addJob(new SimpleJob());
        jobManager.start();
    }

    private static class SimpleJob extends Job {

        private static AtomicInteger i = new AtomicInteger(0);

        protected SimpleJob() {
            super(new Params(1).setRequiresNetwork(false).setPersistent(true));
        }

        @Override
        public void onAdded() {
            Log.d("Job", "onAdded: ctx" + getContext());

        }

        @Override
        public void onRun() throws Throwable {

            Log.d("Job", "onRun: ctx" + getContext());
            Log.d("Job", "before sleep Threadid: " + Thread.currentThread().getId() + "  job Nr: " + i.get() + getContext().getString(R.string.abc_action_bar_home_description));
            Thread.sleep(10000);
            Log.d("Job", "after sleep Threadid: " + Thread.currentThread().getId() + "  job Nr: " + i.get());
//            getJobManager().addJob(new SimpleJob());

        }

        @Override
        protected void onCancel() {
            Log.d("Job", "onCancel: ctx" + getContext());
        }

        @Override
        protected boolean shouldReRunOnThrowable(Throwable throwable) {
            Log.d("Job", "shouldReRunOnThrowable: ctx" + getContext());
            Log.d("Job", "error: " + throwable.getMessage());
            return false;
        }

    }


}

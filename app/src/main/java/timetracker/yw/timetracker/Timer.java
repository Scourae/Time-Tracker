package timetracker.yw.timetracker;

import android.os.Handler;

public class Timer {

    private Handler mHandler;
    private TimerFragment mTimerFragment;
    private Timing mTiming;

    private class Timing implements Runnable {

        public int millisecondInterval;

        Timing(int millisecondInterval) {
            this.millisecondInterval = millisecondInterval;
        }

        @Override
        public void run() {
            update();
            mHandler.postDelayed(this, millisecondInterval);
        }
    }

    public Timer(TimerFragment timerFragment, int millisecondInterval) {
        this.mTimerFragment = timerFragment;
        mTiming = new Timing(millisecondInterval);
    }

    public void update(){
        mTimerFragment.updateTime(1);
    }

    public void start() {
        mHandler = new Handler();
        mHandler.postDelayed(mTiming, mTiming.millisecondInterval);
    }

    public void stop() {
        mHandler.removeCallbacks(mTiming);
    }
}

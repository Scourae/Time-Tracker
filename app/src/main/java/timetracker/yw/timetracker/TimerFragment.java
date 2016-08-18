package timetracker.yw.timetracker;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class TimerFragment extends Fragment {

    private final String emptyTaskButton = "Select your task";
    private final String taskDialogue = "Choose a task";

    // appearance
    private View mView;
    private TextView mTimerText;
    // time tracking
    private long timeCount;
    private Timer mTimer;
    private Calendar start;
    // button state
    private State currentState;
    // time display
    private String timeFormat = "HH:mm:ss";
    private String timeFormatFull = "HH:mm:ss yyyy/MM/dd";
    SimpleDateFormat sdf;
    SimpleDateFormat sdfStart;
    // time storage
    List<Task> tasks;
    // current task
    String taskID;
    Button mTaskButton;
    ToDoFragment mToDoFragment;

    public enum State {
        stop,
        start,
        pause,
    }

    public TimerFragment() {
        mTimer = new Timer(this, 1000);
        currentState = State.stop;
        sdf = new SimpleDateFormat(timeFormat, Locale.CANADA);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        sdfStart = new SimpleDateFormat(timeFormatFull, Locale.CANADA);
        taskID = "";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mView = inflater.inflate(R.layout.timer_fragment, container, false);

        MainActivity currentActivity = (MainActivity) getActivity();
        mToDoFragment = currentActivity.getToDoFragment();
        mTaskButton = (Button) mView.findViewById(R.id.timer_task);
        updateTask();
        mTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectTask();
            }
        });

        mTimerText = (TextView) mView.findViewById(R.id.timer_text);
        mTimerText.setText(sdf.format(new Date(timeCount * 1000)));

        Button startButton = (Button) mView.findViewById(R.id.timer_start);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startClick();
            }
        });
        Button pauseButton = (Button) mView.findViewById(R.id.timer_pause);
        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseClick();
            }
        });
        Button stopButton = (Button) mView.findViewById(R.id.timer_stop);
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopClick();
            }
        });

        return mView;
    }

    public void updateTime(long time) {
        timeCount += time;
        mTimerText.setText(sdf.format(new Date(timeCount * 1000)));
    }

    private void updateTask() {
        if (taskID.isEmpty()) {
            mTaskButton.setText(emptyTaskButton);
        }
        else {
            Task currentTask = mToDoFragment.getTaskById(taskID);
            if (currentTask == null) {
                taskID = "";
                mTaskButton.setText(emptyTaskButton);
            }
            else {
                mTaskButton.setText(currentTask.getName());
            }
        }
    }

    private void selectTask() {
        if (mToDoFragment.getTasks().size() == 0) {
            Toast.makeText(getActivity(), "No tasks yet", Toast.LENGTH_SHORT).show();
        }
        else if (currentState == State.start) {
            Toast.makeText(getActivity(), "Current task is running", Toast.LENGTH_SHORT).show();
        }
        else {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
            dialogBuilder.setTitle(taskDialogue);
            List<Task> taskOptions = mToDoFragment.getTasks();
            CharSequence[] taskNames = new CharSequence[taskOptions.size() + 1];
            taskNames[0] = "";
            for (int i = 0; i < taskOptions.size(); i++) {
                taskNames[i + 1] = taskOptions.get(i).getName();
            }
            dialogBuilder.setSingleChoiceItems(taskNames, -1, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int item) {
                    if (item == 0) {
                        taskID = "";
                    } else {
                        taskID = mToDoFragment.getTaskByPosition(item - 1).getID();
                    }
                    updateTask();
                    dialog.dismiss();
                }
            });
            AlertDialog selectTask = dialogBuilder.create();
            selectTask.show();
        }
    }

    private void setButtonState(State state) {
        /*
        Button startButton = (Button) mView.findViewById(R.id.timer_start);
        Button pauseButton = (Button) mView.findViewById(R.id.timer_pause);
        Button stopButton = (Button) mView.findViewById(R.id.timer_stop);
        if (state == State.stop) {
            startButton.setClickable(true);
            startButton.setBackgroundColor(0x00ff00);
            pauseButton.setClickable(false);
            pauseButton.setBackgroundColor(0x222222);
            stopButton.setClickable(false);
            stopButton.setBackgroundColor(0x222222);
        }
        else if (state == State.start) {
            startButton.setClickable(false);
            startButton.setBackgroundColor(0x222222);
            pauseButton.setClickable(true);
            pauseButton.setBackgroundColor(0xffa500);
            stopButton.setClickable(true);
            stopButton.setBackgroundColor(0xff0000);
        }
        else if (state == State.pause) {
            startButton.setClickable(true);
            startButton.setBackgroundColor(0x00ff00);
            pauseButton.setClickable(false);
            pauseButton.setBackgroundColor(0x222222);
            stopButton.setClickable(true);
            stopButton.setBackgroundColor(0xff0000);
        }
        */
    }

    private void startClick() {
        if ((currentState != State.start)&&(!taskID.equals(""))) {
            TextView startText = (TextView) mView.findViewById(R.id.start_time_text);
            String startTime = "Started: " + sdfStart.format(new Date());
            startText.setText(startTime);
            timeCount = 0;
            start = Calendar.getInstance();
            updateTime(0);
            mTimer.start();
            currentState = State.start;
            setButtonState(currentState);
        }
    }

    private void pauseClick() {
        mTimer.stop();
        currentState = State.pause;
        setButtonState(currentState);
    }

    private void stopClick() {
        mTimer.stop();
        currentState = State.stop;
        setButtonState(currentState);
    }
}

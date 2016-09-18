package timetracker.yw.timetracker;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.PriorityQueue;

public class CalendarDayFragment extends Fragment implements View.OnClickListener {
    private DayAdapter mDayAdapter;
    private TaskManager mTaskManager;
    private DateAdapter mDateAdapter;
    private Context mContext;
    private String timeFormat = "HH:mm:ss";
    private SimpleDateFormat sdf;
    private Button leftArrow;
    private Button rightArrow;
    private Button timeSelect;
    private TextView intro;
    private TextView nothing;
    private int year;
    private int month;
    private int day;

    public CalendarDayFragment() {
        sdf = new SimpleDateFormat(timeFormat);
    }

    public void setTasks(List<Task> tasks) {
        if (tasks.size() == 0) {
            intro.setVisibility(View.GONE);
            nothing.setVisibility(View.VISIBLE);
        }
        else {
            intro.setVisibility(View.VISIBLE);
            nothing.setVisibility(View.GONE);
        }
        mDayAdapter.updateTasks(tasks);
    }

    public void setTaskManager(TaskManager manager) {
        mTaskManager = manager;
    }

    public void setDateAdapter(DateAdapter dateAdapter) {
        this.mDateAdapter = dateAdapter;
    }

    public void updateTasks(Date date) {
        setTasks(mTaskManager.getTaskByDate(date));
        setYearMonthDayUpdate(date);
        mDateAdapter.setMonth(month);
        mDateAdapter.setYear(year);
    }

    public void setYearMonthDayUpdate(Date date) {
        setYearMonthDay(date);
        timeSelect.setText(Integer.toString(month+1) + "/" + Integer.toString(day));
    }

    public void setYearMonthDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        this.year = cal.get(Calendar.YEAR);
        this.month = cal.get(Calendar.MONTH);
        this.day = cal.get(Calendar.DAY_OF_MONTH);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mDayAdapter = new DayAdapter();
        View view = inflater.inflate(R.layout.calendar_day_fragment, container, false);
        leftArrow = (Button) view.findViewById(R.id.calendar_day_left_arrow);
        leftArrow.setOnClickListener(this);
        rightArrow = (Button) view.findViewById(R.id.calendar_day_right_arrow);
        rightArrow.setOnClickListener(this);
        timeSelect = (Button) view.findViewById(R.id.calendar_day_time_select);
        timeSelect.setOnClickListener(this);
        intro = (TextView) view.findViewById(R.id.calendar_day_intro);
        nothing = (TextView) view.findViewById(R.id.calendar_day_nothing);
        Calendar cal = new GregorianCalendar(year, month, day);
        updateTasks(cal.getTime());
        return view;
    }

    @Override
    public void onClick(View view) {
        if (view == leftArrow) {
            if (day == 1) {
                if (month == 0) {
                    year--;
                    month = 11;
                }
                else {
                    month--;
                }
                Calendar cal = new GregorianCalendar(year, month, day);
                day = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
            }
            else {
                day--;
            }
        }
        else if (view == rightArrow) {
            Calendar cal = new GregorianCalendar(year, month, day);
            if (day == cal.getActualMaximum(Calendar.DAY_OF_MONTH)) {
                if (month == 11) {
                    month = 0;
                    year++;
                }
                else {
                    month++;
                }
                day = 1;
            }
            else {
                day++;
            }
        }
        Calendar cal = new GregorianCalendar(year, month, day);
        updateTasks(cal.getTime());
    }

    private class DayAdapter extends ArrayAdapter<Task> {
        private List<NamedTimeSegment> mTimeSegments;
        private Context mContext;

        public DayAdapter() {
            super(getActivity(), -1);
            mContext = getActivity();
            mTimeSegments = new ArrayList<>();
        }

        public void updateTasks(List<Task> tasks) {
            mTimeSegments.clear();
            for (int i = 0; i < tasks.size(); i++) {
                List<TimeSegment> segments = tasks.get(i).getTimeList();
                for (int j = 0; j < segments.size(); j++) {
                    mTimeSegments.add(new NamedTimeSegment(tasks.get(i).getName(), segments.get(j)));
                }

            }
            Collections.sort(mTimeSegments, new Comparator<NamedTimeSegment>() {
                @Override
                public int compare(NamedTimeSegment lhs, NamedTimeSegment rhs) {
                    if (lhs.getBegin().before(rhs.getBegin())) {
                        return -1;
                    }
                    else if (lhs.getBegin().after(rhs.getBegin())) {
                        return 1;
                    }
                    return 0;
                }
            });
            notifyDataSetChanged();
        }

        class ViewHolderItem {
            TextView title;
            TextView time;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolderItem viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolderItem();
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.calendar_day_task_item, parent, false);
                viewHolder.title = (TextView) convertView.findViewById(R.id.calendar_day_item_title);
                viewHolder.time = (TextView) convertView.findViewById(R.id.calendar_day_item_time);
                convertView.setTag(viewHolder);
            }
            else {
                viewHolder = (ViewHolderItem) convertView.getTag();
            }
            viewHolder.title.setText(mTimeSegments.get(position).getName());
            String timeText = sdf.format(mTimeSegments.get(position).getBegin()) + " to " + sdf.format(mTimeSegments.get(position).getEnd());
            viewHolder.time.setText(timeText);
            return convertView;
        }

        @Override
        public int getCount() {
            return mTimeSegments.size();
        }
    }
}

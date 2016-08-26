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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CalendarDayFragment extends Fragment implements View.OnClickListener {
    private DayAdapter mDayAdapter;
    private Context mContext;
    private String timeFormat = "HH:mm:ss";
    private SimpleDateFormat sdf;
    private Button leftArrow;
    private Button rightArrow;
    private Button timeSelect;
    private int month;
    private int day;

    public CalendarDayFragment() {
        mDayAdapter = new DayAdapter();
        sdf = new SimpleDateFormat(timeFormat);
    }

    public void setTasks(List<Task> tasks) {
        mDayAdapter.updateTasks(tasks);
    }

    public void setMonthAndDay(int month, int day) {
        this.month = month;
        this.day = day;
        timeSelect.setText(Integer.toString(month) + "/" + Integer.toString(day));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.calendar_day_fragment, container, false);
        leftArrow = (Button) view.findViewById(R.id.calendar_day_left_arrow);
        rightArrow = (Button) view.findViewById(R.id.calendar_day_right_arrow);
        timeSelect = (Button) view.findViewById(R.id.calendar_day_time_select);

        return view;
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
    }
}

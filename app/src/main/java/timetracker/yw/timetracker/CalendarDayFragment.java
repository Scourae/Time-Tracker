package timetracker.yw.timetracker;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CalendarDayFragment extends Fragment {
    private DayAdapter mDayAdapter;
    private Context mContext;

    public CalendarDayFragment() {
        mDayAdapter = new DayAdapter();
    }

    public void setTasks(List<Task> tasks) {
        mDayAdapter.updateTasks(tasks);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

    }

    private class DayAdapter extends ArrayAdapter<Task> {
        private List<TimeSegment> mTimeSegments;
        private Context mContext;

        public DayAdapter() {
            super(getActivity(), -1);
            mContext = getActivity();
            mTimeSegments = new ArrayList<>();
        }

        public void updateTasks(List<Task> tasks) {
            mTimeSegments.clear();
            for (int i = 0; i < tasks.size(); i++) {
                mTimeSegments.addAll(tasks.get(i).getTimeList());
            }
            Collections.sort(mTimeSegments, new Comparator<TimeSegment>() {
                @Override
                public int compare(TimeSegment lhs, TimeSegment rhs) {
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

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO:
        }
    }
}

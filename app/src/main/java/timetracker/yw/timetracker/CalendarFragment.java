package timetracker.yw.timetracker;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class CalendarFragment extends Fragment {
    private int testCount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        testCount += 1;
        View calendarView = inflater.inflate(R.layout.calendar_fragment, container, false);

        TextView calendarText = (TextView) calendarView.findViewById(R.id.calendar_text);
        calendarText.setText(Integer.toString(testCount));

        return calendarView;
    }
}

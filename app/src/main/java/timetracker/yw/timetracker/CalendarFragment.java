package timetracker.yw.timetracker;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;

public class CalendarFragment extends Fragment implements View.OnClickListener {
    private int testCount;
    private int currMonth;
    private int currYear;
    private Button timeSelect;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View calendarView = inflater.inflate(R.layout.calendar_fragment, container, false);
        currMonth = Calendar.MONTH;
        currYear = Calendar.YEAR;
        timeSelect = (Button) calendarView.findViewById(R.id.calendar_time_select);
        String time = Integer.toString(currYear) + "/";
        if (currMonth < 9) {
            time += "0";
        }
        time += Integer.toString(currMonth+1);
        timeSelect.setText(time);

        return calendarView;
    }

    @Override
    public void onClick(View view) {

    }
}

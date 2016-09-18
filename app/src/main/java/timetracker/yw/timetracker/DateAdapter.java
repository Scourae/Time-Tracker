package timetracker.yw.timetracker;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.Console;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateAdapter extends BaseAdapter implements View.OnClickListener {
    private List<DateInfo> mList;
    private SimpleDateFormat sdf;
    private TaskManager mTaskManager;
    private Activity mActivity;
    private CalendarDayFragment mDayFragment;
    private int month;
    private int year;

    private class DateInfo {
        public String text;
        public String tag;
        public DateInfo(String text, String tag) {
            this.text = text;
            this.tag = tag;
        }
    }

    public DateAdapter() {
        super();
        mList = new ArrayList<>();
        sdf = new SimpleDateFormat("yyyy_MM_dd");
        month = Calendar.getInstance().get(Calendar.MONTH);
        year = Calendar.getInstance().get(Calendar.YEAR);
        setDates();
    }

    public void setTaskManager(TaskManager taskManager) {
        mTaskManager = taskManager;
        if (mDayFragment != null) {
            mDayFragment.setTaskManager(mTaskManager);
        }
    }

    public void setActivity(Activity activity) {
        mActivity = activity;
        if (mDayFragment != null) {
            mDayFragment.setDateAdapter(this);
        }
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public void onClick(View view) {
        if (mDayFragment == null) {
            mDayFragment = new CalendarDayFragment();
            mDayFragment.setTaskManager(mTaskManager);
            mDayFragment.setDateAdapter(this);
        }
        String year_month_day = (String) view.getTag(R.id.CALENDAR_DETAIL_ID);
        Log.d("", year_month_day);
        if (year_month_day != null) {
            try {
                Date date = sdf.parse(year_month_day);
                mDayFragment.setYearMonthDay(date);
                FragmentManager fragmentManager = mActivity.getFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.content_frame, mDayFragment);
                transaction.commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String getItem(int position) {
        return mList.get(position).text;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class ViewHolderItem {
        Button gridItem;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderItem viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolderItem();
            LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.calendar_grid_item, parent, false);
            viewHolder.gridItem = (Button) convertView.findViewById(R.id.calendar_grid_item);
            viewHolder.gridItem.setOnClickListener(this);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolderItem) convertView.getTag();
        }
        viewHolder.gridItem.setText(mList.get(position).text);
        viewHolder.gridItem.setTag(R.id.CALENDAR_DETAIL_ID, mList.get(position).tag);
        if (!mList.get(position).text.equals(" ")) {
            viewHolder.gridItem.setBackgroundColor(0xFF87CEFA);
        }
        else {
            viewHolder.gridItem.setBackgroundColor(0x00000000);
        }
        return convertView;
    }

    public int getMonth() {
        return this.month;
    }

    public int getYear() {
        return this.year;
    }

    public void setMonth(int month) {
        this.month = month;
        setDates();
    }
    public void setYear(int year) {
        this.year = year;
        setDates();
    }

    private void setDates() {
        mList.clear();
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        int blanks = cal.get(Calendar.DAY_OF_WEEK) - 1;
        int days = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        for (int i = 0; i < blanks; i++) {
            mList.add(new DateInfo(" ", ""));
        }
        Log.d("", Integer.toString(days));
        Log.d("", Integer.toString(blanks));
        for (int i = 0; i < days; i++) {
            String year_month_day = Integer.toString(year) + "_" + Integer.toString(month) + "_" + Integer.toString(i + 1);
            mList.add(new DateInfo(Integer.toString(i+1), year_month_day));
        }
        int remain = 7 - mList.size() % 7;
        for (int i = 0; i < remain; i++) {
            mList.add(new DateInfo(" ", ""));
        }
    }
}

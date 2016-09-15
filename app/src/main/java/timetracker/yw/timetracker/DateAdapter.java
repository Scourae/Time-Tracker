package timetracker.yw.timetracker;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.view.View;
import android.widget.BaseAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DateAdapter extends BaseAdapter implements View.OnClickListener {
    private List<String> mList;
    private SimpleDateFormat sdf;
    private TaskManager mTaskManager;
    private Activity mActivity;
    private CalendarDayFragment mDayFragment;

    public DateAdapter() {
        mList = new ArrayList<>();
        sdf = new SimpleDateFormat("yyyy-MM-dd");
        mDayFragment = new CalendarDayFragment();
        mDayFragment.setTaskManager(mTaskManager);
    }

    public void setTaskManager(TaskManager taskManager) {
        mTaskManager = taskManager;
    }

    public void setActivity(Activity activity) {
        mActivity = activity;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public void onClick(View view) {
        String date_month_year = (String) view.getTag();
        try {
            Date date = sdf.parse(date_month_year);
            mDayFragment.updateTasks(date);
            FragmentManager fragmentManager = mActivity.getFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.content_frame, mDayFragment);
            transaction.commit();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getItem(int position) {
        return mList.get(position);
    }
}

package timetracker.yw.timetracker;

import android.view.View;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

public class DateAdapter extends BaseAdapter implements View.OnClickListener {
    private List<String> mList;

    public DateAdapter() {
        mList = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public void onClick(View view) {
        
    }
}

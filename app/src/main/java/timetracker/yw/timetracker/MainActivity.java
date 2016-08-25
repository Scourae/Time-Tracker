package timetracker.yw.timetracker;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private String[] sideBarList;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;

    private int currentPostition;

    private FragmentManager mFragmentManager;
    private ToDoFragment mToDoFragment;
    private TimerFragment mTimerFragment;
    private CalendarFragment mCalendarFragment;
    private AnalysisFragment mAnalysisFragment;

    private TaskManager mTaskManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        currentPostition = -1;

        // left hand side bar stuff
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        sideBarList = getResources().getStringArray(R.array.side_bar);
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.side_bar_item, sideBarList));
        mDrawerList.setOnItemClickListener(new SideBarClickListener());
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        // action bar part of side bar
        mTitle = mDrawerTitle = getTitle();
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close)
        {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.addDrawerListener(mDrawerToggle);

        // load tasks
        mTaskManager = new TaskManager();
        mTaskManager.getTasksFromFile();

        // fragments
        mFragmentManager = getFragmentManager();
        mToDoFragment = new ToDoFragment();
        mToDoFragment.SetTaskList(mTaskManager);
        mTimerFragment = new TimerFragment();
        mCalendarFragment = new CalendarFragment();
        mCalendarFragment.setTaskManager(mTaskManager);
        mCalendarFragment.setActivity(this);
        mAnalysisFragment = new AnalysisFragment();
        if (savedInstanceState == null) {
            selectItem(0);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch(item.getItemId()) {
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public ToDoFragment getToDoFragment() {
        return mToDoFragment;
    }

    private class SideBarClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {
        if (currentPostition != position) {
            DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
            mDrawerLayout.closeDrawers();

            FragmentTransaction transaction = mFragmentManager.beginTransaction();
            switch (position) {
                case 0:
                    // to task list
                    transaction.replace(R.id.content_frame, mToDoFragment);
                    transaction.commit();
                    break;
                case 1:
                    // to timer
                    transaction.replace(R.id.content_frame, mTimerFragment);
                    transaction.commit();
                    break;
                case 2:
                    // to calendar
                    transaction.replace(R.id.content_frame, mCalendarFragment);
                    transaction.commit();
                    break;
                case 3:
                    // to analysis
                    transaction.replace(R.id.content_frame, mAnalysisFragment);
                    transaction.commit();
                    break;
            }
        }
    }
}

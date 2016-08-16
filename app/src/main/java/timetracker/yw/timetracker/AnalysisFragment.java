package timetracker.yw.timetracker;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class AnalysisFragment extends Fragment {
    private int testCount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        testCount += 1;
        View analysisView = inflater.inflate(R.layout.analysis_fragment, container, false);

        TextView analysisText = (TextView) analysisView.findViewById(R.id.analysis_text);
        analysisText.setText(Integer.toString(testCount));

        return analysisView;
    }
}

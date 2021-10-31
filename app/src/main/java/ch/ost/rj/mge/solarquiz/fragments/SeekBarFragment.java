package ch.ost.rj.mge.solarquiz.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import ch.ost.rj.mge.solarquiz.R;
import ch.ost.rj.mge.solarquiz.helper.DataInterface;

public class SeekBarFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    SeekBar sb;
    TextView sbValueTextView;
    DataInterface dataPasser;
    public int max;

    public SeekBarFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            max = getArguments().getInt("max");
        }
    }

    SeekBar.OnSeekBarChangeListener sbl = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            dataPasser.onResult(seekBar.getProgress());
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress,
        boolean fromUser) {
            sbValueTextView.setText(Integer.toString(progress));
        }
    };

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        dataPasser = (DataInterface) context;
        dataPasser.onResult("PASS SUCCESSFUL"); // Pass result back to activity
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_slider, container, false);
        sb = (SeekBar) view.findViewById(R.id.seekBar);
        sbValueTextView = (TextView)view.findViewById(R.id.seekBarValueText);
        sb.setOnSeekBarChangeListener(sbl);
        sb.setMax(max);
        return view;
    }
}
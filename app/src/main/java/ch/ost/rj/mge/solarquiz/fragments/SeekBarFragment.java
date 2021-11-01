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
    SeekBar sb;
    TextView sbValueTextView;
    DataInterface dataPasser;
    public int stepSize;
    public int startValue;

    public SeekBarFragment() {
        // Required empty public constructor
    }

    public static SeekBarFragment newInstance(int startValue, int stepSize) {
        SeekBarFragment slider = new SeekBarFragment();
        Bundle args = new Bundle();
        args.putInt("stepSize", stepSize);
        args.putInt("startValue", startValue);
        slider.setArguments(args);
        return slider;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            stepSize = getArguments().getInt("stepSize");
            startValue = getArguments().getInt("startValue");
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
            //Log.i("hello", Integer.toString(startValue));
            //Log.i("hello", Integer.toString(stepSize));
            sbValueTextView.setText(Integer.toString(startValue + progress * stepSize));
        }
    };

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        dataPasser = (DataInterface) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_slider, container, false);
        sb = (SeekBar) view.findViewById(R.id.seekBar);
        sbValueTextView = (TextView)view.findViewById(R.id.seekBarValueText);
        sb.setOnSeekBarChangeListener(sbl);
        sb.setMax(9);
        return view;
    }
}
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
import ch.ost.rj.mge.solarquiz.helper.SliderQuestion;

public class SeekBarFragment extends Fragment {
    SeekBar sb;
    TextView sbValueTextView;
    DataInterface dataPasser;
    public static final String ARG_SLIDER_QUESTION = "sliderQuestion";
    public SliderQuestion sliderQuestion;

    public SeekBarFragment() {
        // Required empty public constructor
    }

    public static SeekBarFragment newInstance(SliderQuestion sliderQuestion) {
        SeekBarFragment slider = new SeekBarFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_SLIDER_QUESTION, sliderQuestion);
        slider.setArguments(args);
        return slider;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            sliderQuestion = (SliderQuestion) getArguments().getSerializable(ARG_SLIDER_QUESTION);
        }
    }

    SeekBar.OnSeekBarChangeListener sbl = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            sliderQuestion.setUserGuessPlacement(seekBar.getProgress());
            int correctAnswer = sliderQuestion.getAnswerValue();
            int userGuess = sliderQuestion.getUserGuessValue();
            if (correctAnswer == userGuess) {
                sliderQuestion.setAnswerTitle("Correct Answer");
                sliderQuestion.setAnswerText("Your guess " + userGuess + " is correct!");
            } else {
                sliderQuestion.setAnswerTitle("Wrong Answer");
                sliderQuestion.setAnswerText("Your guess was " + userGuess + ". The correct answer is " + correctAnswer + ".");
            }
            dataPasser.onResult(sliderQuestion);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress,
        boolean fromUser) {
            if(sliderQuestion != null) {
                sbValueTextView.setText(Integer.toString(sliderQuestion.getStartValue() + progress * sliderQuestion.getStepSize()));
            }
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
        View view = inflater.inflate(R.layout.fragment_slider, container, false);
        sb = (SeekBar) view.findViewById(R.id.seekBar);
        sbValueTextView = (TextView)view.findViewById(R.id.seekBarValueText);
        sb.setOnSeekBarChangeListener(sbl);
        sb.setMax(9);
        return view;
    }
}
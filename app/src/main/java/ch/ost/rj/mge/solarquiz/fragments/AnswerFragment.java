package ch.ost.rj.mge.solarquiz.fragments;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.ColorRes;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ch.ost.rj.mge.solarquiz.R;

public class AnswerFragment extends Fragment {
    private static final String ARG_CONTENT = "content";
    private static final String ARG_CORRECT_ANSWER = "correctAnswer";
    private static String content;
    private static boolean correctAnswer;

    public AnswerFragment() {
        // Required empty public constructor
    }

    public static AnswerFragment newInstance(boolean correctAnswer, String content) {
        AnswerFragment fragment = new AnswerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CONTENT, content);
        args.putBoolean(ARG_CORRECT_ANSWER, correctAnswer);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            content = getArguments().getString(ARG_CONTENT);
            correctAnswer = getArguments().getBoolean(ARG_CORRECT_ANSWER);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_answer, container, false);
        if(correctAnswer) {
            view.setBackgroundColor(Color.GREEN);
        } else {
            view.setBackgroundColor(Color.RED);
        }
        TextView answerView = view.findViewById(R.id.answerTextView);
        answerView.setText(content);
        return view;
    }
}
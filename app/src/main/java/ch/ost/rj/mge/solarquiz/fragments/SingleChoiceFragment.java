package ch.ost.rj.mge.solarquiz.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import ch.ost.rj.mge.solarquiz.R;
import ch.ost.rj.mge.solarquiz.helper.DataInterface;
import ch.ost.rj.mge.solarquiz.questions.SingleChoiceQuestion;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SingleChoiceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SingleChoiceFragment extends Fragment {
    private static final String ARG_SINGLE_CHOICE_QUESTION = "SingleChoiceQuestion";
    private SingleChoiceQuestion singleChoiceQuestion;
    private DataInterface dataPasser;

    public SingleChoiceFragment() {
        // Required empty public constructor
    }

    public static SingleChoiceFragment newInstance(SingleChoiceQuestion singleChoiceQuestion) {
        SingleChoiceFragment fragment = new SingleChoiceFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_SINGLE_CHOICE_QUESTION, singleChoiceQuestion);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            singleChoiceQuestion = (SingleChoiceQuestion) getArguments().getSerializable(ARG_SINGLE_CHOICE_QUESTION);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        dataPasser = (DataInterface) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_single_choice, container, false);
        Button[] singleChoiceBtns = new Button[4];
        singleChoiceBtns[0] = view.findViewById(R.id.singleChoiceBtn1);
        singleChoiceBtns[1] = view.findViewById(R.id.singleChoiceBtn2);
        singleChoiceBtns[2] = view.findViewById(R.id.singleChoiceBtn3);
        singleChoiceBtns[3] = view.findViewById(R.id.singleChoiceBtn4);

        for(int i = 0; i < singleChoiceBtns.length; i++) {
            singleChoiceBtns[i].setText(singleChoiceQuestion.getAnswerChoices()[i]);
            singleChoiceBtns[i].setOnClickListener(answerButtonListener);
        }
        return view;
    }

    // TODO
    View.OnClickListener answerButtonListener = new View.OnClickListener() {
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.singleChoiceBtn1:
                    singleChoiceQuestion.setUserPressedButtonIndex(0);
                    break;
                case R.id.singleChoiceBtn2:
                    singleChoiceQuestion.setUserPressedButtonIndex(1);
                    break;
                case R.id.singleChoiceBtn3:
                    singleChoiceQuestion.setUserPressedButtonIndex(2);
                    break;
                case R.id.singleChoiceBtn4:
                    singleChoiceQuestion.setUserPressedButtonIndex(3);
                    break;
            }
            int correctAnswer = singleChoiceQuestion.getCorrectAnswerButtonIndex();
            int userGuess = singleChoiceQuestion.getUserPressedButtonIndex();
            if (correctAnswer == userGuess) {
                singleChoiceQuestion.setDialogTitle("Correct Answer");
                singleChoiceQuestion.setDialogText("Your guess " + singleChoiceQuestion.getAnswerChoices()[userGuess] + " is correct!");
            } else {
                singleChoiceQuestion.setDialogTitle("Wrong Answer");
                singleChoiceQuestion.setDialogText("Your guess was " + singleChoiceQuestion.getAnswerChoices()[userGuess] + ". The correct answer is " + singleChoiceQuestion.getAnswerChoices()[correctAnswer] + ".");
            }
            dataPasser.onResult(singleChoiceQuestion);
        }
    };
}
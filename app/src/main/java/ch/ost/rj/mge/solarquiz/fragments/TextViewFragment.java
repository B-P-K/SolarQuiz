package ch.ost.rj.mge.solarquiz.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import ch.ost.rj.mge.solarquiz.R;
import ch.ost.rj.mge.solarquiz.helper.DataInterface;
import ch.ost.rj.mge.solarquiz.questions.TextViewQuestion;

public class TextViewFragment extends Fragment {
    private static final String ARG_TEXTVIEW_QUESTION = "TextViewQuestion";
    private TextViewQuestion textViewQuestion;
    EditText userGuessEditTextField;
    DataInterface dataPasser;

    public TextViewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        dataPasser = (DataInterface) context;
    }

    public static TextViewFragment newInstance(TextViewQuestion textViewQuestion) {
        TextViewFragment textViewFragment = new TextViewFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_TEXTVIEW_QUESTION, textViewQuestion);
        textViewFragment.setArguments(args);
        return textViewFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            textViewQuestion = (TextViewQuestion) getArguments().getSerializable(ARG_TEXTVIEW_QUESTION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_text_view, container, false);
        Button confirmButton = view.findViewById(R.id.confirmButton);
        userGuessEditTextField = view.findViewById(R.id.editTextUserGuess);
        confirmButton.setOnClickListener(confirmButtonListener);
        return view;
    }

    View.OnClickListener confirmButtonListener = new View.OnClickListener() {
        public void onClick(View v) {
            // Do something in response to button click
            textViewQuestion.setUserGuessText(userGuessEditTextField.getText().toString());
            String correctAnswer = textViewQuestion.getCorrectAnswer();
            String userGuess = textViewQuestion.getUserGuessText();
            if (correctAnswer.equals(userGuess)) {
                textViewQuestion.setDialogTitle("Correct Answer");
                textViewQuestion.setDialogText("Your guess " + userGuess + " is correct!");
            } else {
                textViewQuestion.setDialogTitle("Wrong Answer");
                textViewQuestion.setDialogText("Your guess was " + userGuess + ". The correct answer is " + correctAnswer + ".");
            }
            dataPasser.onResult(textViewQuestion);
        }
    };

}
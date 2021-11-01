package ch.ost.rj.mge.solarquiz.activities;

import java.lang.*;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.TextView;

import ch.ost.rj.mge.solarquiz.R;
import ch.ost.rj.mge.solarquiz.fragments.SeekBarFragment;
import ch.ost.rj.mge.solarquiz.helper.DataInterface;
import ch.ost.rj.mge.solarquiz.questions.Question;
import ch.ost.rj.mge.solarquiz.questions.QuestionGenerator;
import ch.ost.rj.mge.solarquiz.questions.SliderQuestion;

public class QuizActivity extends AppCompatActivity implements DataInterface {
    TextView questionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        questionTextView = findViewById(R.id.questionTextView);
        showSliderQuestion();
    }

    public void showSliderQuestion() {
        SliderQuestion sliderQuestion = QuestionGenerator.generateSliderQuestion(getApplicationContext());
        questionTextView.setText(sliderQuestion.getQuestionText());
        startSliderFragment(sliderQuestion);
    }

    @Override
    public void onResult(Question result) {
        showAnswerDialog(result);
    }

    public void showAnswerDialog(Question question) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(question.getAnswerText()).setTitle(question.getAnswerTitle())
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // TODO Generate next question
                        showSliderQuestion();
                    }
                });
        builder.create().show();
    }

    public void startSliderFragment(SliderQuestion sliderQuestion) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        SeekBarFragment slider = SeekBarFragment.newInstance(sliderQuestion);
        ft.replace(R.id.fragmentContainerView, slider);
        ft.commit();
    }
}
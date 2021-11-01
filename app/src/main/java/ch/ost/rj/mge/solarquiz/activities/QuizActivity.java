package ch.ost.rj.mge.solarquiz.activities;

import java.lang.*;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import ch.ost.rj.mge.solarquiz.R;
import ch.ost.rj.mge.solarquiz.fragments.SeekBarFragment;
import ch.ost.rj.mge.solarquiz.fragments.TextViewFragment;
import ch.ost.rj.mge.solarquiz.helper.DataInterface;
import ch.ost.rj.mge.solarquiz.questions.Question;
import ch.ost.rj.mge.solarquiz.questions.QuestionGenerator;
import ch.ost.rj.mge.solarquiz.questions.SliderQuestion;
import ch.ost.rj.mge.solarquiz.questions.TextViewQuestion;

public class QuizActivity extends AppCompatActivity implements DataInterface {
    TextView questionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        questionTextView = findViewById(R.id.questionTextView);
        //showSliderQuestion();
        showTextViewQuestion();
    }

    public void showTextViewQuestion() {
        TextViewQuestion textViewQuestion = QuestionGenerator.generateTextViewQuestion(getApplicationContext());
        questionTextView.setText(textViewQuestion.getQuestionText());
        startTextViewFragment(textViewQuestion);
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

    // TODO Set image based on answer correctness
    public void showAnswerDialog(Question question) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        ImageView image = new ImageView(this);
        // Read your drawable from somewhere
        Drawable dr = getResources().getDrawable(R.drawable.astronaut_false);
        Bitmap bitmap = ((BitmapDrawable) dr).getBitmap();
// Scale it to 50 x 50
        Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 200, 200, true));
        image.setImageDrawable(d);
        builder.setView(image).setMessage(question.getDialogText()).setTitle(question.getDialogTitle())
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

    public void startTextViewFragment(TextViewQuestion textViewQuestion) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        TextViewFragment textViewFragment = TextViewFragment.newInstance(textViewQuestion);
        ft.replace(R.id.fragmentContainerView, textViewFragment);
        ft.commit();
    }
}
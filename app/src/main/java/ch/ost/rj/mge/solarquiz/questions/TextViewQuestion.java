package ch.ost.rj.mge.solarquiz.questions;

public class TextViewQuestion extends Question {
    String userGuessText;
    String correctAnswer;

    public TextViewQuestion(String questionText, String correctAnswer) {
        this.questionText = questionText;
        this.correctAnswer = correctAnswer;
    }

    public String getUserGuessText() {
        return userGuessText;
    }

    public void setUserGuessText(String userGuessText) {
        this.userGuessText = userGuessText;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }
}

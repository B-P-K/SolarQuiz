package ch.ost.rj.mge.solarquiz.questions;

public class SingleChoiceQuestion extends Question {
    int userPressedButtonIndex;
    int correctAnswerButtonIndex;
    String[] answerChoices;

    public SingleChoiceQuestion(String questionText, int correctAnswerButtonIndex, String[] answerChoices) {
        this.questionText = questionText;
        this.correctAnswerButtonIndex = correctAnswerButtonIndex;
        this.answerChoices = answerChoices;
    }

    public int getUserPressedButtonIndex() {
        return userPressedButtonIndex;
    }

    public void setUserPressedButtonIndex(int userPressedButtonId) {
        this.userPressedButtonIndex = userPressedButtonId;
    }

    public int getCorrectAnswerButtonIndex() {
        return correctAnswerButtonIndex;
    }

    public String[] getAnswerChoices() {
        return answerChoices;
    }
}

package question;

public class NumericQuestion extends Question {
    private double answer;

    public void setAnswer(double answer) {
        this.answer = answer;
    }

    public boolean checkAnswer (String answer) {
        return answer.equals(Double.toString(this.answer));
    }
}

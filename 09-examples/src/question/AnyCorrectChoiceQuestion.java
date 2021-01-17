package question;

public class AnyCorrectChoiceQuestion extends ChoiceQuestion {
    private String answer;

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public boolean checkAnswer(String answer) {
        return answer.equals(this.answer);
    }

    public void display() {
        System.out.print(answer);
    }
}

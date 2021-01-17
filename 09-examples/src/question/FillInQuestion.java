package question;

public class FillInQuestion extends Question {
    @Override
    public void setText (String questionText) {
        String[] tokens = questionText.split("_");
        String question = tokens[0] + "____________";
        String answer   = tokens[1];
        if (tokens.length > 2) question += tokens[2];

        super.setText(question);
        setAnswer(answer);
    }
}

package question;

import org.omg.CORBA.Any;

import java.util.*;

/**
   This program shows a simple quiz with two question types.
*/
public class QuestionDemo3 {

  private static Scanner in;
  
  public static void main(String[] args) {
    in = new Scanner(System.in);

    Question first = new Question();
    first.setText("Who was the inventor of Java?");
    first.setAnswer("James Gosling");

    ChoiceQuestion second = new ChoiceQuestion();
    second.setText("In which country was the inventor of Java born?");
    second.addChoice("Australia", false);
    second.addChoice("Canada", true);
    second.addChoice("Denmark", false);
    second.addChoice("United States", false);

    NumericQuestion third = new NumericQuestion();
    third.setText("What is the value of the earth gravitation factor?");
    third.setAnswer(9.81);

    FillInQuestion fourth = new FillInQuestion();
    fourth.setText("The Birds is a film by _Alfred Hitchcock");

    MultiChoiceQuestion fifth = new MultiChoiceQuestion();
    fifth.addChoice("length", false);
    fifth.addChoice("substring", false);
    fifth.addChoice("pow", true);
    fifth.addChoice("sqrt", true);

    AnyCorrectChoiceQuestion sixth = new AnyCorrectChoiceQuestion();
    sixth.setText("Which is the names below are valid Java names?");
    sixth.addChoice("$100", true);
    sixth.addChoice("100$", false);
    sixth.addChoice("_100$", true);
    sixth.addChoice("Object-oriented", false);
    sixth.addChoice("java", true);

    presentQuestion(first);
    presentQuestion(second);
    presentQuestion(third);
    presentQuestion(fourth);
    presentQuestion(fifth);
    presentQuestion(sixth);
  }

  /**
     Presents a question to the user and checks the response.
     @param q the question
  */
  private static void presentQuestion(Question q) {
    q.display();
    System.out.print("Your answer: ");
    String response = in.nextLine();
    System.out.println(q.checkAnswer(response));
  }
}

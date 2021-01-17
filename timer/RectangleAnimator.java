import javafx.animation.*;
import javafx.application.*;
import javafx.scene.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.stage.*;

/**
   This program displays an animated rectangle.
*/
public class RectangleAnimator extends Application {

  private static final 
    int SCENE_WIDTH = 300, SCENE_HEIGHT = 400;
  private static final 
    int BOX_X = 100, BOX_Y = 100, BOX_WIDTH = 20, BOX_HEIGHT = 30;
  private static final int DELAY = 100_000_000; // 100 ms

  public void start(Stage stage) {
    // The rectangle to animate.
    Rectangle box = new Rectangle(BOX_X, BOX_Y, BOX_WIDTH, BOX_HEIGHT);
    box.setFill(Color.TRANSPARENT);
    box.setStroke(Color.BLACK);

    Group group = new Group(box);
    Scene scene = new Scene(group, SCENE_WIDTH, SCENE_HEIGHT); 
    
    class RectangleTimer extends AnimationTimer {         
      private int xPos, yPos;
      private long currentTime;

      public void handle(long now) {
	if (now > currentTime + DELAY) {
	  currentTime = now;
	  box.setTranslateX(xPos++); // Move in the x-direction by one pixel
	  box.setTranslateY(yPos++); // Move in the y-direction by one pixel
	}
      }
    }

    AnimationTimer timer = new RectangleTimer();

    stage.setTitle("An Animated Rectangle");
    stage.setScene(scene);
    stage.show();
    timer.start();
  }
}

package harjoitustyo.presentation;

//Roni
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.text.Text;

public class Utils {

    public static TextField createTextField(String text, int minWidth) {
        TextField returnable = new TextField();
        returnable.setTooltip(new Tooltip(text));
        returnable.setPromptText(text);
        returnable.setMinWidth(minWidth);
        return returnable;
    }

    public static void replaceTextAfterSleep(Text text, String input, int duration) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(duration);
                    text.setText(input);
                } catch (InterruptedException e) {
                    System.out.println("INTERRUPTED: " + e);
                    text.setText(input);
                }
            }
        };
        thread.start();
    }
}

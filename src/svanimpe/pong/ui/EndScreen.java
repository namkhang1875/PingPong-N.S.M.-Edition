package svanimpe.pong.ui;

import javafx.application.Platform;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import static svanimpe.pong.Constants.*;

public class EndScreen extends Pane {
    private Runnable onRestart = () -> {
    }; /* Do nothing for now. */

    public void setOnRestart(Runnable onRestart) {
        this.onRestart = onRestart;
    }

    private final Text header = new Text();

    public void setScore(int playerScore, int p2Score) {
        if (playerScore == WINNING_SCORE) {
            header.getStyleClass().remove("header2");
            header.setText("p1 win");
        } else if (p2Score == WINNING_SCORE){
            header.setText("p2 win");
            header.getStyleClass().add("header2");
        }
        else
            header.setText("leaving so soon?");
    }

    public EndScreen() {
        header.boundsInLocalProperty().addListener(observable ->
        {
            /*
             * When using CSS, the width and height (with CSS applied) aren't available right away.
             * Therefore, we listen for changes and update the position once the width and height
             * are available.
             */
            header.setTranslateX((WIDTH - header.getBoundsInLocal().getWidth()) / 2); /* Centered. */
            header.setTranslateY(TEXT_MARGIN_TOP_BOTTOM);
        });
        header.getStyleClass().add("endText");

        Text info = new Text("press enter to restart\npress escape to back to main menu");
        info.boundsInLocalProperty().addListener(observable ->
        {
            /*
             * When using CSS, the width and height (with CSS applied) aren't available right away.
             * Therefore, we listen for changes and update the position once the width and height
             * are available.
             */
            info.setTranslateX((WIDTH - info.getBoundsInLocal().getWidth()) / 2); /* Centered. */
            info.setTranslateY(HEIGHT - TEXT_MARGIN_TOP_BOTTOM - info.getBoundsInLocal().getHeight());
        });
        info.getStyleClass().add("info");

        setPrefSize(WIDTH, HEIGHT);
        getChildren().addAll(header, info);
        getStyleClass().add("screen");

        setOnKeyPressed(event ->
        {
            if (event.getCode() == KeyCode.ENTER) {
                onRestart.run();
            } else if (event.getCode() == KeyCode.ESCAPE) {
                Back.run();
            }
        });
    }

    private Runnable Back = () -> {
    };

    public void setOnBack(Runnable Back) {
        this.Back = Back;
    }
}
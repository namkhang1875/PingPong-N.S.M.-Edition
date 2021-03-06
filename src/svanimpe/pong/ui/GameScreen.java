package svanimpe.pong.ui;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import svanimpe.pong.Game;
import svanimpe.pong.objects.Paddle.Movement;

import static svanimpe.pong.Constants.*;

public class GameScreen extends Pane {
    private final Game game;

    private final Rectangle ball = new Rectangle(BALL_SIZE, BALL_SIZE);
    private final Rectangle player = new Rectangle(PADDLE_WIDTH, PADDLE_HEIGHT);
    private final Rectangle opponent = new Rectangle(PADDLE_WIDTH, PADDLE_HEIGHT);

    private final Text playerScore = new Text("0");
    private final Text opponentScore = new Text("0");

    public GameScreen(Game game) {

        this.game = game;
        ball.translateXProperty().bind(game.getBall().xProperty());
        ball.translateYProperty().bind(game.getBall().yProperty());
        ball.setFill(Color.RED);


        player.translateXProperty().bind(game.getPlayer().xProperty());
        player.translateYProperty().bind(game.getPlayer().yProperty());
        player.getStyleClass().add("paddle");

        opponent.translateXProperty().bind(game.getOpponent().xProperty());
        opponent.translateYProperty().bind(game.getOpponent().yProperty());
        opponent.setFill(Color.CYAN);

        playerScore.textProperty().bind(game.getPlayer().scoreProperty().asString());
        playerScore.boundsInLocalProperty().addListener(observable ->
        {
            /*
             * When using CSS, the width and height (with CSS applied) aren't available right away.
             * Therefore, we listen for changes and update the position once the width and height
             * are available.
             */
            playerScore.setTranslateX(WIDTH / 2 - SCORE_SPACING / 2 - playerScore.getBoundsInLocal().getWidth());
        });
        playerScore.setTranslateY(TEXT_MARGIN_TOP_BOTTOM);
        playerScore.getStyleClass().add("score");

        opponentScore.textProperty().bind(game.getOpponent().scoreProperty().asString());
        opponentScore.setTranslateX(WIDTH / 2 + SCORE_SPACING / 2);
        opponentScore.setTranslateY(TEXT_MARGIN_TOP_BOTTOM);
        opponentScore.getStyleClass().add("p2Score");

        setPrefSize(WIDTH, HEIGHT);
        getChildren().addAll(ball, player, opponent, playerScore, opponentScore);
        getStyleClass().add("screen");

        setOnKeyPressed(this::keyPressed);
        setOnKeyReleased(this::keyReleased);
    }

    private void keyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.P) {
            game.pause();
        } else if (event.getCode() == KeyCode.ESCAPE) {
            game.forfeit();
        }
        if (event.getCode() == KeyCode.W) {
            game.getPlayer().setMovement(Movement.UP);
        } else if (event.getCode() == KeyCode.S) {
            game.getPlayer().setMovement(Movement.DOWN);
        }

        if (game.getIs2p()) {
            if (event.getCode() == KeyCode.UP) {
                game.getOpponent().setMovement(Movement.UP);
            } else if (event.getCode() == KeyCode.DOWN) {
                game.getOpponent().setMovement(Movement.DOWN);
            }
        }
    }

    private void keyReleased(KeyEvent event) {
        if (game.getPlayer().getMovement() == Movement.UP && event.getCode() == KeyCode.W) {
            game.getPlayer().setMovement(Movement.NONE);
        } else if (game.getPlayer().getMovement() == Movement.DOWN && event.getCode() == KeyCode.S) {
            game.getPlayer().setMovement(Movement.NONE);
        }
        if (game.getIs2p()) {
            if (game.getOpponent().getMovement() == Movement.UP && event.getCode() == KeyCode.UP) {
                game.getOpponent().setMovement(Movement.NONE);
            } else if (game.getOpponent().getMovement() == Movement.DOWN && event.getCode() == KeyCode.DOWN) {
                game.getOpponent().setMovement(Movement.NONE);
            }
        }
    }
}

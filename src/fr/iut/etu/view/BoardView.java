package fr.iut.etu.view;

import fr.iut.etu.Controller;
import fr.iut.etu.model.Board;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.scene.Group;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

/**
 * Created by Sylvain DUPOUY on 25/10/16.
 */
public class BoardView extends Group {

    private Board board;

    public BoardView(Board board) {
        super();


        this.board = board;

        for (int i = 0; i < this.board.getPlayerCount(); i++) {
            getChildren().add(new PlayerView(this.board.getPlayer(i)));
        }

        DeckView deckView = new DeckView(board.getDeck());


        deckView.setTranslateX((Controller.SCREEN_WIDTH - Controller.CARD_WIDTH) / 2);
        deckView.setTranslateY(Controller.SCREEN_HEIGHT / 2);

        getChildren().add(deckView);

        RotateTransition rotate = new RotateTransition(Duration.seconds(3), deckView);

        rotate.setAxis(Rotate.Z_AXIS);
        rotate.setFromAngle(360);
        rotate.setToAngle(0);
        rotate.setInterpolator(Interpolator.LINEAR);
        rotate.setCycleCount(RotateTransition.INDEFINITE);
        rotate.play();

//
//        Point2D centerPoint = deckView.localToParent(deckViewLocalBounds.getWidth() / 2, deckViewLocalBounds.getHeight() / 2);
//        double x = centerPoint.getX();
//        double y = centerPoint.getY();
//
//        Translate translate = new Translate();
//        deckView.getTransforms().add(translate);
//
//        Timeline timeline2 = new Timeline(
//                new KeyFrame(Duration.ZERO, new KeyValue(translate.xProperty(), 0)), // initial rotate
//                new KeyFrame(Duration.ZERO, new KeyValue(translate.yProperty(), 0)), // initial rotate
//                new KeyFrame(Duration.seconds(3), new KeyValue(translate.xProperty(), boardWidth - x)), // initial rotate
//                new KeyFrame(Duration.seconds(3), new KeyValue(translate.yProperty(), boardHeight - y)) // initial rotate
//        );
//
//        timeline2.play();

    }
}

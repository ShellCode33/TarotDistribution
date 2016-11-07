package fr.iut.etu.view;

import fr.iut.etu.Controller;
import fr.iut.etu.model.Board;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import java.util.ArrayList;

/**
 * Created by Sylvain DUPOUY on 25/10/16.
 */
public class BoardView extends Group {

    private Board board;

    private ArrayList<PlayerView> playerViews = new ArrayList<>();
    private DeckView deckView;

    public BoardView(Board board) {
        super();

        this.board = board;

        for (int i = 0; i < this.board.getPlayerCount(); i++) {
            PlayerView playerView = new PlayerView(this.board.getPlayer(i));
            playerViews.add(playerView);
            getChildren().add(playerView);
        }

        this.deckView = new DeckView(board.getDeck());
        getChildren().add(this.deckView);

        init();
    }

    private void init(){
        centerDeckView();

    }

    private void centerDeckView(){
        RotateTransition rotate = new RotateTransition(Duration.seconds(2), deckView);

        rotate.setAxis(Rotate.Z_AXIS);
        rotate.setFromAngle(0);
        rotate.setToAngle(90);
        rotate.setInterpolator(Interpolator.LINEAR);
        rotate.setCycleCount(1);
        rotate.play();

        Bounds boundsInLocal = deckView.getBoundsInLocal();
        Point2D point2D = deckView.localToParent(new Point2D(boundsInLocal.getWidth() / 2, boundsInLocal.getHeight() / 2));

        TranslateTransition translate = new TranslateTransition(Duration.seconds(3), deckView);
        translate.setToX(Controller.SCENE_WIDTH/2 - point2D.getX());
        translate.setToY(Controller.SCENE_HEIGHT/2 - point2D.getY());

        translate.setCycleCount(1);
        translate.play();
    }

}

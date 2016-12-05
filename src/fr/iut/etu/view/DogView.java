package fr.iut.etu.view;

import fr.iut.etu.Controller;
import fr.iut.etu.model.Card;
import fr.iut.etu.model.Hand;
import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

/**
 * Created by shellcode on 11/14/16.
 */
public class DogView extends HandView {

    public DogView(Hand dog) {
        super(dog);
    }

    @Override
    public Animation getDispatchAnimation() {

        ParallelTransition pt = new ParallelTransition();

        for (int i = cardViews.size() - 1; i >= 0; i--) {

            TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.5), cardViews.get(i));
            translateTransition.setByX(-i * CardView.CARD_WIDTH - i * GAP_BETWEEN_CARDS / 2);
            translateTransition.setToZ(-1);
            translateTransition.setCycleCount(1);

            pt.getChildren().add(translateTransition);
        }

        return pt;
    }

    @Override
    public Animation getFlipAllCardViewsAnimation() {
        ParallelTransition pt = new ParallelTransition();

        for (int i = 0; i < cardViews.size(); i++) {
            CardView currentCardView = cardViews.get(i);
            SequentialTransition st = new SequentialTransition();

            //onStart
            Transition useless = new Transition() {@Override protected void interpolate(double frac) {}};
            useless.setDelay(Duration.millis(i * 300));

            useless.setOnFinished(actionEvent -> {//onStart du flipAnimation
                currentCardView.setMoving(true);
            });


            Animation flipAnimation = currentCardView.getFlipAnimation();
            flipAnimation.setOnFinished(actionEvent -> {
                currentCardView.setMoving(false);
            });

            st.getChildren().addAll(useless, flipAnimation);
            pt.getChildren().add(st);
        }

        return pt;
    }

    public Animation createExplodeAnimation() {

        ParallelTransition pt = new ParallelTransition();

        for (int i = 0; i < cardViews.size(); i++) {
            CardView cardView = cardViews.get(i);
            SequentialTransition st = new SequentialTransition();

            TranslateTransition ttz = new TranslateTransition(Duration.seconds(0.5), cardView);
            ttz.setByZ(-100);
            st.getChildren().add(ttz);

            TranslateTransition tt = new TranslateTransition(Duration.seconds(1), cardView);
            tt.setToX(Controller.SCREEN_WIDTH / 2 - 4 * Controller.SCREEN_WIDTH / 6 - CardView.CARD_WIDTH / 2);
            st.getChildren().add(tt);

            TranslateTransition tt2 = new TranslateTransition(Duration.seconds(1), cardView);
            tt2.setDelay(Duration.seconds(1.5));
            tt2.setToX(2000 * Controller.SCALE_COEFF * Math.cos(i * Math.PI / cardViews.size() * 2));
            tt2.setToY(2000 * Controller.SCALE_COEFF * Math.sin(i * Math.PI / cardViews.size() * 2));
            st.getChildren().add(tt2);

            pt.getChildren().add(st);
        }

        pt.setOnFinished(event -> {
            hand.getCards().clear();
            cardViews.parallelStream().forEach(cardView -> cardView.setMoving(false));
        });

        return pt;
    }
}

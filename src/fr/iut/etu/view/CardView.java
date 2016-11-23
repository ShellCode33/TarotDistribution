package fr.iut.etu.view;

import fr.iut.etu.model.Card;
import javafx.animation.*;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by Sylvain DUPOUY on 25/10/16.
 */
public class CardView extends Group implements Observer, Comparable<CardView> {

    public static int CARD_WIDTH = 120;
    public static int CARD_HEIGHT = 212;

    public static Image backCard = null;

    private Card card;
    private ImageView front, back;
    private Animation flipAnimation;
    private boolean selected = false;

    public CardView(Card card) {

        this.card = card;
        this.card.addObserver(this);

        if(backCard == null)
            backCard = new Image("file:./res/cards/back0.jpg");

        back = new ImageView(backCard);
        back.setSmooth(true);
        back.setFitHeight(CARD_HEIGHT);
        back.setFitWidth(CARD_WIDTH);
        back.setTranslateZ(-1.01);

        getChildren().add(back);

        Image imageFace;

        if (card.getType() == Card.Type.FOOL) {
            imageFace = new Image("file:./res/cards/FOOL.png");
        } else {
            imageFace = new Image("file:./res/cards/" + card.getType().toString() + "_" + card.getValue() + ".png");
        }

        front = new ImageView(imageFace);
        front.setFitHeight(CARD_HEIGHT);
        front.setFitWidth(CARD_WIDTH);

        front.setTranslateZ(-1);

        front.setRotationAxis(Rotate.Y_AXIS);
        front.setRotate(180);

        getChildren().add(front);

        createFlipAnimation();
    }



    private void createFlipAnimation() {
        double width = CARD_WIDTH;
        double height = CARD_HEIGHT;

        setRotationAxis(Rotate.Y_AXIS);
        setRotate(card.isHidden() ? 0 : 0);

        TranslateTransition translate1 = new TranslateTransition(Duration.seconds(0.2), this);

        translate1.setByY(-height);

        translate1.setCycleCount(1);

        TranslateTransition translate2 = new TranslateTransition(Duration.seconds(0.3), this);
        translate2.setByZ(-height);
        translate2.setCycleCount(1);

        RotateTransition rotate = new RotateTransition(Duration.seconds(0.3), this);
        rotate.setFromAngle(getRotate());
        rotate.setToAngle(-180);
        rotate.setAxis(Rotate.X_AXIS);
        rotate.setCycleCount(1);

        TranslateTransition translate3 = new TranslateTransition(Duration.seconds(0.2), this);
        translate3.setByZ(height);
        translate3.setCycleCount(1);

        TranslateTransition translate4 = new TranslateTransition(Duration.seconds(0.3), this);
        translate4.setByY(height);

        translate4.setCycleCount(1);

        ParallelTransition pt = new ParallelTransition();
        pt.getChildren().addAll(translate2, rotate);

        SequentialTransition st = new SequentialTransition();
        st.getChildren().addAll(translate1,pt, translate3, translate4);

        flipAnimation = st;
    }

    public Animation getFlipAnimation() {
        return flipAnimation;
    }

    @Override
    public void update(Observable observable, Object o) {
    }

    @Override
    public int compareTo(CardView cardView) {
        return card.compareTo(cardView.card);
    }

    @Override
    public String toString() {
        return card.toString();
    }

    public void setSelect(boolean value) {
        selected = value;
        setTranslateY(selected ? -40 : 0);
    }

    public boolean isSelected() {
        return selected;
    }

    public Card getCard() {
        return card;
    }
}
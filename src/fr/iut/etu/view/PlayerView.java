package fr.iut.etu.view;

import fr.iut.etu.Controller;
import fr.iut.etu.model.Card;
import fr.iut.etu.model.Notifications;
import fr.iut.etu.model.Player;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.transform.Translate;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Sylvain DUPOUY on 25/10/16.
 */
public class PlayerView extends Group implements Observer {

    public static int GAP_BETWEEN_CARDS = 40;

    private Player player;

    private ArrayList<CardView> cardViews = new ArrayList<>();

    public PlayerView(Player player) {
        super();

        this.player = player;
        this.player.addObserver(this);

        ImageView avatar = new ImageView(player.getAvatar());
        avatar.setFitHeight(50);
        avatar.setFitWidth(50);
        avatar.setTranslateY(-75);
        avatar.setTranslateZ(-1);

        Label usernameLabel = new Label();
        usernameLabel.setText(player.getName());
        usernameLabel.setTranslateY(-68);
        usernameLabel.setTranslateX(55);
        usernameLabel.setTranslateZ(-1);
        usernameLabel.setTextFill(Color.WHITE);
        usernameLabel.setFont(new Font(30));

        getChildren().add(avatar);
        getChildren().add(usernameLabel);
    }

    @Override
    public void update(Observable observable, Object o) {
        if(o != null && o == Notifications.CARD_PICKED){
            ArrayList<Card> cards = player.getCards();

            CardView cardView = new CardView(cards.get(cards.size()-1));

            cardView.setTranslateX((cards.size() - 1)* GAP_BETWEEN_CARDS);
            cardView.setTranslateZ(-cards.size()*0.1-1);

            cardViews.add(cardView);
            getChildren().add(cardView);

            //recenter

            if(cards.size() == 1)
                getTransforms().add(new Translate(- Controller.CARD_WIDTH/2, 0));
            else
                getTransforms().add(new Translate(- GAP_BETWEEN_CARDS/2, 0));
        }
    }
}

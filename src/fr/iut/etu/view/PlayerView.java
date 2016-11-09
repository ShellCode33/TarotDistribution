package fr.iut.etu.view;

import fr.iut.etu.model.Card;
import fr.iut.etu.model.Notifications;
import fr.iut.etu.model.Player;
import javafx.scene.Group;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Sylvain DUPOUY on 25/10/16.
 */
public class PlayerView extends Group implements Observer {

    private Player player;

    private ArrayList<CardView> cardViews = new ArrayList<>();

    public PlayerView(Player player) {
        super();

        this.player = player;
        this.player.addObserver(this);
    }

    @Override
    public void update(Observable observable, Object o) {
        if(o != null && o == Notifications.CARD_PICKED){
            ArrayList<Card> cards = player.getCards();
            CardView cardView = new CardView(cards.get(cards.size()-1));
            cardViews.add(cardView);
            getChildren().add(cardView);
        }
    }
}

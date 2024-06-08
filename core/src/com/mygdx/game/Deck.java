package com.mygdx.game;
import java.util.ArrayList;
import java.lang.Math;

public class Deck
{
    ArrayList<Card> cards;

    public Deck()
    {
        cards = new ArrayList<>();
        // PLACE ALL 52 CARDS IN DECK **IN ORDER**
        for (int suit = 1; suit <= 4; suit++)
        {
            for (int value = 1; value <= 13; value++)
            {
                cards.add(new Card(suit, value));
            }
        }
    }
    public ArrayList<Card> getDeck()
    {
        return cards;
    }

    void shuffle()
    {
        //ArrayList<Card> copyCards = cards;
        ArrayList<Card> shuffledCards = new ArrayList<>();

        Card randomCard;

        for (int i = 0; i < cards.size(); i++)
        {
            int randomIndex = (int)(Math.random() * cards.size());
            randomCard = cards.get(randomIndex);
            shuffledCards.add(randomCard);
            cards.remove(randomIndex);
            i--;
        }
        cards = shuffledCards;
    }
}

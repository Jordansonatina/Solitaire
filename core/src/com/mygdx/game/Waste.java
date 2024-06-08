package com.mygdx.game;

import java.util.ArrayList;

public class Waste
{
    ArrayList<Card> waste;

    public Waste()
    {
        waste = new ArrayList<>();
    }

    public void addCard(Card c)
    {
        waste.add(c);
    }
    public ArrayList<Card> getWaste()
    {
        return waste;
    }
}

package com.mygdx.game;

import java.util.ArrayList;

public class Stock
{
    ArrayList<Card> stock;

    public Stock()
    {
        stock = new ArrayList<>();
    }
    public void setStock(ArrayList<Card> cards)
    {
        stock = cards;
    }

    public ArrayList<Card> getStock()
    {
        return stock;
    }
}

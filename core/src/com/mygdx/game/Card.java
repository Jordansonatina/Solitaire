package com.mygdx.game;

public class Card
{
    //  1 == DIAMONDS , 2 == HEARTS , 3 == CLUBS , 4 == SPADES
    private final int suit;
    // 1 == ACE ,  11 == JACK , 12 == QUEEN , 13 == KING
    private final int value;

    private boolean faceUp;

    public Card(int suit, int value)
    {
        this.suit = suit;
        this.value = value;
        faceUp = false;
    }

    public int getSuit()
    {
        return suit;
    }
    public int getValue()
    {
        return value;
    }
    public boolean getFaceUp()
    {
        return faceUp;
    }

    public void setFaceUp(boolean n)
    {
        faceUp = n;
    }
}

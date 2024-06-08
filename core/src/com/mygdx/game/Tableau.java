package com.mygdx.game;

import java.util.ArrayList;

public class Tableau
{
    ArrayList<ArrayList<Card>> tableau;

    public Tableau()
    {
        tableau = new ArrayList<>();
    }

    public ArrayList<ArrayList<Card>> getTableau()
    {
        return tableau;
    }
}

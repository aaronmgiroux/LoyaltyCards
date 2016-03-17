package com.amgprogramming.loyaltycards;

import android.app.Application;

import java.util.ArrayList;

public class GlobalVar extends Application{

    private static ArrayList cards;

    public static ArrayList<Item> getCards() {
        return cards;
    }

    public static void setCards(ArrayList<Item> list) {
        cards = list;
    }
}

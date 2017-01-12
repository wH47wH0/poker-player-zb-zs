package org.leanpoker.player;

import com.google.gson.JsonObject;

import java.util.*;

/**
 * Created by hamargyuri on 2017. 01. 12..
 */
public class Cards {
    private List<String> colors = new ArrayList<>();
    private List<Integer> values = new ArrayList<>();
    private boolean aceAndNine;
    private boolean kingAndTen;
    private boolean jockAndQueen;
    private boolean sameColorEightAndHigher;
    private boolean smallPair;
    private boolean medPair;
    private boolean highPair;

    public Cards(JsonObject card1, JsonObject card2) {
        String color1 = card1.get("suit").getAsString();
        String color2 = card2.get("suit").getAsString();
        Integer value1 = ConvertCardValue.convert(card1.get("rank").getAsString());
        Integer value2 = ConvertCardValue.convert(card2.get("rank").getAsString());
        this.colors.add(color1);
        this.colors.add(color2);
        this.values.add(value1);
        this.values.add(value2);
        this.aceAndNine = aceAndNine(value1, value2);
        this.kingAndTen = kingAndTen(value1, value2);
        this.jockAndQueen = jockAndQueen(value1, value2);
        this.sameColorEightAndHigher = sameColorEightAndHigher(color1, color2, value1, value2);
        this.smallPair = smallPair(value1, value2);
        this.medPair = mediumPair(value1, value2);
        this.highPair = highPair(value1, value2);
    }

    public boolean hasAceAndNine() { return this.aceAndNine; }
    public boolean hasKingAndTen() { return this.kingAndTen; }
    public boolean hasJockAndQueen() { return this.jockAndQueen; }
    public boolean hasSameColorEightAndHigher() { return this.sameColorEightAndHigher; }
    public List<String> getColors() { return this.colors; }
    public List<Integer> getValues() { return this.values; }
    public boolean hasSmallPair() {return this.smallPair;}
    public boolean hasMedPair() {return this.medPair;}
    public boolean hasHighPair() {return this.highPair;}

    private boolean sameColor(String color1, String color2) {
        return color1.equals(color2);
    }

    private boolean aceAndNine(Integer value1, Integer value2) {
        return (value1==14 && value2>8) || (value2==14 && value1>8);
    }

    private boolean kingAndTen(Integer value1, Integer value2) {
        return (value1==13 && value2>9) || (value2==13 && value1>9);
    }

    private boolean jockAndQueen(Integer value1, Integer value2) {
        return (value1==12 && value2>10) || (value2==12 && value1>10);
    }

    private boolean sameColorEightAndHigher(String color1, String color2, Integer value1, Integer value2) {
        return sameColor(color1, color2) && (value1>7 && value2>7);
    }

    public boolean smallPair(Integer value1, Integer value2){
        return value1.equals(value2) && value1 < 6;
    }
    public boolean mediumPair(Integer value1, Integer value2){
        return value1.equals(value2) && (5 < value1 && value1< 11);
    }
    public boolean highPair(Integer value1, Integer value2){
        return value1.equals(value2) && value1 > 10;
    }
}

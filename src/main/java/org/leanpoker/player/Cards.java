package org.leanpoker.player;

import com.google.gson.JsonObject;

import java.util.Arrays;
import java.util.List;

/**
 * Created by hamargyuri on 2017. 01. 12..
 */
public class Cards {
    private static final List<String> FACES = Arrays.asList("J", "Q", "K", "A");
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
        Integer value1 = convertCardValues(card1.get("rank").getAsString());
        Integer value2 = convertCardValues(card2.get("rank").getAsString());
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
    public boolean hasSmallPair() {return this.smallPair;}
    public boolean hasMedPair() {return this.medPair;}
    public boolean hasHighPair() {return this.highPair;}

    private Integer convertCardValues(String stringValue) {
        if (FACES.contains(stringValue)) {
            switch (stringValue) {
                case "J":
                    return 11;
                case "Q":
                    return 12;
                case "K":
                    return 13;
                case "A":
                    return 14;
                default:
                    return Integer.parseInt(stringValue);
            }
        } else return Integer.parseInt(stringValue);
    }

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

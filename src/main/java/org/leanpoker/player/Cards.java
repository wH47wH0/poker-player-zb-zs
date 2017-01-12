package org.leanpoker.player;

import com.google.gson.JsonObject;

import java.util.Arrays;
import java.util.List;

/**
 * Created by hamargyuri on 2017. 01. 12..
 */
public class Cards {
    private static final List<String> FACES = Arrays.asList("J", "Q", "K", "A");
    private boolean pair;
    private boolean aceAndNine;
    private boolean kingAndTen;
    private boolean jockAndQueen;
    private boolean sameColorEightAndHigher;

    public Cards(JsonObject card1, JsonObject card2) {
        String color1 = card1.get("suit").getAsString();
        String color2 = card2.get("suit").getAsString();
        Integer value1 = convertCardValues(card1.get("rank").getAsString());
        Integer value2 = convertCardValues(card2.get("rank").getAsString());
        this.pair = pair(value1, value2);
        this.aceAndNine = aceAndNine(value1, value2);
        this.kingAndTen = kingAndTen(value1, value2);
        this.jockAndQueen = jockAndQueen(value1, value2);
        this.sameColorEightAndHigher = sameColorEightAndHigher(color1, color2, value1, value2);
    }

    public boolean hasPair() { return this.pair; }
    public boolean hasAceAndNine() { return this.aceAndNine; }
    public boolean hasKingAndTen() { return this.kingAndTen; }
    public boolean hasJockAndQueen() { return this.jockAndQueen; }
    public boolean hasSameColorEightAndHigher() { return this.sameColorEightAndHigher; }

    private static Integer convertCardValues(String stringValue) {
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

    private static boolean hasAce(Integer value1, Integer value2) {
        return value1.equals(14) || value2.equals(14);
    }

    private static boolean hasKing(Integer value1, Integer value2) {
        return value1.equals(13) || value2.equals(13);
    }

    private static boolean hasQueen(Integer value1, Integer value2) {
        return value1.equals(12) || value2.equals(12);
    }

    private static boolean hasJock(Integer value1, Integer value2) {
        return value1.equals(11) || value2.equals(11);
    }

    private static boolean eightAndHigher(Integer value1, Integer value2) {
        return value1>7 && value2>7;
    }

    private static boolean sameColor(String color1, String color2) {
        return color1.equals(color2);
    }

    private static boolean pair(Integer value1, Integer value2) {
        return value1.equals(value2);
    }

    private static boolean aceAndNine(Integer value1, Integer value2) {
        return hasAce(value1, value2) && (value1>8 || value2>8);
    }

    private static boolean kingAndTen(Integer value1, Integer value2) {
        return hasKing(value1, value2) && (value1>9 || value2>9);
    }

    private static boolean jockAndQueen(Integer value1, Integer value2) {
        return hasQueen(value1, value2) && hasJock(value1, value2);
    }

    private static boolean sameColorEightAndHigher(String color1, String color2, Integer value1, Integer value2) {
        return sameColor(color1, color2) && (eightAndHigher(value1, value2));
    }
}

package org.leanpoker.player;

import java.util.Arrays;
import java.util.List;

/**
 * Created by hamargyuri on 2017. 01. 12..
 */
abstract class ConvertCardValue {
    private static final List<String> FACES = Arrays.asList("J", "Q", "K", "A");

    public static Integer convert(String stringValue) {
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
}

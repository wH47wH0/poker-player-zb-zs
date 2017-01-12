package org.leanpoker.player;

import com.google.gson.JsonArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hamargyuri on 2017. 01. 12..
 */
public class HandValue {
    private List<Integer> sameValues = new ArrayList<>();

    public HandValue(Cards ownCards, JsonArray commCards) {
        List<String> colors = new ArrayList<>();
        List<Integer> values = new ArrayList<>();
        for (int i=0; i<commCards.size(); i++) {
            colors.add(commCards.get(i).getAsJsonObject().get("suit").getAsString());

            values.add(ConvertCardValue.convert(commCards.get(i).getAsJsonObject().get("rank").getAsString()));
        }
        for (String color : ownCards.getColors()) {
            colors.add(color);
        }
        for (Integer value : ownCards.getValues()) {
            values.add(value);
        }

        this.sameValues = sameValue(values);
    }

    public List<Integer> getSameValues() { return this.sameValues; }

    private List<Integer> sameValue(List<Integer> allValues) {
        List<Integer> gameValues = new ArrayList<>();
        List<Integer> usedValues = new ArrayList<>();
            for (Integer value : allValues) {
                if (!usedValues.contains(value)) {
                    Integer counter = counter(value, allValues);
                    if (counter > 1) {
                        gameValues.add(counter);
                    }
                    usedValues.add(value);
                }
            }
        return gameValues;
    }

    private Integer counter(Integer value, List<Integer> allValues) {
        int counter = 0;
        for (Integer compare : allValues) {
            if (value.equals(compare)) {
                counter++;
            }
        }
        return counter;
    }
}

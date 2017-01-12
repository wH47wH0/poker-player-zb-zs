package org.leanpoker.player;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class Player {

    static final String VERSION = "_ZB.ZS";

    public static int betRequest(JsonElement request) {
        JsonObject json = request.getAsJsonObject();
        System.out.println("find me " + json);
        String ownStack = "0";
        int bet = 0;
        List<JsonObject> holeCards = new ArrayList();
        String playerStatus;
        JsonArray playerList = json.get("players").getAsJsonArray();
        for (JsonElement player : playerList) {
            if (player.getAsJsonObject().get("player").toString().equals("_ZB.ZS")) {
                ownStack = player.getAsJsonObject().get("stack").toString();
            }
            playerStatus = player.getAsJsonObject().get("status").toString();
            holeCards.add(player.getAsJsonObject().get("hole_cards").getAsJsonObject());
            if (playerStatus.equals("in_action")) {
                bet = player.getAsJsonObject().get("bet").getAsInt();
            }
        }
        int currentBuyIn = json.get("current_buy_in").getAsInt();

        if (holeCards.size() == 2) {
            if (currentBuyIn < (Integer.parseInt(ownStack) / 3)) {
                JsonObject firstCard = holeCards.get(0).get("rank").getAsJsonObject();
                JsonObject secondCard = holeCards.get(1).get("rank").getAsJsonObject();
                if (firstCard.equals(secondCard) || firstCard.equals("A") || secondCard.equals("A") || ((firstCard.equals("K") && secondCard.equals("10")) || (secondCard.equals("K") && firstCard.equals("10")))) {
                    return currentBuyIn - bet + json.get("minimum_raise").getAsInt();
                }
            }
        }
        return 1;

    }

    public static void showdown(JsonElement game) {
    }
}

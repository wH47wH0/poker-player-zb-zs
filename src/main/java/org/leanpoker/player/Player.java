package org.leanpoker.player;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class Player {

    static final String VERSION = "_ZB.ZS rulez";

    public static int betRequest(JsonElement request) {
        JsonObject json = request.getAsJsonObject();
        String ownStack = "0";
        int bet = 0;
        JsonArray holeCards = new JsonArray();
        String playerStatus;
        JsonArray playerList = json.get("players").getAsJsonArray();
        for (JsonElement player : playerList) {
            if (player.getAsJsonObject().get("name").toString().equals("_ZB.ZS")) {
                ownStack = player.getAsJsonObject().get("stack").toString();
                holeCards = player.getAsJsonObject().get("hole_cards").getAsJsonArray();
            }
            playerStatus = player.getAsJsonObject().get("status").toString();
            if (playerStatus.equals("in_action")) {
                bet = player.getAsJsonObject().get("bet").getAsInt();
            }
        }
        Integer currentBuyIn = json.get("current_buy_in").getAsInt();

        if (holeCards.size() == 2) {
            if (currentBuyIn > 0) {
                JsonElement firstCard = holeCards.get(0).getAsJsonObject().get("rank");
                JsonElement secondCard = holeCards.get(1).getAsJsonObject().get("rank");
                if (firstCard.equals(secondCard) || (firstCard.equals("A") && secondCard.getAsInt() > 8) || (secondCard.equals("A") && firstCard.getAsInt() > 8)
                        || (firstCard.equals("K") && secondCard.getAsInt() > 9) || (secondCard.equals("K") && firstCard.getAsInt() > 9) ||
                        (firstCard.equals("Q") && secondCard.equals("J")) || (secondCard.equals("Q") && firstCard.equals("J")) ||
                        (firstCard.equals("J") && ((secondCard.equals(10) && holeCards.get(1).getAsJsonObject().get("suit").equals(holeCards.get(0).getAsJsonObject().get("suit").getAsJsonObject()))))) {
                    System.out.println("zbzs action if good cards " + (currentBuyIn - bet + json.get("minimum_raise").getAsInt()));
                    return currentBuyIn - bet + json.get("minimum_raise").getAsInt();
                }
            }
        }
        return 1;

    }

    public static void showdown(JsonElement game) {
    }
}

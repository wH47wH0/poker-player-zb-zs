package org.leanpoker.player;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class Player {

    static final String VERSION = "_ZB.ZS";

    public static int betRequest(JsonElement request) {
        JsonObject json = request.getAsJsonObject();
        System.out.println("find me " + json);
        String ownStack = "0";
        int bet = 0;
        JsonArray holeCards = new JsonArray();
        String playerStatus;
        JsonArray playerList = json.get("players").getAsJsonArray();
        for (JsonElement player : playerList) {
            System.out.println("player " + player);
            System.out.println("zbzs "+ player.getAsJsonObject().get("name"));
            if (player.getAsJsonObject().get("name").toString().equals("_ZB.ZS")) {
                System.out.println("player zbzs " + player.getAsJsonObject().get("name").toString());
                ownStack = player.getAsJsonObject().get("stack").toString();
            }
            playerStatus = player.getAsJsonObject().get("status").toString();
            holeCards = player.getAsJsonObject().get("hole_cards").getAsJsonArray();
            if (playerStatus.equals("in_action")) {
                bet = player.getAsJsonObject().get("bet").getAsInt();
            }
        }
        int currentBuyIn = json.get("current_buy_in").getAsInt();
        System.out.println("find me 2 " + json);

        if (holeCards.size() == 2) {
            if (currentBuyIn > 0) {
                JsonElement firstCard = holeCards.get(0).getAsJsonObject().get("rank");
                JsonElement secondCard = holeCards.get(1).getAsJsonObject().get("rank");
                System.out.println("find me " + firstCard + " " + secondCard);
                if (firstCard.equals(secondCard) || (firstCard.equals("A") && secondCard.getAsInt() > 8) || (secondCard.equals("A") && firstCard.getAsInt() > 8)
                        || (firstCard.equals("K") && secondCard.getAsInt() > 9) || (secondCard.equals("K") && firstCard.getAsInt() > 9) ||
                        (firstCard.equals("Q") && secondCard.equals("J")) || (secondCard.equals("Q") && firstCard.equals("J")) ||
                        (firstCard.equals("J") && ((secondCard.equals(10) && holeCards.get(1).getAsJsonObject().get("suit").equals(holeCards.get(0).getAsJsonObject().get("suit").getAsJsonObject()))))) {

                    return currentBuyIn - bet + json.get("minimum_raise").getAsInt();
                }
            }
        }
        return 1;

    }

    public static void showdown(JsonElement game) {
    }
}

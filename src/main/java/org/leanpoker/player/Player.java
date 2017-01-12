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
            System.out.println("player "+player);
            if (player.getAsJsonObject().get("name").toString().equals("_ZB.ZS")) {
                System.out.println("player zbzs "+player.getAsJsonObject().get("name").toString());
                ownStack = player.getAsJsonObject().get("stack").toString();
            }
            playerStatus = player.getAsJsonObject().get("status").toString();
            holeCards.add(player.getAsJsonObject().get("hole_cards").getAsJsonObject());
            if (playerStatus.equals("in_action")) {
                bet = player.getAsJsonObject().get("bet").getAsInt();
            }
        }
        int currentBuyIn = json.get("current_buy_in").getAsInt();
        System.out.println("find me 2 "+ json);

        if (holeCards.size() == 2) {
            if (currentBuyIn > 0) {
                JsonObject firstCard = holeCards.get(0).get("rank").getAsJsonObject();
                JsonObject secondCard = holeCards.get(1).get("rank").getAsJsonObject();
                System.out.println("find me "+ firstCard +" "+secondCard);
                if (firstCard.equals(secondCard) || (firstCard.equals("A") && secondCard.getAsInt() > 8) || (secondCard.equals("A") && firstCard.getAsInt() > 8)
                        || (firstCard.equals("K") && secondCard.getAsInt() > 9) || (secondCard.equals("K") && firstCard.getAsInt() > 9) ||
                        (firstCard.equals("Q") && secondCard.equals("J")) || (secondCard.equals("Q") && firstCard.equals("J")) ||
                        (firstCard.equals("J") && ((secondCard.equals(10) && holeCards.get(1).get("suit").getAsJsonObject().equals(holeCards.get(0).get("suit").getAsJsonObject())))))
                {
                    return currentBuyIn - bet + json.get("minimum_raise").getAsInt();

                }
            }
        }


//            if (currentBuyIn < (Integer.parseInt(ownStack) / 3)) {
//                JsonObject firstCard = holeCards.get(0).get("rank").getAsJsonObject();
//                JsonObject secondCard = holeCards.get(1).get("rank").getAsJsonObject();
//                if (firstCard.equals(secondCard) || firstCard.equals("A") || secondCard.equals("A") || ((firstCard.equals("K") && secondCard.equals("10")) || (secondCard.equals("K") && firstCard.equals("10")))) {
//                    return currentBuyIn - bet + json.get("minimum_raise").getAsInt();
//                }
//                return 1;
//            }
//            return currentBuyIn - bet + json.get("minimum_raise").getAsInt();
//        }
        return 1;

    }

    public static void showdown(JsonElement game) {
    }
}

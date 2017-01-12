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

        Integer smallBlind = json.get("small_blind").getAsInt();
        if (holeCards.size() == 2) {
            if (currentBuyIn > smallBlind*2) {
                Cards cards = new Cards(holeCards.get(0).getAsJsonObject(), holeCards.get(1).getAsJsonObject());
                if (cards.hasAceAndNine() || cards.hasJockAndQueen() || cards.hasKingAndTen() || cards.hasPair() || cards.hasSameColorEightAndHigher()) {
                    System.out.println("zbzs action if good cards " + (currentBuyIn - bet + json.get("minimum_raise").getAsInt()));
                    return Integer.parseInt(ownStack);
//                    return currentBuyIn - bet + json.get("minimum_raise").getAsInt();
                }
            }
        }
        return 0;

    }

    public static void showdown(JsonElement game) {
    }
}

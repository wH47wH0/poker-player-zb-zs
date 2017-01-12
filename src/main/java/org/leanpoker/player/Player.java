package org.leanpoker.player;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class Player {

    static final String VERSION = "_ZB.ZS rulez";

    public static int betRequest(JsonElement request) {

        JsonObject json = request.getAsJsonObject();

        System.out.println("find json " + json);

        JsonArray community_cards = json.get("community_cards").getAsJsonArray();
        int betIndex = json.get("bet_index").getAsInt();
        Integer currentBuyIn = json.get("current_buy_in").getAsInt();
        Integer smallBlind = json.get("small_blind").getAsInt();
        Integer dealer = json.get("dealer").getAsInt();
        Integer minimumRaise = json.get("minimum_raise").getAsInt();
        Integer pot = json.get("pot").getAsInt();
        int bet = 0;
        String playerStatus = "in_action";
        Integer actives = 0;

        JsonArray playerList = json.get("players").getAsJsonArray();

        Integer ownStack = 0;
        JsonArray holeCards = new JsonArray();

        for (JsonElement player : playerList) {
            System.out.println("find player " + player);
            if (player.getAsJsonObject().get("name").getAsString().equals("ZB ZS")) {
                System.out.println("find us " + player.getAsJsonObject().get("name").getAsString().equals("ZB ZS"));
                ownStack = player.getAsJsonObject().get("stack").getAsInt();
                System.out.println("find hole cards " + player.getAsJsonObject().get("hole_cards").getAsJsonArray());
                holeCards = player.getAsJsonObject().get("hole_cards").getAsJsonArray();
                bet = player.getAsJsonObject().get("bet").getAsInt();
            } else {
                if (player.getAsJsonObject().get("status").getAsString().equals("active")) {
                    actives += 1;
                }
            }
//            if (playerStatus.equals(player.getAsJsonObject().get("status").getAsString())) {
//                bet = player.getAsJsonObject().get("bet").getAsInt();
//            }
        }
        System.out.println("find holeCards.size() " + holeCards.size());
        if (holeCards.size() == 2) {
            Cards cards = new Cards(holeCards.get(0).getAsJsonObject(), holeCards.get(1).getAsJsonObject());
            if (actives > 2) {
                if (cards.hasHighPair()) {
                    return ownStack;

                } else {
                    return 0;
                }
            } else {
                if (cards.hasAceAndNine() || cards.hasJockAndQueen() || cards.hasKingAndTen() || cards.hasHighPair() ||
                        cards.hasMedPair() || cards.hasSmallPair() || cards.hasSameColorEightAndHigher()) {
                    return ownStack;
                }
            }


        }
        return 0;

// Actions to use:
//        raise:
//              return currentBuyIn - bet + minimumRaise;
//        call:
//              return currentBuyIn - bet;
//        fold:
//              return 0;
//        allin:
//              return Integer.parseInt(ownStack);

    }

    public static void showdown(JsonElement game) {
    }
}

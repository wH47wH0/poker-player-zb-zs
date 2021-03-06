package org.leanpoker.player;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        Map<String, List<Integer>> map = new HashMap();

        for (JsonElement player : playerList) {
            if (player.getAsJsonObject().get("name").getAsString().equals("ZB ZS")) {
                ownStack = player.getAsJsonObject().get("stack").getAsInt();
                holeCards = player.getAsJsonObject().get("hole_cards").getAsJsonArray();
                bet = player.getAsJsonObject().get("bet").getAsInt();
                if (player.getAsJsonObject().get("status").getAsString().equals("active")) {
                    actives += 1;
                }
            } else {
                List data = new ArrayList();
                String playerName = player.getAsJsonObject().get("name").getAsString();
                data.add(player.getAsJsonObject().get("bet").getAsInt());
                data.add(player.getAsJsonObject().get("stack").getAsInt());
                map.put(playerName, data);
                if (player.getAsJsonObject().get("status").getAsString().equals("active")) {
                    actives += 1;
                }
            }
        }

        if (holeCards.size() == 2) {
            Cards cards = new Cards(holeCards.get(0).getAsJsonObject(), holeCards.get(1).getAsJsonObject());
            if (actives > 2) {
                if (cards.hasHighPair()) {
                    return ownStack;

                } else {
                    if (currentBuyIn <= smallBlind * 2) {
                        return currentBuyIn - bet + minimumRaise;
                    }
                }
//                for (Object o : map.keySet()) {
//                    if (map.get(o).get(1) / map.get(o).get(0) < 4) {
//                        return currentBuyIn - bet + minimumRaise;
//                    } else {
//
//                        if (cards.hasHighPair()) {
//                            return ownStack;
//
//                        } else {
//                            return 0;
//                        }


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
//              return ownStack;

    }

    public static void showdown(JsonElement game) {
    }
}

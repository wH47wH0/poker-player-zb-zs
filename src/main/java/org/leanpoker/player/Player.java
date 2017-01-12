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
        int bet = 0;
        List<JsonObject> holeCards = new ArrayList();
        JsonArray jsonArray = json.get("players").getAsJsonArray();
        for (JsonElement array : jsonArray) {
            String playerStatus = array.getAsJsonObject().get("status").toString();
            holeCards.add(array.getAsJsonObject().get("hole_cards").getAsJsonObject());
            if (playerStatus.equals("in_action")) {
                bet = array.getAsJsonObject().get("bet").getAsInt();
            }
        }
        int currentBuyIn = json.get("current_buy_in").getAsInt();
        System.out.println("find me " + (currentBuyIn - bet + json.get("minimum_raise").getAsInt()));
        for (Object o : holeCards) {
            System.out.println("find me " + o);
            JsonObject js0 = holeCards.get(0).get("rank").getAsJsonObject();
            JsonObject js1 = holeCards.get(1).get("rank").getAsJsonObject();
            if (js0.equals(js1) || js0.equals("A") || js1.equals("A") || (js0.equals("K") && js1.equals("10") || js1.equals("K") && js0.equals("10"))) {
                return currentBuyIn - bet + json.get("minimum_raise").getAsInt();

            }

        }
        return 0;
    }

    public static void showdown(JsonElement game) {
    }
}

package org.leanpoker.player;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class Player {

    static final String VERSION = "_ZB.ZS";

    public static int betRequest(JsonElement request) {
        JsonObject json = request.getAsJsonObject();
        int currentBuyIn = json.get("current_buy_in").getAsInt();
        return currentBuyIn;
    }

    public static void showdown(JsonElement game) {
    }
}

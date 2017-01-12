package org.leanpoker.player;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.Map;

public class Player {

    static final String VERSION = "Default Java folding player";

    public static int betRequest(JsonElement request) {
        JsonObject json = request.getAsJsonObject();
        int currentBuyIn = json.get("current_buy_in").getAsInt();
        return currentBuyIn;
    }

    public static void showdown(JsonElement game) {
    }
}

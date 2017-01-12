package org.leanpoker.player;

import com.google.gson.JsonElement;

public class Player {

    static final String VERSION = "_ZB.ZS";

    public static int betRequest(JsonElement request) {
        System.out.println(request.getAsJsonObject().get("current_buy_in"));
        return 1;
    }

    public static void showdown(JsonElement game) {
    }
}

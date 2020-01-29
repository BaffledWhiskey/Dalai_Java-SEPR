package com.kroy;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.JsonValue;

public class Tools {

    public static final int TOP = 1;
    public static final int RIGHT = 2;
    public static final int BOTTOM = 4;
    public static final int LEFT = 8;

    public static Vector2 vector2fromJson(JsonValue json) {
        float x = json.getFloat("x");
        float y = json.getFloat("y");
        return new Vector2(x, y);
    }

}

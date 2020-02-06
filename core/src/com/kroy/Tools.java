package com.kroy;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.JsonValue;

public class Tools {

    public static final float MAP_UNIT_SCALE = 4.0f;

    public static Vector2 vector2fromJson(JsonValue json) {
        if (json.has("x")) {
            float x = json.getFloat("x");
            float y = json.getFloat("y");
            return new Vector2(x, y);
        }
        float row = json.getFloat("row");
        float column = json.getFloat("column");
        return toVector2(row, column);
    }

    public static Vector2 toVector2(float row, float column) {
        return new Vector2(row * 32f * MAP_UNIT_SCALE, column * 32f * MAP_UNIT_SCALE);
    }
}

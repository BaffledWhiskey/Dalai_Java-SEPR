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
        int row = json.getInt("row");
        int column = json.getInt("column");
        return toVector2(row, column);
    }

    public static Vector2 toVector2(int row, int column) {
        float r = (float) row;
        float c = (float) column;
        return new Vector2((r + 0.25f) * 32f * MAP_UNIT_SCALE, (c + 0.25f) * 32f * MAP_UNIT_SCALE);
    }
}

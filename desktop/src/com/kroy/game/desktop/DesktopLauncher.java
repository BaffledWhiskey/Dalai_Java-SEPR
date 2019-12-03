package com.kroy.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.kroy.entities.Bullet;
import com.kroy.screens.GameScreen;
import com.kroy.game.KROY;

public class DesktopLauncher {
	public static void main(String[] arg) {

		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = GameScreen.WIDTH;
		config.height = GameScreen.HEIGHT;
		config.resizable = false;
		new LwjglApplication(new KROY(), config);


	}
}

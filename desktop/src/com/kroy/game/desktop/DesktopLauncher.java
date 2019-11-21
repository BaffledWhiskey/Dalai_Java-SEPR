package com.kroy.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.kroy.game.GameScreen;
import com.kroy.game.MainMenuScreen;

public class DesktopLauncher {
	public void main(String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new MainMenuScreen(), config);
	}
}

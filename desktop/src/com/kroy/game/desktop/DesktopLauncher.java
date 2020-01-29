package com.kroy.game.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.kroy.game.Kroy;
import com.kroy.screens.GameScreen;

public class DesktopLauncher {
	public static void main(String[] arg) {

		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new Kroy(), config);

        config.title = "Kroy";
        config.width = Gdx.app.getGraphics().getWidth();
        config.height = Gdx.app.getGraphics().getHeight();
        config.fullscreen = true;
	}
}

package com.kroy.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.kroy.game.KROY;
/**
 * The game over screen is shown when the game has either been won or lost
 */
public class GameOverScreen implements Screen {

    private static final int BUTTON_WIDTH = 175;
    private static final int BUTTON_HEIGHT = 50;
    private static final int x = GameScreen.WIDTH/2;
    private static final int EXIT_BUTTON_Y = 50;
    private static final int PLAY_BUTTON_Y = 175;
    private static final int KROY_LOGO_Y = 400;
    private static final int LOGO_WIDTH = 600;
    private static final int LOGO_HEIGHT = 300;

    final KROY game;

    Texture playAgainActive;
    Texture playAgainInactive;
    Texture exitButtonActive;
    Texture exitButtonInactive;
    Texture kroyLogo;
    Texture gameOverImage;
    Texture loseText;
    Texture winText;

    String result;

    OrthographicCamera camera;

    public GameOverScreen(KROY game, String result){
        this.game = game;
        this.result = result;
        playAgainActive = new Texture("GameOverScreen/PlayAgainActive.png");
        playAgainInactive = new Texture("GameOverScreen/PlayAgainInactive.png");
        exitButtonActive = new Texture("GameOverScreen/ExitActive.png");
        exitButtonInactive = new Texture("GameOverScreen/ExitInactive.png");
        kroyLogo = new Texture("KROY_logo.png");
        gameOverImage = new Texture("GameOverScreen/GameOver.png");
        loseText = new Texture("GameOverScreen/Lose.png");
        winText = new Texture("GameOverScreen/Win.png");
    }



    @Override
    public void show() {

    }

    /**
     * Displays the Game Over screen
     * @param delta a parameter used to render the screen
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.5f, 0.25f, 0.25f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        game.batch.begin();
        //If the game has been lost, display the 'lose' screen
        if (result == "lose") {
            game.batch.draw(gameOverImage, x - LOGO_WIDTH/2, KROY_LOGO_Y, LOGO_WIDTH, LOGO_HEIGHT);
            game.batch.draw(loseText, x - 116, 325, 233, 65);
        }
        //If the game has been won, display the 'win' screen
        else {
            game.batch.draw(winText, x - 466/2, GameScreen.HEIGHT- 450, 466, 130);
        }

        //If the 'play again' button is scrolled over, display the 'active' sprite
        if(Gdx.input.getX() < x + BUTTON_WIDTH/2 && Gdx.input.getX() > x - BUTTON_WIDTH/2 && GameScreen.HEIGHT
                - Gdx.input.getY() < PLAY_BUTTON_Y + BUTTON_HEIGHT
                && GameScreen.HEIGHT - Gdx.input.getY() > PLAY_BUTTON_Y){
            game.batch.draw(playAgainActive, (x) - BUTTON_WIDTH/2, PLAY_BUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT);
            //If it is clicked, begin another game
            if(Gdx.input.isTouched()){
                game.setScreen(new GameScreen(game));
            }
        }
        //Else display the 'inactive' sprite
        else {
            game.batch.draw(playAgainInactive, (x) - BUTTON_WIDTH / 2, PLAY_BUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT);
        }
        //If the 'exit' button is scrolled over, display the 'active' sprite
        if(Gdx.input.getX() < x + BUTTON_WIDTH/2 && Gdx.input.getX() > x - BUTTON_WIDTH/2 && GameScreen.HEIGHT
                - Gdx.input.getY() < EXIT_BUTTON_Y + BUTTON_HEIGHT
                && GameScreen.HEIGHT - Gdx.input.getY() > EXIT_BUTTON_Y){
            game.batch.draw(exitButtonActive, (x) - BUTTON_WIDTH/2, EXIT_BUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT);
            //If it is clicked, leave the app
            if(Gdx.input.isTouched()){
                Gdx.app.exit();
            }
        }
        //Else display the 'inactive' sprite
        else {
            game.batch.draw(exitButtonInactive, (x) - BUTTON_WIDTH / 2, EXIT_BUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT);
        }
        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() { }

    @Override
    public void resume() { }

    @Override
    public void hide() {
        this.dispose();
    }

    /**
     * Disposes of the screen when it is no longer needed
     */
    @Override
    public void dispose() {
        Gdx.gl.glClearColor(0,0,0,1);
        playAgainActive.dispose();
        playAgainInactive.dispose();
        exitButtonActive.dispose();
        exitButtonInactive.dispose();
        kroyLogo.dispose();

    }
}

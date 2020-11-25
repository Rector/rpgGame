package rpg.game.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import rpg.game.helpers.Assets;

public class WorldRenderer {
    private GameController gc;
    private SpriteBatch batch;
    private TextureRegion cursorTexture;
    private BitmapFont font18;
    private BitmapFont font24;

    public WorldRenderer(GameController gc, SpriteBatch batch) {
        this.gc = gc;
        this.batch = batch;
        this.cursorTexture = Assets.getInstance().getAtlas().findRegion("cursor");
        this.font18 = Assets.getInstance().getAssetManager().get("fonts/font18.ttf");
        this.font24 = Assets.getInstance().getAssetManager().get("fonts/font24.ttf");
    }

    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        gc.getGameMap().render(batch);
        gc.getUnitController().render(batch, font18);
        gc.getProjectileController().render(batch);
        batch.setColor(1, 1, 1, 0.5f);
        batch.draw(cursorTexture, gc.getCursorX() * GameMap.CELL_SIZE, gc.getCursorY() * GameMap.CELL_SIZE);
        batch.setColor(1, 1, 1, 1);

// 2. Необходимо вывести на экран: имя персонажа, количество монет
        font24.draw(batch, "Player: " + gc.getUnitController().getHero().getName(), 20, 710);
        font24.setColor(Color.GOLD);
        font24.draw(batch, "Quantity Coins the Hero: " + gc.getUnitController().getHero().getQuantityCoinsTheHero(), 20, 670);

        font24.setColor(0.76F, 0.0F, 0.0F, 1.0F);
        font24.draw(batch, "ROUND: " + gc.getUnitController().getCounterRound(), 640, 690);


        font24.setColor(Color.WHITE);
        batch.end();
    }
}

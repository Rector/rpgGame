package rpg.game.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import rpg.game.game.units.Hero;
import rpg.game.game.units.Monster;
import rpg.game.game.units.Unit;
import rpg.game.helpers.Assets;
import rpg.game.screens.ScreenManager;

public class WorldRenderer {
    private GameController gc;
    private SpriteBatch batch;
    private TextureRegion cursorTexture;
    private BitmapFont font18;
    private BitmapFont font24;
    private StringBuilder stringHelper;

    public WorldRenderer(GameController gc, SpriteBatch batch) {
        this.gc = gc;
        this.batch = batch;
        this.cursorTexture = Assets.getInstance().getAtlas().findRegion("cursor");
        this.font18 = Assets.getInstance().getAssetManager().get("fonts/font18.ttf");
        this.font24 = Assets.getInstance().getAssetManager().get("fonts/font24.ttf");
        this.stringHelper = new StringBuilder();
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

        Monster m = gc.getUnitController().getMonsterController().getMonsterInCell(gc.getCursorX(), gc.getCursorY());
        if(m != null){
            stringHelper.append("Dam.: ").append(m.getWeapon().damage).append("\n")
                    .append("Rad.: ").append(m.getWeapon().radius);
            font18.draw(batch, stringHelper, gc.getCursorX() * GameMap.CELL_SIZE, gc.getCursorY() * GameMap.CELL_SIZE);
            stringHelper.setLength(0);
        }

        Hero h = gc.getUnitController().getHero();
        if(gc.getUnitController().getHero().getCellX() == gc.getCursorX() && gc.getUnitController().getHero().getCellY() == gc.getCursorY()){
            stringHelper.append("Dam.: ").append(h.getWeapon().damage).append("\n")
                    .append("Rad.: ").append(h.getWeapon().radius);
            font18.draw(batch, stringHelper, gc.getCursorX() * GameMap.CELL_SIZE, gc.getCursorY() * GameMap.CELL_SIZE);
            stringHelper.setLength(0);
        }

        batch.setColor(1, 1, 1, 1);
        batch.end();

        float camX = ScreenManager.getInstance().getCamera().position.x;
        float camY = ScreenManager.getInstance().getCamera().position.y;
        ScreenManager.getInstance().resetCamera();
        batch.begin();
        gc.getUnitController().getHero().renderHUD(batch, font24, 10, ScreenManager.WORLD_HEIGHT - 10);
        batch.end();

        ScreenManager.getInstance().pointCameraTo(camX, camY);
    }
}

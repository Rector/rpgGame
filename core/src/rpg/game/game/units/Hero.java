package rpg.game.game.units;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import lombok.Getter;
import rpg.game.game.Berry;
import rpg.game.game.GameController;
import rpg.game.game.Weapon;
import rpg.game.helpers.Assets;
import rpg.game.helpers.Utils;
import rpg.game.screens.ScreenManager;

import java.util.List;

@Getter
public class Hero extends Unit {
    private String name;

    private int satiety;
    private int satietyMax;

    private Group guiGroup;
    private Label hpLabel;
    private Label goldLabel;

    private Label satietyLabel;

    public Hero(GameController gc) {
        super(gc, 1, 1, 10, "Hero");
        this.name = "Sir Lancelot";

        this.satietyMax = 8;
        this.satiety = satietyMax;
        this.textureHp = Assets.getInstance().getAtlas().findRegion("hp");
        this.weapon = new Weapon(Weapon.Type.SPEAR, 2, 2, 0);
        this.createGui();
    }

    public void update(float dt) {
        super.update(dt);
        if (Gdx.input.justTouched() && canIMakeAction()) {
            Monster m = gc.getUnitController().getMonsterController().getMonsterInCell(gc.getCursorX(), gc.getCursorY());
            if (Utils.isCellsAreNeighbours(cellX, cellY, gc.getCursorX(), gc.getCursorY())) {
                List<Berry> l = gc.getGameMap().getListBerry();
                tryToeEatBerry(l);
            }
            if (m != null && canIAttackThisTarget(m, 1)) {
                attack(m);
            } else {
                goTo(gc.getCursorX(), gc.getCursorY());
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            tryToEndTurn();
        }
        updateGui();
    }

    public void tryToEndTurn() {
        if (gc.getUnitController().isItMyTurn(this) && isStayStill()) {
            stats.resetPoints();
        }
    }

    public void updateGui() {
        stringHelper.setLength(0);
        stringHelper.append("Hp: ").append(stats.hp).append(" / ").append(stats.maxHp);
        hpLabel.setText(stringHelper);

        stringHelper.setLength(0);
        stringHelper.append("Sat: ").append(satiety).append(" / ").append(satietyMax);
        satietyLabel.setText(stringHelper);


        stringHelper.setLength(0);
        stringHelper.append(gold);
        goldLabel.setText(stringHelper);
    }

    public void hunger() {
        satiety--;
        if (satiety <= 0) {
            stats.hp -= 2;
            satiety = 0;
            if(stats.hp <= 0){
                ScreenManager.getInstance().changeScreen(ScreenManager.ScreenType.GAME_OVER);
            }
        }
    }


    public void tryToeEatBerry(List<Berry> list) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getBerryX() == gc.getCursorX() && list.get(i).getBerryY() == gc.getCursorY()) {
                satiety += list.get(i).getSatisfyingHunger();
                list.get(i).deactiveVisible();
                if (satiety > satietyMax) {
                    satiety = satietyMax;
                }
                return;
            }
        }
    }


    public void createGui() {
        this.guiGroup = new Group();
        Skin skin = new Skin();
        skin.addRegions(Assets.getInstance().getAtlas());
        BitmapFont font24 = Assets.getInstance().getAssetManager().get("fonts/font24.ttf");
        Label.LabelStyle labelStyle = new Label.LabelStyle(font24, Color.WHITE);
        this.hpLabel = new Label("", labelStyle);
        this.goldLabel = new Label("", labelStyle);
        this.hpLabel.setPosition(170, 40);
        this.goldLabel.setPosition(450, 30);

        this.satietyLabel = new Label("", labelStyle);
        this.satietyLabel.setPosition(170, 10);

        Image backgroundImage = new Image(Assets.getInstance().getAtlas().findRegion("upperPanel"));
        this.guiGroup.addActor(backgroundImage);
        this.guiGroup.addActor(hpLabel);

        this.guiGroup.addActor(satietyLabel);

        this.guiGroup.addActor(goldLabel);
        this.guiGroup.setPosition(0, ScreenManager.WORLD_HEIGHT - 60);

        skin.dispose();
    }
}

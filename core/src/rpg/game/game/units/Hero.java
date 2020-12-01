package rpg.game.game.units;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.*;
import rpg.game.game.Armor;
import rpg.game.game.GameController;
import rpg.game.game.Weapon;
import rpg.game.helpers.Assets;

public class Hero extends Unit {
    private String name;

    public Hero(GameController gc) {
        super(gc, 1, 1, 10, "Hero");
        this.name = "Sir Lancelot";
        this.textureHp = Assets.getInstance().getAtlas().findRegion("hp");
        this.weapon = new Weapon(Weapon.Type.SPEAR, 5, 2);


        this.armor = new Armor(Armor.TypeArmor.HEAVY);
    }

    public void update(float dt) {
        super.update(dt);
        if (Gdx.input.justTouched() && canIMakeAction()) {
            Monster m = gc.getUnitController().getMonsterController().getMonsterInCell(gc.getCursorX(), gc.getCursorY());
            if (m != null && canIAttackThisTarget(m, 1)) {
                attack(m);
            } else {
                goTo(gc.getCursorX(), gc.getCursorY());
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && isStayStill()) {
            stats.resetPoints();
        }
    }

    public void renderHUD(SpriteBatch batch, BitmapFont font, int x, int y) {
        stringHelper.setLength(0);
        stringHelper
                .append("Player: ").append(name).append("\n")
                .append("Gold: ").append(gold).append("\n");
        font.draw(batch, stringHelper, x, y);
    }
}

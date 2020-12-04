package rpg.game.game;

import com.badlogic.gdx.math.MathUtils;
import lombok.Getter;

import java.util.List;

@Getter
public class Berry {
    private int satisfyingHunger;
    private boolean visible;
    private int berryX;
    private int berryY;

    public Berry(int berryX, int berryY) {
        this.berryX = berryX;
        this.berryY = berryY;
        this.satisfyingHunger = 4;
        this.visible = false;
    }

    public void deactiveVisible() {
        visible = false;
    }

    public void activeVisible() {
        visible = true;
    }
}

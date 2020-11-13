package rpg.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Hero {
    private ProjectileController projectileController;
    private Vector2 position;
    private TextureRegion texture;

    private byte firingMode;

    private Vector2 movementToRight;
    private Vector2 movementToLeft;
    private Vector2 movementToUp;
    private Vector2 movementToDown;

    public Hero(TextureAtlas atlas, ProjectileController projectileController) {
        this.position = new Vector2(100, 100);

        this.movementToRight = new Vector2(200, 0);
        this.movementToLeft = new Vector2(-200, 0);
        this.movementToUp = new Vector2(0, 200);
        this.movementToDown = new Vector2(0, -200);

        this.texture = atlas.findRegion("tank");
        this.projectileController = projectileController;

        this.firingMode = 1;
    }


    public void changeFiringMode() {
        firingMode++;
        if (firingMode > 2) {
            firingMode = 1;
        }
    }


    public boolean checkPosition(Vector2 vector2) {
        boolean condition = true;

        if (vector2.x > 780) {
            vector2.x = 780;
            condition = false;
        }

        if (vector2.x < 20) {
            vector2.x = 20;
            condition = false;
        }

        if (vector2.y > 700) {
            vector2.y = 700;
            condition = false;
        }

        if (vector2.y < 20) {
            vector2.y = 20;
            condition = false;
        }

        return condition;
    }

    public void update(float dt) {

        if (Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
            changeFiringMode();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && firingMode == 1) {
            projectileController.activate(position.x, position.y, 400, 0);
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && firingMode == 2) {
            projectileController.activate(position.x, position.y, 400, 20);
            projectileController.activate(position.x, position.y, 400, -20);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            if (checkPosition(position)) {
                position.mulAdd(movementToRight, dt);
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            if (checkPosition(position)) {
                position.mulAdd(movementToLeft, dt);
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            if (checkPosition(position)) {
                position.mulAdd(movementToUp, dt);
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            if (checkPosition(position)) {
                position.mulAdd(movementToDown, dt);
            }
        }
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x - 20, position.y - 20);
    }
}

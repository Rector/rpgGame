package rpg.game.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WeaponController {
    private GameController gc;
    private Map<Integer, List<Weapon>> weaponary;

    public Weapon getRandomWeaponByLevel(int level) {
        List<Weapon> vars = weaponary.get(level);
        return vars.get(MathUtils.random(vars.size() - 1));
    }

    public WeaponController(GameController gc) {
        this.gc = gc;
        this.weaponary = new HashMap<>();
        try (BufferedReader reader = Gdx.files.internal("data/weaponary.csv").reader(8192)) {
            reader.readLine();
            String line = null;
            while ((line = reader.readLine()) != null) {
                Weapon weapon = new Weapon(line);
                if (!this.weaponary.containsKey(weapon.getLevel())) {
                    this.weaponary.put(weapon.getLevel(), new ArrayList<Weapon>());
                }
                this.weaponary.get(weapon.getLevel()).add(weapon);
            }
        } catch (IOException e) {
            throw new RuntimeException("Unable to read file 'data/weaponary.csv'");
        }
    }
}

package rpg.game.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import rpg.game.game.units.Unit;
import rpg.game.helpers.Assets;

import java.util.ArrayList;
import java.util.List;

public class GameMap {
    public enum CellType {
        GRASS, WATER, TREE
    }

    public enum DropType {
        NONE, GOLD
    }

    private class Cell {
        CellType type;
        DropType dropType;
        int dropPower;

        int index;

        public Cell() {
            type = CellType.GRASS;
            dropType = DropType.NONE;
            index = 0;
        }

        public void changeType(CellType to) {
            type = to;
            if (type == CellType.TREE) {
                index = MathUtils.random(4);
            }
        }
    }

    public static final int CELLS_X = 22;
    public static final int CELLS_Y = 12;
    public static final int CELL_SIZE = 60;
    public static final int FOREST_PERCENTAGE = 5;

    public int getCellsX() {
        return CELLS_X;
    }

    public int getCellsY() {
        return CELLS_Y;
    }

    public List<Berry> getListBerry() {
        return listBerry;
    }

    private Cell[][] data;

    private List<Berry> listBerry;


    private TextureRegion grassTexture;
    private TextureRegion goldTexture;
    private TextureRegion[] treesTextures;


    private TextureRegion berryTexture;


    public GameMap() {

        listBerry = new ArrayList<>();


        this.data = new Cell[CELLS_X][CELLS_Y];
        for (int i = 0; i < CELLS_X; i++) {
            for (int j = 0; j < CELLS_Y; j++) {
                this.data[i][j] = new Cell();
            }
        }
        int treesCount = (int) ((CELLS_X * CELLS_Y * FOREST_PERCENTAGE) / 100.0f);
        for (int i = 0; i < treesCount; i++) {
            this.data[MathUtils.random(0, CELLS_X - 1)][MathUtils.random(0, CELLS_Y - 1)].changeType(CellType.TREE);

        }

        for (int i = 0; i < CELLS_X; i++) {
            for (int j = 0; j < CELLS_Y; j++) {
                if (data[i][j].type == CellType.TREE) {
                   listBerry.add(new Berry(i,j));
                }
            }
        }

        this.grassTexture = Assets.getInstance().getAtlas().findRegion("grass");
        this.goldTexture = Assets.getInstance().getAtlas().findRegion("chest").split(60, 60)[0][0];
        this.treesTextures = Assets.getInstance().getAtlas().findRegion("trees").split(60, 90)[0];


        this.berryTexture = Assets.getInstance().getAtlas().findRegion("berry");

    }

    public boolean isCellPassable(int cx, int cy) {
        if (cx < 0 || cx > getCellsX() - 1 || cy < 0 || cy > getCellsY() - 1) {
            return false;
        }
        if (data[cx][cy].type != CellType.GRASS) {
            return false;
        }
        return true;
    }

    public void renderGround(SpriteBatch batch) {
        for (int i = 0; i < CELLS_X; i++) {
            for (int j = CELLS_Y - 1; j >= 0; j--) {
                batch.draw(grassTexture, i * CELL_SIZE, j * CELL_SIZE);
            }
        }
    }

    public void renderObjects(SpriteBatch batch) {
        for (int i = 0; i < CELLS_X; i++) {
            for (int j = CELLS_Y - 1; j >= 0; j--) {
                if (data[i][j].type == CellType.TREE) {
                    batch.draw(treesTextures[data[i][j].index], i * CELL_SIZE, j * CELL_SIZE);
                }


                for (int k = 0; k < listBerry.size(); k++) {
                    if(i == listBerry.get(k).getBerryX() && j == listBerry.get(k).getBerryY() && listBerry.get(k).isVisible() ){
                        batch.draw(berryTexture, i * CELL_SIZE + 15, j * CELL_SIZE + 25);
                    }
                }

                if (data[i][j].dropType == DropType.GOLD) {
                    batch.draw(goldTexture, i * CELL_SIZE, j * CELL_SIZE);
                }
            }
        }
    }

    public void generateBerries() {
        for (int i = 0; i < listBerry.size(); i++) {
            boolean chanceGenerate = MathUtils.random() < 0.5F;
            if (chanceGenerate && !listBerry.get(i).isVisible()) {
                listBerry.get(i).activeVisible();
            }

        }
    }

    // todo: перенести в калькулятор
    public void generateDrop(int cellX, int cellY, int power) {
        if (MathUtils.random() < 0.5f) {
            DropType randomDropType = DropType.GOLD;

            if (randomDropType == DropType.GOLD) {
                int goldAmount = power + MathUtils.random(power, power * 3);
                data[cellX][cellY].dropType = randomDropType;
                data[cellX][cellY].dropPower = goldAmount;
            }
        }
    }

    public boolean hasDropInCell(int cellX, int cellY) {
        return data[cellX][cellY].dropType != DropType.NONE;
    }

    public void checkAndTakeDrop(Unit unit) {
        Cell currentCell = data[unit.getCellX()][unit.getCellY()];
        if (currentCell.dropType == DropType.NONE) {
            return;
        }
        if (currentCell.dropType == DropType.GOLD) {
            unit.addGold(currentCell.dropPower);
        }
        currentCell.dropType = DropType.NONE;
        currentCell.dropPower = 0;
    }
}

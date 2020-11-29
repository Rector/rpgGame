package rpg.game.game;

public class Z {
    public static void main(String[] args) {
        int[] array = new int[8];
        for (int i = 0; i < array.length; i++) {
            array[i] = i;
        }

        float l = 0.1f;

        for (int i = 0; i < 24; i++) {
            int index = ((int) (l / 0.2f)) % array.length;
            l+=0.2f;
            System.out.println(array[index]);
        }


    }
}

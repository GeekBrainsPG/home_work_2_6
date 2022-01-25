import java.util.Arrays;
import java.util.List;

public class AppLauncher {

    private static final int size = 10000000;

    public static void main(String[] args) {
        synchronousOperation();
    }

    public static void synchronousOperation() {
        float[] array = createOneDimensionArray();
        long start = System.currentTimeMillis();

        for (int i = 0; i < array.length; i++) {
            array[i] = calculateValue(array[i], i);
        }

        System.out.println("Synchronous calculation took: " + (System.currentTimeMillis() - start) + "ms");
    }

    public static void asynchronousOperation() {

    }

    private static float[] createOneDimensionArray() {
        final float[] array = new float[size];

        Arrays.fill(array, 1);

        return array;
    }

    private static float calculateValue(float value, int index) {
        return (float) (value * Math.sin(0.2f + index / 5) * Math.cos(0.2f + index / 5) * Math.cos(0.4f + index / 2));
    }

}

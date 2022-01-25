import java.util.Arrays;

public class AppLauncher {

    private static final int size = 10000000;

    public static void main(String[] args) {
        synchronousOperation();
        asynchronousOperation();
    }

    public static void synchronousOperation() {
        float[] array = createOneDimensionArray();
        long start = System.currentTimeMillis();

        for (int i = 0; i < array.length; i++) {
            array[i] = AppLauncher.calculateValue(array[i], i);
        }

        System.out.println("Synchronous calculation took: " + (System.currentTimeMillis() - start) + "ms");
    }

    public static void asynchronousOperation() {
        float[] array = createOneDimensionArray();
        final int h = size / 2;
        long start = System.currentTimeMillis();
        float[] array1 = new float[h];
        float[] array2 = new float[h];

        System.arraycopy(array, 0, array1, 0, h);
        System.arraycopy(array, h, array2, 0, h);

        CalculationThread thread1 = new CalculationThread(array1);
        CalculationThread thread2 = new CalculationThread(array2);

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Asynchronous calculation took: " + (System.currentTimeMillis() - start) + "ms");
    }

    public static float calculateValue(float value, int index) {
        return (float) (value * Math.sin(0.2f + index / 5) * Math.cos(0.2f + index / 5) * Math.cos(0.4f + index / 2));
    }

    private static float[] createOneDimensionArray() {
        final float[] array = new float[size];

        Arrays.fill(array, 1);

        return array;
    }

}

class CalculationThread extends Thread {
    final float[] array;

    CalculationThread(float[] array) {
        this.array = array;
    }

    @Override
    public void run() {
        for (int i = 0; i < array.length; i++) {
            array[i] = AppLauncher.calculateValue(array[i], i);
        }
    }
}


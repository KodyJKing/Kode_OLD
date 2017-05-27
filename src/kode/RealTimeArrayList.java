package kode;

public class RealTimeArrayList<T> {

    private static final int INIT_MAJOR_SIZE = 1;

    private T[] major, minor, retired;
    private int majorIndex, minorIndex;

    public RealTimeArrayList() {
        major = (T[]) new Object[INIT_MAJOR_SIZE];
        minor = (T[]) new Object[INIT_MAJOR_SIZE >> 1];
        majorIndex = minor.length;
        minorIndex = 0;
    }

    public void show() {
        for (T item: major) {
            System.out.print(item);
            System.out.print(" ");
        }
        System.out.println();

        for (T item: minor) {
            System.out.print(item);
            System.out.print(' ');
        }
        System.out.println();
    }

    public void push(T item) {
        if (majorIndex >= major.length)
            sizeUp();
        major[majorIndex++] = item;
        if (minorIndex >= 0 && minor.length > 0)
            migrateUp();
    }

    public T pop() {
        if (majorIndex <= minor.length && minor.length > 0)
            sizeDown();
        T popped = major[--majorIndex];
        major[majorIndex] = null;
        if (minorIndex < minor.length && minor.length > 0)
            migrateDown();
        return popped;
    }

    public T get(int index) {
        return index < minorIndex ? minor[index] : major[index];
    }

    public void set(int index, T item) {
        if (index < minorIndex)
            minor[index] = item;
        else
            major[index] = item;
    }

    private void migrateUp() {
        minorIndex--;
        int migrationIndex = minorIndex;
        major[migrationIndex] = minor[migrationIndex];
        minor[migrationIndex] = null;
    }

    private void migrateDown() {
        int migrationIndex = minorIndex;
        minorIndex++;
        minor[migrationIndex] = major[migrationIndex];
        major[migrationIndex] = null;
    }

    private void sizeUp() {
        retired = minor;
        minor = major;
        int newLength = minor.length << 1;
        major = retired.length == newLength ? retired : (T[]) new Object[newLength];
        minorIndex = minor.length;
        majorIndex = minor.length;
    }

    private void sizeDown() {
        retired = major;
        major = minor;
        int newLength = minor.length >> 1;
        minor = retired.length == newLength ? retired : (T[]) new Object[newLength];
        majorIndex = major.length;
        minorIndex = 0;
    }
}

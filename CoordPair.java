public class CoordPair {
    double x;
    double y;
    double z;

    public CoordPair(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public CoordPair(int x, int y, int z) {
        this((double)x, (double)y, (double)z);
    }

    public CoordPair(int x, int y) {
        this(x, y, 0);
    }

    public CoordPair(double x, double y) {
        this(x, y, 0);
    }

    public CoordPair() {
        this(0, 0, 0);
    }

    public String toString() {
        return String.format("(%d, %d, %d)", (int)x, (int)y, (int)z);
    }
}
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
        // return String.format("(%d, %d, %d)", (int)x, (int)y, (int)z);
        return String.format("(%f, %f, %f)", x, y, z);
    }

    public double get_x() {
        return this.x;
    }

    public double get_y() {
        return this.y;
    }

    public double get_z() {
        return this.z;
    }
}
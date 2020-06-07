public class CoordPair {
    int x;
    int y;
    int z;

    public CoordPair(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public CoordPair(int x, int y) {
        this(x, y, 0);
    }

    public CoordPair() {
        this(0, 0, 0);
    }

    public String toString() {
        return String.format("(%d, %d, %d)", x, y, z);
    }
}
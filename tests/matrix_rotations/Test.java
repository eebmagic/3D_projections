import java.lang.Math;

public class Test {
    
    public static CoordPair x_rotate(CoordPair pos, int x_angle) {
        double angle = Math.toRadians(x_angle);

        double s = Math.sin(angle);
        double c = Math.cos(angle);

        int x = pos.x;
        int y = (int)(c * pos.y) + (int)(-s * pos.z);
        int z = (int)(s * pos.y) + (int)(c * pos.z);

        return new CoordPair(x, y, z);
    }

    public static CoordPair y_rotate(CoordPair pos, int y_angle) {
        double angle = Math.toRadians(y_angle);

        double s = Math.sin(angle);
        double c = Math.cos(angle);

        int x = (int)(c * pos.x) + (int)(s * pos.z);
        int y = pos.y;
        int z = (int)(-s * pos.x) + (int)(c * pos.z);

        return new CoordPair(x, y, z);
    }

    public static CoordPair z_rotate(CoordPair pos, int z_angle) {
        double angle = Math.toRadians(z_angle);

        double s = Math.sin(angle);
        double c = Math.cos(angle);

        int x = (int)(c * pos.x) + (int)(-s * pos.y);
        int y = (int)(s * pos.x) + (int)(c * pos.y);
        int z = pos.z;

        return new CoordPair(x, y, z);
    }

    public static void main(String[] args) {

        System.out.println("Hello World!");
        // CoordPair x = new CoordPair(1, 2, 3);
        CoordPair a = new CoordPair(0, 100, 0);
        int angle = 45;

        System.out.print("Original: ");
        System.out.println(a);
        
        CoordPair x = x_rotate(a, angle);
        System.out.print("Rotated x: ");
        System.out.println(x);

        CoordPair y = y_rotate(a, angle);
        System.out.print("Rotated y: ");
        System.out.println(y);   

        CoordPair z = z_rotate(a, angle);
        System.out.print("Rotated z: ");
        System.out.println(z);

        // CoordPair z2 = z_rotate(a, -90);
        // System.out.print("Rotated z: ");
        // System.out.println(z2);
	}
}

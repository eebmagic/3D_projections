import java.lang.Math;

public class Tree {
    private Node head = new Node();

    public Tree(CoordPair start, double startSize, int minSize, int angleChange) {
        head = new Node(start);
        head = makeTree(head, startSize, minSize, angleChange);
    }

    public Tree(double startSize, int minSize, int angleChange) {
        this(new CoordPair(), startSize, minSize, angleChange);
    }

    public Node makeTree(Node start, double startSize, int minSize, int angleChange) {
        makeHelper(start, startSize, minSize, -90, angleChange, true);
        return start;
    }

    public void makeHelper(Node start, double currSize, int minSize, int angle, int angleChange, boolean y) {
        if (currSize < minSize) {
            return;
        }

        // Make new point at given angle and distance
        CoordPair origin = start.get_pos();
        double realAngle = Math.toRadians(angle);

        double newX = currSize * Math.cos(realAngle);
        double newY = currSize * Math.sin(realAngle);

        CoordPair newPoint;
        if (y) {
            newPoint = new CoordPair(origin.get_x() + newX, origin.get_y() + newY, origin.get_z());
        } else {
            newPoint = new CoordPair(origin.get_x(), origin.get_y() + newY, origin.get_z() + newX);
        }
        Node newNode = new Node(newPoint);

        // Connect to that new node
        start.add_connection(newNode);

        // Make the child nodes for the new node
        double sizeAdjust = 0.58;  // NOTE: Visualization starts to freeze if sizeAdjust >= 0.60
                                // probably too many nodes to draw at that point
        double newSize = currSize * sizeAdjust;
        makeHelper(newNode, newSize, minSize, angle + angleChange, angleChange, true);
        makeHelper(newNode, newSize, minSize, angle - angleChange, angleChange, true);
        makeHelper(newNode, newSize, minSize, angle + angleChange, angleChange, false);
        makeHelper(newNode, newSize, minSize, angle - angleChange, angleChange, false);
    }

    public Node get_head() {
        return head;
    }
}
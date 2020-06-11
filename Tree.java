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
        makeHelper(start, startSize, minSize, -90, 90, angleChange);
        return start;
    }

    public void makeHelper(Node start, double currSize, int minSize, int xangle, int yangle, int angleChange) {
        if (currSize < minSize) {
            return;
        }

        // Make new point at given angle and distance
        double realX = Math.toRadians(xangle);
        double realZ = Math.toRadians(yangle);

        double newX = currSize * Math.cos(realX);
        double newY = currSize * Math.sin(realX);
        double newZ = currSize * Math.cos(realZ);

        CoordPair origin = start.get_pos();
        CoordPair newPoint = new CoordPair(origin.get_x() + newX, origin.get_y() + newY, origin.get_z() + newZ);
        Node newNode = new Node(newPoint);

        // Connect to that new node
        start.add_connection(newNode);

        // Make the child nodes for the new node
        double sizeAdjust = 0.58;   // NOTE: Visualization starts to freeze if sizeAdjust >= 0.60
                                    // probably too many nodes to draw at that point
        double newSize = currSize * sizeAdjust;
        makeHelper(newNode, newSize, minSize, xangle - angleChange, yangle - angleChange, angleChange);
        makeHelper(newNode, newSize, minSize, xangle - angleChange, yangle + angleChange, angleChange);
        makeHelper(newNode, newSize, minSize, xangle + angleChange, yangle - angleChange, angleChange);
        makeHelper(newNode, newSize, minSize, xangle + angleChange, yangle + angleChange, angleChange);
    }

    public Node get_head() {
        return head;
    }
}
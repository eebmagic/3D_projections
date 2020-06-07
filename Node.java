import java.util.ArrayList;

public class Node {
    private CoordPair pos;
    private ArrayList<Node> connected = new ArrayList<Node>();

    public Node(int x, int y, int z) {
        this.pos = new CoordPair(x, y, z);
    }

    public void add_connection(Node n, boolean fill) {
        connected.add(n);
        if (fill) {
            n.add_connection(this, false);    
        }
    }

    public void add_connection(Node n) {
        add_connection(n, true);
    }

    public CoordPair get_pos() {
        return this.pos;
    }

    public void set_pos(CoordPair newPos) {
        if (newPos == null) {
            throw new IllegalArgumentException("###ERROR: Null position passed to the Node's set_pos() function");
        }
        this.pos = newPos;
    }

    public ArrayList<Node> get_connected() {
        return this.connected;
    }
}
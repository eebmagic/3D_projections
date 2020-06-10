import java.util.HashSet;
import java.util.ArrayList;

// javafx imports
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.Group;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;


public class Program extends Application {
    /**
     * Function for getting the 2D projection of a 3D point
     * https://math.stackexchange.com/questions/2305792/3d-projection-on-a-2d-plane-weak-maths-ressources
     * @param point the 3D point that should be projected to a 2D point
     * @param angle the h value for the projection formula (see link)
     */
    public static CoordPair project(CoordPair point, int angle) {
        double multiplier = (double) angle / (angle + point.z);
        // // with top left of canvas as center
        // float x = ((float)point.x) * multiplier;
        // float y = ((float)point.y) * multiplier;

        // With center of screen as center
        double x = (((double)point.x) * multiplier) + (screen_width / 2);
        double y = (((double)point.y) * multiplier) + (screen_height / 2);
        
        CoordPair out = new CoordPair(x, y);
        // System.out.printf("%s => %s\n", point, out);

        return out;
    }

    /**
     * Draws two nodes and the edge between them on a canvas
     * @param gc the GraphicsContext canvas to draw on
     * @param edge the Edge object to draw (should have two nodes)
     * @param drawn_nodes the nodes that have already been drawn (and don't need to be redrawn)
     * @param angle the h value for the 3D => 2D projection 
     */
    public static void draw_edge(GraphicsContext gc, Edge edge, HashSet<Node> drawn_nodes, int angle) {
        Node a = null;
        Node b = null;
        for (Node n : edge.nodes) {
            if (!drawn_nodes.contains(n)) {
                // draw node
                CoordPair real = project(n.get_pos(), angle);
                // gc.strokeOval(real.x, real.y, node_size, node_size);
                gc.fillOval(real.x, real.y, node_size, node_size);
            }
            if (a == null) {
                a = n;
            } else {
                b = n;
            }
        }
        // draw edge
        CoordPair real_a = project(a.get_pos(), angle);
        CoordPair real_b = project(b.get_pos(), angle);
        gc.strokeLine(real_a.x + (node_size/2), real_a.y + (node_size/2), real_b.x + (node_size/2), real_b.y + (node_size/2));
    }

    /**
     * Iterates over every node connected to start and draws the edges between them
     * @param gc the graphics context on which to draw the nodes/edges
     * @param start the first / "head" node to iterate from
     * @param h_angle the value for h in the formula for 3D => 2D projection
     */
    public static void depth_draw(GraphicsContext gc, Node start, int h_angle) {
        HashSet<Edge> drawn_edges = new HashSet<>();
        HashSet<Node> drawn_nodes = new HashSet<>();
        draw_help(gc, start, drawn_edges, drawn_nodes, h_angle);
    }

    /**
     * Recursive helper method for drawing all nodes
     * @param drawn_edges the edges that have already been drawn
     * @param drawn_nodes the nodes that have already been drawn
     */
    public static void draw_help(GraphicsContext gc, Node curr, HashSet<Edge> drawn_edges, HashSet<Node> drawn_nodes, int angle) {
        for (Node n: curr.get_connected()) {
            Edge curr_edge = new Edge(curr, n);
            if (!drawn_edges.contains(curr_edge)) {
                draw_edge(gc, curr_edge, drawn_nodes, angle);
                drawn_edges.add(curr_edge);
                drawn_nodes.add(curr);
                drawn_nodes.add(n);
                draw_help(gc, n, drawn_edges, drawn_nodes, angle);
            }
        }
    }

    
    /**
     * function for rotating a point along x-axis by given angle
     * @param pos the CoordPair object that should be rotated
     * @param x_angle the angle by which to rotate the point
     * @return a new CoordPair that is the original rotated by given angle
     */
    public static CoordPair x_rotate(CoordPair pos, int x_angle) {
        double angle = Math.toRadians(x_angle);

        double s = Math.sin(angle);
        double c = Math.cos(angle);

        double x = pos.x;
        double y = ((c * (double)pos.y) + (-s * (double)pos.z));
        double z = ((s * (double)pos.y) + (c * (double)pos.z));

        return new CoordPair(x, y, z);
    }

    public static CoordPair y_rotate(CoordPair pos, int y_angle) {
        double angle = Math.toRadians(y_angle);

        double s = Math.sin(angle);
        double c = Math.cos(angle);

        double x = ((c * (double)pos.x) + (s * (double)pos.z));
        double y = pos.y;
        double z = ((-s * (double)pos.x) + (c * (double)pos.z));

        return new CoordPair(x, y, z);
    }

    public static CoordPair z_rotate(CoordPair pos, int z_angle) {
        double angle = Math.toRadians(z_angle);

        double s = Math.sin(angle);
        double c = Math.cos(angle);

        double x = ((c * (double)pos.x) + (-s * (double)pos.y));
        double y = ((s * (double)pos.x) + (c * (double)pos.y));
        double z = pos.z;

        return new CoordPair(x, y, z);
    }

    
    /**
     * perform a dfs on an object and rotate all node positions by given angles
     * @param start the starting / "head" node of the object to rotate
     * @param x_angle the angle by which to rotate along the x axis
     * @param y_angle the angle by which to rotate along the y axis
     * @param z_angle the angle by which to rotate along the z axis
     */
    public static void object_rotate(Node start, int x_angle, int y_angle, int z_angle) {
        HashSet<Node> finished_nodes = new HashSet<>();
        rotate_help(start, finished_nodes, x_angle, y_angle, z_angle);
        /// Return the rotated start node??
    }

    /**
     * Recursive helper method for rotating all nodes in an object
     * @param curr the current node that should be rotated
     * @param finished_nodes the HashSet of nodes that have already been rotated
     */
    public static void rotate_help(Node curr, HashSet<Node> finished_nodes, int x_angle, int y_angle, int z_angle) {
        // rotate curr node
        CoordPair newPos = curr.get_pos();
        newPos = x_rotate(newPos, x_angle);
        newPos = y_rotate(newPos, y_angle);
        newPos = z_rotate(newPos, z_angle);

        // System.out.printf("Original: %s\n", curr.get_pos());
        // System.out.printf("Rotated: %s\n", newPos);
        curr.set_pos(newPos);

        finished_nodes.add(curr);

        // check all connected nodes
        for (Node n : curr.get_connected()) {
            // recurse to nodes not finished
            if (!finished_nodes.contains(n)) {
                rotate_help(n, finished_nodes, x_angle, y_angle, z_angle);
            }
        }
    }

    public static int count_nodes(Node start) {
        HashSet<Node> counted_nodes = new HashSet<>();
        count_help(start, counted_nodes);
        return counted_nodes.size();
    }

    public static void count_help(Node curr, HashSet<Node> counted_nodes) {
        counted_nodes.add(curr);
        for (Node n : curr.get_connected()) {
            if (!counted_nodes.contains(n)) {
                count_help(n, counted_nodes);
            }
        }
    }


    /** ============================================= **/

    final static int screen_width = 800;
    final static int screen_height = 600;
    final static int node_size = 4;
    
    final int max_angle = 1000;
    final int min_angle = 300;
    int move = 30;
    int angle = max_angle;
    int direction = 1;
    

    @Override
    public void start(Stage primaryStage) {
        // Make Nodes
        // Nodes for cube
        Node a = new Node(-50, -50, -50);
        Node b = new Node(-50, -50, 50);
        Node c = new Node(-50, 50, -50);
        Node d = new Node(-50, 50, 50);
        Node i = new Node(50, -50, -50);
        Node j = new Node(50, -50, 50);
        Node k = new Node(50, 50, -50);
        Node l = new Node(50, 50, 50);
        // Make connections
        a.add_connection(b);
        a.add_connection(c);
        a.add_connection(i);
        l.add_connection(d);
        l.add_connection(k);
        l.add_connection(j);
        j.add_connection(b);
        j.add_connection(i);
        c.add_connection(d);
        c.add_connection(k);
        k.add_connection(i);
        d.add_connection(b);
        Node cube = a;


        // Nodes for triangle pyramid thing
        Node z = new Node(200, 300, 100);
        Node w = new Node(300, 300, 100);
        Node v = new Node(250, 300, 150);
        Node u = new Node(250, 250, 125);
        z.add_connection(w);
        z.add_connection(v);
        z.add_connection(u);
        w.add_connection(v);
        w.add_connection(u);
        v.add_connection(u);
        Node pyramid = z;


        // Make tree object
        Tree tree = new Tree(new CoordPair(0, screen_height * 0.4, 0), 180, 15, 60);
        // object_rotate(t.get_head(), 0, 0, 25);   // turn tree at jaunty angle

        int total_nodes = 0;
        total_nodes += count_nodes(tree.get_head());
        total_nodes += count_nodes(cube);
        total_nodes += count_nodes(pyramid);
        System.out.printf("   tree nodes: %d\n", count_nodes(tree.get_head()));
        System.out.printf("   cube nodes: %d\n", count_nodes(cube));
        System.out.printf("pyramid nodes: %d\n", count_nodes(pyramid));
        System.out.printf("  Total nodes: %d\n", total_nodes);

        // setup screen
        Group root = new Group();
        Canvas canvas = new Canvas(screen_width, screen_height);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        root.getChildren().add(canvas);
        primaryStage.setTitle("Renderer");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        new AnimationTimer() {
            @Override
            public void handle(long currentNanoTime) {
                /** ===================================================================== **/
                
                // Main drawing loop
                gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());  // clear screen
                // depth_draw(gc, cube, angle);   // draw cube
                // depth_draw(gc, pyramid, angle);   // draw pyramid

                depth_draw(gc, tree.get_head(), angle);

                // object_rotate(cube, 0, 5, 1); // rotate cube
                // object_rotate(pyramid, 1, 1, 1); // rotate pyramid
                object_rotate(tree.get_head(), 0, 1, 0); // rotate pyramid
                

                
                // Change angle for sweeping effect
                /**
                angle += move * direction;
                if (angle > max_angle) {
                    direction = -1;
                    // System.out.println("BIGGEST");
                } else if (angle <= min_angle && direction == -1) {
                    direction = 1;
                    // System.out.println("smallest");
                }
                */

                // Set framerate
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    System.out.println("Caught try block for timing.");
                }
            }
        }.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
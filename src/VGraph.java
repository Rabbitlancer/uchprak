import java.util.List;
import java.util.Random;
import java.lang.Math;

//объект, превращающий абстрактный граф в его плоскостное представление
public class VGraph {
    private static final double c1 = 2;
    private static final double c2 = 1;
    private static final double c3 = 1;
    private static final double c4 = 0.1;
    public int vertices;
    private List<VGraphNode> nodes;
    private Random rand;

    private double dist(Coord p1, Coord p2) {
        return Math.sqrt(Math.pow((p1.x-p2.x),2) + Math.pow((p1.y-p2.y),2));
    }

    public VGraph() {
        rand = new Random();
    }

    public void generateLayout() {
        for (VGraphNode vertex: nodes) {
            vertex.pos = new Coord(rand.nextInt(400), rand.nextInt(250));
        }

        for (int i = 0; i<100; ++i) {
            Coord force = new Coord(0,0);
            Double t;
            for (VGraphNode vertex: nodes) {
                for (VGraphNode other : nodes) {
                    if (vertex != other) {
                        if (vertex.isConnected(other)) {
                            t = c1 * Math.log(Math.abs(vertex.pos.x - other.pos.x) / c2);
                            force.x = t.intValue();
                            t = c1 * Math.log(Math.abs(vertex.pos.y - other.pos.y) / c2);
                            force.y = t.intValue();

                            if (other.pos.x<vertex.pos.x) force.x*=-1;
                            if (other.pos.y<vertex.pos.y) force.y*=-1;
                        } else {
                            t = c3 / (Math.pow(vertex.pos.x - other.pos.x, 2));
                            force.x = t.intValue();
                            t = c3 / (Math.pow(vertex.pos.y - other.pos.y, 2));
                            force.y = t.intValue();

                            if (other.pos.x>vertex.pos.x) force.x*=-1;
                            if (other.pos.y>vertex.pos.y) force.y*=-1;
                        }
                    }
                }
                t = c4*force.x;
                vertex.pos.x += t.intValue();
                t = c4*force.y;
                vertex.pos.y += t.intValue();
            }
        }
    }

    public Coord getCoord(int id) {
        return nodes.get(id).pos;
    }
}

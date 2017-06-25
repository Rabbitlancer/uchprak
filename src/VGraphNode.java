import java.util.ArrayList;
import java.util.List;

public class VGraphNode {
    public int connections;
    public int id;
    private static int nextid;
    public Coord pos;
    public List<Integer> edges;

    public VGraphNode() {
        id = nextid;
        nextid++;
        connections = 0;
        edges = new ArrayList<>(0);
    }

    public Boolean isConnected(VGraphNode to) {
        for (int cur: edges) {
            if (cur == to.id) return true;
        }
        return false;
    }
}

import java.util.List;

public class VGraphNode {
    public int connections;
    public int id;
    private static int nextid;
    public Coord pos;
    private List<Integer> edges;

    public VGraphNode() {
        id = nextid;
        nextid++;
    }

    public void setCoord(Coord pos) {
        this.pos.x = pos.x;
        this.pos.y = pos.y;
    }

    public Boolean isConnected(VGraphNode to) {
        for (int cur: edges) {
            if (cur == to.id) return true;
        }
        return false;
    }
}

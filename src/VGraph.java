import java.util.List;

//объект, превращающий абстрактный граф в его плоскостное представление
public class VGraph {
    public int vertices;
    private List<VGraphNode> nodes;

    public VGraph() {

    }

    public Coord getCoord(int id) {
        return nodes.get(id).pos;
    }
}

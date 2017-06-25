import java.util.*;
import java.lang.Math;

//объект, превращающий абстрактный граф в его плоскостное представление
public class VGraph {
    //константы сил визуализации
    private static final double c1 = 2;
    private static final double c2 = 1;
    private static final double c3 = 1;
    private static final double c4 = 0.1;

    private int vertices;
    private List<VGraphNode> nodes;
    private Random rand;

    public VGraph(MyGraph original) {
        rand = new Random();

        this.vertices = original.numV;
        nodes = new ArrayList<>(this.vertices);
        for (int i = 0; i<this.vertices; ++i) {
            int c = 0;
            VGraphNode node = new VGraphNode();
            for (int cur: original.IncidList[i]) {
                ++c;
                node.edges.add(cur);
            }
            node.connections = c;
            nodes.add(node);
        }
    }

    //расчетная функция для расположения вершин графа
    public void generateLayout() {
        for (VGraphNode vertex: nodes) {
            vertex.pos = new Coord(rand.nextInt(400), rand.nextInt(250));
        }

        //расчет сил
        for (int i = 0; i<100; ++i) {
            Coord force = new Coord(0,0);
            Double t;
            for (VGraphNode vertex: nodes) {
                for (VGraphNode other : nodes) {
                    if (vertex != other) {
                        if (vertex.isConnected(other)) {
                            //логарифмическая пружина для соединенных вершин
                            t = c1 * Math.log(Math.abs(vertex.pos.x - other.pos.x) / c2);
                            force.x = t.intValue();
                            t = c1 * Math.log(Math.abs(vertex.pos.y - other.pos.y) / c2);
                            force.y = t.intValue();

                            if (other.pos.x<vertex.pos.x) force.x*=-1;
                            if (other.pos.y<vertex.pos.y) force.y*=-1;
                        } else {
                            //обратно пропорциональная сила для не соединенных
                            t = c3 / (Math.pow(vertex.pos.x - other.pos.x, 2));
                            force.x = t.intValue();
                            t = c3 / (Math.pow(vertex.pos.y - other.pos.y, 2));
                            force.y = t.intValue();

                            if (other.pos.x>vertex.pos.x) force.x*=-1;
                            if (other.pos.y>vertex.pos.y) force.y*=-1;
                        }
                    }
                }
                //инкрементация сил
                t = c4*force.x;
                vertex.pos.x += t.intValue();
                t = c4*force.y;
                vertex.pos.y += t.intValue();

                //ограничение по размеру отрисовки
                if (vertex.pos.x<0) vertex.pos.x = 0;
                if (vertex.pos.x>428) vertex.pos.x = 428;
                if (vertex.pos.y<0) vertex.pos.y = 0;
                if (vertex.pos.y>300) vertex.pos.y = 300;
            }
        }
    }

    public List<Coord> getCoord() {
        List<Coord> res = new ArrayList<>(vertices);

        for (VGraphNode vertex: nodes) {
            res.add(vertex.pos);
        }
        return res;
    }
}

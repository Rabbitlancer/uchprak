import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.util.*;
import java.lang.Math;

//объект, превращающий абстрактный граф в его плоскостное представление
public class VGraph extends JPanel {
    public ArrayList<HashMap<String,Object>> vertices;
    public ArrayList<HashMap<String,Object>> edges;

    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        for (HashMap<String,Object> e: edges) {
            g2d.draw((Line2D) e.get("component"));
            Coord c1 = (Coord) e.get("posFrom");
            Coord c2 = (Coord) e.get("posTo");
            g.drawLine(c2.x, c2.y, c2.x+(c2.x-c1.x)/5, c2.y+(c2.y-c1.y)/5);
        }

        //Работа с цветом линии/фигуры
        // Запоминаем исходный цвет;
        Color oldColor = g.getColor();
        
        //рисование кружочков для вершин графа
        for (int i = 0; i<vertices.size(); ++i) {       //проходим по всем вершинам
            Coord vertC = (Coord) vertices.get(i).get("pos");    //координата вершины

            //если надо закрасить кружочки:
            Color newColor = (Color) vertices.get(i).get("color");
            // Устанавливаем новый цвет;
            g.setColor(newColor);
            g.fillOval(vertC.x-19,vertC.y-10,30,30);

            // Восстанавливаем исходный цвет;
            g.setColor(oldColor);
            g.drawOval(vertC.x-19,vertC.y-10,30,30);

            g.drawString((String) vertices.get(i).get("name"), vertC.x-10, vertC.y+10);
        }
    }

    private void addVertex(String name) {
        HashMap<String, Object> vertex = new HashMap<>(5);
        vertex.put("name", name);
        vertex.put("color",Color.white);
        vertex.put("selected",false);
        vertex.put("pos", new Coord(0,0));
        vertex.put("component", new JLabel());
        vertices.add(vertex);
    }

    private void addEdge(String fromName, String toName) {
        HashMap<String, Object> edge = new HashMap<>(5);
        edge.put("from", fromName);
        edge.put("to", toName);
        edge.put("posFrom", new Coord(0,0));
        edge.put("posTo", new Coord(0,0));
        edge.put("component", new Line2D.Double(0,0,0,0));
        edges.add(edge);
    }

    private HashMap<String,Object> vertexLookup(String name) {
        for (int i = 0; i<vertices.size(); ++i) {
            HashMap<String,Object> cur = vertices.get(i);
            if (cur.get("name").equals(name)) return cur;
        }

        return null;
    }

    private void reposition() {
        for (int i = 0; i<vertices.size(); ++i) {
            HashMap<String,Object> cur = vertices.get(i);
            Coord place = new Coord(214+(int)(130*Math.cos(6.28/vertices.size()*i)),150+(int)(130*Math.sin(6.28/vertices.size()*i)));
            cur.replace("pos", place);
            JLabel lbl = (JLabel) cur.get("component");
            lbl.setText((String) cur.get("name"));
            lbl.setBounds(place.x-10,place.y-10,30,30);
            lbl.setVisible(true);
            lbl.setBackground((Color) cur.get("color"));
            this.add(lbl);
        }

        for (int i = 0; i<edges.size(); ++i) {
            HashMap<String,Object> cur = edges.get(i);

            HashMap<String,Object> v = vertexLookup((String) cur.get("from"));
            Coord fromC = new Coord((Coord) v.get("pos"));
            v = vertexLookup((String) cur.get("to"));
            Coord toC = new Coord((Coord) v.get("pos"));

            double con = (fromC.x - toC.x)/(fromC.y - toC.y);
            double b = Math.sqrt(225/(Math.pow(con,2)+1));
            double a = Math.abs(con)*b;

            if (fromC.x>214) fromC.x = fromC.x - (int)a;
            else fromC.x = fromC.x + (int)a;

            if (fromC.y>150) fromC.y = fromC.y - (int)b;
            else fromC.y = fromC.y + (int)b;

            if (toC.x>214) toC.x = toC.x - (int)a;
            else toC.x = toC.x + (int)a;

            if (toC.y>150) toC.y = toC.y - (int)b;
            else toC.y = toC.y + (int)b;

            fromC.x -= 5;
            fromC.y += 5;
            toC.x -=5;
            toC.y += 5;

            Line2D line = (Line2D) cur.get("component");
            line.setLine(fromC.x, fromC.y, toC.x, toC.y);
        }
    }

    public void recolor(int id, Color c) {
        vertices.get(id).replace("color",c);
    }

    public VGraph(MyGraph original) {
        this.setBounds(0,0,428, 300);
        this.setLayout(null);

        vertices = new ArrayList<>(original.numV);
        edges = new ArrayList<>(original.numE);

        for (int i = 1; i<=original.numV; ++i) {
            this.addVertex("v" + String.valueOf(i));
        }

        for (int i = 0; i<original.numV; ++i) {
            for (int j = 0; j<original.IncidList[i].size(); ++j) {
                this.addEdge("v"+String.valueOf(i+1), "v"+String.valueOf(original.IncidList[i].get(j)+1));
            }
        }

        reposition();

        this.revalidate();
        this.repaint();
    }
}

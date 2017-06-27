import javafx.scene.shape.Ellipse;
import org.jgrapht.graph.*;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.*;
import java.lang.Math;

//объект, превращающий абстрактный граф в его плоскостное представление
public class VGraph extends ListenableDirectedGraph {
    public VGraph(MyGraph original) {
        super(DefaultEdge.class);

        for (int i = 1; i<=original.numV; ++i) {
            this.addVertex("v" + String.valueOf(i));
        }

        for (int i = 0; i<original.numV; ++i) {
            for (int j = 0; j<original.IncidList[i].size(); ++j) {
                this.addEdge("v"+String.valueOf(i+1), "v"+String.valueOf(original.IncidList[i].get(j)+1));
            }
        }
    }
}

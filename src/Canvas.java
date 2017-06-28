
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.Hashtable;
import java.util.Map;

import javax.swing.*;

import com.jgraph.layout.*;
import com.jgraph.layout.organic.JGraphFastOrganicLayout;
import com.mxgraph.util.mxConstants;
import javafx.scene.shape.Ellipse;
import org.jgraph.*;
import org.jgraph.graph.*;
import org.jgrapht.ext.JGraphModelAdapter;


public class Canvas extends  JApplet {
    private VGraph content;
    private JGraph jgraph;
    private Map<String,Hashtable> layoutMap;
    private JGraphFacade facade;
    private JGraphLayout layout;

    private static final Color     DEFAULT_BG_COLOR = Color.decode( "#FAFBFF" );
    private static final Dimension DEFAULT_SIZE = new Dimension( 428, 300 );

    private JGraphModelAdapter m_jgAdapter;

    //инициализация (убрать?)
    public void init() {

    }

    //обновление свойств окошка
    private void adjustDisplaySettings() {
        jgraph.setPreferredSize( DEFAULT_SIZE );

        Color  c        = DEFAULT_BG_COLOR;
        String colorStr = null;

        try {
            colorStr = getParameter( "bgcolor" );
        }
        catch( Exception e ) {}

        if( colorStr != null ) {
            c = Color.decode( colorStr );
        }

        jgraph.setBackground( c );

        jgraph.setAutoResizeGraph(true);
        facade = new JGraphFacade(jgraph);
        layout = new JGraphFastOrganicLayout();
        layout.run(facade);
        layoutMap = facade.createNestedMap(true,true);
        jgraph.getGraphLayoutCache().edit(layoutMap);

        jgraph.setMoveable(false);
        jgraph.setSelectionEnabled(false);
    }

    //конструктор
    public Canvas() {

    }

    //обновление содержимого
    public void setContent(MyGraph data) {
        content = new VGraph(data);

        GraphConstants.setBounds(JGraphModelAdapter.createDefaultVertexAttributes(),new Rectangle2D.Double(-10,-10,10,10));

        m_jgAdapter = new JGraphModelAdapter<>(content);
        jgraph = new JGraph(m_jgAdapter);
        jgraph.setBounds(0,0,428,300);

        adjustDisplaySettings();
        getContentPane().add(jgraph);
        resize(DEFAULT_SIZE);

        jgraph.refresh();
    }

    //перекраска посещенных вершин
    public void colorVisited(boolean[] visited, int num) {
        m_jgAdapter.beginUpdate();
        for (int i = 0; i<num; ++i) {
            if (visited[i]) {
                /*AttributeMap attr = new AttributeMap(1);
                attr.put(GraphConstants.BACKGROUND,Color.blue);
                m_jgAdapter.getVertexCell("v"+String.valueOf(i+1)).setAttributes(attr);*/
                //Hashtable cur = layoutMap.get("v"+String.valueOf(i+1));
                Hashtable attr = JGraphModelAdapter.createDefaultVertexAttributes();
                attr.replace(GraphConstants.BACKGROUND,Color.blue);
                facade.setAttributes(new DefaultGraphCell("v"+String.valueOf(i+1)), attr);
                //cur.replace("backgroundColor",Color.blue);
            } else {
                m_jgAdapter.getVertexCell("v"+String.valueOf(i+1)).getAttributes().put(GraphConstants.BACKGROUND,Color.cyan);
            }
        }
        layoutMap = facade.createNestedMap(true,true);
        jgraph.getGraphLayoutCache().edit(layoutMap);
        m_jgAdapter.endUpdate();
        jgraph.refresh();
    }

    public void select(int id) {
        if (id == -1) {
            jgraph.setSelectionCell(null);
        } else {
            jgraph.setSelectionCell(m_jgAdapter.getVertexCell("v" + String.valueOf(id + 1)));
        }
    }
}
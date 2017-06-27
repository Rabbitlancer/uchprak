
import java.awt.*;
import java.util.Map;

import javax.swing.*;

import com.jgraph.layout.*;
import com.jgraph.layout.organic.JGraphFastOrganicLayout;
import com.mxgraph.util.mxConstants;
import org.jgraph.*;
import org.jgraph.graph.*;
import org.jgrapht.ext.JGraphModelAdapter;


public class Canvas extends  JApplet {
    private VGraph content;
    private JGraph jgraph;

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
        JGraphFacade facade = new JGraphFacade(jgraph);
        JGraphLayout layout = new JGraphFastOrganicLayout();
        layout.run(facade);
        Map nested = facade.createNestedMap(true,true);
        jgraph.getGraphLayoutCache().edit(nested);

        jgraph.setMoveable(false);
        jgraph.setSelectionEnabled(false);
    }

    //конструктор
    public Canvas() {

    }

    //обновление содержимого
    public void setContent(MyGraph data) {
        content = new VGraph(data);

        m_jgAdapter = new JGraphModelAdapter(content);
        jgraph = new JGraph(m_jgAdapter);
        jgraph.setBounds(0,0,428,300);

        adjustDisplaySettings();
        getContentPane().add(jgraph);
        resize(DEFAULT_SIZE);

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
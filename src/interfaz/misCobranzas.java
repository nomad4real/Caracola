/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
*/
package interfaz;

//import TableColumnManager.TableColumnManager;
import interfaz.FileDrop;
import conexion.conexion;
import de.javasoft.plaf.synthetica.SyntheticaClassyLookAndFeel;
import java.awt.Color;
import java.awt.Label;
import java.awt.Point;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import static java.nio.file.Files.delete;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import metodos.consultas;
import metodos.consultor;
import org.jdesktop.swingx.decorator.ColorHighlighter;
import org.jdesktop.swingx.decorator.FontHighlighter;
import org.jdesktop.swingx.decorator.HighlightPredicate;
import org.jdesktop.swingx.decorator.Highlighter;
import org.jdesktop.swingx.decorator.PatternPredicate;
import org.jdesktop.swingx.decorator.ShadingColorHighlighter;
import metodos.ButtonColumn;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;









/**
 *
 * @author pilina
 */
public class misCobranzas extends javax.swing.JFrame {
    
consultor r= new consultor();
public static long idCobranza=0;
public static int idContacto;
public static long numPoliza=0;
    /**alerta_verde
     * Creates new form misCobranzas
     */
    public misCobranzas() {

    
        
        //   jcmb_MES.getSelectionModel().addListSelectionListener(new MyListener());
        //cargar todo antes del init.
        initComponents();//se carga el jfram
        
        this.tabla.setModel(r.buscarTablaCobranza((DefaultTableModel) tabla.getModel()));  //se carga el modelo
        jtxt_total_filas.setText(String.valueOf(tabla.getRowCount()));//se muestra la cantidad de filas
        //Inicio crear colores de filtro
        
        //setear en tabla colores
//        tabla.addHighlighter(new ColorHighlighter( new PatternPredicate("Pendiente", 12), Color.RED,Color.WHITE, null, Color.WHITE));
//        tabla.addHighlighter( new ColorHighlighter(new PatternPredicate("Ingresado", 12), Color.GREEN,null, null, null));
//        tabla.addHighlighter( new ColorHighlighter(new PatternPredicate("Cobrado", 12), Color.ORANGE,null,null,Color.ORANGE));
//        //fin setear en tabla
//inicio cargar combobox
     
        //fin cargar combox setear estado
 
//se añade listener para cuando se edita la tabla
            
     

 //   AbstractHyperlinkAction<Object> simpleAction = new AbstractHyperlinkAction<Object>(null) {

//    @Override
//    public void actionPerformed(ActionEvent e) {
//        try {
//            Path u= Paths.get(tabla.getValueAt(tabla.getSelectedRow(), 14).toString());
//            
//            // here goes what you want to do on activating the hyperlink
//            //LOG.info("hit: " + getTarget());
//            Runtime.getRuntime().exec(new String[]
//            {"rundll32", "url.dll,FileProtocolHandler",
//                u.toAbsolutePath().toString()});
//        } catch (IOException ex) {
//            Logger.getLogger(misCobranzas.class.getName()).log(Level.SEVERE, null, ex);
//        }
//          catch(NullPointerException ss){
//
//          }
//    }
//
//};
  //  TableCellRenderer renderer = new DefaultTableRenderer(new HyperlinkProvider(simpleAction));
  //  tabla.getColumnExt(14).setEditable(false);
  //  tabla.getColumnExt(14).setCellRenderer(renderer);
  //  tabla.setRowSelectionInterval(0, 0);
  //fin link celda
  Action delete = new AbstractAction()
{
    public void actionPerformed(ActionEvent e)
    {
        System.out.println("Holaaa El Button Column");
        JTable table = (JTable)e.getSource();
   
          

    }
};
 
 ButtonColumn buttonColumn = new ButtonColumn(tabla, delete, 5);


 //  tabla.setCellSelectionEnabled(true);
   //se centra la ventana
    this.setLocationRelativeTo(null);
    this.setLayout(null);
    //fin centrar
   // tabla.setSortOrder("Fecha Vencimiento", SortOrder.DESCENDING);  
      tabla.setRowHeight(50);
      
     // this.setExtendedState(JFrame.MAXIMIZED_BOTH); 
     final ArrayList<String[]> listadoPendientes= new ArrayList();
    final ArrayList<String> listadoCobrados= new ArrayList();
    final ArrayList<String> listadoIngresados= new ArrayList();
     DefaultTableModel modeloARevisar = new javax.swing.table.DefaultTableModel(new Object [][] {},new String [] {"id", "Poliza", "Item", "TipoDoc", "cuota", "Max Cuota", "monto", "moneda", "Fecha de Vencimiento", "Estado", "Observaciones"}) ;
       DateTimeFormatter dtf = DateTimeFormat.forPattern("dd/MM/YYYY");
       DateTime hoy = DateTime.now(); 
     for(int l=0;l<tabla.getRowCount();l++){
        modeloARevisar =r.buscarTablaPoliza(modeloARevisar, Long.parseLong(tabla.getValueAt(l, 2).toString()));
    
    
    for(int i=0;i<modeloARevisar.getRowCount();i++){//aca no se busca la tabla se reemplaza por modelo buscar tabla completa sin group
        if(modeloARevisar.getValueAt(i, 9).equals("Pendiente")){
            String[] fecha_cuota = new String[2];
             fecha_cuota[0]=modeloARevisar.getValueAt(i, 8).toString();
             fecha_cuota[1]=modeloARevisar.getValueAt(i, 4).toString();
            listadoPendientes.add(fecha_cuota);//fechas Pendientes
            if(i==0){
          
            }
            
        }else{//si no hay pendiente entonces se comprueba por Cobrado
            if(modeloARevisar.getValueAt(i, 9).equals("Cobrado")){
            
            listadoCobrados.add(modeloARevisar.getValueAt(i, 8).toString());//fechas Cobradas
               this.jlbl_total_cobrados.setText(String.valueOf(listadoCobrados.size()));
            
        }else{//entonces es ingresado
                listadoIngresados.add(modeloARevisar.getValueAt(i, 8).toString()); // fechas ingresadas
            
            }
        }

//este queda el primero


}
    String fechaPendiente="";

if(!(listadoIngresados.isEmpty())){////si  esta al dia no se eliminan defaults
    
}else{
    if(!(listadoCobrados.isEmpty())){
       if(!(listadoPendientes.isEmpty())){
            DateTime FA=dtf.parseDateTime(String.valueOf(listadoPendientes.get(0)[0]));   
       
       DateTime today = new DateTime();
     if(FA.isBefore(today)){//primero se ve la fecha
          System.out.println("La cuota Pendiente esta atrasada fila:"+l);
           }else{
         //Vigente, por vencer
     }
      
       }
    ;
    }else{

    if(!(listadoPendientes.isEmpty())){
        DateTime FA=dtf.parseDateTime(String.valueOf(listadoPendientes.get(0)[0]));   
       
       DateTime today = new DateTime();
     if(FA.isBefore(today)){//primero se ve la fecha
          System.out.println("La cuota Pendiente esta atrasada fila:"+l);
           }else{
         //Vigente, por vencer
     }
    }
    }
 
}
}
    }
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ver_jpopup = new javax.swing.JPopupMenu();
        jmenu_item_verPolizas = new javax.swing.JMenuItem();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabla = new org.jdesktop.swingx.JXTable(){
            @Override
            public Class getColumnClass(int column)
            {
                for (int row = 0; row < getRowCount(); row++)
                {
                    Object o = getValueAt(row, column);

                    if (o != null)
                    {
                        return o.getClass();
                    }
                }

                return Object.class;
            }

        };
        jPanel5 = new javax.swing.JPanel();
        jtxt_buscar = new org.jdesktop.swingx.JXSearchField();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jCheckBox3 = new javax.swing.JCheckBox();
        jCheckBox4 = new javax.swing.JCheckBox();
        jCheckBox5 = new javax.swing.JCheckBox();
        jCheckBox6 = new javax.swing.JCheckBox();
        jCheckBox7 = new javax.swing.JCheckBox();
        jCheckBox8 = new javax.swing.JCheckBox();
        jCheckBox9 = new javax.swing.JCheckBox();
        jCheckBox10 = new javax.swing.JCheckBox();
        jcbox_octubre = new javax.swing.JCheckBox();
        jCheckBox12 = new javax.swing.JCheckBox();
        jCheckBox2 = new javax.swing.JCheckBox();
        jPanel4 = new javax.swing.JPanel();
        jtbn_addUser = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        filtro_jcombo = new javax.swing.JComboBox<>();
        filtro_especial = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jtxt_total_filas = new javax.swing.JLabel();
        jlbl_total_cobrados = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jlbl_fecha = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jtxt_total_filas1 = new javax.swing.JLabel();
        jtxt_total_filas2 = new javax.swing.JLabel();

        jmenu_item_verPolizas.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        jmenu_item_verPolizas.setText("Ver Poliza");
        jmenu_item_verPolizas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jmenu_item_verPolizasMousePressed(evt);
            }
        });
        jmenu_item_verPolizas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmenu_item_verPolizasActionPerformed(evt);
            }
        });
        ver_jpopup.add(jmenu_item_verPolizas);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Mis Cobranzas");
        setAutoRequestFocus(false);

        jLabel5.setFont(new java.awt.Font("Eurostile LT Std", 3, 24)); // NOI18N
        jLabel5.setText("Fecha :");

        tabla.setForeground(new java.awt.Color(0, 0, 204));
        tabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nombre", "Poliza", "item", "Compañia", "VerPoliza"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, true, false, true, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabla.setFont(new java.awt.Font("Avenir LT Std 35 Light", 0, 18)); // NOI18N
        tabla.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tablaMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tabla);
        if (tabla.getColumnModel().getColumnCount() > 0) {
            tabla.getColumnModel().getColumn(0).setMinWidth(1);
            tabla.getColumnModel().getColumn(0).setPreferredWidth(1);
            tabla.getColumnModel().getColumn(0).setMaxWidth(1);
            tabla.getColumnModel().getColumn(1).setMinWidth(300);
            tabla.getColumnModel().getColumn(1).setPreferredWidth(300);
            tabla.getColumnModel().getColumn(1).setMaxWidth(300);
            tabla.getColumnModel().getColumn(2).setMinWidth(150);
            tabla.getColumnModel().getColumn(2).setPreferredWidth(150);
            tabla.getColumnModel().getColumn(2).setMaxWidth(150);
            tabla.getColumnModel().getColumn(3).setMinWidth(50);
            tabla.getColumnModel().getColumn(3).setPreferredWidth(50);
            tabla.getColumnModel().getColumn(3).setMaxWidth(50);
            tabla.getColumnModel().getColumn(4).setMinWidth(100);
            tabla.getColumnModel().getColumn(4).setPreferredWidth(100);
            tabla.getColumnModel().getColumn(4).setMaxWidth(100);
            tabla.getColumnModel().getColumn(5).setMinWidth(45);
            tabla.getColumnModel().getColumn(5).setPreferredWidth(45);
            tabla.getColumnModel().getColumn(5).setMaxWidth(45);
        }

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Busqueda", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Eurostile LT Std", 1, 14))); // NOI18N
        jPanel5.setToolTipText("Buscar");

        jtxt_buscar.setForeground(new java.awt.Color(0, 0, 255));
        jtxt_buscar.setToolTipText("Buscar");
        jtxt_buscar.setFont(new java.awt.Font("Eurostile LT Std", 1, 27)); // NOI18N
        jtxt_buscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtxt_buscarKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtxt_buscarKeyTyped(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Eurostile LT Std Condensed", 2, 14)); // NOI18N
        jLabel1.setText("Tambien puedes buscar apretando las teclas CTRL+F");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jtxt_buscar, javax.swing.GroupLayout.PREFERRED_SIZE, 329, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jtxt_buscar, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addContainerGap())
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Mostrando Meses", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Eurostile LT Std Condensed", 1, 18))); // NOI18N

        jCheckBox1.setText("Enero");

        jCheckBox3.setText("Febrero");

        jCheckBox4.setText("Marzo");

        jCheckBox5.setText("Abril");

        jCheckBox6.setText("Mayo");
        jCheckBox6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox6ActionPerformed(evt);
            }
        });

        jCheckBox7.setText("Junio");

        jCheckBox8.setText("Diciembre");

        jCheckBox9.setText("Noviembre");

        jCheckBox10.setText("Septiembre");

        jcbox_octubre.setText("Octubre");
        jcbox_octubre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbox_octubreActionPerformed(evt);
            }
        });

        jCheckBox12.setText("Agosto");

        jCheckBox2.setText("Julio");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jCheckBox3)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jCheckBox1)
                            .addComponent(jCheckBox6)
                            .addComponent(jCheckBox4)
                            .addComponent(jCheckBox7)
                            .addComponent(jCheckBox5))
                        .addGap(71, 71, 71)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jCheckBox12)
                            .addComponent(jCheckBox2)
                            .addComponent(jCheckBox10)
                            .addComponent(jCheckBox8)
                            .addComponent(jCheckBox9)
                            .addComponent(jcbox_octubre))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBox1)
                    .addComponent(jCheckBox2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBox3)
                    .addComponent(jCheckBox12))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBox4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBox6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBox5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jCheckBox7)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jCheckBox10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jcbox_octubre, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jCheckBox9, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jCheckBox8)
                        .addGap(97, 97, 97))))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Herramientas", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Eurostile LT Std Condensed", 1, 18))); // NOI18N

        jtbn_addUser.setIcon(new javax.swing.ImageIcon("C:\\madremia\\PRogramaLiberty\\descarga\\add-user (1).png")); // NOI18N
        jtbn_addUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtbn_addUserActionPerformed(evt);
            }
        });

        jLabel3.setText("Añadir Usuario");

        jLabel4.setText("Añadir Cobranza");

        jButton1.setIcon(new javax.swing.ImageIcon("C:\\madremia\\PRogramaLiberty\\descarga\\add-file.png")); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(2, 2, 2))
                    .addComponent(jtbn_addUser, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(jLabel4))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1))
                    .addComponent(jtbn_addUser, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        filtro_jcombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Todos Los Registros", "Solo los Pendientes", "Solo Ingresados", "Solo Cobrados" }));
        filtro_jcombo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                filtro_jcomboItemStateChanged(evt);
            }
        });

        filtro_especial.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Filtro Especial" }));
        filtro_especial.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                filtro_especialItemStateChanged(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Eurostile LT Std", 1, 18)); // NOI18N
        jLabel2.setText("Total Filas :");

        jtxt_total_filas.setFont(new java.awt.Font("Vectora LT Std Light", 1, 18)); // NOI18N
        jtxt_total_filas.setText("0");

        jlbl_total_cobrados.setFont(new java.awt.Font("Eurostile LT Std", 1, 18)); // NOI18N
        jlbl_total_cobrados.setText("Total Pendientes :");

        jLabel11.setFont(new java.awt.Font("Eurostile LT Std", 1, 18)); // NOI18N
        jLabel11.setText("Total Ingresados :");

        jlbl_fecha.setFont(new java.awt.Font("Eurostile LT Std", 3, 24)); // NOI18N
        jlbl_fecha.setText(consultas.fechaActual());

        jtxt_total_filas1.setFont(new java.awt.Font("Vectora LT Std Light", 1, 18)); // NOI18N
        jtxt_total_filas1.setText("0");

        jtxt_total_filas2.setFont(new java.awt.Font("Vectora LT Std Light", 1, 18)); // NOI18N
        jtxt_total_filas2.setText("0");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jlbl_fecha)
                        .addGap(59, 59, 59)
                        .addComponent(filtro_jcombo, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(63, 63, 63)
                        .addComponent(filtro_especial, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addGap(86, 86, 86)
                                        .addComponent(jtxt_total_filas))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jlbl_total_cobrados)
                                                .addGap(27, 27, 27)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jtxt_total_filas1)
                                            .addComponent(jtxt_total_filas2, javax.swing.GroupLayout.Alignment.TRAILING)))))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 657, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jScrollPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel5)
                        .addComponent(jlbl_fecha))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(filtro_jcombo, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(filtro_especial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jtxt_total_filas))
                        .addGap(25, 25, 25)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jlbl_total_cobrados)
                            .addComponent(jtxt_total_filas1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(jtxt_total_filas2))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    
    
    
    
    
    private void jcmb_MESActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcmb_MESActionPerformed
        
        
        
    }//GEN-LAST:event_jcmb_MESActionPerformed
       
    private void filtro_jcomboItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_filtro_jcomboItemStateChanged
     
        //Filtros por String basado en columnas
        try{
            
            if ((evt.getStateChange() == ItemEvent.SELECTED)) {
                
                TableRowSorter<TableModel> elQueOrdena = new TableRowSorter<TableModel>((TableModel) tabla.getModel());
                tabla.setRowSorter(elQueOrdena);
                
                int selection = this.filtro_jcombo.getSelectedIndex();
                switch (selection){
                    case 0:
                        elQueOrdena.setRowFilter(RowFilter.regexFilter(null));
                        this.jtxt_total_filas.setText(String.valueOf(tabla.getRowCount()));
                    case 1:
                        elQueOrdena.setRowFilter(RowFilter.regexFilter("Pendiente", 12));
                        this.jtxt_total_filas.setText(String.valueOf(tabla.getRowCount()));
                        ;break;
                    case 2:
                        elQueOrdena.setRowFilter(RowFilter.regexFilter("Ingresado", 12));
                        this.jtxt_total_filas.setText(String.valueOf(tabla.getRowCount()));
                        ;break;
                    case 3:
                        
                        elQueOrdena.setRowFilter(RowFilter.regexFilter("Cobrado", 12));
                        this.jtxt_total_filas.setText(String.valueOf(tabla.getRowCount()));
                }
            }
        }catch(java.lang.NullPointerException er){
            this.jtxt_total_filas.setText(String.valueOf(tabla.getRowCount()));
        }
        
        
        
        
    }//GEN-LAST:event_filtro_jcomboItemStateChanged
                    
    private void jmenu_item_verPolizasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmenu_item_verPolizasActionPerformed
    //se aprieta boton ver Polizas

    }//GEN-LAST:event_jmenu_item_verPolizasActionPerformed
    
    private void tablaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaMouseClicked
int fila=tabla.rowAtPoint(evt.getPoint());
int columna=tabla.columnAtPoint(evt.getPoint());
//columna transferencias
if(columna==7){
    String dir=r.buscarDirectorioPorId(Long.parseLong(tabla.getValueAt(fila, 0).toString()));
    if(dir.equals("")){//buscar directorio guardado
    
    }else{
        try {
           
        Runtime.getRuntime().exec(new String[]
        {"rundll32", "url.dll,FileProtocolHandler",
            dir});
    } catch (IOException ex) {
        Logger.getLogger(misCobranzas.class.getName()).log(Level.SEVERE, null, ex);
    }
    }

            
            
    
       
         
    }//GEN-LAST:event_tablaMouseClicked
    }
    private void jmenu_item_verPolizasMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jmenu_item_verPolizasMousePressed

      //  try {// verifica si tiene max cuotas o no
     //       maxCuotas= Integer.parseInt(tabla.getValueAt(tabla.getSelectedRow(),7).toString());
    //    } catch (NullPointerException e) {
  //          maxCuotas=0;
   //     }
   numPoliza=Long.parseLong(tabla.getValueAt(tabla.getSelectedRow(),2).toString());   
idCobranza=Long.parseLong(tabla.getValueAt(tabla.getSelectedRow(),0).toString());
           new verPolizas().setVisible(true);
        this.dispose();
        
        
        
    }//GEN-LAST:event_jmenu_item_verPolizasMousePressed
        
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
this.idCobranza=0;               
        new agregarCobranza().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jtbn_addUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtbn_addUserActionPerformed
     
        new agregarContacto().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jtbn_addUserActionPerformed

    private void tablaMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaMouseReleased
        //boton derecho activa menu Popup no sirve par MAC
        int r = tabla.rowAtPoint(evt.getPoint());
        if (r >= 0 && r < tabla.getRowCount()) {
            tabla.setRowSelectionInterval(r, r);
        } else {
            tabla.clearSelection();
        }

        int rowindex = tabla.getSelectedRow();
        if (rowindex < 0)
        return;

        if (evt.isPopupTrigger() && evt.getComponent() instanceof JTable ) {
            JPopupMenu popup = this.ver_jpopup;
            popup.show(evt.getComponent(), evt.getX(), evt.getY());
        }

    }//GEN-LAST:event_tablaMouseReleased

    private void jtxt_buscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxt_buscarKeyReleased
//filtrar listado por regex
TableRowSorter<TableModel> elQueOrdena = new TableRowSorter<TableModel>((TableModel) tabla.getModel());
                tabla.setRowSorter(elQueOrdena);
elQueOrdena.setRowFilter(RowFilter.regexFilter(this.jtxt_buscar.getText().toUpperCase(),2));
        
    }//GEN-LAST:event_jtxt_buscarKeyReleased

    private void jtxt_buscarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxt_buscarKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxt_buscarKeyTyped

    private void jCheckBox6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox6ActionPerformed

    private void jcbox_octubreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbox_octubreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jcbox_octubreActionPerformed

    private void filtro_especialItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_filtro_especialItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_filtro_especialItemStateChanged
    
    /**sout
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            javax.swing.UIManager.setLookAndFeel(new SyntheticaClassyLookAndFeel());
        } catch (ParseException ex) {
         //   Logger.getLogger(verPolizas.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
       //     Logger.getLogger(verPolizas.class.getName()).log(Level.SEVERE, null, ex);
        } 
        //</editor-fold>
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                
                new misCobranzas().setVisible(true);
                
                
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> filtro_especial;
    private javax.swing.JComboBox<String> filtro_jcombo;
    private javax.swing.JButton jButton1;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox10;
    private javax.swing.JCheckBox jCheckBox12;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JCheckBox jCheckBox4;
    private javax.swing.JCheckBox jCheckBox5;
    private javax.swing.JCheckBox jCheckBox6;
    private javax.swing.JCheckBox jCheckBox7;
    private javax.swing.JCheckBox jCheckBox8;
    private javax.swing.JCheckBox jCheckBox9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JCheckBox jcbox_octubre;
    private javax.swing.JLabel jlbl_fecha;
    private javax.swing.JLabel jlbl_total_cobrados;
    private javax.swing.JMenuItem jmenu_item_verPolizas;
    private javax.swing.JButton jtbn_addUser;
    private org.jdesktop.swingx.JXSearchField jtxt_buscar;
    private javax.swing.JLabel jtxt_total_filas;
    private javax.swing.JLabel jtxt_total_filas1;
    private javax.swing.JLabel jtxt_total_filas2;
    private org.jdesktop.swingx.JXTable tabla;
    private javax.swing.JPopupMenu ver_jpopup;
    // End of variables declaration//GEN-END:variables
}

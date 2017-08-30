/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
*/
package interfaz;

//import TableColumnManager.TableColumnManager;
import interfaz.FileDrop;
import conexion.conexion;
import de.javasoft.plaf.synthetica.SyntheticaBlackStarLookAndFeel;
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
        PatternPredicate patternPredicate = new PatternPredicate("Pendiente", 12);//condicion pendiente
        ColorHighlighter red = new ColorHighlighter(patternPredicate, null,
                Color.RED, null, Color.RED);//fin rojo
        patternPredicate = new PatternPredicate("Ingresado", 12);//condicion Ingresado
        ColorHighlighter verde = new ColorHighlighter(patternPredicate, Color.GREEN,
                Color.getHSBColor(137, 86, 95), Color.GREEN, Color.getHSBColor(137, 86, 95));//fin verde
        patternPredicate = new PatternPredicate("Cobrado", 12);  //condicion Cobrado
        ColorHighlighter amarillo = new ColorHighlighter(patternPredicate, null,
                Color.ORANGE, null, Color.ORANGE);//fin amarillo
        //fin crear colores
        //setear en tabla colores
        tabla.addHighlighter(red);
        tabla.addHighlighter(verde);
        tabla.addHighlighter(amarillo);
        //fin setear en tabla
//inicio cargar combobox
        final JComboBox comboBox = new JComboBox();
        comboBox.addItem("Pendiente");
        comboBox.addItem("Cobrado");
        comboBox.addItem("Ingresado");
        TableColumn miColumna = tabla.getColumnModel().getColumn(12);
        miColumna.setCellEditor(new DefaultCellEditor(comboBox));
        //fin cargar combox setear estado
        tabla.getModel().addTableModelListener(new TableModelListener() {
//se añade listener para cuando se edita la tabla
            
            @Override
            public void tableChanged(TableModelEvent evento) {
                switch(evento.getColumn()){
                    case 0:;break;
                    case 1:;break;
                    case 2:;break;
                    case 3:;break;
                    case 4:;break;
                    case 5:;break;
                    case 6:;break;
                    case 7:;break;
                    case 8:;break;
                    case 9:;break;
                    case 10:;break;
                    case 11:;break;
                    case 12://cuando se cambio el combobox
                        switch(comboBox.getSelectedItem().toString()){                          
                            case "Cobrado"://si el estado es cobrado entonces abre explorador para seleccionar transferencia de respaldo
                                JFileChooser fc = new JFileChooser();
                               
                                fc.setCurrentDirectory(new File("C:\\madremia\\PRogramaLiberty\\descarga"));
                                int returnVal =  fc.showOpenDialog(null);
                                File file = fc.getSelectedFile();
                                if (returnVal == JFileChooser.APPROVE_OPTION) {
                                    
                                    r.updatearEstadoConTransferencia(1, Long.parseLong(tabla.getValueAt(tabla.getSelectedRow(), 0).toString()), file);
                                }else{
                                    r.updatearEstadoConTransferencia(1, Long.parseLong(tabla.getValueAt(tabla.getSelectedRow(), 0).toString()), null);
                                }
                                ;break;
                                
                            case "Pendiente":
                                r.updatearEstadoConTransferencia(0, Long.parseLong(tabla.getValueAt(tabla.getSelectedRow(), 0).toString()), null);;break;
                                
                            case "Ingresado":r.updatearEstadoConTransferencia(2, Long.parseLong(tabla.getValueAt(tabla.getSelectedRow(), 0).toString()), null);;break;
                        }
                        
                        
                        ;break;
                    case 13:;break;
                    case 14:;break;
                    
                }
                
                
            }
        });

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
        JTable table = (JTable)e.getSource();
          if(table.getValueAt(table.getSelectedRow(), 14).toString().equals("C:\\madremia\\PRogramaLiberty\\descarga\\no-imagen.png")){
          //agregar transferencia
          
          
          }   

    }
};
 
 ButtonColumn buttonColumn = new ButtonColumn(tabla, delete, 14);


 //  tabla.setCellSelectionEnabled(true);
   //se centra la ventana
    this.setLocationRelativeTo(null);
    this.setLayout(null);
    //fin centrar
    tabla.setSortOrder("Fecha Vencimiento", SortOrder.DESCENDING);  
      tabla.setRowHeight(30);
      this.setExtendedState(JFrame.MAXIMIZED_BOTH); 

    }
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ver_jpopup = new javax.swing.JPopupMenu();
        jmenu_item_verPolizas = new javax.swing.JMenuItem();
        jPanel3 = new javax.swing.JPanel();
        jtxt_totalFilas = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        filtro_jcombo = new javax.swing.JComboBox<>();
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
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jtxt_total_filas = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jtxt_buscar = new org.jdesktop.swingx.JXSearchField();
        jLabel1 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jlbl_fecha = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jtxt_alerta_verde = new javax.swing.JLabel();
        jtxt_alerta_roja = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
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

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), "Tipo Consulta"));
        jPanel3.setToolTipText("Hola");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Filtros", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Eurostile LT Std Condensed", 1, 18))); // NOI18N

        filtro_jcombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Todos Los Registros", "Solo los Pendientes", "Solo Ingresados", "Solo Cobrados" }));
        filtro_jcombo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                filtro_jcomboItemStateChanged(evt);
            }
        });

        jCheckBox1.setText("Enero");

        jCheckBox3.setText("Febrero");

        jCheckBox4.setText("Marzo");

        jCheckBox5.setText("Abril");

        jCheckBox6.setText("Mayo");

        jCheckBox7.setText("Junio");

        jCheckBox8.setText("Diciembre");

        jCheckBox9.setText("Noviembre");

        jCheckBox10.setText("Septiembre");

        jcbox_octubre.setText("Octubre");

        jCheckBox12.setText("Agosto");

        jCheckBox2.setText("Julio");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(filtro_jcombo, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jCheckBox7)
                    .addComponent(jCheckBox6)
                    .addComponent(jCheckBox5)
                    .addComponent(jCheckBox4)
                    .addComponent(jCheckBox3)
                    .addComponent(jCheckBox1))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jCheckBox8)
                    .addComponent(jCheckBox9)
                    .addComponent(jCheckBox10)
                    .addComponent(jcbox_octubre)
                    .addComponent(jCheckBox12)
                    .addComponent(jCheckBox2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(filtro_jcombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jCheckBox2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jCheckBox12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jcbox_octubre)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jCheckBox10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jCheckBox9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jCheckBox8))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jCheckBox1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jCheckBox3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jCheckBox4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jCheckBox5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jCheckBox6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jCheckBox7)))))
                .addGap(0, 24, Short.MAX_VALUE))
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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1))
            .addComponent(jtbn_addUser, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Resumen", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Eurostile LT Std", 1, 14))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Vectora LT Std Light", 1, 18)); // NOI18N
        jLabel2.setText("Total Filas :");

        jLabel10.setFont(new java.awt.Font("Vectora LT Std Light", 1, 18)); // NOI18N
        jLabel10.setText("Total Pendientes :");

        jLabel11.setFont(new java.awt.Font("Vectora LT Std Light", 1, 18)); // NOI18N
        jLabel11.setText("Total Ingresados :");

        jLabel12.setFont(new java.awt.Font("Vectora LT Std Light", 1, 18)); // NOI18N
        jLabel12.setText("Por vencer (-30 dias) :");

        jtxt_total_filas.setFont(new java.awt.Font("Vectora LT Std Light", 1, 18)); // NOI18N
        jtxt_total_filas.setText("0");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jtxt_total_filas, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 42, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jtxt_total_filas))
                .addGap(17, 17, 17)
                .addComponent(jLabel10)
                .addGap(18, 18, 18)
                .addComponent(jLabel11)
                .addGap(18, 18, 18)
                .addComponent(jLabel12))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Busqueda", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Eurostile LT Std", 1, 14))); // NOI18N
        jPanel5.setToolTipText("Buscar");

        jtxt_buscar.setToolTipText("Buscar");
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

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 153, 0));
        jLabel14.setText("!");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1))
                    .addComponent(jtxt_buscar, javax.swing.GroupLayout.PREFERRED_SIZE, 329, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(64, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(35, Short.MAX_VALUE)
                .addComponent(jtxt_buscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jLabel1))
                .addGap(25, 25, 25))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jtxt_totalFilas)
                .addGap(247, 247, 247))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                .addComponent(jtxt_totalFilas))
        );

        jlbl_fecha.setText(consultas.fechaActual());

        jLabel5.setText("Fecha :");

        jtxt_alerta_verde.setText("Se han cobrado 28 registros.");

        jtxt_alerta_roja.setText("No existen alertas al momento");

        jLabel6.setText("Existen algunas polizas que venceran pronto");

        jLabel8.setIcon(new javax.swing.ImageIcon("C:\\madremia\\PRogramaLiberty\\descarga\\red_alert.png")); // NOI18N

        jLabel9.setIcon(new javax.swing.ImageIcon("C:\\madremia\\PRogramaLiberty\\descarga\\medium_alert.png")); // NOI18N

        jLabel7.setIcon(new javax.swing.ImageIcon("C:\\madremia\\PRogramaLiberty\\descarga\\ok.png")); // NOI18N

        tabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Ramo", "Contacto", "Poliza", "Item", "Tipo Documento", "Cuota", "Max Cuota", "Monto", "Moneda", "Fecha Vencimiento", "Compañia", "Estado", "Observaciones", "Transferencias"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, true, true, true, true, true, true, false, true, true, true, true, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabla.setFont(new java.awt.Font("Vectora LT Std Light", 0, 14)); // NOI18N
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
            tabla.getColumnModel().getColumn(0).setMinWidth(60);
            tabla.getColumnModel().getColumn(0).setMaxWidth(60);
            tabla.getColumnModel().getColumn(1).setMinWidth(80);
            tabla.getColumnModel().getColumn(1).setPreferredWidth(80);
            tabla.getColumnModel().getColumn(1).setMaxWidth(80);
            tabla.getColumnModel().getColumn(2).setMinWidth(200);
            tabla.getColumnModel().getColumn(2).setPreferredWidth(200);
            tabla.getColumnModel().getColumn(2).setMaxWidth(200);
            tabla.getColumnModel().getColumn(3).setMinWidth(80);
            tabla.getColumnModel().getColumn(3).setMaxWidth(80);
            tabla.getColumnModel().getColumn(4).setMinWidth(50);
            tabla.getColumnModel().getColumn(4).setMaxWidth(50);
            tabla.getColumnModel().getColumn(5).setMinWidth(70);
            tabla.getColumnModel().getColumn(5).setPreferredWidth(70);
            tabla.getColumnModel().getColumn(5).setMaxWidth(70);
            tabla.getColumnModel().getColumn(6).setResizable(false);
            tabla.getColumnModel().getColumn(6).setPreferredWidth(50);
            tabla.getColumnModel().getColumn(7).setResizable(false);
            tabla.getColumnModel().getColumn(7).setPreferredWidth(50);
            tabla.getColumnModel().getColumn(8).setMinWidth(70);
            tabla.getColumnModel().getColumn(8).setMaxWidth(70);
            tabla.getColumnModel().getColumn(9).setMinWidth(50);
            tabla.getColumnModel().getColumn(9).setMaxWidth(50);
            tabla.getColumnModel().getColumn(10).setMinWidth(100);
            tabla.getColumnModel().getColumn(10).setPreferredWidth(100);
            tabla.getColumnModel().getColumn(10).setMaxWidth(100);
            tabla.getColumnModel().getColumn(11).setMinWidth(80);
            tabla.getColumnModel().getColumn(11).setMaxWidth(80);
            tabla.getColumnModel().getColumn(12).setMinWidth(70);
            tabla.getColumnModel().getColumn(12).setMaxWidth(70);
            tabla.getColumnModel().getColumn(13).setMinWidth(200);
            tabla.getColumnModel().getColumn(13).setPreferredWidth(200);
            tabla.getColumnModel().getColumn(13).setMaxWidth(200);
            tabla.getColumnModel().getColumn(14).setMinWidth(35);
            tabla.getColumnModel().getColumn(14).setPreferredWidth(35);
            tabla.getColumnModel().getColumn(14).setMaxWidth(35);
        }

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1309, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtxt_alerta_roja, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtxt_alerta_verde, javax.swing.GroupLayout.PREFERRED_SIZE, 346, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(jlbl_fecha)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel9)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jtxt_alerta_verde, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel5)
                                .addComponent(jlbl_fecha))
                            .addComponent(jLabel6))
                        .addComponent(jLabel7))
                    .addComponent(jLabel8)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jtxt_alerta_roja)))
                .addGap(24, 24, 24)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
if(columna==14){
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
   numPoliza=Long.parseLong(tabla.getValueAt(tabla.getSelectedRow(),3).toString());   
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
    
    /**sout
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            javax.swing.UIManager.setLookAndFeel(new SyntheticaClassyLookAndFeel());
        } catch (ParseException ex) {
            Logger.getLogger(verPolizas.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(verPolizas.class.getName()).log(Level.SEVERE, null, ex);
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
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JCheckBox jcbox_octubre;
    private javax.swing.JLabel jlbl_fecha;
    private javax.swing.JMenuItem jmenu_item_verPolizas;
    private javax.swing.JButton jtbn_addUser;
    private javax.swing.JLabel jtxt_alerta_roja;
    private javax.swing.JLabel jtxt_alerta_verde;
    private org.jdesktop.swingx.JXSearchField jtxt_buscar;
    private javax.swing.JLabel jtxt_totalFilas;
    private javax.swing.JLabel jtxt_total_filas;
    private org.jdesktop.swingx.JXTable tabla;
    private javax.swing.JPopupMenu ver_jpopup;
    // End of variables declaration//GEN-END:variables
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaz;






import de.javasoft.plaf.synthetica.SyntheticaClassyLookAndFeel;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import metodos.consultor;
import org.jdesktop.swingx.decorator.ColorHighlighter;
import org.jdesktop.swingx.decorator.PatternPredicate;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 *
 * @author Nomad
 */
public class verPolizas extends javax.swing.JFrame {
    public static double primaTotal=0;
    public static consultor x= new consultor();
    public String alertaRoja;
    public String alertaAmarilla;
    public String alertaVerde;
    public static int company=2;
    int preventInicio=0;
    public static long numPoliza=misCobranzas.numPoliza;
    public Object itemDeseleccionado;
    /**
     * Creates new form NewJFrame
     */
    public verPolizas() {
        //condiciones para mensajes--------------------------
   
        
        initComponents();
        numPoliza=misCobranzas.numPoliza;
        this.tabla.setModel(x.buscarTablaPoliza((DefaultTableModel) this.tabla.getModel(), misCobranzas.numPoliza));
   

final JComboBox comboBox = new JComboBox();
        ItemListener j = new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
         
                   if (e.getStateChange() == ItemEvent.DESELECTED) {
          itemDeseleccionado= e.getItem();
         
                       System.out.println(itemDeseleccionado); 
       }
                   
             
            }
        };
        comboBox.addItemListener(j);
        comboBox.addItem("Pendiente");
        comboBox.addItem("Cobrado");
        comboBox.addItem("Ingresado");
        TableColumn miColumna = tabla.getColumnModel().getColumn(9);
        miColumna.setCellEditor(new DefaultCellEditor(comboBox));
        
        //se centra la clase
       
        
  
        jarea_observaciones.setText(x.buscarObservacionPorPoliza(numPoliza));
        //se agrega listener para tabla en caso de cambios
        
        
        this.jtxt_numPoliza.setText(String.valueOf(numPoliza));//se agrega num poliza
        
        this.jinicio.setText(tabla.getValueAt(0, 8).toString());
        this.jfinal.setText(String.valueOf(tabla.getValueAt(tabla.getRowCount()-1, 8)));
        Object[] r = x.buscarPorPoliza(numPoliza);// hay que cambiar la busqueda por poliza o copiar el metodo 
//se cargan los datos de la poliza
this.jtxt_numPoliza.setText(String.valueOf(r[2]));

this.jtxt_rut.setText(r[3].toString()+"-"+r[4].toString());
this.jtxt_nombre.setText(r[2].toString());
this.jlbl_telefonos.setText(r[5].toString());
this.jlbl_direccion.setText(r[6].toString());

switch (Integer.parseInt(r[15].toString())){
    case 0:this.jlbl_company.setIcon(new ImageIcon("C:\\madremia\\PRogramaLiberty\\descarga\\iconos\\hdi.jpg"));break;//hdi
    case 1:this.jlbl_company.setIcon(new ImageIcon("C:\\madremia\\PRogramaLiberty\\descarga\\iconos\\liberty.jpg"));break;//Liberty
    case 2:this.jlbl_company.setIcon(new ImageIcon("C:\\madremia\\PRogramaLiberty\\descarga\\iconos\\sura.jpg"));break;//sura

}

//this.jtxt_fechaInicio.setText(tabla.getValueAt(0, 1).toString());//fecha inicio
//  this.jtxt_fechaFinal.setText(tabla.getValueAt(tabla.getRowCount(),5).toString());
this.jtxt_numPoliza.setText(r[7].toString());
//se averigua si existe Max Cuota, con Max cuota existe fecha Final
try{ this.jtxt_maxCuotas.setText(r[11].toString());}catch(NullPointerException ex){ this.jtxt_maxCuotas.setText("0");}
this.jtxt_primaTotal.setText("0");
this.jtxt_cuota_pendiente.setText(r[10].toString());
DateTime today = new DateTime();
DateTime fechaVenc=DateTime.parse(r[10].toString());
int l=Days.daysBetween(today, fechaVenc).getDays();
double sumador=0.0;
        for (int i = 0; i < tabla.getRowCount(); i++) {
            sumador=Float.parseFloat(tabla.getValueAt(i, 6).toString())+sumador;
        }
this.jlbl_valor_prima.setText(String.valueOf(sumador));

//      System.out.println(tabla.getValueAt(0, 10).toString());
// se centra la ventana

  //se cargan la compañia en el icono
  switch(Integer.parseInt(r[15].toString())){
      case 0:this.jbtn_company.setIcon(new ImageIcon("C:\\madremia\\PRogramaLiberty\\descarga\\iconos\\hdi.jpg"));//hdi
      case 1:;//liberty
      case 2:;//sura
  
  }
  //se agrega combobox para updatear estado

        //fin comboxo updatear estado
        
        //se crean los colores
         PatternPredicate patternPredicate = new PatternPredicate("Pendiente", 9);//condicion pendiente
        ColorHighlighter red = new ColorHighlighter(patternPredicate, null,
                Color.RED, null, Color.WHITE);//fin rojo
        patternPredicate = new PatternPredicate("Ingresado", 9);//condicion Ingresado
        ColorHighlighter verde = new ColorHighlighter(patternPredicate, Color.GREEN,
                Color.WHITE, null, Color.GREEN);//fin verde
        patternPredicate = new PatternPredicate("Cobrado", 9);  //condicion Cobrado
        ColorHighlighter amarillo = new ColorHighlighter(patternPredicate, null,
                Color.ORANGE,//color no seleccionado
                null,
                Color.ORANGE);//color seleccionado
        //fin crear colores
        //font higltheger
      //  FontHighlighter black= new FontHighlighter(patternPredicate);
        //fin
        //setear en tabla colores
        tabla.addHighlighter(red);
        tabla.addHighlighter(verde);
       // tabla.addHighlighter(shading);
        tabla.addHighlighter(amarillo);
       // tabla.addHighlighter(black);
        //fin setear en tabla
     //se juntan todas las fechas en un array (dejar aparte otra clase)
    final ArrayList<String[]> listadoPendientes= new ArrayList();
    final ArrayList<String> listadoCobrados= new ArrayList();
    final ArrayList<String> listadoIngresados= new ArrayList();
    for(int i=0;i<tabla.getRowCount();i++){//this.jtxt_cuota_pendiente.setText(String.valueOf(r[6]));
        if(tabla.getValueAt(i, 9).equals("Pendiente")){
            String[] fecha_cuota = new String[2];
             fecha_cuota[0]=tabla.getValueAt(i, 8).toString();
             fecha_cuota[1]=tabla.getValueAt(i, 4).toString();
            listadoPendientes.add(fecha_cuota);//fechas Pendientes
            if(i==0){
          
            }
            
        }else{//si no hay pendiente entonces se comprueba por Cobrado
            if(tabla.getValueAt(i, 9).equals("Cobrado")){
            
            listadoCobrados.add(tabla.getValueAt(i, 8).toString());//fechas Cobradas
               this.jtxt_total_cobrados.setText(String.valueOf(listadoCobrados.size()));
            
        }else{//entonces es ingresado
                listadoIngresados.add(tabla.getValueAt(i, 8).toString()); // fechas ingresadas
            
            }
        }

//este queda el primero


}
    
    //Listeners
           WindowListener exitListener = new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                //     int confirm = JOptionPane.showOptionDialog(
                //        null, "Are You Sure to Close Application?",
                //         "Exit Confirmation", JOptionPane.YES_NO_OPTION,
                //           JOptionPane.QUESTION_MESSAGE, null, null, null);
                //     if (confirm == 0) {
                //         System.exit(0);
                /////      }
     
                e.getWindow().dispose();
                
                
                
            }
        };this.addWindowListener(exitListener);
      final   DateTimeFormatter dtf = DateTimeFormat.forPattern("dd/MM/YYYY");
       final JFileChooser xc = new JFileChooser();   
       xc.setCurrentDirectory(new File("C:\\madremia\\PRogramaLiberty\\descarga"));
          tabla.getModel().addTableModelListener(new TableModelListener() {
//se añade listener para cuando se edita la tabla
            
            @Override
            public void tableChanged(TableModelEvent evento) {
                
                switch(evento.getColumn()){
                    case 0://x.updateCell(newValue, id, false);break;//id
                    case 1:;break;//poliza
                    case 2:;break;//item
                    case 3:;break;//tipoDoc
                    case 4:;break;//cuota
                    case 5:;break;//MaxCuota
                    case 6:;break;//monto
                    case 7:;break;//moneda
                    case 8:;break;//fecha de vencimiento
                    case 9://estado
                        switch(comboBox.getSelectedItem().toString()){
                            case "Cobrado"://si el estado es cobrado entonces abre explorador para seleccionar transferencia de respaldo
                                if(evento.getFirstRow()==0){
                                    //aca la primera cuota
                                     int returnVal =  xc.showOpenDialog(null);
                                        File file = xc.getSelectedFile();
                                        if (returnVal == JFileChooser.APPROVE_OPTION) {
                                            x.updatearEstadoConTransferencia(1, Long.parseLong(tabla.getValueAt(tabla.getSelectedRow(), 0).toString()), file);
                                        }else{
                                            x.updatearEstadoConTransferencia(1, Long.parseLong(tabla.getValueAt(tabla.getSelectedRow(), 0).toString()), null);
                                        }
                                }else{
                                    if(tabla.getValueAt(evento.getFirstRow()-1, 9).equals("Pendiente")){
                                        //no se puede pero preguntar si desea rellenar
                                        if(JOptionPane.showConfirmDialog(jbtn_company, "Todavia existen cuotas pendiente antes que esta, desea completar todas hasta aca?")==1){
                      
                                            System.out.println("Acepto!!!!");
                                       
                                        }else{
                                          
                                        }
                                           comboBox.setSelectedItem("puta");
                                    }else{
                                        //si se puede
                                        int returnVal =  xc.showOpenDialog(null);
                                        File file = xc.getSelectedFile();
                                        if (returnVal == JFileChooser.APPROVE_OPTION) {
                                            x.updatearEstadoConTransferencia(1, Long.parseLong(tabla.getValueAt(tabla.getSelectedRow(), 0).toString()), file);
                                        }else{
                                            x.updatearEstadoConTransferencia(1, Long.parseLong(tabla.getValueAt(tabla.getSelectedRow(), 0).toString()), null);
                                        }
                                    }
                                    
                                }
                                
                                ;break;
                                
                            case "Pendiente":
                                
                                x.updatearEstadoConTransferencia(0, Long.parseLong(tabla.getValueAt(tabla.getSelectedRow(), 0).toString()), null);;break;
                                
                            case "Ingresado":x.updatearEstadoConTransferencia(2, Long.parseLong(tabla.getValueAt(tabla.getSelectedRow(), 0).toString()), null);;break;
                            
                        }
                        
                       
                    
                
                    
                }
                
                
            }
        });
    
   
//listadoFecha.get(0)
           this.jtxt_total_cobrados.setText(String.valueOf(listadoCobrados.size()));
         this.jtxt_totalPendientes.setText(String.valueOf(listadoPendientes.size()));
           this.jtxt_totalIngresadas.setText(String.valueOf(listadoIngresados.size())); 
//LocalDate currentDate = LocalDate.parse("22/02/2017");//localFecha.get(0)

String resultado[]=x.calcularPendiente(numPoliza);
DateTime w= DateTime.parse(resultado[3]);
        Date dateNew = w.toDate();
        SimpleDateFormat hh = new SimpleDateFormat("dd MM yyyy");
        hh.applyPattern("dd/MM/yyyy");
switch(resultado[0]){
    
    case "0"://Al dia
        jlbl_vigencia.setText("Al Dia");
        jlbl_vigencia.setForeground(Color.GREEN);
        this.jlbl_fechaPendiente.setText(hh.format(dateNew));
        jarea_info.setText("Al Dia:\nLa cuota todavia le faltan "+resultado[1]+" Dias. ");
        jarea_info.setForeground(Color.GREEN);      
        this.jtxt_cuota_pendiente.setText(resultado[2]);break;
        
    case "1"://Por vencer
        jlbl_vigencia.setText("Por Vencer");
        jlbl_vigencia.setForeground(Color.YELLOW);
        jarea_info.setForeground(Color.YELLOW);
        jarea_info.setText("Por Vencer:\nLa Poliza vencera en "+resultado[1]+" Dias.");
        
        this.jlbl_fechaPendiente.setText(hh.format(dateNew));
        this.jtxt_cuota_pendiente.setText(resultado[2]);
        this.jtxt_cuota_pendiente.setForeground(Color.YELLOW);
        this.jlbl_dias_diferencia.setText(resultado[1]);
        ;break;
    case "2"://Vencida Por Anularse
        jlbl_vigencia.setText("Vencida");
        jlbl_vigencia.setForeground(Color.RED);
        jarea_info.setText("Por Anularse:\nLa Poliza se anulara en \n"+(Integer.parseInt(resultado[1])+15)+" Dias.");
        jarea_info.setForeground(Color.RED);
        this.jlbl_fechaPendiente.setText(hh.format(dateNew));
        this.jtxt_cuota_pendiente.setText(resultado[2]);
        this.jtxt_cuota_pendiente.setForeground(Color.RED);
         this.jlbl_dias_diferencia.setText(resultado[1]);;break;
    case "3":
        int result=(Integer.parseInt(resultado[1])*-1);
        jlbl_vigencia.setText("Anulada");
        jlbl_vigencia.setForeground(new Color(153, 0, 153));
        jarea_info.setText("Anulada:\nLa poliza deberia estar ANULADA o cobrada por otro agente. \nHan pasado "+result+" dias desde su vencimiento");
        jarea_info.setForeground(new Color(153, 0, 153));
        this.jlbl_fechaPendiente.setText(hh.format(dateNew));
        this.jlbl_fechaPendiente.setForeground(new Color(153, 0, 153));
        this.jtxt_cuota_pendiente.setText(resultado[2]);
        this.jtxt_cuota_pendiente.setForeground(new Color(153, 0, 153));
         this.jlbl_dias_diferencia.setText(String.valueOf(result));;break;
    case "4"://VENCE HOY
        jlbl_vigencia.setText("Esta Poliza Vence HOY");
        jlbl_vigencia.setForeground(Color.PINK);
        jarea_info.setText("Esta Poliza Vence HOY!!");
        jarea_info.setForeground(Color.PINK);
        this.jlbl_fechaPendiente.setText(hh.format(dateNew));
        this.jtxt_cuota_pendiente.setText(resultado[2]);
        this.jtxt_cuota_pendiente.setForeground(Color.PINK);//VEnce hoy
         this.jlbl_dias_diferencia.setText(resultado[1]);;break;
      case "5"://Al dia
        jlbl_vigencia.setText("Poliza Completa");
        jlbl_vigencia.setForeground(Color.GREEN);
        this.jlbl_fechaPendiente.setText("");
        jarea_info.setText("Poliza Completa");
        jarea_info.setForeground(Color.GREEN);      
        this.jtxt_cuota_pendiente.setText(resultado[2]);break;  
        
}
 
//default en caso de contactos
ArrayList<String> itemPolizas = x.buscarNumPolizaxContacto(r[3].toString());
        for (int i = 0; i < itemPolizas.size(); i++) {
            this.jpolizas.addItem(itemPolizas.get(i));  
        }
        for (int i = 0; i < this.jpolizas.getItemCount(); i++) {
            if(this.jpolizas.getItemAt(i).toString().equals(this.jtxt_numPoliza.getText())){
               this.jpolizas.setSelectedItem(this.jpolizas.getItemAt(i));
            }
        }
        
        
        JComboBox comboBoxq = (JComboBox)this.jpolizas;
        comboBoxq.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                
                JComboBox comboBox = (JComboBox) evt.getSource();
                
                Object selected = comboBox.getSelectedItem();
                misCobranzas.numPoliza=Long.parseLong(selected.toString());
                new verPolizas().setVisible(true);
                verPolizas.super.dispose();
                
                
                
            }
        });
        
        
        this.jpolizas= (JComboBox) comboBoxq;
        
//no caxo de lo de abajo FIN Setear Fechas
if(tabla.getValueAt(0, 5).toString().isEmpty()){
    
}else{
    jpanel_generarConMaxCuota.setVisible(false);
}//muestra la generacion de cuotas si es que no hay maxcuota




final AbstractAction escapeAction = new AbstractAction() {
    private static final long serialVersionUID = 1L;

    @Override
    public void actionPerformed(ActionEvent ae) {
        
 
         
        dispose();
       
    }
};
//metodo para listener con tecla escape
this.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
        .put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "ESCAPE_KEY");
this.getRootPane().getActionMap().put("ESCAPE_KEY", escapeAction);


       

//SE PONE EL ESTADO ACTUAL DE LA POLIZA COMPARANDO CON LA FECHA ACTUAL
 //jlbl_vigencia.setText(alertaRoja);
this.jlbl_fechaHoy.setText(new LocalDate().toString());//muestra la fecha de hoy
this.setLocationRelativeTo(null);// se centra la ventana
this.setLayout(null);// se centra la ventana
    }//fin creacion clase
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

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
        jlbl_fechaHoy = new javax.swing.JLabel();
        jpanel_generarConMaxCuota = new javax.swing.JPanel();
        jtxt_primaTotal = new javax.swing.JTextField();
        jlbl_primaTotal = new javax.swing.JLabel();
        jlbl_maxCuotas = new javax.swing.JLabel();
        jbtn_agregarMaxCuotas = new javax.swing.JButton();
        jtcombo_maxCuota = new javax.swing.JComboBox<>();
        jPanel1 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jfinal = new javax.swing.JLabel();
        jtxt_fechaPrimaPendiente = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jtxt_numPoliza = new javax.swing.JLabel();
        jlbl_vigencia = new javax.swing.JLabel();
        jbtn_company = new javax.swing.JButton();
        jinicio = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jlbl_fechaPendiente = new javax.swing.JLabel();
        jtxt_cuota_pendiente = new javax.swing.JLabel();
        jtxt_posesivo = new javax.swing.JLabel();
        jtxt_maxCuotas = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jlbl_dias_diferencia = new javax.swing.JLabel();
        jlbl_company = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jtxt_nombre = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jtxt_rut = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jlbl_direccion = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jlbl_telefonos = new javax.swing.JLabel();
        jtbn_edit = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jpolizas = new javax.swing.JComboBox<>();
        jSeparator1 = new javax.swing.JSeparator();
        jbtn_add_poliza = new javax.swing.JButton();
        jLabel21 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jarea_observaciones = new org.jdesktop.swingx.JXTextArea();
        jLabel3 = new javax.swing.JLabel();
        jbtn_editarObservaciones = new org.jdesktop.swingx.JXButton();
        jLabel5 = new javax.swing.JLabel();
        jlbl_valor_prima = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jarea_info = new javax.swing.JTextArea();
        jLabel16 = new javax.swing.JLabel();
        jtbn_todas_ingresadas = new org.jdesktop.swingx.JXButton();
        jtxt_totalIngresadas = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jbtn_todas_cobradas = new org.jdesktop.swingx.JXButton();
        jbtn_todas_pendientes = new org.jdesktop.swingx.JXButton();
        jtxt_totalPendientes = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jtxt_total_cobrados = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Ver Polizas");
        setBounds(new java.awt.Rectangle(0, 0, 0, 0));
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        tabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id", "Poliza", "Item", "TipoDoc", "cuota", "Max Cuota", "monto", "moneda", "Fecha de Vencimiento", "Estado", "Observaciones"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, false, false, false, false, false, false, false, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabla.setFont(new java.awt.Font("Inconsolata", 0, 18)); // NOI18N
        jScrollPane1.setViewportView(tabla);
        if (tabla.getColumnModel().getColumnCount() > 0) {
            tabla.getColumnModel().getColumn(0).setMinWidth(1);
            tabla.getColumnModel().getColumn(0).setPreferredWidth(1);
            tabla.getColumnModel().getColumn(0).setMaxWidth(1);
            tabla.getColumnModel().getColumn(1).setMinWidth(150);
            tabla.getColumnModel().getColumn(1).setPreferredWidth(150);
            tabla.getColumnModel().getColumn(1).setMaxWidth(150);
            tabla.getColumnModel().getColumn(2).setMinWidth(50);
            tabla.getColumnModel().getColumn(2).setPreferredWidth(50);
            tabla.getColumnModel().getColumn(2).setMaxWidth(50);
            tabla.getColumnModel().getColumn(3).setMinWidth(120);
            tabla.getColumnModel().getColumn(3).setPreferredWidth(120);
            tabla.getColumnModel().getColumn(3).setMaxWidth(120);
            tabla.getColumnModel().getColumn(4).setMinWidth(50);
            tabla.getColumnModel().getColumn(4).setPreferredWidth(50);
            tabla.getColumnModel().getColumn(4).setMaxWidth(50);
            tabla.getColumnModel().getColumn(5).setMinWidth(50);
            tabla.getColumnModel().getColumn(5).setPreferredWidth(50);
            tabla.getColumnModel().getColumn(5).setMaxWidth(50);
            tabla.getColumnModel().getColumn(6).setMinWidth(80);
            tabla.getColumnModel().getColumn(6).setPreferredWidth(80);
            tabla.getColumnModel().getColumn(6).setMaxWidth(80);
            tabla.getColumnModel().getColumn(7).setMinWidth(65);
            tabla.getColumnModel().getColumn(7).setPreferredWidth(65);
            tabla.getColumnModel().getColumn(7).setMaxWidth(65);
            tabla.getColumnModel().getColumn(8).setMinWidth(130);
            tabla.getColumnModel().getColumn(8).setPreferredWidth(130);
            tabla.getColumnModel().getColumn(8).setMaxWidth(130);
            tabla.getColumnModel().getColumn(9).setMinWidth(140);
            tabla.getColumnModel().getColumn(9).setPreferredWidth(140);
            tabla.getColumnModel().getColumn(9).setMaxWidth(140);
        }

        jlbl_fechaHoy.setFont(new java.awt.Font("Bookman Old Style", 1, 12)); // NOI18N
        jlbl_fechaHoy.setText("25/12/2017");

        jpanel_generarConMaxCuota.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Generar Filas Con Maximo de Cuotas", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N

        jtxt_primaTotal.setText("0");

        jlbl_primaTotal.setText("Prima Total :");

        jlbl_maxCuotas.setText("Cant Cuotas :");

        jbtn_agregarMaxCuotas.setText("Agregar");
        jbtn_agregarMaxCuotas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtn_agregarMaxCuotasActionPerformed(evt);
            }
        });

        jtcombo_maxCuota.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" }));

        javax.swing.GroupLayout jpanel_generarConMaxCuotaLayout = new javax.swing.GroupLayout(jpanel_generarConMaxCuota);
        jpanel_generarConMaxCuota.setLayout(jpanel_generarConMaxCuotaLayout);
        jpanel_generarConMaxCuotaLayout.setHorizontalGroup(
            jpanel_generarConMaxCuotaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpanel_generarConMaxCuotaLayout.createSequentialGroup()
                .addComponent(jlbl_primaTotal)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jtxt_primaTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jlbl_maxCuotas)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jtcombo_maxCuota, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jbtn_agregarMaxCuotas)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jpanel_generarConMaxCuotaLayout.setVerticalGroup(
            jpanel_generarConMaxCuotaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpanel_generarConMaxCuotaLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jpanel_generarConMaxCuotaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtxt_primaTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlbl_primaTotal)
                    .addComponent(jlbl_maxCuotas)
                    .addComponent(jbtn_agregarMaxCuotas)
                    .addComponent(jtcombo_maxCuota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Poliza", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Eurostile LT Std", 1, 14))); // NOI18N

        jLabel7.setFont(new java.awt.Font("Eurostile LT Std", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("Fecha Inicio:");

        jLabel10.setFont(new java.awt.Font("Eurostile LT Std", 1, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 0));
        jLabel10.setText("Fecha Final :");

        jfinal.setFont(new java.awt.Font("Vectora LT Std Light", 0, 18)); // NOI18N
        jfinal.setText("24/10/2017");

        jtxt_fechaPrimaPendiente.setFont(new java.awt.Font("Vectora LT Std Light", 0, 18)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Vectora LT Std Light", 1, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 255));
        jLabel6.setText("N° :");

        jtxt_numPoliza.setFont(new java.awt.Font("Eurostile LT Std Condensed", 0, 28)); // NOI18N
        jtxt_numPoliza.setText("777");
        jtxt_numPoliza.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        jlbl_vigencia.setFont(new java.awt.Font("Vectora LT Std Light", 0, 24)); // NOI18N
        jlbl_vigencia.setForeground(new java.awt.Color(0, 204, 0));
        jlbl_vigencia.setText("Vigente");

        jbtn_company.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtn_companyActionPerformed(evt);
            }
        });

        jinicio.setFont(new java.awt.Font("Vectora LT Std Light", 0, 18)); // NOI18N
        jinicio.setText("24/10/2017");

        jLabel12.setFont(new java.awt.Font("Eurostile LT Std", 1, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 0, 0));
        jLabel12.setText("Fecha Cuota Pendiente :");

        jlbl_fechaPendiente.setFont(new java.awt.Font("Vectora LT Std Light", 1, 18)); // NOI18N
        jlbl_fechaPendiente.setForeground(new java.awt.Color(0, 204, 0));
        jlbl_fechaPendiente.setText("Sin Fecha Pendiente");

        jtxt_cuota_pendiente.setFont(new java.awt.Font("Vectora LT Std Light", 0, 24)); // NOI18N
        jtxt_cuota_pendiente.setText("4");

        jtxt_posesivo.setFont(new java.awt.Font("Vectora LT Std Light", 0, 24)); // NOI18N
        jtxt_posesivo.setText("de");

        jtxt_maxCuotas.setFont(new java.awt.Font("Vectora LT Std Light", 0, 24)); // NOI18N
        jtxt_maxCuotas.setText("12");

        jLabel14.setFont(new java.awt.Font("Eurostile LT Std", 1, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 0, 0));
        jLabel14.setText("Cuota Actual :");

        jLabel17.setFont(new java.awt.Font("Eurostile LT Std", 1, 18)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(0, 0, 0));
        jLabel17.setText("Dias de Atraso :");

        jlbl_dias_diferencia.setFont(new java.awt.Font("Avenir LT Std 35 Light", 1, 18)); // NOI18N
        jlbl_dias_diferencia.setText("0");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(76, 76, 76)
                        .addComponent(jtxt_fechaPrimaPendiente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(365, 365, 365))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12)
                            .addComponent(jLabel14))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(jLabel7)
                                        .addGap(158, 158, 158))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel17)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jfinal, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jinicio, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                            .addComponent(jtxt_cuota_pendiente)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(jtxt_posesivo)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(jtxt_maxCuotas)))
                                    .addComponent(jlbl_dias_diferencia, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jlbl_fechaPendiente, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(101, 101, 101))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jlbl_vigencia)
                                .addGap(12, 12, 12)
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jtxt_numPoliza, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jlbl_company)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addComponent(jbtn_company)
                        .addGap(36, 36, 36))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jlbl_vigencia)
                        .addComponent(jtxt_numPoliza)
                        .addComponent(jLabel6)
                        .addComponent(jlbl_company))
                    .addComponent(jbtn_company, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jLabel7))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jinicio, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jfinal, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jlbl_fechaPendiente))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jtxt_maxCuotas, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jtxt_cuota_pendiente)
                        .addComponent(jLabel14)
                        .addComponent(jtxt_posesivo)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(jlbl_dias_diferencia, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jtxt_fechaPrimaPendiente))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Contacto", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Eurostile LT Std", 1, 14))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Eurostile LT Std", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("Nombre:");

        jtxt_nombre.setFont(new java.awt.Font("Inconsolata", 1, 18)); // NOI18N
        jtxt_nombre.setText("Benjamin Casanova");

        jLabel11.setFont(new java.awt.Font("Eurostile LT Std", 0, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 0, 0));
        jLabel11.setText("Rut :");

        jtxt_rut.setEditable(false);
        jtxt_rut.setFont(new java.awt.Font("Avenir LT Std 35 Light", 1, 18)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Eurostile LT Std", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Telefonos :");

        jlbl_direccion.setText("Sin información");

        jLabel19.setFont(new java.awt.Font("Eurostile LT Std", 0, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(0, 0, 0));
        jLabel19.setText("Dirección :");

        jlbl_telefonos.setText("Sin información");

        jtbn_edit.setIcon(new javax.swing.ImageIcon("C:\\madremia\\PRogramaLiberty\\descarga\\edit.png")); // NOI18N
        jtbn_edit.setToolTipText("Editar Contacto");
        jtbn_edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtbn_editActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Eurostile LT Std", 0, 14)); // NOI18N
        jLabel2.setText("Polizas");

        jpolizas.setFont(new java.awt.Font("Avenir LT Std 35 Light", 1, 18)); // NOI18N
        jpolizas.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jpolizasItemStateChanged(evt);
            }
        });
        jpolizas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jpolizasActionPerformed(evt);
            }
        });

        jbtn_add_poliza.setIcon(new javax.swing.ImageIcon("C:\\madremia\\PRogramaLiberty\\descarga\\add-file-small.png")); // NOI18N
        jbtn_add_poliza.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jbtn_add_polizaMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(145, 145, 145)
                        .addComponent(jLabel2)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel11)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel19))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 80, Short.MAX_VALUE)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jlbl_direccion)
                                    .addComponent(jlbl_telefonos)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(jtxt_rut, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jtxt_nombre, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 80, Short.MAX_VALUE)
                                        .addComponent(jtbn_edit, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jpolizas, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(jbtn_add_poliza, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jtxt_nombre))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(jtxt_rut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jtbn_edit)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jlbl_direccion))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(jlbl_telefonos))
                .addGap(17, 17, 17)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jbtn_add_poliza, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
                    .addComponent(jpolizas))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel21.setFont(new java.awt.Font("Eurostile LT Std", 0, 12)); // NOI18N
        jLabel21.setText("Fecha de Hoy:");

        jarea_observaciones.setEditable(false);
        jarea_observaciones.setColumns(20);
        jarea_observaciones.setForeground(new java.awt.Color(0, 0, 153));
        jarea_observaciones.setLineWrap(true);
        jarea_observaciones.setRows(5);
        jarea_observaciones.setText("Poliza se pago completa los dias 30.");
        jarea_observaciones.setFont(new java.awt.Font("Eurostile LT Std", 0, 14)); // NOI18N
        jScrollPane2.setViewportView(jarea_observaciones);

        jLabel3.setFont(new java.awt.Font("Eurostile LT Std", 2, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(153, 153, 153));
        jLabel3.setText("Observaciones");

        jbtn_editarObservaciones.setText("Editar");
        jbtn_editarObservaciones.setFont(new java.awt.Font("Vectora LT Std Light", 2, 11)); // NOI18N
        jbtn_editarObservaciones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtn_editarObservacionesActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Eurostile LT Std", 0, 12)); // NOI18N
        jLabel5.setText("Valor Prima :");

        jlbl_valor_prima.setFont(new java.awt.Font("Vectora LT Std Light", 0, 18)); // NOI18N
        jlbl_valor_prima.setText("No existen cuotas!");

        jarea_info.setEditable(false);
        jarea_info.setColumns(20);
        jarea_info.setFont(new java.awt.Font("Inconsolata", 1, 14)); // NOI18N
        jarea_info.setLineWrap(true);
        jarea_info.setRows(5);
        jScrollPane3.setViewportView(jarea_info);

        jLabel16.setFont(new java.awt.Font("Eurostile LT Std", 1, 18)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 102, 0));
        jLabel16.setText("Cobradas");

        jtbn_todas_ingresadas.setForeground(new java.awt.Color(0, 0, 102));
        jtbn_todas_ingresadas.setText("Dejar todas Ingresadas");
        jtbn_todas_ingresadas.setFont(new java.awt.Font("Vectora LT Std Light", 0, 14)); // NOI18N
        jtbn_todas_ingresadas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtbn_todas_ingresadasActionPerformed(evt);
            }
        });

        jtxt_totalIngresadas.setFont(new java.awt.Font("Eurostile LT Std", 1, 18)); // NOI18N
        jtxt_totalIngresadas.setForeground(new java.awt.Color(0, 153, 0));
        jtxt_totalIngresadas.setText("0");

        jLabel9.setFont(new java.awt.Font("Eurostile LT Std", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 153, 0));
        jLabel9.setText("Ingresadas");

        jbtn_todas_cobradas.setForeground(new java.awt.Color(0, 0, 102));
        jbtn_todas_cobradas.setText("Dejar todas Cobradas");
        jbtn_todas_cobradas.setFont(new java.awt.Font("Vectora LT Std Light", 0, 14)); // NOI18N
        jbtn_todas_cobradas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtn_todas_cobradasActionPerformed(evt);
            }
        });

        jbtn_todas_pendientes.setForeground(new java.awt.Color(0, 0, 102));
        jbtn_todas_pendientes.setText("Dejar todas Pendientes");
        jbtn_todas_pendientes.setFont(new java.awt.Font("Vectora LT Std Light", 0, 14)); // NOI18N
        jbtn_todas_pendientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtn_todas_pendientesActionPerformed(evt);
            }
        });

        jtxt_totalPendientes.setFont(new java.awt.Font("Eurostile LT Std", 1, 18)); // NOI18N
        jtxt_totalPendientes.setForeground(new java.awt.Color(255, 0, 0));
        jtxt_totalPendientes.setText("0");

        jLabel15.setFont(new java.awt.Font("Eurostile LT Std", 1, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 0, 0));
        jLabel15.setText("Pendientes");

        jtxt_total_cobrados.setFont(new java.awt.Font("Eurostile LT Std", 1, 18)); // NOI18N
        jtxt_total_cobrados.setForeground(new java.awt.Color(255, 102, 0));
        jtxt_total_cobrados.setText("0");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 493, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel21)
                                        .addGap(24, 24, 24)
                                        .addComponent(jlbl_fechaHoy)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel5)
                                        .addGap(18, 18, 18)
                                        .addComponent(jlbl_valor_prima))
                                    .addComponent(jpanel_generarConMaxCuota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel16)
                                    .addComponent(jLabel15)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING))
                                .addGap(26, 26, 26)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jtxt_totalPendientes, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jtxt_totalIngresadas, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jtxt_total_cobrados, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 63, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jbtn_todas_cobradas, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jbtn_todas_pendientes, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jtbn_todas_ingresadas, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(37, 37, 37)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(jLabel3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jbtn_editarObservaciones, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(44, 44, 44)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jtxt_total_cobrados)
                                    .addComponent(jLabel16)
                                    .addComponent(jbtn_todas_pendientes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(10, 10, 10)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jtxt_totalPendientes)
                                    .addComponent(jbtn_todas_cobradas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel15))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jtxt_totalIngresadas)
                                    .addComponent(jtbn_todas_ingresadas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel9)))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jbtn_editarObservaciones, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(46, 46, 46))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jlbl_fechaHoy)
                            .addComponent(jLabel21)
                            .addComponent(jLabel5)
                            .addComponent(jlbl_valor_prima))
                        .addGap(18, 18, 18)
                        .addComponent(jpanel_generarConMaxCuota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jtbn_editActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtbn_editActionPerformed
//editar conacto
 
new agregarContacto().setVisible(true);
   
    }//GEN-LAST:event_jtbn_editActionPerformed

    private void jbtn_companyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtn_companyActionPerformed

    }//GEN-LAST:event_jbtn_companyActionPerformed

    private void jbtn_agregarMaxCuotasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtn_agregarMaxCuotasActionPerformed
        //se agrega el maximo de cutoa

        // TODO add your handling code here:

        int maxCuota=Integer.parseInt(this.jtcombo_maxCuota.getSelectedItem().toString());
        double prima=Double.parseDouble(this.jtxt_primaTotal.getText());
        agregarCobranza x = new agregarCobranza();
        x.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jbtn_agregarMaxCuotasActionPerformed

    private void jbtn_editarObservacionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtn_editarObservacionesActionPerformed
      // editar TEXT AREA OBSERVACION POLIZA
       if(this.jbtn_editarObservaciones.getText().equals("Editar")){
        this.jbtn_editarObservaciones.setText("Aceptar");
      this.jarea_observaciones.setEditable(true);
      //insertar a la bd
      
       }else{
           x.insertarObservacionPoliza(numPoliza,jarea_observaciones.getText());//se graba las observacion
             this.jbtn_editarObservaciones.setText("Editar");
      this.jarea_observaciones.setEditable(false);
       }
       
      
       
    }//GEN-LAST:event_jbtn_editarObservacionesActionPerformed

    private void jpolizasItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jpolizasItemStateChanged
//if(Long.parseLong(this.jpolizas.getSelectedItem().toString())==misCobranzas.numPoliza){
//        System.out.println("No cambiar"+evt.getItem());
//
//}else{
//     System.out.println("Cambiar"+evt.getItem());
//     this.numPoliza=Long.parseLong(this.jpolizas.getSelectedItem().toString());
//     misCobranzas.numPoliza=this.numPoliza;
//     new verPolizas().setVisible(true);
//     this.dispose();
//}
//
    }//GEN-LAST:event_jpolizasItemStateChanged

    private void jpolizasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jpolizasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jpolizasActionPerformed

    private void jbtn_todas_pendientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtn_todas_pendientesActionPerformed
JOptionPane.showConfirmDialog(jbtn_company, "Esta seguro que desea dejar esta poliza como pendiente?");        // TODO add your handling code here:
    }//GEN-LAST:event_jbtn_todas_pendientesActionPerformed

    private void jbtn_todas_cobradasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtn_todas_cobradasActionPerformed
   x.updatear_poliza_a_cobrado(numPoliza);//se cambian todas a cobrados
   this.repaint();
    }//GEN-LAST:event_jbtn_todas_cobradasActionPerformed

    private void jtbn_todas_ingresadasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtbn_todas_ingresadasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtbn_todas_ingresadasActionPerformed

    private void jbtn_add_polizaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbtn_add_polizaMouseClicked
numPoliza=Long.parseLong(jpolizas.getSelectedItem().toString());

new agregarCobranza().setVisible(true);
this.dispose();
    }//GEN-LAST:event_jbtn_add_polizaMouseClicked

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
   
    }//GEN-LAST:event_formKeyPressed

    /**
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
       

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new verPolizas().setVisible(true);
                
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextArea jarea_info;
    private org.jdesktop.swingx.JXTextArea jarea_observaciones;
    private javax.swing.JButton jbtn_add_poliza;
    private javax.swing.JButton jbtn_agregarMaxCuotas;
    private javax.swing.JButton jbtn_company;
    private org.jdesktop.swingx.JXButton jbtn_editarObservaciones;
    private org.jdesktop.swingx.JXButton jbtn_todas_cobradas;
    private org.jdesktop.swingx.JXButton jbtn_todas_pendientes;
    private javax.swing.JLabel jfinal;
    private javax.swing.JLabel jinicio;
    private javax.swing.JLabel jlbl_company;
    private javax.swing.JLabel jlbl_dias_diferencia;
    private javax.swing.JLabel jlbl_direccion;
    private javax.swing.JLabel jlbl_fechaHoy;
    private javax.swing.JLabel jlbl_fechaPendiente;
    private javax.swing.JLabel jlbl_maxCuotas;
    private javax.swing.JLabel jlbl_primaTotal;
    private javax.swing.JLabel jlbl_telefonos;
    private javax.swing.JLabel jlbl_valor_prima;
    private javax.swing.JLabel jlbl_vigencia;
    private javax.swing.JPanel jpanel_generarConMaxCuota;
    private javax.swing.JComboBox<String> jpolizas;
    private javax.swing.JButton jtbn_edit;
    private org.jdesktop.swingx.JXButton jtbn_todas_ingresadas;
    private javax.swing.JComboBox<String> jtcombo_maxCuota;
    private javax.swing.JLabel jtxt_cuota_pendiente;
    private javax.swing.JLabel jtxt_fechaPrimaPendiente;
    private javax.swing.JLabel jtxt_maxCuotas;
    private javax.swing.JLabel jtxt_nombre;
    private javax.swing.JLabel jtxt_numPoliza;
    private javax.swing.JLabel jtxt_posesivo;
    private javax.swing.JTextField jtxt_primaTotal;
    private javax.swing.JTextField jtxt_rut;
    private javax.swing.JLabel jtxt_totalIngresadas;
    private javax.swing.JLabel jtxt_totalPendientes;
    private javax.swing.JLabel jtxt_total_cobrados;
    private org.jdesktop.swingx.JXTable tabla;
    // End of variables declaration//GEN-END:variables
}

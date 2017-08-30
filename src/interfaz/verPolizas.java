/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaz;





import de.javasoft.plaf.synthetica.SyntheticaBlackStarLookAndFeel;
import de.javasoft.plaf.synthetica.SyntheticaStandardLookAndFeel;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.plaf.synth.SynthLookAndFeel;
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
   

    
    /**
     * Creates new form NewJFrame
     */
    public verPolizas() {
        //condiciones para mensajes--------------------------
   
        
        initComponents();
        this.tabla.setModel(x.buscarTablaPoliza((DefaultTableModel) this.tabla.getModel(), misCobranzas.numPoliza));
        //se centra la clase
        try{//verifica si hay max cuotas
            Integer.parseInt(x.buscarPorId(misCobranzas.idCobranza)[7].toString());
            
        }catch(NullPointerException ex){
            this.jpanel_generarConMaxCuota.setEnabled(false);
            this.jlbl_maxCuotas.setEnabled(false);this.jlbl_primaTotal.setEnabled(false);
            this.jtcombo_maxCuota.setEnabled(false);
            this.jbtn_agregarMaxCuotas.setEnabled(false);
            this.jtxt_primaTotal.setEnabled(false);
        }
        
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
                new misCobranzas().setVisible(true);
                e.getWindow().dispose();
                
                
                
            }
        };
        this.addWindowListener(exitListener);
        
        //se agrega listener para tabla en caso de cambios
        
        
        this.jtxt_numPoliza.setText(String.valueOf(misCobranzas.numPoliza));//se agrega num poliza
        
        this.jinicio.setText(tabla.getValueAt(0, 8).toString());
        this.jfinal.setText(String.valueOf(tabla.getValueAt(tabla.getRowCount()-1, 8)));
        Object[] r = x.buscarPorId(misCobranzas.idCobranza);
//se cargan los datos de la poliza
this.jtxt_numPoliza.setText(String.valueOf(r[2]));
this.jtxt_cuota_pendiente.setText(String.valueOf(r[6]));
this.jtxt_rut.setText(r[3].toString()+"-"+r[4].toString());
this.jtxt_nombre.setText(r[2].toString());
this.jlbl_telefonos.setText(r[5].toString());
this.jlbl_direccion.setText(r[6].toString());
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

//      System.out.println(tabla.getValueAt(0, 10).toString());
// se centra la ventana

  //se cargan la compañia en el icono
  switch(Integer.parseInt(r[15].toString())){
      case 0:this.jbtn_company.setIcon(new ImageIcon("C:\\madremia\\PRogramaLiberty\\descarga\\iconos\\hdi.jpg"));//hdi
      case 1:;//liberty
      case 2:;//sura
  
  }
  //se agrega combobox para updatear estado
  final JComboBox comboBox = new JComboBox();
        comboBox.addItem("Pendiente");
        comboBox.addItem("Cobrado");
        comboBox.addItem("Ingresado");
        TableColumn miColumna = tabla.getColumnModel().getColumn(9);
        miColumna.setCellEditor(new DefaultCellEditor(comboBox));
        //fin comboxo updatear estado
        
        //se crean los colores
        PatternPredicate patternPredicate = new PatternPredicate("Pendiente", 9);//condicion pendiente
        ColorHighlighter red = new ColorHighlighter(patternPredicate, null,
                Color.RED, null, Color.RED);
        patternPredicate = new PatternPredicate("Ingresado", 9);//condicion Ingresado
        ColorHighlighter verde = new ColorHighlighter(patternPredicate, Color.GREEN,
                Color.getHSBColor(137, 86, 95), Color.GREEN, Color.getHSBColor(137, 86, 95));
        patternPredicate = new PatternPredicate("Cobrado", 9);  //condicion Cobrado
        ColorHighlighter amarillo = new ColorHighlighter(patternPredicate, null,
                Color.ORANGE, null, Color.ORANGE);
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
                    case 9://Estado
                        switch(comboBox.getSelectedItem().toString()){// !!!!!!no funciona, la pantalla queda atras de esta
                            case "Cobrado"://si el estado es cobrado entonces abre explorador para seleccionar transferencia de respaldo
                                JFileChooser xc = new JFileChooser();
                                
                                xc.setCurrentDirectory(new File("C:\\madremia\\PRogramaLiberty\\descarga"));
                                int returnVal =  xc.showOpenDialog(null);
                                File file = xc.getSelectedFile();
                                if (returnVal == JFileChooser.APPROVE_OPTION) {
                                    
                                    x.updatearEstadoConTransferencia(1, Long.parseLong(tabla.getValueAt(tabla.getSelectedRow(), 0).toString()), file);
                                }else{
                                    x.updatearEstadoConTransferencia(1, Long.parseLong(tabla.getValueAt(tabla.getSelectedRow(), 0).toString()), null);
                                }
                                ;break;
                                
                            case "Pendiente":
                                x.updatearEstadoConTransferencia(0, Long.parseLong(tabla.getValueAt(tabla.getSelectedRow(), 0).toString()), null);;break;
                                
                            case "Ingresado":x.updatearEstadoConTransferencia(2, Long.parseLong(tabla.getValueAt(tabla.getSelectedRow(), 0).toString()), null);;break;
                        }
                        
                        
                        ;break;
                    case 10:;break;//observaciones
                
                    
                }
                
                
            }
        });
    
    
   ArrayList<String> listadoFecha= new ArrayList();

for(int i=0;i<tabla.getRowCount();i++){
if(tabla.getValueAt(i, 9).equals("Pendiente")){

     listadoFecha.add(tabla.getValueAt(i, 8).toString());
     
     
}else{

}

//este queda el primero
   

}
//listadoFecha.get(0)
    
//LocalDate currentDate = LocalDate.parse("22/02/2017");//localFecha.get(0)

this.jlbl_fechaPendiente.setText(listadoFecha.get(0));//se pone la primera cuota pendiente
if(tabla.getValueAt(0, 5).toString().isEmpty()){

}else{
        jpanel_generarConMaxCuota.removeAll();
}//muestra la generacion de cuotas si es que no hay maxcuota
// Format for input
DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ssZ");
// Parsing the date
LocalDate jodatime = new LocalDate();
 
this.jlbl_fechaHoy.setText(jodatime.toString());//muestra la fecha de hoy
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
        tabla = new org.jdesktop.swingx.JXTable();
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
        jLabel9 = new javax.swing.JLabel();
        jtxt_maxCuotas = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
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
        jComboBox1 = new javax.swing.JComboBox<>();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel4 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jLabel21 = new javax.swing.JLabel();
        jlbl_red = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jXButton1 = new org.jdesktop.swingx.JXButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Ver Polizas");
        setAlwaysOnTop(true);

        tabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id", "Poliza", "Item", "TipoDoc", "cuota", "Max Cuota", "monto", "moneda", "Fecha de Vencimiento", "Estado", "Observaciones"
            }
        ));
        tabla.setFont(new java.awt.Font("Vectora LT Std Light", 0, 14)); // NOI18N
        jScrollPane1.setViewportView(tabla);

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
        jLabel7.setForeground(new java.awt.Color(0, 204, 204));
        jLabel7.setText("Fecha Inicio:");

        jLabel10.setFont(new java.awt.Font("Eurostile LT Std", 1, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 204, 204));
        jLabel10.setText("Fecha Final :");

        jfinal.setFont(new java.awt.Font("Vectora LT Std Light", 0, 18)); // NOI18N
        jfinal.setText("24/10/2017");

        jtxt_fechaPrimaPendiente.setFont(new java.awt.Font("Vectora LT Std Light", 0, 18)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Vectora LT Std Light", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 255));
        jLabel6.setText("N° :");

        jtxt_numPoliza.setFont(new java.awt.Font("Eurostile LT Std Condensed", 0, 18)); // NOI18N
        jtxt_numPoliza.setText("777");

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
        jLabel12.setForeground(new java.awt.Color(0, 204, 204));
        jLabel12.setText("Fecha Cuota Pendiente :");

        jlbl_fechaPendiente.setFont(new java.awt.Font("Vectora LT Std Light", 0, 18)); // NOI18N
        jlbl_fechaPendiente.setForeground(new java.awt.Color(204, 0, 0));
        jlbl_fechaPendiente.setText("24/10/2017");

        jtxt_cuota_pendiente.setFont(new java.awt.Font("Vectora LT Std Light", 0, 24)); // NOI18N
        jtxt_cuota_pendiente.setText("4");

        jLabel9.setFont(new java.awt.Font("Vectora LT Std Light", 0, 24)); // NOI18N
        jLabel9.setText("de");

        jtxt_maxCuotas.setFont(new java.awt.Font("Vectora LT Std Light", 0, 24)); // NOI18N
        jtxt_maxCuotas.setText("12");

        jLabel14.setFont(new java.awt.Font("Eurostile LT Std", 1, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 204, 204));
        jLabel14.setText("Cuota Actual :");

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
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jlbl_vigencia)
                        .addGap(39, 39, 39)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jtxt_numPoliza, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jbtn_company)
                        .addGap(36, 36, 36))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(133, 133, 133)
                        .addComponent(jinicio, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(263, 263, 263))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jfinal, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel12)
                                    .addComponent(jLabel14))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jtxt_cuota_pendiente)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel9)
                                        .addGap(10, 10, 10)
                                        .addComponent(jtxt_maxCuotas))
                                    .addComponent(jlbl_fechaPendiente, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(263, 263, 263))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jlbl_vigencia)
                        .addComponent(jLabel6)
                        .addComponent(jtxt_numPoliza))
                    .addComponent(jbtn_company, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jinicio, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jfinal, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jlbl_fechaPendiente))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtxt_cuota_pendiente)
                    .addComponent(jLabel9)
                    .addComponent(jtxt_maxCuotas)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addComponent(jtxt_fechaPrimaPendiente))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Contacto", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Eurostile LT Std", 1, 14))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Eurostile LT Std", 0, 14)); // NOI18N
        jLabel1.setText("Nombre:");

        jtxt_nombre.setFont(new java.awt.Font("Vectora LT Std Light", 0, 11)); // NOI18N
        jtxt_nombre.setText("Benjamin Casanova");

        jLabel11.setFont(new java.awt.Font("Eurostile LT Std", 0, 14)); // NOI18N
        jLabel11.setText("Rut :");

        jtxt_rut.setEditable(false);

        jLabel4.setFont(new java.awt.Font("Eurostile LT Std", 0, 14)); // NOI18N
        jLabel4.setText("Telefonos :");

        jlbl_direccion.setText("Sin información");

        jLabel19.setFont(new java.awt.Font("Eurostile LT Std", 0, 14)); // NOI18N
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

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "777", "343434232", "344443433" }));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel11)
                    .addComponent(jLabel4)
                    .addComponent(jLabel19))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jtxt_nombre)
                        .addGap(18, 36, Short.MAX_VALUE)
                        .addComponent(jtbn_edit, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jlbl_direccion)
                            .addComponent(jtxt_rut, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jlbl_telefonos))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addComponent(jSeparator1)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(125, 125, 125))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(79, 79, 79)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(jtxt_nombre))
                    .addComponent(jtbn_edit, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jtxt_rut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Enviar Ruta a Google Maps", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Eurostile LT Std", 1, 14))); // NOI18N

        jButton1.setIcon(new javax.swing.ImageIcon("C:\\madremia\\PRogramaLiberty\\descarga\\map_icon.png")); // NOI18N
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
                .addGap(59, 59, 59)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(65, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel21.setText("Fecha de Hoy:");

        jlbl_red.setFont(new java.awt.Font("Vectora LT Std Light", 1, 24)); // NOI18N
        jlbl_red.setForeground(new java.awt.Color(255, 0, 0));
        jlbl_red.setText("Sin Alertas");

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 0, 0));
        jLabel13.setText("!");

        jXButton1.setForeground(new java.awt.Color(0, 0, 102));
        jXButton1.setText("Cerrar Poliza");
        jXButton1.setFont(new java.awt.Font("Vectora LT Std Light", 0, 24)); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jXButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jXButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 377, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel21)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jlbl_fechaHoy)
                        .addGap(83, 83, 83)
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jlbl_red)
                        .addGap(84, 84, 84)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(53, Short.MAX_VALUE))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jpanel_generarConMaxCuota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jlbl_fechaHoy)
                            .addComponent(jLabel21)
                            .addComponent(jlbl_red)
                            .addComponent(jLabel13))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jpanel_generarConMaxCuota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jtbn_editActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtbn_editActionPerformed
//editar conacto
 
new agregarContacto().setVisible(true);
     this.dispose();
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

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
       
        try {
            javax.swing.UIManager.setLookAndFeel(new SyntheticaBlackStarLookAndFeel());
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
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private org.jdesktop.swingx.JXButton jXButton1;
    private javax.swing.JButton jbtn_agregarMaxCuotas;
    private javax.swing.JButton jbtn_company;
    private javax.swing.JLabel jfinal;
    private javax.swing.JLabel jinicio;
    private javax.swing.JLabel jlbl_direccion;
    private javax.swing.JLabel jlbl_fechaHoy;
    private javax.swing.JLabel jlbl_fechaPendiente;
    private javax.swing.JLabel jlbl_maxCuotas;
    private javax.swing.JLabel jlbl_primaTotal;
    private javax.swing.JLabel jlbl_red;
    private javax.swing.JLabel jlbl_telefonos;
    private javax.swing.JLabel jlbl_vigencia;
    private javax.swing.JPanel jpanel_generarConMaxCuota;
    private javax.swing.JButton jtbn_edit;
    private javax.swing.JComboBox<String> jtcombo_maxCuota;
    private javax.swing.JLabel jtxt_cuota_pendiente;
    private javax.swing.JLabel jtxt_fechaPrimaPendiente;
    private javax.swing.JLabel jtxt_maxCuotas;
    private javax.swing.JLabel jtxt_nombre;
    private javax.swing.JLabel jtxt_numPoliza;
    private javax.swing.JTextField jtxt_primaTotal;
    private javax.swing.JTextField jtxt_rut;
    private org.jdesktop.swingx.JXTable tabla;
    // End of variables declaration//GEN-END:variables
}

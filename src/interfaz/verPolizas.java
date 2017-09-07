/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaz;






import de.javasoft.plaf.synthetica.SyntheticaClassyLookAndFeel;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.sql.SQLWarning;
import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
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
    int preventInicio=0;
    long numPoliza=misCobranzas.numPoliza;
    public Object itemDeseleccionado;
    /**
     * Creates new form NewJFrame
     */
    public verPolizas() {
        //condiciones para mensajes--------------------------
   
        
        initComponents();
        this.tabla.setModel(x.buscarTablaPoliza((DefaultTableModel) this.tabla.getModel(), numPoliza));
      
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
                new misCobranzas().setVisible(true);
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
if(!(listadoIngresados.isEmpty())){////si  esta al dia no se eliminan defaults
    this.jlbl_fechaPendiente.setText("Ninguna");//
    this.jtxt_cuota_pendiente.setText("Al Dia");
    this.jtxt_posesivo.setText("");
    this.jtxt_cuota_pendiente.setForeground(Color.GREEN);
     this.jtxt_maxCuotas.setText("");
}else{
    if(!(listadoCobrados.isEmpty())){
       if(!(listadoPendientes.isEmpty())){
         this.jlbl_fechaPendiente.setText(listadoPendientes.get(0)[0]);//se pone la primera cuota pendiente

        this.jtxt_cuota_pendiente.setText(listadoPendientes.get(0)[1]);//se pone la primera cuota pendiente
       }
    ;
    }else{

    if(!(listadoPendientes.isEmpty())){
       this.jlbl_fechaPendiente.setText(listadoPendientes.get(0)[0]);//se pone la primera cuota pendiente

        this.jtxt_cuota_pendiente.setText(listadoPendientes.get(0)[1]);//se pone la primera cuota pendiente

    }else{      
 
    }
    }
 
}    
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

String fechaObtenida=String.valueOf(this.jlbl_fechaPendiente.getText());

if(fechaObtenida.equals("Ninguna")){
}else{
   DateTime fa = dtf.parseDateTime(fechaObtenida);
DateTime hoy = DateTime.now(); 
if(!fa.isBeforeNow()){

 jlbl_vigencia.setText("Posible Anulacion");
 jlbl_vigencia.setForeground(Color.ORANGE);
 jlbl_red.setText("La cuota PENDIENTE tiene un atraso de"+hoy.dayOfYear().getDifference(fa)*-1+" Dias.");
}else{
    this.jlbl_red.setText("La poliza vencio hace "+hoy.dayOfYear().getDifference(fa)+" dias.");
}


}




       

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
        jButton2 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jLabel21 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jtxt_total_cobrados = new javax.swing.JLabel();
        jtxt_totalIngresadas = new javax.swing.JLabel();
        jtxt_totalPendientes = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jXButton1 = new org.jdesktop.swingx.JXButton();
        jXButton2 = new org.jdesktop.swingx.JXButton();
        jXButton3 = new org.jdesktop.swingx.JXButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jarea_observaciones = new org.jdesktop.swingx.JXTextArea();
        jLabel3 = new javax.swing.JLabel();
        jbtn_editarObservaciones = new org.jdesktop.swingx.JXButton();
        jlbl_red = new javax.swing.JLabel();
        jlbl_amarillo = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jlbl_valor_prima = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Ver Polizas");
        setBounds(new java.awt.Rectangle(0, 0, 0, 0));

        tabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id", "Poliza", "Item", "TipoDoc", "cuota", "Max Cuota", "monto", "moneda", "Fecha de Vencimiento", "Estado", "Observaciones"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, false, true, false, false, true, true, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabla.setFont(new java.awt.Font("Inconsolata", 0, 14)); // NOI18N
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
            tabla.getColumnModel().getColumn(3).setMinWidth(80);
            tabla.getColumnModel().getColumn(3).setPreferredWidth(80);
            tabla.getColumnModel().getColumn(3).setMaxWidth(80);
            tabla.getColumnModel().getColumn(4).setMinWidth(50);
            tabla.getColumnModel().getColumn(4).setPreferredWidth(50);
            tabla.getColumnModel().getColumn(4).setMaxWidth(50);
            tabla.getColumnModel().getColumn(5).setMinWidth(50);
            tabla.getColumnModel().getColumn(5).setPreferredWidth(50);
            tabla.getColumnModel().getColumn(5).setMaxWidth(50);
            tabla.getColumnModel().getColumn(6).setMinWidth(100);
            tabla.getColumnModel().getColumn(6).setPreferredWidth(100);
            tabla.getColumnModel().getColumn(6).setMaxWidth(100);
            tabla.getColumnModel().getColumn(7).setMinWidth(50);
            tabla.getColumnModel().getColumn(7).setPreferredWidth(50);
            tabla.getColumnModel().getColumn(7).setMaxWidth(50);
            tabla.getColumnModel().getColumn(8).setMinWidth(90);
            tabla.getColumnModel().getColumn(8).setPreferredWidth(90);
            tabla.getColumnModel().getColumn(8).setMaxWidth(90);
            tabla.getColumnModel().getColumn(9).setMinWidth(100);
            tabla.getColumnModel().getColumn(9).setPreferredWidth(100);
            tabla.getColumnModel().getColumn(9).setMaxWidth(100);
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
        jLabel7.setForeground(new java.awt.Color(0, 204, 204));
        jLabel7.setText("Fecha Inicio:");

        jLabel10.setFont(new java.awt.Font("Eurostile LT Std", 1, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 204, 204));
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
        jLabel12.setForeground(new java.awt.Color(0, 204, 204));
        jLabel12.setText("Fecha Cuota Pendiente :");

        jlbl_fechaPendiente.setFont(new java.awt.Font("Vectora LT Std Light", 1, 18)); // NOI18N
        jlbl_fechaPendiente.setForeground(new java.awt.Color(204, 0, 0));
        jlbl_fechaPendiente.setText("24/10/2017");

        jtxt_cuota_pendiente.setFont(new java.awt.Font("Vectora LT Std Light", 0, 24)); // NOI18N
        jtxt_cuota_pendiente.setText("4");

        jtxt_posesivo.setFont(new java.awt.Font("Vectora LT Std Light", 0, 24)); // NOI18N
        jtxt_posesivo.setText("de");

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
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12)
                            .addComponent(jLabel14))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel7)
                                .addGap(133, 133, 133))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jlbl_vigencia)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtxt_numPoliza, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(25, 25, 25)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(jtxt_cuota_pendiente)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jtxt_posesivo)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jtxt_maxCuotas))
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jfinal, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jinicio, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jlbl_fechaPendiente, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(159, 159, 159)
                        .addComponent(jbtn_company)
                        .addGap(36, 36, 36))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jlbl_vigencia)
                        .addComponent(jtxt_numPoliza)
                        .addComponent(jLabel6))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jtxt_fechaPrimaPendiente))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Contacto", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Eurostile LT Std", 1, 14))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Eurostile LT Std", 0, 14)); // NOI18N
        jLabel1.setText("Nombre:");

        jtxt_nombre.setFont(new java.awt.Font("Inconsolata", 1, 18)); // NOI18N
        jtxt_nombre.setText("Benjamin Casanova");

        jLabel11.setFont(new java.awt.Font("Eurostile LT Std", 0, 14)); // NOI18N
        jLabel11.setText("Rut :");

        jtxt_rut.setEditable(false);
        jtxt_rut.setFont(new java.awt.Font("Avenir LT Std 35 Light", 1, 18)); // NOI18N

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

        jpolizas.setFont(new java.awt.Font("Eurostile LT Std", 1, 18)); // NOI18N
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

        jButton2.setIcon(new javax.swing.ImageIcon("C:\\madremia\\PRogramaLiberty\\descarga\\add-file-small.png")); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel11)
                            .addComponent(jLabel4)
                            .addComponent(jLabel19))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jlbl_direccion)
                            .addComponent(jlbl_telefonos)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jtxt_rut, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jtxt_nombre, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jtbn_edit, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(0, 78, Short.MAX_VALUE)
                        .addComponent(jpolizas, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(50, 50, 50))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(145, 145, 145)
                        .addComponent(jLabel2)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
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
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jpolizas))
                .addGap(12, 12, 12))
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
                .addContainerGap(70, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1))
        );

        jLabel21.setFont(new java.awt.Font("Eurostile LT Std", 0, 12)); // NOI18N
        jLabel21.setText("Fecha de Hoy:");

        jtxt_total_cobrados.setFont(new java.awt.Font("Eurostile LT Std", 1, 18)); // NOI18N
        jtxt_total_cobrados.setForeground(new java.awt.Color(255, 102, 0));
        jtxt_total_cobrados.setText("0");

        jtxt_totalIngresadas.setFont(new java.awt.Font("Eurostile LT Std", 1, 18)); // NOI18N
        jtxt_totalIngresadas.setForeground(new java.awt.Color(0, 153, 0));
        jtxt_totalIngresadas.setText("0");

        jtxt_totalPendientes.setFont(new java.awt.Font("Eurostile LT Std", 1, 18)); // NOI18N
        jtxt_totalPendientes.setForeground(new java.awt.Color(255, 0, 0));
        jtxt_totalPendientes.setText("0");

        jLabel9.setFont(new java.awt.Font("Eurostile LT Std", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 153, 0));
        jLabel9.setText("Ingresadas");

        jLabel15.setFont(new java.awt.Font("Eurostile LT Std", 1, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 0, 0));
        jLabel15.setText("Pendientes");

        jLabel16.setFont(new java.awt.Font("Eurostile LT Std", 1, 18)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 102, 0));
        jLabel16.setText("Cobradas");

        jXButton1.setForeground(new java.awt.Color(0, 0, 102));
        jXButton1.setText("Dejar todas Pendientes");
        jXButton1.setFont(new java.awt.Font("Vectora LT Std Light", 0, 14)); // NOI18N
        jXButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jXButton1ActionPerformed(evt);
            }
        });

        jXButton2.setForeground(new java.awt.Color(0, 0, 102));
        jXButton2.setText("Dejar todas Cobradas");
        jXButton2.setFont(new java.awt.Font("Vectora LT Std Light", 0, 14)); // NOI18N
        jXButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jXButton2ActionPerformed(evt);
            }
        });

        jXButton3.setForeground(new java.awt.Color(0, 0, 102));
        jXButton3.setText("Dejar todas Ingresadas");
        jXButton3.setFont(new java.awt.Font("Vectora LT Std Light", 0, 14)); // NOI18N
        jXButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jXButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel9)
                        .addComponent(jLabel15))
                    .addComponent(jLabel16))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jtxt_totalPendientes, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jtxt_totalIngresadas, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jtxt_total_cobrados, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jXButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jXButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jXButton3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtxt_total_cobrados)
                    .addComponent(jLabel16))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addComponent(jtxt_totalPendientes)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jtxt_totalIngresadas)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel9)
                        .addContainerGap())))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jXButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jXButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jXButton3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

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

        jlbl_red.setFont(new java.awt.Font("Eurostile LT Std", 1, 14)); // NOI18N
        jlbl_red.setForeground(new java.awt.Color(255, 0, 0));
        jlbl_red.setText("Antecion!");
        jlbl_red.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jlbl_red.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);

        jlbl_amarillo.setFont(new java.awt.Font("Eurostile LT Std", 1, 14)); // NOI18N
        jlbl_amarillo.setForeground(new java.awt.Color(255, 102, 0));
        jlbl_amarillo.setText("Antecion!");
        jlbl_amarillo.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        jLabel5.setFont(new java.awt.Font("Eurostile LT Std", 0, 12)); // NOI18N
        jLabel5.setText("Valor Prima :");

        jlbl_valor_prima.setFont(new java.awt.Font("Vectora LT Std Light", 0, 18)); // NOI18N
        jlbl_valor_prima.setText("No existen cuotas!");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 413, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jlbl_red, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jlbl_amarillo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addComponent(jLabel21)
                                .addGap(24, 24, 24)
                                .addComponent(jlbl_fechaHoy)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel5)
                                .addGap(18, 18, 18)
                                .addComponent(jlbl_valor_prima))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jpanel_generarConMaxCuota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 93, Short.MAX_VALUE)
                                .addComponent(jbtn_editarObservaciones, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jlbl_red)
                        .addGap(18, 18, 18)
                        .addComponent(jlbl_amarillo)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jlbl_fechaHoy)
                            .addComponent(jLabel21)
                            .addComponent(jLabel5)
                            .addComponent(jlbl_valor_prima))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jpanel_generarConMaxCuota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(35, 35, 35))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jbtn_editarObservaciones, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3))
                                .addGap(21, 21, 21))))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
          
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

    private void jXButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jXButton1ActionPerformed
JOptionPane.showConfirmDialog(jbtn_company, "Esta seguro que desea dejar esta poliza como pendiente?");        // TODO add your handling code here:
    }//GEN-LAST:event_jXButton1ActionPerformed

    private void jXButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jXButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jXButton2ActionPerformed

    private void jXButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jXButton3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jXButton3ActionPerformed

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
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
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
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private org.jdesktop.swingx.JXButton jXButton1;
    private org.jdesktop.swingx.JXButton jXButton2;
    private org.jdesktop.swingx.JXButton jXButton3;
    private org.jdesktop.swingx.JXTextArea jarea_observaciones;
    private javax.swing.JButton jbtn_agregarMaxCuotas;
    private javax.swing.JButton jbtn_company;
    private org.jdesktop.swingx.JXButton jbtn_editarObservaciones;
    private javax.swing.JLabel jfinal;
    private javax.swing.JLabel jinicio;
    private javax.swing.JLabel jlbl_amarillo;
    private javax.swing.JLabel jlbl_direccion;
    private javax.swing.JLabel jlbl_fechaHoy;
    private javax.swing.JLabel jlbl_fechaPendiente;
    private javax.swing.JLabel jlbl_maxCuotas;
    private javax.swing.JLabel jlbl_primaTotal;
    private javax.swing.JLabel jlbl_red;
    private javax.swing.JLabel jlbl_telefonos;
    private javax.swing.JLabel jlbl_valor_prima;
    private javax.swing.JLabel jlbl_vigencia;
    private javax.swing.JPanel jpanel_generarConMaxCuota;
    private javax.swing.JComboBox<String> jpolizas;
    private javax.swing.JButton jtbn_edit;
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

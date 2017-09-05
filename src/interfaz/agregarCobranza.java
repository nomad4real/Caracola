/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaz;


import conexion.conexion;
import java.awt.Color;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.List;
import javax.swing.AbstractButton;
import javax.swing.border.TitledBorder;
import metodos.consultor;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;



/**
 *
 * @author Nomad
 */
public class agregarCobranza extends javax.swing.JFrame {
public static consultor x= new consultor();
public static String ramo="";
public static String tipoDoc="";
public static long numPoliza=misCobranzas.numPoliza;
public static int company=0;
public static int estado=0;
public static int cuotaInicial=1;
public static int cuotaMax=0;
public static Date fecha_vencimiento= null;
public static String moneda="";
public static double monto=0.0;
public static int  item=0;
public static long  poliza=misCobranzas.numPoliza;
public static int  tipoDocInt=0;
public static String  companyName="";
public static int rutSeleccionado=0;
public static String nombreNuevoContacto="";
public static double valorCuota=monto/cuotaMax;//monto igual prima 
public static double modCuotas=monto%valorCuota;
public static String rutCompleto="";
public static  String[] parts = rutCompleto.split("-");
public static  int rut=0;
public static double primaTotal=0;
 UtilDateModel model = new UtilDateModel();
JDatePanelImpl datePanel = new JDatePanelImpl(model);
JDatePickerImpl datePicker = new JDatePickerImpl(datePanel);
    /**
     * Creates new form agregarCobranza
     */
    public agregarCobranza()  {
        initComponents();
        
   //     JDatePicker l = (JDatePicker) new JXDatePicker();
        //   this.add(l.);
        // se insertan vathislores a radio group item para rescatarlos
        this.jradio_emision.setActionCommand(jradio_emision.getText());
        this.jradio_modificacion.setActionCommand(jradio_modificacion.getText());
        this.jradio_rehabilitacion.setActionCommand(jradio_rehabilitacion.getText());
//nombre Contacto
//       this.jcombo_contactos.setSelectedItem(x.buscarAseguradoPorId(misCobranzas.idCobranza)[2].toString());
//valores para company
this.jradio_emision.setActionCommand(jradio_emision.getText());
this.jradiobtn_hdi.setActionCommand(jradiobtn_hdi.getText());
this.jradiobtn_liberty.setActionCommand(jradiobtn_liberty.getText());
this.jradiobtn_sura.setActionCommand(jradiobtn_sura.getText());
TitledBorder titledBorder = (TitledBorder) this.jpane_ramo.getBorder();
titledBorder.setTitleColor(Color.RED);


DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//jXDatePicker1.setFormats(dateFormat);
consultor x= new consultor();
List<String> fullArray = new ArrayList<>();
try{
    conexion.conectar();
    String strSql="SELECT * from contactos";
    conexion.sentencia=conexion.conn.prepareStatement(strSql);
    ResultSet objSet=conexion.sentencia.executeQuery(strSql);
    while(objSet.next()){
        String rutCompleto="";
        for (int i = 0; i < 3; i++) {
            if(i==2){

                fullArray.add(objSet.getObject(i+1).toString());
            }
            
        }
     
    }
}catch(SQLException ex){
    System.out.println("Error Mostrar combobox contactos"+ex);
}   

if(misCobranzas.idCobranza!=0){
//se llenan los campos que vienen
Object [] fila=x.buscarAseguradoPorId(misCobranzas.idCobranza);

this.jtxt_poliza.setText(fila[7].toString());
this.jtxt_rut.setText(fila[3].toString());
this.jtxt_item.setText(fila[8].toString());
this.jtxt_primaTotal.setText(String.valueOf(verPolizas.primaTotal));

switch(Integer.parseInt(fila[9].toString())){
    case 0:this.jradio_emision.setSelected(true);
    case 1:this.jradio_modificacion.setSelected(true);
    case 2: this.jradio_rehabilitacion.setSelected(true);
};
switch(Integer.parseInt(fila[15].toString())){
    case 0:this.jradiobtn_hdi.setSelected(true);
    case 1:this.jradiobtn_liberty.setSelected(true);
    case 2: this.jradiobtn_sura.setSelected(true);
};
this.jcombo_maxCuotas.setSelectedIndex(Integer.parseInt(fila[11].toString()));
            


}

            this.setLocationRelativeTo(null);
  this.setLayout(null);
//fin inicio clase


FilterComboBox j = new FilterComboBox((fullArray));
j.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
         consultor r = new consultor();
jtxt_rut.setText(r.buscarRutPorNombre(e.getItem().toString()));
 String rutCompleto=jtxt_rut.getText();
 String[] parts = rutCompleto.split("-");
rutSeleccionado = Integer.parseInt(parts[0]); // 004
            }
        });
this.getContentPane().add(j);
j.setVisible(true);
j.setBounds(0, 10, 200, 25);



datePicker.setBounds(10,100,200,40);

jpanel_cobranzas.add(datePicker);
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
      
    }
    
    
    
    
  
 
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jtbngroup_company = new javax.swing.ButtonGroup();
        jtbngroup_tipoDoc = new javax.swing.ButtonGroup();
        jpane_poliza = new javax.swing.JPanel();
        jtxt_poliza = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jradiobtn_hdi = new javax.swing.JRadioButton();
        jradiobtn_liberty = new javax.swing.JRadioButton();
        jradiobtn_sura = new javax.swing.JRadioButton();
        jpanel_cobranzas = new javax.swing.JPanel();
        jtxt_primaTotal = new javax.swing.JTextField();
        jcombo_moneda = new javax.swing.JComboBox<>();
        jcombo_maxCuotas = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jbtn_add = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jpane_poliza2 = new javax.swing.JPanel();
        jradio_emision = new javax.swing.JRadioButton();
        jradio_modificacion = new javax.swing.JRadioButton();
        jradio_rehabilitacion = new javax.swing.JRadioButton();
        jpane_ramo = new javax.swing.JPanel();
        jcombo_ramo = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jpane_poliza5 = new javax.swing.JPanel();
        jtxt_item = new javax.swing.JTextField();
        jbtn_agregar_contacto = new javax.swing.JButton();
        jtxt_rut = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtxtArea_observacion_poliza = new org.jdesktop.swingx.JXTextArea();
        jSeparator1 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jpane_poliza.setBorder(javax.swing.BorderFactory.createTitledBorder("N° de Poliza"));
        jpane_poliza.setForeground(new java.awt.Color(102, 255, 102));

        jtxt_poliza.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxt_polizaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpane_polizaLayout = new javax.swing.GroupLayout(jpane_poliza);
        jpane_poliza.setLayout(jpane_polizaLayout);
        jpane_polizaLayout.setHorizontalGroup(
            jpane_polizaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpane_polizaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jtxt_poliza, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
                .addContainerGap())
        );
        jpane_polizaLayout.setVerticalGroup(
            jpane_polizaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpane_polizaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jtxt_poliza, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Compañia"));

        jtbngroup_company.add(jradiobtn_hdi);
        jradiobtn_hdi.setSelected(true);
        jradiobtn_hdi.setText("HDI");

        jtbngroup_company.add(jradiobtn_liberty);
        jradiobtn_liberty.setText("Liberty");

        jtbngroup_company.add(jradiobtn_sura);
        jradiobtn_sura.setText("SURA");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jradiobtn_hdi)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jradiobtn_liberty)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jradiobtn_sura)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jradiobtn_sura)
                    .addComponent(jradiobtn_liberty)
                    .addComponent(jradiobtn_hdi))
                .addContainerGap())
        );

        jpanel_cobranzas.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Cobranza", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Vectora LT Std Light", 0, 12))); // NOI18N

        jtxt_primaTotal.setText("0");
        jtxt_primaTotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxt_primaTotalActionPerformed(evt);
            }
        });

        jcombo_moneda.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "UF", "US" }));

        jcombo_maxCuotas.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" }));
        jcombo_maxCuotas.setSelectedIndex(11);
        jcombo_maxCuotas.setToolTipText("Si el valor es 0 sera tomado como desconocido");
        jcombo_maxCuotas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcombo_maxCuotasActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Vectora LT Std Light", 0, 11)); // NOI18N
        jLabel1.setText("Total Prima:");

        jLabel4.setFont(new java.awt.Font("Vectora LT Std Light", 0, 11)); // NOI18N
        jLabel4.setText("Total Cuotas:");

        jLabel2.setFont(new java.awt.Font("Vectora LT Std Light", 0, 11)); // NOI18N
        jLabel2.setText("Fecha Inicio Poliza:");

        javax.swing.GroupLayout jpanel_cobranzasLayout = new javax.swing.GroupLayout(jpanel_cobranzas);
        jpanel_cobranzas.setLayout(jpanel_cobranzasLayout);
        jpanel_cobranzasLayout.setHorizontalGroup(
            jpanel_cobranzasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpanel_cobranzasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpanel_cobranzasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpanel_cobranzasLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jpanel_cobranzasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel4))
                        .addGap(31, 31, 31)
                        .addGroup(jpanel_cobranzasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jcombo_maxCuotas, 0, 49, Short.MAX_VALUE)
                            .addComponent(jtxt_primaTotal))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jcombo_moneda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jpanel_cobranzasLayout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jpanel_cobranzasLayout.setVerticalGroup(
            jpanel_cobranzasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpanel_cobranzasLayout.createSequentialGroup()
                .addGroup(jpanel_cobranzasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jcombo_moneda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jtxt_primaTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpanel_cobranzasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jcombo_maxCuotas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addContainerGap(66, Short.MAX_VALUE))
        );

        jbtn_add.setText("Añadir");
        jbtn_add.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jbtn_addMousePressed(evt);
            }
        });
        jbtn_add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtn_addActionPerformed(evt);
            }
        });

        jButton2.setText("Cancelar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jpane_poliza2.setBorder(javax.swing.BorderFactory.createTitledBorder("Seleccione el tipo de Documento :"));

        jtbngroup_tipoDoc.add(jradio_emision);
        jradio_emision.setSelected(true);
        jradio_emision.setText("EMISIÓN");
        jradio_emision.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jradio_emisionActionPerformed(evt);
            }
        });

        jtbngroup_tipoDoc.add(jradio_modificacion);
        jradio_modificacion.setText("MODIFICACIÓN");
        jradio_modificacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jradio_modificacionActionPerformed(evt);
            }
        });

        jtbngroup_tipoDoc.add(jradio_rehabilitacion);
        jradio_rehabilitacion.setText("REHABILITACIÓN");
        jradio_rehabilitacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jradio_rehabilitacionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpane_poliza2Layout = new javax.swing.GroupLayout(jpane_poliza2);
        jpane_poliza2.setLayout(jpane_poliza2Layout);
        jpane_poliza2Layout.setHorizontalGroup(
            jpane_poliza2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpane_poliza2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpane_poliza2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jradio_modificacion)
                    .addComponent(jradio_emision)
                    .addComponent(jradio_rehabilitacion))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jpane_poliza2Layout.setVerticalGroup(
            jpane_poliza2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpane_poliza2Layout.createSequentialGroup()
                .addComponent(jradio_emision)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jradio_modificacion)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 6, Short.MAX_VALUE)
                .addComponent(jradio_rehabilitacion)
                .addContainerGap())
        );

        jpane_ramo.setBorder(javax.swing.BorderFactory.createTitledBorder("Ramo :"));

        jcombo_ramo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione un Ramo...", "HOGAR INTERNET", "RESPONSABILIDAD CIVIL", "VEHICULOS MOTORIZADOS HDI", "VM MIGRACIÓN", "HDI HOGAR", "INCENDIO" }));
        jcombo_ramo.setToolTipText("");
        jcombo_ramo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jcombo_ramoItemStateChanged(evt);
            }
        });
        jcombo_ramo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcombo_ramoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpane_ramoLayout = new javax.swing.GroupLayout(jpane_ramo);
        jpane_ramo.setLayout(jpane_ramoLayout);
        jpane_ramoLayout.setHorizontalGroup(
            jpane_ramoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpane_ramoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jcombo_ramo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jpane_ramoLayout.setVerticalGroup(
            jpane_ramoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpane_ramoLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jcombo_ramo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLabel3.setFont(new java.awt.Font("Eurostile LT Std", 0, 14)); // NOI18N
        jLabel3.setText("Rut:");

        jpane_poliza5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "ITEM", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Microsoft YaHei UI", 1, 11))); // NOI18N

        jtxt_item.setText("0");

        javax.swing.GroupLayout jpane_poliza5Layout = new javax.swing.GroupLayout(jpane_poliza5);
        jpane_poliza5.setLayout(jpane_poliza5Layout);
        jpane_poliza5Layout.setHorizontalGroup(
            jpane_poliza5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpane_poliza5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jtxt_item, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jpane_poliza5Layout.setVerticalGroup(
            jpane_poliza5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpane_poliza5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jtxt_item, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jbtn_agregar_contacto.setIcon(new javax.swing.ImageIcon("C:\\madremia\\PRogramaLiberty\\descarga\\plus.png")); // NOI18N
        jbtn_agregar_contacto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtn_agregar_contactoActionPerformed(evt);
            }
        });

        jtxt_rut.setFont(new java.awt.Font("American Typewriter Std Lt", 0, 18)); // NOI18N

        jtxtArea_observacion_poliza.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Observaciones", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Eurostile LT Std", 2, 12))); // NOI18N
        jtxtArea_observacion_poliza.setColumns(20);
        jtxtArea_observacion_poliza.setForeground(new java.awt.Color(0, 0, 153));
        jtxtArea_observacion_poliza.setLineWrap(true);
        jtxtArea_observacion_poliza.setRows(5);
        jtxtArea_observacion_poliza.setFont(new java.awt.Font("Vectora LT Std Light", 0, 12)); // NOI18N
        jScrollPane1.setViewportView(jtxtArea_observacion_poliza);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(136, 136, 136)
                .addComponent(jbtn_add)
                .addGap(107, 107, 107)
                .addComponent(jButton2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 454, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jpane_ramo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jpane_poliza, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(jpane_poliza5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(jpane_poliza2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jpanel_cobranzas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jbtn_agregar_contacto, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jtxt_rut, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(46, 46, 46)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jbtn_agregar_contacto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jtxt_rut, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jpane_ramo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jpane_poliza5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jpane_poliza, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jpane_poliza2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jpanel_cobranzas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jbtn_add))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void jbtn_addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtn_addActionPerformed
//se toman los datos a añadir
consultor r = new consultor();
ramo=this.jcombo_ramo.getSelectedItem().toString();
tipoDoc=this.jtbngroup_tipoDoc.getSelection().getActionCommand();
tipoDocInt=r.consultarTipoDoc(tipoDoc);//busco el id por nombre del tipo Documento
poliza=Integer.parseInt(this.jtxt_poliza.getText());
item=Integer.parseInt(this.jtxt_item.getText());
companyName=this.jtbngroup_company.getSelection().getActionCommand();
company=r.consultarCompany(companyName);//busco el id por nombre de la compañia
fecha_vencimiento=(Date)datePicker.getModel().getValue();
moneda=this.jcombo_moneda.getSelectedItem().toString();
cuotaMax=Integer.parseInt(this.jcombo_maxCuotas.getSelectedItem().toString());
monto=Double.parseDouble(this.jtxt_primaTotal.getText());
modCuotas=monto%cuotaMax;
primaTotal=monto;
int estado=0;

//prima total se divide en las cuotas y se deja lo que sobra para el final
valorCuota= r.calcularCuotas(monto,cuotaMax);

double valorUltimaCuota=x.calcularUltimaCuota(monto,cuotaMax);
NumberFormat nf= NumberFormat.getInstance();
BigDecimal modBig = new BigDecimal(monto).remainder(new BigDecimal(cuotaMax));
nf.format(modBig);
modCuotas=Double.parseDouble(modBig.toString().replace(",", "."));

if(modCuotas==0.0){//si mod cuotas es igual a zero entonces todas las cuotas son iguales
    r.insertarObservacionPoliza(poliza,this.jtxtArea_observacion_poliza.getText());
    r.insertarCobranza(ramo, tipoDocInt, poliza, item, company, fecha_vencimiento, moneda,cuotaMax, valorCuota,rut,primaTotal);
}else{
    //Se toma lo que sobra de la division
    double ultimaCuota=valorUltimaCuota;
    for(int i=1;i<=cuotaMax;i++){
        
        if(i==cuotaMax){
            //la ultima fila tiene la diferencia
            //aca se pone la diferencia si hay mas decimales que causen diferencia
            valorCuota=ultimaCuota;
        }
        
        r.insertarObservacionPoliza(poliza,this.jtxtArea_observacion_poliza.getText());
        r.insertarCobranza( ramo, tipoDocInt, poliza, item, company, fecha_vencimiento, moneda, cuotaMax, valorCuota,rut,primaTotal);
        cuotaInicial++;
    }
    
    
}


new misCobranzas().setVisible(true);
this.dispose();


    }//GEN-LAST:event_jbtn_addActionPerformed

    
    
    private void jbtn_addMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbtn_addMousePressed
        // TODO add your handling code here:
        for (Enumeration<AbstractButton> buttons = this.jtbngroup_company.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();

            if (button.isSelected()) {
                System.out.println(button.getText()); 
            }
        }
        
        consultor r = new consultor();
        
        
    }//GEN-LAST:event_jbtn_addMousePressed

    private void jradio_emisionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jradio_emisionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jradio_emisionActionPerformed

    private void jradio_modificacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jradio_modificacionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jradio_modificacionActionPerformed

    private void jcombo_ramoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcombo_ramoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jcombo_ramoActionPerformed

    private void jcombo_maxCuotasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcombo_maxCuotasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jcombo_maxCuotasActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
new misCobranzas().setVisible(true);
        this.dispose();        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jradio_rehabilitacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jradio_rehabilitacionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jradio_rehabilitacionActionPerformed

    private void jtxt_polizaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxt_polizaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxt_polizaActionPerformed

    private void jcombo_ramoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jcombo_ramoItemStateChanged
      if(!(evt.getItem().equals("Seleccione un Ramo..."))){
     TitledBorder titledBorder = (TitledBorder) this.jpane_ramo.getBorder();
    titledBorder.setTitleColor(Color.GREEN); 
      }else{
          
      }
    }//GEN-LAST:event_jcombo_ramoItemStateChanged

    private void jbtn_agregar_contactoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtn_agregar_contactoActionPerformed
             agregarContacto.contacto_creado=1;
        new agregarContacto().setVisible(true);    
this.dispose();
//this.dispose();// TODO add your handling code here:
    }//GEN-LAST:event_jbtn_agregar_contactoActionPerformed

    private void jtxt_primaTotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxt_primaTotalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxt_primaTotalActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(agregarCobranza.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(agregarCobranza.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(agregarCobranza.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(agregarCobranza.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new agregarCobranza().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JButton jbtn_add;
    private javax.swing.JButton jbtn_agregar_contacto;
    private javax.swing.JComboBox<String> jcombo_maxCuotas;
    private javax.swing.JComboBox<String> jcombo_moneda;
    private javax.swing.JComboBox<String> jcombo_ramo;
    private javax.swing.JPanel jpane_poliza;
    private javax.swing.JPanel jpane_poliza2;
    private javax.swing.JPanel jpane_poliza5;
    private javax.swing.JPanel jpane_ramo;
    private javax.swing.JPanel jpanel_cobranzas;
    private javax.swing.JRadioButton jradio_emision;
    private javax.swing.JRadioButton jradio_modificacion;
    private javax.swing.JRadioButton jradio_rehabilitacion;
    private javax.swing.JRadioButton jradiobtn_hdi;
    private javax.swing.JRadioButton jradiobtn_liberty;
    private javax.swing.JRadioButton jradiobtn_sura;
    private javax.swing.ButtonGroup jtbngroup_company;
    private javax.swing.ButtonGroup jtbngroup_tipoDoc;
    private org.jdesktop.swingx.JXTextArea jtxtArea_observacion_poliza;
    private javax.swing.JTextField jtxt_item;
    private javax.swing.JTextField jtxt_poliza;
    private javax.swing.JTextField jtxt_primaTotal;
    private javax.swing.JTextField jtxt_rut;
    // End of variables declaration//GEN-END:variables
}

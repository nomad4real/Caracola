/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaz;


import conexion.conexion;
import java.awt.Color;
import java.awt.Frame;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Properties;
import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import metodos.DateLabelFormatter;
import metodos.consultor;
import org.jdesktop.swingx.JXDialog;


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

    /**
     * Creates new form agregarCobranza
     */
    public agregarCobranza()  {
        initComponents();
        
        
        
        // se insertan valores a radio group item para rescatarlos
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
jXDatePicker1.setFormats(dateFormat);
consultor x= new consultor();

try{
    conexion.conectar();
    String strSql="SELECT * from contactos";
    conexion.sentencia=conexion.conn.prepareStatement(strSql);
    ResultSet objSet=conexion.sentencia.executeQuery(strSql);
    while(objSet.next()){
        String rutCompleto="";
        for (int i = 0; i < 3; i++) {
            if(i==2){
            this.jcombo_contactos.addItem(objSet.getObject(i+1).toString());
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
this.jcombo_contactos.setSelectedItem(fila[2]);
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


    }
    
    public void cambiarNuevoContacto(String nombre ,String rut){
    this.jcombo_contactos.addItem(nombre);
    this.jcombo_contactos.setSelectedItem(this.jcombo_contactos.getItemAt(this.jcombo_contactos.getItemCount()-1));
    this.jtxt_rut.setText(rut);
    
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
        jLabel2 = new javax.swing.JLabel();
        jpane_poliza = new javax.swing.JPanel();
        jtxt_poliza = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jradiobtn_hdi = new javax.swing.JRadioButton();
        jradiobtn_liberty = new javax.swing.JRadioButton();
        jradiobtn_sura = new javax.swing.JRadioButton();
        jPanel3 = new javax.swing.JPanel();
        jtxt_primaTotal = new javax.swing.JTextField();
        jcombo_moneda = new javax.swing.JComboBox<>();
        jPanel4 = new javax.swing.JPanel();
        jcombo_maxCuotas = new javax.swing.JComboBox<>();
        jbtn_add = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jpane_poliza2 = new javax.swing.JPanel();
        jradio_emision = new javax.swing.JRadioButton();
        jradio_modificacion = new javax.swing.JRadioButton();
        jradio_rehabilitacion = new javax.swing.JRadioButton();
        jpane_ramo = new javax.swing.JPanel();
        jcombo_ramo = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jXDatePicker1 = new org.jdesktop.swingx.JXDatePicker();
        jpane_poliza5 = new javax.swing.JPanel();
        jtxt_item = new javax.swing.JTextField();
        jcombo_contactos = new javax.swing.JComboBox<>();
        jButton3 = new javax.swing.JButton();
        jtxt_rut = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel2.setText("Contacto :");

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
                .addGap(12, 12, 12)
                .addComponent(jradiobtn_liberty)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
                .addComponent(jradiobtn_sura)
                .addGap(27, 27, 27))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jradiobtn_hdi)
                    .addComponent(jradiobtn_liberty)
                    .addComponent(jradiobtn_sura))
                .addGap(0, 3, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Monto Total"));

        jtxt_primaTotal.setText("0");

        jcombo_moneda.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "UF", "US" }));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jtxt_primaTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(jcombo_moneda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(36, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jtxt_primaTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jcombo_moneda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Max Cuotas"));

        jcombo_maxCuotas.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" }));
        jcombo_maxCuotas.setSelectedIndex(11);
        jcombo_maxCuotas.setToolTipText("Si el valor es 0 sera tomado como desconocido");
        jcombo_maxCuotas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcombo_maxCuotasActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jcombo_maxCuotas, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jcombo_maxCuotas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jradio_modificacion)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jradio_rehabilitacion))
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

        jLabel3.setText("Rut:");

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Fecha Vencimiento Cuota Inicial"));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jXDatePicker1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(22, 22, 22))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jXDatePicker1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

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

        jcombo_contactos.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jcombo_contactosItemStateChanged(evt);
            }
        });

        jButton3.setIcon(new javax.swing.ImageIcon("C:\\madremia\\PRogramaLiberty\\descarga\\plus.png")); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jpane_poliza2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jcombo_contactos, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(21, 21, 21))
                            .addComponent(jpane_ramo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jpane_poliza, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jpane_poliza5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 42, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jbtn_add)
                        .addGap(84, 84, 84)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(29, 29, 29)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jtxt_rut)))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addGap(89, 89, 89))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(jLabel3)
                        .addComponent(jtxt_rut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton3)
                    .addComponent(jcombo_contactos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jpane_ramo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jpane_poliza5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jpane_poliza, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jpane_poliza2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbtn_add)
                    .addComponent(jButton2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
fecha_vencimiento=jXDatePicker1.getDate();
moneda=this.jcombo_moneda.getSelectedItem().toString();
cuotaMax=Integer.parseInt(this.jcombo_maxCuotas.getSelectedItem().toString());
monto=Double.parseDouble(this.jtxt_primaTotal.getText());
modCuotas=monto%cuotaMax;
primaTotal=monto;
int estado=0;
String rutCompleto=r.buscarRutPorNombre(this.jcombo_contactos.getSelectedItem().toString());
String[] parts = rutCompleto.split("-");
int rut=Integer.parseInt(parts[0]);
//prima total se divide en las cuotas y se deja lo que sobra para el final
valorCuota= r.calcularCuotas(monto,cuotaMax);

double valorUltimaCuota=x.calcularUltimaCuota(monto,cuotaMax);
NumberFormat nf= NumberFormat.getInstance();
BigDecimal modBig = new BigDecimal(monto).remainder(new BigDecimal(cuotaMax));
nf.format(modBig);
modCuotas=Double.parseDouble(modBig.toString().replace(",", "."));

if(modCuotas==0.0){//si mod cuotas es igual a zero entonces todas las cuotas son iguales
    
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

    private void jcombo_contactosItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jcombo_contactosItemStateChanged
 consultor r = new consultor();
this.jtxt_rut.setText(r.buscarRutPorNombre(evt.getItem().toString()));
 String rutCompleto=this.jtxt_rut.getText();
 String[] parts = rutCompleto.split("-");
rutSeleccionado = Integer.parseInt(parts[0]); // 004



        // TODO add your handling code here:
    }//GEN-LAST:event_jcombo_contactosItemStateChanged

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
new agregarContacto().setVisible(true);    
this.dispose();// TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

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
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private org.jdesktop.swingx.JXDatePicker jXDatePicker1;
    private javax.swing.JButton jbtn_add;
    private javax.swing.JComboBox<String> jcombo_contactos;
    private javax.swing.JComboBox<String> jcombo_maxCuotas;
    private javax.swing.JComboBox<String> jcombo_moneda;
    private javax.swing.JComboBox<String> jcombo_ramo;
    private javax.swing.JPanel jpane_poliza;
    private javax.swing.JPanel jpane_poliza2;
    private javax.swing.JPanel jpane_poliza5;
    private javax.swing.JPanel jpane_ramo;
    private javax.swing.JRadioButton jradio_emision;
    private javax.swing.JRadioButton jradio_modificacion;
    private javax.swing.JRadioButton jradio_rehabilitacion;
    private javax.swing.JRadioButton jradiobtn_hdi;
    private javax.swing.JRadioButton jradiobtn_liberty;
    private javax.swing.JRadioButton jradiobtn_sura;
    private javax.swing.ButtonGroup jtbngroup_company;
    private javax.swing.ButtonGroup jtbngroup_tipoDoc;
    private javax.swing.JTextField jtxt_item;
    private javax.swing.JTextField jtxt_poliza;
    private javax.swing.JTextField jtxt_primaTotal;
    private javax.swing.JTextField jtxt_rut;
    // End of variables declaration//GEN-END:variables
}

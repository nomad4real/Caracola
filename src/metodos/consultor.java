/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metodos;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import conexion.conexion;
import java.awt.Button;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import java.util.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.swing.ButtonModel;
import javax.swing.ComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Row;
import org.jdesktop.swingx.JXTable;
import org.joda.time.DateTime;
import org.joda.time.Days;



/**
 *
 * @author Nomad
 */
public class consultor {

public boolean actualizarPagados(){
    
    //se reemplaza campo estado si es no figura en excel
 boolean c=false; 
    try{
     conexion.conectar(); 
      System.out.println("1.-Atualizando Clientes Que ya Pagaron..........................................");
    //String eliminar_no_morosos="DELETE FROM morosos WHERE not (id  in (SELECT  id FROM listadonuevo))";
String actualizar_estado="update cobranzas set estado='PAGO INGRESADO' WHERE not (id  in (SELECT  id FROM listadonuevo))";
    conexion.sentencia=conexion.conn.prepareStatement(actualizar_estado);
    if(conexion.sentencia.execute(actualizar_estado)){
    c=true;
       
    }
   
}catch(Exception ex){
        System.out.println("error:"+ex);
    }
    
    return c;
}



public boolean agregarMorosos(){
 boolean c=false; 
    try{
     conexion.conectar(); 
      System.out.println("2.-Agregando Nuevos Clientes Morosos.....................................");

String agregar_morosos="insert into cobranzas (select * from listadonuevo a Where not exists (select * from cobranzas b where b.id= a.id))";
    conexion.sentencia=conexion.conn.prepareStatement(agregar_morosos);
    if(conexion.sentencia.execute(agregar_morosos)){
    c=true;
       
    }
   
}catch(Exception ex){
        System.out.println("error:"+ex);
    }
    
    return c;

}


 public int getRowCount(ResultSet set) throws SQLException  
{  
   int rowCount;  
   int currentRow = set.getRow();            // Get current row  
   rowCount = set.last() ? set.getRow() : 0; // Determine number of rows  
   if (currentRow == 0)                      // If there was no current row  
      set.beforeFirst();                     // We want next() to go to first row  
   else                                      // If there WAS a current row  
      set.absolute(currentRow);              // Restore it  
   return rowCount;  
}  
 
public void insertarCambiosObservaciones(String valorNuevo, long id){
       try{
            valorNuevo=valorNuevo.trim().replaceAll("\\\\", "");
            String strSql="update cobranzas SET obs='"+valorNuevo+"' where id="+id;
            conexion.conectar();
            conexion.sentencia = conexion.conn.prepareStatement(strSql);
            conexion.sentencia.executeUpdate(strSql);
            if(conexion.sentencia.getUpdateCount()!=0){
                System.out.println("No se updateo nada");
            }else{
                System.out.println("Se updateo");
            }
            conexion.desconectar();
       
            
        } catch (Exception ex) {
            System.out.println("Error al insertar Cambios de Observacion"+ex);
            System.out.println(ex);
        }
}

    public Object[] buscarPoliza(Long id) {
        Object [] fila=new Object [15];
        try{
            String strSql="SELECT * from cobranzas where id="+id;
            conexion.conectar();
            conexion.sentencia=conexion.conn.prepareStatement(strSql);
            ResultSet objSet=conexion.sentencia.executeQuery(strSql);
            
            
            while(objSet.next()){
                for (int i = 0; i < 15; i++) {
                    
                    fila[i]=objSet.getObject(i+1);
                    
                }
            }
            
            conexion.desconectar();
        }catch(SQLException ex){
            System.out.println(ex);
            
        }
        
  return fila;
    }
    

public void extraerExcelHdi_Final(String dirArchivo){
    
    try{
        
        System.out.println("1.-Importanto Excel HDI a Tabla...........................................");
        
        FileInputStream input = new FileInputStream(dirArchivo);
        
        POIFSFileSystem fs = new POIFSFileSystem( input );
        HSSFWorkbook wb = new HSSFWorkbook(fs);
        HSSFSheet sheet = wb.getSheetAt(0);
        Row row;
        for(int i=1; i<=sheet.getLastRowNum(); i++){
            
            row = sheet.getRow(i);
if(!(row.getCell(6).toString().equals(""))){
            //Datos sacados del excel
            String ramo =row.getCell(0).getStringCellValue();                  //0
            long poliza =Long.parseLong(row.getCell(1).getStringCellValue().split("-")[2]);  //1 poliza
            int item=Integer.parseInt(row.getCell(1).getStringCellValue().split("-")[3]);     //1  item
            String rut = row.getCell(2).getStringCellValue();                   //2
            String nombre = row.getCell(3).getStringCellValue();           //3
            String moneda=row.getCell(5).getStringCellValue();//5
            java.util.Date fecha_vencimiento_inicial = row.getCell(6).getDateCellValue(); //6echa inicial
            int cantidad_cuotas=(int)row.getCell(7).getNumericCellValue();//7cantidad de cuotas
            double prima_total_cuotas=(double)row.getCell(8).getNumericCellValue(); //8 prima sumando todas las cuotas
            java.sql.Date sqlDate = new java.sql.Date(fecha_vencimiento_inicial.getTime());//fecha convertida a sql date
            //fin extraccion excel
            
            //hay que validar al contacto crear o ignorar si es igual
            
            if(cantidad_cuotas==0){ //se omite la fila, si es que no hay fecha ni cantidad cuotas
           
            }else{ 
            this.insertarContacto(rut, nombre);
            insertarPoliza(ramo, 0, poliza, item, 0, sqlDate, moneda,cantidad_cuotas,Integer.parseInt(rut.split("-")[0]),prima_total_cuotas);
                    System.out.println(nombre);
            }
           
            
        }
        }
    }catch(IOException ioe){
        System.out.println(ioe);
    }catch(Exception ex){
        System.out.println("Error buscar Excel "+ex.fillInStackTrace());
    }
    
    
    
}

public void insertarCambiosCuotas(String valorNuevo, long id) {
    try{
        String strSql="update cobranzas SET maxCuota="+valorNuevo+" where id = "+id;
        conexion.conectar();
        conexion.sentencia = conexion.conn.prepareStatement(strSql);
        conexion.sentencia.execute(strSql);
        conexion.desconectar();
        
        
    } catch (Exception ex) {
        System.out.println("Error al insertar Max Cuotas");
        System.out.println(ex);
    }
}

public void insertarContacto(String rut, String nombre){
    String dv=rut.split("-")[1];
    if(dv.equals("K")){
    dv="0";
    }
 try{  
 
        String strSql="insert into contactos (rut,dv,nombre) VALUES ("+Integer.parseInt(rut.split("-")[0])+","+Integer.parseInt(dv)+",'"+nombre+"') ON DUPLICATE KEY UPDATE  rut="+Integer.parseInt(rut.split("-")[0]);
        conexion.conectar();
        conexion.sentencia = conexion.conn.prepareStatement(strSql);
        conexion.sentencia.execute(strSql);
        conexion.desconectar();
        
        
    } catch (Exception ex) {
        System.out.println("Error al insertar Contacto");
        System.out.println(ex);
    }

}


public void agregarCuotas(int totalCuotas,long id) {
    try{
        
        //debe haber un sql que haga todo de aca, pero me da paja buscarlo
        String sql="select poliza from cobranzas where id="+id;
        conexion.conectar();
        conexion.sentencia = conexion.conn.prepareStatement(sql);
        ResultSet objSet=conexion.sentencia.executeQuery(sql);
        String poliza="";
        
        while(objSet.next()){
            poliza=objSet.getObject(1).toString();
                }
            String strSql="update cobranzas SET maxCuota="+totalCuotas+" where poliza ='"+poliza+"'";
            conexion.conectar();
            conexion.sentencia = conexion.conn.prepareStatement(strSql);
            conexion.sentencia.execute(strSql);
            conexion.desconectar();
       
            
        } catch (SQLException ex) {
            System.out.println("Error al insertarCuotas");
            System.out.println(ex);
        }
    }

    

    public void insertarCambioEstado(int nuevoEstado, long id) {
        
         try{
             
            String strSql="update cobranzas SET estado="+nuevoEstado+" where id = "+id;
            conexion.conectar();
            conexion.sentencia = conexion.conn.prepareStatement(strSql);
            conexion.sentencia.execute(strSql);
            conexion.desconectar();
       
            
        } catch (Exception ex) {
            System.out.println("Error al Updatear Estado");
            System.out.println(ex);
        }
         
    }
    
    public void insertarPoliza(String ramo, int tipoDoc, long poliza, int item, int company, Date fecha_vencimiento, String moneda, int cantidad_cuotas, int rut_id,double primaTotal) {
        //Preapracion de variables
        SimpleDateFormat formater = new SimpleDateFormat("yyyy/MM/dd");
        Calendar c = Calendar.getInstance();
        c.setTime(fecha_vencimiento);
        Double [] listado_cuotas=calcularUltimaCuota(primaTotal, cantidad_cuotas);
        
        for (int i = 1; i <= cantidad_cuotas; i++) {
            fecha_vencimiento=c.getTime();
            String idFinal=String.valueOf(poliza)+String.valueOf(item)+String.valueOf(i)+tipoDoc+String.valueOf(company);
            idFinal=idFinal.trim();//se crea la id unica compuesta por poliza,item,cuota,tipoDoc,compañia
            try{
                
                String strSql="insert into cobranzas (id,ramo,tipoDoc,poliza,item,company,fecha_vencimiento,moneda,cuota,maxCuota,monto,fk_idContacto) values ("+idFinal+",'"+ramo+"',"+tipoDoc+","+poliza+","+item+","+company+",'"+formater.format(fecha_vencimiento)+"','"+moneda+"',"+i+","+cantidad_cuotas+","+listado_cuotas[i-1]+","+rut_id+") ON DUPLICATE KEY UPDATE cuota="+i+", maxCuota="+cantidad_cuotas+", fecha_vencimiento='"+formater.format(fecha_vencimiento)+"'";//ON DUPLICATE KEY UPDATE cuota=values(cuota), estado=values(estado)
                c.add(Calendar.MONTH, 1);
                conexion.conectar();
                conexion.sentencia = conexion.conn.prepareStatement(strSql);
                conexion.sentencia.execute(strSql);
                conexion.desconectar();
                
            } catch (SQLException ex) {
                System.out.println("Error insertarCobranza SQL :"+ex);
                
            } 
        }
    }
    
    public List<Double> generar_listado_cuotas (double prima_total, int cantidad_cuotas){
        List<Double> listaCuotas = new ArrayList();
        
        try{
            NumberFormat nf= NumberFormat.getInstance();
            nf.setMaximumFractionDigits(2);
            nf.setMinimumFractionDigits(2);
            nf.setRoundingMode(RoundingMode.HALF_UP);
            BigDecimal decima_cant_cuotas=new BigDecimal(cantidad_cuotas);
            BigDecimal decima_prima_total = new BigDecimal(prima_total);
            BigDecimal modBig = decima_prima_total.remainder(decima_cant_cuotas);
            BigDecimal cuotas= decima_prima_total.divide(decima_cant_cuotas, 2, RoundingMode.HALF_UP);
            BigDecimal valorTotal=cuotas.multiply(new BigDecimal(cantidad_cuotas));
            nf.format(decima_prima_total);
            nf.format(decima_prima_total);
            BigDecimal diferencia_totales=decima_prima_total.subtract(valorTotal);
            for (int i = 1; i <= cantidad_cuotas; i++) {
                if(Double.parseDouble(diferencia_totales.toString())==0.00&&i==cantidad_cuotas){//si la diferencia es 0
                    //insertar cobranza iteracion
                    
                }else{//
                    
                    
                }
            }
   
    

    }catch(Exception ex){
        System.out.println("Error Calcular Listado de Cuotas"+ex);
    }
        
        
        
        return listaCuotas;
    }
    
    public Double[] calcularUltimaCuota(double prima_total, int cantidad_cuotas) {
        NumberFormat nf= NumberFormat.getInstance();nf.setMaximumFractionDigits(2);nf.setMinimumFractionDigits(2);nf.setRoundingMode(RoundingMode.HALF_UP);//se inicio el formater
        BigDecimal decima_cant_cuotas=new BigDecimal(cantidad_cuotas);
        BigDecimal decima_prima_total = new BigDecimal(prima_total);
        nf.format(decima_prima_total);
        nf.format(decima_cant_cuotas);
        double mod_normal=prima_total%cantidad_cuotas;
        BigDecimal modBig = decima_prima_total.remainder(decima_cant_cuotas);
        String resto_division=nf.format(modBig);
        BigDecimal cuotas= decima_prima_total.divide(decima_cant_cuotas, 2, RoundingMode.HALF_UP);
        nf.format(cuotas);
        double cuotas_normal=Double.parseDouble(cuotas.toString());
        double  ultimaCuota= ((cuotas_normal*(cantidad_cuotas-1))-prima_total)*-1;
        Double[] array_cuotas = new Double[cantidad_cuotas];
        for (int i = 0; i < cantidad_cuotas; i++) {

            array_cuotas[i]=Double.parseDouble(cuotas.toString().replace(",", "."));
   
        }
        if(!(resto_division.equals("0.00"))){//entonces todas las cuotas son iguales
            BigDecimal uc = new BigDecimal(ultimaCuota);           
            array_cuotas[cantidad_cuotas-1]=Double.parseDouble(nf.format(uc).replace(".", "").replace(",", "."));

        }
     
        
    return array_cuotas;
}
    public int consultarTipoDoc(String tipoDoc) {
        int tipoDocInt=0;
        if(tipoDoc.equals("Rehabilitación")){
            tipoDocInt=2;
        }else{
            if(tipoDoc.equals("Modificacion")){
                tipoDocInt=1;
            }else{
                tipoDocInt=0;
            }
        }
        return tipoDocInt;
    }

    public int consultarCompany(String companyName) {
        int companyInt=0;
          if(companyName.equals("HDI")){
                companyInt=0;
                }else{
                     if(companyName.equals("Liberty")){
                     companyInt=1;
                     }else{
                         companyInt=2;
                     }
                 }
          return companyInt;
    
    
    }

    public String buscarRutPorNombre(String nombre) {
  String rut="";
  try{
            conexion.conectar();
            String strSql="SELECT rut,dv from contactos where nombre='"+nombre+"'";
            conexion.sentencia=conexion.conn.prepareStatement(strSql);
            ResultSet objSet=conexion.sentencia.executeQuery(strSql);
            while(objSet.next()){
                 for (int i = 0; i < 1; i++) {
                     if(i==0){
                       rut=objSet.getObject(i+1).toString();
                     }else{
                         rut=rut+"-"+objSet.getObject(i+1).toString();
                     }
           
            }
            }
  }catch(SQLException ex){
      System.out.println("Error buscar rut por Nombre"+ex);
  }
    return rut;
    }

    public void  agregarContactos(String rut, String nombre,String telefonos,String direccion) {
        
  
  String split[]=rut.split("-");
  if(split[1].equals("k")){
  split[1]="0";
  }
    
     try{
            String strSql="insert into contactos (rut,dv,nombre, telefonos,direccion) VALUES ("+Integer.parseInt(split[0])+","+Integer.parseInt(split[1])+",'"+nombre+"','"+telefonos+"','"+direccion+"') ON DUPLICATE KEY UPDATE  nombre='"+nombre+"', telefonos='"+telefonos+"', direccion='"+direccion+"'";
            conexion.conectar();
            conexion.sentencia = conexion.conn.prepareStatement(strSql);
            conexion.sentencia.execute(strSql);
            conexion.desconectar();
       
            
        } catch (Exception ex) {
            System.out.println("Error AL AGREGAR NUEVO CONTACTO");
            System.out.println(ex);
        }
    }
  
   

  public boolean validarRut(String rut) {
 
boolean validacion = false;
try {
rut =  rut.toUpperCase();
rut = rut.replace(".", "");
rut = rut.replace("-", "");
int rutAux = Integer.parseInt(rut.substring(0, rut.length() - 1));
 
char dv = rut.charAt(rut.length() - 1);
 
int m = 0, s = 1;
for (; rutAux != 0; rutAux /= 10) {
s = (s + rutAux % 10 * (9 - m++ % 6)) % 11;
}
if (dv == (char) (s != 0 ? s + 47 : 75)) {
validacion = true;
}
 
} catch (java.lang.NumberFormatException e) {
} catch (Exception e) {
}
return validacion;
} 

 

    public void updatearAnteriores(double cuota, int id) {
    try{
             
            String strSql="";
            conexion.conectar();
            conexion.sentencia = conexion.conn.prepareStatement(strSql);
            conexion.sentencia.execute(strSql);
            conexion.desconectar();
       
            
        } catch (Exception ex) {
            System.out.println("Error al Updatear Anteriores");
            System.out.println(ex);
        }
    
    
    }


public Double calcularCuotas(Double PrimaTotal, int cantidad_cuotas) {
    double cuota=0.0;
    try{
        NumberFormat nf= NumberFormat.getInstance();
        nf.setMaximumFractionDigits(2);
        nf.setMinimumFractionDigits(2);
        nf.setRoundingMode(RoundingMode.HALF_UP);
        BigDecimal primaTotal = new BigDecimal(PrimaTotal);
        nf.format(PrimaTotal);
        BigDecimal divisor = new BigDecimal(cantidad_cuotas);
        BigDecimal mod = primaTotal.remainder(divisor);
        BigDecimal cuociente = primaTotal.divide(divisor, 2, RoundingMode.HALF_UP);
        
        
        
        
        cuota= Double.parseDouble(nf.format(cuociente).replace(",", "."));
    }catch(Exception ex){
        System.out.println("error calcular cuotas "+ex);
    }
    
        return cuota;
    }



    public void editarContacto(String nombre, String rut, String telefonos, String direccion) {
   try{
             
            String strSql="UPDATE morosos SET  nombre='"+nombre+"', telefonos='"+telefonos+"', direccion='"+direccion+"' where rut="+rut;
            conexion.conectar();
            conexion.sentencia = conexion.conn.prepareStatement(strSql);
            conexion.sentencia.execute(strSql);
            conexion.desconectar();
       
            
        } catch (Exception ex) {
            System.out.println("Error editar Contacto");
            System.out.println(ex);
        }
    
    
    }
    
    public long buscar_id_por_numPoliza(long numPoliza){
    long id=0;
      try{
             conexion.conectar();
            String strSql="SELECT id from cobranzas where poliza="+numPoliza+" group by id";
            conexion.sentencia=conexion.conn.prepareStatement(strSql);
            ResultSet objSet=conexion.sentencia.executeQuery(strSql);
            while(objSet.next()){
           
                   id=Long.parseLong(objSet.getObject(0).toString());
           
            
            }
       conexion.desconectar();
            
        } catch (Exception ex) {
            System.out.println("Error buscar id Cobranza segun numPoliza");
            System.out.println(ex);
        }
    
    return id;
    }
    
    public   Object [] buscarAseguradoPorId(long rut) {
          Object [] fila=new Object [5];
           try{
          
          //debe haber un sql que haga todo de aca, pero me da paja buscarlo
            String sql="select * from contactos where rut="+rut;
            conexion.conectar();
            conexion.sentencia = conexion.conn.prepareStatement(sql);
            ResultSet objSet=conexion.sentencia.executeQuery(sql);
           
                   while(objSet.next()){
                for (int i = 0; i < 5; i++) {
                    fila[i]=objSet.getObject(i+1);
                }
                   }
            
           } catch (SQLException ex) {
               System.out.println("Error al buscar Asegurado");
               System.out.println(ex);
           }
            return fila;
           
    }

    public DefaultTableModel buscarTablaCobranza(DefaultTableModel modelo) {
        
     //   ImageIcon errorIcon = new ImageIcon("C:\\madremia\\PRogramaLiberty\\descarga\\no-imagen.png");

        Object [] fila=new Object [7];

           try{
          
          //debe haber un sql que haga todo de aca, pero me da paja buscarlo
            String sql="SELECT a.id,b.nombre,a.poliza,a.item,a.company FROM caracola.cobranzas a, caracola.contactos b where a.fk_idContacto=b.rut group by a.poliza";
            conexion.conectar();
            conexion.sentencia = conexion.conn.prepareStatement(sql);
            ResultSet objSet=conexion.sentencia.executeQuery(sql);
           int contador=0;
                   while(objSet.next()){
                for (int i = 0; i < 5; i++) {
                    fila[i]=objSet.getObject(i+1);
                    
                    switch(i){
                        
                      
                        case 4:
                            switch((int)fila[i]){
                                case 0:fila[i]="HDI";break;
                                case 1:fila[i]="Liberty";break;
                                case 2:fila[i]="SURA";break;
                            } ;break;
      
                    }

                }
                   modelo.addRow(fila);
      modelo.setValueAt(calcularPendiente((long)modelo.getValueAt(contador, 2))[0], contador, 5);
                 
                  

//modelo.setValueAt(new ImageIcon("C:\\madremia\\PRogramaLiberty\\descarga\\no-imagen.png"),contador,5);
                  contador++;
                   }
            conexion.desconectar();
           } catch (SQLException ex) {
               System.out.println("Error al buscar Tabla Cobranza");
               System.out.println(ex);
           }
            return modelo; 
    }
        public DefaultTableModel buscarTablaPoliza(DefaultTableModel modelo, long idPoliza) {
      Object [] fila=new Object [12];
           try{
          
          //debe haber un sql que haga todo de aca, pero me da paja buscarlo
            String sql="SELECT a.id,a.poliza,a.item,a.tipoDoc,a.cuota,a.maxCuota,a.monto,a.moneda,a.fecha_vencimiento,a.estado,a.obs,a.transferencia FROM caracola.cobranzas a, caracola.contactos b where a.poliza="+idPoliza+" and b.rut=a.fk_idContacto order by fecha_vencimiento ASC";
            conexion.conectar();
            conexion.sentencia = conexion.conn.prepareStatement(sql);
            ResultSet objSet=conexion.sentencia.executeQuery(sql);
           
                   while(objSet.next()){
                for (int i = 0; i < 11; i++) {
                    fila[i]=objSet.getObject(i+1);
                    
                    switch(i){
                        case 3://tipo documento
                            switch((int)fila[i]){
                                case 0:fila[i]="EMISIÓN";break;
                                case 1:fila[i]="MODIFICACIÓN";break;
                                case 2:fila[i]="REHABILITACIÓN";break;
                            }
                            ;break;
                        case 8:
                        String fecha=fila[i].toString();
                        SimpleDateFormat hh = new SimpleDateFormat("dd MM yyyy");
                        java.sql.Date fechaUtil = java.sql.Date.valueOf(fecha);
                        java.util.Date fechaActual = new java.util.Date();
                        fechaUtil.toLocalDate();                       
                        hh.applyPattern("dd/MM/yyyy");
                        hh.format(fechaUtil);
                        hh.format(fechaActual);
                        //     LocalDate x =fechaUtil.toLocalDate();
                        //   x.format(DateTimeFormatter.ofPattern("dd MMM yyyy"));
                        java.util.Date fecha_actual=java.sql.Date.from(Calendar.getInstance().toInstant());
                        hh.format(fecha_actual);
                        fila[i]=hh.format(fechaUtil);
                            ;break;
                        
                    //    case 11:
                    //        switch((int)fila[i]){
                   //             case 0:fila[i]="HDI";break;
                   //             case 1:fila[i]="Liberty";break;
                   //             case 2:fila[i]="SURA";break;
                  //          } ;break;
                        case 9:
              
                            switch((int) fila[i]){
                                case 0:fila[i]="Pendiente";break;
                                case 1:fila[i]="Cobrado";break;
                                case 2:fila[i]="Ingresado";break;
                            }
                            ;break;
                        case 12://se inserta el icono de la imagen
                            if(fila[i]!=null){
                            
                            
                            }
                            ;break;   
                         
                            
                    }
                    
                  
                 
                   
                   
                 
                }
                   modelo.addRow(fila);
                   }
            
           } catch (SQLException ex) {
               System.out.println("Error al buscar Tabla Poliza");
               System.out.println(ex);
           }
            return modelo; 
    }

  

    public String consultarFechaInicioPorId(int idCobranza) {

    String fecha_inicial="";
           try{
          
          //debe haber un sql que haga todo de aca, pero me da paja buscarlo
            String sql="SELECT fecha_vencimiento FROM cobranzas WHERE fecha_vencimiento = ( SELECT MIN(fecha_vencimiento) FROM cobranzas where id="+idCobranza+")";
            conexion.conectar();
            conexion.sentencia = conexion.conn.prepareStatement(sql);
            ResultSet objSet=conexion.sentencia.executeQuery(sql);
            
                   while(objSet.next()){
              fecha_inicial=objSet.getObject(1).toString();
                   }
    
    
           }catch(SQLException ex){
            System.out.println("Error consulta fecha inicial por id"+ex);
           }
           
return fecha_inicial; }

    public void updatearTablaPolizas(TableModelEvent evento, JXTable tabla) {
        DateFormat x = DateFormat.getInstance();
        
     String valorNuevo=tabla.getValueAt(evento.getFirstRow(), evento.getColumn()).toString();
     String strSql="";
     int id=Integer.parseInt(tabla.getValueAt(evento.getFirstRow(), 0).toString());
  
        try{
            switch(evento.getColumn()){
                case 0:break;//nada por que la ID no se edita
                case 1:strSql="UPDATE cobranzas SET poliza="+Integer.parseInt(valorNuevo)+" WHERE id="+id;break;
                case 3:strSql="UPDATE cobranzas SET item="+Integer.parseInt(valorNuevo)+" WHERE id="+id;break;
                case 4:strSql="UPDATE cobranzas SET tipoDoc="+Integer.parseInt(valorNuevo)+" WHERE id="+id;break;
                case 5:strSql="UPDATE cobranzas SET company="+Integer.parseInt(valorNuevo)+" WHERE id="+id;break;
                case 6:;// no se puede editar el nombre en la tabla
                case 7:strSql="UPDATE cobranzas SET cuota="+Integer.parseInt(valorNuevo)+" WHERE id="+id;break;
                case 8:strSql="UPDATE cobranzas SET maxCuota="+Integer.parseInt(valorNuevo)+" WHERE id="+id;break;
                case 9:strSql="UPDATE cobranzas SET monto="+Double.parseDouble(valorNuevo)+" WHERE id="+id;break;
                case 10:strSql="UPDATE cobranzas SET moneda='"+valorNuevo+"' WHERE id="+id;break;
                case 11:strSql="UPDATE cobranzas SET fecha_vencimiento='"+x.parse(valorNuevo)+"' WHERE id="+id;break;//cambiar a date
                case 12:strSql="UPDATE cobranzas SET estado='"+valorNuevo+"' WHERE id="+id;break;
                case 13:strSql="UPDATE cobranzas SET obs='"+valorNuevo+"' WHERE id="+id;break;
                case 14:strSql="UPDATE cobranzas SET transferencia='"+valorNuevo+"' WHERE id="+id;break;
   
            }
            
            conexion.conectar();
            conexion.sentencia = conexion.conn.prepareStatement(strSql);
            conexion.sentencia.execute(strSql);
            conexion.desconectar();
       
            
        } catch (Exception ex) {
            System.out.println("Error editar Contacto");
            System.out.println(ex);
        }
        
        
        
        
     }

    public void agregarTransferencia(String dirTrans, long id) {
   try{
            String strSql="UPDATE morosos SET transferencia='"+dirTrans.replace("\\", "\\\\")+"' WHERE id="+id;
            conexion.conectar();
            conexion.sentencia = conexion.conn.prepareStatement(strSql);
            conexion.sentencia.execute(strSql);
            conexion.desconectar();
       
            
        } catch (Exception ex) {
            System.out.println("Error AL AGREGAR Nueva Transferencia");
            System.out.println(ex);
        }
    
    }

    public void updatearEstadoConTransferencia(int i,long id, File file) {
         try{
//            Path u= Paths.get(dirArchivo);          
//            Runtime.getRuntime().exec(new String[]
//            {"rundll32", "url.dll,FileProtocolHandler",
//                u.toAbsolutePath().toString()});
String strSql="update cobranzas SET estado="+i+" where id ="+id;
if(file!=null){
    String dirArchivo=file.toString();
   
    CharSequence x="\\";
    CharSequence z="\\\\";
    dirArchivo=dirArchivo.replace(x, z);
     strSql="update cobranzas SET estado="+i+", transferencia='C:\\\\madremia\\\\PRogramaLiberty\\\\descarga\\\\transferencias\\\\"+file.getName()+"' where id ="+id;//se agrega la transferencia
    //copiar archivo a carpeta transferencia
    Path source=  Paths.get(dirArchivo);
    System.out.println("Source"+source);
    Path target= Paths.get("C:\\madremia\\PRogramaLiberty\\descarga\\transferencias\\"+file.getName());
    System.out.println("Target :"+target);
    Files.copy(source, target, REPLACE_EXISTING);
}
            conexion.conectar();
             conexion.sentencia = conexion.conn.prepareStatement(strSql);
            conexion.sentencia.execute(strSql);
            conexion.desconectar();
            
        } catch (Exception ex) {
            System.out.println("Error al updatear ESTADO");
            System.out.println(ex);
        }
        
      }

    public String buscarDirectorioPorId(Long id) {
          String directorio="";
          try{
          
          //debe haber un sql que haga todo de aca, pero me da paja buscarlo
            String sql="select transferencia from cobranzas where id="+id;
            conexion.conectar();
            conexion.sentencia = conexion.conn.prepareStatement(sql);
            ResultSet objSet=conexion.sentencia.executeQuery(sql);
          

             while(objSet.next()){
              directorio=objSet.getObject(1).toString();
                }
          
            conexion.desconectar();
       
            
        } catch (SQLException ex) {
            System.out.println("Error al Buscar Directorio");
            System.out.println(ex);
        }catch(NullPointerException ex){
        directorio="";
        }
          
        
        return directorio;
   }

    public ArrayList<String> buscarNumPolizaxContacto(String rut) {
      ArrayList<String> itemPolizas= new ArrayList();
           try{
          
          //debe haber un sql que haga todo de aca, pero me da paja buscarlo
            String sql="SELECT a.poliza FROM caracola.cobranzas a where a.fk_idContacto='"+rut+"' GROUP BY(a.poliza)";
            conexion.conectar();
            conexion.sentencia = conexion.conn.prepareStatement(sql);
            ResultSet objSet=conexion.sentencia.executeQuery(sql);
            
                   while(objSet.next()){
              itemPolizas.add(objSet.getObject(1).toString());
                   }
    
    
           }catch(SQLException ex){
            System.out.println("Error consulta polizas por nombre"+ex);
           }
           
return itemPolizas;
        
     }

    public Object[] buscarPorPoliza(long numPoliza) {
     
   Object [] fila=new Object [17];
           try{
          
          //debe haber un sql que haga todo de aca, pero me da paja buscarlo
            String sql="SELECT a.id,a.ramo,b.nombre,b.rut,b.dv,b.direccion,b.telefonos,a.poliza,a.item,a.tipoDoc,a.cuota,a.maxCuota,a.monto,a.moneda,a.fecha_vencimiento,a.company,a.estado,a.obs FROM caracola.cobranzas a, caracola.contactos b where a.poliza="+numPoliza+" and b.rut=a.fk_idContacto";
            conexion.conectar();
            conexion.sentencia = conexion.conn.prepareStatement(sql);
            ResultSet objSet=conexion.sentencia.executeQuery(sql);
            
                   while(objSet.next()){
                for (int i = 0; i < 17; i++) {
                    fila[i]=objSet.getObject(i+1);
                
                }
                   }
    
    
           }catch(SQLException ex){
            System.out.println("Error consultar por Poliza"+ex);
           }
           
return fila;

    }
    
    
    

    public void insertarObservacionPoliza(long poliza, String observacion) {
     

 
     try{
            String sql="INSERT INTO observaciones_poliza value ("+poliza+",'"+observacion+"') ON DUPLICATE KEY UPDATE observaciones='"+observacion+"'";
            conexion.conectar();
            conexion.sentencia = conexion.conn.prepareStatement(sql);
            conexion.sentencia.execute(sql);
            conexion.desconectar();
       
            
        } catch (Exception ex) {
            System.out.println("Error AL AGREGAR OBSERVACION");
            System.out.println(ex);
        }
   

    }

    public String buscarObservacionPorPoliza(long numPoliza) {
        Object [] fila=new Object [1];
        fila[0]="Sin Observaciones";
           try{         
            String sql="SELECT b.observaciones FROM caracola.cobranzas a, caracola.observaciones_poliza b where a.poliza="+numPoliza+" and b.numero_poliza="+numPoliza+"";
            conexion.conectar();
            conexion.sentencia = conexion.conn.prepareStatement(sql);
            ResultSet objSet=conexion.sentencia.executeQuery(sql);
            
                   while(objSet.next()){
                for (int i = 0; i < 1; i++) {
                    fila[i]=objSet.getObject(i+1);
                   }
                   }
    
    
           }catch(SQLException ex){
            System.out.println("Error consultar por buscarObservacionPorPoliza"+ex);
           }
           
return fila[0].toString();
        
    }
    
      public boolean buscarNombrePorRut(String rut) {
        boolean j = false;
        try{
            String sql="SELECT rut FROM caracola.contactos where rut="+rut;
            conexion.conectar();
            conexion.sentencia = conexion.conn.prepareStatement(sql);
            ResultSet objSet=conexion.sentencia.executeQuery(sql);
            
            while(objSet.next()){
                for (int i = 0; i < 1; i++) {
                    j=true;
                }
            }
        }catch(SQLException ex){
            System.out.println("Error consultar por nombre"+ex);
        }
        
        return j;
    }
    
      public String[] calcularPendiente (long numPoliza){
          String[] resultado = new String[4];//estado,fecha y cuota
          int dias_de_diferencia=0;
          int company=0;
          DateTime  fa = new DateTime();
          DateTime hoy = new DateTime();         
          String cuotaPendiente="";
          try{
              String sql="SELECT min(fecha_vencimiento),cuota,company from cobranzas where estado=0 and poliza="+numPoliza;          
              conexion.conectar();
              conexion.sentencia = conexion.conn.prepareStatement(sql);
              ResultSet objSet=conexion.sentencia.executeQuery(sql);
           

              while(objSet.next()){
                  fa=DateTime.parse(objSet.getObject(1).toString());
                  cuotaPendiente=objSet.getObject(2).toString();
                  company=Integer.parseInt(objSet.getObject(3).toString());
                  
            }
      
        conexion.desconectar();
        
        
        dias_de_diferencia=hoy.dayOfYear().getDifference(fa)*-1;
        resultado[1]=String.valueOf(dias_de_diferencia);
        resultado[2]=cuotaPendiente;
        resultado[3]=String.valueOf(fa);
        DateTime f_anulacion= fa.plusMonths(1);
        f_anulacion=f_anulacion.withDayOfMonth(25);//HDI SURA comparacion solo para hdi y sura mes siguiente el dia 25
        
        if(company==0||company==2){
            int dias_de_diferencia_anulada=fa.dayOfYear().getDifference(f_anulacion);
            
            if(dias_de_diferencia<=dias_de_diferencia_anulada){//si esta anulada +-15
                resultado[1]=String.valueOf(dias_de_diferencia_anulada*-1);
                resultado[0]="3";//ANULADA
            }else{
                if(dias_de_diferencia<0&&dias_de_diferencia>dias_de_diferencia_anulada){//si esta vencida y por anularse
                    resultado[0]="2";//por anularse vencida
                    resultado[1]=String.valueOf(dias_de_diferencia_anulada*-1);
                }else{
                    if(dias_de_diferencia==0){
                        resultado[0]="4";//hoy
                    }else{
                        if(dias_de_diferencia>0&&dias_de_diferencia<=15){// si esta dentro de lo 15 dias para el vencimiento
                            resultado[0]="1";//Por vencer
                        }else{
                            if(dias_de_diferencia>0&&dias_de_diferencia>15){
                                resultado[0]="0";//al Dia
                            }
                        }
                    }
                }
            }
        }else{
            
            
            
            if(dias_de_diferencia<=(-15)){//si esta anulada +-15
                resultado[0]="3";//ANULADA
            }else{
                if(dias_de_diferencia>(-15)&&dias_de_diferencia<0){//si esta vencida y por anularse
                    resultado[0]="2";//por anularse vencida
                    
                }else{
                    if(dias_de_diferencia==0){
                        resultado[0]="4";//hoy
                    }else{
                        if(dias_de_diferencia>0&&dias_de_diferencia<=15){// si esta dentro de lo 15 dias para el vencimiento
                            resultado[0]="1";//Por vencer
                        }else{
                            if(dias_de_diferencia>0&&dias_de_diferencia>15){
                                resultado[0]="0";//al Dia
                            }
                        }
                    }
                }
            }
        }
        
          }catch(SQLException | NullPointerException ex){
        
            
            
         
            try{
                conexion.desconectar();
              String sql="SELECT max(fecha_vencimiento),cuota from cobranzas where estado=1 and poliza="+numPoliza+" or estado=2 and poliza="+numPoliza;          
              conexion.conectar();
              conexion.sentencia = conexion.conn.prepareStatement(sql);
              ResultSet objSet=conexion.sentencia.executeQuery(sql);
           

              while(objSet.next()){
                  fa=DateTime.parse(objSet.getObject(1).toString());
                  cuotaPendiente=objSet.getObject(2).toString();
                 
                  
            }
              resultado[0]="5";
          resultado[1]="0";
        resultado[2]=cuotaPendiente;
        resultado[3]=String.valueOf(fa);
         conexion.desconectar();
              return resultado;
            }catch(SQLException | NullPointerException es){
                System.out.println("Error Buscar ultima poliza cobrada o ingresada");
            
            }
        }



return resultado;
    }

    public void updatear_poliza_a_cobrado(long numPoliza) {
   
    try{
    String strSql="UPDATE caracola.cobranzas set estado=1 where poliza="+numPoliza;
    conexion.conectar();
    conexion.sentencia = conexion.conn.prepareStatement(strSql);
    conexion.sentencia.execute(strSql);
    conexion.desconectar();
    
    
    
    }catch(SQLException ex){
        System.out.println("Error Updatear Poliza Completa a 'Cobrado'");
                }
    
    
    
    }

    public Object[] buscarAseguradoPorPoliza(long numPoliza) {
        Object [] fila=new Object [5];
           try{
          
          //debe haber un sql que haga todo de aca, pero me da paja buscarlo
            String sql="select * from contactos a, cobranzas b where a.rut=b.fk_idContacto and b.poliza="+numPoliza;
            conexion.conectar();
            conexion.sentencia = conexion.conn.prepareStatement(sql);
            ResultSet objSet=conexion.sentencia.executeQuery(sql);
           
                   while(objSet.next()){
                for (int i = 0; i < 5; i++) {
                    fila[i]=objSet.getObject(i+1);
                }
                   }
            
           } catch (SQLException ex) {
               System.out.println("Error al buscar Datos Asegurado con numero de poliza");
               System.out.println(ex);
           }
            return fila;
    }
    
    public void exportar_excel(JTable tabla){
        
        try {
            FileOutputStream fileOut = new FileOutputStream("C:\\madremia\\PRogramaLiberty\\descarga\\reporte.xls");
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet worksheet = workbook.createSheet("POI Worksheet");
            for (int i = 0; i < tabla.getRowCount(); i++) {
                // index from 0,0... cell A1 is cell(0,0)
                HSSFRow row1 = worksheet.createRow((short) 0);
           
                HSSFCell cellA1 = row1.createCell((short) 0);
                cellA1.setCellValue("Hello");
                HSSFCellStyle cellStyle = workbook.createCellStyle();
                cellStyle.setFillForegroundColor(HSSFColor.GOLD.index);
                cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                cellA1.setCellStyle(cellStyle);
                
                HSSFCell cellB1 = row1.createCell((short) 1);
                cellB1.setCellValue("Goodbye");
                cellStyle = workbook.createCellStyle();
                cellStyle.setFillForegroundColor(HSSFColor.LIGHT_CORNFLOWER_BLUE.index);
                cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                cellB1.setCellStyle(cellStyle);
                
                HSSFCell cellC1 = row1.createCell((short) 2);
                cellC1.setCellValue(true);
                
                HSSFCell cellD1 = row1.createCell((short) 3);
                cellD1.setCellValue(new Date());
                cellStyle = workbook.createCellStyle();
                cellStyle.setDataFormat(HSSFDataFormat
                        .getBuiltinFormat("m/d/yy h:mm"));
                cellD1.setCellStyle(cellStyle);
            }
            workbook.write(fileOut);
            fileOut.flush();
            fileOut.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        
        
        
    }
    
}
    
    












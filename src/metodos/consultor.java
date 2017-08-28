/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metodos;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import conexion.conexion;
import static interfaz.agregarCobranza.cuotaMax;
import static interfaz.agregarCobranza.monto;
import java.awt.Button;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.io.File;
import java.io.FileInputStream;
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
import java.util.Calendar;
import java.util.List;
import javax.swing.ButtonModel;
import javax.swing.ComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Row;
import org.jdesktop.swingx.JXTable;
import org.joda.time.DateTime;
import org.joda.time.Days;
import static org.joda.time.format.ISODateTimeFormat.date;


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
    
public void extraerExcelHdi(String dirArchivo){
    
try{
    
        System.out.println("1.-Importanto Excel HDI a Tabla...........................................");
             
            FileInputStream input = new FileInputStream(dirArchivo);
            
            POIFSFileSystem fs = new POIFSFileSystem( input );
            HSSFWorkbook wb = new HSSFWorkbook(fs);
            HSSFSheet sheet = wb.getSheetAt(0);
            Row row;
             String sqlBorrar="DELETE FROM listadoNuevo";
             conexion.conectar();
        conexion.sentencia=conexion.conn.prepareStatement(sqlBorrar);
               conexion.sentencia.execute(sqlBorrar);
          
              
            for(int i=1; i<=sheet.getLastRowNum(); i++){
              
                row = sheet.getRow(i);
                
                String ramo =row.getCell(0).getStringCellValue();
                int poliza =(int)row.getCell(1).getNumericCellValue();
                int item =(int)row.getCell(2).getNumericCellValue();
                String rut = row.getCell(3).getStringCellValue();
                String nombre = row.getCell(4).getStringCellValue();
                String tipoDoc = row.getCell(5).getStringCellValue();               
                int cuota =(int)row.getCell(6).getNumericCellValue();
               // int maxCuota=(int)row.getCell(7).getNumericCellValue();
                double monto = (double) row.getCell(7).getNumericCellValue();
                String moneda = row.getCell(8).getStringCellValue();
                java.util.Date fecha_vencimiento = row.getCell(9).getDateCellValue();
                java.sql.Date sqlDate = new java.sql.Date(fecha_vencimiento.getTime());
                String estado = row.getCell(10).getStringCellValue(); 
                
                int tipo_documento=0;
                int company=0;
                String str_company="HDI";
                  int estadoindex=0;
                
                
                 if(tipoDoc.equals("Rehabilitación")){
                tipo_documento=2;
                }else{
                     if(tipoDoc.equals("Modificacion")){
                     tipo_documento=1;
                     }else{
                         tipo_documento=0;
                     }
                 }
                 if(str_company.equals("HDI")){
                 company=0;
                 }else{
                     if(str_company.equals("Liberty")){
                       company=1;
                     }else{
                         //no hay sura...todavia.
                     }
                   
                      if(estado.equals("PENDIENTE")){
                 estadoindex=0;
                 }else{
                     if(estado.equals("Cobrado")){
                       estadoindex=1;
                     }else{
                        estadoindex=2;
                     }
                      }
                     
                 }
                 
                 //aprendi a aanotar:-->>> Se genera la ID FINAL de la FILA.
                 // es numero poliza,item,cuota,tipo_Doc,y compañia los mismo con liberty
                 //se añade una ademas digito final. 1-2-3 Represeanta 1.Libertty, 2.-HDI 3.-Sura
                String id=String.valueOf(poliza)+String.valueOf(item)+String.valueOf(cuota)+tipo_documento+String.valueOf(company);
                id=id.trim();
              
            
               long idFinal=Long.parseLong(id);
               
               //    int idFinal=Integer.parseInt(id);
               String digitoVerificador=String.valueOf(rut);
               String[] parts = digitoVerificador.split("-");
               String idRut = parts[0]; // 004
               String verificador = parts[1]; // 034556
               String sqlContacto="INSERT IGNORE INTO contactos (rut,dv,nombre) values ("+idRut+",'"+verificador+"','"+nombre+"')";
               conexion.sentencia=conexion.conn.prepareStatement(sqlContacto);
               conexion.sentencia.execute(sqlContacto);                                                                                                                                                                                                                                                         //ON DUPLICATE KEY UPDATE cuota=values(cuota), estado=values(estado)
               String sql = "INSERT INTO listadoNuevo (id,ramo,poliza,item,tipoDoc,cuota,monto,moneda,fecha_vencimiento,estado,company,fk_idContacto) VALUES ('"+idFinal+"','"+ramo+"','"+poliza+"','"+item+"',"+tipo_documento+",'"+cuota+"','"+monto+"','"+moneda+"','"+sqlDate+"',"+estadoindex+",0,"+idRut+") ;";              
               conexion.sentencia=conexion.conn.prepareStatement(sql);
               conexion.sentencia.execute(sql);
               
               
               
               
            }
          
            conexion.desconectar();
            System.out.println("2.-EXCEL IMPORTADO A TABLA.");
            consultor consulta = new consultor();
             if(consulta.agregarMorosos()){ //si es diferente elimina, ahora que 
                System.out.println("Clientes morosos Fueron Agregados");
                
            }else{
                System.out.println("Ningun moroso nuevo fue agregado.");
            }
            if(consulta.actualizarPagados()){ //si es diferente elimina, ahora que 
                System.out.println("Clientes con Pagos Ingresados Actualizados");
                
            }else{
                System.out.println("No se actualizo ningun Cliente");
            }
        //Elimina las diferencias          
            
            //Empieza UNION de archivos
      
            
            
        }catch(SQLException ex){
            System.out.println(ex);
        }catch(IOException ioe){
            System.out.println(ioe);
        }catch(Exception ex){
        System.out.println(ex);
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

public boolean alertaRoja(Date fecha_vencimiento){
Date fecha_actual=(Date) Calendar.getInstance().getTime();
  
    
    
return false;
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
    
    public void insertarCobranza(String ramo, int tipoDoc, long poliza, int item, int company, Date fecha_vencimiento, String moneda, int cuotaMax, double monto, int contacto,double primaTotal) {
       //hay que insertar el mes inicial despues añadir
         SimpleDateFormat formater = new SimpleDateFormat("yyyy/MM/dd");//se toma la fecha y se añade un mes despues
                Calendar c = Calendar.getInstance();
        for(int i=1;i<=cuotaMax;i++){
            if(i==cuotaMax){
       //la ultima fila tiene la diferencia
       //aca se pone la diferencia si hay mas decimales que causen diferencia
    monto=this.calcularUltimaCuota(primaTotal, cuotaMax);
       }
            try{
                
               
                fecha_vencimiento=c.getTime();
                c.setTime(fecha_vencimiento);
                c.add(Calendar.MONTH, 1);
                String idFinal=String.valueOf(poliza)+String.valueOf(item)+String.valueOf(i)+tipoDoc+String.valueOf(company);
                idFinal=idFinal.trim();//se crea la id unica compuesta por poliza,item,cuota,tipoDoc,compañia
                String strSql="insert into cobranzas (id,ramo,tipoDoc,poliza,item,company,fecha_vencimiento,moneda,cuota,maxCuota,monto,fk_idContacto) values ("+idFinal+",'"+ramo+"',"+tipoDoc+","+poliza+","+item+","+company+",'"+formater.format(fecha_vencimiento)+"','"+moneda+"',"+i+","+cuotaMax+","+monto+","+contacto+") ON DUPLICATE KEY UPDATE cuota="+i+", maxCuota="+cuotaMax+", fecha_vencimiento='"+formater.format(fecha_vencimiento)+"'";//ON DUPLICATE KEY UPDATE cuota=values(cuota), estado=values(estado)
                conexion.conectar();
                conexion.sentencia = conexion.conn.prepareStatement(strSql);
                conexion.sentencia.execute(strSql);
              conexion.desconectar();
              
            
        } catch (SQLException ex) {
            System.out.println("Error al Nueva Cobranza con max Cuotas");
            System.out.println(ex);
        }
        
        }
        
    }

    public void insertarCobranzaSinMaxCuota(long id,String ramo, int tipoDoc, int poliza, int item, int company, java.util.Date fecha_vencimiento, String moneda, int cuotaInicial, double monto, int contacto) {
    try{
          SimpleDateFormat formater = new SimpleDateFormat("yyyy/MM/dd");
            //inserta nueva fila si el id generado es igual entonces updatea la fila.
        String idFinal=String.valueOf(poliza)+String.valueOf(item)+String.valueOf(cuotaInicial)+tipoDoc+String.valueOf(company);
                idFinal=idFinal.trim();
            String strSql="insert into cobranzas (id,ramo,tipoDoc,poliza,item,company,fecha_vencimiento,moneda,cuota,maxCuota,monto,fk_idContacto) values ("+idFinal+",'"+ramo+"',"+tipoDoc+","+poliza+","+item+","+company+",'"+formater.format(fecha_vencimiento)+"','"+moneda+"',"+cuotaInicial+","+cuotaMax+","+monto+","+contacto+") ON DUPLICATE KEY UPDATE cuota="+cuotaInicial+", maxCuota="+cuotaMax;//ON DUPLICATE KEY UPDATE cuota=values(cuota), estado=values(estado)
              System.out.println(strSql);
            conexion.conectar();
            conexion.sentencia = conexion.conn.prepareStatement(strSql);
            conexion.sentencia.execute(strSql);
            conexion.desconectar();
       
       
            
        } catch (Exception ex) {
            System.out.println("Error al Nueva Cobranza con max Cuotas");
            System.out.println(ex);
        }
    
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
    
     try{
            String strSql="insert into contactos (rut,dv,nombre, telefonos,direccion) VALUES ("+Integer.parseInt(split[0])+","+Integer.parseInt(split[1])+",'"+nombre.trim()+"','"+telefonos.trim()+"','"+direccion+"') ON DUPLICATE KEY UPDATE  nombre='"+nombre+"', telefonos='"+telefonos+"', direccion='"+direccion+"'";
            conexion.conectar();
            conexion.sentencia = conexion.conn.prepareStatement(strSql);
            conexion.sentencia.execute(strSql);
            conexion.desconectar();
       
            
        } catch (Exception ex) {
            System.out.println("Error AL AGREGAR NUEVO CONTACTO");
            System.out.println(ex);
        }
    }

    public void updatearContacto(){
    
    
    };
  
   

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




public void limpiarPoliza(int numPoliza){
    
    try{
             
            String strSql="DELETE FROM morosos WHERE poliza="+numPoliza;
            conexion.conectar();
            conexion.sentencia = conexion.conn.prepareStatement(strSql);
            conexion.sentencia.execute(strSql);
            conexion.desconectar();
       
            
        } catch (Exception ex) {
            System.out.println("Error al Aliminar Polizas");
            System.out.println(ex);
        }
    
    }

public Double calcularCuotas(Double PrimaTotal, int MaxCuotas) {
    NumberFormat nf= NumberFormat.getInstance();
    nf.setMaximumFractionDigits(2);
    nf.setMinimumFractionDigits(2);
    nf.setRoundingMode(RoundingMode.HALF_UP);
    BigDecimal primaTotal = new BigDecimal(PrimaTotal);
    nf.format(PrimaTotal);
    BigDecimal divisor = new BigDecimal(MaxCuotas);
    BigDecimal mod = primaTotal.remainder(divisor);
    BigDecimal cuociente = primaTotal.divide(divisor, 2, RoundingMode.HALF_UP);
    



 double cuota= Double.parseDouble(nf.format(cuociente).replace(",", ".")); 
        
        return cuota;
    }

    public double calcularUltimaCuota(double primaTotalInt, int cuotaMax) {
        NumberFormat nf= NumberFormat.getInstance();
nf.setMaximumFractionDigits(2);
nf.setMinimumFractionDigits(2);
nf.setRoundingMode(RoundingMode.HALF_UP);
       BigDecimal cuotas= new BigDecimal(this.calcularCuotas(monto, cuotaMax));
BigDecimal primaTotal = new BigDecimal(monto);      
BigDecimal divisor = new BigDecimal(cuotaMax);
BigDecimal modBig = primaTotal.remainder(divisor);
BigDecimal valorTotal=cuotas.multiply(new BigDecimal(cuotaMax));
nf.format(primaTotal);
nf.format(valorTotal);
BigDecimal ultimaCuota=primaTotal.subtract(valorTotal);


double ultima= Double.parseDouble(nf.format(ultimaCuota).replace(",", ".")); 
        ultima=ultima+Double.parseDouble(nf.format(cuotas).replace(",", "."));
        return ultima;
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

        Object [] fila=new Object [15];
ImageIcon icon= new ImageIcon("");
           try{
          
          //debe haber un sql que haga todo de aca, pero me da paja buscarlo
            String sql="SELECT a.id,a.ramo,b.nombre,a.poliza,a.item,a.tipoDoc,a.cuota,a.maxCuota,a.monto,a.moneda,a.fecha_vencimiento,a.company,a.estado,a.obs,a.transferencia FROM caracola.cobranzas a, caracola.contactos b where a.fk_idContacto=b.rut order by fecha_vencimiento ASC";
            conexion.conectar();
            conexion.sentencia = conexion.conn.prepareStatement(sql);
            ResultSet objSet=conexion.sentencia.executeQuery(sql);
           int contador=0;
                   while(objSet.next()){
                for (int i = 0; i < 15; i++) {
                    fila[i]=objSet.getObject(i+1);
                    
                    switch(i){
                        case 10:
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
                        case 5://tipo documento
                            switch((int)fila[i]){
                                case 0:fila[i]="EMISIÓN";break;
                                case 1:fila[i]="MODIFICACIÓN";break;
                                case 2:fila[i]="REHABILITACIÓN";break;
                            }
                            ;break;
                    //    case 11:
                    //        switch((int)fila[i]){
                   //             case 0:fila[i]="HDI";break;
                   //             case 1:fila[i]="Liberty";break;
                   //             case 2:fila[i]="SURA";break;
                  //          } ;break;
                        case 12:
              
                            switch((int) fila[i]){
                                case 0:fila[i]="Pendiente";break;
                                case 1:fila[i]="Cobrado";break;
                                case 2:fila[i]="Ingresado";break;
                            }
                            ;break;
                        case 14://se inserta el icono de la imagen      
                            if(fila[i]==null){
                          icon= new ImageIcon("C:\\madremia\\PRogramaLiberty\\descarga\\no-imagen.png");
                            }else{
                             icon= new ImageIcon("C:\\madremia\\PRogramaLiberty\\descarga\\yes-imagen.png"); 
                            }
                            ;break;   
            
                            
                    }
                    
                  
                 
                   
                   
                 
                }
                   modelo.addRow(fila);
                  modelo.setValueAt(icon, contador, 14);
                  contador++;
                   }
            
           } catch (SQLException ex) {
               System.out.println("Error al buscar Asegurado");
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

    public Object[] buscarPorId(long idCobranza) {
   Object [] fila=new Object [17];
           try{
          
          //debe haber un sql que haga todo de aca, pero me da paja buscarlo
            String sql="SELECT a.id,a.ramo,b.nombre,b.rut,b.dv,b.direccion,b.telefonos,a.poliza,a.item,a.tipoDoc,a.cuota,a.maxCuota,a.monto,a.moneda,a.fecha_vencimiento,a.company,a.estado,a.obs FROM caracola.cobranzas a, caracola.contactos b where a.id="+idCobranza+" and b.rut=a.fk_idContacto";
            conexion.conectar();
            conexion.sentencia = conexion.conn.prepareStatement(sql);
            ResultSet objSet=conexion.sentencia.executeQuery(sql);
            
                   while(objSet.next()){
                for (int i = 0; i < 17; i++) {
                    fila[i]=objSet.getObject(i+1);
                     if(i==10){ }}
                   }
    
    
           }catch(SQLException ex){
            System.out.println("Error consultar por id"+ex);
           }
           
return fila;
}

    public String consultarFechaInicioPorId(int idCobranza) {
;
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

 
    
   


}











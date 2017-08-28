/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package metodos;


import conexion.conexion;
import java.sql.*;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;



/**
 *
 * @author pilina
 */
public class consultas {
    public static String rut="";
    public static String items[];
    public static String meses[]=new String [12];
    public static String mes[]=new String [4];
    public boolean vencido;
    public static Calendar fecha=Calendar.getInstance();
  
    
    public  static int getRowCount(ResultSet set) throws SQLException  
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


    

 public void compararPersistir(){
 conexion.conectar();
 try{
     String str1="insert into cobranzas (select * from listadonuevo a Where Not exists (select * from cobranzas b where b.poliza= a.poliza and b.item=a.item));";
     String str2="delete from cobranzas where (select * from listadonuevo a Where Not exists(select * from cobranzas b where b.poliza= a.poliza and b.item=a.item))";
           conexion.sentencia=conexion.conn.prepareStatement(str1);
           conexion.sentencia.execute(str1);
           conexion.sentencia=conexion.conn.prepareStatement(str2);
           conexion.sentencia.execute(str2);
 
 
 } catch(Exception ex){
     System.out.println("eror:"+ex);    
 }
 }  
    
    
    public String arreglarFecha(String fecha){
                 
            String año=""+fecha.charAt(6)+fecha.charAt(7)+fecha.charAt(8)+fecha.charAt(9);
        String mes=""+fecha.charAt(3)+fecha.charAt(4);
        String dia=""+fecha.charAt(0)+fecha.charAt(1);
        fecha=año+"-"+mes+"-"+dia;
       return fecha;
        }
        
public int buscarPorId(int id){

 try{
           conexion.conectar();
           String strSql="select * from tablacobranza where id='"+id+"'";
           conexion.sentencia=conexion.conn.prepareStatement(strSql);
           ResultSet objSet=conexion.sentencia.executeQuery(strSql);
         
           conexion.desconectar();
           
       } catch (Exception ex) {
           System.out.println("Error al buscar: "+ex);
       }
 
 return id;
}        


public int buscarPorPolizaItem(int poliza, Double item){
int id=0;
 try{
       
           String strSql="select * from listadonuevo a Where Not exists (select * from cobranzas b where b.poliza= a.poliza and b.item=a.item)";
           conexion.sentencia=conexion.conn.prepareStatement(strSql);
           ResultSet objSet=conexion.sentencia.executeQuery(strSql);
           for(int i=0;i<consultas.getRowCount(objSet);i++){
            
              Object fila[]=(Object[])objSet.getObject(i+1);
              
           
           }

    
            conexion.desconectar();
       } catch (Exception ex) {
           System.out.println("Error al buscar la fila: "+ex);
       }

 return id;
}        
 

public void pilinesca(){
String sql="SELECT * from listanuevos where poliza,item distinct ";
}

public void insertarCambios(String valorNuevo, int id){
       try{
            String strSql="update cobranzas SET estado='"+valorNuevo+"' where id = "+id;
            conexion.conectar();
            conexion.sentencia = conexion.conn.prepareStatement(strSql);
            conexion.sentencia.execute(strSql);
            conexion.desconectar();
            System.out.println("Registro Actualizado");
            
        } catch (Exception ex) {
            System.out.println("Error al insertar");
            System.out.println(ex);
        }
}
    
    public double arreglarDouble(String strDecimal){
        
               double decimal=0;
               if(strDecimal.length()>7){
                   for (int i = 0; i < strDecimal.length(); i++) {
                       if(strDecimal.charAt(i)=='.'){
                       StringBuilder sb = new StringBuilder(strDecimal);
                       sb.deleteCharAt(i);
                       strDecimal=sb.toString();
                       strDecimal=strDecimal.replace(',', '.');
                          decimal=Double.parseDouble(strDecimal);
                           
                       }
                   }                 
                   
               }else{
               strDecimal=strDecimal.replace(',', '.');
               decimal=Double.parseDouble(strDecimal);
        
               }        
        
        return decimal;
    }
    
    
    public static String fechaActual(){
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
         Calendar cal = Calendar.getInstance();
	String fecha=dateFormat.format(cal.getTime());
        
        return fecha;
    }
    
   
    public int mesActual(){
        Calendar x = Calendar.getInstance();
        x.roll(x.MONTH, true);
        int mesActual = x.get(x.MONTH);
        return mesActual;
    }
    public static String constularNombreMes(int numeroMes){
        String nombreMes;
        if(numeroMes>=13){
            numeroMes=numeroMes-12;
            
        }
        
        switch (numeroMes) {
            case 0:nombreMes="Enero";break;
            case 1:nombreMes="Febrero";break;
            case 2:nombreMes="Marzo";break;
            case 3:nombreMes="Abril";break;
            case 4:nombreMes="Mayo";break;
            case 5:nombreMes="Junio";break;
            case 6:nombreMes="Julio";break;
            case 7:nombreMes="Agosto";break;
            case 8:nombreMes="Septiembre";break;
            case 9:nombreMes="Octubre";break;
            case 10:nombreMes="Noviembre"; break;    
            case 11:nombreMes="Diciembre";break;
               
            default:
                throw new AssertionError();
        }
        return nombreMes;
    }
    
    public TableRowSorter agregarfiltros(DefaultTableModel modelo, java.sql.Date fecha_inicial, java.sql.Date fecha_final){
        TableRowSorter filtrador = new  TableRowSorter(modelo);
        List<RowFilter<Object,Object>> listaFiltro = new ArrayList<RowFilter<Object,Object>>(2);
        listaFiltro.add(RowFilter.dateFilter(RowFilter.ComparisonType.AFTER, fecha_inicial));
        listaFiltro.add(RowFilter.dateFilter(RowFilter.ComparisonType.BEFORE, fecha_final));
        RowFilter filtro= RowFilter.andFilter(listaFiltro);
        filtrador.setRowFilter(filtro); 
        return filtrador;
    }
    
     public TableRowSorter agregarfiltrosconOR(DefaultTableModel modelo, java.sql.Date fecha_inicial, java.sql.Date fecha_final){
        TableRowSorter filtrador = new  TableRowSorter(modelo);
        List<RowFilter<Object,Object>> listaFiltro = new ArrayList<RowFilter<Object,Object>>(2);
        listaFiltro.add(RowFilter.dateFilter(RowFilter.ComparisonType.AFTER, fecha_inicial));
        listaFiltro.add(RowFilter.dateFilter(RowFilter.ComparisonType.EQUAL, fecha_inicial));
        RowFilter<Object,Object> despueseIgual = RowFilter.orFilter(listaFiltro);
        List<RowFilter<Object,Object>> listaFiltro2 = new ArrayList<RowFilter<Object,Object>>(2);
        listaFiltro2.add(RowFilter.dateFilter(RowFilter.ComparisonType.BEFORE, fecha_final));    
        listaFiltro2.add(RowFilter.dateFilter(RowFilter.ComparisonType.EQUAL, fecha_final));   
        RowFilter<Object,Object> anteseIgual = RowFilter.orFilter(listaFiltro2);
        List<RowFilter<Object,Object>> listaFiltro3 = new ArrayList<RowFilter<Object,Object>>(2);
        listaFiltro3.add(despueseIgual);
        listaFiltro3.add(anteseIgual); 
        RowFilter filtro= RowFilter.andFilter(listaFiltro3);
        filtrador.setRowFilter(filtro); 
       //se supone que toma 2 listas de or y los junta en un and
        return filtrador;
    }
     
    
    
   
    public static void main(String[] args) {
    
    }
    
    
    
    
   public void buscarIdPorPolizaEItem(int numeroPoliza, int item){
       //le debe llegar la fila del libro descargado nuevo, 
       //se hace la consulta sobre el numero de poliza y el tiem es igual alguno de ahi
       //select * from morosos where numPoliza= num poliza && item=item;
       //si es igual se revisa si hay diferencias (item, cuota,f.vencimiento)
       //y se actualiza en la base de datos con el mismo id
       //si hay alguno nuevo: se agrega completo en la base de datos
       //si falta alguno, se cambia el estado como al dia
       //
       
       //select ** from nuevo where not exist WHERE NOT EXISTS (
       //    SELECT name FROM table_listnames WHERE name = 'Rupert'
      //            ) LIMIT 1;
    Object  fila[];
    String str_listadoNuevo="INSERT INTO morosos SELECT * FROM (SELECT *) AS tmp WHERE NOT EXISTS (SELECT poliza,item FROM listadonuevo WHERE poliza ="+2+" && item="+1+")";
try{
      Class.forName("com.mysql.jdbc.Driver");
            com.mysql.jdbc.Connection con = (com.mysql.jdbc.Connection) DriverManager.getConnection("jdbc:mysql://localhost/liberty","root","");
            con.setAutoCommit(false);
            com.mysql.jdbc.PreparedStatement pstm = null ;
            pstm = (com.mysql.jdbc.PreparedStatement) con.prepareStatement(str_listadoNuevo);
            pstm.execute();
    
      
   }catch(Exception ex){
    System.out.println("error:"+ex);
   }
     
   }
    
}

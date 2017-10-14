/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaz;

import conexion.conexion;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import metodos.Scratcher;
import metodos.consultor;
import org.joda.time.LocalDate;
import org.openqa.selenium.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;



/**
 *
 * @author Nomad
 */
public class NewClass1 {

    public static void invoke(){
        Scratcher t = new Scratcher();
              consultor x = new consultor();
            System.setProperty("webdriver.chrome.driver", "C:\\madremia\\PRogramaLiberty\\descarga\\chromedriver.exe");
        WebDriver driver;
          driver = new ChromeDriver();
            driver.get("https://www.hdi.cl/zona-corredores/");
            //   driver.getTitle();
            driver.findElement(By.id("usuario")).sendKeys("2639");
            //   Thread.sleep(3000);//element[@someAttribute]
            driver.findElement(By.id("clave")).sendKeys("05042017");
            //  Thread.sleep(3000);
            driver.findElement(By.id("ContentPlaceHolderDefault_ContenidoHome_pbtn")).click();
        
         List<String> listado_ruts =x.buscarTodosLosRuts();
         
         String rut="";
         //se debe dividir en  partes de 100 para evitar que colapse el navegador
         //se agrega la cantidad de filas totales
         int cantidad_ruts=listado_ruts.size();
         int k=cantidad_ruts/200;//cantidad de partesd
        
         
         for (int i = 0; i < k; i++) {
            
        }
         
         int cont=0;//listado_ruts.size()
         System.out.println("Cantidad de Ruts"+listado_ruts.size());
         for (int y = 1; y < listado_ruts.size(); y++) {
             
             if(y==0){
             
             };
             cont++;
             rut=listado_ruts.get(y);
    
             String line = System.getProperty("line.separator");
             driver.switchTo().frame("topFrame");
             try{
                 
                 driver.findElement(By.xpath("/html/body/div[1]/table/tbody/tr[2]/td/div/div[2]/ul/li[1]/a")).click();
                 driver.switchTo().defaultContent();
                 driver.switchTo().frame("mainFrame");
                 driver.switchTo().frame("aTrabajo");
                 driver.findElement(By.xpath("/html/body/form/div/div[2]/table/tbody/tr[1]/td[2]/input")).sendKeys(rut);
                 driver.findElement(By.xpath("/html/body/form/div/div[2]/table/tbody/tr[4]/td[2]/input")).click(); //boton ver cliente
                 
                 try{//se verifica si tiene polizas, sino tiene se aprieta un back y siguiente rut
                     
                     ///html/body/div/table/tbody/tr[1]/td/h3
                     driver.findElement(By.xpath("/html/body/div[1]/table/tbody/tr[2]/td/table/tbody/tr[2]/td[1]/font/a")).click();// Se clickea el cliente
                     //se cambia a panel izquierdo para mostrar polizas del clientes
                     //Se buscan los datos mostrados
                 
                     String nombre_contacto=driver.findElement(By.xpath("//html/body/div/table/tbody/tr/td/table/tbody/tr[2]/td[3]")).getText();
                     String direccion_contacto=driver.findElement(By.xpath("/html/body/div/table/tbody/tr/td/table/tbody/tr[3]/td[3]")).getText();
                     String telefono_contacto=driver.findElement(By.xpath("/html/body/div/table/tbody/tr/td/table/tbody/tr[8]/td[3]")).getText();
                     String correo_contacto=driver.findElement(By.xpath("/html/body/div/table/tbody/tr/td/table/tbody/tr[9]/td[3]/a")).getText();
                     String dv=driver.findElement(By.xpath("/html/body/div/table/tbody/tr/td/table/tbody/tr[1]/td[3]")).getText();
                     
                     System.out.println(nombre_contacto);
                     System.out.println(rut+"-"+dv);
                     System.out.println(direccion_contacto);
                     System.out.println(telefono_contacto);
                     System.out.println(correo_contacto);
                     driver.switchTo().defaultContent();
               
                    
                     
                    driver.switchTo().frame("mainFrame");
                     driver.switchTo().frame("leftFrame");
                     driver.findElement(By.xpath("//*[@id=\"MuestraCliente_Polizas\"]")).click();//muestra polizas del clientes
                     //se cambia a la tabla donde muestra las polizas
                     
                     
                     //no encuentra poliza enotnces
                     try{
                     
                     
                     
                     }catch(NoSuchElementException er){
                         System.out.println("No hay Polizas"+er);
                         driver.navigate().back();
                         driver.navigate().back();
                     }
                     driver.switchTo().defaultContent();
                     driver.switchTo().frame("mainFrame");
                     driver.switchTo().frame("aTrabajo");
                     
                     //------------------------POLIZAS
                     
                     //total de polzias
                     StringTokenizer tokenizer = new StringTokenizer(driver.findElement(By.xpath("/html/body/div/table/tbody/tr[2]/td/table")).getText(), line);
                     int cantidad_de_polizas=  tokenizer.countTokens()-2;
                     System.out.println("N°"+cont+"  ///  Rut:"+rut+" Cantidad de Poolizass:"+cantidad_de_polizas);
                     for (int u = 2; u <= (cantidad_de_polizas+1); u++) {
                         System.out.println("Poliza N°"+(u-1)+" del Rut"+rut);
                         driver.switchTo().defaultContent();
                         driver.switchTo().frame("mainFrame");
                         driver.switchTo().frame("aTrabajo");// /html/body/div/table/tbody/tr[2]/td/table/tbody/tr[2]/td[3]/a
                         driver.findElement(By.xpath("/html/body/div/table/tbody/tr[2]/td/table/tbody/tr["+u+"]/td[3]/a")).click();//entrada poliza segun iteracion
                         driver.switchTo().defaultContent();
                         driver.switchTo().frame("mainFrame");//*[@id="MuestraCliente_EstadoPago"]
                         driver.switchTo().frame("leftFrame");
                         driver.findElement(By.xpath("//*[@id=\"Poliza_EstadoPago\"] ")).click();
                         driver.switchTo().defaultContent();
                driver.switchTo().frame("mainFrame");
                driver.switchTo().frame("aTrabajo");
                //-----------------------------CUOTASSS
                
                StringTokenizer tokenizer2 = new StringTokenizer( driver.findElement(By.xpath("/html/body/div/table/tbody/tr/td/table/tbody/tr[2]/td/table/tbody")).getText(), line);
               int cantidad_de_cuotas=tokenizer2.countTokens()-3;
                String[] cuotas_poliza= new String[8]; 
                //INsercion de cuootas
                for (int i = 3; i < (cantidad_de_cuotas+3); i++) {//cantidad de couoas
                  
                    for (int j = 1; j <= 8; j++) {//columnas
                        cuotas_poliza[j-1]=driver.findElement(By.xpath("/html/body/div/table/tbody/tr/td/table/tbody/tr[2]/td/table/tbody/tr["+i+"]/td["+j+"]")).getText();                                        //       "/html/body/div/table/tbody/tr/td/table/tbody/tr[2]/td/table/tbody/tr[11]/td[1]"
                        if(j==2){
                            if(cuotas_poliza[j-1].trim().equals("Emisión")){
                                cuotas_poliza[j-1]="0";
                            }else{
                                if(cuotas_poliza[j-1].trim().equals("Modificación")){
                                    cuotas_poliza[j-1]="1";
                                }else{
                                    if(cuotas_poliza[j-1].trim().equals("Rehabilitación")){
                                        cuotas_poliza[j-1]="2";
                                    }else{
                                           cuotas_poliza[j-1]="3";
                                    }
                                }
                            }
                        }
                        if(j==7){
                            if(cuotas_poliza[j-1].trim().equals("Cobrado")){
                                cuotas_poliza[j-1]="1";
                            }else{
                                if(cuotas_poliza[j-1].trim().equals("Pendiente")){
                                    cuotas_poliza[j-1]="0";
                                }else{
                                    if(cuotas_poliza[j-1].trim().equals("Ingresado")){
                                        cuotas_poliza[j-1]="2";
                                    }else{
                                        cuotas_poliza[j-1]="3";
                                    }
                                }
                            }
                            
                        }
                        if(j==4){//ser verifica que la cuota sea R
                            if(cuotas_poliza[j-1].trim().equals("R")){
                                cuotas_poliza[j-1]=String.valueOf(j+1);
                            }
                            
                        }
                    }
                    
                    SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");
                    String moneda=cuotas_poliza[5].trim().split("\\s+")[0];
                    String valor_cuota=cuotas_poliza[5].trim().split("\\s+")[1];
                    String fecha_de_vencimiento_prueba=cuotas_poliza[4].trim().replace("-", "/");
                    String id_final=String.valueOf(cuotas_poliza[2].split("-")[0].trim())+String.valueOf(cuotas_poliza[2].split("-")[1].trim())+cuotas_poliza[3].trim()+cuotas_poliza[1].trim()+String.valueOf(0);//solo hdi
                    id_final=id_final.trim();//se crea la id unica compuesta por poliza,item,cuota,tipoDoc,compañia
                    
                    java.util.Date face_vencimiento_util= new java.util.Date();
                             try {
                                 face_vencimiento_util = formater.parse(fecha_de_vencimiento_prueba);
                             } catch (ParseException ex) {
                                 Logger.getLogger(NewClass1.class.getName()).log(Level.SEVERE, null, ex);
                             }
                    java.sql.Date fecha_vencimiento_sql = new java.sql.Date(face_vencimiento_util.getTime());
                    try{
                        
                        String strSql="insert into cobranzas (id,ramo,tipoDoc,poliza,item,company,fecha_vencimiento,moneda,cuota,maxCuota,monto,fk_idContacto) values ("+id_final+",'"+cuotas_poliza[0].trim()+"',"+cuotas_poliza[1].trim()+","+cuotas_poliza[2].trim().split("-")[0]+","+cuotas_poliza[2].trim().split("-")[1]+","+0+",'"+fecha_vencimiento_sql+"','"+moneda+"',"+cuotas_poliza[3].trim()+","+cantidad_de_cuotas+","+Double.parseDouble(valor_cuota.replace(",", "."))+","+rut+") ON DUPLICATE KEY UPDATE cuota="+cuotas_poliza[3].trim()+",estado="+cuotas_poliza[6]+", maxCuota="+(cantidad_de_cuotas)+", fecha_vencimiento='"+fecha_vencimiento_sql+"'";//ON DUPLICATE KEY UPDATE cuota=values(cuota), estado=values(estado)
                        conexion.conectar();
                        conexion.sentencia = conexion.conn.prepareStatement(strSql);
                        conexion.sentencia.execute(strSql);
                        conexion.desconectar();
                        
                    } catch (SQLException ex) {
                        System.out.println("Error insertarCobranza por pagina web hdi SQL :"+ex);
                        
                    }
                    
                    
                }
                
                
                driver.navigate().back();
                driver.navigate().back();
                
                
            }
            
            
            
           }catch (NoSuchElementException|NoSuchFrameException ex) {
                   //sino tiene polizas entonces back back
                     System.out.println("No hay Polizas"+ex);
                   driver.navigate().back();
                   driver.navigate().back();
           }
           
        }catch(NoSuchFrameException|NoSuchElementException ex){System.out.println("Error general"+ex);}
             
        }//Metodo para b
        
        
        driver.quit();
    
    }
    
    public static void main(String[] args) {
        long start = System.nanoTime();    
       invoke();
long elapsedTime = System.nanoTime() - start;
        System.out.println("Programa Finalizado Con exito en"+elapsedTime);
 
   
    }
}
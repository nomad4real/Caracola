/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metodos;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Nomad
 */
 public class CustomCellRenderer extends DefaultTableCellRenderer {

  /* (non-Javadoc)
   * @see javax.swing.table.DefaultTableCellRenderer#getTableCellRendererComponent(javax.swing.JTable, java.lang.Object, boolean, boolean, int, int)
   */
  public Component getTableCellRendererComponent(JTable table, Object value,
    boolean isSelected, boolean hasFocus, int row, int column) {

   Component rendererComp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus,
     row, column);

   //Set foreground color
   rendererComp.setForeground(Color.red);

   //Set background color
   rendererComp .setBackground(Color.blue);

   return rendererComp ;
  }

 }
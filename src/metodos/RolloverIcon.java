/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metodos;

/**
 *
 * @author Nomad
 */
import java.awt.*;
import javax.swing.*;
/**
 * this class was created by two ibm authors.
 * @see http://www.ibm.com/developerworks/web/library/us-j2d/
 */
public class RolloverIcon implements Icon
{
  protected Icon icon;

  public RolloverIcon(Icon icon)
  {
    this.icon = icon;
  }

  public int getIconHeight()
  {
    return icon.getIconHeight();
  }

  public int getIconWidth()
  {
    return icon.getIconWidth();
  }

  public void paintIcon(Component c, Graphics g, int x, int y)
  {
    Graphics2D graphics2d = (Graphics2D) g;
    Composite oldComposite = graphics2d.getComposite();
    graphics2d.setComposite(RolloverComposite.DEFAULT);
    icon.paintIcon(c, g, x, y);
    graphics2d.setComposite(oldComposite);
  }
}
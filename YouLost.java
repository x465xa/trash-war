import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.event.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * You Lost Screen GUI
 * 
 * @author Vaishnavi Kunapuli
 * @version 04/29/2022
 * @author Sources - Meenakshi, Anne
 */

public class YouLost extends Draw{

    public static void main(String[] args) throws Exception{
        new YouLost();
    }

    public YouLost() throws Exception {
        design();

    }

   /**
    * Design for the YouLost Screen
 * @throws Exception
    */
    public void design() throws Exception{
        JFrame frame = new JFrame();
        frame.setSize(400, 300);
        frame.setLocation(450, 100);
        Image myPicture = ImageIO.read(new File("youlost.jpg"));
        myPicture = getScaledImage(myPicture, 400, 300);
        JLabel l = new JLabel(new ImageIcon(myPicture));
        JPanel panel = new JPanel();
        panel.add(l);
        frame.add(panel);
        frame.setVisible(true);



    }

    private static Image getScaledImage(Image srcImg, int w, int h) {
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resizedImg.createGraphics();

        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg, 0, 0, w, h, null);
        g2.dispose();

        return resizedImg;
    }
    
}

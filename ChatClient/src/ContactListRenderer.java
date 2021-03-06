import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.Random;

import javax.swing.DefaultListSelectionModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

class ContactListRenderer extends JLabel implements ListCellRenderer<String> {
	
     public ContactListRenderer() {
         setOpaque(true);
     }

     public Component getListCellRendererComponent(JList<? extends String> list,
                                                   String value,
                                                   int index,
                                                   boolean isSelected,
                                                   boolean cellHasFocus) {

        
         
         
    	 setText(value.toString());
         if(value.toString().contains("_")) {
        	 
        	 setText(value.toString().substring(0, value.toString().length()-1));
         }
         
         
         
                  
               
         
         setFont(new Font("Verdana", Font.PLAIN, 20));
         
         if (isSelected) {
            
        	 setBackground(list.getBackground());
        	 setFont(new Font("Verdana", Font.PLAIN, 26));
         }
         if (!isSelected) {
             setBackground(list.getBackground());
             setFont(new Font("Verdana", Font.PLAIN, 20));
         }
         
         
         if(cellHasFocus) {
        	 setBackground(list.getBackground());
         }
         
         if(!cellHasFocus) {
        	 setBackground(list.getBackground());
        	 
         }
         
         
         
         
         
         
         
         
         ///////// Oznaczanie aktywnych uzytkownikow
         if(value.contains("_")) setBackground(new Color(170,220,170));
         
         
         
        setHorizontalAlignment(JLabel.CENTER);

         
         
         return this;
     }
 }
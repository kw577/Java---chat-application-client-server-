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
         
         
         
         
         
         
         // TEST
         Random r = new Random();
         int fontSize = r.nextInt(10) + 10;

         
         //
       
         
         setFont(new Font("Verdana", Font.PLAIN, fontSize));
         
         if (isSelected) {
            
             
         }
         if (!isSelected) {
             setBackground(list.getBackground());
             
         }
         
         
         if(cellHasFocus) {
        	 setBackground(Color.green);
         }
         
         if(!cellHasFocus) {
        	 setBackground(list.getBackground());
        	 
         }
         
         
         
         if( value.equalsIgnoreCase("Kuba"))  setBackground(Color.yellow);
         
         
         
         
         ///////// Oznaczanie aktywnych uzytkownikow
         if(value.contains("_")) setBackground(Color.cyan);
         
         
         
         if( value.equalsIgnoreCase("Kuba"))  setHorizontalAlignment(JLabel.CENTER);

         
         
         return this;
     }
 }
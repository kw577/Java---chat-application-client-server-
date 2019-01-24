import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.Random;

import javax.swing.DefaultListSelectionModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

class MessageListRenderer extends JLabel implements ListCellRenderer<String> {
	
     public MessageListRenderer() {
         setOpaque(true);
     }

     public Component getListCellRendererComponent(JList<? extends String> list,
                                                   String value,
                                                   int index,
                                                   boolean isSelected,
                                                   boolean cellHasFocus) {

        
         
         
    	 setText(value.toString());
    	 
    	 
    	
         
         
                  
               
         
         setFont(new Font("Verdana", Font.PLAIN, 14));
         
         if (isSelected) {
            
        	 setBackground(list.getBackground());
        	 
         }
         if (!isSelected) {
             setBackground(list.getBackground());
             
         }
         
         
         if(cellHasFocus) {
        	 setBackground(list.getBackground());
         }
         
         if(!cellHasFocus) {
        	 setBackground(list.getBackground());
        	 
         }
         
         
         
         
    	 // rozroznianie komunikatow
         if(value.toString().substring(0, 4).equals("You:")) {
        	 
        	 setBackground(new Color(200, 220, 240));
         } else {
        	 setBackground(new Color(255, 255, 220));
         }
         
         
         
         
         
         
        
         
         
        setHorizontalAlignment(JLabel.LEFT);

         
         
         return this;
     }
 }
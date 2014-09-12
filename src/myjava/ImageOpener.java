package myjava;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
public class ImageOpener {
	
	  public void RunMe(String location) {
	    if (location != null) {
	    	if (!location.trim().isEmpty()) {
	    		File f = new File(location);
			   if (f.exists()) {
				   Desktop dt = Desktop.getDesktop();
				    try {
						dt.open(f);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
			}	    			    
		}	   		  	    
	 }
	 	 
}

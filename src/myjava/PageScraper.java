package myjava;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class PageScraper {
	
	public String ParseURL (String urlText)
	{
		String result="";
		URL url;
		   URLConnection uc;
		   StringBuilder parsedContentFromUrl = new StringBuilder();
		   //String urlString="http://www.springerlink.com/content/w2e4dhy3kxya1v0d/";
		   System.out.println("Getting content for URl : " + urlText);
		   try {
			url = new URL(urlText);
			uc = url.openConnection();
			uc.addRequestProperty("User-Agent", 
			        "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
			   uc.connect();
			   uc.getInputStream();
			   //DataInputStream in = new DataInputStream(uc.getInputStream());
			   BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
			   String ch;
			   
			   while ((ch = in.readLine()) != null) {
			       parsedContentFromUrl.append(ch);
			   }
		   	}    
		
		   
		  catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		   
		   String html = parsedContentFromUrl.toString();
		// replace all white spaces region with single space  
		   html = html.replaceAll("\\s+", " ");  
		      // build the pattern using regular expression  
		      Pattern p = Pattern.compile("<span class="+'"'+"rank"+'"'+">(.*?)</span>");  
		      // Match the pattern with given html source  
		      Matcher m = p.matcher(html);  
		      // Get all matches that matched my pattern  
		      if (m.find() == true){  
		        // Print the first matched pattern  
		       result = m.group(1).replace('#', ' ').trim();  
		      }  
		      return result;
		   //System.out.println(parsedContentFromUrl);
		//return parsedContentFromUrl.toString();
	}
	
}

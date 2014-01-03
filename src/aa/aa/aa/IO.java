package aa.aa.aa;

import java.io.File;
import java.io.FileOutputStream;
import android.os.Environment;

public class IO 
{	
	public void saveFileToSDCard(String filename, String content) throws Exception  
    {
    	  Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    	  File file = new File("/sdcard", filename);
    	  //File file = Environment.getExternalStorageDirectory();
    	  FileOutputStream fos = new FileOutputStream(file,true);
    	  fos.write(content.getBytes());
    	  fos.close();	 
    	  String tmp = "\n";
    	  FileOutputStream fos1 = new FileOutputStream(file,true);
    	  fos1.write(tmp.getBytes());
    	  fos1.close();	
    }	
}

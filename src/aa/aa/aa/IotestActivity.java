package aa.aa.aa;

import java.io.FileNotFoundException;
import java.io.IOException;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class IotestActivity extends Activity 
{
    /** Called when the activity is first created. */
	private Button ms,tp,sp,ap;
	private ImageView imageView;	
	private TextView view_result,view_mode;
	private Bitmap bitmap;
	private int modness;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        findview();
        modness=0;
        view_mode.setText("cal mode");
        
        ms.setOnClickListener(new OnClickListener(){
        	   public void onClick(View v) {
            	   if(modness==1){
            		  modness=0;
                      view_mode.setText("cal mode");
            	       }
            	   else{
            		modness=1;
            		view_mode.setText("test mode");
            	      }
        	}
        });
        tp.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	Intent takepicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            	startActivity(takepicture);
            }
    });
        sp.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
               	Intent selectpicture = new Intent();
		        selectpicture.setType("image/*");
		        selectpicture.setAction(Intent.ACTION_GET_CONTENT); 
		        startActivityForResult(selectpicture, 1);
            }
         });
        ap.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	//view_result.setText("Here");
                try {
					analyse();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        });
    }
    
    //exit button in the menu
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
    	menu.add(0, 1, 1, R.string.exit);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
    	if(item.getItemId()==1){
    		finish();
    	}
		return super.onOptionsItemSelected(item);
	}

	//show the picture on the screen
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			Uri uri = data.getData();
			ContentResolver cr = this.getContentResolver();
			try {
				bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
				int bitmapWidth = bitmap.getWidth();
				int width = 100;
				int height =100;
				int bitmapHeight = bitmap.getHeight();
				float scaleWidth = (float) width / (float)bitmapWidth;
				float scaleHeight = (float) height / (float)bitmapHeight;
				Matrix matrix = new Matrix();
				matrix.postScale(scaleWidth, scaleHeight);
				Bitmap resizeBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmapWidth, bitmapHeight, matrix, false);		
				imageView.setImageBitmap(resizeBitmap);
			} catch (FileNotFoundException e)
			{
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
    
    public void findview()
    {
    	ms=(Button) findViewById(R.id.ms);
    	tp=(Button) findViewById(R.id.tp);
    	sp=(Button) findViewById(R.id.sp);	
    	ap=(Button) findViewById(R.id.ap);
    	imageView=(ImageView) findViewById(R.id.imagePreview);
    	view_result=(TextView)findViewById(R.id.result);
    	view_mode=(TextView)findViewById(R.id.mode);   	    	
    }
    public void analyse() throws IOException
    {
        if(bitmap!=null)
        {
        	int width =100;
        	int height =100;
        	int bitmapWidth = bitmap.getWidth();
			int bitmapHeight = bitmap.getHeight();
			float scaleWidth = (float) width / (float)bitmapWidth;
			float scaleHeight = (float) height / (float)bitmapHeight;
			Matrix matrix = new Matrix();
			matrix.postScale(scaleWidth, scaleHeight);
			Bitmap resizeBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmapWidth, bitmapHeight, matrix, false);		
			
	     	int r,g,b,mycolor;
	     	int []pixvalue;
	         pixvalue=new int[width*height];
	        int flag=0;
	     	for(int j=0;j!=height;++j)
	     	{
	     		for(int i=0;i!=width;++i)
	     		{
		     		mycolor=resizeBitmap.getPixel(i, j);
		     		r=((mycolor & 0x00FF0000) >> 16); 
		     		g=((mycolor & 0x0000FF00) >> 8); 
		     		b=((mycolor & 0x000000FF) ); 
		     		pixvalue[flag++]=(30*r+59*g+11*b);   
		     		if(flag>width*height)
		     		{
		     			view_result.setText("The picture is too large");
		     		}		
	     		}
	     	}
	     	//view_result.setText("Here"); 
	     	lbp lbp888=new lbp();
	     	lbp888.grid(pixvalue,width,height);
	     	//view_result.setText(Double.toString(pixvalue[20]));
        }
        else
        {
        	view_result.setText("No picture");
        }
    }
}
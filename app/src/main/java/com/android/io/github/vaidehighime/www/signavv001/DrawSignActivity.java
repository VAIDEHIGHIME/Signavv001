package com.android.io.github.vaidehighime.www.signavv001;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class DrawSignActivity extends AppCompatActivity {
    private CanvasView canvasView;

  //  @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
 //   @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
 //   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw_sign);
        //CanvasView canvasView=new CanvasView(getApplicationContext(),null, getIntent().getData());
        canvasView= findViewById(R.id.myCanvas);
       // File f = new File(getRealPathFromURI(getIntent().getData()));
       // Drawable d = Drawable.createFromPath(f.getAbsolutePath());
       // canvasView.setBackground(d);
    }

    public void clearCanvas(View v)
    {
        canvasView.clearCanvas();
    }

    public void sendSign(View view)
    {
        StringBuilder gcode=canvasView.ProcessCanvas(getApplicationContext());
        Toast.makeText(getApplicationContext(),"Printing Your Signature:"+gcode,Toast.LENGTH_SHORT).show();
    }

//    private String getRealPathFromURI(Uri contentURI) {
//        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
//        if (cursor == null) { // Source is Dropbox or other similar local file path
//            return contentURI.getPath();
//        } else {
//            cursor.moveToFirst();
//            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
//            return cursor.getString(idx);
//        }
//    }


}

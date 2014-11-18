package test;

import play.apilearn.R;
import utils.BitmapTool;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Demo描述:
 * 往EditText中插入图片
 */
public class EditTextWithImageActivity extends Activity {
 private EditText mEditText;
 private Button mButton;
 
 private ImageView iv;
 @Override
protected void onCreate(Bundle savedInstanceState) {
	 super.onCreate(savedInstanceState);
	 setContentView(R.layout.edit_text_with_image);
	 init();
 }
 private void init(){
	 mEditText=(EditText) findViewById(R.id.editText);
	 mButton=(Button) findViewById(R.id.button);
	 mButton.setOnClickListener(new ClickListenerImpl());
	 
	 iv = (ImageView) findViewById(R.id.image_view);
	 
	 Bitmap src = BitmapFactory.decodeResource(getResources(), R.drawable.banner_default);
	 
	 Bitmap des = BitmapTool.getRoundedCornerBitmap(src);
	 
	 iv.setImageBitmap(des);
	 
	 
 }

 private class ClickListenerImpl implements OnClickListener {
	  @Override
	  public void onClick(View v) {
		  Bitmap bmp = BitmapFactory.decodeResource(getResources(),R.drawable.ic_launcher);
		  ImageSpan imageSpan = new ImageSpan(EditTextWithImageActivity.this, bmp);
		  SpannableString spannableString = new SpannableString("test");
		  spannableString.setSpan(imageSpan, 0, spannableString.length(),SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
		  mEditText.append(spannableString);
		  
		  Toast.makeText(getApplicationContext(), "text: " + mEditText.getText().toString(), Toast.LENGTH_LONG).show();
	  }

 	}

}



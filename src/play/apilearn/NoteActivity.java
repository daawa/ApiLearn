package play.apilearn;

import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NoteActivity extends Activity {

	public static DBAdapter db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction().add(R.id.container, new PlaceholderFragment()).commit();
		}

		db = DBAdapter.getInstance(this);
		db.open();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
			Log.d("FFFFFF", "construct");
		}

		@Override
		public void onCreate(Bundle sa) {
			super.onCreate(sa);
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

			View rootView = inflater.inflate(R.layout.fragment_main, container, false);
			Button btn = (Button) rootView.findViewById(R.id.button_save);
			Button check = (Button) rootView.findViewById(R.id.button_check);
			check.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					Intent it = new Intent(getActivity(), HistoryActivity.class);
					getActivity().startActivity(it);					
				}
				
			});
			
			btn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {

					EditText ed = (EditText) PlaceholderFragment.this.getView().findViewById(R.id.editText_content);
					String content = ed.getText().toString();
					if (content.length() <= 0) {
						Toast.makeText(getActivity(), "content can't be empty", Toast.LENGTH_SHORT).show();
						;
						Intent it = new Intent(getActivity(), HistoryActivity.class);
						getActivity().startActivity(it);
						return;
					}

					String digest = content.substring(0, 10 < content.length() ? 10 : content.length());

					long rowid = NoteActivity.db.insertNote(digest, content);

					Intent it = new Intent(getActivity(), HistoryActivity.class);
					getActivity().startActivity(it);

					// Cursor cursor = db.getTop10Notes();
					// if (cursor.moveToFirst()) {
					// do {
					// DisplayNote(cursor);
					// } while (cursor.moveToNext());
					// }
					//
					// Toast.makeText(getActivity(), "inserted : " + rowid,
					// Toast.LENGTH_LONG).show();

				}
			});

			return rootView;
		}

		protected void DisplayNote(Cursor cursor) {
			Toast.makeText(
					getActivity(),
					"id:" + cursor.getInt(0) + " digest:" + cursor.getString(1) + " content:" + cursor.getString(2)
							+ "  create_datetime:" + cursor.getLong(3) + " update:" + cursor.getLong(4),
					Toast.LENGTH_SHORT).show();

			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss", Locale.CHINA);

			Log.d("DBAdapter:",
					"id:" + cursor.getInt(0) + " digest:" + cursor.getString(1) + " content:" + cursor.getString(2)
							+ "  create_datetime:" + df.format(new Date(cursor.getLong(3))) + " update:"
							+ df.format(new Date(cursor.getLong(4))));
		}

		@Override
		public void onActivityCreated(Bundle savedInstance) {
			super.onActivityCreated(savedInstance);

		}
		
		
		/**
	       * 通过路径获取系统图片
	       * @param uri
	       * @return
	       */
	      private Bitmap getBitmap(Uri uri) {
	       Bitmap pic = null;
	       BitmapFactory.Options op = new BitmapFactory.Options();
	       op.inJustDecodeBounds = true;
	       Display display = getActivity().getWindowManager().getDefaultDisplay();
	       int dw = display.getWidth();
	       int dh = display.getHeight();
	       try {
	        pic = BitmapFactory.decodeStream(getActivity().getContentResolver()
	          .openInputStream(uri), null, op);
	       } catch (FileNotFoundException e) {
	        e.printStackTrace();
	       }
	       int wRatio = (int) Math.ceil(op.outWidth / (float) dw);
	       int hRatio = (int) Math.ceil(op.outHeight / (float) dh);
	       if (wRatio > 1 && hRatio > 1) {
	        op.inSampleSize = wRatio + hRatio;
	       }
	       op.inJustDecodeBounds = false;
	       try {
	        pic = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(uri), null, op);
	       } catch (FileNotFoundException e) {
	        e.printStackTrace();
	       }
	       return pic;
	      }
	      /**
	       * 图片转成SpannableString加到EditText中
	       * 
	       * @param pic
	       * @param uri
	       * @return
	       */
	      private SpannableString getBitmapMime(Bitmap pic, Uri uri) {
//	       int imgWidth = pic.getWidth();
//	       int imgHeight = pic.getHeight();
//	       float scalew = (float) 40 / imgWidth;
//	       float scaleh = (float) 40 / imgHeight;
//	       Matrix mx = new Matrix();
//	       mx.setScale(scalew, scaleh);
//	       pic = Bitmap.createBitmap(pic, 0, 0, imgWidth, imgHeight, mx, true);
	       String smile = uri.getPath();
	       SpannableString ss = new SpannableString(smile);
	       ImageSpan span = new ImageSpan(this.getActivity(), pic);
	       ss.setSpan(span, 0, smile.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
	       return ss;
	      }

	      /**
	       * 这里是重点
	       */
	      private void insertIntoEditText(EditText editText_Content,SpannableString ss) {
	       Editable et = editText_Content.getText();// 先获取Edittext中的内容
	       int start = editText_Content.getSelectionStart();
	       et.insert(start, ss);// 设置ss要添加的位置
	       editText_Content.setText(et);// 把et添加到Edittext中
	         editText_Content.setSelection(start + ss.length());// 设置Edittext中光标在最后面显示
	      }


	}

}

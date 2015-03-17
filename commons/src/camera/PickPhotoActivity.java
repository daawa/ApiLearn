package camera;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import play.apilearn.R;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * take photo or video by intent
 * 
 * @author may
 *
 */

public class PickPhotoActivity extends Activity {
	public static final String TAG = "PickPhotoActivity";

	public static final int MEDIA_TYPE_IMAGE = 1;
	public static final int MEDIA_TYPE_VIDEO = 2;

	static final int REQ_CAPTURE_PICTURE = 101111;
	static final int REQ_CAPTURE_VIDEO = 101112;

	TextView mResultLabel;
	Button mButtonPhoto;
	Button mButtonVideo;

	Uri mImageUri;
	Uri mVideoUri;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pick_photo);

		mResultLabel = (TextView) findViewById(R.id.textview_result);
		mButtonPhoto = (Button) findViewById(R.id.btn_pick_photo);
		mButtonVideo = (Button) findViewById(R.id.btn_pick_video);

		mButtonPhoto.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// mImageUri = Uri.fromFile(new
				// File(Environment.getExternalStorageDirectory(),
				// "image.jpg"));
				mImageUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
				if (mImageUri == null) {
					Log.w(TAG, "mImageUri is null");
				}
				Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				openCameraIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);

				startActivityForResult(openCameraIntent, REQ_CAPTURE_PICTURE);

			}
		});

		mButtonVideo.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				mVideoUri = getOutputMediaFileUri(MEDIA_TYPE_VIDEO);
				Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent.putExtra(MediaStore.EXTRA_OUTPUT, mVideoUri);
				intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1); // set the
																	// video
																	// image
																	// quality
																	// to high

				startActivityForResult(intent, REQ_CAPTURE_VIDEO);

			}
		});

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// int req =
		// getResources().getDimensionPixelSize(R.dimen.record_event_image_size);
		switch (requestCode) {
		case REQ_CAPTURE_PICTURE:
			if (resultCode == RESULT_OK) {
				/**
				 * 拍照和录视屏不一样，穿回来得 data 是空的， 只能通过之前记录的 imageUri 取读取照片
				 * mResultLabel.setText(data.getData().toString());
				 */
				/**
				 * File imageFile = new File(mImageUri.getPath()); File destFile
				 * = new File(Environment.getExternalStorageDirectory(),System.
				 * currentTimeMillis() + ".jpg"); try {
				 * FileUtils.copyFile(imageFile,destFile); } catch (IOException
				 * e) {
				 * 
				 * } addAddedImage(destFile,req);
				 */
				mResultLabel.setText("success, location:" + mImageUri.toString());
			}
			break;
		case REQ_CAPTURE_VIDEO:
			if (resultCode == RESULT_OK) {
				mResultLabel.setText(data.getData().toString());
			}
			break;
		default:
			Log.w(TAG, "NO matched request code!");
		}
	}

	/** Create a file Uri for saving an image or video */
	public static Uri getOutputMediaFileUri(int type) {
		return Uri.fromFile(getOutputMediaFile(type));
	}

	/** Create a File for saving an image or video */
	public static File getOutputMediaFile(int type) {
		// To be safe, you should check that the SDCard is mounted
		// using Environment.getExternalStorageState() before doing this.
		if (!Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
			return null;
		}

		File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
				Environment.DIRECTORY_DCIM), "apiLearn.MyCameraApp");
		// This location works best if you want the created images to be shared
		// between applications and persist after your app has been uninstalled.

		// Create the storage directory if it does not exist
		if (!mediaStorageDir.exists()) {
			if (!mediaStorageDir.mkdirs()) {
				Log.d("MyCameraApp", "failed to create directory");
				return null;
			}
		}

		// Create a media file name
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA);
		String timeStamp = formatter.format(new Date());

		File mediaFile;
		if (type == MEDIA_TYPE_IMAGE) {
			mediaFile = new File(mediaStorageDir.getPath() + File.separator +
					"CAPTURED_IMG_" + timeStamp + ".jpg");
		} else if (type == MEDIA_TYPE_VIDEO) {
			mediaFile = new File(mediaStorageDir.getPath() + File.separator +
					"CAPTURED_VID_" + timeStamp + ".mp4");
		} else {
			return null;
		}

		return mediaFile;
	}

}

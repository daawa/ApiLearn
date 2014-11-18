package camera;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import play.apilearn.R;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class CameraActivity extends Activity {

	public static final String LOG_TAG = "CameraActivity";

	private Camera mCamera;
	private CameraPreview mPreviewSurface;
	private MediaRecorder mMediaRecorder;

	private int mCapturePicOrVideo = 1;
	private boolean isRecording = false;

	Button captureButton;

	float startX;
	float startY;
	
	@Override
	public void onResume(){
		super.onResume();
	}

	@Override
	public void onCreate(Bundle saved) {
		Log.w(LOG_TAG, "CameraActivity onCreate");
		super.onCreate(saved);
		setContentView(R.layout.activity_camera_activity);

		if (!checkCameraHardware(this)) {
			Toast.makeText(this, "No camera feature!", Toast.LENGTH_LONG).show();
			return;
		}

		// create an instance of Camera
		mCamera = getCameraInstance();

		mPreviewSurface = new CameraPreview(this, mCamera);
		FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
		preview.addView(mPreviewSurface);
		
		final ToggleButton tb = new ToggleButton(this);
		mCapturePicOrVideo = 1;
		tb.setTextOn("Capture a Pic");
		tb.setTextOff("Record a video");
		tb.setBackgroundColor(0x9900aaaa);
		tb.setChecked(true);
		//tb.setText("Record a video");
		
		preview.addView(tb);
		tb.getLayoutParams().height = 200;
		tb.getLayoutParams().width = 300;
		
		tb.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked){
					mCapturePicOrVideo = 1;
				} else {
					mCapturePicOrVideo = 2;
				}
				
			}
		});

		TextView floatText = new TextView(this);
		preview.addView(floatText);
		ViewGroup.LayoutParams lp = floatText.getLayoutParams();

		FrameLayout.LayoutParams flp = new FrameLayout.LayoutParams(lp);
		flp.gravity = Gravity.CENTER;
		flp.width =
				ViewGroup.LayoutParams.WRAP_CONTENT;
		flp.height =
				ViewGroup.LayoutParams.WRAP_CONTENT;
		floatText.setLayoutParams(flp);
		floatText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
		floatText.setTextColor(0xffaaffaa);
		floatText.setBackgroundColor(0x00000000);
		floatText.setTextColor(0xffffffff);
		floatText.setText("simulate a float mask ^ ^ ");
		

		floatText.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				
				ViewGroup vp = (ViewGroup) v.getParent();
				Rect rect = new Rect();
				vp.getChildVisibleRect(vp, rect, null);
				
				
				float x= event.getX();
				float y = event.getY();
				switch (event.getActionMasked()) {
				case
				MotionEvent.ACTION_DOWN:
					startX = event.getX();
					startY =
							event.getY();
					break;
				case MotionEvent.ACTION_MOVE:
					v.setX(v.getX() + x-startX);
					v.setY(v.getY() + y - startY);
//					v.setTranslationX(x-startX);
//					v.setTranslationY(y-startY);
					break;
				case
				MotionEvent.ACTION_UP:
					startX = startY = 0;
					break;
					

				}

				return true;
			}
		});

		captureButton = (Button) findViewById(R.id.button_capture);
		captureButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.w(LOG_TAG, "button clicked");
				/**
				 * Get an image from the camera
				 */

				if (mCapturePicOrVideo == 1) {
					mCamera.takePicture(null, null, null, mPictureCallBack);
				}
				/**
				 * record a video
				 */
				else if (mCapturePicOrVideo == 2) {
					if (isRecording) {
						Log.w(LOG_TAG, "stoping ...");
						// stop recording and release camera
						mMediaRecorder.stop();
						releaseMediaRecorder();
						// mCamera.lock();

						setCaptureButtonText("Capture");
						isRecording = false;
					} else {
						Log.w(LOG_TAG, "trying to start..");
						// initialize video camera
						if (prepareVideoRecorder()) {
							// Camera is available and unlocked, MediaRecorder
							// is prepared, now you can start recording
							mMediaRecorder.start();

							// inform the user that recording has started
							setCaptureButtonText("Stop");
							isRecording = true;

						} else {
							// prepare didn't work, release the camera
							releaseMediaRecorder();
							Toast.makeText(CameraActivity.this, "preparing didn't work", Toast.LENGTH_LONG)
									.show();
						}
					}
				}

			}
		});
	}

	private void setCaptureButtonText(String text) {
		captureButton.setText(text);
	}

	private boolean prepareVideoRecorder() {
		// mCamera = getCameraInstance();
		mMediaRecorder = new MediaRecorder();

		// Step 1: Unlock and set camera to MediaRecorder
		mCamera.unlock();
		mMediaRecorder.setCamera(mCamera);

		// Step 2: Set sources
		try {
			mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
			mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
		} catch (IllegalStateException e) {
			mCamera.lock();
			Toast.makeText(this, "IllegalStateException happened when setting AudioSource or VideoSource",
					Toast.LENGTH_LONG).show();
			return false;
		}

		// Step 3: Set a CamcorderProfile (requires API Level 8 or higher)
		/**
		 * Set output format and encoding (for versions prior to APILevel 8):
		 * 
		 * mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
		 * mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
		 * mMediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.DEFAULT);
		 */
		mMediaRecorder.setProfile(CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH));

		// Step 4: Set output file
		mMediaRecorder.setOutputFile(PickPhotoActivity.getOutputMediaFile(PickPhotoActivity.MEDIA_TYPE_VIDEO)
				.toString());

		// Step 5: Set the preview output
		mMediaRecorder.setPreviewDisplay(mPreviewSurface.getHolder().getSurface());

		// Step 6: Prepare configured MediaRecorder
		try {
			mMediaRecorder.prepare();
		} catch (IllegalStateException e) {
			Log.e(LOG_TAG, "IllegalStateException preparing MediaRecorder: " + e.getMessage());
			mCamera.lock();
			return false;
		} catch (IOException e) {
			Log.e(LOG_TAG, "IOException preparing MediaRecorder: " + e.getMessage());
			releaseMediaRecorder();
			return false;
		}

		return true;
	}

	/** A safe way to get an instance of the Camera object. */
	public static Camera getCameraInstance() {
		Camera c = null;
		try {
			int cameraCount = Camera.getNumberOfCameras();
			Log.w(LOG_TAG, "camera count: " + cameraCount);

			/**
			 * attempt to get a Camera instance access the default, first,
			 * back-facing camera on a device with more than one camera.
			 */
			c = Camera.open();
		} catch (Exception e) {
			/**
			 * Camera is not available (in use or does not exist)
			 */
			Log.e(LOG_TAG, "can't open camera: " + e.toString());
		}
		return c;
	}

	/** Check if this device has a camera */
	private boolean checkCameraHardware(Context context) {
		if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
			// this device has a camera
			return true;
		} else {
			// no camera on this device
			return false;
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		releaseMediaRecorder(); // if you are using MediaRecorder, release it
								// first
		releaseCamera(); // release the camera immediately on pause event
	}

	private void releaseMediaRecorder() {
		Log.w(LOG_TAG, "releaseMediaRecorder");
		if (mMediaRecorder != null) {
			mMediaRecorder.reset(); // clear recorder configuration
			mMediaRecorder.release(); // release the recorder object
			mMediaRecorder = null;
			// mCamera.lock(); // lock camera for later use
		}
	}

	private void releaseCamera() {
		if (mCamera != null) {
			mCamera.release(); // release the camera for other applications
			mCamera = null;
		}
	}

	private Camera.PictureCallback mPictureCallBack = new Camera.PictureCallback() {

		@Override
		public void onPictureTaken(byte[] data, Camera camera) {

			File picFile = PickPhotoActivity.getOutputMediaFile(PickPhotoActivity.MEDIA_TYPE_IMAGE);
			if (picFile == null) {
				Log.e(LOG_TAG, "Error creating media file, check storage permissions");
				return;
			}

			try {
				FileOutputStream fos = new FileOutputStream(picFile);
				fos.write(data);
				fos.close();
			} catch (FileNotFoundException e) {
				Log.e(LOG_TAG, "File not found: " + e.getMessage());
			} catch (IOException e) {
				Log.e(LOG_TAG, "Erro accessing file: " + e.getMessage());
			}
			
			camera.startPreview();
		}
		
	};

}

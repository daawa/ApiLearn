package camera;

import java.io.IOException;

import android.content.Context;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {

	public static final String LOG_TAG = "CameraPreView";

	private SurfaceHolder mHolder;
	private Camera mCamera;

	public CameraPreview(Context context, Camera camera) {
		super(context);
		mCamera = camera;

		/**
		 * Install a SurfaceHolder.Callback so we get notified when the
		 * underlying surface is created and destroyed.
		 */
		mHolder = getHolder();
		mHolder.addCallback(this);
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		
		if(mCamera == null){
			Log.e(LOG_TAG, "surface created , but mCamera is null");
			return;
		}
		/**
		 * The Surface has been created ,now tell the camera where to draw the
		 * preview.
		 */
		try {
			mCamera.setPreviewDisplay(mHolder);
			mCamera.startPreview();
		} catch (IOException e) {
			Log.e(LOG_TAG, "can't preview: " + e.getMessage());
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		
		if(mCamera == null){
			Log.e(LOG_TAG, "surface created , but mCamera is null");
			return;
		}

		//
		if (mHolder.getSurface() == null) {
			return;
		}

		try {
			mCamera.stopPreview();
		} catch (Exception e) {

			Log.w(LOG_TAG, "surfaceChanged: " + e.getMessage());
		}
		
		try{
			mCamera.setPreviewDisplay(mHolder);
			mCamera.startPreview();
		}catch (Exception e){
            Log.d(LOG_TAG, "Error starting camera preview: " + e.getMessage());
        }

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {

	}

}

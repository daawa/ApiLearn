package play.apilearn;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass. Activities that contain this fragment
 * must implement the {@link BlankFragment.OnFragmentInteractionListener}
 * interface to handle interaction events. Use the
 * {@link BlankFragment#newInstance} factory method to create an instance of
 * this fragment.
 * 
 */
public class BlankFragment extends Fragment {
	
	static final String LOG_TAG = "BlankFragment";
	
	private static final String ARG_PARAM1 = "param1";
	private static final String ARG_PARAM2 = "param2";

	// TODO: Rename and change types of parameters
	private String mParam1;
	private String mParam2;

	private OnFragmentInteractionListener mListener;

	/**
	 * Use this factory method to create a new instance of this fragment using
	 * the provided parameters.
	 * 
	 * @param param1
	 *            Parameter 1.
	 * @param param2
	 *            Parameter 2.
	 * @return A new instance of fragment BlankFragment.
	 */
	public static BlankFragment newInstance(String param1, String param2) {
		BlankFragment fragment = new BlankFragment();
		Bundle args = new Bundle();
		args.putString(ARG_PARAM1, param1);
		args.putString(ARG_PARAM2, param2);
		fragment.setArguments(args);
		return fragment;
	}

	public BlankFragment() {
		// Required empty public constructor
	}
	
	
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.w(LOG_TAG, "onCreate");
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			mParam1 = getArguments().getString(ARG_PARAM1);
			mParam2 = getArguments().getString(ARG_PARAM2);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Log.w(LOG_TAG, "onCreateView");
		return inflater.inflate(R.layout.fragment_blank, container, false);
	}

	// TODO: Rename method, update argument and hook method into UI event
	public void onButtonPressed(Uri uri) {
		if (mListener != null) {
			mListener.onFragmentInteraction(uri);
		}
	}

//	@Override
//	public void onAttach(Activity activity) {
//		super.onAttach(activity);
//		try {
//			mListener = (OnFragmentInteractionListener) activity;
//		} catch (ClassCastException e) {
//			throw new ClassCastException(activity.toString()
//					+ " must implement OnFragmentInteractionListener");
//		}
//	}
//
	@Override
	public void onDetach() {
		Log.w(LOG_TAG, "onDetach");
		super.onDetach();
		mListener = null;
	}

	/**
	 * This interface must be implemented by activities that contain this
	 * fragment to allow an interaction in this fragment to be communicated to
	 * the activity and potentially other fragments contained in that activity.
	 * <p>
	 * See the Android Training lesson <a href=
	 * "http://developer.android.com/training/basics/fragments/communicating.html"
	 * >Communicating with Other Fragments</a> for more information.
	 */
	public interface OnFragmentInteractionListener {
		// TODO: Update argument type and name
		public void onFragmentInteraction(Uri uri);
	}

	@Override
	public void onHiddenChanged(boolean hidden) {
		Log.w(LOG_TAG, "onHiddenChanged");
		super.onHiddenChanged(hidden);
	}

	@Override
	public void onInflate(Activity activity, AttributeSet attrs, Bundle savedInstanceState) {
		Log.w(LOG_TAG, "onInflate");
		super.onInflate(activity, attrs, savedInstanceState);
	}

	@Override
	public void onAttach(Activity activity) {
		Log.w(LOG_TAG, "onAttach");
		super.onAttach(activity);
	}

	@Override
	public Animator onCreateAnimator(int transit, boolean enter, int nextAnim) {
		Log.w(LOG_TAG, "onCreateAnimator");
		return super.onCreateAnimator(transit, enter, nextAnim);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		Log.w(LOG_TAG, "onViewCreated");
		super.onViewCreated(view, savedInstanceState);
	}

	@Override
	public View getView() {
		Log.w(LOG_TAG, "getView");
		return super.getView();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		Log.w(LOG_TAG, "onActivityCreated");
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onViewStateRestored(Bundle savedInstanceState) {
		Log.w(LOG_TAG, "onViewStateRestored");
		super.onViewStateRestored(savedInstanceState);
	}

	@Override
	public void onStart() {
		Log.w(LOG_TAG, "onStart");
		super.onStart();
	}

	@Override
	public void onResume() {
		Log.w(LOG_TAG, "onResume");
		super.onResume();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		Log.w(LOG_TAG, "onSaveInstanceState");
		super.onSaveInstanceState(outState);
	}

	@Override
	public void onPause() {
		Log.w(LOG_TAG, "onPause");
		super.onPause();
	}

	@Override
	public void onStop() {
		Log.w(LOG_TAG, "onStop");
		super.onStop();
	}

	@Override
	public void onDestroyView() {
		Log.w(LOG_TAG, "onDestroyView");
		super.onDestroyView();
	}

	@Override
	public void onDestroy() {
		Log.w(LOG_TAG, "onDestroy");
		super.onDestroy();
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		Log.w(LOG_TAG, "onCreateOptionsMenu");
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public void onDestroyOptionsMenu() {
		Log.w(LOG_TAG, "onDestroyOptionsMenu");
		super.onDestroyOptionsMenu();
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		Log.w(LOG_TAG, "onCreateContextMenu");
		super.onCreateContextMenu(menu, v, menuInfo);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		Log.w(LOG_TAG, "onContextItemSelected");
		return super.onContextItemSelected(item);
	}

}

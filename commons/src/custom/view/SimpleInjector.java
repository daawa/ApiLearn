package custom.view;


import java.lang.reflect.Field;
import java.lang.reflect.Method;

import android.app.Activity;
import android.app.Application;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Movie;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;


public class SimpleInjector {

	
	public static void uninjectAll(Object obj) {
		if(obj==null) return;
		
		
		for (Field field : obj.getClass().getDeclaredFields()) {
			if (field.isAnnotationPresent(InjectResource.class)
					|| field.isAnnotationPresent(InjectView.class)) {
				uninjectField(field, obj);
			}
		}
	}

	
	

	
	
	
	public static void uninjectResourceMembers(Object obj) {
		if(obj==null) return;
		for (Field field : obj.getClass().getDeclaredFields()) {
			if (field.isAnnotationPresent(InjectResource.class)) {
				uninjectField(field, obj);
			}
		}
	}

	
	public static void uninjectViewMembers(Object obj) {
		if(obj==null) return;
		for (Field field : obj.getClass().getDeclaredFields()) {
			if (field.isAnnotationPresent(InjectView.class)) {
				uninjectField(field, obj);
			}
		}
	}

//	private static void callXBusinessMehtod(String methodName, Field field,
//			Object obj) {
//		try {
//			Method m = XBusiness.class.getMethod(methodName);
//
//			field.setAccessible(true);
//			Object fObj = field.get(obj);
//			if(fObj==null) return;
//			m.invoke(fObj);
//		} catch (Exception e) {
//			throw new RuntimeException(e);
//		}
//	}

	private static void uninjectField(Field field, Object obj) {
		field.setAccessible(true);

		try {
			field.set(obj, null);
		} catch (IllegalArgumentException e) {
			throw e;
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	
//	public static void injectBusinessMembers(Object obj) {
//		if(obj==null) return;
//		
//		for (Field field : obj.getClass().getDeclaredFields()) {
//			if (field.isAnnotationPresent(InjectBusiness.class)) {
//				injectBusiness(field, obj);
//			}
//		}
//	}

//	private static void injectBusiness(Field field, Object obj) {
//		if (obj==null || Nullable.isNullable(field))
//			return;
//
//		try {
//			XBusiness b = (XBusiness)field.getType().newInstance();
//			field.setAccessible(true);
//			field.set(obj, b);
//		} catch (Exception e) {
//			throw new RuntimeException(e);
//		}
//	}

	
	public static void injectResourceMembers(Object obj, Application application) {
		if(obj==null) return;
		
		for (Field field : obj.getClass().getDeclaredFields()) {
			if (field.isAnnotationPresent(InjectResource.class)) {
				injectResource(field, obj, application);
			}
		}
	}

	private static int getId(Resources resources, InjectResource annotation) {
		int id = annotation.value();
		return id >= 0 ? id : resources.getIdentifier(annotation.name(), null,
				null);
	}

	private static void injectResource(Field field, Object obj,
			Application application) {
		if (Nullable.isNullable(field))
			return;

		Object value = null;
		try {
			final Resources resources = application.getResources();
			final InjectResource annotation = field
					.getAnnotation(InjectResource.class);
			final int id = getId(resources, annotation);
			final Class<?> t = field.getType();

			if (String.class.isAssignableFrom(t)) {
				value = resources.getString(id);
			} else if (boolean.class.isAssignableFrom(t)
					|| Boolean.class.isAssignableFrom(t)) {
				value = resources.getBoolean(id);
			} else if (ColorStateList.class.isAssignableFrom(t)) {
				value = resources.getColorStateList(id);
			} else if (int.class.isAssignableFrom(t)
					|| Integer.class.isAssignableFrom(t)) {
				value = resources.getInteger(id);
			} else if (Drawable.class.isAssignableFrom(t)) {
				value = resources.getDrawable(id);
			} else if (String[].class.isAssignableFrom(t)) {
				value = resources.getStringArray(id);
			} else if (int[].class.isAssignableFrom(t)
					|| Integer[].class.isAssignableFrom(t)) {
				value = resources.getIntArray(id);
			} else if (Animation.class.isAssignableFrom(t)) {
				value = AnimationUtils.loadAnimation(application, id);
			} else if (Movie.class.isAssignableFrom(t)) {
				value = resources.getMovie(id);
			}

			if (value == null) {
				throw new NullPointerException(
						String.format(
								"Can't inject null value into %s.%s when field is not @Nullable",
								field.getDeclaringClass(), field.getName()));
			}

			field.setAccessible(true);
			field.set(obj, value);

		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);

		} catch (IllegalArgumentException f) {
			throw new IllegalArgumentException(String.format(
					"Can't assign %s value %s to %s field %s",
					value != null ? value.getClass() : "(null)", value, field
							.getType(), field.getName()));
		}
	}

	
	public static void injectViewMembers(Object obj, View viewsContainer) {
		if(obj==null || viewsContainer==null) return;
		
		for (Field field : obj.getClass().getDeclaredFields()) {
			if (field.isAnnotationPresent(InjectView.class)) {
				injectView(field, obj, viewsContainer);
			}
		}
	}

	
	public static void injectViewMembers(Activity activity) {
		if(activity==null) return;
		
		for (Field field : activity.getClass().getDeclaredFields()) {
			if (field.isAnnotationPresent(InjectView.class)) {
				injectView(field, activity, activity);
			}
		}
	}

	private static void injectView(Field field, Object obj, Object view) {
		if (Nullable.isNullable(field))
			return;

		Object value = null;
		try {
			if (view instanceof Activity) {
				value = ((Activity) view).findViewById(field.getAnnotation(
						InjectView.class).value());
			} else if (view instanceof View) {
				value = ((View) view).findViewById(field.getAnnotation(
						InjectView.class).value());
			} else {
				throw new RuntimeException(String.format(
						"Not right %s view on %s field %s", view.getClass()
								.getName(), field.getType(), field.getName()));
			}

			if (value == null) {
				throw new NullPointerException(
						String.format(
								"Can't inject null value into %s.%s when field is not @Nullable",
								field.getDeclaringClass(), field.getName()));
			}
			field.setAccessible(true);
			field.set(obj, value);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (IllegalArgumentException f) {
			throw new IllegalArgumentException(String.format(
					"Can't assign %s value %s to %s field %s",
					value != null ? value.getClass() : "(null)", value, field
							.getType(), field.getName()));
		}
	}

}

package com.games.tilewars;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.animation.Animator;
import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.util.Log;
import android.view.View;
import android.view.View.OnLayoutChangeListener;
import android.view.ViewGroup;
import android.widget.Toast;

public class LayoutTransitionUtil {
	
	public static Animator.AnimatorListener sAnimatorListener;
	
	public static void checkAndSetupChangeAnimationForAllChild(ViewGroup viewGroup) {
		Log.i("entered checkAndSetupChangeAnimationForAllChild", "yes");
		for(int i = 0 ; i < viewGroup.getChildCount();i++){
			View childAt = viewGroup.getChildAt(i);
			if (childAt instanceof ViewGroup ) {
				LayoutTransition layoutTransition = null;
				ViewGroup childViewGroup = (ViewGroup) childAt;
				if((layoutTransition=childViewGroup.getLayoutTransition())!=null && sLayoutTransitions.contains(layoutTransition)){
					LayoutTransitionUtil.setupChangeAnimation(childViewGroup);
				}
				checkAndSetupChangeAnimationForAllChild(childViewGroup);
			}
		}
	}
	
	public static void enableChangeTransition(LayoutTransition layoutTransition){
		Log.i("entered enableChangeTransition", "yes");
		if(layoutTransition==null){
			return;
		}
		try {
			Method method = LayoutTransition.class.getMethod("enableTransitionType", new Class[]{int.class});
			Field field = LayoutTransition.class.getField("CHANGING");
			method.invoke(layoutTransition, field.get(null));
		} catch (Exception e) {
			Log.i("enableChangeTransition","entered");
			sLayoutTransitions.add(layoutTransition);
		}
	}
	
	public static void disableChangeTransition(LayoutTransition layoutTransition){
		Log.i("entered disableChangeTransition", "yes");
		if(layoutTransition==null){
			return;
		}
		try {
			Method method = LayoutTransition.class.getMethod("disableTransitionType", new Class[]{int.class});
			Field field = LayoutTransition.class.getField("CHANGING");
			method.invoke(layoutTransition, field.get(null));
		} catch (Exception e) {
			sLayoutTransitions.remove(layoutTransition);
		}
	}
	
	public static void setupChangeAnimationOneTime(ViewGroup viewGroup) {
		Log.i("entered setupChangeAnimationOneTime ViewGroup", "yes");
		LayoutTransition layoutTransition = viewGroup.getLayoutTransition();
		if(layoutTransition==null){
			return;
		}
		/*try {
			Method method = LayoutTransition.class.getMethod("enableTransitionType", new Class[]{int.class});
			Field field = LayoutTransition.class.getField("CHANGING");
			method.invoke(layoutTransition, field.get(null));
		} catch (Exception e) {*/
			Log.i("setupChangeAnimationOneTime","entered");
			setupChangeAnimationOneTime((View)viewGroup);
			for(int i = 0 ; i < viewGroup.getChildCount();i++){
				View childAt = viewGroup.getChildAt(i);
				setupChangeAnimationOneTime(childAt);
			//}
		}
	}
	
	public static void setupChangeAnimation(ViewGroup viewGroup) {
		Log.i("entered setupChangeAnimation ViewGroup", "yes");
		setupChangeAnimation((View)viewGroup);
		for(int i = 0 ; i < viewGroup.getChildCount();i++){
			View childAt = viewGroup.getChildAt(i);
			setupChangeAnimation(childAt);
		}
	}

	private static List<LayoutTransition> sLayoutTransitions = new ArrayList<LayoutTransition>();
	private static Map<View, ObjectAnimator> sMapAnimators = new HashMap<View, ObjectAnimator>();
	private static Map<View, View.OnLayoutChangeListener> sMapListeners = new HashMap<View, View.OnLayoutChangeListener>();
	private static ObjectAnimator sDefaultChangeAnimator; 
	
	private static void setupChangeAnimation(final View view) {
		Log.i("entered setupChangeAnimation", "yes");
		ObjectAnimator objectAnimator = sMapAnimators.get(view);
		if(objectAnimator!=null){
			objectAnimator.cancel();
			sMapAnimators.remove(objectAnimator);
		}
		OnLayoutChangeListener listener = sMapListeners.get(view);
		if(listener!=null){
			view.removeOnLayoutChangeListener(listener);
			sMapListeners.remove(view);
		}
		final ObjectAnimator changeAnimator = getDefaultChangeAnimator().clone();
		changeAnimator.setTarget(view);
		sMapAnimators.put(view, changeAnimator);
		
		changeAnimator.setupStartValues();
		final View.OnLayoutChangeListener onLayoutChangeListener = new View.OnLayoutChangeListener() {
			@Override
			public void onLayoutChange(View v, int left, int top, int right,
					int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
				
				if(left!=oldLeft || 
						top!=oldTop ||
						right!= oldRight ||
						bottom!=oldBottom
						){
					changeAnimator.setupEndValues();
					changeAnimator.start();
				}else{

					view.removeOnLayoutChangeListener(this);
					sMapListeners.remove(this);
					sMapAnimators.remove(view);
					if(sAnimatorListener!=null){
						sAnimatorListener.onAnimationEnd(changeAnimator);
					}
				}
			}
		};
		Animator.AnimatorListener animatorListener = new Animator.AnimatorListener() {
			
			@Override
			public void onAnimationStart(Animator animation) {
				view.removeOnLayoutChangeListener(onLayoutChangeListener);
				sMapListeners.remove(onLayoutChangeListener);
				if(sAnimatorListener!=null){
					sAnimatorListener.onAnimationStart(animation);
				}
			}
			
			@Override
			public void onAnimationRepeat(Animator animation) {
				if(sAnimatorListener!=null){
					sAnimatorListener.onAnimationRepeat(animation);
				}
			}
			
			@Override
			public void onAnimationEnd(Animator animation) {
				sMapAnimators.remove(view);
				if(sAnimatorListener!=null){
					sAnimatorListener.onAnimationEnd(animation);
				}
			}
			
			@Override
			public void onAnimationCancel(Animator animation) {
				if(sAnimatorListener!=null){
					sAnimatorListener.onAnimationCancel(animation);
				}
			}
		};
		changeAnimator.addListener(animatorListener);
		view.addOnLayoutChangeListener(onLayoutChangeListener);
		sMapListeners.put(view,onLayoutChangeListener);
	}
	
	private static void setupChangeAnimationOneTime(final View view) {
		Log.i("entered setupChangeAnimationOneTime", "yes");
		ObjectAnimator objectAnimator = sMapAnimators.get(view);
		if(objectAnimator!=null){
			objectAnimator.cancel();
			sMapAnimators.remove(objectAnimator);
		}
		OnLayoutChangeListener listener = sMapListeners.get(view);
		if(listener!=null){
			view.removeOnLayoutChangeListener(listener);
			sMapListeners.remove(view);
		}

		final View.OnLayoutChangeListener onLayoutChangeListener = new View.OnLayoutChangeListener() {
			@Override
			public void onLayoutChange(View v, int left, int top, int right,
					int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
				
				ObjectAnimator objectAnimator = sMapAnimators.get(view);
				if(objectAnimator!=null){
					objectAnimator.cancel();
					sMapAnimators.remove(objectAnimator);
				}
				
				final ObjectAnimator changeAnimator = getChangeAnimator(v, left, top, right,
						bottom, oldLeft, oldTop, oldRight, oldBottom);
				
				sMapAnimators.put(view, changeAnimator);
				
				if(left!=oldLeft || 
						top!=oldTop ||
						right!= oldRight ||
						bottom!=oldBottom
						){
					Animator.AnimatorListener animatorListener = new Animator.AnimatorListener() {
						
						@Override
						public void onAnimationStart(Animator animation) {
							if(sAnimatorListener!=null){
								sAnimatorListener.onAnimationStart(animation);
							}
						}
						
						@Override
						public void onAnimationRepeat(Animator animation) {
							if(sAnimatorListener!=null){
								sAnimatorListener.onAnimationRepeat(animation);
							}
						}
						
						@Override
						public void onAnimationEnd(Animator animation) {
							sMapAnimators.remove(view);
							if(sAnimatorListener!=null){
								sAnimatorListener.onAnimationEnd(animation);
							}
						}
						
						@Override
						public void onAnimationCancel(Animator animation) {
							if(sAnimatorListener!=null){
								sAnimatorListener.onAnimationCancel(animation);
							}
						}
					};
					changeAnimator.addListener(animatorListener);
					
					changeAnimator.start();
				}else{
					sMapAnimators.remove(view);
					if(sAnimatorListener!=null){
						sAnimatorListener.onAnimationEnd(changeAnimator);
					}
				}
			}
		};
		view.addOnLayoutChangeListener(onLayoutChangeListener);
		sMapListeners.put(view,onLayoutChangeListener);
	}
	
	public static ObjectAnimator getDefaultChangeAnimator(){
		if(sDefaultChangeAnimator==null){
			Log.i("entered getDefaultChangeAnimator", "yes");
	        PropertyValuesHolder pvhLeft = PropertyValuesHolder.ofInt("left", 0, 1);
	        PropertyValuesHolder pvhTop = PropertyValuesHolder.ofInt("top", 0, 1);
	        PropertyValuesHolder pvhRight = PropertyValuesHolder.ofInt("right", 0, 1);
	        PropertyValuesHolder pvhBottom = PropertyValuesHolder.ofInt("bottom", 0, 1);
	        PropertyValuesHolder pvhScrollX = PropertyValuesHolder.ofInt("scrollX", 0, 1);
	        PropertyValuesHolder pvhScrollY = PropertyValuesHolder.ofInt("scrollY", 0, 1);
	        sDefaultChangeAnimator = ObjectAnimator.ofPropertyValuesHolder((Object)null,
	                pvhLeft, pvhTop, pvhRight, pvhBottom, pvhScrollX, pvhScrollY);
	        sDefaultChangeAnimator.setDuration(600);
		}
        return sDefaultChangeAnimator;
	}
	
	public static ObjectAnimator getChangeAnimator(View view, int left, int top, int right,
			int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom){
		Log.i("entered getChangeAnimator", "yes");
        PropertyValuesHolder pvhLeft = PropertyValuesHolder.ofInt("left", oldLeft, left);
        PropertyValuesHolder pvhTop = PropertyValuesHolder.ofInt("top", oldTop, top);
        PropertyValuesHolder pvhRight = PropertyValuesHolder.ofInt("right", oldRight, right);
        PropertyValuesHolder pvhBottom = PropertyValuesHolder.ofInt("bottom", oldBottom, bottom);
        ObjectAnimator changeAnimator = ObjectAnimator.ofPropertyValuesHolder((Object)view,
                pvhLeft, pvhTop, pvhRight, pvhBottom);
        changeAnimator.setDuration(600);
        return changeAnimator;
	}
}

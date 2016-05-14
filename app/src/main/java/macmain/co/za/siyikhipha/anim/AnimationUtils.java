package macmain.co.za.siyikhipha.anim;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by ProJava on 12/23/2015.
 */
public class AnimationUtils {

    private static int counter = 0;

    public static void scaleXY(RecyclerView.ViewHolder holder) {
        holder.itemView.setScaleX(0);
        holder.itemView.setScaleY(0);

        PropertyValuesHolder propx = PropertyValuesHolder.ofFloat("scaleX", 1);
        PropertyValuesHolder propy = PropertyValuesHolder.ofFloat("scaleY", 1);

        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(holder.itemView, propx, propy);


        animator.setDuration(800);
        animator.start();
    }

    public static void animate(RecyclerView.ViewHolder holder, boolean goesDown){

        AnimatorSet animatorSet = new AnimatorSet();

       ObjectAnimator aminateTranslationY = ObjectAnimator.ofFloat(holder.itemView,"translationY",goesDown== true?300:-300,0);

       ObjectAnimator aminateTranslationX = ObjectAnimator.ofFloat(holder.itemView,"translationX",-25,25,-20,-15,15,-10,10,-5,5,0);
        aminateTranslationY.setDuration(1000);

        aminateTranslationX.setDuration(1000);

        animatorSet.playTogether(aminateTranslationX,aminateTranslationY);
        animatorSet.start();

        //aminateTranslationY.start();

    }

    public static void animateDefault(RecyclerView.ViewHolder holder, boolean goesDown){

       // AnimatorSet animatorSet = new AnimatorSet();

        ObjectAnimator aminateTranslationY = ObjectAnimator.ofFloat(holder.itemView,"translationY",goesDown== true?300:-300,0);

            aminateTranslationY.setDuration(1000);
            aminateTranslationY.start();
    }

    public static void animateToolBar(View containerToolbar){

    }


}

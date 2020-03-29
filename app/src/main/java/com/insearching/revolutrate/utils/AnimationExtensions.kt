package com.insearching.revolutrate.utils

import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.blackcat.currencyedittext.CurrencyEditText
import com.insearching.revolutrate.R

fun CurrencyEditText.setValueWithAnimation(newValue: Double) {
    val fadeInAnimation = AnimationUtils.loadAnimation(context, R.anim.fade_in)
    val fadeOutAnimation = AnimationUtils.loadAnimation(context, R.anim.fade_out)
    fadeInAnimation.setAnimationListener(object : Animation.AnimationListener {
        override fun onAnimationRepeat(animation: Animation?) {
            // no op
        }
        
        override fun onAnimationEnd(animation: Animation?) {
            setActualAmount(newValue)
            startAnimation(fadeOutAnimation)
        }
        
        override fun onAnimationStart(animation: Animation?) {
            // no op
        }
        
    })
    startAnimation(fadeInAnimation)
}
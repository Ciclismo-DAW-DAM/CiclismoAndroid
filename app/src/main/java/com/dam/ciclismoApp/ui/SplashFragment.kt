package com.dam.ciclismoApp.ui

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.BounceInterpolator
import android.view.animation.OvershootInterpolator
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.dam.ciclismoApp.R
import com.dam.ciclismoApp.databinding.FragmentParticipationsBinding
import com.dam.ciclismoApp.databinding.FragmentSplashBinding

class SplashFragment : Fragment() {
    val ANIMATION_DURATION:Long = 1200
    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startAnimation()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun startAnimation() {
        // Intro animation configuration.
        val valueAnimator = ValueAnimator.ofFloat(0f, 1f)
        valueAnimator.addUpdateListener {
            val value = it.animatedValue as Float
            binding.imageView2.scaleX = value
            binding.imageView2.scaleY = value
        }
        valueAnimator.interpolator = BounceInterpolator()
        valueAnimator.duration = ANIMATION_DURATION


        valueAnimator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {

            }

            override fun onAnimationEnd(animation: Animator) {
                findNavController().navigate(R.id.action_splash_to_login)
            }

            override fun onAnimationCancel(animation: Animator) {

            }

            override fun onAnimationRepeat(animation: Animator) {

            }
        })

        // Start animation.
        valueAnimator.start()
    }
}

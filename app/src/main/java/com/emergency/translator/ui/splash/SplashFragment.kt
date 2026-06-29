package com.emergency.translator.ui.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.emergency.translator.R
import com.emergency.translator.databinding.FragmentSplashBinding
import com.emergency.translator.ui.common.ThemeManager

class SplashFragment : Fragment() {

    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // After a theme change the Activity recreates and lands here.
        // Skip the animation so the user goes straight back to Home.
        val delay = if (ThemeManager.consumeSkipSplash(requireContext())) 0L else 2200L

        Handler(Looper.getMainLooper()).postDelayed({
            if (isAdded && !isDetached) {
                findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
            }
        }, delay)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

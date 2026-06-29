package com.emergency.translator.ui.settings

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.emergency.translator.BuildConfig
import com.emergency.translator.R
import com.emergency.translator.databinding.FragmentSettingsBinding
import com.emergency.translator.ui.common.ThemeManager
import com.emergency.translator.ui.common.ThemeManager.AccentColor
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SettingsViewModel by viewModels()

    // Accent colors used for the swatches
    private val COLOR_BLUE  = 0xFF1565C0.toInt()
    private val COLOR_GREEN = 0xFF2E7D32.toInt()
    private val COLOR_RED   = 0xFFC62828.toInt()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupDarkModeSwitch()
        setupColorPicker()
        setupAboutSection()
    }

    private fun setupDarkModeSwitch() {
        binding.darkModeSwitch.isChecked = ThemeManager.isDarkMode(requireContext())
        binding.darkModeSwitch.setOnCheckedChangeListener { _, isChecked ->
            ThemeManager.setDarkMode(requireContext(), isChecked)
            applyThemeChange()
        }
    }

    private fun setupColorPicker() {
        val dark = ThemeManager.isDarkMode(requireContext())
        val accent = ThemeManager.getAccentColor(requireContext())

        renderSwatch(binding.swatchBlue,  COLOR_BLUE,  selected = accent == AccentColor.BLUE,  dark = dark, enabled = true)
        renderSwatch(binding.swatchGreen, COLOR_GREEN, selected = accent == AccentColor.GREEN, dark = dark, enabled = true)
        renderSwatch(binding.swatchRed,   COLOR_RED,   selected = accent == AccentColor.RED,   dark = dark, enabled = true)

        binding.swatchBlue.setOnClickListener {
            ThemeManager.setAccentColor(requireContext(), AccentColor.BLUE)
            applyThemeChange()
        }
        binding.swatchGreen.setOnClickListener {
            ThemeManager.setAccentColor(requireContext(), AccentColor.GREEN)
            applyThemeChange()
        }
        binding.swatchRed.setOnClickListener {
            ThemeManager.setAccentColor(requireContext(), AccentColor.RED)
            applyThemeChange()
        }
    }

    private fun renderSwatch(
        container: FrameLayout,
        color: Int,
        selected: Boolean,
        dark: Boolean,
        enabled: Boolean
    ) {
        // Inner view (the color circle)
        val inner = container.getChildAt(0)
        inner.background = oval(color)

        // Outer ring shown when selected
        if (selected) {
            val ringColor = if (dark) Color.WHITE else Color.parseColor("#424242")
            container.background = ringOval(ringColor)
        } else {
            container.background = null
        }

        container.alpha = if (enabled) 1f else 0.35f
        container.isClickable = enabled
        container.isFocusable = enabled
    }

    private fun oval(fillColor: Int) = GradientDrawable().apply {
        shape = GradientDrawable.OVAL
        setColor(fillColor)
    }

    private fun ringOval(strokeColor: Int) = GradientDrawable().apply {
        shape = GradientDrawable.OVAL
        setColor(Color.TRANSPARENT)
        setStroke(dpToPx(3), strokeColor)
    }

    private fun dpToPx(dp: Int): Int =
        (dp * resources.displayMetrics.density + 0.5f).toInt()

    private fun applyThemeChange() {
        ThemeManager.markThemeChange(requireContext())
        requireActivity().recreate()
    }

    private fun setupAboutSection() {
        binding.appVersionText.text = getString(R.string.app_version_label, BuildConfig.VERSION_NAME)
        binding.apiKeyStatusText.text = getString(R.string.offline_mode_status)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

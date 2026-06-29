package com.emergency.translator.ui.phrases

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.emergency.translator.R
import com.emergency.translator.databinding.ItemPhraseBinding
import com.emergency.translator.domain.model.EmergencyPhrase
import com.emergency.translator.domain.model.PhraseCategory

class PhraseAdapter(
    private val onPhraseClick: (EmergencyPhrase) -> Unit,
    private val onFavoriteClick: (EmergencyPhrase) -> Unit
) : ListAdapter<EmergencyPhrase, PhraseAdapter.PhraseViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhraseViewHolder =
        PhraseViewHolder(
            ItemPhraseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: PhraseViewHolder, position: Int) =
        holder.bind(getItem(position))

    inner class PhraseViewHolder(
        private val binding: ItemPhraseBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(phrase: EmergencyPhrase) {
            binding.phraseText.text = phrase.englishText
            binding.categoryChip.text = phrase.category.displayName
            binding.categoryChip.chipBackgroundColor =
                android.content.res.ColorStateList.valueOf(phrase.category.chipColor())
            binding.favoriteButton.setImageResource(
                if (phrase.isFavorite) R.drawable.ic_favorite else R.drawable.ic_favorite_border
            )
            binding.root.setOnClickListener { onPhraseClick(phrase) }
            binding.favoriteButton.setOnClickListener { onFavoriteClick(phrase) }
        }
    }

    private companion object DiffCallback : DiffUtil.ItemCallback<EmergencyPhrase>() {
        override fun areItemsTheSame(old: EmergencyPhrase, new: EmergencyPhrase) = old.id == new.id
        override fun areContentsTheSame(old: EmergencyPhrase, new: EmergencyPhrase) = old == new
    }
}

private fun PhraseCategory.chipColor(): Int = when (this) {
    PhraseCategory.MEDICAL        -> 0xFFE53935.toInt()  // Red
    PhraseCategory.FOOD_WATER     -> 0xFF2E7D32.toInt()  // Green
    PhraseCategory.SHELTER        -> 0xFF1565C0.toInt()  // Blue
    PhraseCategory.FAMILY         -> 0xFF6A1B9A.toInt()  // Purple
    PhraseCategory.SAFETY         -> 0xFFFF6F00.toInt()  // Amber
    PhraseCategory.TRANSPORTATION -> 0xFF00838F.toInt()  // Teal
    PhraseCategory.COMMUNICATION  -> 0xFF37474F.toInt()  // Blue-Grey
}

package edu.pract5.apirestfree.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import edu.pract5.apirestfree.databinding.MotorcycleItemBinding
import edu.pract5.apirestfree.models.Motorcycle

/**
 * Class MotorcyclesAdapter.kt
 * Adapter for the RecyclerView of motorcycles.
 * @author Víctor Lamas
 *
 * @param onClick Función que se ejecuta al hacer click en una palabra.
 * @param onClickFav Función que se ejecuta al hacer click en el icono de favorito.
 */
class MotorcyclesAdapter(
    private val onClick: (Motorcycle) -> Unit,
    private val onClickFav: (Motorcycle) -> Unit
) : ListAdapter<Motorcycle, MotorcyclesAdapter.MotorcyclesViewHolder>(
    MotorcyclesDiffCallback()
) {

    /**
     * Creates a ViewHolder for the motorcycle.
     *
     * @param parent Group of views to which the ViewHolder is added.
     * @param viewType Type of view.
     * @return ViewHolder of the motorcycle.
     */
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MotorcyclesViewHolder {
        return MotorcyclesViewHolder(
            MotorcycleItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ).root
        )
    }

    /**
     * Binds the motorcycle data to the ViewHolder.
     *
     * @param holder ViewHolder of the motorcycle.
     * @param position Position of the motorcycle in the list.
     */
    override fun onBindViewHolder(holder: MotorcyclesViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    /**
     * Internal class of WordsViewHolder.
     * ViewHolder for the motorcycles.
     *
     * @param view View of the motorcycle.
     */
    inner class MotorcyclesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val bind = MotorcycleItemBinding.bind(view)

        fun bind(motorcycle: Motorcycle) {
        }
    }
}

/**
 * Class MotorcyclesAdapter.kt
 * Compare two words.
 *
 * @author Víctor Lamas
 */
class MotorcyclesDiffCallback : DiffUtil.ItemCallback<Motorcycle>() {
    /**
     * Checks if two motorcycles are the same by their ID.
     *
     * @param oldItem Old motorcycle.
     * @param newItem New motorcycle.
     * @return True if the words is the same.
     */
    override fun areItemsTheSame(oldItem: Motorcycle, newItem: Motorcycle): Boolean {
        return oldItem.make == newItem.make && oldItem.model == newItem.model
    }

    /**
     * Checks if the content of two motorcycles are the same.
     *
     * @param oldItem Old motorcycle.
     * @param newItem New motorcycle.
     * @return True if the content of the words is the same.
     */
    override fun areContentsTheSame(oldItem: Motorcycle, newItem: Motorcycle): Boolean {
        return oldItem == newItem
    }
}
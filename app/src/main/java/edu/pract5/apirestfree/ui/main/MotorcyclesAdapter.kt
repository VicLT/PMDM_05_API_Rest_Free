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
 * @param onClickDetail The action to perform when a motorcycle is clicked.
 * @param onClickDelete The action to perform when a delete icon is clicked.
 */
class MotorcyclesAdapter(
    private val onClickDetail: (Motorcycle) -> Unit,
    private val onClickDelete: (Motorcycle) -> Unit,
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
            bind.tvYear.text = motorcycle.year
            bind.tvMake.text = motorcycle.make
            bind.tvModel.text = motorcycle.model

            bind.root.setOnClickListener {
                onClickDetail(motorcycle)
            }
            bind.icDelete.setOnClickListener {
                onClickDelete(motorcycle)
                notifyItemChanged(adapterPosition)
            }
        }
    }
}

/**
 * Class MotorcyclesAdapter.kt
 * Compare two motorcycles.
 *
 * @author Víctor Lamas
 */
class MotorcyclesDiffCallback : DiffUtil.ItemCallback<Motorcycle>() {
    /**
     * Checks if two motorcycles are the same by their model.
     *
     * @param oldItem Old motorcycle.
     * @param newItem New motorcycle.
     * @return True if the motorcycles are the same.
     */
    override fun areItemsTheSame(oldItem: Motorcycle, newItem: Motorcycle): Boolean {
        return oldItem.year == newItem.year
                && oldItem.make == newItem.make
                && oldItem.model == newItem.model
    }

    /**
     * Checks if the content of two motorcycles are the same.
     *
     * @param oldItem Old motorcycle.
     * @param newItem New motorcycle.
     * @return True if the content of the motorcycles are the same.
     */
    override fun areContentsTheSame(oldItem: Motorcycle, newItem: Motorcycle): Boolean {
        return oldItem == newItem
    }
}
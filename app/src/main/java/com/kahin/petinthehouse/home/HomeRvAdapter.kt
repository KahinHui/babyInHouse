package com.kahin.petinthehouse.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kahin.petinthehouse.R


class HomeRvAdapter(private val dataSet: Array<String>, private val img: Array<Int>) :
    RecyclerView.Adapter<HomeRvAdapter.ViewHolder>() {

    val clicked = mutableListOf<Int>()

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivMain: ImageView
        val tvName: TextView
        val ivLike: ImageView

        init {
            // Define click listener for the ViewHolder's View.
            ivMain = view.findViewById(R.id.iv_main)
            tvName = view.findViewById(R.id.tv_name)
            ivLike = view.findViewById(R.id.iv_like)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_pets, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
//        viewHolder.ivMain.setImageResource()
        viewHolder.apply {
            tvName.text = dataSet[position]
            ivMain.setImageResource(img[position])
            ivLike.apply {
                if (clicked.contains(position)) {
                    ivLike.setImageResource(R.drawable.ic_favorite_full)
                } else {
                    ivLike.setImageResource(R.drawable.ic_favorite_empty)
                }

                setOnClickListener {
                    if (clicked.contains(position)) {
                        ivLike.setImageResource(R.drawable.ic_favorite_empty)
                        clicked.remove(position)
                    } else {
                        ivLike.setImageResource(R.drawable.ic_favorite_full)
                        clicked.add(position)
                    }
                }
            }
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}

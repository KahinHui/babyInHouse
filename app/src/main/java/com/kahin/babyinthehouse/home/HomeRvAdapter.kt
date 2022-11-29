package com.kahin.babyinthehouse.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kahin.babyinthehouse.R


class HomeRvAdapter(private val dataSet: Array<String>, private val img: Array<Int>) :
    RecyclerView.Adapter<HomeRvAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivMain: ImageView
        val tvName: TextView

        init {
            // Define click listener for the ViewHolder's View.
            ivMain = view.findViewById(R.id.iv_main)
            tvName = view.findViewById(R.id.tv_name)
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
        viewHolder.tvName.text = dataSet[position]
        viewHolder.ivMain.setImageResource(img[position])
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}

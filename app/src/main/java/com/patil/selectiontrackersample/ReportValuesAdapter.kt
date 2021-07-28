package com.patil.selectiontrackersample

import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.RecyclerView

class ReportValuesAdapter : RecyclerView.Adapter<ReportValuesAdapter.ViewHolder>() {

    var list: List<String> = ArrayList()
    var tracker: SelectionTracker<Long>? = null

    init {
        setHasStableIds(true)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val number = list[position]
        tracker?.let {
            holder.bind(number, it.isSelected(position.toLong()))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.report_values_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int = list.size

    override fun getItemId(position: Int): Long = position.toLong()

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var text: TextView = view.findViewById(R.id.text)
        private var imagev: ImageView = view.findViewById(R.id.imageView10)

        fun bind(value: String, isActivated: Boolean = false) {
            text.text = value.toString()
            itemView.isActivated = isActivated
            if(isActivated){
                imagev.visibility = View.VISIBLE
            }else{
                imagev.visibility = View.GONE

            }
        }

        fun getItemDetails(): ItemDetailsLookup.ItemDetails<Long> =
            object : ItemDetailsLookup.ItemDetails<Long>() {
                override fun getPosition(): Int = adapterPosition
                override fun getSelectionKey(): Long? = itemId
            }
    }


    class MyItemDetailsLookup(private val recyclerView: RecyclerView) :
        ItemDetailsLookup<Long>() {
        override fun getItemDetails(event: MotionEvent): ItemDetails<Long>? {
            val view = recyclerView.findChildViewUnder(event.x, event.y)
            if (view != null) {
                return (recyclerView.getChildViewHolder(view) as ReportValuesAdapter.ViewHolder)
                    .getItemDetails()
            }
            return null
        }
    }
}
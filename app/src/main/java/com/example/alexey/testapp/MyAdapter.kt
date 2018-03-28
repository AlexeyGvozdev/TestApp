package com.example.alexey.testapp

/**
 * Created by alexey on 28.03.18.
 */

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.alexey.testapp.model.Event


class MyAdapter(private val clickListener: (Event) -> Unit):
        RecyclerView.Adapter<MyAdapter.ViewHolder>() {


    private var listEvents: List<Event> = emptyList()

    fun setListEvents(list: List<Event>) {
        this.listEvents = list
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listEvents[position])
    }

    override fun getItemCount(): Int = listEvents.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.item, parent, false)
        return ViewHolder(view, clickListener)
    }


    class ViewHolder(itemView: View,
                     private val clickListener: (Event) -> Unit):
            RecyclerView.ViewHolder(itemView) {
        val tv_title = itemView.findViewById<TextView>(R.id.tv_title)
        val tv_place = itemView.findViewById<TextView>(R.id.tv_place)
        val tv_coefficient = itemView.findViewById<TextView>(R.id.tv_coefficient)

        fun bind(event: Event) {
            with(event) {
                tv_title.text = title
                tv_place.text = place
                tv_coefficient.text = coefficient
            }
            itemView.setOnClickListener { clickListener(event) }
        }

    }
}
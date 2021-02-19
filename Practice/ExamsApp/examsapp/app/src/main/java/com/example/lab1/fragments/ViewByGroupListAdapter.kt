package com.example.lab1.fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.lab1.R
import com.example.lab1.model.Exam
import kotlinx.android.synthetic.main.custom_row.view.*

class ViewByGroupListAdapter : RecyclerView.Adapter<ViewByGroupListAdapter.MyViewHolder>() {

    private var examsList = emptyList<Exam>()

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun getItemCount(): Int {
        return examsList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(
                parent.context
            ).inflate(R.layout.custom_row, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentBook = examsList[position]
        holder.itemView.examID.text = currentBook.id.toString()
        holder.itemView.name.text = currentBook.name.toString()
        holder.itemView.group.text = currentBook.group.toString()
        holder.itemView.type.text = currentBook.type.toString()
    }

    fun setData(book: List<Exam>) {
        this.examsList = book
        notifyDataSetChanged()
    }

}
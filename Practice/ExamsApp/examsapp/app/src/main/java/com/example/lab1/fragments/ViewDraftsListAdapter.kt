package com.example.lab1.fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.lab1.R
import com.example.lab1.model.Exam
import kotlinx.android.synthetic.main.custom_row.view.*

class ViewDraftsListAdapter: RecyclerView.Adapter<ViewDraftsListAdapter.MyViewHolder>() {

    private var examList = emptyList<Exam>()


    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewDraftsListAdapter.MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(
                parent.context
            ).inflate(R.layout.custom_row, parent, false)
        )    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentBook = examList[position]
        holder.itemView.examID.text = currentBook.id.toString()
        holder.itemView.name.text = currentBook.name.toString()
        holder.itemView.group.text = currentBook.group.toString()
        holder.itemView.type.text = currentBook.type.toString()


        holder.itemView.rowLayout.setOnClickListener{
            val action = ViewDraftsFragmentDirections.actionViewDraftsFragmentToJoinExamFragment(currentBook)
            holder.itemView.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return examList.size
    }

    fun setData(book: List<Exam>) {
        this.examList = book
        notifyDataSetChanged()
    }

}
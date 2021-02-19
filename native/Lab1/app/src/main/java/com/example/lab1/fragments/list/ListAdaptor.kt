package com.example.lab1.fragments.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.lab1.R
import com.example.lab1.model.Book
import kotlinx.android.synthetic.main.custom_row.view.*

class ListAdaptor : RecyclerView.Adapter<ListAdaptor.MyViewHolder>()  {

    private var bookList = emptyList<Book>()

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(
                parent.context
            ).inflate(R.layout.custom_row, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return bookList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentBook = bookList[position]
        holder.itemView.bookId_txt.text = currentBook.id.toString()
        holder.itemView.title.text = currentBook.title.toString()
        holder.itemView.author.text = currentBook.author.toString()
        holder.itemView.year.text = currentBook.year.toString()
        holder.itemView.description.text = currentBook.description.toString()
        holder.itemView.rating.text = currentBook.rating.toString()

        holder.itemView.rowLayout.setOnClickListener{
            val action =
                ListFragmentDirections.actionListFragmentToUpdateFragment(
                    currentBook
                )
            holder.itemView.findNavController().navigate(action)
        }
    }

    fun setData(book: List<Book>) {
        this.bookList = book
        notifyDataSetChanged()
    }
}
package com.example.lab1.fragments.SecondSection

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lab1.R
import com.example.lab1.fragments.FirstSection.ViewEntitiesAdapter
import com.example.lab1.model.Entity
import kotlinx.android.synthetic.main.custom_row.view.*

class ViewByLevelAdapter: RecyclerView.Adapter<ViewByLevelAdapter.MyViewHolder>() {

    private var entityList = emptyList<Entity>()

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewByLevelAdapter.MyViewHolder {
        return ViewByLevelAdapter.MyViewHolder(
            LayoutInflater.from(
                parent.context
            ).inflate(R.layout.custom_row, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewByLevelAdapter.MyViewHolder, position: Int) {
        val currentEntity = entityList[position]
        holder.itemView.entityID.text = currentEntity.id.toString()
        holder.itemView.entityAttr1.text = currentEntity.name.toString()
        holder.itemView.entityAttr2.text = currentEntity.level.toString()
        holder.itemView.entityAttr3.text = currentEntity.status.toString()

    }

    override fun getItemCount(): Int {
        return entityList.size
    }

    fun setData(entities: List<Entity>) {
        this.entityList = entities
        notifyDataSetChanged()
    }

}
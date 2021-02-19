package com.example.lab1.fragments.SecondSection

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.lab1.R
import com.example.lab1.fragments.FirstSection.ViewEntitiesAdapter
import com.example.lab1.fragments.FirstSection.ViewEntitiesFragmentDirections
import com.example.lab1.model.Entity
import kotlinx.android.synthetic.main.custom_row.view.*

class SecondSectionItemsAdapter: RecyclerView.Adapter<SecondSectionItemsAdapter.MyViewHolder>() {

    private var entityList = emptyList<Entity>()

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SecondSectionItemsAdapter.MyViewHolder {
        return SecondSectionItemsAdapter.MyViewHolder(
            LayoutInflater.from(
                parent.context
            ).inflate(R.layout.custom_row, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentEntity = entityList[position]
        holder.itemView.entityID.text = currentEntity.id.toString()
        holder.itemView.entityAttr1.text = currentEntity.number.toString()
        holder.itemView.entityAttr2.text = currentEntity.status.toString()
        holder.itemView.entityAttr3.text = currentEntity.count.toString()
        holder.itemView.entityAttr4.text = currentEntity.address.toString()



        holder.itemView.rowLayout.setOnClickListener{
            val action = SecondSectionFragmentDirections.actionSecondSectionFragmentToBuyItemFragment2(currentEntity)
            holder.itemView.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return entityList.size
    }

    fun setData(entities: List<Entity>) {
        this.entityList = entities
        notifyDataSetChanged()
    }


}
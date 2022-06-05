package com.atividade.devmobile.randomdogs.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.atividade.devmobile.randomdogs.models.FactModel
import com.atividade.devmobile.randomdogs.R
import kotlinx.android.synthetic.main.fact_card.view.*

class FactsRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var list = ArrayList<FactModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return FactViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.fact_card, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is FactViewHolder -> {
                holder.bind(list[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class FactViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val message: TextView = itemView.factMessage

        fun bind(fact: FactModel) {
            message.text = fact.message

        }
    }
}
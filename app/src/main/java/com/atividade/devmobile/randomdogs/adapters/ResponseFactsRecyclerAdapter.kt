package com.atividade.devmobile.randomdogs.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.atividade.devmobile.randomdogs.R
import com.atividade.devmobile.randomdogs.models.ResponseFact
import com.google.gson.JsonElement
import kotlinx.android.synthetic.main.fact_await.view.*

class ResponseFactsRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var list = ArrayList<ResponseFact>()
    private var onClickCheckbox: ((ResponseFact)->Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ResponseFactsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.fact_await, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is ResponseFactsViewHolder -> {
                holder.bind(list[position])
                holder.box.setOnClickListener { onClickCheckbox?.invoke(list[position]) }
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setDataset(facts: ArrayList<JsonElement>) {

        val stringedFacts: ArrayList<ResponseFact> = ArrayList()

        for (fact in facts) {
            val fact = ResponseFact(message = fact.toString(), checked = false)
            stringedFacts.add(fact)
        }

        list = stringedFacts
        notifyDataSetChanged()
    }

    fun checkFact(fact: ResponseFact) {
        fact.checked = !fact.checked
    }

    fun setOnClickCheckbox(callback: (ResponseFact)->Unit) {
        /// Callback para ativar o `onClickCheckBox`, se baseando no `ResponseFact` posicionado
        /// no índice do CheckBox clicado
        this.onClickCheckbox = callback
    }

    class ResponseFactsViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val message: TextView = itemView.factFromResponse
        val box: CheckBox = itemView.factCheckbox
        fun bind(fact: ResponseFact) {
            message.text = fact.message
            box.isChecked = fact.checked


        }
    }
}
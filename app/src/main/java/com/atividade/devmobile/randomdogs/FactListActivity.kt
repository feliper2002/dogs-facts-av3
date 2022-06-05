package com.atividade.devmobile.randomdogs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.atividade.devmobile.randomdogs.adapters.FactsRecyclerAdapter
import com.atividade.devmobile.randomdogs.data.SQLiteHelper
import com.atividade.devmobile.randomdogs.databinding.ActivityFactListBinding
import com.atividade.devmobile.randomdogs.databinding.ActivityMainBinding

class FactListActivity : AppCompatActivity() {

    private lateinit var sqlite: SQLiteHelper

    private lateinit var adapter: FactsRecyclerAdapter
    private lateinit var recyclerView: RecyclerView

    private lateinit var binding: ActivityFactListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFactListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initStorage()

        initView()
        initRecyclerView()

        getFacts()

        // =========================================

        adapter.setOnClickDeleteTodo { deleteFact(it.id) }

    }

    private fun getFacts() {
        val list = sqlite.getFacts()
        adapter.setDataset(list)
    }

    private fun deleteFact(id: String) {
        sqlite.deleteFact(id)
        getFacts()
    }

    private fun initStorage() {
        sqlite = SQLiteHelper(context = this)
    }

    private fun initView() {
        recyclerView = binding.reclyclerView
    }

    private fun initRecyclerView() {
        adapter = FactsRecyclerAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }
}
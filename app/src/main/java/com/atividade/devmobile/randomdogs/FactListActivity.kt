package com.atividade.devmobile.randomdogs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.atividade.devmobile.randomdogs.data.SQLiteHelper
import com.atividade.devmobile.randomdogs.databinding.ActivityFactListBinding
import com.atividade.devmobile.randomdogs.databinding.ActivityMainBinding

class FactListActivity : AppCompatActivity() {

    private lateinit var sqlite: SQLiteHelper

    private lateinit var binding: ActivityFactListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFactListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initStorage()

    }

    private fun getFacts() {
        val list = sqlite.getFacts()

    }

    private fun initStorage() {
        sqlite = SQLiteHelper(context = this)
    }
}
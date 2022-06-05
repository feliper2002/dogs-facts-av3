package com.atividade.devmobile.randomdogs
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.atividade.devmobile.randomdogs.databinding.ActivityMainBinding
import com.atividade.devmobile.randomdogs.repository.DogsClient
import com.atividade.devmobile.randomdogs.repository.Endpoint
import com.bumptech.glide.Glide
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var centerText: TextView
    private lateinit var botao: Button
    private lateinit var botaoSalvar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()

        botao.setOnClickListener {
            getRandom()
        }

        botaoSalvar.setOnClickListener {
            saveFact()
        }



    }

    private fun getRandom() {
        val retrofitClient = DogsClient.get("https://dog-api.kinduff.com/")
        val endpoint = retrofitClient.create(Endpoint::class.java)

        endpoint.getRandomFact().enqueue(object : retrofit2.Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                var body = response.body()

                var facts = body?.get("facts")?.asJsonArray

                var fact = facts?.get(0).toString()

                centerText.text = fact
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun saveFact() {

    }

    private fun initView() {
        botao = binding.rdnBtn
        centerText = binding.factText
        botaoSalvar = binding.saveBtn
    }
}


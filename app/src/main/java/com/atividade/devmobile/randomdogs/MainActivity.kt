package com.atividade.devmobile.randomdogs
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.atividade.devmobile.randomdogs.adapters.ResponseFactsRecyclerAdapter
import com.atividade.devmobile.randomdogs.data.SQLiteHelper
import com.atividade.devmobile.randomdogs.databinding.ActivityMainBinding
import com.atividade.devmobile.randomdogs.models.FactModel
import com.atividade.devmobile.randomdogs.models.ResponseFact
import com.atividade.devmobile.randomdogs.repository.DogsClient
import com.atividade.devmobile.randomdogs.repository.Endpoint
import com.atividade.devmobile.randomdogs.utils.AppConsts
import com.atividade.devmobile.randomdogs.utils.AppFunctions
import com.atividade.devmobile.randomdogs.utils.AppToasts
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var sqlite: SQLiteHelper

    private lateinit var binding: ActivityMainBinding

    private lateinit var responseFactsRecyclerAdapter: ResponseFactsRecyclerAdapter
    private lateinit var recyclerView: RecyclerView

    private lateinit var botao: Button
    private lateinit var botaoSalvar: Button

    private val factsNumber: String = "4"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        initRecyclerView()
        initStorage()
        initBotaoSalvar()

        botao.setOnClickListener {
            getRandom(factsNumber)
        }

        botaoSalvar.setOnClickListener {
            saveFact()
        }

        responseFactsRecyclerAdapter.setOnClickCheckbox {
            checkFact(it)
            initBotaoSalvar()
        }
    }

    private fun checkFact(fact: ResponseFact) {
        /// Marca o fato retornado pela CheckBox, atualizando o valor `checked` do model (true | false)
        responseFactsRecyclerAdapter.checkFact(fact)
    }

    private fun getRandom(number: String?) {
        /// Faz a chamada da API, retornando fatos randômicos
        ///
        /// Recebe como parâmetro um [number: String?], que se conecta à uma @Query do `Retrofit`
        /// para fazer a chamada da API por meio da query `?number=`


        val retrofitClient = DogsClient.get(AppConsts.baseURL)
        val endpoint = retrofitClient.create(Endpoint::class.java)

        val jsonObjectFactKey = "facts"

        endpoint.getRandomFact(number).enqueue(object : retrofit2.Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                val body = response.body()

                val facts = body?.get(jsonObjectFactKey)?.asJsonArray

                val factsArray = ArrayList<JsonElement>()

                /// Passando os elementos do `JsonArray` para uma `ArrayList`
                facts?.forEach { factsArray.add(it) }

                responseFactsRecyclerAdapter.setDataset(facts = factsArray)

            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                AppToasts.show(MainActivity(), "Erro na requisição...")
            }
        })
    }

    private fun saveFact() {
        /// Verifica qual dos fatos retornados está com a CheckBox `true`
        /// Os fatos que estiverem com a CheckBox marcada, serão salvos no local storage do `SQLite`
        val facts = responseFactsRecyclerAdapter.list

        val validFacts = facts.filter { it.checked && (it.message.isNotEmpty()) }
        /// Filtra lista apenas com fatos válidos para serem salvos no SQLite
        /// Fato válido: [ResponseFact.checked = true ; ResponseFact.message.isNotEmpty()]

        if (validFacts.isEmpty()) {
            AppToasts.show(this, "Nenhum fato selecionado...")
        }

        for (fact in validFacts) {
            val exists: Boolean = sqlite.existsAtStorage(fact.message)
            /// Verifica se o fato marcado já existe no local storage do SQLite

            if (!exists) {
                /// Caso o fato nunca tenha sido salvo ou não esteja presente no local storage,
                /// será adicionado como um `FactModel`
                val factModel = FactModel(AppFunctions.randomID(fact.message), fact.message)
                sqlite.insertFact(factModel)
                AppToasts.show(this, "Fato(s) adicionado(s) à lista!")
            } else {
                AppToasts.show(this, "Este(s) fato(s) já está(ão) adicionado(s) na lista!")
            }
        }
    }

    private fun initView() {
        botao = binding.rdnBtn
        botaoSalvar = binding.saveBtn
        recyclerView = binding.responseRecylerView
    }

    private fun initBotaoSalvar() {
        val list = responseFactsRecyclerAdapter.list

        val checkedFacts = list.filter { it.checked }

        when(checkedFacts.isEmpty()) {
            true -> botaoSalvar.isEnabled = false
            false -> botaoSalvar.isEnabled = true
        }
    }

    private fun initRecyclerView() {
        responseFactsRecyclerAdapter = ResponseFactsRecyclerAdapter()
        recyclerView.adapter = responseFactsRecyclerAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun initStorage() {
        sqlite = SQLiteHelper(context = this)
    }

    private fun navController(route: String) {
        /// Método que utiliza o padrão [`Named Routes`] para facilitar a navegação entre telas

        val intent: Intent = when(route) {
            "/facts" -> Intent(this, FactListActivity::class.java)
            "/info" -> Intent(this, AppInfo::class.java)
            else -> Intent(this, MainActivity::class.java)
        }
        startActivity(intent)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = MenuInflater(this)
        menuInflater.inflate(R.menu.menu_item, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.appInfo -> {
                navController("/info")
            }
            R.id.factListMenu -> {
                navController("/facts")
            }
        }

        return super.onOptionsItemSelected(item)
    }
}


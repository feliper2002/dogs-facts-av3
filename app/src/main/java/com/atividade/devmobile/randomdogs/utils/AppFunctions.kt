package com.atividade.devmobile.randomdogs.utils

import kotlin.math.roundToInt

class AppFunctions {

    companion object {
        fun randomID(modifier: String): String {
            /*
            Este método utiliza uma String como uma forma de `modificador` para
            através de uma lógica simples e genérica criar um ID personalizado
            apenas para identificação dos Livros no banco de dados
            */

            var less = modifier.subSequence(0, ((modifier.length / 2).toInt())).toString()

            var lower = less.replace(" ", "").lowercase()

            for (i in 0..4) {
                val random = Math.random().roundToInt()
                lower += "$random"
            }
            lower += "id"

            return lower
        }
    }

}
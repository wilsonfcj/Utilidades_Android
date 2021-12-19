package com.mpfcoding.kotlinflows

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    val countDownFlow = flow {
        val startingValue = 10
        var currentValue = startingValue
        emit(startingValue)
        while (currentValue > 0) {
            delay(500L)
            currentValue--
            emit(currentValue)
        }
    }

    init {
        collectFlow()
    }

    private fun collectFlow() {
        viewModelScope.launch {
            val count = countDownFlow
                .filter { time -> // filtra os valores dentro do escopo
                    time % 2 == 0
                }
                .map { time -> // mapeia valores antigos por novos
                    time + time
                }
                .onEach { time ->
                   println(time)
                }
                .count {
                    it % 2 == 0
                }

            println("The count is $count")
        }
    }
}
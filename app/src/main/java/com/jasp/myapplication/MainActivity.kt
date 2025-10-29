package com.jasp.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.padding
import com.jasp.myapplication.data.local.AppDatabase
import com.jasp.myapplication.data.repository.MusicaRepository
import com.jasp.myapplication.ui.musica.MusicaViewModel
import com.jasp.myapplication.ui.musica.TelaCadastroMusica

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dao = AppDatabase.getDatabase(applicationContext).musicaDao()
        val repository = MusicaRepository(dao)
        val viewModel = MusicaViewModel(repository)

        setContent {
            Scaffold {
                Surface(modifier = Modifier.padding(it)) {
                    TelaCadastroMusica(viewModel)
                }
            }
        }
    }
}

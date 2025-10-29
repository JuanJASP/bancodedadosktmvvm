package com.jasp.myapplication.ui.musica

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jasp.myapplication.data.local.Musica
import com.jasp.myapplication.data.repository.MusicaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class MusicaUiState(
    val listaMusicas: List<Musica> = emptyList(),
    val titulo: String = "",
    val artista: String = "",
    val musicaEmEdicao: Musica? = null
) {
    val textoBotao: String
        get() = if (musicaEmEdicao == null) "Adicionar" else "Atualizar"
}

class MusicaViewModel(private val repository: MusicaRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(MusicaUiState())
    val uiState: StateFlow<MusicaUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.buscarTodas().collect { musicas ->
                _uiState.update { it.copy(listaMusicas = musicas) }
            }
        }
    }

    fun onTituloChange(novoTitulo: String) {
        _uiState.update { it.copy(titulo = novoTitulo) }
    }

    fun onArtistaChange(novoArtista: String) {
        _uiState.update { it.copy(artista = novoArtista) }
    }

    fun onEditar(musica: Musica) {
        _uiState.update {
            it.copy(
                titulo = musica.titulo,
                artista = musica.artista,
                musicaEmEdicao = musica
            )
        }
    }

    fun onDeletar(musica: Musica) {
        viewModelScope.launch {
            repository.deletarMusica(musica)
        }
    }

    fun onSalvar() {
        val state = _uiState.value
        if (state.titulo.isBlank() || state.artista.isBlank()) return

        val musicaParaSalvar = state.musicaEmEdicao?.copy(
            titulo = state.titulo,
            artista = state.artista
        ) ?: Musica(
            titulo = state.titulo,
            artista = state.artista
        )

        viewModelScope.launch {
            if (state.musicaEmEdicao == null) {
                repository.inserirMusica(musicaParaSalvar)
            } else {
                repository.atualizarMusica(musicaParaSalvar)
            }
        }

        limparCampos()
    }

    private fun limparCampos() {
        _uiState.update {
            it.copy(
                titulo = "",
                artista = "",
                musicaEmEdicao = null
            )
        }
    }
}

package com.jasp.myapplication.data.repository

import com.jasp.myapplication.data.local.Musica
import com.jasp.myapplication.data.local.MusicaDao
import kotlinx.coroutines.flow.Flow

class MusicaRepository(private val musicaDao: MusicaDao) {

    fun buscarTodas(): Flow<List<Musica>> = musicaDao.buscarTodas()

    suspend fun inserirMusica(musica: Musica) {
        musicaDao.inserir(musica)
    }

    suspend fun atualizarMusica(musica: Musica) {
        musicaDao.atualizar(musica)
    }

    suspend fun deletarMusica(musica: Musica) {
        musicaDao.deletar(musica)
    }
}

package com.jasp.myapplication.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MusicaDao {

    @Query("SELECT * FROM musicas")
    fun buscarTodas(): Flow<List<Musica>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inserir(musica: Musica)

    @Update
    suspend fun atualizar(musica: Musica)

    @Delete
    suspend fun deletar(musica: Musica)
}

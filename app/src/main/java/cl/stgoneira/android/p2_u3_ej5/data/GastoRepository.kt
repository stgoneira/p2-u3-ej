package cl.stgoneira.android.p2_u3_ej5.data

import android.content.Context
import androidx.room.Room

class GastoRepository(
    private val gastoDao: GastoDao
) {
    suspend fun obtenerTodos():List<Gasto> = gastoDao.getAll()

    suspend fun agregar(gasto:Gasto) = gastoDao.insertAll(gasto)

    suspend fun contarRegistros():Int = gastoDao.count()

    companion object {
        @Volatile
        private var instance: GastoRepository? = null

        fun getInstance(contexto: Context):GastoRepository {
            return instance ?: synchronized(this) {
                instance ?: GastoRepository(
                    Room.databaseBuilder(
                        contexto.applicationContext,
                        AppDatabase::class.java,
                        "gastos.db"
                    ).build().gastoDao()
                ).also {
                    instance = it
                }
            }
        }
    }
}
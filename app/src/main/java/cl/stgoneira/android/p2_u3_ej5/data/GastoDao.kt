package cl.stgoneira.android.p2_u3_ej5.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface GastoDao {
    @Query("SELECT * FROM Gasto")
    suspend fun getAll():List<Gasto>

    @Query("SELECT * FROM Gasto WHERE id = :id")
    suspend fun findById(id:Int):Gasto?

    @Query("SELECT COUNT(*) FROM Gasto")
    suspend fun count():Int

    @Insert
    suspend fun insertAll(vararg gastos: Gasto)

    @Update
    suspend fun update(gasto:Gasto)

    @Delete
    suspend fun delete(gasto:Gasto)
}
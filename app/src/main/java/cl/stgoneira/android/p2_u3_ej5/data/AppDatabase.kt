package cl.stgoneira.android.p2_u3_ej5.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Gasto::class], version = 1)
@TypeConverters(LocalDateConverter::class)
abstract class AppDatabase:RoomDatabase() {
    abstract fun gastoDao():GastoDao
}
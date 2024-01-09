package cl.stgoneira.android.p2_u3_ej5.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.time.LocalDate

class GastoDaoDbHelperImp(contexto:Context)
    : SQLiteOpenHelper(contexto, DB_NAME, null, DB_VERSION)
    , GastoDao
{

    companion object {
        const val DB_NAME = "gastos.db"
        const val DB_VERSION = 1
        const val TABLE_NAME = "gastos"
        const val COL_ID            = "id"
        const val COL_DESCRIPCION   = "descripcion"
        const val COL_FECHA         = "fecha"
        const val COL_CATEGORIA     = "categoria"
        const val COL_MONTO         = "monto"
        const val DB_SQL_CREATE_TABLES = """
            CREATE TABLE IF NOT EXISTS ${TABLE_NAME}(
                ${COL_ID} INTEGER PRIMARY KEY AUTOINCREMENT,
                ${COL_DESCRIPCION} TEXT,
                ${COL_FECHA} INTEGER,
                ${COL_CATEGORIA} TEXT,
                ${COL_MONTO} INTEGER
            );
        """
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(DB_SQL_CREATE_TABLES)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun getAll(): List<Gasto> {
        val cursor = this.readableDatabase.query(
            TABLE_NAME,
            null, // null devuelve todas las columnas
            null,
            null,
            null,
            null,
            null
        )
        val gastos = mutableListOf<Gasto>()
        with(cursor) {
            while(moveToNext()) {
                val id = getInt( getColumnIndexOrThrow(COL_ID) )
                val glosa = getString( getColumnIndexOrThrow(COL_DESCRIPCION) )
                val monto = getInt( getColumnIndexOrThrow(COL_MONTO) )
                val fechaNum = getLong( getColumnIndexOrThrow(COL_FECHA) )
                val fecha = LocalDateConverter().fromTimestamp(fechaNum) ?: LocalDate.now()
                val categoria = getString( getColumnIndexOrThrow(COL_CATEGORIA))
                val gasto = Gasto(id, monto, fecha, categoria, glosa)
                gastos.add(gasto)
            }
        }
        return gastos
    }

    override suspend fun findById(id: Int): Gasto? {
        TODO("Not yet implemented")
    }

    override suspend fun count(): Int {
        TODO("Not yet implemented")
    }

    suspend fun insert(gasto:Gasto) {
        Log.v("GastoDaoDbHelperImp", "::insert()")
        val valores = ContentValues().apply {
            put(COL_CATEGORIA, gasto.categoria)
            put(COL_DESCRIPCION, gasto.descripcion)
            put(COL_FECHA, LocalDateConverter().dateToTimestamp(gasto.fecha))
            put(COL_MONTO, gasto.monto)
        }
        this.writableDatabase.insert(
            TABLE_NAME,
            null,
            valores
        )
    }

    override suspend fun insertAll(vararg gastos: Gasto) {
        gastos.forEach {
            insert(it)
        }
    }

    override suspend fun update(gasto: Gasto) {
        TODO("Not yet implemented")
    }

    override suspend fun delete(gasto: Gasto) {
        TODO("Not yet implemented")
    }
}
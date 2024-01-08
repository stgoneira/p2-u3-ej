package cl.stgoneira.android.p2_u3_ej5.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity
data class Gasto(
    @PrimaryKey(autoGenerate = true) val id:Int,
    val monto:Int,
    val fecha: LocalDate,
    val categoria:String,
    val descripcion:String
)

enum class TipoGasto {
    SALUD,
    COMIDA,
    DIVERSION
}
package cl.stgoneira.android.p2_u3_ej5

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cl.stgoneira.android.p2_u3_ej5.domain.FormatCurrencyUseCase
import cl.stgoneira.android.p2_u3_ej5.domain.FormatDateUseCase
import java.time.LocalDate

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppGastos()
        }
    }
}

data class Gasto(
    val id:Int,
    val monto:Int,
    val fecha:LocalDate,
    val categoria:String,
    val descripcion:String
)

enum class TipoGasto {
    SALUD,
    COMIDA,
    DIVERSION
}

fun gastosDePrueba():List<Gasto> {
    return listOf(
        Gasto(1, 100_000, LocalDate.now(), TipoGasto.COMIDA.toString(), "Supermercado"),
        Gasto(2, 30_000, LocalDate.now(), TipoGasto.SALUD.toString(), "Veterinario"),
        Gasto(3, 40_000, LocalDate.now(), TipoGasto.DIVERSION.toString(), "Salida fin de semana"),
    )
}

@Preview(showSystemUi = true)
@Composable
fun AppGastos() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp)
    ) {
        ListaGastosUI(gastosDePrueba())
    }
}

@Composable
fun ListaGastosUI(
    gastos:List<Gasto>
) {
    LazyColumn() {
        items(gastos){
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = FormatDateUseCase()(it.fecha),
                        style = TextStyle(
                            fontSize = 10.sp
                        )
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column() {
                        Text(it.descripcion)
                        Text(
                            text = FormatCurrencyUseCase()(it.monto),
                            style = TextStyle(
                                fontSize = 10.sp,
                                color = Color.Gray,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }

                Row() {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Filled.Edit, contentDescription = "Editar")
                    }
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Filled.Delete, contentDescription = "Eliminar")
                    }
                }
            }
            Divider()
        }
    }
}
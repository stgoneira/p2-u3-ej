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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import cl.stgoneira.android.p2_u3_ej5.data.Gasto
import cl.stgoneira.android.p2_u3_ej5.domain.FormatCurrencyUseCase
import cl.stgoneira.android.p2_u3_ej5.domain.FormatDateUseCase
import kotlinx.coroutines.launch
import java.time.LocalDate
import cl.stgoneira.android.p2_u3_ej5.data.GastoRepository
import cl.stgoneira.android.p2_u3_ej5.data.TipoGasto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {

    private lateinit var gastoRepository:GastoRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gastoRepository = GastoRepository.getInstance(this)

        lifecycleScope.launch(Dispatchers.IO) {
            gastosDePrueba().forEach {
                gastoRepository.agregar(
                    it
                )
            }
        }

        setContent {
            AppGastos()
        }
    }
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
    val contexto = LocalContext.current
    var gastos by remember {
        mutableStateOf( emptyList<Gasto>() )
    }

    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            gastos = GastoRepository.getInstance(contexto).obtenerTodos()
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { /*TODO*/ }) {
                Icon(Icons.Default.Add, contentDescription = "Agregar")
            }
        },
        modifier = Modifier.padding(horizontal = 10.dp)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = innerPadding.calculateLeftPadding(LayoutDirection.Ltr))
        ) {
            ListaGastosUI(gastos)
        }
    }
}

@Composable
fun IconoGasto(gasto: Gasto) {
    when(TipoGasto.valueOf(gasto.categoria)) {
        TipoGasto.DIVERSION -> Icon(
            imageVector = Icons.Filled.Face,
            contentDescription = TipoGasto.DIVERSION.toString())
        TipoGasto.SALUD -> Icon(
            imageVector = Icons.Filled.Favorite,
            contentDescription = TipoGasto.SALUD.toString())
        TipoGasto.COMIDA -> Icon(
            imageVector = Icons.Filled.Home,
            contentDescription = TipoGasto.SALUD.toString())
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
                    IconoGasto(it)
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
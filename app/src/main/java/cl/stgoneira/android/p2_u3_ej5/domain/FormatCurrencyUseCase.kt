package cl.stgoneira.android.p2_u3_ej5.domain

import java.text.NumberFormat

class FormatCurrencyUseCase {

    private val formatter = NumberFormat.getCurrencyInstance()

    operator fun invoke(monto:Int):String {
        return formatter.format(monto)
    }
}
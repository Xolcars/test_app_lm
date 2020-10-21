package com.testapp.testapp_app.setup.extensions

import android.util.Log
import java.util.*

const val FILTER = "DevFilter"

val Any?.safe get() = Unit

//region Log Methods
fun Any.logDebug(text: String) {
    val message = " \n------------------------------Log\n$text\n------------------------------\n"
    Log.d("$FILTER: ${this::class.java.simpleName}", message)
}

fun Any.logInfo(text: String) {
    val message = "------------------------------Info\n$text\n-----------------------------\n"
    Log.i("$FILTER: ${this::class.java.simpleName}", message)
}

fun Any.logWarn(text: String) {
    val message = " \n----------------------------Warning\n$text\n----------------------------\n"
    Log.w("$FILTER: ${this::class.java.simpleName}", message)
}

fun Any.logError(text: String) {
    val message = " \n-----------------------------Error\n$text\n-----------------------------\n"
    Log.e("$FILTER: ${this::class.java.simpleName}", message)
}

fun Any.logVerbose(text: String) {
    val message = "----------------------------Verbose\n$text\n----------------------------\n"
    Log.v("$FILTER: ${this::class.java.simpleName}", message)
}

fun Any.logApi(text: String) { ///FIXME: El JSON no se muestra como se espera
    when {
        text.contains("{") -> {
            text.replace("{", " {\n\t")
        }
        text.contains("}") -> {
            text.replace("}", " \n}")
        }
        text.contains(",") -> {
            text.replace(",", " ,\n")
        }
        text.contains("[") -> {
            text.replace("[", " [\n\t")
        }
        text.contains("]") -> {
            text.replace("]", " \n]")
        }
        text.contains("&") -> {
            text.replace("&", " &\n")
        }
    }
    val message =
        "API->\n------------------------------------------------------------------\n$text\n------------------------------------------------------------------"
    Log.v("$FILTER: ${this::class.java.simpleName}", message)
}
//endregion Log Methods

inline fun Timer.schedule(
    delay: Long,
    crossinline action: TimerTask.() -> Unit) {
}
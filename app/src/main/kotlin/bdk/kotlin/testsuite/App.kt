package bdk.kotlin.testsuite

class App {
    val greeting: String
        get() = "Hello Bitcoindevkit!"
}

fun main() {
    println(App().greeting)
}

package bdk.kotlin.testsuite

import org.bitcoindevkit.Progress

object LogProgress : Progress {
    override fun update(progress: Float, message: String?) {
        println("Syncing...")
    }
}

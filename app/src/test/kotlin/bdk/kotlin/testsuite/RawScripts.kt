package bdk.kotlin.testsuite

import org.bitcoindevkit.*
import kotlin.test.Test

class RawScripts {

    private val blockchainConfig = BlockchainConfig.Electrum(
        ElectrumConfig(
            "ssl://electrum.blockstream.info:60002",
            null,
            5u,
            null,
            200u
        )
    )

    private val memoryDatabaseConfig = DatabaseConfig.Memory

    @Test
    fun `Build tx from raw script`() {
        val descriptor = Descriptors.singleKeyDescriptor
        val wallet = Wallet(descriptor, null, Network.TESTNET, memoryDatabaseConfig)
        val blockchain = Blockchain(blockchainConfig)
        wallet.sync(blockchain, LogProgress)

        val balance = wallet.getBalance()
        println("Balance is ${balance.total}")

        val outputScript = "00205a2667ad52754e09b754a43b25b4ec4309e5729e983e869374770c8c3136d2a9".toUByteList()
        val scriptAmount: ScriptAmount = ScriptAmount(
            script = Script(outputScript),
            amount = 200000UL
        )

        val txBuilder = TxBuilder()
            .addRecipient(scriptAmount.script, scriptAmount.amount)
            .feeRate(1.2f)
        val psbt = txBuilder.finish(wallet)
        wallet.sign(psbt)
        val rawTx = psbt.extractTx()
        println(rawTx)
    }
}

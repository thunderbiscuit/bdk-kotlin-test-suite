package bdk.kotlin.testsuite

import org.bitcoindevkit.*
import kotlin.test.Test
import java.io.File
import java.nio.file.Files
import kotlin.test.assertTrue

class ListUnspent {

    private fun getTestDataDir(): String {
        return Files.createTempDirectory("bdk-test").toString()
    }

    private fun cleanupTestDataDir(testDataDir: String) {
        File(testDataDir).deleteRecursively()
    }

    // BIP84 descriptor
    private val descriptor = "wpkh([c258d2e4/84h/1h/0h]tpubDDYkZojQFQjht8Tm4jsS3iuEmKjTiEGjG6KnuFNKKJb5A6ZUCUZKdvLdSDWofKi4ToRCwb9poe1XdqfUnP4jaJjCB2Zwv11ZLgSbnZSNecE/0/*)"

    private val blockchainConfig = BlockchainConfig.Electrum(
        ElectrumConfig(
            "ssl://electrum.blockstream.info:60002",
            null,
            5u,
            null,
            100u
        )
    )

    // @Test
    // fun `Sqlite wallet list unspent UTXOs`() {
    //     val testDataDir = getTestDataDir() + "/bdk-wallet.sqlite"
    //     val databaseConfig = DatabaseConfig.Sqlite(SqliteDbConfiguration(testDataDir))
    //     val wallet = Wallet(descriptor, null, Network.TESTNET, databaseConfig)
    //     val blockchain = Blockchain(blockchainConfig)
    //     wallet.sync(blockchain, LogProgress)
    //
    //     val unspentUtxos: List<LocalUtxo> = wallet.listUnspent()
    //     println(unspentUtxos.first())
    //     assertTrue(unspentUtxos.isNotEmpty())
    //     cleanupTestDataDir(testDataDir)
    // }
}

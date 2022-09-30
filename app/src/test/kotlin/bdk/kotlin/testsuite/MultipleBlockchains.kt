package bdk.kotlin.testsuite

import org.bitcoindevkit.*
import kotlin.test.Test

class MultipleBlockchains {

    private val descriptor = "wpkh([c258d2e4/84h/1h/0h]tpubDDYkZojQFQjht8Tm4jsS3iuEmKjTiEGjG6KnuFNKKJb5A6ZUCUZKdvLdSDWofKi4ToRCwb9poe1XdqfUnP4jaJjCB2Zwv11ZLgSbnZSNecE/1/*)"

    private val blockchainConfig1 = BlockchainConfig.Electrum(
        ElectrumConfig(
            url = "ssl://electrum.blockstream.info:60002",
            socks5 = null,
            retry = 5u,
            timeout = 20u,
            stopGap = 100u
        )
    )

    private val blockchainConfig2 = BlockchainConfig.Electrum(
        ElectrumConfig(
            url = "tcp://electrum.blockstream.info:60001",
            socks5 = null,
            retry = 5u,
            timeout = 20u,
            stopGap = 20u
        )
    )

    private val blockchainConfig3 = BlockchainConfig.Electrum(
        ElectrumConfig(
            url = "tcp://testnet.aranguren.org:51001",
            socks5 = null,
            retry = 5u,
            timeout = 20u,
            stopGap = 20u
        )
    )

    @Test
    fun `Create 3 Electrum Blockchains`() {
        val testDataDir = getTestDataDir() + "/bdk-wallet.sqlite"
        val databaseConfig = DatabaseConfig.Sqlite(SqliteDbConfiguration(testDataDir))
        val wallet = Wallet(descriptor, null, Network.TESTNET, databaseConfig)
        val blockchain = Blockchain(blockchainConfig1)
        wallet.sync(blockchain, LogProgress)
        val blockchain2 = Blockchain(blockchainConfig2)
        wallet.sync(blockchain2, LogProgress)
        val blockchain3 = Blockchain(blockchainConfig3)
        wallet.sync(blockchain3, LogProgress)
    }

}
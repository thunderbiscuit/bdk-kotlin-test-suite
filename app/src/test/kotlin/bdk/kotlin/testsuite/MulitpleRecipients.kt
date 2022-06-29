package bdk.kotlin.testsuite

import kotlin.test.assertNotNull
import org.bitcoindevkit.*
import kotlin.test.Test
import java.nio.file.Files
import kotlin.test.fail

class MultipleRecipients {
    private fun getTestDataDir(): String {
        return Files.createTempDirectory("bdk-test").toString()
    }

    private val blockchainConfig = BlockchainConfig.Electrum(
        ElectrumConfig(
            url = "ssl://electrum.blockstream.info:60002",
            socks5 = null,
            retry = 5u,
            timeout = 20u,
            stopGap = 200u
        )
    )

    // @Test
    // fun `Send to multiple recipients`() {
    //     val descriptor = "wpkh(tprv8hwWMmPE4BVNxGdVt3HhEERZhondQvodUY7Ajyseyhudr4WabJqWKWLr4Wi2r26CDaNCQhhxEftEaNzz7dPGhWuKFU4VULesmhEfZYyBXdE/0/*)"
    //     val testDataDir = getTestDataDir() + "/bdk-wallet.sqlite"
    //     val databaseConfig = DatabaseConfig.Sqlite(SqliteDbConfiguration(testDataDir))
    //     val wallet = Wallet(descriptor, null, Network.TESTNET, databaseConfig)
    //     val blockchain = Blockchain(blockchainConfig)
    //     wallet.sync(blockchain, LogProgress)
    //     val balance = wallet.getBalance()
    //
    //     if (balance > 3000u) {
    //         val faucetAddress1 = "tb1ql7w62elx9ucw4pj5lgw4l028hmuw80sndtntxt"
    //         val faucetAddress2 = "mv4rnyY3Su5gjcDNzbMLKBQkBicCtHUtFB"
    //         val txBuilder = TxBuilder()
    //             .addRecipient(faucetAddress1, 1000u)
    //             .addRecipient(faucetAddress2, 1000u)
    //             .feeRate(1.2f)
    //         val psbt = txBuilder.finish(wallet)
    //         wallet.sign(psbt)
    //         blockchain.broadcast(psbt)
    //         val txid = psbt.txid()
    //         println("https://mempool.space/testnet/tx/$txid")
    //         assertNotNull("txid was null", txid)
    //     } else {
    //         val addressInfo = wallet.getAddress(AddressIndex.LAST_UNUSED)
    //         fail("Wallet balance is below 3000 sats (current balance: $balance, send more to ${addressInfo.address} (index ${addressInfo.index}")
    //     }
    // }
}
package bdk.kotlin.testsuite

import org.bitcoindevkit.*
// import org.junit.Assert
import java.io.File
import java.nio.file.Files
import kotlin.test.*

class FeeRate {

    private val memoryDatabaseConfig = DatabaseConfig.Memory

    private val blockchainConfig = BlockchainConfig.Electrum(
        ElectrumConfig(
            "ssl://electrum.blockstream.info:60002",
            null,
            5u,
            null,
            200u
        )
    )

    @Test
    fun `Transaction builder returns PSBT`() {
        val descriptor = Descriptors.singleKeyDescriptor
        val wallet = Wallet(descriptor, null, Network.TESTNET, memoryDatabaseConfig)
        val blockchain = Blockchain(blockchainConfig)
        wallet.sync(blockchain, LogProgress)
        val balance = wallet.getBalance()

        if (balance.total > 2000u) {
            println("balance $balance")
            // send coins back to https://bitcoinfaucet.uo1.net
            val faucetAddress = Address("tb1ql7w62elx9ucw4pj5lgw4l028hmuw80sndtntxt")
            val psbt: PartiallySignedBitcoinTransaction = TxBuilder().addRecipient(faucetAddress.scriptPubkey(), 1000u).feeRate(1.2f).finish(wallet)
            val signatureWorked: Boolean = wallet.sign(psbt)
            println("Signature worked: $signatureWorked")
            println("Psbt data: $psbt")
            println("Psbt data: $psbt")
            println("Psbt data: $psbt")
            val txid = psbt.txid()
        } else {
            println("Balance: $balance")
            val depositAddress = wallet.getAddress(AddressIndex.LAST_UNUSED).address
            fail("Not enough coins to perform the test. Send more testnet coins to: $depositAddress")
        }
    }
}

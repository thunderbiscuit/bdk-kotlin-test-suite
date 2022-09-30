package bdk.kotlin.testsuite

import org.bitcoindevkit.*
// import org.junit.Assert
import kotlin.test.*

class Psbt {

    // Single key descriptor
    private val singleKeyDescriptor = "wpkh(tprv8ZgxMBicQKsPeny1AcBqcv4u4RDpRUFT3DYkRFWzQpZDkbQC1e7Ce5ciXY9GwewcpzTn8qCS65xosMKUWpjK59U21bnDSpFeGjsBduQ3hPM/84h/1h/0h/0/0)"

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
        val descriptor = singleKeyDescriptor
        val wallet = Wallet(descriptor, null, Network.TESTNET, memoryDatabaseConfig)
        val blockchain = Blockchain(blockchainConfig)
        wallet.sync(blockchain, LogProgress)
        val balance = wallet.getBalance()

        if (balance.total > 2000u) {
            println("balance $balance")
            // send coins back to https://bitcoinfaucet.uo1.net
            val faucetAddress = "tb1ql7w62elx9ucw4pj5lgw4l028hmuw80sndtntxt"
            val psbt: PartiallySignedBitcoinTransaction = TxBuilder().addRecipient(Address(faucetAddress).scriptPubkey(), 1000u).feeRate(1.2f).finish(wallet)
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

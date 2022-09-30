package bdk.kotlin.testsuite

import org.bitcoindevkit.*
import java.io.File
import java.nio.file.Files
import kotlin.test.*

class CoinSelection {

    // BIP84 descriptor
    private val descriptor = "wpkh([c258d2e4/84h/1h/0h]tpubDDYkZojQFQjht8Tm4jsS3iuEmKjTiEGjG6KnuFNKKJb5A6ZUCUZKdvLdSDWofKi4ToRCwb9poe1XdqfUnP4jaJjCB2Zwv11ZLgSbnZSNecE/0/*)"

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
    fun `Sqlite wallet sync and get balance`() {
        val testDataDir = getTestDataDir() + "/bdk-wallet.sqlite"
        val databaseConfig = DatabaseConfig.Sqlite(SqliteDbConfiguration(testDataDir))
        val wallet = Wallet(descriptor, null, Network.TESTNET, databaseConfig)
        val blockchain = Blockchain(blockchainConfig)
        wallet.sync(blockchain, LogProgress)
        val balance = wallet.getBalance()
        assertTrue(balance.total > 0u)
        cleanupTestDataDir(testDataDir)
    }

    @Test
    fun `Online wallet sync and get balance`() {
        val wallet = Wallet(descriptor, null, Network.TESTNET, memoryDatabaseConfig)
        val blockchain = Blockchain(blockchainConfig)
        wallet.sync(blockchain, LogProgress)
        val balance = wallet.getBalance()
        assertTrue(balance.total > 0u)
    }

    @Test
    fun `Invalid address on TxBuilder throws`() {
        assertFailsWith(exceptionClass = BdkException.Generic::class) {
            val descriptor = "wpkh([c1ed86ca/84'/1'/0'/0]tprv8hTkxK6QT7fCQx1wbuHuwbNh4STr2Ruz8RwEX7ymk6qnpixtbRG4T99mHxJwKTHPuKQ61heWrrpxZ8jpHj4sbisrQhDxnyx3HoQEZebtraN/*)"
            val wallet = Wallet(descriptor, null, Network.TESTNET, memoryDatabaseConfig)
            val txBuilder = TxBuilder().addRecipient(Address("INVALID_ADDRESS").scriptPubkey(), 1000u).feeRate(1.2f)
            txBuilder.finish(wallet)
        }
    }

    @Test
    fun `Transaction builder do not spend change only works with wallets that have a change descriptor`() {
        assertFailsWith(exceptionClass = BdkException.Generic::class) {
            val descriptor = Descriptors.singleKeyDescriptor
            val wallet = Wallet(descriptor, null, Network.TESTNET, memoryDatabaseConfig)
            val blockchain = Blockchain(blockchainConfig)
            wallet.sync(blockchain, LogProgress)
            val balance = wallet.getBalance()

            if (balance.total > 2000u) {
                println("balance $balance")
                val faucetAddress = "tb1ql7w62elx9ucw4pj5lgw4l028hmuw80sndtntxt"
                val psbt: PartiallySignedBitcoinTransaction = TxBuilder()
                    .addRecipient(Address(faucetAddress).scriptPubkey(), 1000u)
                    .feeRate(1.2f)
                    .doNotSpendChange()
                    .finish(wallet)
                val signatureWorked: Boolean = wallet.sign(psbt)
                println("Signature worked: $signatureWorked")
                // blockchain.broadcast(psbt)
                val txid = psbt.txid()
                // println("https://mempool.space/testnet/tx/$txid")
                assertNotNull("txid was null", txid)
            } else {
                println("Balance: $balance")
                val depositAddress = wallet.getAddress(AddressIndex.LAST_UNUSED)
                fail("Not enough coins to perform the test. Send more testnet coins to: ${depositAddress.address}, index ${depositAddress.index}")
            }
        }
    }

    @Test
    fun `Transaction builder only spend change only works with wallets that have a change descriptor`() {
        assertFailsWith(exceptionClass = BdkException.Generic::class) {
            val descriptor = Descriptors.singleKeyDescriptor
            val wallet = Wallet(descriptor, null, Network.TESTNET, memoryDatabaseConfig)
            val blockchain = Blockchain(blockchainConfig)
            wallet.sync(blockchain, LogProgress)
            val balance = wallet.getBalance()

            if (balance.total > 2000u) {
                println("balance $balance")
                val faucetAddress = "tb1ql7w62elx9ucw4pj5lgw4l028hmuw80sndtntxt"
                val psbt: PartiallySignedBitcoinTransaction = TxBuilder()
                    .addRecipient(Address(faucetAddress).scriptPubkey(), 1000u)
                    .feeRate(1.2f)
                    .onlySpendChange()
                    .finish(wallet)
                val signatureWorked: Boolean = wallet.sign(psbt)
                println("Signature worked: $signatureWorked")
                // blockchain.broadcast(psbt)
                val txid = psbt.txid()
                // println("https://mempool.space/testnet/tx/$txid")
                assertNotNull("txid was null", txid)
            } else {
                println("Balance: $balance")
                val depositAddress = wallet.getAddress(AddressIndex.LAST_UNUSED).address
                fail("Not enough coins to perform the test. Send more testnet coins to: $depositAddress")
            }
        }
    }

    @Test
    fun `Transaction builder do not spend change`() {
            val descriptor: String = Descriptors.singleKeyDescriptor
            val changeDescriptor: String = Descriptors.bip84Descriptor
            val wallet = Wallet(descriptor, changeDescriptor, Network.TESTNET, memoryDatabaseConfig)
            val blockchain = Blockchain(blockchainConfig)
            wallet.sync(blockchain, LogProgress)
            val balance = wallet.getBalance()

            if (balance.total > 2000u) {
                println("balance $balance")
                val faucetAddress = "tb1ql7w62elx9ucw4pj5lgw4l028hmuw80sndtntxt"
                val psbt: PartiallySignedBitcoinTransaction = TxBuilder()
                    .addRecipient(Address(faucetAddress).scriptPubkey(), 1000u)
                    .feeRate(1.2f)
                    .doNotSpendChange()
                    .finish(wallet)
                val signatureWorked: Boolean = wallet.sign(psbt)
                println("Signature worked: $signatureWorked")
                // blockchain.broadcast(psbt)
                val txid = psbt.txid()
                // println("https://mempool.space/testnet/tx/$txid")
                assertNotNull("txid was null", txid)
            } else {
                println("Balance: $balance")
                val depositAddress = wallet.getAddress(AddressIndex.LAST_UNUSED)
                fail("Not enough coins to perform the test. Send more testnet coins to: ${depositAddress.address}, index ${depositAddress.index}")
            }
    }

    @Test
    fun `Transaction builder set absolute fee`() {
        val descriptor = Descriptors.singleKeyDescriptor
        val wallet = Wallet(descriptor, null, Network.TESTNET, memoryDatabaseConfig)
        val blockchain = Blockchain(blockchainConfig)
        wallet.sync(blockchain, LogProgress)
        val balance = wallet.getBalance()

        if (balance.total > 2000u) {
            println("balance $balance")
            val faucetAddress = "tb1ql7w62elx9ucw4pj5lgw4l028hmuw80sndtntxt"
            val psbt: PartiallySignedBitcoinTransaction = TxBuilder()
                .addRecipient(Address(faucetAddress).scriptPubkey(), 1000u)
                .feeAbsolute(900uL)
                .finish(wallet)
            val signatureWorked: Boolean = wallet.sign(psbt)
            println("Signature worked: $signatureWorked")
            // blockchain.broadcast(psbt)
            val txid = psbt.txid()
            // println("https://mempool.space/testnet/tx/$txid")
            assertNotNull("txid was null", txid)
        } else {
            println("Balance: $balance")
            val depositAddress = wallet.getAddress(AddressIndex.LAST_UNUSED).address
            fail("Not enough coins to perform the test. Send more testnet coins to: $depositAddress")
        }
    }

    @Test
    fun `Transaction builder fails if manually selected only with empty selection`() {
        assertFailsWith(exceptionClass = BdkException.NoUtxosSelected::class) {
            val descriptor = Descriptors.singleKeyDescriptor
            val wallet = Wallet(descriptor, null, Network.TESTNET, memoryDatabaseConfig)
            val blockchain = Blockchain(blockchainConfig)
            wallet.sync(blockchain, LogProgress)
            val balance = wallet.getBalance()

            if (balance.total > 2000u) {
                println("balance $balance")
                val faucetAddress = "tb1ql7w62elx9ucw4pj5lgw4l028hmuw80sndtntxt"
                val psbt: PartiallySignedBitcoinTransaction = TxBuilder()
                    .addRecipient(Address(faucetAddress).scriptPubkey(), 1000u)
                    .manuallySelectedOnly()
                    .finish(wallet)
                val signatureWorked: Boolean = wallet.sign(psbt)
                println("Signature worked: $signatureWorked")
                // blockchain.broadcast(psbt)
                val txid = psbt.txid()
                // println("https://mempool.space/testnet/tx/$txid")
                assertNotNull("txid was null", txid)
            } else {
                println("Balance: $balance")
                val depositAddress = wallet.getAddress(AddressIndex.LAST_UNUSED).address
                fail("Not enough coins to perform the test. Send more testnet coins to: $depositAddress")
            }
        }
    }
}
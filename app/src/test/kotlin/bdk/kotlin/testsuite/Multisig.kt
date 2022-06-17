package bdk.kotlin.testsuite

import org.bitcoindevkit.*
import org.junit.Assert
import org.junit.Test
import java.io.File
import java.nio.file.Files
import kotlin.test.assertTrue

class MultisigTest {

    private fun getTestDataDir(): String {
        return Files.createTempDirectory("bdk-test").toString()
    }

    private fun cleanupTestDataDir(testDataDir: String) {
        File(testDataDir).deleteRecursively()
    }

    private val faucetAddress = "mkHS9ne12qx9pS9VojpwU5xtRd4T7X7ZUt"

    private val blockchainConfig = BlockchainConfig.Electrum(
        ElectrumConfig(
            "ssl://electrum.blockstream.info:60002",
            null,
            5u,
            null,
            100u
        )
    )

    private val xprvAlice = "[5fa993c4/84'/1'/0'/0]tprv8hwWMmPE4BVNxGdVt3HhEERZhondQvodUY7Ajyseyhudr4WabJqWKWLr4Wi2r26CDaNCQhhxEftEaNzz7dPGhWuKFU4VULesmhEfZYyBXdE/0/*"
    private val xpubAlice = "[5fa993c4/84'/1'/0'/0]tpubDEdYWBRUCZB3qjfHmgxHde5gGqJZaFzY3qhx2VuxPyi2gYmMDhf6VzxiEeYyz6XdVcDfxX6GvkLC2dE6UKvmJtDyvs7s3hnN9jfQQZikitz/0/*"
    // private val xprvBob   = "[840503f0/84'/1'/0'/0]tprv8hVFghdyB69yzPCaWDh1by9VcoSPswKRwKur87h3zVtVZFvLKJGq6R2bXMpZDaucKmcZg6fXtYZ7HLsm7NNKPuWPB73Nhx7ctWfSdcBKEzJ/0/*"
    private val xpubBob   = "[840503f0/84'/1'/0'/0]tpubDEBHq7gDKTqesrENPsMc1NocBpxL3GWLWdWdQdjMQmgtPkB6wh6RGueThVY45mwbFGyEmiZYpjdZFepE4UaG35m5ZR7DDZrmQWRiN6zyWe5/0/*"





    // series of tests 1
    // a sortedmulti with 1-of-2 signatures required
    private val aliceBobMultisigPublicDescriptor1 = "wsh(sortedmulti(1,$xpubAlice,$xpubBob))"
    private val alicePrivMultisigDescriptor1      = "wsh(sortedmulti(1,$xprvAlice,$xpubBob))"

    @Test
    fun `Get multisig address (1-of-2)`() {
        val testDataDirectory = getTestDataDir()+"/bdk-wallet.sqlite"
        val databaseConfig = DatabaseConfig.Memory
        val wallet = Wallet(aliceBobMultisigPublicDescriptor1, null, Network.TESTNET, databaseConfig)
        val address = wallet.getAddress(AddressIndex.LAST_UNUSED).address
        Assert.assertNotNull(address)
        Assert.assertEquals("tb1qq04ys0gztd8ks2xan4nu8sjtk2fselp4337gn7gjswkqus4h8ekqduv4s3", address)
        cleanupTestDataDir(testDataDirectory)
    }

    @Test
    fun `Sync multisig balance (1-of-2)`() {
        val testDataDirectory = getTestDataDir()+"/bdk-wallet.sqlite"
        val databaseConfig = DatabaseConfig.Memory
        val wallet = Wallet(aliceBobMultisigPublicDescriptor1, null, Network.TESTNET, databaseConfig)
        val blockchain = Blockchain(blockchainConfig)
        wallet.sync(blockchain, LogProgress)
        val balance = wallet.getBalance()
        assertTrue(balance > 0u)
        cleanupTestDataDir(testDataDirectory)
    }

    // @Test
    // fun `Sign Alice part of multisig (1-of-2)`() {
    //     val testDataDirectory = getTestDataDir()+"/bdk-wallet.sqlite"
    //     val databaseConfig = DatabaseConfig.Sqlite(SqliteDbConfiguration(testDataDirectory))
    //     val wallet = Wallet(alicePrivMultisigDescriptor1, null, Network.TESTNET, databaseConfig)
    //     val blockchain = Blockchain(blockchainConfig)
    //     wallet.sync(blockchain, LogProgress)
    //     val txBuilder = TxBuilder().addRecipient(faucetAddress, 1000u).feeRate(2.0f)
    //     val psbt = txBuilder.finish(wallet)
    //     val finalized = wallet.sign(psbt)
    //     assertTrue(finalized)
    //     cleanupTestDataDir(testDataDirectory)
    // }





    // series of tests 2
    // a sortedmulti with 2-of-2 signatures required
    private val aliceBobMultisigPublicDescriptor2 = "wsh(sortedmulti(2,$xpubAlice,$xpubBob))"
    private val alicePrivMultisigDescriptor2      = "wsh(sortedmulti(2,$xprvAlice,$xpubBob))"

    @Test
    fun `Get multisig address (2-of-2)`() {
        val testDataDirectory = getTestDataDir()+"/bdk-wallet.sqlite"
        val databaseConfig = DatabaseConfig.Memory
        val wallet = Wallet(aliceBobMultisigPublicDescriptor2, null, Network.TESTNET, databaseConfig)
        val address = wallet.getAddress(AddressIndex.LAST_UNUSED).address
        Assert.assertNotNull(address)
        Assert.assertEquals("tb1qhwq4ujsghdsrx5q0xezx42r7939v6emd33slce704q0xxvhwlygsyz40wc", address)
        cleanupTestDataDir(testDataDirectory)
    }

    @Test
    fun `Sync multisig balance (2-of-2)`() {
        val testDataDirectory = getTestDataDir()+"/bdk-wallet.sqlite"
        val databaseConfig = DatabaseConfig.Memory
        val wallet = Wallet(aliceBobMultisigPublicDescriptor2, null, Network.TESTNET, databaseConfig)
        val blockchain = Blockchain(blockchainConfig)
        wallet.sync(blockchain, LogProgress)
        val balance = wallet.getBalance()
        assertTrue(balance > 0u)
        cleanupTestDataDir(testDataDirectory)
    }

    // @Test
    // fun `Sign Alice part of multisig (2-of-2)`() {
    //     val testDataDirectory = getTestDataDir()+"/bdk-wallet.sqlite"
    //     val databaseConfig = DatabaseConfig.Memory
    //     val wallet = Wallet(alicePrivMultisigDescriptor2, null, Network.TESTNET, databaseConfig)
    //     val blockchain = Blockchain(blockchainConfig)
    //     wallet.sync(blockchain, LogProgress)
    //     val txBuilder = TxBuilder().addRecipient(faucetAddress, 1000u).feeRate(2.0f)
    //     val psbt = txBuilder.finish(wallet)
    //     val finalized = wallet.sign(psbt)
    //     assertTrue(!finalized)
    //     cleanupTestDataDir(testDataDirectory)
    // }
}

package bdk.kotlin.testsuite

import org.bitcoindevkit.*
import org.junit.Assert
import java.io.File
import java.nio.file.Files
import kotlin.test.Test
import kotlin.test.assertEquals

class OfflineWallet {

    private fun getTestDataDir(): String {
        return Files.createTempDirectory("bdk-test").toString()
    }

    private fun cleanupTestDataDir(testDataDir: String) {
        File(testDataDir).deleteRecursively()
    }

    // BIP84 descriptor
    private val descriptor = "wpkh([c258d2e4/84h/1h/0h]tpubDDYkZojQFQjht8Tm4jsS3iuEmKjTiEGjG6KnuFNKKJb5A6ZUCUZKdvLdSDWofKi4ToRCwb9poe1XdqfUnP4jaJjCB2Zwv11ZLgSbnZSNecE/0/*)"

    private val memoryDatabaseConfig = DatabaseConfig.Memory

    @Test
    fun `Memory wallet new address`() {
        val wallet = Wallet(descriptor, null, Network.TESTNET, memoryDatabaseConfig)
        val address = wallet.getAddress(AddressIndex.NEW).address
        assertEquals("tb1qzg4mckdh50nwdm9hkzq06528rsu73hjxxzem3e", address)
    }

    @Test
    fun `New address index 0`() {
        val wallet = Wallet(descriptor, null, Network.TESTNET, memoryDatabaseConfig)
        val addressIndex: UInt = wallet.getAddress(AddressIndex.NEW).index
        assertEquals<UInt>(0U, addressIndex)
    }

    @Test(expected = BdkException.Descriptor::class)
    fun `Invalid descriptor exception is thrown`() {
        Wallet("invalid-descriptor", null, Network.TESTNET, memoryDatabaseConfig)
    }

    @Test
    fun `Sled wallet new address`() {
        val testDataDir = getTestDataDir()
        val databaseConfig = DatabaseConfig.Sled(SledDbConfiguration(testDataDir, "testdb"))
        val wallet = Wallet(descriptor, null, Network.TESTNET, databaseConfig)
        val address = wallet.getAddress(AddressIndex.NEW).address
        Assert.assertNotNull(address)
        Assert.assertEquals("tb1qzg4mckdh50nwdm9hkzq06528rsu73hjxxzem3e", address)
        cleanupTestDataDir(testDataDir)
    }

    @Test
    fun `Online wallet in memory`() {
        val database = DatabaseConfig.Memory
        val wallet = Wallet(descriptor, null, Network.TESTNET, database)
        Assert.assertNotNull(wallet)
        val network = wallet.getNetwork()
        Assert.assertEquals(network, Network.TESTNET)
    }
}

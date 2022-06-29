package bdk.kotlin.testsuite

import org.bitcoindevkit.*
// import org.junit.Assert
import java.io.File
import java.nio.file.Files
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull

class OfflineWallet {

    private fun getTestDataDir(): String {
        return Files.createTempDirectory("bdk-test").toString()
    }

    private fun cleanupTestDataDir(testDataDir: String) {
        File(testDataDir).deleteRecursively()
    }

    // BIP84 descriptor
    private val descriptor = "wpkh([c258d2e4/84h/1h/0h]tpubDDYkZojQFQjht8Tm4jsS3iuEmKjTiEGjG6KnuFNKKJb5A6ZUCUZKdvLdSDWofKi4ToRCwb9poe1XdqfUnP4jaJjCB2Zwv11ZLgSbnZSNecE/0/*)"

    // Single key descriptor
    private val singleKeyDescriptor = "wpkh(tprv8ZgxMBicQKsPeny1AcBqcv4u4RDpRUFT3DYkRFWzQpZDkbQC1e7Ce5ciXY9GwewcpzTn8qCS65xosMKUWpjK59U21bnDSpFeGjsBduQ3hPM/84h/1h/0h/0/0)"

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

    @Test
    fun `New address single key descriptor`() {
        val wallet = Wallet(singleKeyDescriptor, null, Network.TESTNET, memoryDatabaseConfig)
        val addressInfo1 = wallet.getAddress(AddressIndex.NEW)
        println("${addressInfo1.address}, ${addressInfo1.index}")
        assertEquals<UInt>(0U, addressInfo1.index)

        val addressInfo2: AddressInfo = wallet.getAddress(AddressIndex.NEW)
        println("${addressInfo2.address}, ${addressInfo2.index}")

        val addressInfo3: AddressInfo = wallet.getAddress(AddressIndex.LAST_UNUSED)
        println("${addressInfo3.address}, ${addressInfo3.index}")
    }

    // @Test(expected = BdkException.Descriptor::class)
    // fun `Invalid descriptor exception is thrown`() {
    //     Wallet("invalid-descriptor", null, Network.TESTNET, memoryDatabaseConfig)
    // }

    @Test
    fun `Invalid descriptor throws`() {
        assertFailsWith(BdkException.Descriptor::class) {
            Wallet("invaliddescriptor", null, Network.TESTNET, memoryDatabaseConfig)
        }
    }

    @Test
    fun `Sled wallet new address`() {
        val testDataDir = getTestDataDir()
        val databaseConfig = DatabaseConfig.Sled(SledDbConfiguration(testDataDir, "testdb"))
        val wallet = Wallet(descriptor, null, Network.TESTNET, databaseConfig)
        val address = wallet.getAddress(AddressIndex.NEW).address
        assertNotNull(address)
        assertEquals("tb1qzg4mckdh50nwdm9hkzq06528rsu73hjxxzem3e", address)
        cleanupTestDataDir(testDataDir)
    }

    @Test
    fun `Online wallet in memory`() {
        val database = DatabaseConfig.Memory
        val wallet = Wallet(descriptor, null, Network.TESTNET, database)
        assertNotNull(wallet)
        val network = wallet.getNetwork()
        assertEquals(network, Network.TESTNET)
    }
}

package bdk.kotlin.testsuite

import bdk.kotlin.testsuite.Descriptors.index100PubDescriptor
import org.bitcoindevkit.*
import kotlin.test.*

class GetBlockInfo {

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
    fun `Get best block height`() {
        val blockchain = Blockchain(blockchainConfig)
        val blockHeight: UInt = blockchain.getHeight()
        println("Latest block height is $blockHeight")
    }

    @Test
    fun `Get best block hash`() {
        val blockchain = Blockchain(blockchainConfig)
        val blockHeight: UInt = blockchain.getHeight()
        val blockHash: String = blockchain.getBlockHash(blockHeight)
        println("Latest block $blockHeight hash is $blockHash")
    }
}
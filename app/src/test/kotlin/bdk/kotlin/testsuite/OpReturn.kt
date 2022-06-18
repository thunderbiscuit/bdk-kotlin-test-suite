package bdk.kotlin.testsuite

import com.google.common.io.BaseEncoding
import org.bitcoindevkit.*
import kotlin.test.Test
import kotlin.test.assertNotNull
import kotlin.test.fail

// convert a hexadecimal string to a ByteArray
fun String.toByteArray(): ByteArray {
    return BaseEncoding.base16().decode(this.uppercase())
}

class OpReturn {

    private val memoryDatabaseConfig = DatabaseConfig.Memory
    private val blockchainConfig = BlockchainConfig.Electrum(
        ElectrumConfig(
            "ssl://electrum.blockstream.info:60002",
            null,
            5u,
            null,
            100u
        )
    )

    // @OptIn(ExperimentalUnsignedTypes::class)
    // @Test
    // fun `Build UByte list`() {
    //     // Working with friends is a happy way to live.
    //     val message: ByteArray =
    //         "576f726b696e67207769746820667269656e647320697320612068617070792077617920746f206c6976652e".toByteArray()
    //     println(message)
    //     val messageAsUByteList: List<UByte> = message.asUByteArray().asList()
    //     println(messageAsUByteList)
    // }

    // @OptIn(ExperimentalUnsignedTypes::class)
    // @Test
    // fun OP_RETURN() {
    //     val descriptor =
    //         "wpkh([5fa993c4/84'/1'/0'/0]tprv8hwWMmPE4BVNxGdVt3HhEERZhondQvodUY7Ajyseyhudr4WabJqWKWLr4Wi2r26CDaNCQhhxEftEaNzz7dPGhWuKFU4VULesmhEfZYyBXdE/0/*)"
    //     val wallet = Wallet(descriptor, null, Network.TESTNET, memoryDatabaseConfig)
    //     val blockchain = Blockchain(blockchainConfig);
    //     wallet.sync(blockchain, LogProgress)
    //     val balance = wallet.getBalance()
    //     println("Balance is $balance")
    //
    //     // "What's up Bitcoin Dev Kit"
    //     // utf-8 to string: 57686174277320757020426974636f696e20446576204b6974
    //     val opReturnData: List<UByte> =
    //         "57686174277320757020426974636f696e20446576204b6974".toByteArray().asUByteArray().toList()
    //
    //     if (balance > 2000u) {
    //         // send coins back to faucet
    //         val faucetAddress = "tb1ql7w62elx9ucw4pj5lgw4l028hmuw80sndtntxt"
    //         val txBuilder = TxBuilder()
    //             .addRecipient(faucetAddress, 1000u)
    //             .feeRate(1.2f)
    //             .addData(opReturnData)
    //         val psbt = txBuilder.finish(wallet)
    //         wallet.sign(psbt)
    //         blockchain.broadcast(psbt)
    //         val txid = psbt.txid()
    //         println("https://mempool.space/testnet/tx/$txid")
    //         assertNotNull("txid was null", txid)
    //     } else {
    //         val depositAddress = wallet.getAddress(AddressIndex.LAST_UNUSED)
    //         fail("Send more testnet coins to: ${depositAddress.address} (index ${depositAddress.index})")
    //     }
    // }
}

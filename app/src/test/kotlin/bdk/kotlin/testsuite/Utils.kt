package bdk.kotlin.testsuite

import com.google.common.io.BaseEncoding
import java.io.File
import java.nio.file.Files

fun getTestDataDir(): String {
    return Files.createTempDirectory("bdk-test").toString()
}

fun cleanupTestDataDir(testDataDir: String) {
    File(testDataDir).deleteRecursively()
}

// convert a hexadecimal string to a ByteArray
fun String.toByteArray(): ByteArray {
    return BaseEncoding.base16().decode(this.uppercase())
}

@OptIn(ExperimentalUnsignedTypes::class)
fun String.toUByteList(): List<UByte> {
    val byteArray = BaseEncoding.base16().decode(this.uppercase())
    val uByteArray = byteArray.map { byte ->
        byte.toUByte()
    }
    return uByteArray
}

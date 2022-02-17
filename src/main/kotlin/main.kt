import java.io.FileInputStream
import java.util.*
import kotlin.system.measureTimeMillis
import kotlinx.coroutines.*

const val IP_FILE_PATH = "C:\\Users\\Vladislav\\Desktop\\ecwid\\tst_46.txt"
const val IP_BITSET_SIZE = 1073741824L

fun main(args: Array<String>) {
    //There are 4294967296 combinations of IPv4 bits total, so I allocated 4294967296 bits in 4 sets
    var execTime = 0L
    //for (i in 0..10) {
        var counter = 0
        val ipSets = listOf(
            BitSet(IP_BITSET_SIZE.toInt()),
            BitSet(IP_BITSET_SIZE.toInt()),
            BitSet(IP_BITSET_SIZE.toInt()),
            BitSet(IP_BITSET_SIZE.toInt())
        )
        execTime += measureTimeMillis {
            FileInputStream(IP_FILE_PATH).bufferedReader().use {
                it.forEachLine { ipString ->
                    val split = ipString.split('.')
                    //The number of combination: ip[0]*256^3 + ip[1]*256^2 + ip[2]*256^1 + ip[3]*256^0
                    val bit = split[0].toLong() * 16777216 + split[1].toLong() * 65536 + split[2].toLong() * 256 + split[3].toLong()
                    //The number of set: number of bit/bitset size
                    //The number of bit in set: number of bit % bitset size
                    ipSets[(bit / IP_BITSET_SIZE).toInt()].set((bit % IP_BITSET_SIZE).toInt())
                }
            }
            counter = ipSets.sumOf { it.cardinality() }
        }
        println("Total unique lines: $counter")
    //}
    //execTime /= 10
    println("The program took $execTime ms to complete")
}

suspend fun printSeq(seq: Sequence<String>) {
    seq.forEach { println(it) }
}
import java.io.File
import java.io.FileInputStream
import java.util.*

const val IP_BITSET_SIZE = 1073741824L

fun main(args: Array<String>) {
    //There are 4294967296 combinations of IPv4 bits total, so I allocated 4294967296 bits in 4 sets
    println("Unique IP counter")
    while (true) {
        print("Specify the path to the file with IPs or print 'exit' to quit: \n")
        val filePath = readLine()
        if (filePath.isNullOrEmpty() || filePath.isBlank()) {
            println("You have entered empty path. Please, try again.")
        } else if (filePath == "exit") {
            break
        } else {
            val ipFile = File(filePath)
            if (ipFile.exists() && ipFile.isFile) {
                var counter = 0
                val ipSets = listOf(
                    BitSet(IP_BITSET_SIZE.toInt()),
                    BitSet(IP_BITSET_SIZE.toInt()),
                    BitSet(IP_BITSET_SIZE.toInt()),
                    BitSet(IP_BITSET_SIZE.toInt())
                )
                FileInputStream(ipFile).bufferedReader().use {
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
                println("Total unique lines: $counter")
            } else {
                println("The file you specified doest not exist or it's a directory. Please, try again.")
            }
        }
    }
}
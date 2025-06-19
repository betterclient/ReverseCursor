package io.github.betterclient.reversecursor.common

object LinGanEncoder {
    private val tokens = listOf("lin", "gan", "gu", "li", "guli", "guacha")

    fun encrypt(input: String): String {
        val bytes = input.encodeToByteArray()
        val sb = StringBuilder()
        for (b in bytes) {
            val base6 = toBase6(b.toInt() and 0xFF)
            for (digit in base6) {
                sb.append(tokens[digit]).append(" ")
            }
        }
        return sb.toString().trim()
    }

    fun decrypt(encoded: String): String {
        val parts = encoded.split("\\s+".toRegex())
        if (parts.size % 4 != 0 && !parts.isEmpty()) throw IllegalArgumentException("Invalid encoded string length")

        val byteList = mutableListOf<Byte>()
        for (i in parts.indices step 4) {
            val digits = parts.subList(i, i + 4).map { tokenToDigit(it) }
            val value = fromBase6(digits)
            if (value > 255) throw IllegalArgumentException("Decoded value out of byte range")
            byteList.add(value.toByte())
        }
        return byteList.toByteArray().decodeToString()
    }

    private fun toBase6(value: Int): List<Int> {
        val digits = MutableList(4) { 0 }
        var v = value
        for (i in 3 downTo 0) {
            digits[i] = v % 6
            v /= 6
        }
        return digits
    }

    private fun fromBase6(digits: List<Int>): Int {
        var value = 0
        for (d in digits) {
            if (d !in 0..5) throw IllegalArgumentException("Digit out of range")
            value = value * 6 + d
        }
        return value
    }

    private fun tokenToDigit(token: String): Int {
        val idx = tokens.indexOf(token)
        if (idx == -1) throw IllegalArgumentException("Invalid token: $token")
        return idx
    }
}
package com.placetopay.commerce.util

import android.os.Build
import java.math.BigInteger
import java.net.Inet4Address
import java.net.InetAddress
import java.net.NetworkInterface
import java.security.MessageDigest
import java.security.SecureRandom
import java.text.SimpleDateFormat
import java.util.*


class Commons {
    companion object {
        fun getRandom(): String {
            return BigInteger(130, SecureRandom()).toString()
        }

        fun getCurrentDateFormat(): String {
            return SimpleDateFormat("yyyy-MM-dd'T'HH:mmZ", Locale.getDefault()).format(Date())
        }

        fun getBase64(input: ByteArray): String {
            val encodedBytes: ByteArray = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Base64.getEncoder().encode(input)
            } else {
                android.util.Base64.encode(input, android.util.Base64.NO_WRAP)
            }
            return String(encodedBytes)
        }

        fun getSHA256(input: String): ByteArray {
            val mDigest: MessageDigest = MessageDigest.getInstance("SHA-256")
            return mDigest.digest(input.toByteArray())
        }

        fun getIPAddress(useIPv4: Boolean): String {
            try {
                val interfaces: List<NetworkInterface> =
                    Collections.list(NetworkInterface.getNetworkInterfaces())
                for (intf in interfaces) {
                    val addrs: List<InetAddress> =
                        Collections.list(intf.inetAddresses)
                    for (addr in addrs) {
                        if (!addr.isLoopbackAddress) {
                            val sAddr: String = addr.hostAddress.toUpperCase()
                            val isIPv4: Boolean = addr is Inet4Address
                            if (useIPv4) {
                                if (isIPv4) return sAddr
                            } else {
                                if (!isIPv4) {
                                    val delim = sAddr.indexOf('%') // drop ip6 port suffix
                                    return if (delim < 0) sAddr else sAddr.substring(0, delim)
                                }
                            }
                        }
                    }
                }
            } catch (ex: Exception) {
            } // for now eat exceptions
            return ""
        }
    }
}
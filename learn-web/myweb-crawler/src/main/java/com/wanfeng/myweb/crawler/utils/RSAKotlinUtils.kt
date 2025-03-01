package com.wanfeng.myweb.crawler.utils

import java.security.KeyFactory
import java.security.spec.MGF1ParameterSpec
import java.security.spec.X509EncodedKeySpec
import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.OAEPParameterSpec
import javax.crypto.spec.PSource


class RSAKotlinUtils {
    fun getCorrespondPath(): String {
        val publicKeyPEM = """
        -----BEGIN PUBLIC KEY-----
        MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDLgd2OAkcGVtoE3ThUREbio0Eg
        Uc/prcajMKXvkCKFCWhJYJcLkcM2DKKcSeFpD/j6Boy538YXnR6VhcuUJOhH2x71
        nzPjfdTcqMz7djHum0qSZA0AyCBDABUqCrfNgCiJ00Ra7GmRj+YCK1NJEuewlb40
        JNrRuoEUXpabUzGB8QIDAQAB
        -----END PUBLIC KEY-----
    """.trimIndent()

        val publicKey = KeyFactory.getInstance("RSA").generatePublic(
            X509EncodedKeySpec(
                Base64.getDecoder().decode(
                    publicKeyPEM
                        .replace("-----BEGIN PUBLIC KEY-----", "")
                        .replace("-----END PUBLIC KEY-----", "")
                        .replace("\n", "")
                        .trim()
                )
            )
        )

        val cipher = Cipher.getInstance("RSA/ECB/OAEPPadding").apply {
            init(
                Cipher.ENCRYPT_MODE,
                publicKey,
                OAEPParameterSpec("SHA-256", "MGF1", MGF1ParameterSpec.SHA256, PSource.PSpecified.DEFAULT)
            )
        }
        val timestamp = System.currentTimeMillis()
        return cipher.doFinal("refresh_$timestamp".toByteArray()).joinToString("") { "%02x".format(it) }
    }
}


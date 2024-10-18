package com.omnitech.weatherapptest.utils

import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec
import android.util.Base64
object EncryptionUtil {

    private const val ALGORITHM = "AES"

    // Method to encrypt a string
    fun encrypt(plainText: String, secretKey: String): String {
        val key = SecretKeySpec(secretKey.toByteArray(), ALGORITHM)
        val cipher = Cipher.getInstance(ALGORITHM)
        cipher.init(Cipher.ENCRYPT_MODE, key)
        val encryptedBytes = cipher.doFinal(plainText.toByteArray())
        return Base64.encodeToString(encryptedBytes, Base64.DEFAULT)
    }

    // Method to decrypt a string
    fun decrypt(encryptedText: String, secretKey: String): String {
        val key = SecretKeySpec(secretKey.toByteArray(), ALGORITHM)
        val cipher = Cipher.getInstance(ALGORITHM)
        cipher.init(Cipher.DECRYPT_MODE, key)
        val decodedBytes = Base64.decode(encryptedText, Base64.DEFAULT)
        val decryptedBytes = cipher.doFinal(decodedBytes)
        return String(decryptedBytes)
    }
}
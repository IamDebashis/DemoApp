package com.example.demoapp.util

import com.example.demoapp.data.models.User
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.Translator
import com.google.mlkit.nl.translate.TranslatorOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


sealed class TranslationResult<out T> {
    data class Success<out T>(val translated: T) : TranslationResult<T>()
    data class Error(val errorMessage: String) : TranslationResult<Nothing>()
}


class TranslateUtil {

    private val hindiOption = TranslatorOptions.Builder()
        .setSourceLanguage(TranslateLanguage.ENGLISH)
        .setTargetLanguage(TranslateLanguage.HINDI)
        .build()
    private val bengaliOption = TranslatorOptions.Builder()
        .setSourceLanguage(TranslateLanguage.ENGLISH)
        .setTargetLanguage(TranslateLanguage.BENGALI)
        .build()

    private val bengaliTranslator = Translation.getClient(bengaliOption)
    private val hindiTranslator = Translation.getClient(hindiOption)
    suspend fun translateUsers(
        userList: List<User>,
        lang: Language
    ): TranslationResult<List<User>> {
        val translator = Translation.getClient(
            when (lang) {
                Language.HINDI -> hindiOption
                Language.BENGALI -> bengaliOption
                Language.ENGLISH -> return TranslationResult.Error("Select language between hindi and bengali")
            }
        )
        return withContext(Dispatchers.IO) {
            try {
                val translatedUsers = userList.map { user ->
                    User(
                        id = translateText(user.id, translator),
                        name = translateText(user.name, translator),
                        email = translateText(user.email, translator),
                        address = translateText(user.address, translator),
                        createdAt = translateText(user.createdAt, translator),
                    )
                }
                TranslationResult.Success(translatedUsers)
            } catch (e: Exception) {
                TranslationResult.Error("Translation error: ${e.message}")
            }
        }
    }

    suspend fun translateUser(user: User, lang: Language): TranslationResult<User> {
        val translator = when (lang) {
            Language.HINDI -> hindiTranslator
            Language.BENGALI -> bengaliTranslator
            Language.ENGLISH -> return TranslationResult.Error("Select language between hindi and bengali")
        }

        return withContext(Dispatchers.IO) {
            try {

                val translatedUsers = User(
                    id = translateText(user.id, translator),
                    name = translateText(user.name, translator),
                    email = translateText(user.email, translator),
                    address = translateText(user.address, translator),
                    createdAt = translateText(user.createdAt, translator),
                )

                TranslationResult.Success(translatedUsers)
            } catch (e: Exception) {
                TranslationResult.Error("Translation error: ${e.message}")
            }
        }
    }

    suspend fun translateText(text: String, lang: Language): TranslationResult<String> {
        val translator = when (lang) {
            Language.HINDI -> hindiTranslator
            Language.BENGALI -> bengaliTranslator
            Language.ENGLISH -> return TranslationResult.Error("Select language between hindi and bengali")
        }
        return try {
            val translatedText = translateText(text, translator)
            TranslationResult.Success(translatedText)
        } catch (e: Exception) {
            TranslationResult.Error("Translation error: ${e.message}")
        }
    }

    @Throws(Exception::class)
    private suspend fun translateText(text: String, translator: Translator): String {
        translator.downloadModelIfNeeded().await()
        val translateText = translator.translate(text).await()
        return translateText
    }


}


/**
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.codeCheck

import android.content.Context
import android.os.Parcelable
import androidx.lifecycle.ViewModel
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.android.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import jp.co.yumemi.android.codeCheck.TopActivity.Companion.lastSearchDate
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlinx.parcelize.Parcelize
import org.json.JSONObject
import java.util.*

/**
 * TwoFragment で使う
 */
class OneViewModel : ViewModel() {

    // 検索結果
    fun searchResults(context: Context, inputText: String): List<item> = runBlocking {
        val client = HttpClient(Android)

        return@runBlocking GlobalScope.async {
            val items = mutableListOf<item>()

            try {
                val response: HttpResponse = client.get("https://api.github.com/search/repositories") {
                    header("Accept", "application/vnd.github.v3+json")
                    parameter("q", inputText)
                }

                val jsonBody = JSONObject(response.receive<String>())

                val jsonItems = jsonBody.optJSONArray("items")

                if (jsonItems != null) {
                    /** アイテムの個数分ループする */
                    for (i in 0 until jsonItems.length()) {
                        val jsonItem = jsonItems.optJSONObject(i)
                        val name = jsonItem.optString("full_name")
                        val ownerItem = jsonItem.optJSONObject("owner")
                        val ownerIconUrl = ownerItem?.optString("avatar_url")
                        val language = jsonItem.optString("language")
                        val stargazersCount = jsonItem.optLong("stargazers_count")
                        val watchersCount = jsonItem.optLong("watchers_count")
                        val forksCount = jsonItem.optLong("forks_count")
                        val openIssuesCount = jsonItem.optLong("open_issues_count")

                        items.add(
                            item(
                                name = name,
                                ownerIconUrl = ownerIconUrl ?: "",
                                language = context.getString(R.string.written_language, language),
                                stargazersCount = stargazersCount,
                                watchersCount = watchersCount,
                                forksCount = forksCount,
                                openIssuesCount = openIssuesCount
                            )
                        )
                    }
                    lastSearchDate = Date()
                }
            }
            catch (e: Exception) {
                e.printStackTrace()
            }

            return@async items.toList()
        }.await()
    }
}

@Parcelize
data class item(
    val name: String,
    val ownerIconUrl: String,
    val language: String,
    val stargazersCount: Long,
    val watchersCount: Long,
    val forksCount: Long,
    val openIssuesCount: Long,
) : Parcelable
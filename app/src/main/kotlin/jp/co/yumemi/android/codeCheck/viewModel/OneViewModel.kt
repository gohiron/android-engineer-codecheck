/**
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.codeCheck.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.DiffUtil
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.android.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import jp.co.yumemi.android.codeCheck.R
import jp.co.yumemi.android.codeCheck.TopActivity.Companion.lastSearchDate
import jp.co.yumemi.android.codeCheck.model.Item
import kotlinx.coroutines.runBlocking
import org.json.JSONObject
import java.util.*

/**
 * OneFragment で使う
 */
class OneViewModel : ViewModel() {
    private val items = mutableListOf<Item>()

    // 検索結果
    fun searchGit(context: Context, inputText: String): OneViewModel {
        val client = HttpClient(Android)

        try {
            // github のリポジトリを検索する
            val response: HttpResponse = runBlocking {
                client.get("https://api.github.com/search/repositories") {
                    header("Accept", "application/vnd.github.v3+json")
                    parameter("q", inputText)
                }
            }

            val jsonBody = runBlocking {
                JSONObject(response.receive<String>())
            }

            val jsonItems = jsonBody.optJSONArray("items")

            if (jsonItems != null) {
                /** アイテムの個数分ループする */
                for (i in 0 until jsonItems.length()) {
                    val jsonItem = jsonItems.optJSONObject(i)
                    val ownerItem = jsonItem.optJSONObject("owner")

                    items.add(
                        Item(
                            name = jsonItem.optString("full_name"),
                            ownerIconUrl = ownerItem?.optString("avatar_url") ?: "",
                            language = context.getString(
                                R.string.written_language,
                                jsonItem.optString("language")
                            ),
                            stargazersCount = jsonItem.optLong("stargazers_count"),
                            watchersCount = jsonItem.optLong("watchers_count"),
                            forksCount = jsonItem.optLong("forks_count"),
                            openIssuesCount = jsonItem.optLong("open_issues_count")
                        )
                    )
                }
                lastSearchDate = Date()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return this
    }

    fun getResult(): List<Item> {
        return items.toList()
    }
}

package jp.co.yumemi.android.codeCheck.model

import android.os.Parcelable
import coil.load
import jp.co.yumemi.android.codeCheck.databinding.FragmentTwoBinding
import kotlinx.parcelize.Parcelize

@Parcelize
data class Item(
    val name: String,
    val ownerIconUrl: String,
    val language: String,
    val stargazersCount: Long,
    val watchersCount: Long,
    val forksCount: Long,
    val openIssuesCount: Long,
) : Parcelable {
    fun bind(binding: FragmentTwoBinding) {
        binding.ownerIconView.load(ownerIconUrl)
        binding.nameView.text = name
        binding.languageView.text = language
        var joinString = "$stargazersCount stars"
        binding.starsView.text = joinString
        joinString = "$watchersCount watchers"
        binding.watchersView.text = joinString
        joinString = "$forksCount forks"
        binding.forksView.text = joinString
        joinString = "$openIssuesCount open issues"
        binding.openIssuesView.text = joinString
    }

}

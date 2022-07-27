/**
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.codeCheck

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.load
import jp.co.yumemi.android.codeCheck.TopActivity.Companion.lastSearchDate
import jp.co.yumemi.android.codeCheck.databinding.FragmentTwoBinding

class TwoFragment : Fragment(R.layout.fragment_two) {

    private val args: TwoFragmentArgs by navArgs()

    private var _binding: FragmentTwoBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("検索した日時", lastSearchDate.toString())

        _binding = FragmentTwoBinding.bind(view)

        val item = args.item

        binding.ownerIconView.load(item.ownerIconUrl)
        binding.nameView.text = item.name
        binding.languageView.text = item.language
        var joinString = "${item.stargazersCount} stars"
        binding.starsView.text = joinString
        joinString = "${item.watchersCount} watchers"
        binding.watchersView.text = joinString
        joinString = "${item.forksCount} forks"
        binding.forksView.text = joinString
        joinString = "${item.openIssuesCount} open issues"
        binding.openIssuesView.text = joinString
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

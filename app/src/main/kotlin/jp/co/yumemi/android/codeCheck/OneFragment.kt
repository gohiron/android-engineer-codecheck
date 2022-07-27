/**
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.codeCheck

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.*
import jp.co.yumemi.android.codeCheck.R
import jp.co.yumemi.android.codeCheck.databinding.FragmentOneBinding
import jp.co.yumemi.android.codeCheck.model.ItemListAdapter
import jp.co.yumemi.android.codeCheck.model.Item
import jp.co.yumemi.android.codeCheck.viewModel.OneViewModel

class OneFragment : Fragment(R.layout.fragment_one) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ItemListAdapter(object : ItemListAdapter.OnItemClickListener {
            override fun itemClick(item: Item) {
                val action = OneFragmentDirections.actionRepositoriesFragmentToRepositoryFragment(item = item)
                findNavController().navigate(action)
            }
        })

        val binding = FragmentOneBinding.bind(view)

        val viewModel = OneViewModel()

        val layoutManager = LinearLayoutManager(context)
        val dividerItemDecoration = DividerItemDecoration(context, layoutManager.orientation)

        binding.searchInputText.setOnEditorActionListener { editText, action, _ ->
            // 検索処理
            if (action == EditorInfo.IME_ACTION_SEARCH) {
                editText.text.toString().let {
                    context?.let { it1 ->
                        viewModel.searchGit(it1, it).getResult().apply {
                            adapter.submitList(this)
                        }
                    }
                }
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }

        // RecyclerView にデータをセットする
        binding.recyclerView.also {
            it.layoutManager = layoutManager
            it.addItemDecoration(dividerItemDecoration)
            it.adapter = adapter
        }
    }
}

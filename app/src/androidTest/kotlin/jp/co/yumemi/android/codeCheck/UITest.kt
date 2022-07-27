package jp.co.yumemi.android.codeCheck

import android.view.KeyEvent
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import junit.framework.TestCase
import org.hamcrest.CoreMatchers
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UITest : TestCase() {
    @Test
    fun searchGit() {
        // 検索バーに何も表示されていないことを確認
        val searchBar = Espresso.onView(ViewMatchers.withId(R.id.searchBar))
        searchBar.check(ViewAssertions.matches(CoreMatchers.not(ViewMatchers.isDisplayed())))

        var searchText = Espresso.onView(ViewMatchers.withId(R.id.searchInputText))
        searchText.perform(ViewActions.pressKey(KeyEvent.KEYCODE_SEARCH))
    }
}
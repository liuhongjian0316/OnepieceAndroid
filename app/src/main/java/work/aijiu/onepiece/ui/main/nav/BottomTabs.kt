package work.aijiu.onepiece.ui.main.nav

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector
import work.aijiu.onepiece.R

enum class BottomTabs(
    @StringRes val title: Int,
    val icon: ImageVector,
    val selectIcon: ImageVector,
) {
    TAB_FIRST_PAGE(R.string.tab_first, Icons.Filled.AutoAwesome, Icons.Filled.AutoAwesome),
    TAB_SECOND_PAGE(R.string.tab_second, Icons.Filled.Headset, Icons.Filled.Headset),
    TAB_THIRD_PAGE(R.string.tab_third, Icons.Filled.MenuBook, Icons.Filled.MenuBook),
    TAB_FORTH_PAGE(R.string.tab_forth, Icons.Filled.TripOrigin, Icons.Filled.TripOrigin),
    TAB_FIFTH_PAGE(R.string.tab_fifth, Icons.Filled.Person, Icons.Filled.Person),
}

package work.aijiu.onepiece.page

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import work.aijiu.onepiece.R
import work.aijiu.onepiece.ui.main.HomeViewModel
import work.aijiu.onepiece.ui.main.nav.OnePieceActions
import work.aijiu.onepiece.ui.view.bar.OnePieceAppBar


@Composable
fun ToolPage(
    modifier: Modifier,
    isLand: Boolean,
    homeViewModel: HomeViewModel,
    actions: OnePieceActions,
) {
    Column(modifier = modifier.fillMaxSize()) {
        OnePieceAppBar(title = stringResource(R.string.tab_forth), false)

        Button(onClick = {
            actions.toToolClockIn()
        }) {
            Text(text = stringResource(id = R.string.clock_in))
        }

    }

}



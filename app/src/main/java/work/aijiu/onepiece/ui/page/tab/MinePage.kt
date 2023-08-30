package work.aijiu.onepiece.page

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import work.aijiu.onepiece.R
import work.aijiu.onepiece.ui.main.HomeViewModel
import work.aijiu.onepiece.ui.main.nav.OnePieceActions
import work.aijiu.onepiece.ui.view.bar.OnePieceAppBar

@Composable
fun MinePage(
    modifier: Modifier,
    isLand: Boolean,
    homeViewModel: HomeViewModel,
    actions: OnePieceActions,
) {
    Column(modifier = modifier.fillMaxSize()) {
        OnePieceAppBar(stringResource(id = R.string.tab_fifth), false)

        if (isLand) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                BlogItem(Modifier.weight(0.7f), actions.toTheme)
            }
        } else {
            BlogItem(Modifier, actions.toTheme)
        }
    }
}


@Composable
private fun BlogItem(
    modifier: Modifier = Modifier,
    toTheme: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
    ) {
        ProfilePropertyItem(Icons.Default.Favorite, stringResource(id = R.string.theme_change)) {
            toTheme()
        }
    }
}

@Composable
fun ProfilePropertyItem(
    imageVector: ImageVector,
    title: String,
    onClick: () -> Unit,
) {
    Column(modifier = Modifier
        .clickable {
            onClick()
        }
    ) {
        Divider()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(imageVector = imageVector, contentDescription = "")
            Text(
                text = title,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 5.dp),
                style = MaterialTheme.typography.subtitle2
            )
            Icon(
                Icons.Default.KeyboardArrowRight,
                contentDescription = "",
                modifier = Modifier.wrapContentWidth(Alignment.End),
            )
        }
    }
}







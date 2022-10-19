package co.ke.xently.template.features.customers.ui.list

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import co.ke.xently.template.features.customers.ui.search.CustomerSearchScreen
import co.ke.xently.template.features.ui.XentlyPreview
import co.ke.xently.template.features.ui.theme.XentlyTheme
import co.ke.xently.template.features.utils.ListState
import co.ke.xently.template.features.utils.State

@SuppressLint("VisibleForTests")
@XentlyPreview
@Composable
private fun CustomerListScreenPreview() {
    XentlyTheme {
        CustomerListScreen(
            modifier = Modifier.fillMaxSize(),
            config = CustomerSearchScreen.Config(),
            listState = ListState.Success(emptyList()),
            removeState = State.Success(null),
            isRefreshing = false,
            menuItems = emptySet(),
        )
    }
}
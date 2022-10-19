package co.ke.xently.template.features.customers.ui.detail

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import co.ke.xently.template.features.ui.XentlyPreview
import co.ke.xently.template.features.ui.theme.XentlyTheme
import co.ke.xently.template.features.utils.State

@SuppressLint("VisibleForTests")
@XentlyPreview
@Composable
private fun CustomerDetailScreen() {
    XentlyTheme {
        CustomerDetailScreen(
            modifier = Modifier.fillMaxSize(),
            detailState = State.Success(null),
            saveState = State.Success(null),
            config = CustomerDetailScreen.Config(),
        )
    }
}
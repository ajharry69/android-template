package co.ke.xently.template.features.users.ui.password.request

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
private fun PasswordResetRequestScreenPreview() {
    XentlyTheme {
        PasswordResetRequestScreen(
            modifier = Modifier.fillMaxSize(),
            passwordResetRequestState = State.Success(null),
            config = PasswordResetRequestScreen.Config(),
        )
    }
}
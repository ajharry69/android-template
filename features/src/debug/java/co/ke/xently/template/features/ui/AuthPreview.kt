package co.ke.xently.template.features.ui

import androidx.compose.runtime.Composable
import co.ke.xently.template.features.ui.theme.XentlyTheme
import co.ke.xently.template.libraries.data.source.remote.HttpException
import java.net.SocketTimeoutException

@XentlyPreview
@Composable
private fun ErrorButtonPreview() {
    XentlyTheme {
        ErrorButton(error = HttpException().apply {
            initCause(SocketTimeoutException())
        })
    }
}

@XentlyPreview
@Composable
private fun ErrorButtonRequiresAuthenticationPreview() {
    XentlyTheme {
        ErrorButton(error = HttpException(statusCode = 401))
    }
}

@XentlyPreview
@Composable
private fun PasswordVisibilityToggleInvisiblePreview() {
    XentlyTheme {
        PasswordVisibilityToggle(isVisible = false) {

        }
    }
}

@XentlyPreview
@Composable
private fun PasswordVisibilityToggleVisiblePreview() {
    XentlyTheme {
        PasswordVisibilityToggle(isVisible = true) {

        }
    }
}

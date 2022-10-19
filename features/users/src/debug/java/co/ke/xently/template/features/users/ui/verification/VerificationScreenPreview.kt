package co.ke.xently.template.features.users.ui.verification

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
private fun VerificationScreenPreview() {
    XentlyTheme {
        VerificationScreen(
            modifier = Modifier.fillMaxSize(),
            verificationState = State.Success(null),
            resendVerificationCodeState = State.Success(null),
            config = VerificationScreen.Config(),
        )
    }
}
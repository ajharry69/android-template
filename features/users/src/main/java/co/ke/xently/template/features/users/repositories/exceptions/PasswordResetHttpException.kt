package co.ke.xently.template.features.users.repositories.exceptions

import co.ke.xently.template.libraries.data.source.remote.HttpException

internal class PasswordResetHttpException(
    val oldPassword: List<String> = emptyList(),
    val newPassword: List<String> = emptyList(),
) : HttpException() {
    override fun hasFieldErrors(): Boolean {
        return arrayOf(oldPassword, newPassword).any { it.isNotEmpty() }
    }
}
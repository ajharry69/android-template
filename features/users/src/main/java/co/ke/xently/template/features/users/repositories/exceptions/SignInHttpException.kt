package co.ke.xently.template.features.users.repositories.exceptions

import co.ke.xently.template.libraries.data.source.remote.HttpException

internal class SignInHttpException(
    val email: List<String> = emptyList(),
    val password: List<String> = emptyList(),
) : HttpException() {
    override fun hasFieldErrors(): Boolean {
        return arrayOf(email, password).any { it.isNotEmpty() }
    }
}
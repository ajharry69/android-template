package co.ke.xently.template.features.users.repositories.exceptions

import co.ke.xently.template.libraries.data.source.remote.HttpException

internal class VerificationHttpException(val code: List<String> = emptyList()) : HttpException()
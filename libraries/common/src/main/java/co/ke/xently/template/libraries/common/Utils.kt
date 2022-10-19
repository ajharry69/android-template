package co.ke.xently.template.libraries.common

object Utils {
    val isReleaseBuild by lazy {
        BuildConfig.BUILD_TYPE.lowercase().contains(Regex("^release$"))
    }
}
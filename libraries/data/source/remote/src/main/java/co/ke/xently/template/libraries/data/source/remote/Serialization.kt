package co.ke.xently.template.libraries.data.source.remote

import android.net.Uri
import co.ke.xently.template.libraries.data.source.remote.Exclude.During.*
import com.google.gson.*
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter

object Serialization {
    private fun getExclusionStrategy(during: Exclude.During = BOTH): ExclusionStrategy {
        return object : ExclusionStrategy {
            override fun shouldSkipClass(clazz: Class<*>?): Boolean = false

            override fun shouldSkipField(f: FieldAttributes?): Boolean {
                return if (f == null) true else {
                    val annotation = f.getAnnotation(Exclude::class.java)
                    if (annotation == null) {
                        false
                    } else {
                        annotation.during == during
                    }
                }
            }
        }
    }

    val JSON_CONVERTER: Gson = GsonBuilder()
        .enableComplexMapKeySerialization()
        .addSerializationExclusionStrategy(getExclusionStrategy(SERIALIZATION))
        .addDeserializationExclusionStrategy(getExclusionStrategy(DESERIALIZATION))
        .setExclusionStrategies(getExclusionStrategy())
        .serializeNulls()
        .setDateFormat("yyyy-MM-dd'T'HH:mmZ")
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        // https://www.javadoc.io/doc/com.google.code.gson/gson/2.8.0/com/google/gson/TypeAdapter.html
        .registerTypeAdapter(Uri::class.java, object : TypeAdapter<Uri>() {
            override fun write(out: JsonWriter?, value: Uri?) {
                out?.value(value?.toString())
            }

            override fun read(`in`: JsonReader?): Uri? {
                val uri = `in`?.nextString() ?: return null
                return Uri.parse(uri)
            }
        }.nullSafe())
        /*.setPrettyPrinting()
        .setVersion(1.0)*/
        .create()
}
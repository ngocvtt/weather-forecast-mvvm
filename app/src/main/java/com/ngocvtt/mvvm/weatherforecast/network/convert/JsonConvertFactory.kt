package com.ngocvtt.mvvm.weatherforecast.network.convert

import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

internal class JsonConvertFactory : Converter.Factory() {
    override fun responseBodyConverter(type: Type, annotations: Array<Annotation>, retrofit: Retrofit): Converter<ResponseBody, *>? {
        return JsonResponseBodyConverter<Any>()
    }
}
package com.ngocvtt.mvvm.weatherforecast.model

import com.ngocvtt.mvvm.weatherforecast.model.enum.MessageType

sealed class AppMessage(val type: MessageType, val message: String){
    class ErrorMessage(message: String): AppMessage(MessageType.Error, message)
    class NoticeMessage(message: String): AppMessage(MessageType.Notice, message)
}
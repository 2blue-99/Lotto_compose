package com.example.domain.util

sealed class ExceptionType(val message: String) {
    data object SocketException: ExceptionType(Constants.TOAST_ERROR_WRONG_CONNECTION)
    data object HttpException: ExceptionType(Constants.TOAST_ERROR_PARSE_ERROR)
    data object UnknownHostException: ExceptionType(Constants.TOAST_ERROR_INTERNET_CONNECTED)
    data object SocketTimeOutException: ExceptionType(Constants.TOAST_ERROR_SOCKET_TIMEOUT)
    data object IllegalStateException: ExceptionType(Constants.TOAST_ERROR_ILLEGAL_STATE)
    data object NonDataException: ExceptionType(Constants.TOAST_ERROR_NON_KEYWORD)
    data object JsonException: ExceptionType(Constants.TOAST_ERROR_EMPTY_INPUT)
    class UnHandleError(message: String = Constants.TOAST_ERROR_UNHANDLED): ExceptionType(message)
}
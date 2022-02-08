package com.example.collapsable.network.response

class Response<T> private constructor(var status: Status, var data: T, var message: String?) {
    override fun toString(): String {
        return "Response{" +
                "status=" + status +
                ", data=" + data +
                ", message='" + message + '\'' +
                '}'
    }

    companion object {
        @JvmStatic
        fun <T> success(data: T): Response<T> {
            return Response(Status.SUCCESS, data, null)
        }

        @JvmStatic
        fun <T> error(data: T, message: String?): Response<T> {
            return Response(Status.ERROR, data, message)
        }

        @JvmStatic
        fun <T> loading(data: T, message: String?): Response<T> {
            return Response(Status.LOADING, data, message)
        }
    }
}
package com.pluto.plugins.network.internal

import com.pluto.plugins.network.intercept.NetworkData
import com.pluto.utilities.DebugLog
import okhttp3.Request
import okhttp3.Response
import okio.IOException
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.nio.charset.Charset
import java.util.zip.GZIPInputStream

internal fun Request.convert(): NetworkData.Request {
    val body = this.body?.processBody(this.isGzipped)
    return NetworkData.Request(
        url = this.url.toString(),
        method = this.method,
        body = body,
        headers = this.headerMap(body?.sizeInBytes ?: 0L),
        sentTimestamp = System.currentTimeMillis()
    )
}

internal fun Request.headerMap(contentLength: Long): Map<String, String?> {
    val headerNames = arrayListOf<String>()
    headerNames.addAll(headers.names())
    headerNames.add("content-type")
    headerNames.add("content-length")
    headerNames.sortBy { it }

    val map = mutableMapOf<String, String?>()
    headerNames.forEach {
        val key = it.lowercase().trim()
        when (it) {
            "content-type" -> body?.contentType()?.toString()?.let { value ->
                map[key] = value.trim()
            }
            "content-length" -> map[key] = headers[it]?.trim() ?: run { contentLength.toString() }
            else -> map[key] = headers[it]?.trim()
        }
    }
    return map
}

internal fun Response.convert(body: NetworkData.Body?): NetworkData.Response {
    return NetworkData.Response(
        statusCode = code,
        body = body,
        protocol = protocol.name,
        fromDiskCache = false,
        headers = headersMap(),
        sentTimestamp = sentRequestAtMillis,
        receiveTimestamp = receivedResponseAtMillis
    )
}

private fun Response.headersMap(): Map<String, String?> {
    val headerNames = arrayListOf<String>()
    headerNames.addAll(headers.names())
    headerNames.sortBy { it }

    val map = mutableMapOf<String, String?>()
    headerNames.forEach {
        map[it.lowercase().trim()] = headers[it]?.trim()
    }

    return map
}

@Suppress("TooGenericExceptionCaught")
internal fun doUnZipToString(gzippedMessage: ByteArray?): String {
    var unzippedMessage: String? = null
    try {
        val gzippped = doUnZip(gzippedMessage)
        unzippedMessage = String(gzippped!!, Charset.defaultCharset())
    } catch (e: Throwable) {
        DebugLog.e(LOGTAG, "doUnZipToString 1", e)
    }
    return unzippedMessage ?: ""
}

private fun doUnZip(stream: InputStream?): ByteArray? {
    if (stream !is ByteArrayInputStream) {
        return try {
            doUnZip(stream?.readBytes())
        } catch (e: IOException) {
            DebugLog.e(LOGTAG, "doUnZip 1", e)
            null
//            throw SystemFailedException(e.getMessage(), e)
        }
    }
    var bos: ByteArrayOutputStream? = null
    var gzipStream: InputStream? = null
    var bytes: ByteArray? = null
    try {
        bos = ByteArrayOutputStream()
        gzipStream = GZIPInputStream(stream)
        copy(gzipStream, bos)
        bytes = bos.toByteArray()
    } catch (e: IOException) {
        DebugLog.e(LOGTAG, "error while unzip", e)
    } finally {
        try {
            gzipStream?.close()
            bos?.close()
        } catch (e: IOException) {
            DebugLog.e(LOGTAG, "error while closing stream", e)
        }
    }
    return bytes
}

private fun doUnZip(zippedMessage: ByteArray?): ByteArray? {
    var stream: ByteArrayInputStream? = null
    return try {
        stream = ByteArrayInputStream(zippedMessage)
        doUnZip(stream)
    } finally {
        try {
            stream?.close()
        } catch (e: IOException) {
            DebugLog.e(LOGTAG, "error while closing zippedMessage stream", e)
        }
    }
}

private const val BUFFER_SIZE = 1024

@Throws(IOException::class)
private fun copy(stream: InputStream, out: OutputStream) {
    val buf = ByteArray(BUFFER_SIZE)
    var len: Int
    while (stream.read(buf, 0, buf.size).also { len = it } != -1) {
        out.write(buf, 0, len)
    }
}

private const val LOGTAG = "data-convertor"

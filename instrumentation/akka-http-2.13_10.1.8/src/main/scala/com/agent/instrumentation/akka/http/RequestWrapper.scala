/*
 *
 *  * Copyright 2020 New Relic Corporation. All rights reserved.
 *  * SPDX-License-Identifier: Apache-2.0
 *
 */

package com.agent.instrumentation.akka.http

import akka.http.scaladsl.model.HttpRequest
import com.newrelic.api.agent.{ExtendedRequest, HeaderType}

import java.util
import scala.jdk.javaapi.CollectionConverters

class RequestWrapper(request: HttpRequest) extends ExtendedRequest {

  def getMethod: String = {
    request.method.name
  }

  def getRequestURI: String = {
    request.uri.path.toString()
  }

  def getRemoteUser: String = {
    null
  }

  def getParameterNames: util.Enumeration[_] = {
    CollectionConverters.asJavaEnumeration(request.uri.query().toMap.keysIterator)
  }

  def getParameterValues(name: String): Array[String] = {
    request.uri.query().getAll(name).toArray
  }

  def getAttribute(name: String): AnyRef = {
    null
  }

  def getCookieValue(name: String): String = {
    request.cookies.find(cookie => cookie.name.equalsIgnoreCase(name)).map(cookie => cookie.value).orNull
  }

  def getHeaderType: HeaderType = {
    HeaderType.HTTP
  }

  def getHeader(name: String): String = {
    request.headers.find(header => header.is(name.toLowerCase)).map(header => header.value).orNull
  }

  override def getHeaders(name: String): util.List[String] = {
    val headers = request.headers.filter(header => header.is(name.toLowerCase)).map(header => header.value)
    if (headers.isEmpty) {
      return null
    }
    CollectionConverters.asJava(headers)
  }

}

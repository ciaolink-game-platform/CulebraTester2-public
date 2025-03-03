package com.dtmilano.android.culebratester2

import com.dtmilano.android.culebratester2.location.*
import com.dtmilano.android.culebratester2.location.ObjectStore
import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.application.*
import io.ktor.auth.Authentication
import io.ktor.features.*
import io.ktor.gson.gson
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.content.TextContent
import io.ktor.http.content.resources
import io.ktor.http.content.static
import io.ktor.http.withCharset
import io.ktor.jackson.jackson
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Locations
import io.ktor.locations.get
import io.ktor.locations.post
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.response.respondBytes
import io.ktor.response.respondRedirect
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.route
import io.ktor.routing.routing
import io.ktor.server.engine.ShutDownUrl
import io.ktor.swagger.experimental.HttpException
import io.swagger.server.models.ClickBody
import io.swagger.server.models.Locale
import io.swagger.server.models.PerformTwoPointerGestureBody
import io.swagger.server.models.Selector
import io.swagger.server.models.SwipeBody
import io.swagger.server.models.Text
import java.io.File

@KtorExperimentalLocationsAPI
@Suppress("unused")
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {

    val shutdownUrl = "/quit"

    install(CallLogging) {
    }

    install(Authentication) {
    }

    // This feature enables compression automatically when accepted by the client.
    install(Compression) {
    }

    // See https://enable-cors.org
    install(CORS) {
        anyHost()
    }

    install(ContentNegotiation) {
        gson {
        }

        jackson {
            enable(SerializationFeature.INDENT_OUTPUT)
        }
    }

    install(Locations) {
    }

    install(ShutDownUrl.ApplicationCallFeature) {
        // The URL that will be intercepted (you can also use the application.conf's ktor.deployment.shutdown.url key)
        shutDownUrl = shutdownUrl
        // A function that will be executed to get the exit code of the process
        exitCodeSupplier = { 0 } // ApplicationCall.() -> Int
    }

    routing {
        trace {
            application.log.trace(it.buildText())
            println("🚜")
            println(it.buildText())
        }

        get("/") {
            val local = call.request.local
            val scheme = local.scheme
            val host = local.host
            val port = local.port
            call.respondText(
                "CulebraTester2: Go to ${scheme}://${host}:${port}/help for usage details.\n",
                contentType = ContentType.Text.Plain
            )
        }

        // Static feature. Try to access `/static/ktor_logo.svg`
        static("/static") {
            resources("static")
        }

        get("/json/gson") {
            call.respond(mapOf("hello" to "world"))
        }

        get("/json/jackson") {
            call.respond(mapOf("hello" to "world"))
        }

        get<Help> {
            call.respond(it.response())
        }

        route("/v2") {

            get<Culebra.Info> {
                call.respond(it.response())
            }

            get<Culebra.Help> {
                call.respond(it.response())
            }

            get<Culebra.Help.Query> {
                call.respond(it.response())
            }

            get<Culebra.Quit> {
                println("Going to quit...")
                call.respondRedirect(shutdownUrl, permanent = true)
            }

            get<Configurator.GetWaitForIdleTimeout> {
                call.respond(it.response())
            }

            get<Configurator.SetWaitForIdleTimeout> {
                call.respond(it.response())
            }

            get<Device.DisplayRealSize> {
                call.respond(it.response())
            }

            get<Device.Dumpsys> {
                call.respond(it.response())
            }

            get<Device.Locale.Get> {
                call.respond(it.response())
            }

            post<Device.Locale.Post> {
                // We have to get the body as ktor doesn't do it
                // see https://github.com/ktorio/ktor/issues/190
                // also, it.body is null here
                // println("body ${it.body}");
                val body = call.receive<Locale>()
                call.respond(it.response(body))
            }

            get<Device.WaitForNewToast> {
                call.respond(it.response())
            }

            get<ObjectStore.Clear> {
                call.respond(it.response())
            }

            get<ObjectStore.List> {
                call.respond(it.response())
            }

            get<ObjectStore.Remove> {
                call.respond(it.response())
            }

            get<TargetContext.StartActivity> {
                call.respond(it.response())
            }

            get<UiDevice.ClearLastTraversedText> {
                call.respond(it.response())
            }

            get<UiDevice.Click.Get> {
                call.respond(it.response())
            }

            post<UiDevice.Click.Post> {
                // We have to get the body as ktor doesn't do it
                // see https://github.com/ktorio/ktor/issues/190
                // also, it.body is null here
                // println("body ${it.body}");
                val body = call.receive<ClickBody>()
                call.respond(it.response(body))
            }

            get<UiDevice.DisplayHeight> {
                call.respond(it.response())
            }

            get<UiDevice.DisplayWidth> {
                call.respond(it.response())
            }

            get<UiDevice.Drag> {
                call.respond(it.response())
            }

            get<UiDevice.DumpWindowHierarchy> {
                // We may check call.request.headers["accept"] to determine if it's xml/json
                call.respondText(it.response())
            }

            get<UiDevice.DisplayRotation> {
                call.respond(it.response())
            }

            get<UiDevice.DisplaySizeDp> {
                call.respond(it.response())
            }

            get<UiDevice.FindObject.Get> {
                call.respond(it.response())
            }

            post<UiDevice.FindObject.Post> {
                // We have to get the body as ktor doesn't do it
                // see https://github.com/ktorio/ktor/issues/190
                // also, it.body is null here
                // println("body ${it.body}");
                val selector = call.receive<Selector>()
                call.respond(it.response(selector))
            }

            get<UiDevice.FindObjects.Get> {
                call.respond(it.response())
            }

            post<UiDevice.FindObjects.Post> {
                // We have to get the body as ktor doesn't do it
                // see https://github.com/ktorio/ktor/issues/190
                // also, it.body is null here
                // println("body ${it.body}");
                val selector = call.receive<Selector>()
                call.respond(it.response(selector))
            }

            get<UiDevice.FreezeRotation> {
                call.respond(it.response())
            }

            get<UiDevice.CurrentPackageName> {
                call.respond(it.response())
            }

            get<UiDevice.IsNaturalOrientation> {
                call.respond(it.response())
            }

            get<UiDevice.IsScreenOn> {
                call.respond(it.response())
            }

            get<UiDevice.LastTraversedText> {
                call.respond(it.response())
            }

            get<UiDevice.PressBack> {
                call.respond(it.response())
            }

            get<UiDevice.PressDPadCenter> {
                call.respond(it.response())
            }

            get<UiDevice.PressDPadLeft> {
                call.respond(it.response())
            }

            get<UiDevice.PressDPadRight> {
                call.respond(it.response())
            }

            get<UiDevice.PressDPadUp> {
                call.respond(it.response())
            }

            get<UiDevice.PressDPadDown> {
                call.respond(it.response())
            }

            get<UiDevice.PressDelete> {
                call.respond(it.response())
            }

            get<UiDevice.PressEnter> {
                call.respond(it.response())
            }

            get<UiDevice.PressHome> {
                call.respond(it.response())
            }

            get<UiDevice.PressKeyCode> {
                call.respond(it.response())
            }

            get<UiDevice.PressRecentApps> {
                call.respond(it.response())
            }

            get<UiDevice.ProductName> {
                call.respond(it.response())
            }

            get<UiDevice.HasObject.Get> {
                call.respond(it.response())
            }

            post<UiDevice.HasObject.Post> {
                // We have to get the body as ktor doesn't do it
                // see https://github.com/ktorio/ktor/issues/190
                // also, it.body is null here
                // println("body ${it.body}");
                val selector = call.receive<Selector>()
                call.respond(it.response(selector))
            }

            get<UiDevice.Pixel> {
                call.respond(it.response())
            }

            get<UiDevice.Screenshot> {
                call.respondImage(it.response())
            }

            get<UiDevice.Swipe.Get> {
                call.respond(it.response())
            }

            post<UiDevice.Swipe.Post> {
                // We have to get the body as ktor doesn't do it
                // see https://github.com/ktorio/ktor/issues/190
                // also, it.body is null here
                // println("body ${it.body}");
                val body = call.receive<SwipeBody>()
                call.respond(it.response(body))
            }

            get<UiDevice.UnfreezeRotation> {
                call.respond(it.response())
            }

            get<UiDevice.Wait> {
                call.respond(it.response())
            }

            get<UiDevice.WaitForIdle> {
                call.respond(it.response())
            }

            get<UiDevice.WaitForWindowUpdate> {
                call.respond(it.response())
            }

            get<UiObject.ClearTextField> {
                call.respond(it.response())
            }

            get<UiObject.Click> {
                call.respond(it.response())
            }

            get<UiObject.ClickAndWaitForNewWindow> {
                call.respond(it.response())
            }

            get<UiObject.Dump> {
                call.respond(it.response())
            }

            get<UiObject.Exists> {
                call.respond(it.response())
            }

            get<UiObject.GetBounds> {
                call.respond(it.response())
            }

            get<UiObject.GetChild> {
                call.respond(it.response())
            }

            get<UiObject.GetChildCount> {
                call.respond(it.response())
            }

            get<UiObject.GetClassName> {
                call.respond(it.response())
            }

            get<UiObject.GetContentDescription> {
                call.respond(it.response())
            }

            post<UiObject.PerformTwoPointerGesture.Post> {
                // We have to get the body as ktor doesn't do it
                // see https://github.com/ktorio/ktor/issues/190
                // also, it.body is null here
                // println("body ${it.body}");
                val body = call.receive<PerformTwoPointerGestureBody>()
                call.respond(it.response(body))
            }

            get<UiObject.PinchIn> {
                call.respond(it.response())
            }

            get<UiObject.PinchOut> {
                call.respond(it.response())
            }

            get<UiObject.WaitForExists> {
                call.respond(it.response())
            }

            get<UiObject2.Clear> {
                call.respond(it.response())
            }

            get<UiObject2.Click> {
                call.respond(it.response())
            }

            get<UiObject2.ClickAndWait> {
                call.respond(it.response())
            }

            get<UiObject2.Dump> {
                call.respond(it.response())
            }

            get<UiObject2.FindObject.Get> {
                call.respond(it.response())
            }

            post<UiObject2.FindObject.Post> {
                // We have to get the body as ktor doesn't do it
                // see https://github.com/ktorio/ktor/issues/190
                // also, it.body is null here
                // println("body ${it.body}");
                val selector = call.receive<Selector>()
                call.respond(it.response(selector))
            }

            get<UiObject2.GetChildCount> {
                call.respond(it.response())
            }

            get<UiObject2.GetChildren> {
                call.respond(it.response())
            }

            get<UiObject2.GetParent> {
                call.respond(it.response())
            }

            get<UiObject2.GetContentDescription> {
                call.respond(it.response())
            }

            get<UiObject2.GetText> {
                call.respond(it.response())
            }

            get<UiObject2.LongClick> {
                call.respond(it.response())
            }

            get<UiObject2.SetText.Get> {
                call.respond(it.response())
            }

            post<UiObject2.SetText.Post> {
                // We have to get the body as ktor doesn't do it
                // see https://github.com/ktorio/ktor/issues/190
                // also, it.body is null here
                // println("body ${it.body}");
                val text = call.receive<Text>()
                call.respond(it.response(text))
            }

            get<Until.Dump> {
                call.respond(it.response())
            }

            get<Until.FindObject.Get> {
                call.respond(it.response())
            }

            post<Until.FindObject.Post> {
                // We have to get the body as ktor doesn't do it
                // see https://github.com/ktorio/ktor/issues/190
                // also, it.body is null here
                // println("body ${it.body}");
                val selector = call.receive<Selector>()
                call.respond(it.response(selector))
            }

            get<Until.FindObjects.Get> {
                call.respond(it.response())
            }

            post<Until.FindObjects.Post> {
                // We have to get the body as ktor doesn't do it
                // see https://github.com/ktorio/ktor/issues/190
                // also, it.body is null here
                // println("body ${it.body}");
                val selector = call.receive<Selector>()
                call.respond(it.response(selector))
            }

            get<Until.NewWindow> {
                call.respond(it.response())
            }

        }

        // Handles all the other non-matched routes returning a 404 not found.
        route("{...}") {
            println("🦄")
            handle {
                println("🐹 not found:")
                println(call.request.local.uri)
                if (call.request.local.uri != shutdownUrl) {
                    call.respond(HttpStatusCode.NotFound, "${call.request.local.uri} not found")
                }
            }
        }

        install(StatusPages) {

            exception<AuthenticationException> { cause ->
                call.respond(HttpStatusCode.Unauthorized)
            }

            exception<AuthorizationException> { cause ->
                call.respond(HttpStatusCode.Forbidden)
            }

            exception<HttpException> { cause ->
                call.respond(cause.code, cause.description + "\n")
            }

            exception<Throwable> { cause ->
                val msg = "🛑 ERROR: $cause\n"
                System.err.print(msg)
                System.err.println(cause.message)
                cause.printStackTrace(System.err)
                call.respond(HttpStatusCode.InternalServerError, msg)
            }

            status(HttpStatusCode.NotFound) {
                call.respond(
                    TextContent(
                        "⛔️ ${it.value} ${it.description}",
                        ContentType.Text.Plain.withCharset(Charsets.UTF_8),
                        it
                    )
                )
            }
        }

        CulebraTesterServer().apply {
            println("++++ $this ++++")
        }
    }
}

/**
 * Sends the content of [file] as a response using the provided [contentType].
 */
suspend inline fun ApplicationCall.respondImage(
    file: File,
    contentType: ContentType = ContentType.Image.PNG
) {
    // for some reason this doesn't work and return a 500 error
    // response.headers.append(HttpHeaders.ContentType, ContentType.Image.PNG.toString())
    // so we cannot use respondFile
    //respondFile(file)
    val bytes = file.readBytes()
    respondBytes(bytes, contentType, HttpStatusCode.OK)
}

class AuthenticationException : RuntimeException()
class AuthorizationException : RuntimeException()

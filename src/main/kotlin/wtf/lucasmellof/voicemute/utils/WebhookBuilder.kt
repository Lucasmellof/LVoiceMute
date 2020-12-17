package wtf.lucasmellof.voicemute.utils

import club.minnced.discord.webhook.WebhookClient
import club.minnced.discord.webhook.WebhookClientBuilder
import club.minnced.discord.webhook.send.WebhookEmbedBuilder
import wtf.lucasmellof.voicemute.LVoiceMute
import java.util.concurrent.ThreadFactory

/* 
 * @author Lucasmellof, Lucas de Mello Freitas created on 16/12/2020
 */
class WebhookBuilder(private val plugin: LVoiceMute) {
    var webhook: WebhookClient? = null
    fun sendWebhook(message: WebhookEmbedBuilder) {
        if (webhook == null) return
        webhook!!.send(message.build())
    }
    fun load() {
        val builder = WebhookClientBuilder(plugin.configCache.webhook)
        builder.setThreadFactory { job: Runnable? ->
            val thread = Thread(job)
            thread.name = "WebookThread"
            thread.isDaemon = true
            thread
        }
        webhook = builder.build()
    }
}
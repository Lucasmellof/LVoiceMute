package wtf.lucasmellof.voicemute.utils

import club.minnced.discord.webhook.WebhookClient
import club.minnced.discord.webhook.WebhookClientBuilder
import club.minnced.discord.webhook.send.WebhookEmbedBuilder
import wtf.lucasmellof.voicemute.LVoiceMute
import club.minnced.discord.webhook.send.WebhookMessageBuilder




/* 
 * @author Lucasmellof, Lucas de Mello Freitas created on 16/12/2020
 */
class WebhookBuilder(private val plugin: LVoiceMute) {
    var webhook: WebhookClient? = null
    fun sendWebhook(message: WebhookEmbedBuilder) {
        if (webhook == null) return
        val builder = WebhookMessageBuilder()
        builder.setUsername("LVoiceMute") // use this username
        builder.addEmbeds(message.build())
        webhook!!.send(builder.build())
    }
    fun load() {
        var url = plugin.configCache.webhook
        val builder = WebhookClientBuilder(url)
        builder.setThreadFactory { job: Runnable? ->
            val thread = Thread(job)
            thread.name = "WebhookThread"
            thread.isDaemon = true
            thread
        }
        webhook = builder.build()
    }
    fun reload() {
        webhook = null
        load()
    }
}
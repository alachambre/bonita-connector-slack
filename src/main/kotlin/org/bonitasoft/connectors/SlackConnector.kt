package org.bonitasoft.connectors

import org.bonitasoft.engine.connector.AbstractConnector
import org.bonitasoft.engine.connector.ConnectorValidationException
import java.util.logging.Logger
import com.slack.api.Slack
import com.slack.api.methods.MethodsClient
import com.slack.api.methods.request.chat.ChatPostMessageRequest
import java.io.IOException
import com.slack.api.methods.SlackApiException
import org.bonitasoft.engine.connector.ConnectorException

open class SlackConnector : AbstractConnector {

    val logger = Logger.getLogger(SlackConnector::class.java.name)

    companion object {
        const val TOKEN_INPUT = "tokenInput"
        const val ID_INPUT = "channelIdInput"
        const val MESSAGE_INPUT = "messageInput"
        
        const val TS_OUTPUT = "tsOutput"
    }

    constructor() : super()

    override fun validateInputParameters() {
        checkMandatoryStringInput(TOKEN_INPUT)
        checkMandatoryStringInput(ID_INPUT)
        checkMandatoryStringInput(MESSAGE_INPUT)
    }

    fun checkMandatoryStringInput(inputName: String) {
        val value = getInputParameter(inputName)
        if (!(value is String)) {
            throw ConnectorValidationException(this, "'$inputName' parameter must be a String.")
        } else if (value.isEmpty()) {
            throw ConnectorValidationException(this, "Mandatory parameter '$inputName' is missing.")
        }
    }

    fun setInputParameter(name: String, value: Any) {
        super.setInputParameters(mapOf(name to value))
    }

    fun getOutputParameter(name: String): Any? {
        return super.getOutputParameters()[name]
    }

    public override fun executeBusinessLogic() {
        val client = Slack.getInstance()
        val token = getInputParameter(TOKEN_INPUT) as String; // We do not want to log the secret token!
        val channel = getAndLogStringParameter(ID_INPUT);
        val message = getAndLogStringParameter(MESSAGE_INPUT);
        val result = client
            .methods(token)
            .chatPostMessage { it
                .channel(channel)
                .text(message)
            }
        if (result.isOk()) {
            setOutputParameter(TS_OUTPUT, result.getTs())
        } else {
            throw ConnectorException("Send slack message failed with the following error: ${result.getError()}")
        }
    }
    
    public fun getAndLogStringParameter(parameterName : String) : String {
        val value : String = getInputParameter(parameterName) as String
        logger.info({ -> "$parameterName: $value"})
        return value
    }

    /**
     * [Optional] Open a connection to remote server
     */
    override fun connect() {}

    /**
     * [Optional] Close connection to remote server
     */
    override fun disconnect() {}
}
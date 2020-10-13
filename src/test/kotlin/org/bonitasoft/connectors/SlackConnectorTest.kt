package org.bonitasoft.connectors

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.bonitasoft.engine.connector.ConnectorValidationException
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class SlackConnectorTest {

    lateinit var connector: SlackConnector

    @BeforeEach
    fun setUp() {
        connector = SlackConnector()
    }

    @Test
    fun `should throw exception if mandatory input is missing`() {
        val params1 = mapOf(SlackConnector.TOKEN_INPUT to null, SlackConnector.ID_INPUT to "id", SlackConnector.MESSAGE_INPUT to "message")
        val params2 = mapOf(SlackConnector.TOKEN_INPUT to "token", SlackConnector.ID_INPUT to null, SlackConnector.MESSAGE_INPUT to "message")
        val params3 = mapOf(SlackConnector.TOKEN_INPUT to "token", SlackConnector.ID_INPUT to "id", SlackConnector.MESSAGE_INPUT to null)

        connector.setInputParameters(params1)
        assertThatThrownBy { connector.validateInputParameters() }
                .isExactlyInstanceOf(ConnectorValidationException::class.java)
        
        connector.setInputParameters(params2)
        assertThatThrownBy { connector.validateInputParameters() }
                .isExactlyInstanceOf(ConnectorValidationException::class.java)
        
        connector.setInputParameters(params3)
        assertThatThrownBy { connector.validateInputParameters() }
                .isExactlyInstanceOf(ConnectorValidationException::class.java)
    }

    @Test
    fun `should throw exception if mandatory input is empty`() {
        val params1 = mapOf(SlackConnector.TOKEN_INPUT to "", SlackConnector.ID_INPUT to "id", SlackConnector.MESSAGE_INPUT to "message")
        val params2 = mapOf(SlackConnector.TOKEN_INPUT to "token", SlackConnector.ID_INPUT to "", SlackConnector.MESSAGE_INPUT to "message")
        val params3 = mapOf(SlackConnector.TOKEN_INPUT to "token", SlackConnector.ID_INPUT to "id", SlackConnector.MESSAGE_INPUT to "")

        connector.setInputParameters(params1)
        assertThatThrownBy { connector.validateInputParameters() }
                .isExactlyInstanceOf(ConnectorValidationException::class.java)
        
        connector.setInputParameters(params2)
        assertThatThrownBy { connector.validateInputParameters() }
                .isExactlyInstanceOf(ConnectorValidationException::class.java)
        
        connector.setInputParameters(params3)
        assertThatThrownBy { connector.validateInputParameters() }
                .isExactlyInstanceOf(ConnectorValidationException::class.java)
    }

    @Test
    fun `should throw exception if mandatory input is not a string`() {
        val params1 = mapOf(SlackConnector.TOKEN_INPUT to 1, SlackConnector.ID_INPUT to "id", SlackConnector.MESSAGE_INPUT to "message")
        val params2 = mapOf(SlackConnector.TOKEN_INPUT to "token", SlackConnector.ID_INPUT to 1, SlackConnector.MESSAGE_INPUT to "message")
        val params3 = mapOf(SlackConnector.TOKEN_INPUT to "token", SlackConnector.ID_INPUT to "id", SlackConnector.MESSAGE_INPUT to 1)

        connector.setInputParameters(params1)
        assertThatThrownBy { connector.validateInputParameters() }
                .isExactlyInstanceOf(ConnectorValidationException::class.java)
        
        connector.setInputParameters(params2)
        assertThatThrownBy { connector.validateInputParameters() }
                .isExactlyInstanceOf(ConnectorValidationException::class.java)
        
        connector.setInputParameters(params3)
        assertThatThrownBy { connector.validateInputParameters() }
                .isExactlyInstanceOf(ConnectorValidationException::class.java)
    }

    @Test
    fun `should validate valid input`() {
        val params = mapOf(SlackConnector.TOKEN_INPUT to "token", SlackConnector.ID_INPUT to "id", SlackConnector.MESSAGE_INPUT to "message")

        connector.setInputParameters(params)
        connector.validateInputParameters()
    }
}
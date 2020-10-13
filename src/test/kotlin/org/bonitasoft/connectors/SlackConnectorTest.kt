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
        connector = SlackConnector();
    }

    @Test
    fun `should throw exception if mandatory input is missing`() {
        assertThatThrownBy { connector.validateInputParameters() }
                .isExactlyInstanceOf(ConnectorValidationException::class.java)
    }

    @Test
    fun `should throw exception if mandatory input is empty`() {
        // Given
        connector.setInputParameter(SlackConnector.DEFAULT_INPUT, "")

        // When
        assertThatThrownBy { connector.validateInputParameters() }
                // Then
                .isExactlyInstanceOf(ConnectorValidationException::class.java)
    }

    @Test
    fun `should throw exception if mandatory input is not a string`() {
        // Given
        connector.setInputParameter(SlackConnector.DEFAULT_INPUT, 38);

        // When
        assertThatThrownBy { connector.validateInputParameters() }
                // Then
                .isExactlyInstanceOf(ConnectorValidationException::class.java)
    }

    @Test
    fun `should create output for valid input`() {
        // Given
        connector.setInputParameter(SlackConnector.DEFAULT_INPUT, "valid");

        // When
        connector.executeBusinessLogic();

        // Then
        val output = connector.getOutputParameter(SlackConnector.DEFAULT_OUTPUT);
        assertThat(output).isEqualTo("valid - output");
    }
}
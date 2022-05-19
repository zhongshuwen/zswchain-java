

package org.zhongshuwen.zswjava;

import org.zhongshuwen.zswjava.error.ZswChainError;
import org.zhongshuwen.zswjava.error.serializationProvider.SerializationProviderError;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class ZswChainErrorTest {

    @Test
    public void errroMessage() {
        ZswChainError err = new ZswChainError("Parsing Error!");
        String description = err.getLocalizedMessage();
        assertEquals("Parsing Error!", description);

        Exception origError = new IOException("File Not Found");
        ZswChainError err2 = new ZswChainError(
                "Could not find resource.",
                origError);
        assertEquals("Could not find resource.", err2.getLocalizedMessage());

        assertEquals("File Not Found", err2.getCause().getMessage());
    }

    @Test
    public void asJsonString() {
        String jsonResult = "{\n" +
                "  \"errorType\": \"ZswChainError\",\n" +
                "  \"errorInfo\": {\n" +
                "    \"errorCode\": \"SerializationProviderError\",\n" +
                "    \"reason\": \"Serialization Provider Failure\"\n" +
                "  }\n" +
                "}";
        SerializationProviderError err2 = new SerializationProviderError("Serialization Provider Failure");
        String errJsonString = err2.asJsonString();
        assertEquals(jsonResult, errJsonString);
    }

}
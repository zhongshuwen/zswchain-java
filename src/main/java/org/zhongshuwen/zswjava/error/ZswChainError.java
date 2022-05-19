package org.zhongshuwen.zswjava.error;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;

/**
 * Error class is used when there is an exception while attempting to process anything inside the
 * ZswChain-java library
 */
public class ZswChainError extends Exception {

    /**
     * Create an ZswChainError with a null message and original exception.
     */
    public ZswChainError() {
        super();
    }

    /**
     * Construct an ZswChainError with the given message.
     *
     * @param message - Message text for the exception.
     */
    public ZswChainError(@NotNull String message) {
        super(message);
    }

    /**
     * Construct an ZswChainError with the given message and original exception.
     *
     * @param message - Message text for the exception.
     * @param exception - Original root exception for the error.
     */
    public ZswChainError(@NotNull String message, @NotNull Exception exception) {
        super(message, exception);
    }

    /**
     * Construct an ZswChainError with the given original exception.
     *
     * @param exception - Original root exception for the error.
     */
    public ZswChainError(@NotNull Exception exception) {
        super(exception);
    }

    /**
     * Construct a JSON formatted string describing the error code and reason.
     *
     * @return A JSON formatted string
     */
    @NotNull
    public String asJsonString() {
            JsonObject errInfo = new JsonObject();
            errInfo.addProperty("errorCode", this.getClass().getSimpleName());
            errInfo.addProperty("reason", this.getLocalizedMessage());
            JsonObject err = new JsonObject();
            err.addProperty("errorType", "ZswChainError");
            err.add("errorInfo", errInfo);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String jsonString = gson.toJson(err);
            return jsonString;
    }

}


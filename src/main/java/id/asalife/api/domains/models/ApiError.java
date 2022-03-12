package id.asalife.api.domains.models;

import lombok.Getter;

@Getter
public class ApiError extends ErrorResponse {
    private final int code;

    public ApiError(String message, int code) {
        super(message);
        this.code = code;
    }
}

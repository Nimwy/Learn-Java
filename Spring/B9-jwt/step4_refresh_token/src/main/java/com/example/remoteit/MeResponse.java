package com.example.remoteit;

import java.util.List;

public record MeResponse(String email, List<String> roles) {
}

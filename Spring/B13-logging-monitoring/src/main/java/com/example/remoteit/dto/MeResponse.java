package com.example.remoteit.dto;

import java.util.List;

public record MeResponse(String email, List<String> roles) {
}

package com.oskarwiedeweg.cloudwork.error.dto;

import java.time.LocalDateTime;

public record ErrorDto(int status, String path, Object error, LocalDateTime timestamp) {
}

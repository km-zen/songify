package com.songify.infrastructure.apivalidation;

import java.util.List;

record ApiValidationErrorResponseDto(List<String> errors) {
}

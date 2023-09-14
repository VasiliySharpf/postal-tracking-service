package net.example.postaltrackingservice.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

/**
 * Интерфейс, унаследовавшись от которого интегрируется аутентификация в swagger на endpoint's
 */

@SecurityRequirement(name = "admin")
public interface AdminController {
}

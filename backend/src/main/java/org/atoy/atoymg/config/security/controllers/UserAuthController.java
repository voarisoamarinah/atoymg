package org.atoy.atoymg.config.security.controllers;

import java.util.List;
import jakarta.validation.Valid;
import org.atoy.atoymg.config.security.models.AuthRequest;
import org.atoy.atoymg.config.security.models.PasswordChangeRequest;
import org.atoy.atoymg.config.security.models.UserUpdateRequest;
import org.atoy.atoymg.config.security.models.RefreshRequest;
import org.atoy.atoymg.config.security.services.JwtService;
import org.atoy.atoymg.config.security.services.TokenBlacklistService;
import org.atoy.atoymg.config.security.services.UserInfoService;
import org.atoy.atoymg.dto.RestResponse;
import org.atoy.atoymg.models.RoleEntity;
import org.atoy.atoymg.models.UserEntity;
import org.atoy.atoymg.repositories.RoleEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "Endpoints for user authentication and management")
public class UserAuthController {
  private final JwtService jwtService;
  private final UserInfoService userInfoService;
  private final TokenBlacklistService tokenBlacklistService;
  @Autowired
  private RoleEntityRepository roleEntityRepository;
  
  @Autowired
  public UserAuthController(JwtService jwtService, UserInfoService userInfoService, TokenBlacklistService tokenBlacklistService) {
    this.jwtService = jwtService;
    this.userInfoService = userInfoService;
    this.tokenBlacklistService = tokenBlacklistService;
  }
  
  @Operation(
        summary = "Get all available roles",
        description = "Retrieve the complete list of roles available in the system. This endpoint is public and does not require authentication."
  )
  @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Successfully retrieved roles list",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = RestResponse.class), array = @ArraySchema(schema = @Schema(implementation = RoleEntity.class))
                )
        ),
        @ApiResponse(responseCode = "500", description = "Internal server error",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = RestResponse.class))
        )
  })
  @GetMapping("/roles")
  public ResponseEntity<RestResponse<List<RoleEntity>>> listRoles() {
    var roles = roleEntityRepository.findAll();
    return ResponseEntity.ok(RestResponse.buildSuccessResponse(HttpStatus.OK, "Roles", roles));
  }
  
  @Operation(
    security = { @SecurityRequirement(name = "bearerAuth") },
    summary = "Logout user",
    description = "Invalidate the user's JWT token"
  )
  @ApiResponses({
    @ApiResponse(
      responseCode = "200",
      description = "Successfully logged out",
      content = @Content(mediaType = "application/json", schema = @Schema(implementation = RestResponse.class))
    ),
    @ApiResponse(
      responseCode = "400",
      description = "Invalid Authorization header",
      content = @Content(mediaType = "application/json", schema = @Schema(implementation = RestResponse.class))
   )
  })
  @PostMapping("/logout")
  public ResponseEntity<RestResponse<String>> logout(@RequestHeader("Authorization") String authHeader) {
    if (authHeader != null && authHeader.startsWith("Bearer ")) {
      String token = authHeader.substring(7);
      tokenBlacklistService.invalidateToken(token);
      return ResponseEntity.ok(
        RestResponse.buildSuccessResponse(
          HttpStatus.OK,
          "Logged out successfully",
          null
        )
      );
    }
    return ResponseEntity.badRequest().body(
      RestResponse.buildErrorResponse(
        HttpStatus.BAD_REQUEST,
        "Invalid Authorization Header",
        null
      )
    );
  }

  @Operation(
    security = { @SecurityRequirement(name = "bearerAuth") },
    summary = "Get current user",
    description = "Retrieve details of the currently authenticated user"
  )
  @ApiResponses({
    @ApiResponse(
      responseCode = "200",
      description = "Successfully retrieved user details",
      content = @Content(mediaType = "application/json", schema = @Schema(implementation = RestResponse.class))
    ),
    @ApiResponse(
      responseCode = "400",
      description = "No valid user session",
      content = @Content(mediaType = "application/json", schema = @Schema(implementation = RestResponse.class))
   )
  })
  @GetMapping("/current")
  public ResponseEntity<RestResponse<UserDetails>> currentUser(@RequestHeader("Authorization") String authHeader) {
    try {
      if (authHeader != null && authHeader.startsWith("Bearer ")) {
        String token = authHeader.substring(7);
        String username = jwtService.extractUsername(token);
        UserDetails userConnected = userInfoService.loadUserByUsername(username);
        return ResponseEntity.ok(
          RestResponse.buildSuccessResponse(
            HttpStatus.OK,
            "User details retrieved",
            userConnected
          )
        );
      }
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(
        RestResponse.buildErrorResponse(
          HttpStatus.BAD_REQUEST,
          "Error retrieving user: " + e.getMessage(),
          null
        )
      );
    }
    return ResponseEntity.badRequest().body(
        RestResponse.buildErrorResponse(
            HttpStatus.BAD_REQUEST,
            "No user connected",
            null
        )
    );
  }

  @Operation(
      summary = "Register new user",
      description = "Create a new user account"
  )
  @ApiResponses({
    @ApiResponse(
      responseCode = "200",
      description = "User created successfully",
      content = @Content(mediaType = "application/json", schema = @Schema(implementation = RestResponse.class))
    ),
    @ApiResponse(
      responseCode = "400",
      description = "Invalid user data",
      content = @Content(mediaType = "application/json", schema = @Schema(implementation = RestResponse.class))
   )
  })
  @PostMapping("/register")
  public ResponseEntity<RestResponse<String>> addNewUser(@RequestBody UserEntity user) {
    try {
      if (user.getRoleidRoleEntity() == null) {
        var role = roleEntityRepository.findAllByName("ROLE_ADMIN")
        .orElseThrow(() -> new IllegalArgumentException("Default role ROLE_ADMIN not found"));
        user.setRoleidRoleEntity(role);
      }
      String result = userInfoService.addUser(user);
      return ResponseEntity.ok(RestResponse.buildSuccessResponse(HttpStatus.OK, "User registered successfully", result));
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(RestResponse.buildErrorResponse(HttpStatus.BAD_REQUEST, "Registration failed: " + e.getMessage(), null));
    }
  }

  @Operation(
      summary = "User login",
      description = "Authenticate user and generate JWT token"
  )
  @ApiResponses({
      @ApiResponse(
          responseCode = "200",
          description = "Authentication successful",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = RestResponse.class))
      ),
      @ApiResponse(
          responseCode = "401",
          description = "Invalid credentials",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = RestResponse.class))
      )
  })
  @PostMapping("/login")
  public ResponseEntity<RestResponse<String>> authenticateAndGetToken(@Valid @RequestBody AuthRequest authRequest) {
      try {
          String token = userInfoService.authenticateAndGetToken(authRequest);
          return ResponseEntity.ok(
              RestResponse.buildSuccessResponse(
                  HttpStatus.OK,
                  "Authentication successful",
                  token
              )
          );
      } catch (Exception e) {
          return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
              RestResponse.buildErrorResponse(
                  HttpStatus.UNAUTHORIZED,
                  "Authentication failed: " + e.getMessage(),
                  null
              )
          );
      }
  }
  
  @Operation(
      summary = "Refresh access token",
      description = "Provide the current access-token to obtain a new one; the old one is invalidated."
  )
  @ApiResponses({
    @ApiResponse(
      responseCode = "200",
      description = "New token issued",
      content = @Content(schema = @Schema(implementation = RestResponse.class))
    ),
    @ApiResponse(responseCode = "401", description = "Token invalid or expired")
  })
  @PostMapping("/refresh")
  public ResponseEntity<RestResponse<String>> refresh(
    @Valid @RequestBody RefreshRequest req) {
    try {
      String newToken = userInfoService.refreshAccessToken(req.refreshToken());
      tokenBlacklistService.invalidateToken(req.refreshToken());
      return ResponseEntity.ok(
        RestResponse.buildSuccessResponse(HttpStatus.OK, "Token refreshed", newToken)
      );
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
        RestResponse.buildErrorResponse(HttpStatus.UNAUTHORIZED, e.getMessage(), null)
      );
    }
  }

  @Operation(
    security = { @SecurityRequirement(name = "bearerAuth") },
    summary = "Update user information",
    description = "Update the current user's name and email"
  )
  @ApiResponses({
    @ApiResponse(
      responseCode = "200",
      description = "User updated successfully",
      content = @Content(mediaType = "application/json", schema = @Schema(implementation = RestResponse.class))
    ),
    @ApiResponse(
      responseCode = "400",
      description = "Invalid data or update failed",
      content = @Content(mediaType = "application/json", schema = @Schema(implementation = RestResponse.class))
   )
  })
  @PatchMapping("/update")
  public ResponseEntity<RestResponse<String>> updateUser(
  @RequestHeader("Authorization") String authHeader,
  @Valid @RequestBody UserUpdateRequest updateRequest) {
  
    try {
      if (authHeader != null && authHeader.startsWith("Bearer ")) {
        String token = authHeader.substring(7);
        String username = jwtService.extractUsername(token);
        userInfoService.updateUser(username, updateRequest);
  
        return ResponseEntity.ok(
          RestResponse.buildSuccessResponse(
            HttpStatus.OK,
            "User updated successfully",
            null
          )
        );
      }
      return ResponseEntity.badRequest().body(
        RestResponse.buildErrorResponse(
          HttpStatus.BAD_REQUEST,
          "Invalid Authorization Header",
          null
        )
      );
  } catch (Exception e) {
    return ResponseEntity.badRequest().body(
    RestResponse.buildErrorResponse(
    HttpStatus.BAD_REQUEST,
    "Update failed: " + e.getMessage(),
                  null
              )
          );
      }
  }

  @Operation(
    security = { @SecurityRequirement(name = "bearerAuth") },
    summary = "Change user password",
    description = "Change the current user's password. Requires old password, new password and confirmation."
  )
  @ApiResponses({
    @ApiResponse(
      responseCode = "200",
      description = "Password changed successfully",
      content = @Content(mediaType = "application/json", schema = @Schema(implementation = RestResponse.class))
    ),
    @ApiResponse(
      responseCode = "400",
      description = "Invalid data or password change failed",
      content = @Content(mediaType = "application/json", schema = @Schema(implementation = RestResponse.class))
   )
  })
  @PostMapping("/change-password")
  public ResponseEntity<RestResponse<String>> changePassword(
  @RequestHeader("Authorization") String authHeader,
  @Valid @RequestBody PasswordChangeRequest passwordChangeRequest) {
  
    try {
      if (authHeader != null && authHeader.startsWith("Bearer ")) {
        String token = authHeader.substring(7); 
        String username = jwtService.extractUsername(token);
        userInfoService.changeUserPassword(
          username,
          passwordChangeRequest.getOldPassword(),
          passwordChangeRequest.getNewPassword(),
          passwordChangeRequest.getConfirmPassword()
        );
    
        return ResponseEntity.ok(
          RestResponse.buildSuccessResponse(
            HttpStatus.OK,
            "Password changed successfully",
            null
          )
        );
      }
      return ResponseEntity.badRequest().body(
        RestResponse.buildErrorResponse(
          HttpStatus.BAD_REQUEST,
          "Invalid Authorization Header",
          null
        )
      );
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(
        RestResponse.buildErrorResponse(
          HttpStatus.BAD_REQUEST,
          "Password change failed: " + e.getMessage(),
          null
        )
      );
    }
  }
}

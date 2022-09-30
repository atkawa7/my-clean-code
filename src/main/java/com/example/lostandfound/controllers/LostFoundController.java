package com.example.lostandfound.controllers;

import com.example.lostandfound.api.AccountService;
import com.example.lostandfound.api.ItemLostService;
import com.example.lostandfound.domain.ItemLost;
import com.example.lostandfound.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.ok;

@RestController
public class LostFoundController {
    private final AccountService accountService;
    private final ItemLostService itemLostService;

    public LostFoundController(AccountService accountService, ItemLostService itemLostService) {
        this.accountService = accountService;
        this.itemLostService = itemLostService;
    }

    @PostMapping(value = "/accounts/login")
    @Operation(
            operationId = "loginAccount",
            method = "POST",
            summary = "Login account",
            description = "Login account",
            tags = "Accounts",
            responses = {
                    @ApiResponse(responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = SuccessOrFailure.class))),

            }
    )
    public ResponseEntity<SuccessOrFailure> login(@RequestBody LoginRequest loginRequest){
        return ok(accountService.createToken(loginRequest));
    }

    @PostMapping(value = "/accounts/register")
    @Operation(
            operationId = "createAccount",
            method = "POST",
            summary = "Create account",
            description = "Create account",
            tags = "Accounts",
            responses = {
                    @ApiResponse(responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = SuccessOrFailure.class))),

            }
    )
    public ResponseEntity<SuccessOrFailure> createAccount(@RequestBody CreateAccount createAccount){
        return ok(accountService.createAccount(createAccount));
    }

    @PostMapping(value = "/items")
    @Operation(
            operationId = "createItem",
            method = "POST",
            summary = "Create item",
            description = "Create item",
            tags = "Lost Items",
            responses = {
                    @ApiResponse(responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = SuccessOrFailure.class))),

            },
            security = @SecurityRequirement(name = "api-key")
    )
    public ResponseEntity<SuccessOrFailure> createItem(@RequestBody CreateItemLost createItemLost){
        return ok(itemLostService.createItem(createItemLost));
    }

    @GetMapping(value = "/items")
    @Operation(
            operationId = "listItems",
            method = "GET",
            summary = "List items",
            description = "List items",
            tags = "Lost Items",
            responses = {
                    @ApiResponse(responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = SuccessOrFailure.class))),

            },
            security = @SecurityRequirement(name = "api-key")
    )
    public ResponseEntity<Page<ItemLost>> listItem(Pageable pageable){
        return ok(itemLostService.listItems(pageable));
    }

    @Operation(
            operationId = "updateItem",
            method = "POST",
            summary = "Update item",
            description = "Update item",
            tags = "Lost Items",
            responses = {
                    @ApiResponse(responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = SuccessOrFailure.class))),

            },
            security = @SecurityRequirement(name = "api-key"),
            parameters = @Parameter(name = "id", required = true, in = ParameterIn.PATH)
    )
    @PostMapping(value = "/items/{id}")
    public ResponseEntity<SuccessOrFailure> updateItem(@PathVariable(value = "id") Long id,  @RequestBody UpdateLostItemStatus updateLostItemStatus){
        return ok(itemLostService.updateItem(id, updateLostItemStatus));
    }
    @DeleteMapping(value = "/items/{id}")
    @Operation(
            operationId = "deleteItem",
            method = "DELETE",
            summary = "Delete item",
            description = "Delete item",
            tags = "Lost Items",
            responses = {
                    @ApiResponse(responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = SuccessOrFailure.class))),

            },
            security = @SecurityRequirement(name = "api-key"),
            parameters = @Parameter(name = "id", required = true, in = ParameterIn.PATH)
    )
    public ResponseEntity<SuccessOrFailure> deleteItem(@PathVariable(value = "id") Long id){
        return ok(itemLostService.deleteItem(id));
    }

}

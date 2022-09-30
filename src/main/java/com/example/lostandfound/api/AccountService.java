package com.example.lostandfound.api;


import com.example.lostandfound.dto.CreateAccount;
import com.example.lostandfound.dto.LoginRequest;
import com.example.lostandfound.dto.SuccessOrFailure;

public interface AccountService {
    SuccessOrFailure createAccount(CreateAccount createAccount);
    SuccessOrFailure createToken(LoginRequest loginRequest);

}

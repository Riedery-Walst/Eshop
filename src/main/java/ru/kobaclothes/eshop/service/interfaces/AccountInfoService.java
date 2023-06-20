package ru.kobaclothes.eshop.service.interfaces;

import ru.kobaclothes.eshop.request.AccountInfoRequest;

public interface AccountInfoService {
    void setAccountInfo(String email, AccountInfoRequest accountInfoRequest);
}

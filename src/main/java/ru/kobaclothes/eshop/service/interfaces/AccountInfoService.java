package ru.kobaclothes.eshop.service.interfaces;

import ru.kobaclothes.eshop.dto.AccountInfoDTO;

public interface AccountInfoService {
    void setAccountInfo(String email, AccountInfoDTO accountInfoDTO);
}

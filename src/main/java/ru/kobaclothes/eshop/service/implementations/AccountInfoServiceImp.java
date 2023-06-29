package ru.kobaclothes.eshop.service.implementations;

import org.springframework.stereotype.Service;
import ru.kobaclothes.eshop.model.AccountInfo;
import ru.kobaclothes.eshop.model.User;
import ru.kobaclothes.eshop.repository.AccountInfoRepository;
import ru.kobaclothes.eshop.repository.UserRepository;
import ru.kobaclothes.eshop.dto.AccountInfoDTO;
import ru.kobaclothes.eshop.service.interfaces.AccountInfoService;

@Service
public class AccountInfoServiceImp implements AccountInfoService {
    private final AccountInfoRepository accountInfoRepository;
    private final UserRepository userRepository;

    public AccountInfoServiceImp(AccountInfoRepository accountInfoRepository, UserRepository userRepository) {
        this.accountInfoRepository = accountInfoRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void setAccountInfo(String email, AccountInfoDTO accountInfoDTO) {
        User user = userRepository.findByEmail(email);
        AccountInfo accountInfo = user.getAccountInfo();

        if (accountInfo == null) {
            accountInfo = new AccountInfo();
            user.setAccountInfo(accountInfo);
        }

        accountInfo.setBirthDate(accountInfoDTO.getBirthDate());
        accountInfo.setGender(accountInfoDTO.getGender());
        accountInfo.setFirstName(accountInfoDTO.getFirstName());
        accountInfo.setLastName(accountInfoDTO.getLastName());
        accountInfo.setPatronymic(accountInfoDTO.getPatronymic());

        accountInfoRepository.save(accountInfo);
        userRepository.save(user);
    }

}

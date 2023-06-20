package ru.kobaclothes.eshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kobaclothes.eshop.model.AccountInfo;

@Repository
public interface AccountInfoRepository extends JpaRepository<AccountInfo, Long> {
    AccountInfo findAccountInfoByUserId(Long id);
}

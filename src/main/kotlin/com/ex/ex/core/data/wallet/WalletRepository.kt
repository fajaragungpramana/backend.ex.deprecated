package com.ex.ex.core.data.wallet

import com.ex.ex.core.data.wallet.entity.WalletEntity
import org.springframework.data.jpa.repository.JpaRepository

interface WalletRepository : JpaRepository<WalletEntity, Long>
package com.uqac.commande.infra;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommandeJPA extends JpaRepository<Commande, Long> {
    List<Commande> findCommandesByUserId(Long userId);

    void deleteCommandeByUserIdAndCartId(Long userId, Long cartId);
}

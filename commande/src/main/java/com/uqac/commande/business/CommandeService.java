package com.uqac.commande.business;

import com.uqac.commande.dto.CreateCommandeDTO;
import com.uqac.commande.infra.Commande;

import java.util.List;

public interface CommandeService {

    Boolean createCommande(CreateCommandeDTO createCommandeDTO);

    List<Commande> getAllCommandes();

    List<Commande> getCommandesByUserId(Long userId);

    void cancelCommande(Long userId, Long cartId);

}

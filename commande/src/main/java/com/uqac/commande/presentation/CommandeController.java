package com.uqac.commande.presentation;

import com.uqac.commande.business.CommandeService;
import com.uqac.commande.dto.CreateCommandeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/commande")
public class CommandeController {

    private CommandeService commandeService;

    @Autowired
    public CommandeController(CommandeService commandeService) {
        this.commandeService = commandeService;
    }

    @PostMapping("")
    public ResponseEntity<?> createCommande(@RequestBody CreateCommandeDTO createCommandeDTO) {
        Boolean response = commandeService.createCommande(createCommandeDTO);
        if (!response) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("")
    public ResponseEntity<?> getAllCommandes() {
        return ResponseEntity.ok(commandeService.getAllCommandes());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getCommandesByUserId(Long userId) {
        return ResponseEntity.ok(commandeService.getCommandesByUserId(userId));
    }

}

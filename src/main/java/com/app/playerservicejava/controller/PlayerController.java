package com.app.playerservicejava.controller;

import com.app.playerservicejava.exception.ServiceException;
import com.app.playerservicejava.model.Player;
import com.app.playerservicejava.model.Players;
import com.app.playerservicejava.service.CacheEvictionService;
import com.app.playerservicejava.service.PlayerService;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping(value = "v1/players", produces = { MediaType.APPLICATION_JSON_VALUE })
public class PlayerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerController.class);

    @Resource
    private PlayerService playerService;

    @Autowired
    PagedResourcesAssembler assembler;

    @Autowired
    CacheEvictionService cacheEvictionService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Players> getPlayers() {
        Players players = playerService.getPlayers();
        return ok(players);
    }

    @GetMapping("/filter")
    public HttpEntity<PagedModel<Player>> getPlayers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) throws ServiceException {
        Page<Player> players;
        try{
            players = playerService.getPlayers(page, size);
        }catch(IllegalArgumentException ex) {
            LOGGER.warn("message=Exception in getPlayers; exception={}", ex.toString());
            throw new ServiceException(" Invalid pagination parameters",ex);
        } catch(Exception ex) {
            LOGGER.error("message=Exception in getPlayers; exception={}", ex.toString());
            throw new ServiceException(ex);
        }
        return ResponseEntity.ok(assembler.toModel(players));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Player> getPlayerById(@PathVariable("id") String id) throws Exception {
        Optional<Player> player = playerService.getPlayerById(id);

        if (player.isPresent()) {
            return new ResponseEntity<>(player.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, String>> update(@PathVariable("id") String id, @RequestBody Player player) throws ServiceException {
        try {
            playerService.updatePlayer(id, player);
            return ResponseEntity.ok(Map.of("message", "successfully updated", "id", id));
        }catch(Exception ex) {
            LOGGER.error("Error while updating the player record", ex);
            throw new ServiceException("Update failed", ex);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteById(@PathVariable("id") String id) throws ServiceException {
        try {
            LOGGER.info("Deleting the player having id {}", id);
            playerService.deletePlayerById(id);

            LOGGER.info("Deleting the player having id {} from the cache", id);
            cacheEvictionService.evictSpecificPlayer(id);
            return ResponseEntity.ok(Map.of("message", "successfully deleted", "id", id));
        }catch(Exception ex) {
            LOGGER.error("Error while deleting the player record", ex);
            throw new ServiceException("Delete failed", ex);
        }
    }
}

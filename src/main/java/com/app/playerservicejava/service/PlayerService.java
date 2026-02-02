package com.app.playerservicejava.service;

import com.app.playerservicejava.model.Player;
import com.app.playerservicejava.model.Players;
import com.app.playerservicejava.repository.PlayerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PlayerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerService.class);
    private static final double THREAD_DELAY_DURATION = Math.random() * 200;

    @Autowired
    private PlayerRepository playerRepository;

    @Cacheable(value = "players")
    public Players getPlayers() {
        Players players = new Players();
        playerRepository.findAll()
                .forEach(players.getPlayers()::add);
        return players;
    }

    /**
     * Fetch a paginated list of players
     *
     * @param page zero-based page index
     * @param size number of records per page
     */
    @Cacheable(
            value = "players",
            key = "'page=' + #page + ',size=' + #size"
    )
    public Page<Player> getPlayers(int page, int size) {
        if (page < 0 || size <= 0) {
            throw new IllegalArgumentException("Invalid pagination parameters");
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by("playerId").ascending());
        return playerRepository.findAll(pageable);
    }

    @Cacheable(value = "playerById", key = "#playerId")
    public Optional<Player> getPlayerById(String playerId) throws Exception {
        Optional<Player> player;
        LOGGER.info("Getting the records from the database");
        /* simulated network delay */
        player = playerRepository.findById(playerId);
        Thread.sleep((long)THREAD_DELAY_DURATION);
        return player;
    }

    //@CacheEvict(cacheNames = "playerById", key="#playerId")
    public void deletePlayerById(String playerId) throws Exception {
        LOGGER.info("Deleting the player record having ID as {}", playerId);
        playerRepository.deleteById(playerId);
        Thread.sleep((long)THREAD_DELAY_DURATION);
    }

    public Player updatePlayer(String id, Player player) throws InterruptedException {
        Optional<Player> existingPlayer = playerRepository.findById(id);
        if (existingPlayer.isEmpty()) {
            throw new IllegalArgumentException("Player not found with id: " + id);
        }

        player.setPlayerId(id); // Ensure ID consistency
        Player savedPlayer = playerRepository.save(player);

        // Simulate network delay
        Thread.sleep((long)THREAD_DELAY_DURATION);

        return savedPlayer;
    }


}

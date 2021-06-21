package com.spring.player.service;

import com.spring.player.model.Player;
import com.spring.player.repo.PlayerRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlayerService {

    private final PlayerRepo playerRepo;

    public PlayerService(PlayerRepo playerRepo) {
        this.playerRepo = playerRepo;
    }

    public List<Player> getAllPlayers(){
        return playerRepo.findAll();
    }

    public Player getPlayer(int id) {
        return  playerRepo.findById(id).get();
    }

    public Player addPlayer(Player player){
        return playerRepo.save(player);
    }

    public Player updatePlayer(Player player){
        return playerRepo.save(player);
    }
    public void   deletePlayer(int id){
          playerRepo.deleteById(id);
    }

}

package com.spring.player.controller;

import com.spring.player.exception.PlayerExxception;
import com.spring.player.model.Player;
import com.spring.player.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class PlayerController {

    private final PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping("/players")
    public List<Player> getAllPlayers(){
        return playerService.getAllPlayers();
    }

    @GetMapping("/player/{id}")
    public Player getPlayerById(@PathVariable int id){
        Player player = playerService.getPlayer(id);
        if(player == null){
            throw new PlayerExxception("Player Not Found Id: "+ id);
        }
        return player;
    }

    @PostMapping("/players")
    public Player addPlayer(@RequestBody Player player){
        return playerService.addPlayer(player);
    }

    @PutMapping("/players")
    public Player updatePlayer(@RequestBody Player player){
        return playerService.updatePlayer(player);
    }

    @DeleteMapping("/players/{id}")
    public void deletePlayer(@PathVariable int id){
        if(id <=0){
            throw new PlayerExxception("Player Not Found Id: "+id);
        }
        playerService.deletePlayer(id);
    }







}

package com.freechazz.database.services;

import com.freechazz.database.entities.MatchEntity;
import com.freechazz.database.entities.UserEntity;
import com.freechazz.database.repositories.MatchRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class MatchService {

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private UserService userService;

    public void createMatch(MatchEntity matchEntity) {
        matchRepository.save(matchEntity);
    }

    /*
    public void updateMatch(Game game) {

        Optional<UserEntity> userEntity1 = userService.getUserById(game.getPlayer1().getUserId());
        Optional<UserEntity> userEntity2 = userService.getUserById(game.getPlayer2().getUserId());

        if (userEntity1.isEmpty() || userEntity2.isEmpty()) {
            log.error("User not found");
            return;
        }

        MatchEntity matchEntity = new MatchEntity(game.getGameId(), userEntity1.get(), userEntity2.get(), game.toJson());
        matchRepository.save(matchEntity);
    }

*/

    public List<MatchEntity> getAllMatches() {
        return matchRepository.findAll();
    }

    public MatchEntity getMatchByIdForUser(UUID matchId, UUID userId) {
        Optional<MatchEntity> matchEntity = matchRepository.findById(matchId);
        if (matchEntity.isEmpty()) {
            log.warn("Match not found");
            return null;
        }
        if (matchEntity.get().getUser1() == null) {
            log.error("No user found in match entity");
            return null;
        }

        if (matchEntity.get().getUser1().getUuid().equals(userId) || matchEntity.get().getUser2().getUuid().equals(userId)) {
            return matchEntity.get();
        }
        return matchRepository.findById(matchId).orElse(null);
    }

    public void updateMatch(UUID matchId, String toJson) {
        Optional<MatchEntity> matchEntity = matchRepository.findById(matchId);
        if (matchEntity.isEmpty()) {
            log.warn("Match not found");
            return;
        }
        matchEntity.get().setGameData(toJson);
        matchRepository.save(matchEntity.get());
    }

    public UUID joinGame(UUID gameId, UserEntity user2) {
        Optional<MatchEntity> matchEntity = matchRepository.findById(gameId);
        if (matchEntity.isEmpty()) {
            log.warn("Match not found");
            return null;
        }

        matchEntity.get().setUser2(user2);
        matchRepository.save(matchEntity.get());
        return gameId;
    }

    public List<MatchEntity> getAllMatchesForUser(UUID userId) {

        List<MatchEntity> list1 = matchRepository.findByWatchUserUuid(userId);
        List<MatchEntity> list2 = matchRepository.findByUser1UuidOrUser2Uuid(userId, userId);

        List<MatchEntity> combinedList = new ArrayList<>(list1);
        combinedList.addAll(list2);

        return combinedList;
    }


    public void getMatchById(UUID matchId) {
        matchRepository.findById(matchId);
    }
}


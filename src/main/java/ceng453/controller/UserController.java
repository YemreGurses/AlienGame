package ceng453.controller;


import ceng453.entity.Score;
import ceng453.entity.User;
import ceng453.repository.ScoreRepository;
import ceng453.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class UserController {


    @Autowired
    private UserRepository userRepository;


    @Autowired
    private ScoreRepository scoreRepository;

    @GetMapping("/users")
    public List<User> getUsers() {
        List<User> userList = userRepository.findAll();
        return userList;
    }


    @GetMapping("/users/{id}")
    public User getUser(@PathVariable Integer id) {
        User user = userRepository.findById(id).get();
        return user;
    }

    @GetMapping("/users/{id}/score")
    public List<Score> getUsersScore(@PathVariable Integer id) {
        User user = userRepository.findById(id).get();
        return user.getScoreList();
    }

    @GetMapping("/scores/all")
    public List<Map<String, String>> getScores() {
        List<Map<String, String>> leaderBoard = scoreRepository.getLeaderBoard();
        return leaderBoard;
    }

    @GetMapping("/scores/weekly")
    public List<Map<String, String>> getWeeklyScores() {
        List<Map<String, String>> leaderBoard = scoreRepository.getLeaderBoardWeekly();
        return leaderBoard;
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable Integer id) {
        userRepository.deleteById(id);
    }

}
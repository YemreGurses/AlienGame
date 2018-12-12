package ceng453.service;

import ceng453.entity.Score;
import ceng453.entity.User;
import ceng453.repository.ScoreRepository;
import ceng453.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ScoreRepository scoreRepository;


    public User getUser(int id) {
        return userRepository.findById(id).get();
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void addUser(User user) {
        if (user == null) return;
        user.setPassword(Integer.toString(user.getPassword().hashCode()));
        userRepository.save(user);
    }

    public void updateUser(User user, Integer id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser != null) {
            userRepository.deleteById(id);
            user.setPassword(Integer.toString(user.getPassword().hashCode()));
            userRepository.save(user);
        }
    }

    public void addScore(Integer id, Integer score) {
        User user = userRepository.findById(id).get();
        List<Score> scoreList = user.getScoreList();
        Score score1 = Score.builder().date(new Date()).score(score).build();
        scoreRepository.save(score1);
        scoreList.add(score1);
        user.setScoreList(scoreList);
        userRepository.save(user);
    }

    public List<Score> getUsersScore(Integer id) {
        User user = userRepository.findById(id).get();
        return user.getScoreList();
    }

    public List<Map<String, String>> getScores() {
        return scoreRepository.getLeaderBoard();
    }

    public List<Map<String, String>> getWeeklyScores() {
        return scoreRepository.getLeaderBoardWeekly();
    }

    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }


}
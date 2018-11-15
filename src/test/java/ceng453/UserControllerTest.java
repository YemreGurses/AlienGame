package ceng453;


import ceng453.entity.Score;
import ceng453.entity.User;
import ceng453.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserControllerTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void findAll() {

        User furkan = User.builder().name("furkan1").email("furkan@a").password("asdfg").build();
        userRepository.save(furkan);
        User yemre = User.builder().name("yemre1").email("yemre@a").password("asdfg").build();
        userRepository.save(yemre);

        List<User> userList = Arrays.asList(new User[]{furkan, yemre});

        assertNotNull(userRepository.findAll());

        assertEquals(userList,userRepository.findAll());
    }

    @Test
    public void getUserbyId() {

        User furkan = User.builder().name("furkan1").email("furkan@a").password("asdfg").build();
        userRepository.save(furkan);
        User yemre = User.builder().name("yemre1").email("yemre@a").password("asdfg").build();
        userRepository.save(yemre);


        assertNotNull(userRepository.findById(2));

        assertEquals(yemre,userRepository.findById(2).get());
    }

//    @Test
//    public void getLeaderBoard() {
//
////        List<Map<String, String>> listScore = Arrays.asList(new Map<String, String>[]{(1, 100), (2, 500)} );
//
//
//        User furkan = User.builder().name("furkan1").email("furkan@a").password("asdfg").scoreList().build();
//        userRepository.save(furkan);
//        User yemre = User.builder().name("yemre1").email("yemre@a").password("asdfg").build();
//        userRepository.save(yemre);
//
//
//        assertNotNull(userRepository.findById(2));
//
//        assertEquals(yemre,userRepository.findById(2));
//    }

}

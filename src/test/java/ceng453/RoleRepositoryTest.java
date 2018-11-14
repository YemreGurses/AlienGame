package ceng453;


import ceng453.entity.Role;
import ceng453.repository.RoleRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

@RunWith(SpringRunner.class)
@DataJpaTest
public class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void findByRole() {

        Role User123 = Role.builder().role("User").id(13).build();
        roleRepository.save(User123);

        assertNotNull(roleRepository.findByRole("User").getRole());
        assertEquals(User123.getRole(),roleRepository.findByRole("User").getRole());
    }
}

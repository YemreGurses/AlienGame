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

        Role role = Role.builder().role("User").id(1).build();
        role.setId(1);
        roleRepository.save(role);

        assertNotNull(roleRepository.findByRole("User").getRole());
        assertEquals(role.getRole(),roleRepository.findByRole("User").getRole());
    }
}

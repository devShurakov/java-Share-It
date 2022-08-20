package user.service;

import com.example.ShareIt.user.dto.UserDto;
import com.example.ShareIt.user.mapper.UserMapper;
import com.example.ShareIt.user.model.User;
import com.example.ShareIt.user.service.UserService;
import com.example.ShareIt.user.service.impl.UserServiceImpl;
import com.example.ShareIt.user.storage.UserRepository;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@SpringBootTest(classes = UserServiceImpl.class)
public class UserServiceImplTest {
    @Autowired
    private UserServiceImpl service;
    @MockBean
    private UserRepository repository;
    @MockBean
    private  UserMapper userMapper;

//    public UserServiceImplTest() {
//        userMapper = new UserMapper();
//    }

    @Test
    void addUser() {
        UserDto newUser = UserDto.builder().id(1L).name("Name").email("user@user.ru").build();
        UserDto dto = service.create(newUser);
        Mockito.verify(repository, times(1)).save(userMapper.mapToUser(dto));

//        Mockito.verify(repository, times(1)).save(userMapper.mapToUser(newUser));
    }


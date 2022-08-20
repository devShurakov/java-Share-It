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


    // Given



//        @Test
//        void updateUser() {
//            User user = User.builder().name("newName").email("newUser@user.com").build();
//            service.addUser(user);
//            verify(repository, times(1)).save(user);
//        }

//        @Test
//        void deleteUser() {
//            service.deleteUser(1);
//            verify(repository, times(1)).deleteById(1L);
//        }
//
//        @Test
//        void getUserById() {
//            User user = User.builder().name("newName").email("newUser@user.com").build();
//            service.addUser(user);
//            when(repository.findById(1L)).thenReturn(Optional.of(user));
//            service.getUserById(1);
//            verify(repository, times(1)).findById(1L);
//        }
//
//        @Test
//        void getAllUsers() {
//            User user = User.builder().id(1).name("newName").email("newUser@user.com").build();
//            List<User> users = new ArrayList<>();
//            users.add(user);
//            when(repository.findAll()).thenReturn(users);
//            service.getAllUsers();
//            verify(repository, times(1)).findAll();
//        }
//    }
}

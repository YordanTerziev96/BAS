//package com.brokerage_agency_system.ControllerTests;
//
//import com.brokerage_agency_system.controller.UserController;
//import com.brokerage_agency_system.model.User;
//import com.brokerage_agency_system.DTO.UserCreateTO;
//import com.brokerage_agency_system.DTO.UserTO;
//import com.brokerage_agency_system.service.UserService;
//import com.brokerage_agency_system.validator.UserValidator;
//import jakarta.xml.bind.ValidationException;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.MockitoAnnotations;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//import java.util.Collections;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//class UserControllerTest {
//
//    @Mock
//    private UserService userService;
//
//    @InjectMocks
//    private UserController userController;
//
//    @Mock
//    private UserValidator userValidator;
//
//    private User existingUser;
//    private UserCreateTO userCreateTO;
//    private UserTO userTO;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//
//        // Sample user and data transfer objects (DTOs)
//        existingUser = User.builder()
//                .username("testUser")
//                .phone("1234567890")
//                .email("test@example.com")
//                .description("Test user description")
//                .build();
//
//        userCreateTO = new UserCreateTO();
//        userCreateTO.setUsername("newUser");
//        userCreateTO.setPassword("password");
//        userCreateTO.setRepeatedPassword("password");
//        userCreateTO.setPhone("0987654321");
//        userCreateTO.setEmail("newuser@example.com");
//
//        userTO = new UserTO();
//        userTO.setPhone("0987654321");
//        userTO.setEmail("updatedemail@example.com");
//        userTO.setDescription("Updated description");
//    }
//
//    @Test
//    void getUserByUsername_ShouldReturnUser_WhenUserExists() {
//        // Arrange
//        when(userService.getUserByUsername("testUser")).thenReturn(Optional.of(existingUser));
//
//        // Act
//        ResponseEntity<User> response = userController.getUserByUsername("testUser");
//
//        // Assert
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(existingUser, response.getBody());
//        verify(userService).getUserByUsername("testUser");
//    }
//
//    @Test
//    void getUserByUsername_ShouldReturnNotFound_WhenUserDoesNotExist() {
//        // Arrange
//        when(userService.getUserByUsername("nonExistentUser")).thenReturn(Optional.empty());
//
//        // Act
//        ResponseEntity<User> response = userController.getUserByUsername("nonExistentUser");
//
//        // Assert
//        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
//        assertNull(response.getBody());
//        verify(userService).getUserByUsername("nonExistentUser");
//    }
//
//    @Test
//    void getAllUsers_ShouldReturnListOfUsers() {
//        // Arrange
//        List<User> userList = Collections.singletonList(existingUser);
//        when(userService.getAllUsers()).thenReturn(userList);
//
//        // Act
//        ResponseEntity<List<User>> response = userController.getAllUsers();
//
//        // Assert
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(userList, response.getBody());
//        verify(userService).getAllUsers();
//    }
//
//    @Test
//    void createUser_ShouldReturnCreatedUser_WhenValidInput() {
//        // Arrange
//        when(userService.saveUser(userCreateTO)).thenReturn(existingUser);
//
//        // Act
//        ResponseEntity<?> response = userController.createUser(userCreateTO);
//
//        // Assert
//        assertEquals(HttpStatus.CREATED, response.getStatusCode());
//        assertEquals(existingUser, response.getBody());
//        verify(userService).saveUser(userCreateTO);
//    }
//
//    @Test
//    void createUser_ShouldReturnBadRequest_WhenInvalidInput() {
//        // Arrange
//        when(userService.saveUser(userCreateTO)).thenThrow(new IllegalArgumentException("Passwords do not match"));
//
//        // Act
//        ResponseEntity<?> response = userController.createUser(userCreateTO);
//
//        // Assert
//        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
//        verify(userValidator, Mockito.times(1)).validateForCreate(userCreateTO);
//        verify(userService).saveUser(userCreateTO);
//    }
//
//    @Test
//    void updateUser_ShouldReturnUpdatedUser_WhenValidInput() {
//        // Arrange
//        when(userService.updateUser(userTO, "testUser")).thenReturn(existingUser);
//
//        // Act
//        ResponseEntity<User> response = userController.updateUser("testUser", userTO);
//
//        // Assert
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(existingUser, response.getBody());
//        verify(userService).updateUser(userTO, "testUser");
//    }
//}
//

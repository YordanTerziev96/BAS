//package com.brokerage_agency_system.service;
//
//import com.brokerage_agency_system.model.User;
//import com.brokerage_agency_system.DTO.UserCreateTO;
//import com.brokerage_agency_system.DTO.UserTO;
//import com.brokerage_agency_system.repository.UserRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.ArgumentCaptor;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.util.Base64;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//public class UserServiceTest {
//
//    @Mock
//    private UserRepository userRepository;
//
//    @InjectMocks
//    private UserService userService;
//
//    private User existingUser;
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);  // Initialize mocks
//        existingUser = User.builder()
//                .username("testUser")
//                .phone("1234567890")
//                .email("test@example.com")
//                .description("Test user description")
//                .build();
//    }
//
//    @Test
//    public void testSaveUser_UserAlreadyExists_ThrowsException() {
//        // Arrange
//        UserCreateTO userTO = new UserCreateTO();
//        userTO.setUsername("existingUser");
//        when(userRepository.findByUsername("existingUser")).thenReturn(Optional.of(new User()));
//
//        // Act & Assert
//        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
//            userService.saveUser(userTO);
//        });
//
//        assertEquals("Username already exists!", exception.getMessage());
//        verify(userRepository, times(1)).findByUsername("existingUser");
//    }
//
//    @Test
//    public void testSaveUser_PasswordsDoNotMatch_ThrowsException() {
//        // Arrange
//        UserCreateTO userTO = new UserCreateTO();
//        userTO.setUsername("newUser");
//        userTO.setPassword("password123");
//        userTO.setRepeatedPassword("differentPassword");
//
//        when(userRepository.findByUsername("newUser")).thenReturn(Optional.empty());
//
//        // Act & Assert
//        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
//            userService.saveUser(userTO);
//        });
//
//        assertEquals("Passwords do not match!", exception.getMessage());
//        verify(userRepository, times(1)).findByUsername("newUser");
//    }
//
//    @Test
//    public void testSaveUser_SuccessfulSave_ReturnsUser() {
//        // Arrange
//        UserCreateTO userTO = new UserCreateTO();
//        userTO.setUsername("newUser");
//        userTO.setPassword("password123");
//        userTO.setRepeatedPassword("password123");
//        userTO.setPhone("123456789");
//        userTO.setEmail("user@example.com");
//
//        String encodedPassword = Base64.getEncoder().encodeToString(userTO.getPassword().getBytes());
//        User expectedUser = User.builder()
//                .username(userTO.getUsername())
//                .password(encodedPassword)
//                .phone(userTO.getPhone())
//                .email(userTO.getEmail())
//                .build();
//
//        when(userRepository.findByUsername("newUser")).thenReturn(Optional.empty());
//        when(userRepository.save(any(User.class))).thenReturn(expectedUser);
//
//        // Act
//        User savedUser = userService.saveUser(userTO);
//
//        // Assert
//        assertNotNull(savedUser);
//        assertEquals("newUser", savedUser.getUsername());
//        assertEquals(encodedPassword, savedUser.getPassword());
//        assertEquals("123456789", savedUser.getPhone());
//        assertEquals("user@example.com", savedUser.getEmail());
//
//        verify(userRepository, times(1)).findByUsername("newUser");
//        verify(userRepository, times(1)).save(any(User.class));
//    }
//
//    @Test
//    public void testGetUser_UserExists_ReturnsUser() {
//        // Arrange
//        User expectedUser = new User();
//        expectedUser.setUsername("existingUser");
//
//        when(userRepository.findByUsername(expectedUser.getUsername())).thenReturn(Optional.of(expectedUser));
//
//        // Act
//        Optional<User> result = userService.getUserByUsername(expectedUser.getUsername());
//
//        // Assert
//        assertTrue(result.isPresent());
//        assertEquals(expectedUser, result.get());
//        verify(userRepository, times(1)).findByUsername(expectedUser.getUsername());
//    }
//
//    @Test
//    public void testGetUser_UserDoesNotExist_ReturnsEmptyOptional() {
//        // Arrange
//        when(userRepository.findByUsername("username")).thenReturn(Optional.empty());
//
//        // Act
//        Optional<User> result = userService.getUserByUsername("username");
//
//        // Assert
//        assertFalse(result.isPresent());
//        verify(userRepository, times(1)).findByUsername("username");
//    }
//
//    @Test
//    void updateUser_ShouldUpdateUser_WhenUserExists() {
//        // Arrange
//        UserTO userTO = new UserTO();
//        userTO.setPhone("0987654321");
//        userTO.setEmail("newemail@example.com");
//        userTO.setDescription("Updated description");
//
//        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(existingUser));
//        when(userRepository.save(existingUser)).thenReturn(existingUser);
//
//        // Act
//        User updatedUser = userService.updateUser(userTO, "testUser");
//
//        // Assert
//        assertEquals("0987654321", updatedUser.getPhone());
//        assertEquals("newemail@example.com", updatedUser.getEmail());
//        assertEquals("Updated description", updatedUser.getDescription());
//        verify(userRepository).save(existingUser);
//    }
//
//    @Test
//    void updateUser_ShouldThrowException_WhenUserDoesNotExist() {
//        // Arrange
//        UserTO userTO = new UserTO();
//        userTO.setPhone("0987654321");
//        userTO.setEmail("newemail@example.com");
//        userTO.setDescription("Updated description");
//
//        when(userRepository.findByUsername("nonExistentUser")).thenReturn(Optional.empty());
//
//        // Act & Assert
//        IllegalArgumentException exception = assertThrows(
//                IllegalArgumentException.class,
//                () -> userService.updateUser(userTO, "nonExistentUser")
//        );
//
//        assertEquals("There is no such user with this username: nonExistentUser", exception.getMessage());
//        verify(userRepository, never()).save(any(User.class)); // Ensure save is never called
//    }
//}

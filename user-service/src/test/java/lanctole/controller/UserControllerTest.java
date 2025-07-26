package lanctole.controller;

import lanctole.dto.CreateOrUpdateUserDto;
import lanctole.dto.UserDto;
import lanctole.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Test
    void shouldReturnListOfUsers() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setName("Test User");
        userDto.setEmail("test@example.com");

        given(userService.getAll()).willReturn(Collections.singletonList(userDto));

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Test User"));
    }

    @Test
    void shouldReturnUserById() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setName("Test User");

        given(userService.getById(1L)).willReturn(userDto);

        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test User"));
    }

    @Test
    void shouldCreateUser() throws Exception {
        CreateOrUpdateUserDto createUserDto = new CreateOrUpdateUserDto();
        createUserDto.setName("New User");
        createUserDto.setEmail("new@example.com");

        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setName("New User");
        userDto.setEmail("new@example.com");

        given(userService.create(any(CreateOrUpdateUserDto.class))).willReturn(userDto);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"New User\",\"email\":\"new@example.com\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("New User"));
    }

    @Test
    void shouldUpdateUser() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setName("Updated User");

        given(userService.update(anyLong(), any())).willReturn(userDto);

        mockMvc.perform(put("/api/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Updated User\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated User"));
    }

    @Test
    void shouldDeleteUser() throws Exception {
        doNothing().when(userService).delete(1L);

        mockMvc.perform(delete("/api/users/1"))
                .andExpect(status().isNoContent());
    }
}
package com.ailinguo.controller;

import com.ailinguo.dto.UserDto;
import com.ailinguo.dto.auth.AuthResponse;
import com.ailinguo.dto.auth.LoginRequest;
import com.ailinguo.facade.IAILinguoFacade; // Importe a Facade
import com.ailinguo.model.User;
import com.ailinguo.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional; // Para limpar o banco após cada teste, se necessário

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

// Importações estáticas para facilitar
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors; // Para simular autenticação
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest // Carrega o contexto completo da aplicação Spring Boot
@AutoConfigureMockMvc // Configura o MockMvc para simular requisições HTTP
// @ActiveProfiles("test") // Opcional: Ativa um perfil de teste (ex: application-test.yml) se você tiver um
@Transactional // Opcional: Faz rollback das transações do banco após cada teste
class AuthControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc; // Ferramenta para simular requisições HTTP

    @Autowired
    private ObjectMapper objectMapper; // Para converter objetos Java <-> JSON

    @Autowired
    private UserRepository userRepository; // Acesso direto ao repositório para setup/cleanup

    @Autowired
    private PasswordEncoder passwordEncoder; // Para codificar senhas no setup

    // NÃO mockamos a Facade aqui para um teste de integração mais completo
    // @Autowired
    // private IAILinguoFacade aiLinguoFacade;

    // Limpa o repositório antes de cada teste para garantir isolamento
    @BeforeEach
    void setUpDatabase() {
        userRepository.deleteAll(); // Cuidado: Apaga todos os usuários! Use um banco de testes.
    }

    @Test
    void register_shouldReturnOkAndUserData_whenRequestIsValid() throws Exception {
        // Arrange: Prepara os dados da requisição de registro
        Map<String, Object> registrationRequest = new HashMap<>();
        registrationRequest.put("email", "register.test@example.com");
        registrationRequest.put("name", "Register User");
        registrationRequest.put("password", "password123");
        registrationRequest.put("cefrLevel", "A2");

        // Act & Assert: Simula a requisição POST para /api/auth/register
        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registrationRequest)))
                // Verifica o Status HTTP 200 OK
                .andExpect(status().isOk())
                // Verifica o conteúdo do JSON de resposta
                .andExpect(jsonPath("$.user", notNullValue()))
                .andExpect(jsonPath("$.user.name", is("Register User")))
                .andExpect(jsonPath("$.user.email", is("register.test@example.com")))
                .andExpect(jsonPath("$.user.cefrLevel", is("A2")))
                // Verifica se o cookie 'auth_token' foi setado
                .andExpect(cookie().exists("auth_token"))
                .andExpect(cookie().httpOnly("auth_token", true)) // Verifica HttpOnly
                .andExpect(cookie().path("auth_token", is("/"))); // Verifica o Path

        // Assert (Opcional): Verifica se o usuário foi realmente salvo no banco
        Optional<User> savedUser = userRepository.findByEmail("register.test@example.com");
        assertTrue(savedUser.isPresent(), "Usuário deveria ter sido salvo no banco de dados.");
        assertEquals("Register User", savedUser.get().getName());
    }

    @Test
    void register_shouldReturnBadRequest_whenEmailAlreadyExists() throws Exception {
        // Arrange: Cria um usuário existente no banco
        userRepository.save(User.builder()
                .email("existing@example.com")
                .name("Existing User")
                .password(passwordEncoder.encode("password123"))
                .cefrLevel(User.CefrLevel.A1)
                .createdAt(LocalDateTime.now())
                .build());

        // Prepara a requisição com o mesmo email
        Map<String, Object> registrationRequest = new HashMap<>();
        registrationRequest.put("email", "existing@example.com");
        registrationRequest.put("name", "New User");
        registrationRequest.put("password", "newpassword123");

        // Act & Assert: Espera um status 400 Bad Request
        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registrationRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", containsString("User already exists"))); // Verifica a mensagem de erro
    }

    @Test
    void register_shouldReturnBadRequest_whenInputIsInvalid() throws Exception {
        // Arrange: Prepara dados inválidos (ex: email sem formato correto)
        Map<String, Object> invalidRequest = new HashMap<>();
        invalidRequest.put("email", "invalid-email");
        invalidRequest.put("name", "Test");
        invalidRequest.put("password", "short"); // Senha curta (se houver validação)

        // Act & Assert: Espera um 400 Bad Request
        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", notNullValue())); // Verifica se há uma mensagem de erro
    }


    @Test
    void login_shouldReturnOkAndUserData_whenCredentialsAreValid() throws Exception {
        // Arrange: Cria um usuário no banco para o teste de login
        String userEmail = "login.test@example.com";
        String userPassword = "password123";
        User user = userRepository.save(User.builder()
                .email(userEmail)
                .name("Login User")
                .password(passwordEncoder.encode(userPassword)) // Senha codificada!
                .cefrLevel(User.CefrLevel.B1)
                .createdAt(LocalDateTime.now())
                .build());

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(userEmail);
        loginRequest.setPassword(userPassword); // Senha em texto plano

        // Act & Assert: Simula a requisição POST para /api/auth/login
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.user", notNullValue()))
                .andExpect(jsonPath("$.user.id", is(user.getId().toString()))) // Verifica o ID
                .andExpect(jsonPath("$.user.name", is("Login User")))
                .andExpect(jsonPath("$.user.email", is(userEmail)))
                .andExpect(cookie().exists("auth_token")); // Verifica o cookie
    }

    @Test
    void login_shouldReturnUnauthorized_whenPasswordIsInvalid() throws Exception {
        // Arrange: Cria um usuário
        String userEmail = "login.fail@example.com";
        userRepository.save(User.builder()
                .email(userEmail)
                .name("Login Fail User")
                .password(passwordEncoder.encode("correctPassword"))
                .cefrLevel(User.CefrLevel.A2)
                .createdAt(LocalDateTime.now())
                .build());

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(userEmail);
        loginRequest.setPassword("wrongPassword"); // Senha incorreta

        // Act & Assert: Espera um status 401 Unauthorized
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isUnauthorized()) // Status 401
                .andExpect(jsonPath("$.error", containsString("Invalid password")));
    }

    @Test
    void login_shouldReturnUnauthorized_whenUserNotFound() throws Exception {
        // Arrange: Prepara requisição para um usuário que não existe
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("nonexistent@example.com");
        loginRequest.setPassword("password123");

        // Act & Assert: Espera um status 401 Unauthorized
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.error", containsString("User not found")));
    }

    @Test
    // @WithMockUser(username="2") // Alternativa mais simples se só precisar do ID como String
    void getCurrentUser_shouldReturnUserData_whenAuthenticated() throws Exception {
        // Arrange: Cria um usuário para simular a autenticação
        User user = userRepository.save(User.builder()
                .email("me@example.com")
                .name("Current User")
                .password(passwordEncoder.encode("password"))
                .cefrLevel(User.CefrLevel.B2)
                .createdAt(LocalDateTime.now())
                .build());

        // Cria um objeto Authentication simulado (necessário importar SecurityMockMvcRequestPostProcessors)
        // O 'principal' deve ser o ID do usuário como String, pois é isso que o JwtAuthenticationFilter coloca
        org.springframework.security.authentication.UsernamePasswordAuthenticationToken authToken =
                new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(
                        user.getId().toString(), // Principal é o ID do usuário como String
                        null,
                        List.of() // Autoridades (pode deixar vazio se não usar roles)
                );

        // Act & Assert: Simula a requisição GET para /api/auth/me COM autenticação
        mockMvc.perform(get("/api/auth/me")
                        .with(SecurityMockMvcRequestPostProcessors.authentication(authToken))) // Adiciona a autenticação simulada à requisição
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.user", notNullValue()))
                .andExpect(jsonPath("$.user.id", is(user.getId().toString())))
                .andExpect(jsonPath("$.user.name", is("Current User")))
                .andExpect(jsonPath("$.user.email", is("me@example.com")));
    }

    @Test
    void getCurrentUser_shouldReturnEmptyUser_whenNotAuthenticated() throws Exception {
        // Act & Assert: Simula a requisição GET para /api/auth/me SEM autenticação
        mockMvc.perform(get("/api/auth/me"))
                .andExpect(status().isOk())
                // Espera um objeto 'user' presente, mas com campos nulos ou vazios (conforme sua lógica no controller)
                .andExpect(jsonPath("$.user", notNullValue()))
                .andExpect(jsonPath("$.user.id", nullValue()))
                .andExpect(jsonPath("$.user.name", nullValue()));
    }

    @Test
    void logout_shouldClearAuthCookie() throws Exception {
        // Arrange: Simula uma requisição com um cookie existente
        Cookie existingCookie = new Cookie("auth_token", "some-dummy-token-value");
        existingCookie.setPath("/");
        existingCookie.setHttpOnly(true);

        // Act & Assert: Simula a requisição POST para /api/auth/logout
        mockMvc.perform(post("/api/auth/logout")
                        .cookie(existingCookie)) // Envia o cookie na requisição
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                // Verifica se o cookie 'auth_token' foi removido (Max-Age=0)
                .andExpect(cookie().exists("auth_token"))
                .andExpect(cookie().maxAge("auth_token", is(0)));
    }
}
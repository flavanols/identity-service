# Identity Service — sơ đồ kiến trúc (Notion)

**Context path:** `/identity` (xem `application.yaml`).

## Cách đưa vào Notion

1. **Import Markdown:** Trong Notion: `⋯` trên trang → **Import** → chọn **Markdown** → upload file này.  
   Nếu khối Mermaid không tự vẽ, làm bước 2.
2. **Dán từng sơ đồ:** Tạo khối **Code** → đổi ngôn ngữ thành **Mermaid** → dán nội dung *bên trong* fence (chỉ phần `flowchart` / `classDiagram`, không dán dòng ` ```mermaid `).

---

## 1. Luồng request (flowchart)

```mermaid
flowchart LR
    A["IdentityServiceApplication.main()"] --> B["Spring Context"]
    B --> C["SecurityConfig.filterChain()\njwtDecoder() / jwtAuthenticationConverter()"]
    B --> D["ApplicationInitConfig.applicationRunner()"]
    D --> UR0["UserRepository.findByUsername('admin') / save(User)"]
    UR0 --> DB[(MySQL)]

    subgraph Auth
      R1["POST /identity/auth/token"] --> AC["AuthenticationController.authenticate(AuthenticationRequest)"]
      AC --> AS["AuthenticationService.authenticate()\nfindByUsername, BCrypt matches, generateToken"]
      AS --> UR1["UserRepository.findByUsername"]
      UR1 --> DB
      AS --> JWT["Nimbus JOSE\nMACSigner HS512"]
    end

    subgraph Introspect
      R2["POST /identity/auth/introspect"] --> AC2["AuthenticationController.authenticate(IntrospectRequest)"]
      AC2 --> AS2["AuthenticationService.introspect()\nSignedJWT.parse, MACVerifier, expiry"]
    end

    subgraph Users
      R3["/identity/users ..."] --> UC["UserController"]
      UC --> US["UserService"]
      US --> UM["UserMapper"]
      US --> UR2["UserRepository"]
      US --> PE["PasswordEncoder bean"]
      UR2 --> DB
    end

    subgraph Errors
      GE["GlobalExceptionHandler\nAppException / Exception / MethodArgumentNotValidException"]
    end

    C --> R1
    C --> R2
    C --> R3
```

---

## 2. Class & phụ thuộc chính (class diagram)

```mermaid
classDiagram
    direction TB

    class IdentityServiceApplication {
        +main(String[] args) void
    }

    class SecurityConfig {
        +filterChain(HttpSecurity) SecurityFilterChain
        +jwtAuthenticationConverter() JwtAuthenticationConverter
        +jwtDecoder() JwtDecoder
        +passwordEncoder() PasswordEncoder
    }

    class ApplicationInitConfig {
        -PasswordEncoder passwordEncoder
        +applicationRunner(UserRepository) ApplicationRunner
    }

    class AuthenticationController {
        -AuthenticationService authenticationService
        +authenticate(AuthenticationRequest) ApiResponse~AuthenticationResponse~
        +authenticate(IntrospectRequest) ApiResponse~IntrospectResponse~
    }

    class UserController {
        -UserService userService
        +createUser(UserCreationRequest) ApiResponse~UserResponse~
        +getUsers() ApiResponse (List UserResponse)
        +getUser(String) UserResponse
        +updateUser(String, UserUpdateRequest) UserResponse
        +deleteUser(String) String
    }

    class AuthenticationService {
        -UserRepository userRepository
        -String SIGNED_KEY
        +authenticate(AuthenticationRequest) AuthenticationResponse
        +introspect(IntrospectRequest) IntrospectResponse
        -generateToken(User) String
        -buildScope(User) String
    }

    class UserService {
        -UserRepository userRepository
        -UserMapper userMapper
        -PasswordEncoder passwordEncoder
        +createUser(UserCreationRequest) UserResponse
        +getUsers() List~UserResponse~
        +getUser(String) UserResponse
        +updateUser(String, UserUpdateRequest) UserResponse
        +deleteUser(String) void
    }

    class UserRepository {
        <<interface>>
        +existsByUsername(String) boolean
        +findByUsername(String) Optional~User~
    }

    class UserMapper {
        <<interface>>
        +toUser(UserCreationRequest) User
        +toUserResponse(User) UserResponse
        +updateUser(User, UserUpdateRequest) void
    }

    class User {
        String id
        String username
        String password
        String firstname
        String lastname
        LocalDate dob
        Set~String~ roles
    }

    class GlobalExceptionHandler {
        +handlingAppException(AppException) ResponseEntity
        +handlingRuntimeException(RuntimeException) ResponseEntity
        +handlingValidation(MethodArgumentNotValidException) ResponseEntity
    }

    class AppException {
        -ErrorCode errorCode
        +getErrorCode() ErrorCode
    }

    class ErrorCode {
        <<enumeration>>
    }

    AuthenticationController --> AuthenticationService : uses
    UserController --> UserService : uses
    AuthenticationService --> UserRepository : uses
    UserService --> UserRepository : uses
    UserService --> UserMapper : uses
    ApplicationInitConfig --> UserRepository : uses
    UserRepository ..> User : entity
    GlobalExceptionHandler ..> AppException : handles
    GlobalExceptionHandler ..> ErrorCode : uses
    AppException --> ErrorCode
```

---

*Tệp được tạo để import hoặc dán vào Notion; nội dung Mermaid tương thích trình vẽ Mermaid trong Notion.*

package com.example.writeo.controller;

import com.example.writeo.enums.ArticleStatus;
import com.example.writeo.model.Article;
import com.example.writeo.model.User;
import com.example.writeo.repository.ArticleRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.zalando.problem.ProblemModule;
import org.zalando.problem.violations.ConstraintViolationProblemModule;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {ArticleController.class/*, AuthenticationController.class*/}, properties = {"spring.datasource.url=jdbc:h2:mem:virtual", "spring.datasource.username=sa", "spring.datasource.password=password"})
@ActiveProfiles("test")
class ArticleControllerTest{

    @Autowired
    private MockMvc mockMvc;

//    @MockBean
//    private RoleRepository roleRepository;
//
//    @MockBean
//    private AuthEntryPointJwt authEntryPointJwt;

    @MockBean
    private ArticleRepository articleRepository;

//    @Autowired
//    UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    private ObjectMapper objectMapper;

    private List<Article> articleList;

    @BeforeEach
    void setup() {
        this.articleList = new ArrayList<>();
        this.articleList.add(new Article(0L,"The Butcher of Blaviken", "A brave witcher...", true, ArticleStatus.FreeToUse, 15 ,new User()));
        this.articleList.add(new Article(1L,"The Alpha Pack", "Deucalion...", false, ArticleStatus.NotForSale, 25 ,new User()));
        this.articleList.add(new Article(2L,"The Shining", "Here is Johnnyyy!", true, ArticleStatus.Auction, 1 ,new User()));

        objectMapper.registerModule(new ProblemModule());
        objectMapper.registerModule(new ConstraintViolationProblemModule());

//        AuthenticationController authenticationController = new AuthenticationController();
//        SignUpRequest signUpRequest = new SignUpRequest();
//        signUpRequest.setEmail("test@gmail.com");
//        signUpRequest.setPassword("12345678");
//        Set<String> roles = new HashSet<>();
//        roles.add("author");
//        signUpRequest.setRole(roles);
//        signUpRequest.setUsername("test");
//        authenticationController.registerUser(signUpRequest);
//        LoginRequest loginRequest = new LoginRequest();
//        loginRequest.setUsername("test");
//        loginRequest.setPassword("12345678");
//        authenticationController.authenticateUser(loginRequest);
    }

    @Test
    //@WithMockUser(username="tester",roles={"AUTHOR"})
    void getAllArticles() throws Exception{
        given(articleRepository.findAll()).willReturn(articleList);
//        AuthenticationController authenticationController = new AuthenticationController();
//        SignUpRequest signUpRequest = new SignUpRequest();
//        signUpRequest.setEmail("test@gmail.com");
//        signUpRequest.setPassword("12345678");
//        Set<String> roles = new HashSet<>();
//        roles.add("author");
//        signUpRequest.setRole(roles);
//        signUpRequest.setUsername("test");
//        //authenticationController.registerUser(signUpRequest);
//        LoginRequest loginRequest = new LoginRequest();
//        loginRequest.setUsername("test");
//        loginRequest.setPassword("12345678");
//        authenticationController.authenticateUser(loginRequest);
//
//        this.mockMvc.perform(post("/auth/signup")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(signUpRequest)))
//                .andExpect(status().isOk());
//
//        this.mockMvc.perform(post("/auth/signin")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(loginRequest)))
//                .andExpect(status().isOk());

        this.mockMvc.perform(get("/article/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(articleList.size())));
    }

    @Test
    void getArticleById() throws Exception{
        final long articleId = 1L;
        final Article article = articleList.get(0);
        given(articleRepository.findById(articleId)).willReturn(Optional.of(article));

        this.mockMvc.perform(get("/article/{id}", articleId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.articleTitle", is(article.getArticleTitle())))
                .andExpect(jsonPath("$.articleContent", is(article.getArticleContent())))
                .andExpect(jsonPath("$.articlePrice", is(article.getArticlePrice())))
                .andExpect(jsonPath("$.articlePublished", is(article.getArticlePublished())))
                .andExpect(jsonPath("$.articleStatus", is(article.getArticleStatus().toString())))
                .andExpect(jsonPath("$.author", is(article.getAuthor()), User.class));
    }

    @Test
    void getArticleByIdNotFound() throws Exception {
        final long articleId = 10L;
        given(articleRepository.findById(articleId)).willReturn(Optional.empty());

        this.mockMvc.perform(get("/article/{id}", articleId))
                .andExpect(status().isNotFound());
    }

    @Test
    void createArticle() throws Exception{
        given(articleRepository.save(any(Article.class))).willAnswer((invocation) -> invocation.getArgument(0));
        Article article = articleList.get(0);

        this.mockMvc.perform(post("/article/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(article)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.articleTitle", is(article.getArticleTitle())))
                .andExpect(jsonPath("$.articleContent", is(article.getArticleContent())))
                .andExpect(jsonPath("$.articlePrice", is(article.getArticlePrice())))
                .andExpect(jsonPath("$.articlePublished", is(article.getArticlePublished())))
                .andExpect(jsonPath("$.articleStatus", is(article.getArticleStatus().toString())))
                .andExpect(jsonPath("$.author", is(article.getAuthor()), User.class));

    }

    @Test
    void updateArticle() throws Exception{
        long articleId = 0L;
        Article article = articleList.get(0);
        given(articleRepository.findById(articleId)).willReturn(Optional.of(article));
        given(articleRepository.save(any(Article.class))).willAnswer((invocation) -> invocation.getArgument(0));

        this.mockMvc.perform(put("/article/update/{id}", article.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(article)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.articleTitle", is(article.getArticleTitle())))
                .andExpect(jsonPath("$.articleContent", is(article.getArticleContent())))
                .andExpect(jsonPath("$.articlePrice", is(article.getArticlePrice())))
                .andExpect(jsonPath("$.articlePublished", is(article.getArticlePublished())))
                .andExpect(jsonPath("$.articleStatus", is(article.getArticleStatus().toString())))
                .andExpect(jsonPath("$.author", is(article.getAuthor()), User.class));
    }

    @Test
    void updateNonExistentArticle() throws Exception{
        long articleId = 5L;
        given(articleRepository.findById(articleId)).willReturn(Optional.empty());
        Article article = articleList.get(0);

        this.mockMvc.perform(put("/article/update/{id}", articleId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(article)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteArticle() throws Exception{
        long articleId = 0L;
        Article article = articleList.get(0);
        given(articleRepository.findById(articleId)).willReturn(Optional.of(article));
        doNothing().when(articleRepository).deleteById(article.getId());

        this.mockMvc.perform(delete("/article/delete/{id}", articleId))
                .andExpect(status().isNoContent());
    }

//    @Test
//    void deleteNonExistentArticle() throws Exception{
//        long articleId = 1L;
//        given(articleRepository.findById(articleId)).willReturn(Optional.empty());
//
//        this.mockMvc.perform(delete("/article/delete/{id}", articleId))
//                .andExpect(status().isInternalServerError());
//    }
    //TODO:implement this

    @Test
    void deleteAllArticles() throws Exception{
        given(articleRepository.findAll()).willReturn(articleList);
        doNothing().when(articleRepository).deleteAll();

        this.mockMvc.perform(delete("/article/deleteAll"))
                .andExpect(status().isNoContent());
    }
}
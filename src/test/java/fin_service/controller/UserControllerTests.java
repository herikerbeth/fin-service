package fin_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import fin_service.domain.model.*;
import fin_service.service.UserService;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;


@WebMvcTest
public class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void givenUserObject_whenCreateUser_thenReturnSavedUser() throws Exception {

        // given - precondition or setup
        Account account = Account.builder()
                .number("0000000-0")
                .agency("0000")
                .balance(new BigDecimal("1234.64"))
                .limit(new BigDecimal("1000.00"))
                .build();
        Feature feature = new Feature("URL", "Descrição da Feature");
        Card card = Card.builder()
                .number("xxxx xxxx xxxx 0000")
                .limit(new BigDecimal("1000.00"))
                .build();
        News news = new News("URL", "Descrição da News");
        User user = User.builder()
                .name("Venilton")
                .account(account)
                .features(List.of(feature))
                .card(card)
                .news(List.of(news))
                .build();

        given(userService.create(any(User.class)))
                .willAnswer((invocation) -> invocation.getArgument(0));

        // when - action or behaviour that we are going test
        ResultActions response = mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)));

        // then - verify the result or output using assert statements
        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(user.getName())))
                .andExpect(jsonPath("$.account.number", is(user.getAccount().getNumber())))
                .andExpect(jsonPath("$.account.agency", is(user.getAccount().getAgency())))
                .andExpect(jsonPath("$.account.balance", is(user.getAccount().getBalance().doubleValue())))
                .andExpect(jsonPath("$.account.limit", is(user.getAccount().getLimit().doubleValue())))
                .andExpect(jsonPath("$.features[0].icon", is(user.getFeatures().get(0).getIcon())))
                .andExpect(jsonPath("$.features[0].description", is(user.getFeatures().get(0).getDescription())))
                .andExpect(jsonPath("$.card.number", is(user.getCard().getNumber())))
                .andExpect(jsonPath("$.card.limit", is(user.getCard().getLimit().doubleValue())))
                .andExpect(jsonPath("$.news[0].icon", is(user.getNews().get(0).getIcon())))
                .andExpect(jsonPath("$.news[0].description", is(user.getNews().get(0).getDescription())));
    }
}

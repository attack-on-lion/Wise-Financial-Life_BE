package com.wisespendinglife.wise_spending_life.domain.challenge;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wisespendinglife.wise_spending_life.domain.category.entity.Category;
import com.wisespendinglife.wise_spending_life.domain.category.repository.CategoryRepository;
import com.wisespendinglife.wise_spending_life.domain.challenge.dto.ChallengeCreateRequestDto;
import com.wisespendinglife.wise_spending_life.domain.challenge.entity.Challenge;
import com.wisespendinglife.wise_spending_life.domain.challenge.entity.ChallengeType;
import com.wisespendinglife.wise_spending_life.domain.challenge.repository.ChallengeRepository;
import com.wisespendinglife.wise_spending_life.domain.user.entity.UserEntity;
import com.wisespendinglife.wise_spending_life.domain.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("local")
public class ChallengeDomainTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ChallengeRepository challengeRepository;

    @Autowired
    JdbcTemplate jdbc;

    private UserEntity savedUser;
    private Category savedCategory;
    private Challenge savedChallenge;

    @BeforeEach
    void setUp() {
        // User data
        UserEntity user = UserEntity.builder()
                .name("테스트")
                .email("korea@kw.ac.kr")
                .age(20L)
                .gender("남자")
                .location("서울시 강북구")
                .baseAmount(100000L)
                .phoneNumber("010-1234-5678")
                .profileImgUrl("https://www.google.com/images/branding/googlelogo/2x/googlelogo_color_272x92dp.png")
                .isDeleted(false)
                .build();
        savedUser = userRepository.save(user);

        // AFTER
        Category category = Category.builder()
                .name("OUTFLOW")
                .build();
        savedCategory = categoryRepository.save(category);

        Challenge challenge = Challenge.builder()
                .user(savedUser)
                .isCompleted(false)
                .isDeleted(false)
                .challengeName("공통 챌린지")
                .startAt(LocalDate.now())
                .endAt(LocalDate.now().plusDays(5))
                .challengeType(ChallengeType.PAY_NOT)
                .build();
        savedChallenge = challengeRepository.save(challenge);
    }

    @BeforeEach
    void dropPrepared() {
        jdbc.execute("DEALLOCATE ALL");
    }

    @Test
    @DisplayName("성공: 챌린지 생성")
    void createChallenge_success() throws Exception {
        // given
        ChallengeCreateRequestDto requestDto = ChallengeCreateRequestDto.builder()
                .user_id(savedUser.getId())
                .challengeName("식비 챌린지")
                .challengeType(ChallengeType.PAY_LESS)
                .challengeDays(7L)
                .startAt(LocalDate.now())
                .endAt(LocalDate.now().plusDays(7))
                .createdAt(LocalDate.now())
                .categories(List.of(savedCategory.getName()))
                .build();

        // when & then
        mockMvc.perform(post("/api/challenges")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.msg").value("챌린지가 성공적으로 생성되었습니다."))
                .andDo(print());
    }

    @Test
    @DisplayName("성공: 유효한 챌린지 조회")
    void getValidChallenge_success() throws Exception {
        mockMvc.perform(get("/api/challenges")
                        .param("isCompleted", "false")
                        .param("isDeleted", "false"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.challengeName").value("공통 챌린지"))
                .andDo(print());
    }

    @Test
    @DisplayName("성공: 특정 챌린지 상세 조회")
    void getChallengeById_success() throws Exception {
        mockMvc.perform(get("/api/challenges/{challenge_id}", savedChallenge.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(savedChallenge.getId()))
                .andExpect(jsonPath("$.challengeName").value("공통 챌린지"))
                .andDo(print());
    }

    @Test
    @DisplayName("성공: 특정 챌린지 삭제(Soft Delete)")
    void deleteChallenge_success() throws Exception {
        // given
        Long challengeId = savedChallenge.getId();

        // when
        mockMvc.perform(delete("/api/challenges/{challenge_id}", challengeId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value("챌린지를 성공적으로 삭제했습니다."))
                .andDo(print());

        // then
        Challenge deletedChallenge = challengeRepository.findById(challengeId)
                .orElseThrow(() -> new AssertionError("챌린지가 DB에서 삭제되었습니다."));
        assertThat(deletedChallenge.getIsDeleted()).isTrue();
    }
}
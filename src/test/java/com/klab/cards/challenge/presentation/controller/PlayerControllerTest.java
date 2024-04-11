package com.klab.cards.challenge.presentation.controller;

import com.klab.cards.challenge.presentation.usecase.GameUseCase;
import com.klab.cards.challenge.presentation.usecase.PlayerUseCase;
import org.junit.jupiter.api.DisplayName;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
@DisplayName("Tests for Player Controller")
class PlayerControllerTest {

    @InjectMocks
    private PlayerController playerController;

    @Mock
    private PlayerUseCase playerUseCaseMock;

    @Mock
    private GameUseCase gameUseCaseMock;

    @Test
    void test() {

    }

}
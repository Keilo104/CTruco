package com.joao.alexandre.jormungandrbot;

import com.bueno.spi.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class JormungandrBotTest {
    private JormungandrBot jormungandrBot;
    private GameIntel.StepBuilder stepBuilder;

    @BeforeEach
    public void config() {
        jormungandrBot = new JormungandrBot();

    }

    @Nested
    @DisplayName("Testing getLowestCardInHand() function")
    class GetLowestCardInHandTest {

        @Test
        @DisplayName("When player has one card in hand, should return that one card as lowest card")
        void shouldReturnCardInHandIfHandOnlyHasOneCard() {
            TrucoCard vira = TrucoCard.of(CardRank.FOUR, CardSuit.DIAMONDS);

            List<TrucoCard> currentCards = List.of(
                    TrucoCard.of(CardRank.THREE, CardSuit.CLUBS)
            );

            stepBuilder = GameIntel.StepBuilder.with()
                    .gameInfo(List.of(), List.of(), vira, 1)
                    .botInfo(currentCards, 0)
                    .opponentScore(0);

            assertEquals(
                    TrucoCard.of(CardRank.THREE, CardSuit.CLUBS),
                    jormungandrBot.getLowestCardInHand(stepBuilder.build())
            );
        }

        @Test
        @DisplayName("With 3 cards in hand, should return the lowest card")
        void shouldReturnLowestCardInHand() {
            TrucoCard vira = TrucoCard.of(CardRank.KING, CardSuit.CLUBS);

            List<TrucoCard> currentCards = List.of(
                    TrucoCard.of(CardRank.SEVEN, CardSuit.CLUBS),
                    TrucoCard.of(CardRank.TWO, CardSuit.HEARTS),
                    TrucoCard.of(CardRank.QUEEN, CardSuit.SPADES)
            );

            stepBuilder = GameIntel.StepBuilder.with()
                    .gameInfo(List.of(), List.of(), vira, 1)
                    .botInfo(currentCards, 0)
                    .opponentScore(0);

            assertEquals(
                    TrucoCard.of(CardRank.SEVEN, CardSuit.CLUBS),
                    jormungandrBot.getLowestCardInHand(stepBuilder.build())
            );
        }

        @Test
        @DisplayName("Should return lowest card even accounting for manilha value")
        void shouldReturnLowestCardInHandEvenWhenManilhaIsLow() {
            TrucoCard vira = TrucoCard.of(CardRank.THREE, CardSuit.CLUBS);

            List<TrucoCard> currentCards = List.of(
                    TrucoCard.of(CardRank.FOUR, CardSuit.DIAMONDS),
                    TrucoCard.of(CardRank.FOUR, CardSuit.CLUBS),
                    TrucoCard.of(CardRank.FIVE, CardSuit.SPADES)
            );

            stepBuilder = GameIntel.StepBuilder.with()
                    .gameInfo(List.of(), List.of(), vira, 1)
                    .botInfo(currentCards, 0)
                    .opponentScore(0);

            assertEquals(
                    TrucoCard.of(CardRank.FIVE, CardSuit.SPADES),
                    jormungandrBot.getLowestCardInHand(stepBuilder.build())
            );
        }
    }

    @Nested
    @DisplayName("Testing getHighestCardInHand() function")
    class GetHighestCardInHandTest {

        @Test
        @DisplayName("When player has one card in hand, should return that one card as highest card")
        void shouldReturnCardInHandIfHandOnlyHasOneCard() {
            TrucoCard vira = TrucoCard.of(CardRank.FOUR, CardSuit.DIAMONDS);

            List<TrucoCard> currentCards = List.of(
                    TrucoCard.of(CardRank.THREE, CardSuit.CLUBS)
            );

            stepBuilder = GameIntel.StepBuilder.with()
                    .gameInfo(List.of(), List.of(), vira, 1)
                    .botInfo(currentCards, 0)
                    .opponentScore(0);

            assertEquals(
                    TrucoCard.of(CardRank.THREE, CardSuit.CLUBS),
                    jormungandrBot.getHighestCardInHand(stepBuilder.build())
            );
        }

        @Test
        @DisplayName("With 3 cards in hand, should return the highest card")
        void shouldReturnHighestCardInHand() {
            TrucoCard vira = TrucoCard.of(CardRank.KING, CardSuit.CLUBS);

            List<TrucoCard> currentCards = List.of(
                    TrucoCard.of(CardRank.SEVEN, CardSuit.CLUBS),
                    TrucoCard.of(CardRank.TWO, CardSuit.HEARTS),
                    TrucoCard.of(CardRank.QUEEN, CardSuit.SPADES)
            );

            stepBuilder = GameIntel.StepBuilder.with()
                    .gameInfo(List.of(), List.of(), vira, 1)
                    .botInfo(currentCards, 0)
                    .opponentScore(0);

            assertEquals(
                    TrucoCard.of(CardRank.TWO, CardSuit.HEARTS),
                    jormungandrBot.getHighestCardInHand(stepBuilder.build())
            );
        }

        @Test
        @DisplayName("Should return highest card even accounting for manilha value")
        void shouldReturnHighestCardInHandEvenWhenManilhaIsLow() {
            TrucoCard vira = TrucoCard.of(CardRank.THREE, CardSuit.CLUBS);

            List<TrucoCard> currentCards = List.of(
                    TrucoCard.of(CardRank.FOUR, CardSuit.DIAMONDS),
                    TrucoCard.of(CardRank.FOUR, CardSuit.CLUBS),
                    TrucoCard.of(CardRank.THREE, CardSuit.SPADES)
            );

            stepBuilder = GameIntel.StepBuilder.with()
                    .gameInfo(List.of(), List.of(), vira, 1)
                    .botInfo(currentCards, 0)
                    .opponentScore(0);

            assertEquals(
                    TrucoCard.of(CardRank.FOUR, CardSuit.CLUBS),
                    jormungandrBot.getHighestCardInHand(stepBuilder.build())
            );
        }
    }

    @Nested
    @DisplayName("Testing getCardToTieOpponentsCard() function")
    class GetCardToTieOpponentsCardTest {

        @Test
        @DisplayName("When player only has higher cards than opponent, should return empty")
        void shouldReturnEmptyWhenSelfOnlyHasHigherCardThanOpponent() {
            TrucoCard vira = TrucoCard.of(CardRank.JACK, CardSuit.DIAMONDS);

            List<TrucoCard> currentCards = List.of(
                    TrucoCard.of(CardRank.ACE, CardSuit.CLUBS),
                    TrucoCard.of(CardRank.THREE, CardSuit.SPADES),
                    TrucoCard.of(CardRank.TWO, CardSuit.SPADES)
            );

            TrucoCard opponentCard = TrucoCard.of(CardRank.QUEEN, CardSuit.DIAMONDS);

            stepBuilder = GameIntel.StepBuilder.with()
                    .gameInfo(List.of(), List.of(), vira, 1)
                    .botInfo(currentCards, 0)
                    .opponentScore(0)
                    .opponentCard(opponentCard);

            assertTrue(jormungandrBot.getCardToTieOpponentsCard(stepBuilder.build()).isEmpty());
        }

        @Test
        @DisplayName("When player only has cards close to opponent's, should return empty")
        void shouldReturnEmptyWhenSelfOnlyHasCloseCardsToTie() {
            TrucoCard vira = TrucoCard.of(CardRank.JACK, CardSuit.DIAMONDS);

            List<TrucoCard> currentCards = List.of(
                    TrucoCard.of(CardRank.ACE, CardSuit.CLUBS),
                    TrucoCard.of(CardRank.THREE, CardSuit.SPADES)
            );

            TrucoCard opponentCard = TrucoCard.of(CardRank.TWO, CardSuit.DIAMONDS);

            stepBuilder = GameIntel.StepBuilder.with()
                    .gameInfo(List.of(), List.of(), vira, 1)
                    .botInfo(currentCards, 0)
                    .opponentScore(0)
                    .opponentCard(opponentCard);

            assertTrue(jormungandrBot.getCardToTieOpponentsCard(stepBuilder.build()).isEmpty());
        }

        @Test
        @DisplayName("When player only has lower cards than opponent, should return empty")
        void shouldReturnEmptyWhenSelfOnlyHasLowerCardThanOpponent() {
            TrucoCard vira = TrucoCard.of(CardRank.JACK, CardSuit.DIAMONDS);

            List<TrucoCard> currentCards = List.of(
                    TrucoCard.of(CardRank.FOUR, CardSuit.CLUBS),
                    TrucoCard.of(CardRank.SIX, CardSuit.SPADES),
                    TrucoCard.of(CardRank.QUEEN, CardSuit.SPADES)
            );

            TrucoCard opponentCard = TrucoCard.of(CardRank.TWO, CardSuit.DIAMONDS);

            stepBuilder = GameIntel.StepBuilder.with()
                    .gameInfo(List.of(), List.of(), vira, 1)
                    .botInfo(currentCards, 0)
                    .opponentScore(0)
                    .opponentCard(opponentCard);

            assertTrue(jormungandrBot.getCardToTieOpponentsCard(stepBuilder.build()).isEmpty());
        }

        @Test
        @DisplayName("When player can tie with all their cards, should return any of them")
        void shouldReturnAnyOfTheCardIfTheyAllTie() {
            TrucoCard vira = TrucoCard.of(CardRank.JACK, CardSuit.DIAMONDS);

            List<TrucoCard> currentCards = List.of(
                    TrucoCard.of(CardRank.FOUR, CardSuit.CLUBS),
                    TrucoCard.of(CardRank.FOUR, CardSuit.SPADES),
                    TrucoCard.of(CardRank.FOUR, CardSuit.HEARTS)
            );

            TrucoCard opponentCard = TrucoCard.of(CardRank.FOUR, CardSuit.DIAMONDS);

            stepBuilder = GameIntel.StepBuilder.with()
                    .gameInfo(List.of(), List.of(), vira, 1)
                    .botInfo(currentCards, 0)
                    .opponentScore(0)
                    .opponentCard(opponentCard);

            Optional<TrucoCard> result = jormungandrBot.getCardToTieOpponentsCard(stepBuilder.build());

            assertTrue(result.isPresent());
            assertTrue(
                    result.equals(Optional.of(TrucoCard.of(CardRank.FOUR, CardSuit.CLUBS))) ||
                            result.equals(Optional.of(TrucoCard.of(CardRank.FOUR, CardSuit.SPADES))) ||
                            result.equals(Optional.of(TrucoCard.of(CardRank.FOUR, CardSuit.HEARTS)))
            );
        }

        @Test
        @DisplayName("Should return right card to tie when hand contains many close cards")
        void shouldReturnRightCardWhenManyCloseCards() {
            TrucoCard vira = TrucoCard.of(CardRank.SIX, CardSuit.DIAMONDS);

            List<TrucoCard> currentCards = List.of(
                    TrucoCard.of(CardRank.QUEEN, CardSuit.CLUBS),
                    TrucoCard.of(CardRank.JACK, CardSuit.SPADES),
                    TrucoCard.of(CardRank.KING, CardSuit.SPADES)
            );

            TrucoCard opponentCard = TrucoCard.of(CardRank.JACK, CardSuit.DIAMONDS);

            stepBuilder = GameIntel.StepBuilder.with()
                    .gameInfo(List.of(), List.of(), vira, 1)
                    .botInfo(currentCards, 0)
                    .opponentScore(0)
                    .opponentCard(opponentCard);

            Optional<TrucoCard> result = jormungandrBot.getCardToTieOpponentsCard(stepBuilder.build());

            assertTrue(result.isPresent());
            assertEquals(TrucoCard.of(CardRank.JACK, CardSuit.SPADES), result.orElseThrow());
        }

        @Test
        @DisplayName("Should throw a NoSuchElementException if opponent hasn't played a card yet")
        void shouldThrowExceptionIfOpponentHasntPlayedACard() {
            TrucoCard vira = TrucoCard.of(CardRank.SIX, CardSuit.DIAMONDS);

            List<TrucoCard> currentCards = List.of(
                    TrucoCard.of(CardRank.QUEEN, CardSuit.CLUBS),
                    TrucoCard.of(CardRank.JACK, CardSuit.SPADES),
                    TrucoCard.of(CardRank.KING, CardSuit.SPADES)
            );

            stepBuilder = GameIntel.StepBuilder.with()
                    .gameInfo(List.of(), List.of(), vira, 1)
                    .botInfo(currentCards, 0)
                    .opponentScore(0);

            assertThrows(NoSuchElementException.class,
                    () -> jormungandrBot.getCardToTieOpponentsCard(stepBuilder.build())
            );
        }
    }

    @Nested
    @DisplayName("Testing getLowestCardToBeatOpponentsCard() function")
    class GetLowestCardToBeatOpponentsCardTest {
        @Test
        @DisplayName("Should throw a NoSuchElementException if opponent hasn't played a card yet")
        void shouldThrowExceptionIfOpponentHasntPlayedACard() {
            TrucoCard vira = TrucoCard.of(CardRank.SIX, CardSuit.DIAMONDS);

            List<TrucoCard> currentCards = List.of(
                    TrucoCard.of(CardRank.QUEEN, CardSuit.CLUBS),
                    TrucoCard.of(CardRank.JACK, CardSuit.SPADES),
                    TrucoCard.of(CardRank.KING, CardSuit.SPADES)
            );

            stepBuilder = GameIntel.StepBuilder.with()
                    .gameInfo(List.of(), List.of(), vira, 1)
                    .botInfo(currentCards, 0)
                    .opponentScore(0);

            assertThrows(NoSuchElementException.class,
                    () -> jormungandrBot.getLowestCardToBeatOpponentsCard(stepBuilder.build())
            );
        }

        @Test
        @DisplayName("When player only has lower cards than opponent, should return empty")
        void shouldReturnEmptyWhenSelfOnlyHasLowerCardThanOpponent() {
            TrucoCard vira = TrucoCard.of(CardRank.JACK, CardSuit.DIAMONDS);

            List<TrucoCard> currentCards = List.of(
                    TrucoCard.of(CardRank.JACK, CardSuit.CLUBS),
                    TrucoCard.of(CardRank.SEVEN, CardSuit.SPADES),
                    TrucoCard.of(CardRank.SIX, CardSuit.SPADES)
            );

            TrucoCard opponentCard = TrucoCard.of(CardRank.ACE, CardSuit.DIAMONDS);

            stepBuilder = GameIntel.StepBuilder.with()
                    .gameInfo(List.of(), List.of(), vira, 1)
                    .botInfo(currentCards, 0)
                    .opponentScore(0)
                    .opponentCard(opponentCard);

            assertTrue(jormungandrBot.getLowestCardToBeatOpponentsCard(stepBuilder.build()).isEmpty());
        }

        @Test
        @DisplayName("When player has multiple high cards, should return the lowest one that beats")
        void shouldReturnLowestCardWhenMultipleHighCardsInHand() {
            TrucoCard vira = TrucoCard.of(CardRank.FOUR, CardSuit.DIAMONDS);

            List<TrucoCard> currentCards = List.of(
                    TrucoCard.of(CardRank.THREE, CardSuit.CLUBS),
                    TrucoCard.of(CardRank.TWO, CardSuit.SPADES),
                    TrucoCard.of(CardRank.ACE, CardSuit.SPADES)
            );

            TrucoCard opponentCard = TrucoCard.of(CardRank.SIX, CardSuit.DIAMONDS);

            stepBuilder = GameIntel.StepBuilder.with()
                    .gameInfo(List.of(), List.of(), vira, 1)
                    .botInfo(currentCards, 0)
                    .opponentScore(0)
                    .opponentCard(opponentCard);

            Optional<TrucoCard> result = jormungandrBot.getLowestCardToBeatOpponentsCard(stepBuilder.build());

            assertTrue(result.isPresent());
            assertEquals(TrucoCard.of(CardRank.ACE, CardSuit.SPADES), result.orElseThrow());
        }

        @Test
        @DisplayName("When player has cards close to opponent's, should return the lowest one")
        void shouldReturnLowestCardWhenCardsAreCloseToValue() {
            TrucoCard vira = TrucoCard.of(CardRank.FOUR, CardSuit.DIAMONDS);

            List<TrucoCard> currentCards = List.of(
                    TrucoCard.of(CardRank.THREE, CardSuit.CLUBS),
                    TrucoCard.of(CardRank.TWO, CardSuit.SPADES),
                    TrucoCard.of(CardRank.ACE, CardSuit.SPADES)
            );

            TrucoCard opponentCard = TrucoCard.of(CardRank.ACE, CardSuit.DIAMONDS);

            stepBuilder = GameIntel.StepBuilder.with()
                    .gameInfo(List.of(), List.of(), vira, 1)
                    .botInfo(currentCards, 0)
                    .opponentScore(0)
                    .opponentCard(opponentCard);

            Optional<TrucoCard> result = jormungandrBot.getLowestCardToBeatOpponentsCard(stepBuilder.build());

            assertTrue(result.isPresent());
            assertEquals(TrucoCard.of(CardRank.TWO, CardSuit.SPADES), result.orElseThrow());
        }
    }

    @Nested
    @DisplayName("Testing getHighestNonManilhaCardInHand() function")
    class GetHighestNonManilhaCardInHandTest {

        @Test
        @DisplayName("When player has 1 manilha and 1 non manilha on hand, should return the non manilha")
        void shouldReturnNonManilhaWhenOneManilhaAndOneNonManilhaOnHand() {
            TrucoCard vira = TrucoCard.of(CardRank.SIX, CardSuit.DIAMONDS);

            List<TrucoCard> currentCards = List.of(
                    TrucoCard.of(CardRank.SEVEN, CardSuit.CLUBS),
                    TrucoCard.of(CardRank.THREE, CardSuit.SPADES)
            );

            stepBuilder = GameIntel.StepBuilder.with()
                    .gameInfo(List.of(), List.of(), vira, 1)
                    .botInfo(currentCards, 0)
                    .opponentScore(0);

            Optional<TrucoCard> result = jormungandrBot.getHighestNonManilhaCardInHand(stepBuilder.build());

            assertTrue(result.isPresent());
            assertEquals(TrucoCard.of(CardRank.THREE, CardSuit.SPADES), result.orElseThrow());
        }

        @Test
        @DisplayName("When player has 1 manilha and 2 non manilha on hand, should return the highest of non manilha")
        void shouldReturnHighestNonManilha() {
            TrucoCard vira = TrucoCard.of(CardRank.SIX, CardSuit.DIAMONDS);

            List<TrucoCard> currentCards = List.of(
                    TrucoCard.of(CardRank.SEVEN, CardSuit.CLUBS),
                    TrucoCard.of(CardRank.TWO, CardSuit.DIAMONDS),
                    TrucoCard.of(CardRank.QUEEN, CardSuit.HEARTS)
            );

            stepBuilder = GameIntel.StepBuilder.with()
                    .gameInfo(List.of(), List.of(), vira, 1)
                    .botInfo(currentCards, 0)
                    .opponentScore(0);

            Optional<TrucoCard> result = jormungandrBot.getHighestNonManilhaCardInHand(stepBuilder.build());

            assertTrue(result.isPresent());
            assertEquals(TrucoCard.of(CardRank.TWO, CardSuit.DIAMONDS), result.orElseThrow());
        }

        @Test
        @DisplayName("Should return empty if hand only has manilhas")
        void shouldReturnEmptyIfHandIsFullOfManilhas() {
            TrucoCard vira = TrucoCard.of(CardRank.SIX, CardSuit.DIAMONDS);

            List<TrucoCard> currentCards = List.of(
                    TrucoCard.of(CardRank.SEVEN, CardSuit.CLUBS),
                    TrucoCard.of(CardRank.SEVEN, CardSuit.DIAMONDS),
                    TrucoCard.of(CardRank.SEVEN, CardSuit.HEARTS)
            );

            stepBuilder = GameIntel.StepBuilder.with()
                    .gameInfo(List.of(), List.of(), vira, 1)
                    .botInfo(currentCards, 0)
                    .opponentScore(0);

            assertTrue(jormungandrBot.getHighestNonManilhaCardInHand(stepBuilder.build()).isEmpty());
        }
    }

    @Nested
    @DisplayName("Testing isSecondToPlay() function")
    class IsSecondToPlayTest{

        @Test
        @DisplayName("Should return true if is second to play")
        void shouldReturnTrueIfIsSecondToPlay() {
            TrucoCard vira = TrucoCard.of(CardRank.SIX, CardSuit.DIAMONDS);
            TrucoCard opponentCard = TrucoCard.of(CardRank.QUEEN, CardSuit.CLUBS);

            stepBuilder = GameIntel.StepBuilder.with()
                    .gameInfo(List.of(), List.of(), vira, 1)
                    .botInfo(List.of(), 0)
                    .opponentScore(0)
                    .opponentCard(opponentCard);

            assertTrue(jormungandrBot.isSecondToPlay(stepBuilder.build()));
        }

        @Test
        @DisplayName("Should return false if is first to play")
        void shouldReturnFalseIfIsFirstToPlay() {
            TrucoCard vira = TrucoCard.of(CardRank.SIX, CardSuit.DIAMONDS);

            stepBuilder = GameIntel.StepBuilder.with()
                    .gameInfo(List.of(), List.of(), vira, 1)
                    .botInfo(List.of(), 0)
                    .opponentScore(0);

            assertFalse(jormungandrBot.isSecondToPlay(stepBuilder.build()));
        }
    }

    @Nested
    @DisplayName("Testing getCurrentRoundNumber() function")
    class GetCurrentRoundNumberTest {

        @Test
        @DisplayName("Should return 1 if First round")
        void shouldReturnOneIfFirstRound() {
            TrucoCard vira = TrucoCard.of(CardRank.SIX, CardSuit.DIAMONDS);
            List<GameIntel.RoundResult> roundResults = List.of();

            stepBuilder = GameIntel.StepBuilder.with()
                    .gameInfo(roundResults, List.of(), vira, 1)
                    .botInfo(List.of(), 0)
                    .opponentScore(0);

            assertEquals(1, jormungandrBot.getCurrentRoundNumber(stepBuilder.build()));
        }

        @Test
        @DisplayName("Should return 2 if Second round")
        void shouldReturnTwoIfSecondRound() {
            TrucoCard vira = TrucoCard.of(CardRank.SIX, CardSuit.DIAMONDS);
            List<GameIntel.RoundResult> roundResults = List.of();

            stepBuilder = GameIntel.StepBuilder.with()
                    .gameInfo(roundResults, List.of(), vira, 1)
                    .botInfo(List.of(), 0)
                    .opponentScore(0);

            assertEquals(1, jormungandrBot.getCurrentRoundNumber(stepBuilder.build()));
        }

        @Test
        @DisplayName("Should return 3 if Last round")
        void shouldReturnThreeIfLastRound() {
            TrucoCard vira = TrucoCard.of(CardRank.SIX, CardSuit.DIAMONDS);
            List<GameIntel.RoundResult> roundResults = List.of();

            stepBuilder = GameIntel.StepBuilder.with()
                    .gameInfo(roundResults, List.of(), vira, 1)
                    .botInfo(List.of(), 0)
                    .opponentScore(0);

            assertEquals(1, jormungandrBot.getCurrentRoundNumber(stepBuilder.build()));
        }
    }

    @Nested
    @DisplayName("Testing hasPlayedACard() function")
    class HasPlayedACardTest{
        @Test
        @DisplayName("Should return false if has 3 cards on hand and is first round")
        void shouldReturnFalseIfWithThreeCardsOnFirstRound(){
            List<GameIntel.RoundResult> results = List.of();
            TrucoCard vira = TrucoCard.of(CardRank.THREE, CardSuit.DIAMONDS);

            List<TrucoCard> myCards = List.of(
                    TrucoCard.of(CardRank.ACE, CardSuit.HEARTS),
                    TrucoCard.of(CardRank.KING, CardSuit.HEARTS),
                    TrucoCard.of(CardRank.QUEEN, CardSuit.CLUBS));

            stepBuilder = GameIntel.StepBuilder.with()
                    .gameInfo(results,List.of(), vira, 1)
                    .botInfo(myCards, 1)
                    .opponentScore(0);

            assertFalse(jormungandrBot.hasPlayedACard(stepBuilder.build()));
        }

        @Test
        @DisplayName("Should return true if has 1 cards on hand and is second round")
        void shouldReturnTrueIfWithOneCardOnSecondRound(){
            List<GameIntel.RoundResult> results = List.of(GameIntel.RoundResult.DREW);
            TrucoCard vira = TrucoCard.of(CardRank.THREE, CardSuit.DIAMONDS);

            List<TrucoCard> myCards = List.of(
                    TrucoCard.of(CardRank.ACE, CardSuit.HEARTS));

            stepBuilder = GameIntel.StepBuilder.with()
                    .gameInfo(results,List.of(), vira, 1)
                    .botInfo(myCards, 1)
                    .opponentScore(0);

            assertTrue(jormungandrBot.hasPlayedACard(stepBuilder.build()));
        }
        @Test
        @DisplayName("Should return true if has 0 cards on hand and is last round")
        void shouldReturnTrueIfWithZeroCardOnLastRound(){
            List<GameIntel.RoundResult> results = List.of(
                    GameIntel.RoundResult.DREW, GameIntel.RoundResult.LOST);
            TrucoCard vira = TrucoCard.of(CardRank.THREE, CardSuit.DIAMONDS);

            List<TrucoCard> myCards = List.of();

            stepBuilder = GameIntel.StepBuilder.with()
                    .gameInfo(results,List.of(), vira, 1)
                    .botInfo(myCards, 1)
                    .opponentScore(0);

            assertTrue(jormungandrBot.hasPlayedACard(stepBuilder.build()));
        }
    }

    @Nested
    @DisplayName("Testing getName()")
    class GetNameTest {

        @Test
        @DisplayName("Should display the accurate name when asking for name")
        void shouldDisplayAccurateName() {
            assertEquals("Jörmungandr", jormungandrBot.getName());
        }
    }

    @Nested
    @DisplayName("Testing chooseCardFirstRound()")
    class ChooseCardFirstRoundTest {

        @Test
        @DisplayName("If self has a card that beats the opponent's, should use the lowest one possible to do so")
        void shouldBeatOpponentsCardWithLowestPossible() {
            TrucoCard vira = TrucoCard.of(CardRank.SEVEN, CardSuit.DIAMONDS);

            List<TrucoCard> currentCards = List.of(
                    TrucoCard.of(CardRank.QUEEN, CardSuit.CLUBS),
                    TrucoCard.of(CardRank.ACE, CardSuit.SPADES),
                    TrucoCard.of(CardRank.FIVE, CardSuit.SPADES)
            );

            TrucoCard opponentCard = TrucoCard.of(CardRank.KING, CardSuit.DIAMONDS);

            stepBuilder = GameIntel.StepBuilder.with()
                    .gameInfo(List.of(), List.of(), vira, 1)
                    .botInfo(currentCards, 0)
                    .opponentScore(0)
                    .opponentCard(opponentCard);

            assertEquals(CardToPlay.of(TrucoCard.of(CardRank.ACE, CardSuit.SPADES)),
                    jormungandrBot.chooseCardFirstRound(stepBuilder.build())
            );
        }

        @Test
        @DisplayName("If self can't beat opponent but can tie, then they should force a tie")
        void shouldForceTieIfCantBeatOpponentsCard() {
            TrucoCard vira = TrucoCard.of(CardRank.SEVEN, CardSuit.DIAMONDS);

            List<TrucoCard> currentCards = List.of(
                    TrucoCard.of(CardRank.KING, CardSuit.CLUBS),
                    TrucoCard.of(CardRank.SIX, CardSuit.SPADES),
                    TrucoCard.of(CardRank.FIVE, CardSuit.SPADES)
            );

            TrucoCard opponentCard = TrucoCard.of(CardRank.KING, CardSuit.DIAMONDS);

            stepBuilder = GameIntel.StepBuilder.with()
                    .gameInfo(List.of(), List.of(), vira, 1)
                    .botInfo(currentCards, 0)
                    .opponentScore(0)
                    .opponentCard(opponentCard);

            assertEquals(CardToPlay.of(TrucoCard.of(CardRank.KING, CardSuit.CLUBS)),
                    jormungandrBot.chooseCardFirstRound(stepBuilder.build())
            );
        }

        @Test
        @DisplayName("If self can't beat opponent nor tie, then they should discard worst card")
        void shouldDiscardLowestCardIfCantBeatOrTie() {
            TrucoCard vira = TrucoCard.of(CardRank.SEVEN, CardSuit.DIAMONDS);

            List<TrucoCard> currentCards = List.of(
                    TrucoCard.of(CardRank.KING, CardSuit.CLUBS),
                    TrucoCard.of(CardRank.QUEEN, CardSuit.SPADES),
                    TrucoCard.of(CardRank.FIVE, CardSuit.SPADES)
            );

            TrucoCard opponentCard = TrucoCard.of(CardRank.QUEEN, CardSuit.CLUBS);

            stepBuilder = GameIntel.StepBuilder.with()
                    .gameInfo(List.of(), List.of(), vira, 1)
                    .botInfo(currentCards, 0)
                    .opponentScore(0)
                    .opponentCard(opponentCard);

            assertEquals(CardToPlay.of(TrucoCard.of(CardRank.FIVE, CardSuit.SPADES)),
                    jormungandrBot.chooseCardFirstRound(stepBuilder.build())
            );
        }

        @Test
        @DisplayName("If no manilhas and first, should play highest card")
        void shouldPlayHighestCardIfNoManilhasAndFirst() {
            TrucoCard vira = TrucoCard.of(CardRank.SEVEN, CardSuit.DIAMONDS);

            List<TrucoCard> currentCards = List.of(
                    TrucoCard.of(CardRank.KING, CardSuit.CLUBS),
                    TrucoCard.of(CardRank.THREE, CardSuit.SPADES),
                    TrucoCard.of(CardRank.FIVE, CardSuit.SPADES)
            );

            stepBuilder = GameIntel.StepBuilder.with()
                    .gameInfo(List.of(), List.of(), vira, 1)
                    .botInfo(currentCards, 0)
                    .opponentScore(0);

            assertEquals(CardToPlay.of(TrucoCard.of(CardRank.THREE, CardSuit.SPADES)),
                    jormungandrBot.chooseCardFirstRound(stepBuilder.build())
            );
        }

        @Test
        @DisplayName("If has a manilha but no other cards with relative value >7, should play highest card")
        void shouldPlayOnlyManilhaIfCardsAreLow() {
            TrucoCard vira = TrucoCard.of(CardRank.KING, CardSuit.DIAMONDS);

            List<TrucoCard> currentCards = List.of(
                    TrucoCard.of(CardRank.ACE, CardSuit.HEARTS),
                    TrucoCard.of(CardRank.SEVEN, CardSuit.HEARTS),
                    TrucoCard.of(CardRank.FIVE, CardSuit.CLUBS)
            );

            stepBuilder = GameIntel.StepBuilder.with()
                    .gameInfo(List.of(), List.of(), vira, 1)
                    .botInfo(currentCards, 0)
                    .opponentScore(0);

            assertEquals(CardToPlay.of(TrucoCard.of(CardRank.ACE, CardSuit.HEARTS)),
                    jormungandrBot.chooseCardFirstRound(stepBuilder.build())
            );
        }

        @Test
        @DisplayName("If has a manilha and other cards with relative value >7, should play non-manilha highest card")
        void shouldPlayNonManilhaIfCardsAreHigh() {
            TrucoCard vira = TrucoCard.of(CardRank.KING, CardSuit.DIAMONDS);

            List<TrucoCard> currentCards = List.of(
                    TrucoCard.of(CardRank.ACE, CardSuit.HEARTS),
                    TrucoCard.of(CardRank.KING, CardSuit.HEARTS),
                    TrucoCard.of(CardRank.TWO, CardSuit.CLUBS)
            );

            stepBuilder = GameIntel.StepBuilder.with()
                    .gameInfo(List.of(), List.of(), vira, 1)
                    .botInfo(currentCards, 0)
                    .opponentScore(0);

            assertEquals(CardToPlay.of(TrucoCard.of(CardRank.TWO, CardSuit.CLUBS)),
                    jormungandrBot.chooseCardFirstRound(stepBuilder.build())
            );
        }
    }

    @Nested
    @DisplayName("Testing chooseCardSecondRound()")
    class ChooseCardSecondRoundTest {
        @Test
        @DisplayName("If self has a card that beats the opponent's, should use the lowest one possible to do so after losing the first round")
        void shouldBeatOpponentsCardWithLowestPossibleIfLostFirstRound() {
            TrucoCard vira = TrucoCard.of(CardRank.SEVEN, CardSuit.DIAMONDS);
            List<GameIntel.RoundResult> roundResults = List.of(GameIntel.RoundResult.LOST);

            List<TrucoCard> currentCards = List.of(
                    TrucoCard.of(CardRank.QUEEN, CardSuit.CLUBS),
                    TrucoCard.of(CardRank.ACE, CardSuit.CLUBS)
            );

            TrucoCard opponentCard = TrucoCard.of(CardRank.KING, CardSuit.HEARTS);

            stepBuilder = GameIntel.StepBuilder.with()
                    .gameInfo(roundResults, List.of(), vira, 1)
                    .botInfo(currentCards, 0)
                    .opponentScore(0)
                    .opponentCard(opponentCard);

            assertEquals(CardToPlay.of(TrucoCard.of(CardRank.ACE, CardSuit.CLUBS)),
                    jormungandrBot.chooseCardFirstRound(stepBuilder.build())
            );
        }

        @Test
        @DisplayName("Should play highest card if last round was drawn")
        void shouldPlayHighestCardIfLastRoundWasDrawn() {
            TrucoCard vira = TrucoCard.of(CardRank.JACK, CardSuit.DIAMONDS);
            List<GameIntel.RoundResult> roundResults = List.of(GameIntel.RoundResult.DREW);

            List<TrucoCard> currentCards = List.of(
                    TrucoCard.of(CardRank.QUEEN, CardSuit.CLUBS),
                    TrucoCard.of(CardRank.ACE, CardSuit.CLUBS)
            );

            stepBuilder = GameIntel.StepBuilder.with()
                    .gameInfo(roundResults, List.of(), vira, 1)
                    .botInfo(currentCards, 0)
                    .opponentScore(0);

            assertEquals(CardToPlay.of(TrucoCard.of(CardRank.QUEEN, CardSuit.CLUBS)),
                    jormungandrBot.chooseCardFirstRound(stepBuilder.build())
            );
        }

        @Test
        @DisplayName("Should play the manilha if there's only one in hand and won first round")
        void shouldPlayManilhaIfHasOnlyOneAndWonFirstRound() {
            TrucoCard vira = TrucoCard.of(CardRank.FOUR, CardSuit.DIAMONDS);
            List<GameIntel.RoundResult> roundResults = List.of(GameIntel.RoundResult.WON);

            List<TrucoCard> currentCards = List.of(
                    TrucoCard.of(CardRank.FIVE, CardSuit.CLUBS),
                    TrucoCard.of(CardRank.ACE, CardSuit.CLUBS)
            );

            stepBuilder = GameIntel.StepBuilder.with()
                    .gameInfo(roundResults, List.of(), vira, 1)
                    .botInfo(currentCards, 0)
                    .opponentScore(0);

            assertEquals(CardToPlay.of(TrucoCard.of(CardRank.FIVE, CardSuit.CLUBS)),
                    jormungandrBot.chooseCardFirstRound(stepBuilder.build())
            );
        }

        @Test
        @DisplayName("Should play the lowest manilha if there's two in hand and won first round")
        void shouldPlayLowestManilhaIfHasTwoAndWonFirstRound() {
            TrucoCard vira = TrucoCard.of(CardRank.SIX, CardSuit.DIAMONDS);
            List<GameIntel.RoundResult> roundResults = List.of(GameIntel.RoundResult.WON);

            List<TrucoCard> currentCards = List.of(
                    TrucoCard.of(CardRank.SEVEN, CardSuit.DIAMONDS),
                    TrucoCard.of(CardRank.SEVEN, CardSuit.SPADES)
            );

            stepBuilder = GameIntel.StepBuilder.with()
                    .gameInfo(roundResults, List.of(), vira, 1)
                    .botInfo(currentCards, 0)
                    .opponentScore(0);

            assertEquals(CardToPlay.of(TrucoCard.of(CardRank.SEVEN, CardSuit.DIAMONDS)),
                    jormungandrBot.chooseCardFirstRound(stepBuilder.build())
            );
        }

        @Test
        @DisplayName("Should play the lowest card if self has no manilhas and won the first round")
        void shouldPlayLowestCardIfHasNoManilhasAndWonFirstRound() {
            TrucoCard vira = TrucoCard.of(CardRank.TWO, CardSuit.DIAMONDS);
            List<GameIntel.RoundResult> roundResults = List.of(GameIntel.RoundResult.WON);

            List<TrucoCard> currentCards = List.of(
                    TrucoCard.of(CardRank.KING, CardSuit.DIAMONDS),
                    TrucoCard.of(CardRank.SEVEN, CardSuit.CLUBS)
            );

            stepBuilder = GameIntel.StepBuilder.with()
                    .gameInfo(roundResults, List.of(), vira, 1)
                    .botInfo(currentCards, 0)
                    .opponentScore(0);

            assertEquals(CardToPlay.of(TrucoCard.of(CardRank.KING, CardSuit.DIAMONDS)),
                    jormungandrBot.chooseCardFirstRound(stepBuilder.build())
            );
        }

    }

    @Nested
    @DisplayName("Testing chooseCardThirdRound()")
    class ChooseCardThirdRoundTest {

        @Test
        @DisplayName("Should return CardToPlay of the one card in hand")
        void shouldReturnCardToPlayOfTheOneCardInHand() {
            TrucoCard vira = TrucoCard.of(CardRank.SIX, CardSuit.DIAMONDS);
            List<TrucoCard> trucoCards = List.of(
                    TrucoCard.of(CardRank.JACK, CardSuit.HEARTS)
            );

            stepBuilder = GameIntel.StepBuilder.with()
                    .gameInfo(List.of(), List.of(), vira, 1)
                    .botInfo(trucoCards, 0)
                    .opponentScore(0);

            assertEquals(
                    CardToPlay.of(TrucoCard.of(CardRank.JACK, CardSuit.HEARTS)),
                    jormungandrBot.chooseCardThirdRound(stepBuilder.build())
            );
        }
    }

    @Nested
    @DisplayName("Testing getSelfCardPlayed() function")
    class GetSelfCardPlayedTest{
        @Test
        @DisplayName("Should return empty if hasnt played a card")
        void shouldReturnEmptyIfHasntPlayedACard(){
            List<GameIntel.RoundResult> results = List.of();
            TrucoCard vira = TrucoCard.of(CardRank.THREE, CardSuit.DIAMONDS);

            List<TrucoCard> myCards = List.of(
                    TrucoCard.of(CardRank.FOUR, CardSuit.HEARTS),
                    TrucoCard.of(CardRank.JACK, CardSuit.DIAMONDS),
                    TrucoCard.of(CardRank.FIVE, CardSuit.CLUBS));

            stepBuilder = GameIntel.StepBuilder.with()
                    .gameInfo(results,List.of(), vira, 1)
                    .botInfo(myCards, 1)
                    .opponentScore(0);

            assertTrue(jormungandrBot.getSelfCardPlayed(stepBuilder.build()).isEmpty());
        }

        @Test
        @DisplayName("Should return Card if has played a card")
        void shouldReturnCardIfHasPlayedACard(){
            List<GameIntel.RoundResult> results = List.of();
            TrucoCard vira = TrucoCard.of(CardRank.THREE, CardSuit.DIAMONDS);

            List<TrucoCard> openCards = List.of(
                    vira,
                    TrucoCard.of(CardRank.TWO, CardSuit.HEARTS)
            );

            List<TrucoCard> myCards = List.of(
                    TrucoCard.of(CardRank.FOUR, CardSuit.HEARTS),
                    TrucoCard.of(CardRank.JACK, CardSuit.DIAMONDS));

            stepBuilder = GameIntel.StepBuilder.with()
                    .gameInfo(results,openCards, vira, 1)
                    .botInfo(myCards, 1)
                    .opponentScore(0);

            Optional<TrucoCard> response = jormungandrBot.getSelfCardPlayed(stepBuilder.build());
            assertTrue(response.isPresent());
            assertEquals(TrucoCard.of(CardRank.TWO, CardSuit.HEARTS), response.orElseThrow());
        }

    }

    @Nested
    @DisplayName("Testing getManilhaCountInHand() function")
    class GetManilhaCountInHandTest{

        @Test
        @DisplayName("Check if 3 manilhas in hand, return should be 3")
        void shouldReturnThreeWhenThreeManilhasInHand(){
            TrucoCard vira = TrucoCard.of(CardRank.THREE, CardSuit.DIAMONDS);

            List<TrucoCard> myCards = List.of(
                    TrucoCard.of(CardRank.FOUR, CardSuit.HEARTS),
                    TrucoCard.of(CardRank.FOUR, CardSuit.DIAMONDS),
                    TrucoCard.of(CardRank.FOUR, CardSuit.CLUBS));

            stepBuilder = GameIntel.StepBuilder.with()
                    .gameInfo(List.of(),List.of(), vira, 1)
                    .botInfo(myCards, 1)
                    .opponentScore(0);

            assertEquals(3, jormungandrBot.getManilhaCountInHand(stepBuilder.build()));
        }

        @Test
        @DisplayName("Check if 3 high cards in hand, return should be 0")
        void shouldReturnZeroWhenThreeHighCardsInHand(){
            TrucoCard vira = TrucoCard.of(CardRank.FOUR, CardSuit.DIAMONDS);

            List<TrucoCard> myCards = List.of(
                    TrucoCard.of(CardRank.THREE, CardSuit.HEARTS),
                    TrucoCard.of(CardRank.THREE, CardSuit.DIAMONDS),
                    TrucoCard.of(CardRank.THREE, CardSuit.CLUBS));

            stepBuilder = GameIntel.StepBuilder.with()
                    .gameInfo(List.of(),List.of(), vira, 1)
                    .botInfo(myCards, 1)
                    .opponentScore(0);

            assertEquals(0, jormungandrBot.getManilhaCountInHand(stepBuilder.build()));
        }
    }

    @Nested
    @DisplayName("Testing getCardCountInHandHigherThanRelativeValue() function")
    class GetCardCountInHandHigherThanRelativeValueTest {

        @Test
        @DisplayName("With one card above, one on the value and one below, return should be 1")
        void shouldReturnOneWhenOnlyOneCardIsAboveRelativeValueInHand() {
            TrucoCard vira = TrucoCard.of(CardRank.FIVE, CardSuit.DIAMONDS);

            List<TrucoCard> myCards = List.of(
                    TrucoCard.of(CardRank.KING, CardSuit.CLUBS), //below
                    TrucoCard.of(CardRank.TWO, CardSuit.HEARTS), //above
                    TrucoCard.of(CardRank.ACE, CardSuit.DIAMONDS)); //on

            stepBuilder = GameIntel.StepBuilder.with()
                    .gameInfo(List.of(), List.of(), vira, 1)
                    .botInfo(myCards, 1)
                    .opponentScore(0);

            assertEquals(1,
                    jormungandrBot.getCardCountInHandHigherThanRelativeValue(
                            stepBuilder.build(), 7));
        }

        @Test
        @DisplayName("With three cards below zap, and relative value is copas, return should be 0")
        void shouldReturnZeroWhenNoCardGreaterThanRelativeValue() {
            TrucoCard vira = TrucoCard.of(CardRank.FIVE, CardSuit.DIAMONDS);

            List<TrucoCard> myCards = List.of(
                    TrucoCard.of(CardRank.FOUR, CardSuit.CLUBS), //below
                    TrucoCard.of(CardRank.SIX, CardSuit.HEARTS), //above
                    TrucoCard.of(CardRank.THREE, CardSuit.DIAMONDS)); //on

            stepBuilder = GameIntel.StepBuilder.with()
                    .gameInfo(List.of(), List.of(), vira, 1)
                    .botInfo(myCards, 1)
                    .opponentScore(0);

            assertEquals(0,
                    jormungandrBot.getCardCountInHandHigherThanRelativeValue(
                            stepBuilder.build(), 12));
        }

        @Test
        @DisplayName("With vira is Four, should count five as high card")
        void shouldCountFiveAsHighCardIfViraIsFour() {
            TrucoCard vira = TrucoCard.of(CardRank.FOUR, CardSuit.DIAMONDS);

            List<TrucoCard> myCards = List.of(
                    TrucoCard.of(CardRank.FIVE, CardSuit.CLUBS), //below
                    TrucoCard.of(CardRank.FIVE, CardSuit.HEARTS), //above
                    TrucoCard.of(CardRank.KING, CardSuit.DIAMONDS)); //on

            stepBuilder = GameIntel.StepBuilder.with()
                    .gameInfo(List.of(), List.of(), vira, 1)
                    .botInfo(myCards, 1)
                    .opponentScore(0);

            assertEquals(2,
                    jormungandrBot.getCardCountInHandHigherThanRelativeValue(
                            stepBuilder.build(), 9));
        }

    }
}
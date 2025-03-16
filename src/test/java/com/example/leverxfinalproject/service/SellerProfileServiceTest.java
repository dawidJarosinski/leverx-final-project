package com.example.leverxfinalproject.service;

import com.example.leverxfinalproject.dto.request.SellerProfileRequest;
import com.example.leverxfinalproject.dto.response.SellerProfileResponse;
import com.example.leverxfinalproject.enums.Role;
import com.example.leverxfinalproject.model.SellerProfile;
import com.example.leverxfinalproject.model.User;
import com.example.leverxfinalproject.repository.CommentRepository;
import com.example.leverxfinalproject.repository.SellerProfileRepository;
import com.example.leverxfinalproject.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class SellerProfileServiceTest {

    @Mock
    private CommentRepository commentRepository;
    @Mock
    private SellerProfileRepository sellerProfileRepository;
    @Mock
    private UserRepository userRepository;

    private SellerProfileService underTest;

    @BeforeEach
    void setUp() {
        underTest = new SellerProfileService(
                sellerProfileRepository,
                userRepository,
                commentRepository);
    }

    @Test
    void FindAllSortedByRatingDescending_InvokeMethod_ShouldReturnSortedListOfSellerProfileResponses() {
        SellerProfile sellerProfile1 = new SellerProfile(
                1,
                "first seller profile",
                true,
                null);
        SellerProfile sellerProfile2 = new SellerProfile(
                2,
                "second seller profile",
                true,
                null);

        Mockito.when(sellerProfileRepository.findAll()).thenReturn(List.of(sellerProfile1, sellerProfile2));
        Mockito.when(commentRepository.findAverageRatingBySellerProfile(sellerProfile1)).thenReturn(3.0);
        Mockito.when(commentRepository.findAverageRatingBySellerProfile(sellerProfile2)).thenReturn(5.0);
        List<SellerProfileResponse> actual = underTest.findAllSortedByRatingDescending();

        Assertions.assertEquals(2, actual.size());
        Assertions.assertEquals(sellerProfile2.getId(), actual.get(0).id());
        Assertions.assertEquals(sellerProfile1.getId(), actual.get(1).id());
    }

    @Test
    void Save_InvokeMethod_ShouldSaveSellerProfileAndReturnSellerProfileResponse() {
        SellerProfileRequest sellerProfileRequest = new SellerProfileRequest(
                "best games on the planet"
        );
        String email = "email@email.com";
        User user = new User(
                1,
                "dawid",
                "dawid",
                "password",
                email,
                LocalDateTime.now(),
                Role.SELLER,
                true
        );

        Mockito.when(userRepository.findUserByEmail(email)).thenReturn(Optional.of(user));
        Mockito.when(sellerProfileRepository.existsSellerProfileByUser(user)).thenReturn(false);
        SellerProfileResponse actual = underTest.save(sellerProfileRequest, email);

        ArgumentCaptor<SellerProfile> sellerProfileArgumentCaptor = ArgumentCaptor.forClass(SellerProfile.class);
        Mockito.verify(sellerProfileRepository).save(sellerProfileArgumentCaptor.capture());
        Assertions.assertEquals(sellerProfileRequest.name(), sellerProfileArgumentCaptor.getValue().getName());
        Assertions.assertEquals(user.getId(), actual.userId());
        Assertions.assertEquals(sellerProfileRequest.name(), actual.name());

    }

    @Test
    void Save_InvokeWithUserHavingASellerProfile_ShouldThrowIllegalArgumentException() {
        SellerProfileRequest sellerProfileRequest = new SellerProfileRequest(
                "best games on the planet"
        );
        String email = "email@email.com";
        User user = new User(
                1,
                "dawid",
                "dawid",
                "password",
                email,
                LocalDateTime.now(),
                Role.SELLER,
                true
        );

        Mockito.when(userRepository.findUserByEmail(email)).thenReturn(Optional.of(user));
        Mockito.when(sellerProfileRepository.existsSellerProfileByUser(user)).thenReturn(true);

        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> underTest.save(sellerProfileRequest, email),
                "this user already has seller profile");
        Mockito.verify(sellerProfileRepository, Mockito.never()).save(Mockito.any(SellerProfile.class));
    }
}
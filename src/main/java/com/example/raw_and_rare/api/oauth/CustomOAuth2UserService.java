package com.example.raw_and_rare.api.oauth;

import com.example.raw_and_rare.entity.auth.user.User;
import com.example.raw_and_rare.entity.auth.user.UserStatus;
import com.example.raw_and_rare.repository.user.UserCommandRepository;
import com.example.raw_and_rare.repository.user.UserQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collections;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserCommandRepository userRepository;
    private final UserQueryRepository userQueryRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

//        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        Map<String, Object> attributes = oAuth2User.getAttributes();
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");

        /**
         * kakaoAccount{
         *     profile_nickname_needs_agreement,
         *     profile_image_needs_agreement,
         *     profile={
         *       nickname,
         *       thumbnail_image_url,
         *       profile_image_url,
         *       is_default_image,
         *       is_default_nickname
         *     }
         * }
         * */

//        String email = (String) kakaoAccount.get("email");
        String nickname = (String) profile.get("nickname");
        String profileImageUrl = (String) profile.get("profile_image_url");
        Long oauthId = Long.valueOf(attributes.get("id").toString());

        // 회원 가입 또는 조회
        User user = userQueryRepository.findUserByProfileId(oauthId).orElseGet(() -> {
            User newUser = User.builder().nickname(nickname).profileId(oauthId).created_at(Instant.now()).updated_at(Instant.now()).status(UserStatus.ACTIVE).build();
            return userRepository.save(newUser);
        });

        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")), attributes, userNameAttributeName/* "id" */);
    }
}
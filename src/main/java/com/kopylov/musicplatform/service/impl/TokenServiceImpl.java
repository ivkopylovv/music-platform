package com.kopylov.musicplatform.service.impl;

import com.kopylov.musicplatform.dao.AuthDAO;
import com.kopylov.musicplatform.dao.TokenDAO;
import com.kopylov.musicplatform.entity.Token;
import com.kopylov.musicplatform.entity.User;
import com.kopylov.musicplatform.exception.UnauthorizedException;
import com.kopylov.musicplatform.helper.DateHelper;
import com.kopylov.musicplatform.helper.TokenHelper;
import com.kopylov.musicplatform.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.kopylov.musicplatform.constants.ErrorMessage.USER_IS_UNAUTHORIZED;

@Service
@Transactional
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {
    private final TokenDAO tokenDAO;
    private final AuthDAO authDAO;
    private final TokenHelper tokenHelper;

    @Override
    public void saveToken(String username, String newRefreshToken) {
        Optional<Token> optionalToken = tokenDAO.findByUserUsername(username);
        User user;

        if (optionalToken.isPresent()) {
            Token token = optionalToken.get();
            tokenDAO.delete(token);
            user = token.getUser();
        } else {
            user = authDAO.findUserByUsername(username)
                    .orElseThrow(() -> new UnauthorizedException(USER_IS_UNAUTHORIZED));
        }

        Token newToken = new Token()
                .setToken(newRefreshToken)
                .setUser(user)
                .setExpirationDate(DateHelper
                        .getTokenTimeAlive(tokenHelper.getACCESS_TOKEN_TIME_ALIVE()));

        tokenDAO.save(newToken);
    }
}

package com.cassiomolin.example.security.service;

import com.cassiomolin.example.common.configuration.Configurable;
import com.cassiomolin.example.security.api.AuthenticationTokenDetails;
import com.cassiomolin.example.security.domain.Authority;
import com.cassiomolin.example.security.exception.AuthenticationTokenRefreshmentException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.ZonedDateTime;
import java.util.Set;
import java.util.UUID;

/**
 * Service which provides operations for authentication tokens.
 *
 * @author cassiomolin
 */
@ApplicationScoped
public class AuthenticationTokenService {

    /**
     * How long the token is valid for (in seconds).
     */
    @Inject
    @Configurable("authentication.jwt.validFor")
    private Long validFor;

    /**
     * How many times the token can be refreshed.
     */
    @Inject
    @Configurable("authentication.jwt.refreshLimit")
    private Integer refreshLimit;

    @Inject
    private AuthenticationTokenIssuer tokenIssuer;

    @Inject
    private AuthenticationTokenParser tokenParser;

    /**
     * Issue a token for a user with the given authorities.
     *
     * @param username
     * @param authorities
     * @return
     */
    public String issueToken(String username, Set<Authority> authorities) {

        String id = generateTokenIdentifier();
        ZonedDateTime issuedDate = ZonedDateTime.now();
        ZonedDateTime expirationDate = calculateExpirationDate(issuedDate);

        AuthenticationTokenDetails authenticationTokenDetails = new AuthenticationTokenDetails.Builder()
                .withId(id)
                .withUsername(username)
                .withAuthorities(authorities)
                .withIssuedDate(issuedDate)
                .withExpirationDate(expirationDate)
                .withRefreshCount(0)
                .withRefreshLimit(refreshLimit)
                .build();

        return tokenIssuer.issueToken(authenticationTokenDetails);
    }

    /**
     * Parse and validate the token.
     *
     * @param token
     * @return
     */
    public AuthenticationTokenDetails parseToken(String token) {
        return tokenParser.parseToken(token);
    }

    /**
     * Refresh a token.
     *
     * @param currentTokenDetails
     * @return
     */
    public String refreshToken(AuthenticationTokenDetails currentTokenDetails) {

        if (!currentTokenDetails.isEligibleForRefreshment()) {
            throw new AuthenticationTokenRefreshmentException("This token cannot be refreshed");
        }

        ZonedDateTime issuedDate = ZonedDateTime.now();
        ZonedDateTime expirationDate = calculateExpirationDate(issuedDate);

        AuthenticationTokenDetails newTokenDetails = new AuthenticationTokenDetails.Builder()
                .withId(currentTokenDetails.getId()) // Reuse the same id
                .withUsername(currentTokenDetails.getUsername())
                .withAuthorities(currentTokenDetails.getAuthorities())
                .withIssuedDate(issuedDate)
                .withExpirationDate(expirationDate)
                .withRefreshCount(currentTokenDetails.getRefreshCount() + 1)
                .withRefreshLimit(refreshLimit)
                .build();

        return tokenIssuer.issueToken(newTokenDetails);
    }

    /**
     * Calculate the expiration date for a token.
     *
     * @param issuedDate
     * @return
     */
    private ZonedDateTime calculateExpirationDate(ZonedDateTime issuedDate) {
        return issuedDate.plusSeconds(validFor);
    }

    /**
     * Generate a token identifier.
     *
     * @return
     */
    private String generateTokenIdentifier() {
        return UUID.randomUUID().toString();
    }
}
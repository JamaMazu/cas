package org.apereo.cas.authentication.mfa.trigger;

import org.apereo.cas.authentication.DefaultMultifactorAuthenticationProviderResolver;
import org.apereo.cas.authentication.mfa.TestMultifactorAuthenticationProvider;
import org.apereo.cas.authentication.principal.Service;
import org.apereo.cas.authentication.trigger.AuthenticationAttributeMultifactorAuthenticationTrigger;
import org.apereo.cas.configuration.CasConfigurationProperties;

import lombok.val;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * This is {@link AuthenticationAttributeMultifactorAuthenticationTriggerTests}.
 *
 * @author Misagh Moayyed
 * @since 6.1.0
 */
@Tag("MFA")
@DirtiesContext
public class AuthenticationAttributeMultifactorAuthenticationTriggerTests extends BaseMultifactorAuthenticationTriggerTests {
    @Test
    public void verifyOperationByProvider() {
        val props = new CasConfigurationProperties();
        val mfa = props.getAuthn().getMfa();
        mfa.setGlobalAuthenticationAttributeNameTriggers("category");
        mfa.setGlobalAuthenticationAttributeValueRegex(".+object.*");
        val trigger = new AuthenticationAttributeMultifactorAuthenticationTrigger(props,
            new DefaultMultifactorAuthenticationProviderResolver((providers, service, principal) -> providers.iterator().next()),
            applicationContext);
        val result = trigger.isActivated(authentication, registeredService, this.httpRequest, mock(Service.class));
        assertTrue(result.isPresent());
    }

    @Test
    public void verifyMultipleProvider() {
        val otherProvider = new TestMultifactorAuthenticationProvider();
        otherProvider.setId("mfa-other");
        TestMultifactorAuthenticationProvider.registerProviderIntoApplicationContext(applicationContext, otherProvider);

        val props = new CasConfigurationProperties();
        val mfa = props.getAuthn().getMfa();
        mfa.setGlobalAuthenticationAttributeNameTriggers("mfa-mode");
        mfa.setGlobalAuthenticationAttributeValueRegex(otherProvider.getId());
        val trigger = new AuthenticationAttributeMultifactorAuthenticationTrigger(props,
            new DefaultMultifactorAuthenticationProviderResolver((providers, service, principal) -> providers.iterator().next()),
            applicationContext);
        val result = trigger.isActivated(authentication, registeredService, this.httpRequest, mock(Service.class));
        assertTrue(result.isPresent());
    }
}

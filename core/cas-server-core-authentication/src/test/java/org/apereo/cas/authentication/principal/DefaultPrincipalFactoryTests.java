package org.apereo.cas.authentication.principal;

import lombok.val;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Handles tests for {@link DefaultPrincipalFactory}.
 *
 * @author Misagh Moayyed
 * @since 4.1
 */
public class DefaultPrincipalFactoryTests {

    private static final String UID = "uid";

    @Test
    public void checkCreatingSimplePrincipal() {
        val f = PrincipalFactoryUtils.newPrincipalFactory();
        val p = f.createPrincipal(UID);
        assertEquals(UID, p.getId());
        assertTrue(p.getAttributes().isEmpty());
    }

    @Test
    public void checkCreatingSimplePrincipalWithAttributes() {
        val f = PrincipalFactoryUtils.newPrincipalFactory();
        val p = f.createPrincipal(UID, Collections.singletonMap("mail", List.of("final@example.com")));
        assertEquals(UID, p.getId());
        assertEquals(1, p.getAttributes().size());
        assertTrue(p.getAttributes().containsKey("mail"));
    }

    @Test
    public void checkCreatingSimplePrincipalWithDefaultRepository() {
        val f = PrincipalFactoryUtils.newPrincipalFactory();
        val p = f.createPrincipal(UID);
        assertEquals(UID, p.getId());
        assertTrue(p.getAttributes().isEmpty());
    }

}

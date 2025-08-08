package uk.gov.companieshouse.presenter.account.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class IdGeneratorTest {

    private static final Pattern uuidPattern = Pattern.compile(
            "^[0-9a-z]{8}-[0-9a-z]{4}-[0-9a-z]{4}-[0-9a-z]{4}-[0-9a-z]{12}$");

    private IdGenerator idGenerator;

    @BeforeEach
    void beforeEach() {
        idGenerator = new IdGenerator();
    }

    @Test
    void testCreateUUID() {
        String uuid = idGenerator.createUUID();
        assertTrue(uuidPattern.matcher(uuid).matches());
    }

    @Test
    void testCreateUUIDAreUnique() {
        final int count = 5;
        Set<String> previousUuids = new HashSet<>();
        for (int i = 0; i < count; i++) {
            previousUuids.add(idGenerator.createUUID());
        }
        assertEquals(count, previousUuids.size());
    }

}

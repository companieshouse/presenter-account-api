package uk.gov.companieshouse.presenter.account.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class IdGeneratorTest {

    private final static Pattern uuidPattern = Pattern.compile("^[0-9a-z]{8}-[0-9a-z]{4}-[0-9a-z]{4}-[0-9a-z]{4}-[0-9a-z]{12}$");

    private IdGenerator idGenerator;

    @BeforeEach
    void beforeEach() {
        idGenerator = new IdGenerator();
    }
    
    @Test
    public void testCreateUUID() {
        String uuid = idGenerator.createUUID();
        assertTrue(uuidPattern.matcher(uuid).matches());
    }

    @Test
    public void testCreateUUIDAreUnique() {
        final int count = 5;
        Set<String> previousUuids = new HashSet<>();
        for (int i = 0; i < count; i++) {
            previousUuids.add(idGenerator.createUUID());
        }
        assertEquals(count, previousUuids.size());
    }

}

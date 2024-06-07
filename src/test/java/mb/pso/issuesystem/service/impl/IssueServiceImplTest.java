package mb.pso.issuesystem.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Date;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import com.arangodb.springframework.core.ArangoOperations;

import mb.pso.issuesystem.config.ArangoTestConfig;
import mb.pso.issuesystem.entity.Client;
import mb.pso.issuesystem.entity.Issue;

@SpringBootTest
@ContextConfiguration(classes = { ArangoTestConfig.class, IssueServiceImpl.class })
public class IssueServiceImplTest {

    @Autowired
    private IssueServiceImpl issueServiceImpl;
    @Autowired
    private ArangoOperations arangoOperations;

    String docNumber;
    Date docDate;
    Issue testIssue;
    Client client;

    @BeforeEach
    void initTestIssue() {
        this.docNumber = UUID.randomUUID().toString();
        this.docDate = new Date();
        this.client = new Client("testClient", "testAddress", "testPhoneNumber", "testemail@mail.ru");
        testIssue = new Issue();
        testIssue.setDocDate(docDate);
        testIssue.setDocNumber(docNumber);
        testIssue.setClient(client);
    }

    @Test
    void testCreate() {
        arangoOperations.dropDatabase();
        Issue resp = issueServiceImpl.create(testIssue);
        assertNotNull(resp);
        assertEquals(docDate, resp.getDocDate());
        assertEquals(docNumber, resp.getDocNumber());
        // assertNotNull(resp.getClient().getId());
        assertEquals(client, resp.getClient());

    }

    @Test
    void testDelete() {

    }

    @Test
    void testGet() {

    }

    @Test
    void testGetAll() {

    }

    @Test
    void testUpdate() {

    }
}

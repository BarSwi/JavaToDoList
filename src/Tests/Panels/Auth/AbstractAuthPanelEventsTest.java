package Tests.Panels.Auth;

import Main.Panels.Auth.AbstractAuthPanel;
import Main.Panels.Auth.AbstractAuthPanelEvents;
import org.junit.Test;
import org.mockito.Mockito;


import javax.swing.*;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class AbstractAuthPanelEventsTest {

    static class TestAuthPanelEvents extends AbstractAuthPanelEvents {
        @Override
        public Map<String, String> createAuthRequestBody(AbstractAuthPanel authPanel) throws IllegalArgumentException {
            return super.createAuthRequestBody(authPanel);
        }
    }
    @Test
    public void createAuthRequestBodyTest(){

        AbstractAuthPanel authPanelMock = Mockito.mock(AbstractAuthPanel.class);
        when(authPanelMock.getLoginInput()).thenReturn(new JTextField("TestLogin"));
        when(authPanelMock.getPasswordInput()).thenReturn(new JPasswordField("TestPassword"));

        TestAuthPanelEvents testAuthPanelEvents = new TestAuthPanelEvents() ;

        Map<String, String> testBody = testAuthPanelEvents.createAuthRequestBody(authPanelMock);

        assertEquals(testBody.get("username"), "TestLogin");
        assertEquals(testBody.get("password"), "TestPassword");
        assertEquals(testBody.size(), 2);
    }
}

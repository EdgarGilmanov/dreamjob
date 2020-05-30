package ru.job4j.dream.servlets;

import org.junit.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import ru.job4j.dream.store.PsqlStore;
import ru.job4j.dream.store.Store;
import ru.job4j.dream.store.ValidateStore;


@RunWith(PowerMockRunner.class)
@PrepareForTest(PsqlStore.class)
public class RegServletTest {

    @Test
    public void whenAddUserThenStoreIt() throws ServletException, IOException {
        Store validate = new ValidateStore();
        PowerMockito.mockStatic(PsqlStore.class);
        Mockito.when(PsqlStore.instOf()).thenReturn(validate);
        HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse resp = Mockito.mock(HttpServletResponse.class);
        HttpSession sc = Mockito.mock(HttpSession.class);
        Mockito.when(req.getSession()).thenReturn(sc);
        Mockito.when(req.getParameter("name")).thenReturn("TestMock");
        Mockito.when(req.getParameter("email")).thenReturn("TestMock");
        Mockito.when(req.getParameter("password")).thenReturn("TestMock");
        new RegServlet().doPost(req, resp);
        assertThat(validate.findAllUsers().iterator().next().getName(), is("TestMock"));
    }
}
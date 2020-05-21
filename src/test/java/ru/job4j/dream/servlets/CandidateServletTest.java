package ru.job4j.dream.servlets;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import ru.job4j.dream.store.PsqlStore;
import ru.job4j.dream.store.Store;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(PowerMockRunner.class)
@PrepareForTest({PsqlStore.class})
public class CandidateServletTest {
    @Test
    public void whenSaveNewCandidate() throws ServletException, IOException {
        Store validate = new ValidateStore();
        PowerMockito.mockStatic(PsqlStore.class);
        Mockito.when(PsqlStore.instOf()).thenReturn(validate);
        HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse resp = Mockito.mock(HttpServletResponse.class);
        Mockito.when(req.getParameter("file")).thenReturn("TestMock");
        new CandidateServlet().doPost(req, resp);
        assertThat(validate.findAllCandidates().iterator().next().getPhoto(), is("TestMock"));
    }
}
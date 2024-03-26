package pl.falynsky.course1.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.falynsky.course1.config.LoginCredentials;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StringUtilsTest {

    @Mock
    List<String> mockedList;

    @Mock
    LoginCredentials mockedCredentials;

    @Spy
    List<String> spiedList = new ArrayList<>();

    @Test
    public void whenUseMockAnnotation_thenMockIsInjected() {
        mockedList.add("one");
        verify(mockedList).add("one");
        assertEquals(0, mockedList.size());

        when(mockedList.size()).thenReturn(100);
        assertEquals(100, mockedList.size());
    }

    @Test
    public void whenUseMockAnnotation_thenMockedStringIsInjected() {
        when(mockedCredentials.getPassword()).thenReturn("xd");
        assertEquals("xd", mockedCredentials.getPassword());
    }

    @Test
    public void whenUseSpyAnnotation_thenSpyIsInjectedCorrectly() {
        spiedList.add("one");
        spiedList.add("two");

        verify(spiedList).add("one");
        verify(spiedList).add("two");

        assertEquals(2, spiedList.size());

        doReturn(100).when(spiedList).size();
        assertEquals(100, spiedList.size());
    }
}
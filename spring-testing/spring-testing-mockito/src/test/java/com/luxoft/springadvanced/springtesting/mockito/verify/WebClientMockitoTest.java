package com.luxoft.springadvanced.springtesting.mockito.verify;

import com.luxoft.springadvanced.springtesting.mockito.web.ConnectionFactory;
import com.luxoft.springadvanced.springtesting.mockito.web.WebClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WebClientMockitoTest {
    @Mock
    private ConnectionFactory factory;

    @Mock
    private InputStream mockStream;
    private final WebClient client = new WebClient();

    @BeforeEach
    public void beforeEach() {
        when(factory.getData()).thenReturn(mockStream);
    }

    @Test
    public void testGetContentOk() throws Exception {
        when(mockStream.read())
                .thenReturn((int) 'S')
                .thenReturn((int) 'p')
                .thenReturn((int) 'r')
                .thenReturn((int) 'i')
                .thenReturn((int) 'n')
                .thenReturn((int) 'g')
                .thenReturn(-1);

        final var actual = client.getContent(factory);

        assertAll(
                () -> verify(mockStream, times(7)).read(),
                () -> assertEquals("Spring", actual)
        );

    }

    @Test
    public void testGetContentCannotCloseInputStream()
            throws Exception {
        when(mockStream.read()).thenReturn(-1);
        doThrow(new IOException("cannot close")).when(mockStream).close();

        final var actual = client.getContent(factory);

        assertAll(
                () -> verify(mockStream, times(1)).read(),
                () -> assertNull(actual)
        );
    }
}

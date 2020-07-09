package com.bigdata.boot.chapter79.snake;

import java.io.IOException;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.mock;

public class SnakeTimerTests {

	@Test
	public void removeDysfunctionalSnakes() throws Exception {
		Snake snake = mock(Snake.class);
		willThrow(new IOException()).given(snake).sendMessage(anyString());
		SnakeTimer.addSnake(snake);

		SnakeTimer.broadcast("");
		assertThat(SnakeTimer.getSnakes()).hasSize(0);
	}

}
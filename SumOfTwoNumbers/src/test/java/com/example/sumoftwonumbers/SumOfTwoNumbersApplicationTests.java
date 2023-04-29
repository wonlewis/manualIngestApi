package com.example.sumoftwonumbers;

import com.example.sumoftwonumbers.Sum.AddTwoNumbers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SumOfTwoNumbersApplicationTests {

	@Autowired
	AddTwoNumbers addTwoNumbers;

	@Test
	void testSumOfTWoNumbers() {
		double x = 11.11;
		double y = 22.22;
		assertEquals(11.11+22.22,addTwoNumbers.sum(11.11, 22.22));
	}

}

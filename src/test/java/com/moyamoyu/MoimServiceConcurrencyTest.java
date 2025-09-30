package com.moyamoyu;

import com.moyamoyu.repository.MoimRepository;
import com.moyamoyu.service.MoimService;
import com.moyamoyu.util.JwtUtil;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class MoimServiceConcurrencyTest {
    @MockitoBean
    private JwtUtil jwtUtil;
    @Autowired
    private MoimService moimService;
    @Autowired
    private MoimRepository moimRepository;

    @Test
    @Disabled("동시성 테스트 수동 실행")
    void testConcurrentViewIncrement() throws InterruptedException {
        Long moimId = 1L;

        ExecutorService executor = Executors.newFixedThreadPool(100);
        CountDownLatch latch = new CountDownLatch(100);

        for (int i = 0; i < 100; i++) {
            executor.submit(() -> {
                try {
                    moimService.findMoim(moimId);
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executor.shutdown();

        Long finalCount = moimRepository.findById(moimId).get().getViewCount();
        System.out.println("최종 조회수: " + finalCount);
        assertEquals(100, finalCount);
    }
}


package com.example.restaurant.batch.jobs.pseudonimize.step.create;

import com.example.restaurant.batch.jobs.pseudonimize.constant.TransferTable;
import com.example.restaurant.batch.jobs.pseudonimize.step.create.reader.TransferReaderStrategy;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ReaderStrategyFactory<E> {
    //전략으로 구현한 리더 클래스의 실제 리더에서 파티셔니이 안된다가 이슈

    private final Map<TransferTable, TransferReaderStrategy<E>> trasferReaderMap = new HashMap<>();

    public ReaderStrategyFactory(List<TransferReaderStrategy<E>> stList) {
        for (TransferReaderStrategy<E> st : stList) {
            trasferReaderMap.put(st.getTableType(), st);
        }
    }

    public TransferReaderStrategy<E> getTransferReaderStrategy(TransferTable type) {
        return Optional.ofNullable(trasferReaderMap.get(type)).orElseThrow(() -> new NoSuchElementException("nono : " + type));
    }

}

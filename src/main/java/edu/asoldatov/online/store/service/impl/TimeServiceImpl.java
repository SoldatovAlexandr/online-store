package edu.asoldatov.online.store.service.impl;

import edu.asoldatov.online.store.service.TimeService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TimeServiceImpl implements TimeService {

    @Override
    public LocalDateTime now() {
        return LocalDateTime.now();
    }
}

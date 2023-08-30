package net.example.postaltrackingservice.service.impl;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import net.example.postaltrackingservice.mapper.PostOfficeMapper;
import net.example.postaltrackingservice.model.dto.PostOfficeDto;
import net.example.postaltrackingservice.repository.PostOfficeRepository;
import net.example.postaltrackingservice.service.PostOfficeService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostOfficeServiceImpl implements PostOfficeService {

    private final PostOfficeRepository postOfficeRepository;
    private final PostOfficeMapper postOfficeMapper;

    @Override
    public boolean create(@NotNull PostOfficeDto postOfficeDto) {

        return Optional.of(postOfficeDto)
                .map(postOfficeMapper::map)
                .map(postOffice -> {
                    postOfficeRepository.save(postOffice);
                    return true;
                })
                .orElse(false);
    }

}

package mate.academy.rickandmorty.service.impl;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mate.academy.rickandmorty.dto.external.CharacterResponseDto;
import mate.academy.rickandmorty.dto.external.RickAndMortyResponseDto;
import mate.academy.rickandmorty.model.Character;
import mate.academy.rickandmorty.repository.CharacterRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {
    private static final String API_URL = "https://rickandmortyapi.com/api/character";
    private static final long PAUSE_BETWEEN_REQUESTS_MS = 500L;
    private static final long RETRY_PAUSE_MS = 3000L;

    private final CharacterRepository characterRepository;
    private final RestTemplate restTemplate;

    @Override
    public void run(String... args) {
        if (characterRepository.count() > 0) {
            log.info("Database already contains data. Skipping initialization.");
            return;
        }

        log.info("Starting data synchronization from Rick & Morty API...");
        String url = API_URL;
        List<Character> charactersToSave = new ArrayList<>();

        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)");
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        while (url != null) {
            try {
                ResponseEntity<RickAndMortyResponseDto> responseEntity = restTemplate.exchange(
                        url,
                        HttpMethod.GET,
                        requestEntity,
                        RickAndMortyResponseDto.class
                );

                RickAndMortyResponseDto response = responseEntity.getBody();

                if (response != null && response.getResults() != null) {
                    for (CharacterResponseDto externalDto : response.getResults()) {
                        Character character = new Character();
                        character.setExternalId(externalDto.getId());
                        character.setName(externalDto.getName());
                        character.setStatus(externalDto.getStatus());
                        character.setGender(externalDto.getGender());
                        charactersToSave.add(character);
                    }
                    url = (response.getInfo() != null) ? response.getInfo().getNext() : null;
                } else {
                    url = null;
                }

                // Затримка між запитами, щоб не перевищити rate limit
                Thread.sleep(PAUSE_BETWEEN_REQUESTS_MS);

            } catch (HttpClientErrorException.TooManyRequests e) {
                log.warn("Rate limit hit (429). Waiting {} ms before retrying page: {}",
                        RETRY_PAUSE_MS, url);
                try {
                    Thread.sleep(RETRY_PAUSE_MS);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException("Thread interrupted during retry delay", ex);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Thread interrupted during sync pause", e);
            }
        }

        characterRepository.saveAll(charactersToSave);
        log.info("Successfully synchronized {} characters.", characterRepository.count());
    }
}

package mate.academy.rickandmorty.service.impl;

import java.util.List;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import mate.academy.rickandmorty.dto.CharacterDto;
import mate.academy.rickandmorty.exception.EntityNotFoundException;
import mate.academy.rickandmorty.mapper.CharacterMapper;
import mate.academy.rickandmorty.repository.CharacterRepository;
import mate.academy.rickandmorty.service.CharacterService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CharacterServiceImpl implements CharacterService {
    private final CharacterRepository characterRepository;
    private final CharacterMapper characterMapper;
    private final Random random = new Random();

    @Override
    public CharacterDto getRandomCharacter() {
        long count = characterRepository.count();
        if (count == 0) {
            throw new EntityNotFoundException("No characters available in the local database.");
        }

        long randomId = random.nextLong(count) + 1;
        return characterRepository.findById(randomId)
                // Якщо типи збігаються, цей метод-референс працюватиме ідеально:
                .map(characterMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Character not found."));
    }

    @Override
    public List<CharacterDto> searchByName(String name) {
        return characterRepository.findAllByNameContainingIgnoreCase(name).stream()
                // Тут також використовується посилання на метод:
                .map(characterMapper::toDto)
                .toList();
    }
}

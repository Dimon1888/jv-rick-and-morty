package mate.academy.rickandmorty.service.impl;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import lombok.RequiredArgsConstructor;
import mate.academy.rickandmorty.dto.CharacterDto;
import mate.academy.rickandmorty.dto.CharacterSearchParametersDto;
import mate.academy.rickandmorty.exception.EntityNotFoundException;
import mate.academy.rickandmorty.mapper.CharacterMapper;
import mate.academy.rickandmorty.model.Character;
import mate.academy.rickandmorty.repository.CharacterRepository;
import mate.academy.rickandmorty.repository.character.CharacterSpecificationBuilder;
import mate.academy.rickandmorty.service.CharacterService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CharacterServiceImpl implements CharacterService {
    private final CharacterRepository characterRepository;
    private final CharacterMapper characterMapper;
    private final CharacterSpecificationBuilder characterSpecificationBuilder;

    @Override
    public List<CharacterDto> getAll(Pageable pageable) {
        return characterRepository.findAll(pageable)
                .map(characterMapper::toDto)
                .getContent();
    }

    @Override
    public List<CharacterDto> search(CharacterSearchParametersDto searchParameters,
                                     Pageable pageable) {
        Specification<Character> spec = characterSpecificationBuilder.build(searchParameters);
        return characterRepository.findAll(spec, pageable)
                .map(characterMapper::toDto)
                .getContent();
    }

    @Override
    public CharacterDto getCharacterById(Long id) {
        return characterRepository.findById(id)
                .map(characterMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Can't find "
                        + "character by id: " + id));
    }

    @Override
    public CharacterDto getRandomCharacter() {
        long count = characterRepository.count();
        if (count == 0) {
            throw new EntityNotFoundException("No characters available in the database");
        }

        int randomOffset = ThreadLocalRandom.current().nextInt((int) count);
        Page<Character> characterPage = characterRepository
                .findAll(PageRequest.of(randomOffset, 1));

        return characterPage.getContent().stream()
                .findFirst()
                .map(characterMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Failed to fetch random character"));
    }
}

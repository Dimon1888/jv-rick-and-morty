package mate.academy.rickandmorty.service;

import java.util.List;
import mate.academy.rickandmorty.dto.CharacterDto;
import mate.academy.rickandmorty.dto.CharacterSearchParametersDto;
import org.springframework.data.domain.Pageable;

public interface CharacterService {
    List<CharacterDto> getAll(Pageable pageable);

    List<CharacterDto> search(CharacterSearchParametersDto searchParameters, Pageable pageable);

    CharacterDto getCharacterById(Long id);

    CharacterDto getRandomCharacter();
}

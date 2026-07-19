package mate.academy.rickandmorty.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import mate.academy.rickandmorty.dto.CharacterDto;
import mate.academy.rickandmorty.dto.CharacterSearchParametersDto;
import mate.academy.rickandmorty.service.CharacterService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Rick and Morty Character Management", description = "Endpoints for"
        + " managing and searching characters")
@RestController
@RequestMapping("/characters")
@RequiredArgsConstructor
public class CharacterController {
    private final CharacterService characterService;

    @GetMapping
    @Operation(summary = "Get all characters", description = "Get a paginated"
            + " list of all available characters")
    public List<CharacterDto> getAll(@ParameterObject @PageableDefault(size = 10)
                                         Pageable pageable) {
        return characterService.getAll(pageable);
    }

    @GetMapping("/search")
    @Operation(summary = "Search characters", description = "Search for"
            + " characters dynamically using parameters "
            + "(name, status, gender, etc.)")
    public List<CharacterDto> searchCharacters(
            CharacterSearchParametersDto searchParameters,
            @ParameterObject @PageableDefault(size = 10) Pageable pageable
    ) {
        return characterService.search(searchParameters, pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get character by ID", description = "Get details"
            + " of a specific character by their ID")
    public CharacterDto getCharacterById(@PathVariable Long id) {
        return characterService.getCharacterById(id);
    }

    @GetMapping("/random")
    @Operation(summary = "Get a random character", description = "Fetch one"
            + " random character from the database")
    public CharacterDto getRandomCharacter() {
        return characterService.getRandomCharacter();
    }
}

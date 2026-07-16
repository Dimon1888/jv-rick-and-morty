package mate.academy.rickandmorty.service;

import mate.academy.rickandmorty.dto.BookDto;
import mate.academy.rickandmorty.dto.BookSearchParametersDto;
import mate.academy.rickandmorty.dto.CreateBookRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService {
    Page<BookDto> getAll(Pageable pageable);

    Page<BookDto> search(BookSearchParametersDto searchParameters, Pageable pageable);

    BookDto getBookById(Long id);

    BookDto createBook(CreateBookRequestDto bookDto);

    BookDto update(Long id, CreateBookRequestDto bookDto);

    void deleteById(Long id);
}

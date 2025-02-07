package hu.cubix.logistics.mapper;

import hu.cubix.logistics.dto.SectionDto;
import hu.cubix.logistics.entities.Section;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ISectionMapper {

    SectionDto sectionToDto(Section Section);
    List<SectionDto> sectionsToDtos(List<Section> Sections);
    Section dtoToSection(SectionDto SectionDto);
    List<Section> dtosToSections(List<SectionDto> SectionDtos);
}

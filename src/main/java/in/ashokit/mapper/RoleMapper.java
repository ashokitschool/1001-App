package in.ashokit.mapper;

import org.modelmapper.ModelMapper;

import in.ashokit.dto.RoleDto;
import in.ashokit.entity.RoleEntity;

public class RoleMapper {

    private static ModelMapper modelMapper = new ModelMapper();

    public static RoleDto toDto(RoleEntity roleEntity) {
        return modelMapper.map(roleEntity, RoleDto.class);
    }

    public static RoleEntity toEntity(RoleDto roleDto) {
        return modelMapper.map(roleDto, RoleEntity.class);
    }
}

package in.ashokit.service.impl;

import in.ashokit.dto.RoleDto;
import in.ashokit.entity.RoleEntity;
import in.ashokit.mapper.RoleMapper;
import in.ashokit.repo.RoleRepository;
import in.ashokit.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepo;

    @Override
    public RoleDto createRole(RoleDto roleDto) {
        RoleEntity entity = RoleMapper.toEntity(roleDto);
        RoleEntity savedEntity = roleRepo.save(entity);
        return RoleMapper.toDto(savedEntity);
    }

    @Override
    public List<RoleDto> getAllRoles() {
        return roleRepo.findAll()
                .stream()
                .map(role -> RoleMapper.toDto(role))
                .collect(Collectors.toList());
    }

    @Override
    public RoleDto getRoleById(Integer roleId) {
        Optional<RoleEntity> byId = roleRepo.findById(roleId);
        if(byId.isPresent()) {
            RoleEntity roleEntity = byId.get();
            return RoleMapper.toDto(roleEntity);
        }
        return null;
    }

    @Override
    public RoleDto getRoleByName(String roleName) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public RoleDto updateRole(Integer roleId, RoleDto roleDto) {
        Optional<RoleEntity> byId = roleRepo.findById(roleId);
        if(byId.isPresent()) {
            RoleEntity roleEntity = byId.get();
            roleEntity.setName(roleDto.getName());
            roleEntity.setActiveSw(roleDto.getActiveSw());
            RoleEntity updatedEntity = roleRepo.save(roleEntity);
            return RoleMapper.toDto(updatedEntity);
        }
        return null;
    }

    @Override
    public RoleDto deleteRole(Integer roleId) {
        Optional<RoleEntity> byId = roleRepo.findById(roleId);
        if(byId.isPresent()) {
            RoleEntity roleEntity = byId.get();
            roleEntity.setActiveSw("No");
            RoleEntity deletedEntity = roleRepo.save(roleEntity);
            return RoleMapper.toDto(deletedEntity);
        }
        return null;
    }

}

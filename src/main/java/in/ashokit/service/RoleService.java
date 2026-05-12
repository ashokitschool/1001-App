package in.ashokit.service;

import in.ashokit.dto.RoleDto;

import java.util.List;

public interface RoleService {

    public RoleDto createRole(RoleDto roleDto);

    public RoleDto updateRole(Integer roleId, RoleDto roleDto);

    public List<RoleDto> getAllRoles();

    public RoleDto getRoleById(Integer roleId);

    public RoleDto getRoleByName(String roleName);

    public RoleDto deleteRole(Integer roleId);
}

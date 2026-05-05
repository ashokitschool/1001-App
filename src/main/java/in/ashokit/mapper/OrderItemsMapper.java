package in.ashokit.mapper;

import in.ashokit.dto.OrderItemsDto;
import in.ashokit.entities.OrderItemsEntity;
import org.modelmapper.ModelMapper;


public class OrderItemsMapper {

    private static final ModelMapper modelMapper = new ModelMapper();

    public static OrderItemsEntity dtoToEntity(OrderItemsDto itemsDto) {
        return modelMapper.map(itemsDto, OrderItemsEntity.class);
    }

    public static OrderItemsDto entityToDto(OrderItemsEntity itemsEntity) {
        return modelMapper.map(itemsEntity, OrderItemsDto.class);
    }
}

package ${packageName};

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

<#list importPackages! as pkg>
import ${pkg};
</#list>

@Mapper(componentModel = "spring")
public interface ${className} {

    ${modelClassName} saveRequest2Entity(${saveRequestClassName} request);

    void updateRequest2Entity(@MappingTarget ${modelClassName} entity, ${saveRequestClassName} request);

}

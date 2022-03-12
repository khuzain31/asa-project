package id.asalife.api.utils.converters;

import id.asalife.api.domains.entities.Role;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ModelConverter {
    public static List<String> toStringRoleList(Collection<Role> roles) {
        List<String> stringList = new ArrayList<>();

        for (Role role : roles) {
            stringList.add(role.getName().name());
        }
        return stringList;
    }
}

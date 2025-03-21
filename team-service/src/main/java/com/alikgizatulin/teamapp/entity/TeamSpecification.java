package com.alikgizatulin.teamapp.entity;

import org.springframework.data.jpa.domain.Specification;

public class TeamSpecification {

    public static Specification<Team> containsName(String name) {
        return (root, query, cb) -> {
            if(name == null || name.trim().isEmpty()) {
                return null;
            }
            return cb.like(root.get("name"), "%" + name.trim().toLowerCase() + "%");
        };
    }


    public static Specification<Team> hasStatus(TeamStatus status) {
        return (root, query, cb) -> {
            if (status == null) {
                return null;
            }
            return cb.equal(root.get("status"),status);
        };
    }
}

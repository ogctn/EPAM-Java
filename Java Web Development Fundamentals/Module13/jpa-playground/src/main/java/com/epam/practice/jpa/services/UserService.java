package com.epam.practice.jpa.services;

import com.epam.practice.jpa.domain.User;
import com.epam.practice.jpa.dto.UserNameDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final EntityManager em;

    @Transactional
    public User create(User user) {
        if (user.getName() == null || user.getName().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        em.persist(user);
        return user;
    }

    @Transactional
    public User findByName(String name) {
        TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.name = :name", User.class);
        query.setParameter("name", name);
        User user = query.getSingleResult();
        //user.getAddresses().size();
        return user;
    }

    @Transactional
    public List<UserNameDto> queryUsersNameOnly() {
        TypedQuery<UserNameDto> query = em.createQuery(
                "SELECT new com.epam.practice.jpa.dto.UserNameDto(u.id, u.name) FROM User u", UserNameDto.class);
        return query.getResultList();
    }
}

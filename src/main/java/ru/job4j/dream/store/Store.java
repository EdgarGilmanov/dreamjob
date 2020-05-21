package ru.job4j.dream.store;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;
import ru.job4j.dream.model.User;

import java.util.Collection;
import java.util.Optional;

public interface Store {
    Collection<Post> findAllPosts();

    Collection<Candidate> findAllCandidates();

    void save(Post post);

    Optional<Post> findPostById(int id);

    void save(Candidate candidate);

    Optional<Candidate> findCandidateById(int id);

    Collection<User> findAllUsers();

    void save(User user);

    Optional<Post> findUserById(int id);

    boolean isExist(String email);

    boolean isExist(String email, String password);

}
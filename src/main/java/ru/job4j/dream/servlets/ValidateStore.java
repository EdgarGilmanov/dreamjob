package ru.job4j.dream.servlets;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;
import ru.job4j.dream.model.User;
import ru.job4j.dream.store.Store;
import java.util.*;

public class ValidateStore implements Store {
    private final List<User> users = new ArrayList<>();
    private final List<Post> posts = new ArrayList<>();
    private final List<Candidate> candidates = new ArrayList<>();

    @Override
    public Collection<Post> findAllPosts() {
        return posts;
    }

    @Override
    public Collection<Candidate> findAllCandidates() {
        return candidates;
    }

    @Override
    public void save(Post post) {
        posts.add(post);
    }

    @Override
    public Optional<Post> findPostById(int id) {
        Optional<Post> rsl = Optional.empty();
        for (Post p : posts) {
            if (p.getId() == id) {
                rsl = Optional.of(p);
                break;
            }
        }
        return rsl;
    }

    @Override
    public void save(Candidate candidate) {
        candidates.add(candidate);
    }

    @Override
    public Optional<Candidate> findCandidateById(int id) {
        Optional<Candidate> rsl = Optional.empty();
        for (Candidate c : candidates) {
            if (c.getId() == id) {
                rsl = Optional.of(c);
                break;
            }
        }
        return rsl;
    }

    @Override
    public Collection<User> findAllUsers() {
        return users;
    }

    @Override
    public void save(User user) {
        users.add(user);
    }

    @Override
    public Optional<User> findUserById(int id) {
        Optional<User> rsl = Optional.empty();
        for (User u : users) {
            if (u.getId() == id) {
                rsl = Optional.of(u);
                break;
            }
        }
        return rsl;
    }

    @Override
    public boolean isExist(String email) {
        for (User u : users) {
            if (u.getEmail().endsWith(email)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isExist(String email, String password) {
        for (User u : users) {
            if (u.getEmail().endsWith(email) && u.getPassword().endsWith(password)) {
                return true;
            }
        }
        return false;
    }
}
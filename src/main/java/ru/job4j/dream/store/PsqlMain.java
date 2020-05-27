package ru.job4j.dream.store;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;
import ru.job4j.dream.model.User;

public class PsqlMain {
    public static void main(String[] args) {
        Store store = PsqlStore.instOf();
        store.save(new Post(0, "Java Job #1"));
        store.save(new Post(0, "Java Job #2"));
        store.save(new Post(0, "Java Job #3"));

        store.save(new Candidate(0, 0, "Java Candidate #1"));
        store.save(new Candidate(0, 0, "Java Candidate #2"));
        store.save(new Candidate(0, 0, "Java Candidate #3"));

        store.save(new User(0, "test1", "test1@mail", "pass"));
        store.save(new User(0, "test2", "test2@mail", "pass"));
        store.save(new User(0, "test3", "test3@mail", "pass"));

        for (Post post : store.findAllPosts()) {
            System.out.println(post.getId() + " " + post.getName());
        }

        for (Candidate can : store.findAllCandidates()) {
            System.out.println(can.getId() + " " + can.getName());
        }

        for (User user : store.findAllUsers()) {
            System.out.println(user.getId() + " " + user.getName());
        }
    }
}
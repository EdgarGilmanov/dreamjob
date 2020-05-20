package ru.job4j.dream.store;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;

public class PsqlMain {
    public static void main(String[] args) {
        Store store = PsqlStore.instOf();
        store.save(new Post(0, "Java Job #1"));
        store.save(new Post(0, "Java Job #2"));
        store.save(new Post(0, "Java Job #3"));

        store.save(new Candidate(0, "", "Java Candidate #1"));
        store.save(new Candidate(0, "", "Java Candidate #2"));
        store.save(new Candidate(0, "", "Java Candidate #3"));

        for (Post post : store.findAllPosts()) {
            System.out.println(post.getId() + " " + post.getName());
        }

        for (Candidate can : store.findAllCandidates()) {
            System.out.println(can.getId() + " " + can.getName());
        }
    }
}
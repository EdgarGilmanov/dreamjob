package ru.job4j.dream.store;

import org.apache.commons.dbcp2.BasicDataSource;
import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;
import ru.job4j.dream.model.User;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;


public class PsqlStore implements Store {
    private final BasicDataSource pool = new BasicDataSource();

    private PsqlStore() {
        Properties cfg = new Properties();
        try (BufferedReader io = new BufferedReader(
                new FileReader("db.properties")
        )) {
            cfg.load(io);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        try {
            Class.forName(cfg.getProperty("jdbc.driver"));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        pool.setDriverClassName(cfg.getProperty("jdbc.driver"));
        pool.setUrl(cfg.getProperty("jdbc.url"));
        pool.setUsername(cfg.getProperty("jdbc.username"));
        pool.setPassword(cfg.getProperty("jdbc.password"));
        pool.setMinIdle(5);
        pool.setMaxIdle(10);
        pool.setMaxOpenPreparedStatements(100);
    }

    private static final class Lazy {
        private static final Store INST = new PsqlStore();
    }

    public static Store instOf() {
        return Lazy.INST;
    }

    @Override
    public Collection<Post> findAllPosts() {
        List<Post> posts = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("SELECT * FROM post")
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    posts.add(new Post(it.getInt("id"), it.getString("name")));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return posts;
    }

    @Override
    public Collection<Candidate> findAllCandidates() {
        List<Candidate> candidates = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("SELECT * FROM candidate")
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    candidates.add(new Candidate(it.getInt("id"), it.getInt("photo_id"), it.getString("name")));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return candidates;
    }

    @Override
    public void save(Post post) {
        if (post.getId() == 0) {
            create(post);
        } else {
            update(post);
        }
    }

    private Post create(Post post) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(
                     "INSERT INTO post(name) VALUES (?)", PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, post.getName());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    post.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return post;
    }

    private Candidate create(Candidate candidate) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(
                     "INSERT INTO candidate(name) VALUES (?)", PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, candidate.getName());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    candidate.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return candidate;
    }

    private void update(Post post) {
        try (Connection cnn = pool.getConnection();
            PreparedStatement st = cnn.prepareStatement(
                    "UPDATE post SET name = ? WHERE id = ?"
            )) {
            st.setString(1, post.getName());
            st.setInt(2, post.getId());
            st.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void update(Candidate candidate) {
        try (Connection cnn = pool.getConnection();
             PreparedStatement st = cnn.prepareStatement(
                     "UPDATE candidate SET name = ? WHERE id = ?"
             )) {
            st.setString(1, candidate.getName());
            st.setInt(2, candidate.getId());
            st.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Post> findPostById(int id) {
        Optional<Post> rsl = Optional.empty();
        try (Connection cnn = pool.getConnection();
             PreparedStatement st = cnn.prepareStatement(
                     "SELECT * FROM post WHERE id = ?"
             )) {
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                String name = rs.getString(2);
                rsl = Optional.of(new Post(id, name));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rsl;
    }

    @Override
    public void save(Candidate candidate) {
        if (candidate.getId() == 0) {
            create(candidate);
        } else {
            update(candidate);
        }
    }

    @Override
    public Optional<Candidate> findCandidateById(int id) {
        Optional<Candidate> rsl = Optional.empty();
        try (Connection cnn = pool.getConnection();
             PreparedStatement st = cnn.prepareStatement(
                     "SELECT * FROM candidate WHERE id = ?"
             )) {
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                int photoId = rs.getInt(2);
                String name = rs.getString(3);
                rsl = Optional.of(new Candidate(id, photoId, name));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rsl;
    }

    @Override
    public Collection<User> findAllUsers() {
        List<User> rsl = new ArrayList<>();
        try (Connection cnn = pool.getConnection();
             PreparedStatement st = cnn.prepareStatement(
                     "SELECT * FROM users")) {
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                int id = Integer.parseInt(rs.getString(1));
                String name = rs.getString(2);
                String email = rs.getString(3);
                String password = rs.getString(4);
                rsl.add(new User(id, name, email, password));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rsl;

    }

    @Override
    public void save(User user) {
        if (user.getId() == 0) {
            create(user);
        } else {
            update(user);
        }
    }

    @Override
    public Optional<User> findUserById(int id) {
        Optional<User> rsl = Optional.empty();
        try (Connection cnn = pool.getConnection();
             PreparedStatement st = cnn.prepareStatement(
                     "SELECT * FROM users WHERE id = ?"
             )) {
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                String name = rs.getString(2);
                String email = rs.getString(3);
                String password = rs.getString(4);
                rsl = Optional.of(new User(id, name, email, password));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rsl;
    }

    private void update(User user) {
        try (Connection cnn = pool.getConnection();
             PreparedStatement st = cnn.prepareStatement(
                     "UPDATE users SET name = ?, email = ?, password = ? WHERE id = ?"
             )) {
            st.setString(1, user.getName());
            st.setString(2, user.getEmail());
            st.setString(3, user.getPassword());
            st.setInt(4, user.getId());
            st.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private User create(User user) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(
                     "INSERT INTO users(name, email, password) VALUES (?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.executeUpdate();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    user.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public boolean isExist(String email) {
        try (Connection cnn = pool.getConnection();
             PreparedStatement st = cnn.prepareStatement(
                     "SELECT * FROM users WHERE email = ?")) {
            st.setString(1, email);
            ResultSet set = st.executeQuery();
            if (set.next()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean isExist(String email, String password) {
        try (Connection cnn = pool.getConnection();
             PreparedStatement st = cnn.prepareStatement(
                     "SELECT * FROM users WHERE email = ? AND password = ?")) {
            st.setString(1, email);
            st.setString(2, password);
            ResultSet set = st.executeQuery();
            if (set.next()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public String findPhotoById(String id) {
        try (Connection cnn = pool.getConnection();
             PreparedStatement st = cnn.prepareStatement(
                     "SELECT name FROM photo WHERE id = ?")) {
            st.setInt(1, Integer.parseInt(id));
            ResultSet ph = st.executeQuery();
            if (ph.next()) {
                return ph.getString(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
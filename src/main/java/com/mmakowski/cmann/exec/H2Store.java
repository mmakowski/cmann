package com.mmakowski.cmann.exec;

import com.google.common.collect.ImmutableList;
import com.mmakowski.cmann.model.Message;

import java.sql.*;
import java.time.Instant;
import java.util.List;

public final class H2Store implements Store, AutoCloseable {
    private final Connection connection;

    public H2Store() {
        try {
            Class.forName("org.h2.Driver");
            connection = DriverManager.getConnection("jdbc:h2:mem:");
            initialiseSchema();
        } catch (final ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void insertMessage(final Message message) {
        sqlExceptionToRuntimeException(() -> {
            try (PreparedStatement statement = connection.prepareStatement("insert into messages (user_name, message, message_ts) values (?, ?, ?)")) {
                statement.setString(1, message.userName);
                statement.setString(2, message.message);
                statement.setTimestamp(3, new Timestamp(message.timestamp.toEpochMilli()));
                statement.execute();
                return null;
            }
        });
    }

    @Override
    public List<Message> messagesByUser(final String userName) {
        return sqlExceptionToRuntimeException(() -> {
            try (PreparedStatement statement = connection.prepareStatement("select message, message_ts from messages where user_name = ? order by message_ts desc")) {
                statement.setString(1, userName);
                try (ResultSet result = statement.executeQuery()) {
                    final ImmutableList.Builder<Message> builder = ImmutableList.builder();
                    while (result.next())
                        builder.add(new Message(userName,
                                                result.getString("message"),
                                                Instant.ofEpochMilli(result.getTimestamp("message_ts").getTime())));
                    return builder.build();
                }
            }
        });
    }

    @Override
    public void insertSubsription(final String follower, final String followee) {
        sqlExceptionToRuntimeException(() -> {
            try (PreparedStatement statement = connection.prepareStatement("insert into subscriptions (follower, followee) values (?, ?)")) {
                statement.setString(1, follower);
                statement.setString(2, followee);
                statement.execute();
                return null;
            }
        });
    }

    @Override
    public List<Message> wallMessages(final String userName) {
        return sqlExceptionToRuntimeException(() -> {
            try (PreparedStatement statement = connection.prepareStatement(
                    "select user_name, message, message_ts from messages where user_name = ? " +
                    "union " +
                    "select m.user_name, m.message, m.message_ts from messages m, subscriptions s " +
                    "where s.follower = ? and s.followee = m.user_name " +
                    "order by message_ts desc")) {
                statement.setString(1, userName);
                statement.setString(2, userName);
                try (ResultSet result = statement.executeQuery()) {
                    final ImmutableList.Builder<Message> builder = ImmutableList.builder();
                    while (result.next())
                        builder.add(new Message(result.getString("user_name"),
                                                result.getString("message"),
                                                Instant.ofEpochMilli(result.getTimestamp("message_ts").getTime())));
                    return builder.build();
                }
            }
        });
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static <T> T sqlExceptionToRuntimeException(final DatabaseOperation<T> operation) {
        try {
            return operation.execute();
        } catch (final SQLException e) {
            throw new RuntimeException("error in database operation", e);
        }
    }

    private void initialiseSchema() throws SQLException {
        for (final String ddl : DDL) {
            try (PreparedStatement statement = connection.prepareStatement(ddl)) {
                statement.execute();
            }
        }
    }

    private static final List<String> DDL = ImmutableList.of(
            "create table messages (" +
            "  user_name varchar not null," +
            "  message varchar not null," +
            "  message_ts timestamp not null" +
            ")",
            "create table subscriptions (" +
            "  follower varchar not null," +
            "  followee varchar not null" +
            ")"
    );

    private interface DatabaseOperation<T> {
        T execute() throws SQLException;
    }
}

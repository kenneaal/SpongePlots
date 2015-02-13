/*
 * This file is part of SpongePlots, licensed under the MIT License (MIT).
 *
 * Copyright (c) Kenneth Aalberg <http://github.com/kenneaal/SpongePlots>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package net.localecho.kenneaal.SpongePlots;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import ninja.leaping.configurate.ConfigurationNode;

public class DB {
    public static void createTables(Connection con, int dbVersion) {
        if (con == null) {
            SpongePlots
                    .getLogger()
                    .error("[SpongePlots/DB]: createTables called with a null connection reference.");
            return;
        }
        try {
            PreparedStatement ps = null;
            final ResultSet rs = null;
            ps = con.prepareStatement("CREATE TABLE IF NOT EXISTS master (dbversion int, lastupdate datetime)");
            ps.executeUpdate();
            ps = con.prepareStatement("INSERT INTO master (dbversion,lastupdate) values ("
                    + dbVersion + ",CURRENT_TIMESTAMP)");
            ps.executeUpdate();
            SpongePlots.getLogger().info(
                    "[SpongePlots/DB]: Initialized database tables version "
                            + dbVersion + ".");
        } catch (final SQLException e) {
            SpongePlots.getLogger().error(
                    "[SpongePlots/DB]: Couldn't create tables! " + e);
        } finally {
            try {
                con.close();
            } catch (final SQLException e) {
                SpongePlots
                        .getLogger()
                        .error("[SpongePlots/DB]: Couldn't close database connection.");
            }
        }
        return;
    }

    public static Connection getConnection(ConfigurationNode config) {
        try {
            final String constring = "jdbc:mysql://"
                    + config.getNode("host").getString() + ":"
                    + config.getNode("port").getInt() + "/"
                    + config.getNode("database").getString()
                    + "?autoReconnect=true&user="
                    + config.getNode("username").getString() + "&password="
                    + config.getNode("password").getString();
            return DriverManager.getConnection(constring);
        } catch (final SQLException e) {
            SpongePlots.getLogger().error(
                    "[SpongePlots/DB]: Critical database connection error! "
                            + e);
        }
        return null;
    }
}

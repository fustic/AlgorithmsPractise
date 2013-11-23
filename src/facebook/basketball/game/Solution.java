package facebook.basketball.game;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class Solution {
    public static void main(String[] args) throws IOException {
        Solution solution = new Solution("/Users/vadimivanov/Downloads/basketball_game_example_input.txt");
        solution.playGames();
        System.exit(0);
    }
    public Solution(String filePath) throws FileNotFoundException {
        File file = new File(filePath);
        buildTeams(file);
    }
    public void playGames() {
        School school;
        for (int i = 0; i < schools.size(); ++i) {
            school = schools.get(i);
            school.buildTeams();
//            System.out.println(school.getTeamsPlayerNames());
            school.simulateGame();
            System.out.println("Case #" + (i + 1) + ": " + school.getCurrentPlayers());
        }
    }

    private List<School> schools;
    private void buildTeams (File file) throws FileNotFoundException {
        Scanner s = new Scanner(file).useDelimiter(System.getProperty("line.separator"));
        int N = Integer.parseInt(s.next());
        School school;
        schools = new ArrayList<School>();
        while (s.hasNext()) {
            school = new School(s.next());
            for (int i = 0; i < school.getNumberOfPlayers(); ++i) {
                school.addPlayer(s.next());
            }
            schools.add(school);
        }
//        System.out.println(ToStringBuilder.reflectionToString(schools));
    }

    private class School {
        private List<Player> players;
        private List<Player> team1;
        private List<Player> team2;
        private List<Player> team1OnField;
        private List<Player> team1OnBanch;
        private List<Player> team2OnField;
        private List<Player> team2OnBanch;
        private int numberOfPlayersOnField;
        private int totalMinutes;
        private int numberOfPlayers;
        public School (String cfg) {
            String rule = cfg.replace("\r"," ").replace("\t"," ").replace("  ", " ");
            String[] rules = rule.split(" ");
            numberOfPlayers = Integer.parseInt(rules[0]);
            totalMinutes = Integer.parseInt(rules[1]);
            numberOfPlayersOnField = Integer.parseInt(rules[2]);
            players = new ArrayList<Player>();
            team1 = new ArrayList<Player>();
            team2 = new ArrayList<Player>();
            team1OnField = new ArrayList<Player>();
            team1OnBanch = new ArrayList<Player>();
            team2OnField = new ArrayList<Player>();
            team2OnBanch = new ArrayList<Player>();
        }

        public void addPlayer(String player) {
            players.add(new Player(player));
        }
        public int getNumberOfPlayers() {
            return numberOfPlayers;
        }

        public void buildTeams() {
            Collections.sort(players, new Comparator<Player>() {
                @Override
                public int compare(Player player, Player player2) {
                    if (player.Percentage == player2.Percentage) {
                        return player2.Height - player.Height;
                    }
                    return player2.Percentage - player.Percentage;
                }
            });

            String s = "";
            List<String> team = new ArrayList<String>();
            Player player;
            for (int i = 0; i < players.size(); ++i) {
                player = players.get(i);
                player.Runk = i + 1;
                team.add(player.Name);
            }
            s += StringUtils.join(team, ", ");

            for (int i = 0; i < players.size(); ++i) {
                if (i % 2 == 1) { //odd
                    team1.add(players.get(i));
                } else { //even
                    team2.add(players.get(i));
                }
            }
        }

        public void simulateGame() {
            simulateGame(team1, team1OnField, team1OnBanch);
            simulateGame(team2, team2OnField, team2OnBanch);
        }
        private void simulateGame(List<Player> team, List<Player> onField, List<Player> onBanch) {
            Collections.sort(team, new Comparator<Player>() {
                @Override
                public int compare(Player player, Player player2) {
                    return player.Runk - player2.Runk;
                }
            });
            Player player;
            for (int i = 0; i < team.size(); ++i) {
                player = team.get(i);
                if (i < numberOfPlayersOnField) {
                    onField.add(player);
                    player.MinutesOnField++;
                } else {
                    onBanch.add(player);
                }
            }
            if (numberOfPlayersOnField < team.size()) {
                Player playerToBanch;
                Player playerToField;
                for (int i = 1; i <= totalMinutes; ++i) {
                    Collections.sort(onField, teamOnFieldSorter);
                    playerToBanch = onField.get(0);
                    onField.remove(0);
                    Collections.sort(onBanch, teamOnBanchSorter);
                    playerToField = onBanch.get(0);
                    onBanch.remove(0);
                    onField.add(playerToField);
                    onBanch.add(playerToBanch);
                    for (int j = 0; j < onField.size(); ++j) {
                        player = onField.get(j);
                        player.MinutesOnField++;
                    }
                }
            }

        }
        public String getCurrentPlayers() {
            List<String> team = new ArrayList<String>();
            for (int i = 0; i < team1OnField.size(); ++i) {
                team.add(team1OnField.get(i).Name);
            }
            for (int i = 0; i < team2OnField.size(); ++i) {
                team.add(team2OnField.get(i).Name);
            }
            Collections.sort(team);
            return StringUtils.join(team, " ");
        }
        public String getTeamsPlayerNames() {
            String s = "team1: ";
            List<String> team = new ArrayList<String>();
            for (int i = 0; i < team1.size(); ++i) {
                team.add(team1.get(i).Name);
            }
            s += StringUtils.join(team, ", ");
            s += "; team2: ";
            team = new ArrayList<String>();
            for (int i = 0; i < team2.size(); ++i) {
                team.add(team2.get(i).Name);
            }
            s += StringUtils.join(team, ", ");
            return s;
        }

        private Comparator<Player> teamOnFieldSorter = new Comparator<Player>() {
            @Override
            public int compare(Player player, Player player2) {
                if (player.MinutesOnField == player2.MinutesOnField) {
                    return player2.Runk - player.Runk;
                }
                return player2.MinutesOnField - player.MinutesOnField;
            }
        };
        private Comparator<Player> teamOnBanchSorter = new Comparator<Player>() {
            @Override
            public int compare(Player player, Player player2) {
                if (player.MinutesOnField == player2.MinutesOnField) {
                    return player.Runk - player2.Runk;
                }
                return player.MinutesOnField - player2.MinutesOnField;
            }
        };
    }
    private class Player {
        public Player (String player) {
            String rule = player.replace("\r"," ").replace("\t"," ").replace("  ", " ");
            String[] params = rule.split(" ");
            Name = params[0];
            Percentage = Integer.parseInt(params[1]);
            Height = Integer.parseInt(params[2]);
            MinutesOnField = 0;
        }

        public String Name;
        public int Height;
        public int Percentage;
        public int MinutesOnField;
        public int Runk;
    }
}

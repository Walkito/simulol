package br.com.walkito.simulol.models.team;

import br.com.walkito.simulol.models.gameSession.GameSession;

public class TeamRequest {
    private Team team;
    private GameSession gameSession;
    private String idUser;

    public TeamRequest() {
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public GameSession getGameSession() {
        return gameSession;
    }

    public void setGameSession(GameSession gameSession) {
        this.gameSession = gameSession;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
